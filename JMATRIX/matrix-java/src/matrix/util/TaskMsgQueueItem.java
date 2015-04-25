package matrix.util;

import matrix.protocol.Metatask.TaskMsg;

public class TaskMsgQueueItem implements Comparable<TaskMsg>{
	
	private TaskMsg hpTaskMsg;
	
	public TaskMsgQueueItem(TaskMsg hpTaskMsg){
		this.hpTaskMsg = hpTaskMsg;
	}
	
	@Override
	public int compareTo(TaskMsg lpTaskMsg) {
		return (hpTaskMsg.getDataLength() > lpTaskMsg.getDataLength()) ? 1 : 0;
	}
	
	public TaskMsg getTaskMsg(){
		return hpTaskMsg;
	}

}
