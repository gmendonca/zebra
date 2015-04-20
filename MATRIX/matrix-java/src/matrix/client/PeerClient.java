package matrix.client;

import java.util.List;

import matrix.util.AdjList;
import matrix.util.InDegree;
import matrix.util.Peer;
import matrix.util.TaskMsg;

public abstract class PeerClient implements Peer{
	
	private String id;
	private int index;
	
	public int getIndex(){
		return index;
	}
	
	public abstract void insertTaskInfoToZHT(AdjList dagAdjList, InDegree dagInDegree);
	
	public abstract void initTask();
	
	public abstract void submitTask();
	
	public abstract void submitTaskBc();
	
	public abstract void submitTaskWc(List<TaskMsg> taskMsg, int randomScheduler);
	
	public abstract void doMonitoring();

}
