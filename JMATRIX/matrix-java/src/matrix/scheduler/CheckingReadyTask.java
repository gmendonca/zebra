package matrix.scheduler;

import matrix.protocol.Metatask.TaskMsg;

/* checking ready task thread function, under the condition
 * that the scheduler is still processing tasks, if the
 * waiting queue is not empty, check all the tasks in the
 * waiting queue to see it they are ready to run. Move the
 * tasks that are ready to run to the ready queue.
 * */
public class CheckingReadyTask extends Thread{
	MatrixScheduler ms;
	TaskMsg tm;
	long increment;
	
	public CheckingReadyTask(MatrixScheduler ms){
		this.ms = ms;
		increment = 0;
		
	}
	
	public void run(){
		while (ms.running) {
			while (ms.waitQueue.size() > 0) {
				System.out.println("number of task waiting is:" + ms.waitQueue.size());
				tm = ms.waitQueue.poll();
				System.out.println("next one to process is:" + tm.getTaskId());

				Boolean ready = ms.checkReadyTask(tm);
				increment++;
				if (!ready) {
					ms.waitQueue.add(tm);
					System.out.println("Ok, the task is still not ready! " + tm.getTaskId());
				}
			}
		}

		synchronized(this){
			ms.increZHTMsgCount(increment);
		}

	}

}
