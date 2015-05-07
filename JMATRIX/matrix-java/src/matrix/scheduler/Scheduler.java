package matrix.scheduler;

import java.io.IOException;
import java.nio.charset.Charset;

import matrix.util.Tools;

public class Scheduler {
	
	public static void main(String args[]){
		
		if (args.length != 1) {
			System.out.println("The usage is: scheduler\tconfiguration_file!\n");
			System.exit(1);
		}
		
		MatrixScheduler ms = null;
		
		try {
			String configFile = Tools.readFile(args[0],Charset.defaultCharset());
			 ms = new MatrixScheduler(configFile);
		} catch (IOException e) {
			System.out.println("The usage is: scheduler\tconfiguration_file!\n");
			e.printStackTrace();
			System.exit(1);
		}
		
		if(ms == null) return;
		
		ms.regist();	// regists to ZHT

		ms.waitAllScheduler();	// waits all the other schedulers are running

		ms.forkEsThread();	// forks the epoll event driven server

		//ms->load_data();

		//ms->get_task_from_file();

		ms.waitAllTaskRecv();
		
		//TODO: we gotta change this, it's used somewhere else
		long startTime = System.currentTimeMillis();

		ms.forkCrtThread();	// forks checking ready task thread

		ms.forkExecTaskThread();	// forks executing task threads

		ms.forkCctThread();	// forks checking complete task thread

		ms.forkWsThread();	// forks work stealing thread

		if (ms.config.policy.equals("FLWS")) {
			ms.forkLocalQueueMonitorThread();
		}

		ms.forkRecordStatThread();	// forks recording status thread

		while (true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
