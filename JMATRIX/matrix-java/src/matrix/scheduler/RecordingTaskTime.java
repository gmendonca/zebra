package matrix.scheduler;

import java.io.IOException;

public class RecordingTaskTime extends Thread{
	MatrixScheduler ms;
	
	public RecordingTaskTime(MatrixScheduler ms){
		this.ms = ms;
	}
	
	public void run(){
		while (ms.running) {
			if (ms.taskTimeEntry.size() > 0) {
				synchronized(this){
					for (int i = ms.taskTimeEntry.size(); i > 0; i--) {
						try {
							ms.taskLogOS.write(ms.taskTimeEntry.get(i));
							ms.taskLogOS.newLine();
						} catch (IOException e) { }
					}
				}
			}

			try{ Thread.sleep(ms.config.sleepLength); } catch(Exception e) { }
		}
	}
}
