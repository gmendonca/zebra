package matrix.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import matrix.OverallPeer;
import matrix.util.AdjList;
import matrix.util.InDegree;
import matrix.protocol.Metatask.TaskMsg;

public abstract class PeerClient extends OverallPeer{
	

	public PeerClient(String configFile) throws IOException {
		super(configFile);
	}

	public ArrayList<String> taskList;
	public ArrayList<TaskMsg> tasks;
	
	public long startTime;
	public long stopTime;
	
	public BufferedWriter clientLogOS;
	public BufferedWriter systemLogOS;
	
	private String id;
	private int index;
	
	public abstract void insertTaskInfoToZHT(AdjList dagAdjList, InDegree dagInDegree);
	
	public abstract void initTask();
	
	public abstract void submitTask();
	
	public abstract void submitTaskBc();
	
	public abstract void submitTaskWc(ArrayList<TaskMsg> taskMsg, int randomScheduler);
	
	public abstract void doMonitoring();

	
	public Boolean initZhtClient(String something, String something2){
		return false;
	}

	public void setId(String id){
		
	}

	public String getId(){
		return id;
	}

	public void setIndex(Integer index){
		
	}

	public int getIndex(){
		return index;
	}

	public void insertWrap(String key, String value){
		
	}
	public void insertWrap(char key, char value){
		
	}

	public void lookupWrap(String key, String result){
		
	}
	public void lookupWrap(char key, char result){
		
	}

	public void recvBatchTasks(){
		
	}
	
	public void recvBatchTasks(ArrayList<TaskMsg> taskMsg, int batchNum) {
		
	}

}
