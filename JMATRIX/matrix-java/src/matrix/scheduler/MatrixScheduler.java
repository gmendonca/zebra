package matrix.scheduler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import matrix.protocol.Metamatrix.MatrixMsg;
import matrix.protocol.Metatask.TaskMsg;
import matrix.protocol.Metazht.Value;
import matrix.util.CmpQueueItem;
import matrix.util.MatrixEpollServer;
import matrix.util.Tools;

public class MatrixScheduler extends PeerScheduler{

	public MatrixScheduler(String configFile) throws IOException {
		super(configFile);
		// TODO Auto-generated constructor stub
	}
	
	/* processing requests received by the epoll server */
	public int procReq(int sockfd, String buf) {
		String bufStr = new String(buf);
		/* this is client submitting tasks */
		String prefix = "client send tasks";
		if (bufStr.substring(0, prefix.length()) == prefix) {
			recvTaskFromClient(bufStr, sockfd);
		} else {
			MatrixMsg mm;

			mm = Tools.strToMm(bufStr);

			long increment = 0;

			/* message type is stored in pkg.virtualpath(), and contents
			 * are stored in pkg.readfullpath() */
			String msg = mm.getMsgType();
			if (msg.equals("query load")) { 	// thief querying load
				int load = wsQueue.size();
				MatrixMsg.Builder mmLoad;
				mmLoad.setMsgType("send load");
				mmLoad.setCount(load);
				String strLoad = Tools.mmToStr(mmLoad.build());
				sendBf(sockfd, strLoad);
			} else if (msg.equals("steal task")) {	// thief steals tasks
				sendTask(sockfd);
			} else if (msg.equals("scheduler push task")) {
				recvPushingTask(mm, sockfd);
			} else if (msg.equals("scheduler require data")) {
				String dataPiece;
				//ldMutex.lock();

				if (localData.get(mm.getExtraInfo()) == localData.end()) {
					dataPiece = "shit, that is wrong!";
				} else {
					dataPiece = localData.get(mm.getExtraInfo());
				}
				ldMutex.unlock();

				MatrixMsg.Builder mmDataPiece;
				mmDataPiece.setMsgType("scheduler send data");
				mmDataPiece.setExtraInfo(dataPiece);
				String dataStr = Tools.mmToStr(mmDataPiece.build());
				//mmDataPiece.SerializeAsString();
				//send_bf(sockfd, dataStr);
				sendBig(sockfd, dataStr);
			}
		}
		close(sockfd);
		return 1;
	}

	/* fork epoll server thread */
	public void forkEsThread() {
		MatrixEpollServer mes = new MatrixEpollServer(config.schedulerPortNo, this);
		
		Thread t = new Thread(new Runnable() { public void run() { 
				mes.serve();
			}});
		
		t.start();
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
		MatrixMsg.Builder mm;
		mm.setMsgType("query load");

		String strLoadQuery = Tools.mmToStr(mm.build());

		long load = -1;

		for (int i = 0; i < numNeigh; i++) {
			String result;
			sockMutex.lock();
			int sockfd = send_first(schedulerList.get(neighIdx[i]),
					config.schedulerPortNo, strLoadQuery);
			recvBf(sockfd, result);

			close(sockfd);
			sockMutex.unlock();
			if (result.isEmpty()) {
				continue;
			}
			MatrixMsg mmLoad = Tools.strToMm(result);

			load = mmLoad.getCount();
			if (maxLoad < load) {
				maxLoad = load;
				maxLoadedIdx = neighIdx[i];
			}
		}
	}

