package matrix.scheduler;

import matrix.epoll.MatrixEpollServer;
import matrix.epoll.MatrixEventData;

public class ServeThread extends Thread{
	private MatrixEpollServer mes;
	
	public ServeThread(MatrixEpollServer mes){
		this.mes = mes;
	}
	
	public void run(){
		while(true){
			while(!mes.eventQueue.isEmpty()){
				MatrixEventData eventData;
				synchronized(this){
					eventData = mes.eventQueue.poll();
					mes.ms.procReq(eventData.fd(), eventData.buf(), eventData.fromAddr());
				}
				try { Thread.sleep(1000); } catch (Exception e) { }
			}
			try { Thread.sleep(1000); } catch (Exception e) { }
		}
	}
}
