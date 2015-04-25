package matrix.util;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MatrixTcpProxy {
	
	public static Socket send_first(String ip, int port, String buf) {
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

	public static int recvBf(Socket sock, String buf) {
		char bufStr[] = new char [Declarations.BUF_SIZE];

		int ret = recv(sock, bufStr, bufStr.length, 0);
		buf = new String(bufStr);

		return ret;
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

	int recvBig(Socket sock, String buf) {
		int count = recv_mul(sock, buf);
		buf = buf.substring(0, buf.length() - 1);
		return count;
	}

	int sendMul(Socket sock, String buf, Boolean end) {
		StringBuffer bufUp = new StringBuffer();
		bufUp.append(buf);
		bufUp.append("##");

		if (end) {
			bufUp.append("$");
		}
		return sendBf(sock, bufUp.toString());
	}

	int recv_mul(Socket sock, String buf) {
		String tmpBuf = new String();
		int count = recvBf(sock, tmpBuf);
		int sum = 0;
		StringBuffer temporary = new StringBuffer();

		while (count > 0) {
			sum += count;
			temporary.append(tmpBuf);
			if (tmpBuf.charAt(tmpBuf.length() - 1) == '$') {
				break;
			}
			count = recvBf(sock, tmpBuf);
		}
		buf = temporary.toString();
		return sum;
	}

}
