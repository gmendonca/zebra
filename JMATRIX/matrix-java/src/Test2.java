import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class Test2 {
	
	
	public static void main(String[] args) {
		try {
			String buf = "x";
			Socket sock = new Socket("localhost", 60000);
			sock.getOutputStream();
			OutputStream os = sock.getOutputStream();
	        OutputStreamWriter osw = new OutputStreamWriter(os);
	        BufferedWriter bw = new BufferedWriter(osw);
	        bw.write(buf);
	        System.out.println("Writing on scheduler a buf "+ buf);
	        bw.flush();
	        bw.close();
	        sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
