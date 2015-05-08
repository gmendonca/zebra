
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class Test {
	public static void main(String[] args) {
		try{
		ServerSocket server = new ServerSocket(60000);
		System.out.println("...");
		System.out.println("Server runnning on port "+ 60000);
		System.out.println("Waiting for the client..");
		Socket client = server.accept();
		System.out.println("Client Connected..");
		InputStream is = client.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String buf = null;
        System.out.println("Trying to read from client");
        while((buf = br.readLine()) != null){
        	System.out.println(buf);
        }
        System.out.println("Done trying to read from client");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
