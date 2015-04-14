package matrix.util;

import java.io.OutputStream;
import java.util.List;

public interface Peer {
	
	public void insertTaskInfoToZHT();
	
	public void initTask();
	
	public void submitTask();
	
	public void submitTaskBc();
	
	public void submitTaskWc(List<TaskMsg> taskMsg, int randomScheduler);
	
	public void doMonitoring();
	
	public List<String> taskList = null;
	
	public List<TaskMsg> tasks = null;
	
	public long startTime = 0, stopTime = 0;
	
	public OutputStream clientLogOS = null;
	public OutputStream systemLogOS = null;

}
