package matrix.scheduler;

public class LocalQueueMonitor extends Thread{
	MatrixScheduler ms;
	long diff;
	double time, aveThroughput, estTime;
	long maxSize;
	
	public LocalQueueMonitor(MatrixScheduler ms){
		this.ms = ms;
		time = 0.0;
		aveThroughput = 0.0;
		estTime = 0.0;
		maxSize = 0;
	}
	
	public void run(){
		while (ms->running) {
			clock_gettime(0, &ms->end);
			diff = time_diff(ms->start, ms->end);
			time = (double) diff.tv_sec + (double) diff.tv_nsec / 1E9;
			aveThroughput = (double) (ms->numTaskFin) / time;
			maxSize = (long) (aveThroughput * ms->config->estTimeThreadshold);
			vector<TaskMsg> vecRemain;
			vector<TaskMsg> vecMigrated;
			if (maxSize == 0) {
				usleep(ms->config->sleepLength);
				continue;
			}
			ms->lqMutex.lock();
			if (ms->localQueue.size() > maxSize) {
				int numTaskToMove = ms->localQueue.size() - maxSize;
				for (int i = 0; i < maxSize; i++) {
					vecRemain.push_back(ms->localQueue.top());
					ms->localQueue.pop();
				}

				for (int i = 0; i < numTaskToMove; i++) {
					vecMigrated.push_back(ms->localQueue.top());
					ms->localQueue.pop();
				}

				for (int i = 0; i < maxSize; i++) {
					ms->localQueue.push(vecRemain.at(i));
				}
				ms->lqMutex.unlock();

				ms->wsqMutex.lock();
				for (int i = 0; i < numTaskToMove; i++) {
					ms->wsQueue.push(vecMigrated.at(i));
				}
				ms->wsqMutex.unlock();
			} else {
				ms->lqMutex.unlock();
			}
			usleep(ms->config->sleepLength);
		}
	}
}
