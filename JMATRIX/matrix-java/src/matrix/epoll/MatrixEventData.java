package matrix.epoll;

import java.net.Socket;

public class MatrixEventData {
	private Socket fd;
	private String buf;
	private int bufSize;
	//TODO: see if this can work
	private String fromAddr;
	
	public MatrixEventData(Socket fd, String buf, int bufSize, String fromAddr){
		this.fd = fd;
		this.buf = buf;
		this.bufSize = bufSize;
		this.fromAddr = fromAddr;
	}
	
	public Socket fd(){
		return this.fd;
	}
	
	public String buf(){
		return this.buf;
	}
	
	public int bufSize(){
		return this.bufSize;
	}
	
	public String fromAddr(){
		return this.fromAddr;
	}
}
