package matrix.scheduler;

import matrix.protocol.Metatask.TaskMsg;

/* executing task thread function, under the conditin that the
 * scheduler is still processing tasks, as long as there are
 * tasks in the ready queue, execute the task one by one
 * */
public class ExecutingTask {
	
	MatrixScheduler ms;
	TaskMsg tm;
	
	public ExecutingTask(MatrixScheduler ms){
		this.ms = ms;
	}
	
	public void run(){
		while (ms->running) {
			while (ms->localQueue.size() > 0 || ms->wsQueue.size() > 0) {
				if (ms->localQueue.size() > 0) {
					ms->lqMutex.lock();
					if (ms->localQueue.size() > 0) {
						tm = ms->localQueue.top();
						ms->localQueue.pop();
						ms->lqMutex.unlock();
					} else {
						ms->lqMutex.unlock();
						continue;
					}
				} else if (ms->wsQueue.size() > 0) {
					ms->wsqMutex.lock();
					if (ms->wsQueue.size() > 0) {
						//cout << "The ready queue length is:" << ms->wsQueue.size() << endl;
						tm = ms->wsQueue.top();
						ms->wsQueue.pop();
						ms->wsqMutex.unlock();
					} else {
						ms->wsqMutex.unlock();
						continue;
					}
				} else {
					continue;
				}

				ms->numIdleCoreMutex.lock();
				ms->numIdleCore--;
				ms->numIdleCoreMutex.unlock();

				//cout << "The task to execute is:" << tm.taskid() << endl;
				ms->exec_a_task(tm);

				ms->numIdleCoreMutex.lock();
				ms->numIdleCore++;
				ms->numIdleCoreMutex.unlock();
			}
		}

		pthread_exit(NULL);
		return NULL;
	}
}
