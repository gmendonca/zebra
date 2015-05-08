package matrix.client;

import java.io.IOException;

import matrix.util.Tools;
import matrix.protocol.Metazht.Value;

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

		String numTaskFinStr = null;

		/* system status log head */
		
		
		try {
			mc.systemLogOS.write("Time(us)\tNumAllCore\tNumIdleCore\tNumTaskWait\t" + "NumTaskReady\tNumTaskDone\tThroughput");
			mc.systemLogOS.newLine();
		} catch (Exception e){ }


		long increment = 0;

		while (true) {
			numTaskFinStr = mc.zc.lookup(key);	// lookup how many tasks are done
			numTaskDone = Long.parseLong(numTaskFinStr);
			System.out.println("number of task done is: " + numTaskDone);
			increment++;

			/* log the instant system status */
			
			currentTimeUs = System.currentTimeMillis() % 1000;
			for (int i = 0; i < mc.schedulerList.size(); i++) {
				String schedulerStat;
				schedulerStat = mc.zc.lookup(mc.schedulerList.get(i));
				System.out.println("schedulerStat " + mc.schedulerList.get(i));
				if (schedulerStat.isEmpty())
					continue;
				System.out.println("schedulerStat " + schedulerStat);
				Value value = Tools.strToValue(schedulerStat);
				numIdleCore += value.getNumCoreAvilable();
				numTaskWait += value.getNumTaskWait();
				numTaskReady += value.getNumTaskReady();
			}
				

			increment += mc.schedulerList.size();

			instantThr = (double) (numTaskDone - preNumTaskDone)
						/ (currentTimeUs - prevTimeUs) * 1E6;
			try {
			mc.systemLogOS.write(currentTimeUs + "\t" + numAllCore + "\t"
						+ numIdleCore + "\t" + numTaskWait + "\t"
						+ numTaskReady + "\t" + numTaskDone + "\t" + instantThr);
			mc.systemLogOS.newLine();
			} catch(Exception e) { }

			preNumTaskDone = numTaskDone;
			prevTimeUs = currentTimeUs;
			numIdleCore = 0;
			numTaskWait = 0;
			numTaskReady = 0;

			if (numTaskDone == mc.config.numAllTask)	// all the tasks are done
				break;
			else
				try { Thread.sleep(mc.config.monitorInterval); } catch (Exception e) { }	// sleep sometime 
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
