package matrix.scheduler;

import matrix.protocol.Metatask.TaskMsg;

/* executing task thread function, under the conditin that the
 * scheduler is still processing tasks, as long as there are
 * tasks in the ready queue, execute the task one by one
 * */
public class ExecutingTask extends Thread{
	
	MatrixScheduler ms;
	TaskMsg tm;
	
	public ExecutingTask(MatrixScheduler ms){
		this.ms = ms;
	}
	
	public void run(){
		while (ms.running) {
			while (ms.localQueue.size() > 0 || ms.wsQueue.size() > 0) {
				if (ms.localQueue.size() > 0) {
					synchronized(this){
						if (ms.localQueue.size() > 0) {
							tm = ms.localQueue.poll().getTaskMsg();
						} else {
							continue;
						}
					}
				} else if (ms.wsQueue.size() > 0) {
					synchronized(this){
						if (ms.wsQueue.size() > 0) {
							//cout << "The ready queue length is:" << ms.wsQueue.size() << endl;
							tm = ms.wsQueue.poll().getTaskMsg();
						} else {
							continue;
						}
					}
				} else {
					continue;
				}

				synchronized(this){
					ms.numIdleCore--;
				}

				//cout << "The task to execute is:" << tm.taskid() << endl;
				ms.execTask(tm);

				synchronized(this){
					ms.numIdleCore++;
				}
			}
		}

	}
}
