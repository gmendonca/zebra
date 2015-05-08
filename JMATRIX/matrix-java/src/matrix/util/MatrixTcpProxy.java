package matrix.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MatrixTcpProxy {
	
	@SuppressWarnings("resource")
	public static int sendFirst(String ip, int port, String buf) {
		Socket to_sock = new Socket();
		try {
			to_sock = new Socket(ip, port);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!to_sock.isClosed()) {
			sendBf(to_sock, buf);
		}
		return port;
	}

	public static int sendBf(Socket sock, String buf) {
		
		try {
			sock.getOutputStream();
			OutputStream os = sock.getOutputStream();
	        OutputStreamWriter osw = new OutputStreamWriter(os);
	        BufferedWriter bw = new BufferedWriter(osw);
	        bw.write(buf);
	        bw.flush();
	        bw.close();
	        sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int ret = (buf == null) ? 0 : 1;
		
		return ret;
		
	}

	public static ReturnValue recvBf(ServerSocket sock, String buf) {
		Socket socket;
		
		buf = null;
		System.out.println("Hi");
		try {
			System.out.println("Waiting for the client..");
			socket = sock.accept();
			System.out.println("Connected..");
			InputStream is = socket.getInputStream();
	        InputStreamReader isr = new InputStreamReader(is);
	        BufferedReader br = new BufferedReader(isr);
	        buf = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        int ret = (buf == null) ? 0 : 1; 

		return new ReturnValue(ret, buf);
	}

	public static int sendBig(Socket sock, String buf) {
		int length = buf.length();
		int numSent = 0;
		StringBuffer str;

		while (length >= Declarations.BUF_SIZE) {
			str = new StringBuffer(buf.substring(numSent, Declarations.BUF_SIZE));
			sendBf(sock, str.toString());
			numSent += Declarations.BUF_SIZE;
			length -= Declarations.BUF_SIZE;
		}

		if (length > 0) {
			str = new StringBuffer(buf.substring(numSent, length));
			str.append("$");
		} else {
			str = new StringBuffer("$");
		}
		sendBf(sock, str.toString());
		numSent += str.length();

		return numSent;
	}

	public static ReturnValue recvBig(ServerSocket sock, String buf) {
		ReturnValue rv = recvMul(sock, buf);
		buf = rv.result.substring(0, rv.result.length() - 1);
		return new ReturnValue(rv.value, buf);
	}

	public static int sendMul(Socket sock, String buf, Boolean end) {
		StringBuffer bufUp = new StringBuffer();
		bufUp.append(buf);
		bufUp.append("##");

		if (end) {
			bufUp.append("$");
		}
		return sendBf(sock, bufUp.toString());
	}

	public static ReturnValue recvMul(ServerSocket sock, String buf) {
		String tmpBuf = new String();
		ReturnValue rv = recvBf(sock, tmpBuf);
		int sum = 0;
		StringBuffer temporary = new StringBuffer(buf);
		
		while (rv.value > 0) {
			sum += rv.value ;
			temporary.append(rv.result);
			if (rv.result.charAt(rv.result.length() - 1) == '$') {
				break;
			}
			rv = recvBf(sock, rv.result);
		}
		buf = temporary.toString();
		return new ReturnValue(sum, buf);
	}

}
