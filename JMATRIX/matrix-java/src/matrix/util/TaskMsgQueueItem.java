package matrix.util;

import matrix.protocol.Metatask.TaskMsg;

public class TaskMsgQueueItem implements Comparable<TaskMsgQueueItem>{
	
	private TaskMsg hpTaskMsg;
	
	public TaskMsgQueueItem(TaskMsg hpTaskMsg){
		this.hpTaskMsg = hpTaskMsg;
	}
	
	@Override
	public int compareTo(TaskMsgQueueItem lpTaskMsg) {
		return (hpTaskMsg.getDataLength() > lpTaskMsg.getTaskMsg().getDataLength()) ? 1 : 0;
	}
	
	public TaskMsg getTaskMsg(){
		return hpTaskMsg;
	}

}
