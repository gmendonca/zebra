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
		if (ms.schedulerLogOS.is_open()) {
			ms.schedulerLogOS << "Time\tNumTaskFin\tNumTaskWait\tNumTaskReady\t"
					"NumIdleCore\tNumAllCore\tNumWorkSteal\tNumWorkStealFail"
					<< endl;
		}

		while (true) {
			Value.Builder recordVal;
			recordVal.setId(ms.getId());
			recordVal.set_numtaskfin(ms.numTaskFin);
			recordVal.set_numtaskwait(ms.waitQueue.size());
			recordVal.set_numtaskready(ms.localQueue.size() + ms.wsQueue.size());
			recordVal.set_numcoreavilable(ms.numIdleCore);
			recordVal.setNumAllCore(ms.config.numCorePerExecutor);
			recordVal.set_numworksteal(ms.numWS);
			recordVal.set_numworkstealfail(ms.numWSFail);
			String recordValStr = Tools.valueToStr(recordVal.build());
			sockMutex.lock();
			ms.zc.insert(ms.getId(), recordValStr);
			sockMutex.unlock();

			if (ms.schedulerLogOS.is_open()) {
				ms.schedulerLogOS << get_time_usec() << "\t" << ms.numTaskFin
						<< "\t" << ms.waitQueue.size() << "\t"
						<< ms.localQueue.size() + ms.wsQueue.size() << "\t"
						<< ms.numIdleCore << "\t" << ms.config.numCorePerExecutor
						<< "\t" << ms.numWS << "\t" << ms.numWSFail << endl;
			}

			/* check and modify how many tasks are done for all the schedulers. If all
			 * tasks are done, then flipping the scheduler status to off to indicate
			 * that it is not processing any task any more
			 * */
			String key = new String("num tasks done");
			String numTaskDoneStr;
			sockMutex.lock();
			ms.zc.lookup(key, numTaskDoneStr);
			cout << "Number of task done is:" << numTaskDoneStr << endl;
			sockMutex.unlock();

			increment += 2;

			long numTaskDone = Long.parseLong(numTaskDoneStr);
			if (numTaskDone == ms.config.numAllTask) {
				if (ms.schedulerLogOS.is_open()) {
					ms.schedulerLogOS << get_time_usec() << "\t" << ms.numTaskFin
							<< "\t" << ms.waitQueue.size() << "\t"
							<< ms.localQueue.size() + ms.wsQueue.size() << "\t"
							<< ms.numIdleCore << "\t"
							<< ms.config.numCorePerExecutor << "\t" << ms.numWS
							<< "\t" << ms.numWSFail << endl;

				}
				ms.running = false;
				break;
			}

			ms.numTaskFinMutex.lock();

			numTaskDone += (ms.numTaskFin - ms.prevNumTaskFin);
			String numTaskDoneStrNew = numTaskDone;
			String queryValue;
			increment++;
			sockMutex.lock();
			while (ms.zc.compare_swap(key, numTaskDoneStr, numTaskDoneStrNew,
					queryValue) != 0) {
				if (queryValue.empty()) {
					ms.zc.lookup(key, numTaskDoneStr);
					increment++;
				} else {
					numTaskDoneStr = queryValue;
				}
				numTaskDone = Long.parseLong(numTaskDoneStr);
				if (numTaskDone == ms.config.numAllTask) {
					break;
				}
				numTaskDone += (ms.numTaskFin - ms.prevNumTaskFin);
				numTaskDoneStrNew = numTaskDone;
				increment++;
			}
			//cout << "OK, Number of task done is:" << numTaskDoneStrNew << endl;
			sockMutex.unlock();
			ms.prevNumTaskFin = ms.numTaskFin;

			ms.numTaskFinMutex.unlock();
			usleep(ms.config.sleepLength);
		}

		ms.ZHTMsgCountMutex.lock();
		ms.incre_ZHT_msg_count(increment);
		ms.ZHTMsgCountMutex.unlock();

		ms.schedulerLogOS << "The number of ZHT message is:" << ms.numZHTMsg
				<< endl;
		ms.schedulerLogOS.flush();
		ms.schedulerLogOS.close();

		if (ms.taskTimeEntry.size() > 0) {
			//ms.tteMutex.lock();
			for (int i = 0; i < ms.taskTimeEntry.size(); i++) {
				ms.taskLogOS << ms.taskTimeEntry.at(i) << endl;
			}
			//ms.tteMutex.unlock();
		}

	}
}
