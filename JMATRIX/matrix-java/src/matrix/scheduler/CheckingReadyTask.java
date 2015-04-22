package matrix.scheduler;

import matrix.protocol.Metatask.TaskMsg;

/* checking ready task thread function, under the condition
 * that the scheduler is still processing tasks, if the
 * waiting queue is not empty, check all the tasks in the
 * waiting queue to see it they are ready to run. Move the
 * tasks that are ready to run to the ready queue.
 * */
public class CheckingReadyTask {
	MatrixScheduler ms;
	TaskMsg tm;
	long increment;
	
	public CheckingReadyTask(MatrixScheduler ms){
		this.ms = ms;
		increment = 0;
		
	}
	
	public void run(){
		while (ms->running) {
			while (ms->waitQueue.size() > 0) {
				//cout << "number of task waiting is:" << ms->waitQueue.size() << endl;
				tm = ms->waitQueue.front();
				ms->waitQueue.pop_front();
				//cout << "next one to process is:" << tm.taskid() << endl;

				bool ready = ms->check_a_ready_task(tm);
				increment++;
				if (!ready) {
					ms->waitQueue.push_back(tm);
					//cout << "Ok, the task is still not ready!" << tm.taskid() << endl;
				}
			}
		}

		ms->ZHTMsgCountMutex.lock();
		ms->incre_ZHT_msg_count(increment);
		ms->ZHTMsgCountMutex.unlock();

		pthread_exit(NULL);
		return NULL;
	}

}
