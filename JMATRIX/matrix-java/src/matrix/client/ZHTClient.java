package matrix.client;

public class ZHTClient {
	public native String lookUp(String key);
	public native int insertZHT(String key, String value);
	public native int compareSwapInt(String childTaskId, String childTaskDetail,String childTaskDetailAttempt);
	public native String compareSwapString(String childTaskId, String childTaskDetail,String childTaskDetailAttempt);
}
