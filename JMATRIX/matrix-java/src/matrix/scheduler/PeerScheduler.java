package matrix.scheduler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Deque;
import java.util.NavigableMap;
import java.util.PriorityQueue;

import matrix.OverallPeer;
import matrix.util.CmpQueueItem;
import matrix.util.TaskMsgQueueItem;
import matrix.protocol.Metamatrix.MatrixMsg;
import matrix.protocol.Metatask.TaskMsg;
import matrix.protocol.Metazht.Value;

public abstract class PeerScheduler extends OverallPeer{
	
	public PeerScheduler(String configFile) throws IOException {
		super(configFile);
	}
	
	private String id;
	private int index;

	int numIdleCore;	// number of idle cores
	long prevNumTaskFin;	// number of tasks done last time
	long numTaskFin;	// number of tasks done up to now
	long numTaskSteal;	// number of tasks stolen from other schedulers
	long numTaskStolen;	// number of tasks being stolen by other schedulers
	long numWS;	// number of work stealing operations
	long numWSFail;	// number of failed work stealing operations

	Boolean []chooseBitMap;	// bitmap of neighbors chosen
	int numNeigh;	// number of neighbors
	int []neighIdx;	// the indeces of all chosen neighbors
	int maxLoadedIdx;	// the neighbor index with the maximum load
	long maxLoad;	// the maximum load of all the neighbors
	long pollInterval;	// the work stealing polling interval
	Boolean startWS;


	PriorityQueue<TaskMsgQueueItem> localQueue;

	PriorityQueue<TaskMsgQueueItem> wsQueue;

	Deque<TaskMsg> waitQueue;	// waiting queue
	//deque<string> readyQueue;	// ready queue
	Deque<CmpQueueItem> completeQueue;	// complete queue

	NavigableMap<String, String> localData;
	Boolean cache;

	public BufferedWriter schedulerLogOS;	// scheduler log output stream

	public BufferedWriter taskLogOS;
	ArrayList<String> taskTimeEntry;

	long startTime, stopTime;

	public abstract void regist();	// regist to ZHT server

	public abstract void loadData();

	public abstract void getTaskFromFile();
	/* receive tasks from another scheduler as a
	 * consequence of successful work stealing
	 * */
	public abstract Boolean recvTaskFromScheduler(ServerSocket recvSock);

	public abstract void recvPushingTask(MatrixMsg matrixMsg, Socket sock);

	/* receive tasks submitted by client */
	public abstract void recvTaskFromClient(String something, ServerSocket recvSock);

	/* pack and send tasks to another thief scheduler */
	//TODO: couldn't find implementation for that
	//public abstract void packSendTask(int num, int another, Socket socket, Boolean bool, ArrayDeque<TaskMsg> dequeTaskMsg);

	/* send tasks to another thief scheduler */
	public abstract void sendTask(Socket sock);

	/* processing requests received by the epoll server */
	public abstract int procReq(Socket sock, String buf);

	public abstract void forkEsThread();	// fork epoll server thread

	public abstract void resetChoosebm();	// reset the bitmap of neighbors chosen

	public abstract void chooseNeigh();	// choose candidate neighbors to steal tasks

	/* find the neighbor with the maximum load */
	public abstract void findMostLoadedNeigh();

	/* try to steal tasks from the most-loaded neighbor */
	public abstract Boolean stealTask();

	//void* workstealing(void*);	// work stealing thread function

	public abstract void forkWsThread();	// fork work stealing thread

	public abstract int taskReadyProcess(Value Value, TaskMsg taskMsg);
	/* check if a given task is ready to run, and put it in the right queue */
	public abstract Boolean checkReadyTask(TaskMsg taskMsg);

	public abstract void forkCrtThread();	// fork check ready task thread

	public abstract void execTask(TaskMsg taskMsg);	// execute a task

	public abstract void forkExecTaskThread();	// fork execute task threads

	/* decrease the number of waiting parents for a given task */
	public abstract long notifyChildren(CmpQueueItem cmpQueueItem);

	/* fork check compute task thread */
	public abstract void forkCctThread();

	/* fork recording status thread */
	public abstract void forkRecordStatThread();

	public abstract void forkRecordTaskThread();

	public abstract void forkLocalQueueMonitorThread();
	
	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setIndex(Integer index){
		this.index = index;
	}

	public int getIndex(){
		return index;
	}
}
