package matrix.epoll;

public class MatrixEventData {
	private int fd;
	private char[] buf;
	private int bufSize;
	//TODO: see if this can work
	private String fromAddr;
	
	public MatrixEventData(int fd, String buf, int bufSize, String fromAddr){
		this.fd = fd;
		this.buf = new char[buf.length()];
		this.bufSize = bufSize;
		this.fromAddr = fromAddr;
	}
	
	public int fd(){
		return this.fd;
	}
	
	public char[] buf(){
		return this.buf;
	}
	
	public int bufSize(){
		return this.bufSize;
	}
	
	public String fromAddr(){
		return this.fromAddr;
	}
}
