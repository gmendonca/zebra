package matrix.util;

import java.io.IOException;

import matrix.client.MatrixClient;

public class Monitoring extends Thread{
	
	
	private MatrixClient mc;
	
	public Monitoring(MatrixClient mc){
		this.mc = mc;
	}
	
	/* monitoring thread function, monitoring is conducted only by client 0.
	 * It can monitor the execution progress of all the tasks, the system
	 * status, and log all the task details
	 * */
	public void run(){
		String key = "num tasks done";
		
		long numAllCore = mc.config.numCorePerExecutor * mc.schedulerList.size();
		long numIdleCore = 0;
		long numTaskWait = 0;
		long numTaskReady = 0;
		long preNumTaskDone = 0, numTaskDone = 0;
		long prevTimeUs = 0L, currentTimeUs = 0L;
		double instantThr = 0.0;

		String numTaskFinStr;

		/* system status log head */
		
		mc.systemLogOS.write("Time(us)\tNumAllCore\tNumIdleCore\tNumTaskWait\t" + "NumTaskReady\tNumTaskDone\tThroughput");
		mc.systemLogOS.newLine();


		long increment = 0;

		while (true) {
			mc.zc.lookup(key, numTaskFinStr);	// lookup how many tasks are done
			numTaskDone = Long.parseLong(numTaskFinStr);
			System.out.println("number of task done is: " + numTaskDone);
			increment++;

			/* log the instant system status */
			
			currentTimeUs = System.currentTimeMillis() % 1000;
			for (int i = 0; i < mc.schedulerVec.size(); i++) {
				String schedulerStat;
				mc.zc.lookup(mc.schedulerList.get(i), schedulerStat);
				if (schedulerStat.isEmpty())
					continue;

				Value value = Tools.strToValue(schedulerStat);
				numIdleCore += value.numCoreAvilable();
				numTaskWait += value.numTaskWait();
				numTaskReady += value.numTaskReady();
			}
				

			increment += mc.schedulerList.size();

			instantThr = (double) (numTaskDone - preNumTaskDone)
						/ (currentTimeUs - prevTimeUs) * 1E6;

			mc.systemLogOS.write(currentTimeUs + "\t" + numAllCore + "\t"
						+ numIdleCore + "\t" + numTaskWait + "\t"
						+ numTaskReady + "\t" + numTaskDone + "\t" + instantThr);
			mc.systemLogOS.newLine();

			preNumTaskDone = numTaskDone;
			prevTimeUs = currentTimeUs;
			numIdleCore = 0;
			numTaskWait = 0;
			numTaskReady = 0;

			if (numTaskDone == mc.config.numAllTask)	// all the tasks are done
				break;
			else
				Thread.sleep(mc.config.monitorInterval);	// sleep sometime
		}
		
		mc.stopTime = System.currentTimeMillis();
		long diff = mc.stopTime - mc.startTime;
		double throughput = (double)mc.config.numAllTask / (double)diff;
		
		System.out.println("It takes " + diff + "ms finish tasks!");
		System.out.println("The overall throughput is: " + throughput);
		
		try{
			mc.clientLogOS.newLine();
			mc.clientLogOS.write("It takes " + diff + "ms finish tasks!");
			mc.clientLogOS.write("The overall throughput is: " + throughput);
		} catch(IOException e) { }
		
		try{
			mc.systemLogOS.flush();
			mc.systemLogOS.close();
		} catch(IOException e) { }
		
		mc.increZHTMsgCount(increment);
		
		System.out.println("The number of ZHT message is: " + mc.numZHTMsg);
		
		try{
			mc.clientLogOS.newLine();
			mc.clientLogOS.write("The number of ZHT message is: " + mc.numZHTMsg);
			mc.clientLogOS.flush();
			mc.clientLogOS.close();
		} catch(IOException e) { }
		
	}	

}
