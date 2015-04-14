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
		
		stopTime = System.currentTimeMillis();
	}

	@Override
	public void insertTaskInfoToZHT() {
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
