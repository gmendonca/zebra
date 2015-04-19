package matrix.client;

import java.util.List;

import matrix.util.AdjList;
import matrix.util.InDegree;
import matrix.util.TaskMsg;

public interface PeerClient {
	
	public void insertTaskInfoToZHT(AdjList dagAdjList, InDegree dagInDegree);
	
	public void initTask();
	
	public void submitTask();
	
	public void submitTaskBc();
	
	public void submitTaskWc(List<TaskMsg> taskMsg, int randomScheduler);
	
	public void doMonitoring();

}
