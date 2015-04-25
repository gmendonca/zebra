package matrix.scheduler;

import matrix.util.CmpQueueItem;

/* checking complete queue tasks thread function, under the condition
 * that the scheduler is still processing tasks, as long as the task
 * complete queue is not empty, for each task in the queue, decrease
 * the indegree of each child by one.
 * */
public class CheckingCompleteTask extends Thread{
	MatrixScheduler ms;
	CmpQueueItem cqItem;
	long increment;
	
	public CheckingCompleteTask(MatrixScheduler ms){
		cqItem = new CmpQueueItem();
		increment = 0;
	}
	
	public void run(){
		while (ms.running) {
			while (ms.completeQueue.size() > 0) {
				synchronized(ms.completeQueue){
					if (ms.completeQueue.size() > 0) {
						cqItem = ms.completeQueue.poll();
					} else {
						continue;
					}
				}
				increment += ms.notifyChildren(cqItem);
			}
		}

		synchronized(this){
			ms.increZHTMsgCount(increment);
		}
	}

}
