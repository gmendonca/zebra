package matrix.scheduler;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayDeque;

import matrix.OverallPeer;
import matrix.Peer;
import matrix.util.CmpQueueItem;
import matrix.util.MatrixMsg;
import matrix.util.TaskMsg;

public abstract class PeerScheduler extends OverallPeer{
	
	public PeerScheduler(String configFile) throws IOException {
		super(configFile);
	}
	
	private String id;
	private int index;

	public abstract void regist();	// regist to ZHT server

	public abstract void loadData();

	public abstract void getTaskFromFile();
	/* receive tasks from another scheduler as a
	 * consequence of successful work stealing
	 * */
	public abstract Boolean recvTaskFromScheduler(int something);

	public abstract void recvPushingTask(MatrixMsg matrixMsg, int num);

	/* receive tasks submitted by client */
	public abstract void recvTaskFromClient(String something, int num);

	/* pack and send tasks to another thief scheduler */
	public abstract void packSendTask(int num, int another, Socket socket, Boolean bool, ArrayDeque<TaskMsg> dequeTaskMsg);

	/* send tasks to another thief scheduler */
	public abstract void sendTask();

	/* processing requests received by the epoll server */
	public abstract int procReq(int some, char c);

	public abstract void forkEsThread();	// fork epoll server thread

	public abstract void resetChoosebm();	// reset the bitmap of neighbors chosen

	public abstract void chooseNeigh();	// choose candidate neighbors to steal tasks

	/* find the neighbor with the maximum load */
	public abstract void findMostLoadedNeigh();

	/* try to steal tasks from the most-loaded neighbor */
	public abstract Boolean stealTask();

	//void* workstealing(void*);	// work stealing thread function

	public abstract void forkWsThread();	// fork work stealing thread

	public abstract int taskReadyProcess(String Value, TaskMsg taskMsg);
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
}
