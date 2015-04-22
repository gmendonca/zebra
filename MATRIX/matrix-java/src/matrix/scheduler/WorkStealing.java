package matrix.scheduler;

/* work stealing threading function, under the condition that the scheduler
 * is still processing tasks, as long as the ready queue is empty and the
 * poll interval has reached the upper bound, the scheduler would do work
 * stealing.
 * */
public class WorkStealing {
	MatrixScheduler ms;
	long incre;
	
	public WorkStealing(MatrixScheduler ms){
		this.ms = ms;
		incre = 0;
	}
	
	public void run(){
		

		while (ms->running) {
			while (ms->localQueue.size() + ms->wsQueue.size() == 0
					&& ms->pollInterval < ms->config->wsPollIntervalUb) {
				ms->choose_neigh();
				ms->find_most_loaded_neigh();
				bool success = ms->steal_task();
				ms->numWS++;
				ms->maxLoadedIdx = -1;
				ms->maxLoad = -1000000;

				/* if successfully steals some tasks, then the poll
				 * interval is set back to the initial value, otherwise
				 * sleep the poll interval length, and double the poll
				 * interval, and tries to do work stealing again
				 * */
				if (success) {
					ms->pollInterval = ms->config->wsPollIntervalStart;
				} else {
					ms->numWSFail++;
					usleep(ms->pollInterval);
					ms->pollInterval *= 2;
				}
			}

			if (ms->pollInterval >= ms->config->wsPollIntervalUb) {
				break;
			}

			ms->pollInterval = ms->config->wsPollIntervalStart;
			usleep(ms->pollInterval);
		}

		ms->ZHTMsgCountMutex.lock();
		ms->incre_ZHT_msg_count(incre);
		ms->ZHTMsgCountMutex.unlock();

		pthread_exit(NULL);
		return NULL;
	}
}
