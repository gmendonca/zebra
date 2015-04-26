package matrix.scheduler;

import java.util.ArrayList;

import matrix.protocol.Metatask.TaskMsg;
import matrix.util.TaskMsgQueueItem;

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
		while (ms.running) {
			ms.stopTime = System.currentTimeMillis();
			diff = ms.stopTime - ms.startTime;
			time = (double) diff;
			aveThroughput = (double) (ms.numTaskFin) / time;
			maxSize = (long) (aveThroughput * ms.config.estTimeThreadshold);
			ArrayList<TaskMsg> listRemain = new ArrayList<TaskMsg>();
			ArrayList<TaskMsg> listMigrated = new ArrayList<TaskMsg>();
			if (maxSize == 0) {
				try { Thread.sleep(ms.config.sleepLength); }  catch (Exception e) { }
				continue;
			}
			synchronized(this){
			if (ms.localQueue.size() > maxSize) {
				long numTaskToMove = ms.localQueue.size() - maxSize;
				for (int i = 0; i < maxSize; i++) {
					listRemain.add(ms.localQueue.poll().getTaskMsg());
				}

				for (int i = 0; i < numTaskToMove; i++) {
					listMigrated.add(ms.localQueue.poll().getTaskMsg());
				}

				for (int i = 0; i < maxSize; i++) {
					ms.localQueue.add(new TaskMsgQueueItem(listRemain.get(i)));
				}
				

				synchronized(this){
				for (int i = 0; i < numTaskToMove; i++) {
					ms.wsQueue.add(new TaskMsgQueueItem(listMigrated.get(i)));
				}
				}
			} else {
			}
			}
			try { Thread.sleep(ms.config.sleepLength); }  catch (Exception e) { }
		}
	}
}
