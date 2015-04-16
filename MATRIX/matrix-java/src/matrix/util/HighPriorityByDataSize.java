package matrix.util;

public class HighPriorityByDataSize {
	
	public Boolean operator(TaskMsg hpTaskMsg, TaskMsg lpTaskMsg){
		return hpTaskMsg.datalength() > lpTaskMsg.datalength();
	}

}
