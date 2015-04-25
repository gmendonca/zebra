package matrix.client;

public class ZHTClient {
	
	public native String lookup(String key);
	public native int insert(String key, String value);
	public native int compare_swap(String childTaskId, String childTaskDetail,String childTaskDetailAttempt, String query_value);
}
