package matrix.util;

public class CmpQueueItem {
	
	public String taskId;
	public String key;
	public long dataSize;
	
	public CmpQueueItem(String taskId, String key, long dataSize) {
		this.taskId = taskId;
		this.key = key;
		this.dataSize = dataSize;
	}
	
	public CmpQueueItem() {
		this.taskId = "";
		this.key = "";
		this.dataSize = 0;
	}

}
