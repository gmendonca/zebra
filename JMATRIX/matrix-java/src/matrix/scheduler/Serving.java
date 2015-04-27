package matrix.scheduler;

import matrix.epoll.MatrixEpollServer;

public class Serving extends Thread{
	
	MatrixEpollServer mes;
	
	public Serving(MatrixEpollServer mes){
		this.mes = mes;
	}
	
	public void run(){
		mes.serve();
	}

}
