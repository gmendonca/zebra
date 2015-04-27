package matrix.scheduler;

import matrix.protocol.Metazht.Value;
import matrix.util.Tools;

/* recording status thread function. The recording thread would periodically
 * dump the scheduler status information (number of tasks done, waiting,
 * and ready; number of idle/all cores, and number of (failed) working
 * stealing operations) to ZHT.
 * */
public class RecordingStat extends Thread{
	MatrixScheduler ms;
	long increment;
	long time;
	
	public RecordingStat(MatrixScheduler ms){
		this.ms = ms;
		increment = 0;
		
	}
	
	public void run(){

			try {
				ms.schedulerLogOS.write("Time\tNumTaskFin\tNumTaskWait\tNumTaskReady\t"+
						"NumIdleCore\tNumAllCore\tNumWorkSteal\tNumWorkStealFail");
				ms.schedulerLogOS.newLine();
			} catch (Exception e) { }
			

		while (true) {
			Value.Builder recordVal = Value.newBuilder();
			recordVal.setId(ms.getId());
			recordVal.setNumTaskFin(ms.numTaskFin);
			recordVal.setNumTaskWait(ms.waitQueue.size());
			recordVal.setNumTaskReady(ms.localQueue.size() + ms.wsQueue.size());
			recordVal.setNumCoreAvilable(ms.numIdleCore);
			recordVal.setNumAllCore(ms.config.numCorePerExecutor);
			recordVal.setNumWorkSteal(ms.numWS);
			recordVal.setNumWorkStealFail(ms.numWSFail);
			String recordValStr = Tools.valueToStr(recordVal.build());
			synchronized(this){
				ms.zc.insertZHT(ms.getId(), recordValStr);
			}

			try {
				ms.schedulerLogOS.write(System.currentTimeMillis() + "\t" + ms.numTaskFin
						+ "\t" + ms.waitQueue.size() + "\t"
						+ ms.localQueue.size() + ms.wsQueue.size() + "\t"
						+ ms.numIdleCore + "\t" + ms.config.numCorePerExecutor
						+ "\t" + ms.numWS + "\t" + ms.numWSFail);
				ms.schedulerLogOS.newLine();
			} catch (Exception e) { }
			

			/* check and modify how many tasks are done for all the schedulers. If all
			 * tasks are done, then flipping the scheduler status to off to indicate
			 * that it is not processing any task any more
			 * */
			String key = new String("num tasks done");
			String numTaskDoneStr;
			synchronized(this){
				numTaskDoneStr = ms.zc.lookUp(key);
				System.out.println("Number of task done is:" + numTaskDoneStr + "\n");
			}

			increment += 2;

			long numTaskDone = Long.parseLong(numTaskDoneStr);
			if (numTaskDone == ms.config.numAllTask) {
			
				try {
					ms.schedulerLogOS.write(System.currentTimeMillis() + "\t" + ms.numTaskFin
								+ "\t" + ms.waitQueue.size() + "\t"
								+ ms.localQueue.size() + ms.wsQueue.size() + "\t"
								+ ms.numIdleCore + "\t"
								+ ms.config.numCorePerExecutor + "\t" + ms.numWS
								+ "\t" + ms.numWSFail);
					ms.schedulerLogOS.newLine();
				} catch (Exception e) { }

				ms.running = false;
				break;
			}
			
			String numTaskDoneStrNew;
			String queryValue;
			synchronized(this){
				numTaskDone += (ms.numTaskFin - ms.prevNumTaskFin);
				numTaskDoneStrNew = Long.toString(numTaskDone);
				queryValue = new String();
				increment++;
			}
			while (ms.zc.compareSwapInt(key, numTaskDoneStr, numTaskDoneStrNew) != 0) {
				queryValue = ms.zc.compareSwapString(key, numTaskDoneStr, numTaskDoneStrNew);
				if (queryValue.isEmpty()) {
					numTaskDoneStr = ms.zc.lookUp(key);
					increment++;
				} else {
					numTaskDoneStr = queryValue;
				}
				numTaskDone = Long.parseLong(numTaskDoneStr);
				if (numTaskDone == ms.config.numAllTask) {
					break;
				}
				numTaskDone += (ms.numTaskFin - ms.prevNumTaskFin);
				numTaskDoneStrNew = Long.toString(numTaskDone);
				increment++;
			}
			//cout + "OK, Number of task done is:" + numTaskDoneStrNew + endl;
			synchronized(this){
				ms.prevNumTaskFin = ms.numTaskFin;
			}
			try { Thread.sleep(ms.config.sleepLength); } catch(Exception e) {}
		}

		synchronized(this){
			ms.increZHTMsgCount(increment);
		}

		try {
			ms.schedulerLogOS.write("The number of ZHT message is:" + ms.numZHTMsg);
			ms.schedulerLogOS.newLine();
		} catch (Exception e) { }
		

		if (ms.taskTimeEntry.size() > 0) {
			synchronized(this){
				for (int i = 0; i < ms.taskTimeEntry.size(); i++) {
					try {
						ms.taskLogOS.write(ms.taskTimeEntry.get(i));
						ms.taskLogOS.newLine();
					} catch (Exception e) { }
				}
			}
		}

	}
}
