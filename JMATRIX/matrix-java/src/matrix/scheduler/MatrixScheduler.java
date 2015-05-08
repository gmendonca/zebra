package matrix.scheduler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import matrix.epoll.MatrixEpollServer;
import matrix.protocol.Metamatrix.MatrixMsg;
import matrix.protocol.Metatask.TaskMsg;
import matrix.protocol.Metazht.Value;
import matrix.util.CmpQueueItem;
import matrix.util.Declarations;
import matrix.util.MatrixTcpProxy;
import matrix.util.ReturnValue;
import matrix.util.TaskMsgQueueItem;
import matrix.util.Tools;

public class MatrixScheduler extends PeerScheduler{

	public MatrixScheduler(String configFile) throws IOException {
		super(configFile);

		long startTime, stopTime;
		startTime = System.currentTimeMillis();

		/* number of neighbors is equal to the
		 * squared root of all number of schedulers
		 * */
		if (schedulerList.size() == 1)
			config.workStealingOn = 0;

		numNeigh = (int) (Math.sqrt(schedulerList.size()) + 0.5);
		neighIdx = new int[numNeigh];
		maxLoadedIdx = -1;
		maxLoad = -1000000;
		pollInterval = config.wsPollIntervalStart;
		chooseBitMap = new Boolean[schedulerList.size()];
		resetChoosebm();
		startWS = false;
		
		stopTime = System.currentTimeMillis();
		
		if (config.schedulerLog == 1) {
			String schedulerLogFile = new String("./scheduler." + (Integer.toString(schedulerList.size())) +
					"." + Long.toString(config.numTaskPerClient) + 
					"." + Integer.toString(getIndex()));
			schedulerLogOS = new BufferedWriter(new FileWriter(schedulerLogFile, true));

			long diff = stopTime - startTime;
			schedulerLogOS.write("I am a Scheduler, it takes me " + diff + " ms for initialization!");
		}

		numIdleCore = config.numCorePerExecutor;
		prevNumTaskFin = 0;
		numTaskFin = 0;
		numTaskSteal = 0;
		numTaskStolen = 0;
		numWS = 0;
		numWSFail = 0;

		waitQueue = new ArrayDeque<TaskMsg>();
		localQueue = new PriorityQueue<TaskMsgQueueItem>();
		wsQueue = new PriorityQueue<TaskMsgQueueItem>();
		completeQueue = new ArrayDeque<CmpQueueItem>();
		taskTimeEntry = new ArrayList<String>();

		localData = new TreeMap<String, String>();
		cache = false;
		if(Declarations.DATA_CACHE)
			cache = true;

		String taskLogFile = new String("./task." + (Integer.toString(schedulerList.size())) + "."
						+ Long.toString(config.numTaskPerClient) + "."
						+ Integer.toString(getIndex()));
		taskLogOS = new BufferedWriter(new FileWriter(taskLogFile, true));
	}
	
	/* the scheduler tries to regist to ZHT server by increasing a counter.
	 * The purpose of doing the registration is to ensure that all the
	 * schedulers are running at the beginning before moving forward
	 * */
	public void regist() {
		String regKey = new String("number of scheduler registered");
		String taskFinKey = new String("num tasks done");
		String recvKey = new String("num tasks recv");

		/* the first scheduler (index = 0) intializes the records
		 * including both the number of registered schedulers and
		 * the number of tasks done
		 * */
		if (getIndex() == 0) {
			zc.insert(regKey, new String("1"));
			zc.insert(taskFinKey, new String("0"));
			zc.insert(recvKey, new String("0"));
		} else {
			String value;
			value = zc.lookup(regKey);
			while (value.isEmpty()) {
				try{ Thread.sleep(config.sleepLength); } catch(Exception e) { }
				value = zc.lookup(regKey);
			}

			int newValNum = Integer.parseInt(value) + 1;
			String newVal = Integer.toString(newValNum);
			String queryVal = new String();

			while (zc.compare_swap_int(regKey, value, newVal) != 0) {
				queryVal = zc.compare_swap_string(regKey, value, newVal);
				if (queryVal.isEmpty()) {
					value = zc.lookup(regKey);
				} else {
					value = queryVal;
				}
				newValNum = Integer.parseInt(value) + 1;
				newVal = Integer.toString(newValNum);
				try { Thread.sleep(config.sleepLength); } catch(Exception e){ }
			}
		}
	}
	
