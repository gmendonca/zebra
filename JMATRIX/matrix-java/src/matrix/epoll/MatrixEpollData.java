package matrix.epoll;

public class MatrixEpollData {
	
	private String sender;
	private int fd;
	
	public MatrixEpollData(int fd, String sender){
		this.fd = fd;
		this.sender = sender;
	}
	
	public int fd(){
		return this.fd;
	}
	
	public String sender(){
		return this.sender;
	}

}
