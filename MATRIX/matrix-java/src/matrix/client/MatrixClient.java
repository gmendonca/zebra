package matrix.client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import matrix.util.AdjList;
import matrix.util.Config;
import matrix.util.Configuration;
import matrix.util.InDegree;
import matrix.util.Peer;
import matrix.util.TaskMsg;
import matrix.util.Tools;
import matrix.util.Value;

public class MatrixClient extends PeerClient{
	
	private List<String> taskList;
	private List<TaskMsg> tasks;
	
	private long startTime;
	private long stopTime;
	
	public BufferedWriter clientLogOS;
	public BufferedWriter systemLogOS;
	
	public MatrixClient(String configFile) throws IOException{
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
		Value value;
		ArrayList<Long> existList;
		
		while(it.hasNext()){
			Map.Entry<Long, ArrayList<Long>> pair = (Map.Entry<Long, ArrayList<Long>>)it.next();
			
			taskId = Integer.toString(getIndex()) + (Long)pair.getKey();
			
			existList = (ArrayList<Long>)pair.getValue();
			
			Long inDegree = dagInDegree.inDegree.get((Long)pair.getKey());
			
			value = new Value();
			value.setTaskId(taskId);
			value.setInDegree(inDegree);
			
			for(Long l : existList){
				child = Integer.toString(getIndex()) + l;
				value.addChild(child);
			}
			
			String seriValue;
			seriValue = Tools.valueToStr(value);
			zc.insert(taskId, seriValue);
			
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
		
	}
	
	@Override
	public void initTask(){
		for(int i = 0; i < config.numTaskPerClient; i++){
			String taskId = Integer.toString(getIndex()) + i;
			
			ArrayList<String> taskItemStr = Tools.tokenize(taskId + " " + taskList.get(i));
			
			TaskMsg tm;
			tm.setTaskId(taskItemStr.get(0));
			tm.setUser(taskItemStr.get(1));
			tm.setDir(taskItemStr.get(2));
			tm.setCmd(taskItemStr.get(3));
			tm.setDataLength(0);
		}
	}

	

}
