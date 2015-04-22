package matrix.scheduler;

public class RecordingStatTime extends Thread{
	MatrixScheduler ms;
	
	public RecordingStatTime(MatrixScheduler ms){
		
	}
	
	public void run(){
		while (ms->running) {
			if (ms->taskTimeEntry.size() > 0) {
				ms->tteMutex.lock();
				while (ms->taskTimeEntry.size() > 0) {
					ms->taskLogOS << ms->taskTimeEntry.back() << endl;
					ms->taskTimeEntry.pop_back();
				}
				ms->tteMutex.unlock();
			}

			usleep(ms->config->sleepLength);
		}

		ms->taskLogOS.flush();
		ms->taskLogOS.close();
		pthread_exit(NULL);
	}
}