	/* receive several tasks (numTask) from another scheduler */
	public Boolean recvTaskFromScheduler(int sockfd) {
		String taskStr;
		recvMul(sockfd, taskStr);

		String taskStrLs = taskStr.substring(0, taskStr.length() - 1);

		ArrayList<String> stealList = Tools.tokenizer(taskStrLs, "##");
		if (stealList.size() == 1) {
			return false;
		}

		MatrixMsg mmNumTask = Tools.strToMm(stealList.get(0));
		long numTask = mmNumTask.getCount();

		for (int i = 1; i < stealList.size(); i++) {
			MatrixMsg mm = Tools.strToMm(stealList.get(i));

			ArrayList<TaskMsg> tmList;
			String time = "" + System.currentTimeMillis();

			for (int j = 0; j < mm.getCount(); j++) {
				tmList.add(Tools.strToTaskMsg(mm.getTasks(j)));
			}

			tteMutex.lock();
			for (int j = 0; j < mm.getCount(); j++) {
				taskTimeEntry.add(
						tmList.get(j).getTaskId() + "\tWorkStealQueuedTime\t" + time);
			}
			tteMutex.unlock();

			wsqMutex.lock();
			for (int j = 0; j < mm.getCount(); j++) {
				wsQueue.push(tmList.get(j));
			}
			wsqMutex.unlock();
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
		/* if no neighbors have ready tasks */
		if (maxLoad <= 0) {
			return false;
		}

		MatrixMsg.Builder mm;
		mm.setMsgType("steal task");
		//String strStealTask = mm.SerializeAsString();
		String strStealTask = Tools.mmToStr(mm.build());

		String numTaskPkgStr;
		//System.out.println("OK, before sending stealing task message!");
		sockMutex.lock();
		//System.out.println("OK, I am sending stealing task message!");
		int sockfd = send_first(schedulerList.get(maxLoadedIdx),
				config.schedulerPortNo, strStealTask);

		bool ret = recvTaskFromScheduler(sockfd);
		close(sockfd);
		sockMutex.unlock();
		return ret;
	}

	/* fork work stealing thread */
	public void forkWsThread() {
		if (config.workStealingOn == 1) {
			WorkStealing ws = new WorkStealing(this);
			ws.start();
		}
	}

	/* executing a task. A task's specification has several fields:
	 * taskId, users, directory, command and arguments. They are
	 * delimited with space. After a task is done, move it to the
	 * complete queue.
	 * */
	public void execTask(TaskMsg tm) {
		String taskDetail;
		sockMutex.lock();
		zc.lookup(tm.getTaskId(), taskDetail);
		sockMutex.unlock();
		Value value = Tools.strToValue(taskDetail);

		long startTime = System.currentMiliseconds();

		String data("");

	//#ifdef ZHT_STORAGE
		String dataPiece;
		for (int i = 0; i < value.parents_size(); i++)
		{
			zc.lookup(value.getDataNameList(i), dataPiece);
			data += dataPiece;
		}
	//#else
		for (int i = 0; i < value.parents_size(); i++) {
			if (value.getDataSize(i) > 0) {
				if (value.getParents(i).compare(getId()) == 0) {
					ldMutex.lock();
					//data += "what ever!";
					data += localData.get(value.getDataNameList(i));
					//System.out.println(tm.taskid() << " find the data");
					ldMutex.unlock();
				} else {
					bool dataReq = true;
					if (cache) {
						ldMutex.lock();
						if (localData.find(value.getDataNameList(i))
								!= localData.end()) {
							data += localData.get(value.getDataNameList(i));
							dataReq = false;
						} else {
							dataReq = true;
						}
						ldMutex.unlock();
					}
					if (dataReq) {
						MatrixMsg.Builder mm;
						mm.setMsgType("scheduler require data");
						//System.out.println("The require data is:" << value.datanamelist(i));
						mm.setExtraInfo(value.getDataNameList(i));
						String mmStr = Tools.mmToStr(mm.build());
						//mmStr = mm.SerializeAsString();
						//System.out.println(tm.taskid() << "\trequires " << i << "\tdata!");

						sockMutex.lock();
						int sockfd = sendFirst(value.getParents(i),
								config.schedulerPortNo, mmStr);
						//sockMutex.unlock();

						//System.out.println(tm.taskid() << "\tit takes " << diff.tv_sec << "s, and " << diff.tv_nsec
						//		<< "ns to send the " << i << "\tdata to scheduler " << value.parents(i));

						String dataPiece;
						//recv_bf(sockfd, dataPiece);
						recvBig(sockfd, dataPiece);
						close(sockfd);
						sockMutex.unlock();
						//System.out.println(tm.taskid() << "\tit takes " << diff.tv_sec << "s, and " << diff.tv_nsec
						//		<< "ns to receive the " << i << "\tdata from scheduler " << value.parents(i));
						MatrixMsg mmData = Tools.strToMm(dataPiece);
						//System.out.println("The data piece is:" << dataPiece << ", task id is:" << tm.taskid() << ", before pasre!");
						//mmData.ParseFromString(dataPiece);
						//System.out.println("After parse, extra info is:" << mmData.extrainfo());
						data += mmData.getExtraInfo();
						if (cache) {
							ldMutex.lock();
							localData.insert(
									make_pair(value.getDataNameList(i),
											mmData.getExtraInfo()));
							ldMutex.unlock();
						}
					}
				}
			}
		}
	//#endif

		
		String execmd = tm.getCmd();
		String result = Tools.exec(execmd);
		String key = getId() + tm.getTaskId();

	//#ifdef ZHT_STORAGE
		//sockMutex.lock();
		zc.insert(key, result);
		//sockMutex.unlock();
	//#else
		ldMutex.lock();
		localData.insert(make_pair(key, result));
		//System.out.println("key is:" << key << ", and value is:" << result);
		ldMutex.unlock();
	//#endif

		long finTime = System.currentMiliseconds();
		tteMutex.lock();
		taskTimeEntry.push_back(
				tm.taskid() + "\tStartTime\t" + num_to_str<long>(startTime));
		taskTimeEntry.push_back(
				tm.taskid() + "\tFinTime\t" + num_to_str<long>(finTime));
		tteMutex.unlock();

		cqMutex.lock();
		//completeQueue.push_back(CmpQueueItem(tm.taskid(), key, result.length()));
		completeQueue.push_back(CmpQueueItem(tm.taskid(), key, value.outputsize()));
		cqMutex.unlock();

		numTaskFinMutex.lock();
		numTaskFin++;
		//System.out.println(tm.taskid() << "\tNumber of task fin is:" << numTaskFin);
		numTaskFinMutex.unlock();

		ZHTMsgCountMutex.lock();
		incre_ZHT_msg_count(1);
		ZHTMsgCountMutex.unlock();
	}

	/* forking execute task threads. The number of executing threads is
	 * given by the configuration file, and it is usually equal to the
	 * number of cores a machine has.
	 * */
	public void forkExecTaskThread() {

		for (int i = 0; i < config.numCorePerExecutor; i++) {
			new ExecutingTask(this).start();
		}
	}

	public int taskReadyProcess(Value valuePkg, TaskMsg tm) {
		/* flag = 0, keep it in the work stealing queue
		 * flag = 1, keep it in the local queue
		 * flag = 2, push it to other scheduler's local queue
		 * */
		int flag = 2;
		
		TaskMsg.Builder tb = tm.toBuilder();

	//#ifdef ZHT_STORAGE
		tb.setDataLength(valuePkg.getAllDataSize());
		flag = 0;
	//#else
		if (valuePkg.getAllDataSize() <= config.dataSizeThreshold) {
			tb.setDataLength(valuePkg.getAllDataSize());
			flag = 0;
		} else {
			long maxDataSize = -1000000;
			String maxDataScheduler, key;
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
					ldMutex.lock();
					if (localData.find(key) != localData.end()) {
						flag = 1; 
						taskPush = false;
					} else {
						taskPush = true;
					}
					ldMutex.unlock();
				}
				if (taskPush) {
					MatrixMsg.Builder mm;
					mm.setMsgType("scheduler push task");
					mm.setCount(1);
					mm.addTasks(Tools.taskMsgToStr(tb.build()));
					//String mmStr = mm.SerializeAsString();
					String mmStr = Tools.mmToStr(mm.build());
					sockMutex.lock();
					int sockfd = sendFirst(maxDataScheduler,
							config.schedulerPortNo, mmStr);
					//sockMutex.unlock();
					String ack;
					recv_bf(sockfd, ack);
					close(sockfd);
					sockMutex.unlock();
					flag = 2;
				}
			}
		}
	//#endif

