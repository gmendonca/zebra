package matrix.client;

import java.util.ArrayList;
import java.util.List;

import matrix.util.AdjList;
import matrix.util.Configuration;
import matrix.util.InDegree;
import matrix.util.Peer;
import matrix.util.TaskMsg;

public abstract class PeerClient implements Peer{
	
	private String id;
	private int index;
	
	//Attributes from Interface Peer
	public ZHTClient zc;
	public Configuration config;
	public ArrayList<String> schedulerList;
	public Boolean running;
	public long numZHTMsg;
	
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
