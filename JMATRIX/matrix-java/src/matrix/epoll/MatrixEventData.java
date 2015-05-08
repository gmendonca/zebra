package matrix.epoll;

import java.net.Socket;

public class MatrixEventData {
	private Socket fd;
	private String buf;
	private int bufSize;
	//TODO: see if this can work
	private int fromAddr;
	
	public MatrixEventData(Socket fd, String buf, int bufSize, int fromAddr){
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
	
	public int fromAddr(){
		return this.fromAddr;
	}
}
