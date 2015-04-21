package matrix.client;

import java.io.BufferedWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import matrix.OverallPeer;
import matrix.Peer;
import matrix.util.AdjList;
import matrix.util.Configuration;
import matrix.util.InDegree;
import matrix.util.TaskMsg;

public abstract class PeerClient extends OverallPeer{
	
	public List<String> taskList;
	public List<TaskMsg> tasks;
	
	public long startTime;
	public long stopTime;
	
	public BufferedWriter clientLogOS;
	public BufferedWriter systemLogOS;
	
	public abstract void insertTaskInfoToZHT(AdjList dagAdjList, InDegree dagInDegree);
	
	public abstract void initTask();
	
	public abstract void submitTask();
	
	public abstract void submitTaskBc();
	
	public abstract void submitTaskWc(ArrayList<TaskMsg> taskMsg, int randomScheduler);
	
	public abstract void doMonitoring();

	
	public Boolean initZhtClient(String something, String something2){
		return false;
	}

	public void waitAllScheduler(){
		
	}
	public void waitAllTaskRecv(){
		
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

	public void increZHTMsgCount(long count){
		
	}

	public void insertWrap(String key, String value){
		
	}
	public void insertWrap(char key, char value){
		
	}

	public void lookupWrap(String key, String result){
		
	}
	public void lookupWrap(char key, char result){
		
	}

	public void sendBatchTasks(ArrayList<TaskMsg> taskMsg, Socket socket, String peer){
		
	}
	public void recvBatchTasks(ArrayList<TaskMsg> taskMsg, int batchNum){
		
	}

	public void recvBatchTasks(){
		
	}

}
