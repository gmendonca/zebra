package matrix.util;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MatrixTcpProxy {
	
	public static Socket sendFirst(String ip, int port, String buf) {
		Socket to_sock = new Socket();
		try {
			to_sock = new Socket(ip, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (to_sock.isClosed()) {
			sendBf(to_sock, buf);
		}
		return to_sock;
	}

	public static int sendBf(Socket sock, String buf) {
		return send(sock, buf, buf.length(), 0);
	}

	public static ReturnValue recvBf(Socket sock, String buf) {
		char bufStr[] = new char [Declarations.BUF_SIZE];

		int ret = recv(sock, bufStr, bufStr.length, 0);
		buf = new String(bufStr);

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

	public static ReturnValue recvBig(Socket sock, String buf) {
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

	public static ReturnValue recvMul(Socket sock, String buf) {
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
