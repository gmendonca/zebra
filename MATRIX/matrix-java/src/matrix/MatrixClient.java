package matrix;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

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
		base.append(config.numTaskPerClient.ToString());
		string suffix = base + "." + num_to_str<int>(get_index());
		
		if (config->clientLog == 1 && get_index() == 0) {
			string clientLogFile("./client." + suffix);
			clientLogOS.open(clientLogFile.c_str());
		}

		if (config->systemLog == 1 && get_index() == 0) {
			string systemLogFile("./system." + suffix);
			systemLogOS.open(systemLogFile.c_str());
		}
		
		stopTime = System.currentTimeMillis();
		
		long diff = stopTime - startTime;
		
		System.out.println("I am a Matrix Client, it takes me " + diff.tv_sec + "s, and " + diff.tv_nsec + " ns for initialization!");
		
		if (clientLogOS.isOpen()) {
			clientLogOS = "I am a Matrix Client, it takes me " + diff.tv_sec
					+ "s, and " + diff.tv_nsec + " ns for initialization!";
		}
	}

	@Override
	public void insertTaskInfoToZHT(adjList dagAdjList, inDegree dagInDegree) {
		// TODO Auto-generated method stub
		
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
