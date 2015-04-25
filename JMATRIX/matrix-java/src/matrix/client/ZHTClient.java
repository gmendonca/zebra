package matrix.client;

public class ZHTClient {
	
	public native String lookup(String key);
	public native int insert(String key, String value);
}
