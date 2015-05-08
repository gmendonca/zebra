package matrix.epoll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import matrix.scheduler.MatrixScheduler;
import matrix.scheduler.ServeThread;

public class MatrixEpollServer {
	public MatrixScheduler ms;
	public int port;
	public Queue<MatrixEventData> eventQueue;


	public	MatrixEpollServer(int port, MatrixScheduler ms) {
		this.port = port;
		this.ms = ms;
		eventQueue = new LinkedList<MatrixEventData>();
	}
	
	private void initThread(){
		ServeThread st = new ServeThread(this);
		System.out.println("Trying");
		st.start();
	}
	
	public void serve() {
		initThread();
		System.out.println("Trying to open the server...");
		ServerSocket server = null;
		while(true){
			try {
				StringBuffer buffer = new StringBuffer("");
				System.out.println("...");
				server = new ServerSocket(port);
				System.out.println("Server runnning on port "+ port);
				System.out.println("Waiting for the client..");
				Socket client = server.accept();
				System.out.println("Client Connected..");
				InputStream is = client.getInputStream();
		        InputStreamReader isr = new InputStreamReader(is);
		        BufferedReader br = new BufferedReader(isr);
		        String buf = null;
		        System.out.println("Trying to read from client");
		        while((buf = br.readLine()) != null){
		        	//System.out.println(buf);
		        	buffer.append(buf);
		        	if(buf.endsWith("$")) break;
		        }
		        synchronized(this){
	        		System.out.println("adding event in port " + port + " with uffer length " + buffer.toString().length());
	        		eventQueue.add(new MatrixEventData(client, buffer.toString(), buffer.toString().length(), server));
	        	}
		        br.close();
		        isr.close();
		        System.out.println("Done trying to read from client");
				
			} catch (Exception e) {
				e.printStackTrace();
				try {
					server.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
	}
}