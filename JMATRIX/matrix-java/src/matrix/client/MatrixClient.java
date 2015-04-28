package matrix.client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import matrix.util.AdjList;
import matrix.util.InDegree;
import matrix.protocol.Metatask.TaskMsg;
import matrix.util.Tools; 
import matrix.protocol.Metazht.Value;

public class MatrixClient extends PeerClient{

	
	public MatrixClient(String configFile) throws IOException{
		super(configFile);
		//TODO: check if this tasks is suppose to be initialized
		tasks = new ArrayList<TaskMsg>();
		initMatrixClient();
	}
	
	public void initMatrixClient() throws IOException{
		
		startTime = System.currentTimeMillis();
		
		taskList = Tools.readWorkloadFromFile(config.workloadFile, Charset.defaultCharset());
		
		StringBuffer base = new StringBuffer("");
		base.append(schedulerList.size());
		base.append(config.numTaskPerClient);
		String suffix = base + "." + Integer.toString(getIndex());
		
		if (config.clientLog == 1 && getIndex() == 0) {
			String clientLogFile = "./client." + suffix;
			clientLogOS = new BufferedWriter(new FileWriter(clientLogFile, true));
		}

		if (config.systemLog == 1  && getIndex() == 0) {
			String systemLogFile = "./system." + suffix;
			systemLogOS = new BufferedWriter(new FileWriter(systemLogFile, true));
		}
		
		stopTime = System.currentTimeMillis();
		
		long diff = stopTime - startTime;
		
		System.out.println("I am a Matrix Client, it takes me " + diff + "ms for initialization!");
		
		try{
			clientLogOS.newLine();
			clientLogOS.write("I am a Matrix Client, it takes me " + diff + "ms for initialization!");
		} catch(IOException e) { }
	}


	@Override
	public void insertTaskInfoToZHT(AdjList dagAdjList, InDegree dagInDegree) {
		
		startTime = System.currentTimeMillis();
		Iterator<Entry<Long, ArrayList<Long>>> it = dagAdjList.adjList.entrySet().iterator();
		
		String taskId, child;
		
		ArrayList<Long> existList;
		
		while(it.hasNext()){
			Map.Entry<Long, ArrayList<Long>> pair = (Map.Entry<Long, ArrayList<Long>>)it.next();
			
			taskId = Integer.toString(getIndex()) + (Long)pair.getKey();
			
			existList = (ArrayList<Long>)pair.getValue();
			
			Long inDegree = dagInDegree.get((Long)pair.getKey());
			
			Value.Builder value = Value.newBuilder();
			value.setId(taskId);
			value.setInDegree(inDegree);
			
			for(Long l : existList){
				child = Integer.toString(getIndex()) + l;
				value.addChildren(child);
				
			}
			
			String seriValue;
			seriValue = Tools.valueToStr(value.build());
			zc.insert(taskId, seriValue);
			
		}
			
		increZHTMsgCount(config.numTaskPerClient);
			
		stopTime = System.currentTimeMillis();
			
		long diff = stopTime - startTime;
			
		System.out.println("I am done, the time taken is: " + diff + "ms!");
		System.out.println("------------------------------------------------------------");
			
		try{
			clientLogOS.newLine();
			clientLogOS.write("I am done, the time taken is: " + diff + "ms!");
			clientLogOS.write("------------------------------------------------------------");
		} catch(IOException e) { }
		
	}
	
	@Override
	public void initTask(){
		
		for(int i = 0; i < config.numTaskPerClient; i++){
			String taskId = Integer.toString(getIndex()) + i;
			
			ArrayList<String> taskItemStr = Tools.tokenizer(taskId + " " + taskList.get(i), " ");
			
			if(taskItemStr.isEmpty())
				continue;
			
			//TODO: it's going to work?
			TaskMsg.Builder tm = TaskMsg.newBuilder();
			tm.setTaskId(taskItemStr.get(0));
			tm.setUser(taskItemStr.get(1));
			tm.setDir(taskItemStr.get(2));
			tm.setCmd(taskItemStr.get(3));
			tm.setDataLength(0);
			tasks.add(tm.build());
		}
	}
	
	@Override
	public void submitTask(){
		/* current time to be set as the submission
		 * time of all the tasks. This might be not
		 * accurate with tasks sent batch by batch
		 * */
		long increment = 0;
		
		System.out.println("------------------------------------------------------------");
		System.out.println("Now, I am going to submit tasks to the schedulers");
			
		try{
			clientLogOS.newLine();
			clientLogOS.write("------------------------------------------------------------");
			clientLogOS.write("Now, I am going to submit tasks to the schedulers");
		} catch(IOException e) { }
		
		startTime = System.currentTimeMillis();
		
		int size = -1;
		
		for(int i = 0; i < config.numTaskPerClient; i++){
			String taskId = tasks.get(i).getTaskId();
			String taskDetail = zc.lookup(taskId);
			Value value = Tools.strToValue(taskDetail);
			Value.Builder vb = value.toBuilder();
			vb.setSubmitTime(System.currentTimeMillis());
			
			taskDetail = Tools.valueToStr(vb.build());
			zc.insert(taskId, taskDetail);
			
			increment += 2;
		}
		
		increZHTMsgCount(increment);
		
		if(config.submitMode.equals("bestcase")){
			submitTaskBc();
		}else if(config.submitMode.equals("worstcase")){
			int scheIdx = new Random().nextInt(schedulerList.size());
			submitTaskWc(tasks, scheIdx);
		}
		
		stopTime = System.currentTimeMillis();
		long diff = stopTime - startTime;

		
		System.out.println("It took " + diff + "ms");
		System.out.println("------------------------------------------------------------");

		try {
			clientLogOS.newLine();
			clientLogOS.write("It took " + diff + "ms");
			clientLogOS.write("------------------------------------------------------------");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* submit tasks with the best case scenario, in which,
	 * all the tasks are splited evenly to all the
	 * schedulers in a interleaved way
	 * */

	public void submitTaskBc() {
		int toScheIdx = -1, numSche = schedulerList.size();

		ArrayList<ArrayList<TaskMsg>> tasksList = new ArrayList<ArrayList<TaskMsg>>();
		for (int i = 0; i < numSche; i++) {
			tasksList.add(new ArrayList<TaskMsg>());
		}

		for (int i = 0; i < config.numTaskPerClient; i++) {
			toScheIdx = i % numSche;
			tasksList.get(toScheIdx).add(tasks.get(i));
		}

		/* as long as all the tasks are distributed evenly,
		 * the submission to individual scheduler is like
		 * the worst case
		 * */
		for (int i = 0; i < numSche; i++) {
			submitTaskWc(tasksList.get(i), i);
		}
	}

	/* submit tasks with the worst case scenario, in which,
	 * all the tasks (listed in "taskVec") are submitted to
	 * one scheduler (index is "toScheIdx")
	 * */
	public void submitTaskWc(ArrayList<TaskMsg> tmVec, int toScheIdx) {
		Socket sockfd;
		try {
			sockfd = new Socket(schedulerList.get(toScheIdx), (int) config.schedulerPortNo);
			if (sockfd.isClosed())
				return;
			sendBatchTasks(tmVec, sockfd, "client");
			sockfd.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	public void doMonitoring() {
		while(true){
			if (getIndex() != 0)
				return;
		
			ExecutorService executor = Executors.newFixedThreadPool(4);
			for(int i = 0; i < 4; i++)
				executor.execute(new Monitoring(this));
			
			while (!executor.isTerminated()) { 
				try { Thread.sleep(10); } catch (InterruptedException e) { } 
			}
		}
	}

	
	
}
