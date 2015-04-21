package matrix.scheduler;

import java.net.Socket;
import java.util.ArrayDeque;

import matrix.OverallPeer;
import matrix.Peer;
import matrix.util.CmpQueueItem;
import matrix.util.MatrixMsg;
import matrix.util.TaskMsg;

public abstract class PeerScheduler extends OverallPeer{
	
	void regist();	// regist to ZHT server

	void loadData();

	void getTaskFromFile();
	/* receive tasks from another scheduler as a
	 * consequence of successful work stealing
	 * */
	Boolean recvTaskFromScheduler(int something);

	void recvPushingTask(MatrixMsg matrixMsg, int num);

	/* receive tasks submitted by client */
	void recvTaskFromClient(String something, int num);

	/* pack and send tasks to another thief scheduler */
	void packSendTask(int num, int another, Socket socket, Boolean bool, ArrayDeque<TaskMsg> dequeTaskMsg);

	/* send tasks to another thief scheduler */
	void sendTask();

	/* processing requests received by the epoll server */
	int procReq(int some, char c);

	void forkEsThread();	// fork epoll server thread

	void resetChoosebm();	// reset the bitmap of neighbors chosen

	void chooseNeigh();	// choose candidate neighbors to steal tasks

	/* find the neighbor with the maximum load */
	void findMostLoadedNeigh();

	/* try to steal tasks from the most-loaded neighbor */
	Boolean stealTask();

	//void* workstealing(void*);	// work stealing thread function

	void forkWsThread();	// fork work stealing thread

	int taskReadyProcess(String Value, TaskMsg taskMsg);
	/* check if a given task is ready to run, and put it in the right queue */
	Boolean checkReadyTask(TaskMsg taskMsg);

	void forkCrtThread();	// fork check ready task thread

	void execTask(TaskMsg taskMsg);	// execute a task

	void forkExecTaskThread();	// fork execute task threads

	/* decrease the number of waiting parents for a given task */
	long notifyChildren(CmpQueueItem cmpQueueItem);

	/* fork check compute task thread */
	void forkCctThread();

	/* fork recording status thread */
	void forkRecordStatThread();

	void forkRecordTaskThread();

	void forkLocalQueueMonitorThread();
}
