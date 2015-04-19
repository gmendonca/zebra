package matrix.util;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public interface Peer {
	
	Boolean initZhtClient(String something, String something2);

	void waitAllScheduler();
	void waitAllTaskRecv();

	void setId(String id);

	String getId();

	void setIndex(Integer index);

	int getIndex();

	void increZHTMsgCount(long count);

	void insertWrap(String key, String value);
	void insertWrap(char key, char value);

	void lookupWrap(String key, String result);
	void lookupWrap(char key, char result);

	void sendBatchTasks(ArrayList<TaskMsg> taskMsg, int batchNum, String something);
	void recvBatchTasks(ArrayList<TaskMsg> taskMsg, int batchNum);

	void recvBatchTasks();

}
