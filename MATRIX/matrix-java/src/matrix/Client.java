package matrix;

import java.io.IOException;
import java.nio.charset.Charset;
import matrix.util.Tools;

public class Client {

	
	public static void main(String args[]){
		if (args.length != 2) {
			System.out.println("The usage is: client\tconfiguration_file!\n");
			System.exit(1);
		}
		
		long startTime = System.currentTimeMillis();
		MatrixClient mc;
		
		try {
			String configFile = Tools.readFile(args[1],Charset.defaultCharset());
			 mc = new MatrixClient(configFile);
		} catch (IOException e) {
			System.out.println("The usage is: client\tconfiguration_file!\n");
			System.exit(1);
		}
		
		
		
	}
}
