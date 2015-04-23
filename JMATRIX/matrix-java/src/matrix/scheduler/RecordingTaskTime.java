package matrix.scheduler;

public class RecordingTaskTime extends Thread{
	MatrixScheduler ms;
	
	public RecordingTaskTime(MatrixScheduler ms){
		this.ms = ms;
	}
	
	public void run(){
		while (ms.running) {
			if (ms.taskTimeEntry.size() > 0) {
				ms.tteMutex.lock();
				while (ms.taskTimeEntry.size() > 0) {
					ms.taskLogOS << ms.taskTimeEntry.back() << endl;
					ms.taskTimeEntry.pop_back();
				}
				ms.tteMutex.unlock();
			}

			Thread.sleep(ms.config.sleepLength);
		}

		ms.taskLogOS.flush();
		ms.taskLogOS.close();
	}
}
