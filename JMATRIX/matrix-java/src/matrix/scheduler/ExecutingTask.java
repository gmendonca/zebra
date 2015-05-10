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
			//System.out.println("Executing Task: " + ms.localQueue.size() + " " + ms.wsQueue.size());
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

				System.out.println("The task to execute is:" + tm.getTaskId());
				ms.execTask(tm);

				synchronized(this){
					ms.numIdleCore++;
				}
			}
			try { Thread.sleep(10); } catch(Exception e) { }
		}

	}
}
