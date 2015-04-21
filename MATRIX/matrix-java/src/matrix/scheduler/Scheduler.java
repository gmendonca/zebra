package matrix.scheduler;

public class Scheduler {
	
	public static void main(String args[]){
		if (args.length != 2) {
			System.out.println("The usage is: client\tconfiguration_file!\n");
			System.exit(1);
		}
		
		MatrixScheduler ms = new MatrixScheduler();
		
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