	public void loadData() {
		String filePath = new String("./workload_dag/file." + Integer.toString(
				schedulerList.size()) + "." + Long.toString(config.numTaskPerClient));

		ArrayList<String> fileList = new ArrayList<String>();
		try {
			fileList = Tools.readFromFile(filePath, Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < fileList.size(); i++) {
			ArrayList<String> lineList = Tools.tokenizer(fileList.get(i), " ");
			if (Integer.parseInt(lineList.get(1)) == getIndex()) {
				localData.put(lineList.get(0), "This is the data");
			}
		}
	}
	
	public void getTaskFromFile() {
		String done;
		String key = new String("Split Workload");
		done = zc.lookup(key);

		while (done.isEmpty()) {
			try{ Thread.sleep(config.sleepLength); } catch (Exception e) { }
			done = zc.lookup(key);
		}

		String filePath = config.schedulerWorkloadPath + "/workload."
				+ getIndex();

		ArrayList<String> lines = new ArrayList<String>();
		try {
			lines = Tools.readWorkloadFromFile(filePath, Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (lines.isEmpty()) {
			return;
		} else {
			int numTask = 0;
			for(String line : lines){
				numTask++;
				ArrayList<String> taskItemStr = Tools.tokenizer(line, " ");
				TaskMsg.Builder tm = TaskMsg.newBuilder();
				tm.setTaskId(taskItemStr.get(0));
				tm.setUser(taskItemStr.get(1));
				tm.setDir(taskItemStr.get(2));
				tm.setCmd(taskItemStr.get(3));
				tm.setDataLength(0);
				long time = System.currentTimeMillis();
				taskTimeEntry.add(tm.getTaskId() + "\tWaitQueueTime\t" + time);
				waitQueue.push(tm.build());
			}

			String numTaskRecvStr = new String(), numTaskRecvMoreStr = new String(), queryValue = new String();
			String recvKey = new String("num tasks recv");
			numTaskRecvStr = zc.lookup(recvKey);

			long numTaskRecv = Long.parseLong(numTaskRecvStr);
			numTaskRecv += numTask;
			numTaskRecvMoreStr = Long.toString(numTaskRecv);
			//System.out.println("number of task more recv is:" << numTaskRecv);
			while (zc.compare_swap_int(recvKey, numTaskRecvStr, numTaskRecvMoreStr) != 0) {
				queryValue = zc.compare_swap_string(recvKey, numTaskRecvStr, numTaskRecvMoreStr);
				if (queryValue.isEmpty()) {
					numTaskRecvStr = zc.lookup(recvKey);
				} else {
					numTaskRecvStr = queryValue;
				}
				//System.out.println("OK, conflict, current value is:" << numTaskRecvStr);
				numTaskRecv = Long.parseLong(numTaskRecvStr);
				numTaskRecv += numTask;
				numTaskRecvMoreStr = Long.toString(numTaskRecv);
			}
		}
	}
	
	/* send tasks to another thief scheduler */
	public void sendTask(Socket sockfd) {
		int numTaskToSend = -1;
		ArrayList<TaskMsg> taskList = new ArrayList<TaskMsg>();
		synchronized(this){

		/* number of tasks to send equals to half of the current load,
		 * which is calculated as the number of tasks in the ready queue
		 * minus number of idle cores */
		numTaskToSend = wsQueue.size() / 2;
		for (int i = 0; i < numTaskToSend; i++) {
			taskList.add(wsQueue.poll().getTaskMsg());
		}
		}
		sendBatchTasks(taskList, sockfd, "scheduler");
	}
	
	/* receive tasks submitted by client */
	public void recvTaskFromClient(String str, ServerSocket sockfd) {
		//StringBuffer taskStr = new StringBuffer("");
		//taskStr.append(str);
		//ReturnValue rv = MatrixTcpProxy.recvMul(sockfd,taskStr.toString());
		//String taskStrLs = rv.result.substring(0, taskStr.length() - 1);
		//System.out.println("The task String is:" << taskStrLs);
		System.out.println("Hi you");
		ArrayList<String> stealList;
		if(str.endsWith("$"))
			stealList = Tools.tokenizer(str.substring(0, str.length()-1), "##");
		else
			stealList = Tools.tokenizer(str, "##");
		System.out.println("The task size is:" + stealList.size());
		if (stealList.size() == 1) {
			return;
		}

		MatrixMsg mmNumTask = Tools.strToMm(stealList.get(0));
		long numTask = mmNumTask.getCount();
		System.out.println("Number of tasks is: " + numTask);
		int increment = 0;

		for (int i = 1; i < stealList.size(); i++) {
			MatrixMsg mm = Tools.strToMm(stealList.get(i));
			ArrayList<TaskMsg> tmList = new ArrayList<TaskMsg>();
			String time = Long.toString(System.currentTimeMillis());
			for (int j = 0; j < mm.getCount(); j++) {
				tmList.add(Tools.strToTaskMsg(mm.getTasks(j)));
			}
			System.out.println("OK, before the time record!");
			synchronized(this){
				for (int j = 0; j < mm.getCount(); j++) {
					String taskMD;
					System.out.println("Now, I am doing a zht lookup: " + tmList.get(j).getTaskId());
					taskMD = zc.lookup(tmList.get(j).getTaskId());
					//System.out.println("I got the task metadata: " + taskMD);
					Value value = Tools.strToValue(taskMD);
					taskTimeEntry.add(tmList.get(j).getTaskId() + "\tSubmissionTime\t"
							+ Long.toString(value.getSubmitTime()));
					taskTimeEntry.add(tmList.get(j).getTaskId()
							+ "\tWaitQueueTime\t" + time);
				}
			}
			System.out.println("OK, I did the time record!");
			increment += mm.getCount();

			synchronized(this){
				for (int j = 0; j < mm.getCount(); j++) {
					waitQueue.add(tmList.get(j));
				}
			}
		}
		System.out.println("OK, now I have put the tasks in the wait queue, let's update the ZHT record!");
		String numTaskRecvStr = new String(), numTaskRecvMoreStr = new String(), queryValue = new String();
		numTaskRecvStr = zc.lookup("num tasks recv");
		long numTaskRecv = Long.parseLong(numTaskRecvStr);
		System.out.println("Number of tasks recv is:" + numTaskRecvStr);
		numTaskRecv += numTask;
		numTaskRecvMoreStr = Long.toString(numTaskRecv);
		System.out.println("The one potential to insert is: " + numTaskRecvMoreStr);
		increment += 2;
		System.out.println("Compare Swap Int: " + zc.compare_swap_int("num tasks recv", numTaskRecvStr, numTaskRecvMoreStr));
		do {
			queryValue = zc.compare_swap_string("num tasks recv", numTaskRecvStr,numTaskRecvMoreStr);
			System.out.println("queryValue: " + queryValue);
			if (queryValue.isEmpty()) {
				numTaskRecvStr = zc.lookup("num tasks recv");
				increment++;
			} else {
				numTaskRecvStr = queryValue;
			}
			numTaskRecv = Long.parseLong(numTaskRecvStr);
			numTaskRecv += numTask;
			numTaskRecvMoreStr = Long.toString(numTaskRecv);
		} while (zc.compare_swap_int("num tasks recv", numTaskRecvStr, numTaskRecvMoreStr) != 0);

		if (increment > 0) {
			synchronized(this){
				increZHTMsgCount(increment);
			}
		}
		System.out.println("Now, I am done with the number of tasks:" + queryValue);
	}
	
	public void recvPushingTask(MatrixMsg mm, Socket sockfd) {
		long increment = 0;
		TaskMsg tm = Tools.strToTaskMsg(mm.getTasks(0));

		synchronized(this){
			taskTimeEntry.add(
					tm.getTaskId() + "\tPushQueuedTime\t"
							+ System.currentTimeMillis());
		}

		synchronized(this){
			localQueue.add(new TaskMsgQueueItem(tm));
		}
		//increment += 2;

		MatrixMsg.Builder mmSuc = MatrixMsg.newBuilder();
		mmSuc.setMsgType("success receiving pushing task");
		//String mmSucStr = mmSuc.SerializeAsString();
		String mmSucStr = Tools.mmToStr(mmSuc.build());
		MatrixTcpProxy.sendBf(sockfd, mmSucStr);

		/*ZHTMsgCountMutex.lock();
		 incre_ZHT_msg_count(increment);
		 ZHTMsgCountMutex.unlock();*/
	}
	
	/* processing requests received by the epoll server */
	public int procReq(Socket sockfd, String buf, ServerSocket recvSock) {
		String bufStr = new String(buf);
		//System.out.println(bufStr);
		
		
		/* this is client submitting tasks */
		String prefix = "client send tasks";
		System.out.println("I am in procReq " + bufStr.length());
		if (bufStr.substring(0, prefix.length()).equals(prefix)) {
			System.out.println("Sending task in procReq");
			recvTaskFromClient(bufStr, recvSock);
		} else {
			MatrixMsg mm;

			mm = Tools.strToMm(bufStr);

			long increment = 0;

			/* message type is stored in pkg.virtualpath(), and contents
			 * are stored in pkg.readfullpath() */
			String msg = mm.getMsgType();
			if (msg.equals("query load")) { 	// thief querying load
				int load = wsQueue.size();
				MatrixMsg.Builder mmLoad = MatrixMsg.newBuilder();
				mmLoad.setMsgType("send load");
				mmLoad.setCount(load);
				String strLoad = Tools.mmToStr(mmLoad.build());
				MatrixTcpProxy.sendBf(sockfd, strLoad);
			} else if (msg.equals("steal task")) {	// thief steals tasks
				sendTask(sockfd);
			} else if (msg.equals("scheduler push task")) {
				recvPushingTask(mm, sockfd);
			} else if (msg.equals("scheduler require data")) {
				String dataPiece;
				synchronized(this){
					if (localData.get(mm.getExtraInfo()) == localData.lastEntry().getValue()) {
						dataPiece = "shit, that is wrong!";
					} else {
						dataPiece = localData.get(mm.getExtraInfo());
					}
				}

				MatrixMsg.Builder mmDataPiece = MatrixMsg.newBuilder();
				mmDataPiece.setMsgType("scheduler send data");
				mmDataPiece.setExtraInfo(dataPiece);
				String dataStr = Tools.mmToStr(mmDataPiece.build());
				//mmDataPiece.SerializeAsString();
				//send_bf(sockfd, dataStr);
				MatrixTcpProxy.sendBig(sockfd, dataStr);
			}
		}
		try {
			sockfd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 1;
	}

	/* fork epoll server thread */
	public void forkEsThread() {
		MatrixEpollServer mes;
		Serving s;
		
		//while(true){
			mes = new MatrixEpollServer(config.schedulerPortNo, this);
			s = new Serving(mes);
			s.start();
			
			/*try {
				s.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		//}
	}

	/* reset the bitmap of neighbors chosen, "false"
	 * means hasn't been chosen */
	public void resetChoosebm() {
		for (int i = 0; i < schedulerList.size(); i++) {
			chooseBitMap[i] = false;
		}
	}

	/* choose candidate neighbors to steal tasks,
	 * for simplicity, we randomly choose neighbors
	 * */
	public void chooseNeigh() {
		int idx = -1;
		for (int i = 0; i < numNeigh; i++) {
			idx = new Random().nextInt(schedulerList.size());
			while (idx == getIndex() || chooseBitMap[idx]) {
				idx = new Random().nextInt(schedulerList.size());
			}
			neighIdx[i] = idx;
			chooseBitMap[idx] = true;
			//System.out.println("The neighbor to choose is:" << idx);
		}

		resetChoosebm();
	}

	/* find the neighbor with the maximum load by quering
	 * the load information of each scheduler one by one
	 * */
	public void findMostLoadedNeigh() {
		MatrixMsg.Builder mm = MatrixMsg.newBuilder();
		mm.setMsgType("query load");

		String strLoadQuery = Tools.mmToStr(mm.build());

		long load = -1;

		for (int i = 0; i < numNeigh; i++) {
			String result = new String();
			ReturnValue rv;
			synchronized(this){
				ServerSocket sockfd = null;
				try {
					sockfd = new ServerSocket(MatrixTcpProxy.sendFirst(schedulerList.get(neighIdx[i]),config.schedulerPortNo, strLoadQuery));
				} catch (Exception e) {
					e.printStackTrace();
				}
				rv = MatrixTcpProxy.recvBf(sockfd, result);

			}
			if (rv.result.isEmpty()) {
				continue;
			}
			MatrixMsg mmLoad = Tools.strToMm(rv.result);

			load = mmLoad.getCount();
			if (maxLoad < load) {
				maxLoad = load;
				maxLoadedIdx = neighIdx[i];
			}
		}
	}

	/* receive several tasks (numTask) from another scheduler */
	public Boolean recvTaskFromScheduler(ServerSocket sockfd) {
		String taskStr = new String();
		ReturnValue rv = MatrixTcpProxy.recvMul(sockfd, taskStr);

		String taskStrLs = rv.result.substring(0, rv.result.length() - 1);

		ArrayList<String> stealList = Tools.tokenizer(taskStrLs, "##");
		if (stealList.size() == 1) {
			return false;
		}

		MatrixMsg mmNumTask = Tools.strToMm(stealList.get(0));
		long numTask = mmNumTask.getCount();

		for (int i = 1; i < stealList.size(); i++) {
			MatrixMsg mm = Tools.strToMm(stealList.get(i));

			ArrayList<TaskMsg> tmList = new ArrayList<TaskMsg>();
			String time = "" + System.currentTimeMillis();

			for (int j = 0; j < mm.getCount(); j++) {
				tmList.add(Tools.strToTaskMsg(mm.getTasks(j)));
			}

			synchronized(this){
				for (int j = 0; j < mm.getCount(); j++) {
					taskTimeEntry.add(
							tmList.get(j).getTaskId() + "\tWorkStealQueuedTime\t" + time);
				}
			}

			synchronized(this){
				for (int j = 0; j < mm.getCount(); j++) {
					wsQueue.add(new TaskMsgQueueItem(tmList.get(j)));
				}
			}
		}

		return true;
	}

	/* try to steal tasks from the most-loaded neighbor. The thief first
	 * sends a message ("steal tasks") to the neighbor, and then waits
	 * for the neighbor's response. The neighbor first sends a message
	 * notifying how many tasks could be migrated, then sends all the
	 * tasks batch by batch.
	 * */
	public Boolean stealTask() {
		
		Boolean ret = false;
		/* if no neighbors have ready tasks */
		if (maxLoad <= 0) {
			return ret;
		}

		MatrixMsg.Builder mm = MatrixMsg.newBuilder();
		mm.setMsgType("steal task");
		//String strStealTask = mm.SerializeAsString();
		String strStealTask = Tools.mmToStr(mm.build());

		String numTaskPkgStr;
		//System.out.println("OK, before sending stealing task message!");
		synchronized(this){
		//System.out.println("OK, I am sending stealing task message!");
		ServerSocket sockfd = null;
		try {
			sockfd = new ServerSocket(MatrixTcpProxy.sendFirst(schedulerList.get(maxLoadedIdx),config.schedulerPortNo, strStealTask));
		} catch (Exception e) {
			e.printStackTrace();
		}

		ret = recvTaskFromScheduler(sockfd);
		try {
			sockfd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		}
		return ret;
	}

	/* fork work stealing thread */
	public void forkWsThread() {
		WorkStealing ws;
		if (config.workStealingOn == 1) {
			//while(true){
				ws = new WorkStealing(this);
				ws.start();
				
				//try {
				//	ws.join();
				//} catch (InterruptedException e) {
				//	e.printStackTrace();
				//}
			//}
		}
	}

	/* executing a task. A task's specification has several fields:
	 * taskId, users, directory, command and arguments. They are
	 * delimited with space. After a task is done, move it to the
	 * complete queue.
	 * */
	public void execTask(TaskMsg tm) {
		String taskDetail;
		synchronized(this){
			taskDetail = zc.lookup(tm.getTaskId());
		}
		Value value = Tools.strToValue(taskDetail);

		long startTime = System.currentTimeMillis();

		String data = new String("");

		if(Declarations.ZHT_STORAGE){
		String dataPiece;
		for (int i = 0; i < value.getParentsCount(); i++)
		{
			dataPiece = zc.lookup(value.getDataNameList(i));
			data += dataPiece;
		}
		}else{
		for (int i = 0; i < value.getParentsCount(); i++) {
			if (value.getDataSize(i) > 0) {
				if (value.getParents(i).equals(getId())) {
					synchronized(this){
					//data += "what ever!";
					data += localData.get(value.getDataNameList(i));
					//System.out.println(tm.taskid() << " find the data");
					}
				} else {
					Boolean dataReq = true;
					if (cache) {
						synchronized(this){
						if (localData.get(value.getDataNameList(i)) != localData.lastEntry().getValue()) {
							data += localData.get(value.getDataNameList(i));
							dataReq = false;
						} else {
							dataReq = true;
						}
						}
					}
					if (dataReq) {
						String dataPiece = new String();;
						MatrixMsg.Builder mm = MatrixMsg.newBuilder();
						mm.setMsgType("scheduler require data");
						//System.out.println("The require data is:" << value.datanamelist(i));
						mm.setExtraInfo(value.getDataNameList(i));
						String mmStr = Tools.mmToStr(mm.build());
						//mmStr = mm.SerializeAsString();
						//System.out.println(tm.taskid() << "\trequires " << i << "\tdata!");

						synchronized(this){
						ServerSocket sockfd = null;
						try {
							sockfd = new ServerSocket(MatrixTcpProxy.sendFirst(value.getParents(i),(int)config.schedulerPortNo, mmStr));
						} catch (IOException e) {
							e.printStackTrace();
						}
						//sockMutex.unlock();

						//System.out.println(tm.taskid() << "\tit takes " << diff.tv_sec << "s, and " << diff.tv_nsec
						//		<< "ns to send the " << i << "\tdata to scheduler " << value.parents(i));

						//recv_bf(sockfd, dataPiece);
						ReturnValue rv = MatrixTcpProxy.recvBig(sockfd, dataPiece);
						dataPiece = rv.result;
						try {
							sockfd.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						}
						//System.out.println(tm.taskid() << "\tit takes " << diff.tv_sec << "s, and " << diff.tv_nsec
						//		<< "ns to receive the " << i << "\tdata from scheduler " << value.parents(i));
						MatrixMsg mmData = Tools.strToMm(dataPiece);
						//System.out.println("The data piece is:" << dataPiece << ", task id is:" << tm.taskid() << ", before pasre!");
						//mmData.parseFromString(dataPiece);
						//System.out.println("After parse, extra info is:" << mmData.extrainfo());
						data += mmData.getExtraInfo();
						if (cache) {
							synchronized(this){
							localData.put(value.getDataNameList(i),
											mmData.getExtraInfo());
							}
						}
					}
				}
			}
		}
		}

		
		String execmd = tm.getCmd();
		String result = Tools.exec(execmd);
		String key = getId() + tm.getTaskId();

		if(Declarations.ZHT_STORAGE){
			synchronized(this){
				zc.insert(key, result);
			}
		}else{
			synchronized(this){
				localData.put(key,result);
				//System.out.println("key is:" + key + ", and value is:" + result);
			}
		}

		long finTime = System.currentTimeMillis();
		synchronized(this){
			taskTimeEntry.add(
				tm.getTaskId() + "\tStartTime\t" + startTime);
			taskTimeEntry.add(
				tm.getTaskId() + "\tFinTime\t" + finTime);
		}
		
		synchronized(this){
			//completeQueue.push_back(CmpQueueItem(tm.taskid(), key, result.length()));
			completeQueue.add(new CmpQueueItem(tm.getTaskId(), key, value.getOutputSize()));
		}
		
		synchronized(this){
			numTaskFin++;
			//System.out.println(tm.taskid() + "\tNumber of task fin is:" + numTaskFin);
		}

		synchronized(this){
			increZHTMsgCount(1);
		}
	}

	/* forking execute task threads. The number of executing threads is
	 * given by the configuration file, and it is usually equal to the
	 * number of cores a machine has.
	 * */
	public void forkExecTaskThread() {
		
		ExecutorService executor;

		//while(true){
			executor = Executors.newFixedThreadPool(config.numCorePerExecutor);
			for(int i = 0; i < 4; i++)
				executor.execute(new ExecutingTask(this));
			
			while (!executor.isTerminated()) { 
				try { Thread.sleep(10); } catch (InterruptedException e) { } 
			}
		//}
	}

	public int taskReadyProcess(Value valuePkg, TaskMsg tm) {
		/* flag = 0, keep it in the work stealing queue
		 * flag = 1, keep it in the local queue
		 * flag = 2, push it to other scheduler's local queue
		 * */
		int flag = 2;
		
		TaskMsg.Builder tb = tm.toBuilder();

	if(Declarations.ZHT_STORAGE){
		tb.setDataLength(valuePkg.getAllDataSize());
		flag = 0;
	}else{
		if (valuePkg.getAllDataSize() <= config.dataSizeThreshold) {
			tb.setDataLength(valuePkg.getAllDataSize());
			flag = 0;
		} else {
			long maxDataSize = -1000000;
			String maxDataScheduler = new String(), key = new String();
			for (int i = 0; i < valuePkg.getDataSizeCount(); i++) {
				if (valuePkg.getDataSize(i) > maxDataSize) {
					maxDataSize = valuePkg.getDataSize(i);
					maxDataScheduler = valuePkg.getParents(i);
					key = valuePkg.getDataNameList(i);
				}
			}
			tb.setDataLength(maxDataSize);
			if (maxDataScheduler.equals(getId())) {
				flag = 1;
			} else {
				Boolean taskPush = true;
				if (cache) {
					synchronized(this){
					if (localData.get(key) != localData.lastEntry().getValue()) {
						flag = 1; 
						taskPush = false;
					} else {
						taskPush = true;
					}
					}
				}
				if (taskPush) {
					MatrixMsg.Builder mm = MatrixMsg.newBuilder();
					mm.setMsgType("scheduler push task");
					mm.setCount(1);
					mm.addTasks(Tools.taskMsgToStr(tb.build()));
					//String mmStr = mm.SerializeAsString();
					String mmStr = Tools.mmToStr(mm.build());
					synchronized(this){
					ServerSocket sockfd = null;
					try {
						sockfd = new ServerSocket(MatrixTcpProxy.sendFirst(maxDataScheduler,
								config.schedulerPortNo, mmStr));
					} catch (Exception e) {
						e.printStackTrace();
					}
					//sockMutex.unlock();
					String ack = new String();
					MatrixTcpProxy.recvBf(sockfd, ack);
					try {
						sockfd.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					}
					flag = 2;
				}
			}
		}
	}

		return flag;
	}
	/* check to see whether a task is ready to run or not. A task is
	 * ready only if all of its parants are done (the indegree counter
	 * equals to 0).
	 * */
	public Boolean checkReadyTask(TaskMsg tm) {
		String taskDetail;
		Boolean ready = false;
		synchronized(this){
			taskDetail = zc.lookup(tm.getTaskId());
		}
		Value value = Tools.strToValue(taskDetail);
		//System.out.println("task indegree:" << tm.taskid() << "\t" << value.indegree());

		if (value.getInDegree() == 0) {
			ready = true;
			int flag = taskReadyProcess(value, tm);
			if (flag != 2) {
				synchronized(this){
				taskTimeEntry.add(
						tm.getTaskId() + "\tReadyQueuedTime\t"
								+ System.currentTimeMillis());
				}

			}
			if (flag == 0) {
				synchronized(this){
					wsQueue.add(new TaskMsgQueueItem(tm));
				}
			} else if (flag == 1) {
				synchronized(this){
					localQueue.add(new TaskMsgQueueItem(tm));
				}
			}
		}

		return ready;
	}

	

	/* fork check ready task thread */
	public void forkCrtThread() {
		
		CheckingReadyTask crt;
		
		//while(true){
			crt = new CheckingReadyTask(this);
			crt.start();
			
			/*try {
				crt.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		//}
	}

	/* decrease the indegree of a task by one, because one of
	 * its parents has been done.
	 * */
	public long notifyChildren(CmpQueueItem cqItem) {
		String taskDetail = new String();
		long increment = 0;
		//sockMutex.lock();
		//System.out.println("I got the lock, and I am notifying children!");
		//zc.lookup(cqItem.taskId, taskDetail);
		//System.out.println("OK, the task id is:" << cqItem.taskId << ", and task detail is:" << taskDetail);
		//sockMutex.unlock();
		if (taskDetail.isEmpty()) {
			//System.out.println("I am notifying a children, that is insane:" + cqItem.taskId);
		}
		Value value = Tools.strToValue(taskDetail);

		increment++;
		String childTaskId = new String(), childTaskDetail = new String(), childTaskDetailAttempt = new String(), queryValue = new String();
		Value childVal;

		//System.out.println("task finished, notify children:" << cqItem.taskId << "\t" << taskDetail << "\tChildren size is:" << value.children_size());
		for (int i = 0; i < value.getChildrenCount(); i++) {
			childTaskId = value.getChildren(i);
			synchronized(this){
				childTaskDetail = zc.lookup(childTaskId);
				//System.out.println("The child task id is:" << childTaskId << "\t" << childTaskDetail);
				//System.out.println("The size is:" << childTaskDetail.length());
			}
			increment++;
			if (taskDetail.isEmpty()) {
				System.out.println("I am notifying a children, that is insane:"
						+ cqItem.taskId);
			}
			childVal = Tools.strToValue(childTaskDetail);
			Value.Builder vb = childVal.toBuilder();
			vb.setInDegree(childVal.getInDegree() - 1);
			vb.addParents(getId());
			vb.addDataNameList(cqItem.key);
			vb.addDataSize(cqItem.dataSize);
			vb.setAllDataSize(vb.getAllDataSize() + cqItem.dataSize);
			childTaskDetailAttempt = Tools.valueToStr(vb.build());

			//System.out.println(cqItem.taskId << "\t" << childTaskId << "\t" << childTaskDetail << "\t" << childTaskDetailAttempt);
			increment++;
			synchronized(this){
				while (zc.compare_swap_int(childTaskId, childTaskDetail,
						childTaskDetailAttempt) != 0) {
					queryValue = zc.compare_swap_string(childTaskId, childTaskDetail,childTaskDetailAttempt);
					if (queryValue.isEmpty()) {
						childTaskDetail = zc.lookup(childTaskId);
						increment++;
					} else {
						//System.out.println("The queryValue is:" << queryValue);
						childTaskDetail = queryValue;
					}
					childVal = Tools.strToValue(childTaskDetail);
					Value.Builder vb2 = childVal.toBuilder();
					vb2.setInDegree(childVal.getInDegree() - 1);
					vb2.addParents(getId());
					vb2.addDataNameList(cqItem.key);
					vb2.addDataSize(cqItem.dataSize);
					vb2.setAllDataSize(childVal.getAllDataSize() + cqItem.dataSize);
					childTaskDetailAttempt = Tools.valueToStr(vb2.build());
					increment++;
				}
			}
		}

		return increment;
	}


	/* fork check complete queue tasks thread */
	public void forkCctThread() {
		CheckingCompleteTask cct;
		
		//while(true){
			cct = new CheckingCompleteTask(this);
			cct.start();
			
			/*try {
				cct.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		//}
	}
	

	/* fork recording status thread */
	public void forkRecordStatThread() {
		RecordingStat rs;
		
		//while(true){
			rs = new RecordingStat(this);
			rs.start();
			
			/*try {
				rs.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		//}
	}

	public void forkRecordTaskThread() {
		RecordingTaskTime rtt;
		
		//while(true){
			rtt = new RecordingTaskTime(this);
			rtt.start();
			
			/*try {
				rtt.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		//}
	}

	public void forkLocalQueueMonitorThread() {
		LocalQueueMonitor lqm;
		
		//while(true){
			lqm = new LocalQueueMonitor(this);
			lqm.start();
			
			/*try {
				lqm.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		//}
	}

}
