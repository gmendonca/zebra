package matrix.client;

public class ZHTClient {
	public native String lookUp(String key);
	public native int insertZHT(String key, String value);
	public native int compareSwap(String childTaskId, String childTaskDetail,String childTaskDetailAttempt, String queryValue);
}
