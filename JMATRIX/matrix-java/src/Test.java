
import matrix.zht.ZHTClient;


public class Test {
	public static void main(String[] args) {

		System.loadLibrary("cpp-zhtclient");
		ZHTClient zc = new ZHTClient();
		System.out.println(zc.lookup(" "));
	}

}
