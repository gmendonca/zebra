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
import matrix.util.InDegree;
import matrix.util.Peer;
import matrix.util.TaskMsg;
import matrix.util.Tools;

public class MatrixClient implements ClientInterface, Peer{
	
	private List<String> taskList;
	private List<TaskMsg> tasks;
	
	private long startTime;
	private long stopTime;
	
	public static BufferedWriter clientLogOS;
	public static BufferedWriter systemLogOS;
	
	public MatrixClient(String configFile){
		
	}
	
	public void initMatrixClient() throws IOException{
		
		startTime = System.currentTimeMillis();
		
		taskList = Tools.readWorkloadFromFile("", Charset.defaultCharset());
		
		StringBuffer base = new StringBuffer("");
		base.append(schedulerList.size().ToString());
		base.append(Config.NumTaskPerClient);
		String suffix = base + "." + Integer.toString(getIndex());
		
		if (Config.ClientLog.equals(1) && getIndex() == 0) {
			String clientLogFile = "./client." + suffix;
			clientLogOS = new BufferedWriter(new FileWriter(clientLogFile, true));
		}

		if (Config.SystemLog.equals(1) && getIndex() == 0) {
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
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			
			
			Map<String,InDegree> value;
			
		}
		
		
	}

	@Override
	public void initTask() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void submitTask() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void submitTaskBc() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void submitTaskWc(List<TaskMsg> taskMsg, int randomScheduler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doMonitoring() {
		// TODO Auto-generated method stub
		
	}

}