		return flag;
	}
	/* check to see whether a task is ready to run or not. A task is
	 * ready only if all of its parants are done (the indegree counter
	 * equals to 0).
	 * */
	public Boolean checkReadyTask(TaskMsg tm) {
		String taskDetail;
		Boolean ready = false;
		sockMutex.lock();
		zc.lookup(tm.getTaskId(), taskDetail);
		sockMutex.unlock();
		Value value = Tools.strToValue(taskDetail);
		//System.out.println("task indegree:" << tm.taskid() << "\t" << value.indegree());

		if (value.getInDegree() == 0) {
			ready = true;
			int flag = taskReadyProcess(value, tm);
			if (flag != 2) {
				tteMutex.lock();
				taskTimeEntry.add(
						tm.getTaskId() + "\tReadyQueuedTime\t"
								+ System.currentMiliseconds());
				tteMutex.unlock();

			}
			if (flag == 0) {
				wsqMutex.lock();
				wsQueue.push(tm);
				wsqMutex.unlock();
			} else if (flag == 1) {
				lqMutex.lock();
				localQueue.push(tm);
				lqMutex.unlock();
			}
		}

		return ready;
	}

	

	/* fork check ready task thread */
	public void forkCrtThread() {
		
		CheckingReadyTask crt = new CheckingReadyTask(this);
		crt.start();
	}

	/* decrease the indegree of a task by one, because one of
	 * its parents has been done.
	 * */
	public long notifyChildren(CmpQueueItem cqItem) {
		String taskDetail;
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
		String childTaskId, childTaskDetail, childTaskDetailAttempt, query_value;
		Value childVal;

		//System.out.println("task finished, notify children:" << cqItem.taskId << "\t" << taskDetail << "\tChildren size is:" << value.children_size());
		for (int i = 0; i < value.getChildrenCount(); i++) {
			childTaskId = value.getChildren(i);
			sockMutex.lock();
			zc.lookup(childTaskId, childTaskDetail);
			//System.out.println("The child task id is:" << childTaskId << "\t" << childTaskDetail);
			//System.out.println("The size is:" << childTaskDetail.length());
			sockMutex.unlock();
			increment++;
			if (taskDetail.isEmpty()) {
				System.out.println("I am notifying a children, that is insane:"
						+ cqItem.taskId);
			}
			childVal = Tools.strToValue(childTaskDetail);
			childVal.set_indegree(childVal.getInDegree() - 1);
			childVal.add_parents(getId());
			childVal.add_datanamelist(cqItem.key);
			childVal.add_datasize(cqItem.dataSize);
			childVal.set_alldatasize(childVal.alldatasize() + cqItem.dataSize);
			childTaskDetailAttempt = value_to_str(childVal);

			//System.out.println(cqItem.taskId << "\t" << childTaskId << "\t" << childTaskDetail << "\t" << childTaskDetailAttempt);
			increment++;
			sockMutex.lock();
			while (zc.compare_swap(childTaskId, childTaskDetail,
					childTaskDetailAttempt, query_value) != 0) {
				if (query_value.empty()) {
					zc.lookup(childTaskId, childTaskDetail);
					increment++;
				} else {
					//System.out.println("The query_value is:" << query_value);
					childTaskDetail = query_value;
				}
				childVal = Tools.strToValue(childTaskDetail);
				childVal.set_indegree(childVal.indegree() - 1);
				childVal.add_parents(get_id());
				childVal.add_datanamelist(cqItem.key);
				childVal.add_datasize(cqItem.dataSize);
				childVal.set_alldatasize(childVal.alldatasize() + cqItem.dataSize);
				childTaskDetailAttempt = value_to_str(childVal);
				increment++;
			}
			sockMutex.unlock();
		}

		return increment;
	}


	/* fork check complete queue tasks thread */
	public void forkCctThread() {
		CheckingCompleteTask cct = new CheckingCompleteTask(this);
		cct.start();
	}
	

	/* fork recording status thread */
	public void forkRecordStatThread() {
		RecordingStat rs = new RecordingStat(this);
		rs.start();
	}

	public void forkRecordTaskThread() {
		RecordingTaskTime rtt = new RecordingTaskTime(this);
		rtt.start();
	}

	public void forkLocalQueueMonitorThread() {
		LocalQueueMonitor lqm = new LocalQueueMonitor(this);
		lqm.start();
	}



}
