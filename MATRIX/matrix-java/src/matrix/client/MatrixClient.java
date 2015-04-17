package matrix.client;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

import matrix.util.Config;
import matrix.util.Peer;
import matrix.util.TaskMsg;
import matrix.util.Tools;

public class MatrixClient implements Peer{
	
	private List<String> taskList;
	private List<TaskMsg> tasks;
	
	private long startTime;
	private long stopTime;
	
	public OutputStream clientLogOS;
	public OutputStream systemLogOS;
	
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
			clientLogOS.open(clientLogFile.c_str());
		}

		if (Config.SystemLog.equals(1) && getIndex() == 0) {
			String systemLogFil = "./system." + suffix;
			systemLogOS.open(systemLogFile.c_str());
		}
		
		stopTime = System.currentTimeMillis();
		
		long diff = stopTime - startTime;
		
		System.out.println("I am a Matrix Client, it takes me " + diff + "ms for initialization!");
		
		if (clientLogOS.isOpen()) {
			clientLogOS = "I am a Matrix Client, it takes me " + diff + "ms for initialization!";
		}
	}

	@Override
	public void insertTaskInfoToZHT(adjList dagAdjList, inDegree dagInDegree) {
		startTime = System.currentTimeMillis();
		
		for()
		
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
