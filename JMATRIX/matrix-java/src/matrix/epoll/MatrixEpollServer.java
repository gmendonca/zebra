package matrix.epoll;

import java.util.LinkedList;
import java.util.Queue;

import matrix.scheduler.MatrixScheduler;
import matrix.scheduler.ServeThread;

public class MatrixEpollServer {
	public MatrixScheduler ms;
	public long port;
	public Queue<MatrixEventData> eventQueue;


	public	MatrixEpollServer(long port, MatrixScheduler ms) {
		this.port = port;
		this.ms = ms;
		eventQueue = new LinkedList<MatrixEventData>();
	}
	
	private void initThread(){
		ServeThread st = new ServeThread(this);
		st.start();
	}
	
	public void serve() {
		initThread();
		
	}
}