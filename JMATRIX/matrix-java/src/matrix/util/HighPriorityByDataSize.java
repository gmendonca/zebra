package matrix.util;

import matrix.protocol.Metatask.TaskMsg;

public class HighPriorityByDataSize {
	
	public Boolean operator(TaskMsg hpTaskMsg, TaskMsg lpTaskMsg){
		return hpTaskMsg.getDataLength() > lpTaskMsg.getDataLength();
	}

}
