package matrix.util;

public class TaskMsg {
	
	private String taskId;
	private String user;
	private String dir;
	private String cmd;
	private int dataLength;
	
	
	public void setDataLength(int datalength){
		this.dataLength = datalength;
	}

	public int getDataLength() {
		return dataLength;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
}
