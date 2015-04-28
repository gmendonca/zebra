package matrix;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;

import matrix.zht.ZHTClient;
import matrix.protocol.Metamatrix.MatrixMsg;
import matrix.protocol.Metatask.TaskMsg;
import matrix.util.Configuration;
import matrix.util.MatrixTcpProxy;
import matrix.util.Tools;

public abstract class OverallPeer implements Peer{
	
	//Attributes from Interface Peer
		public ZHTClient zc;
		public Configuration config;
		public ArrayList<String> schedulerList;
		public Boolean running;
		public long numZHTMsg;
		
		public OverallPeer(String configFile) throws IOException{
			config = new Configuration(configFile);
			setId(Tools.getHostId(config.hostIdType));
			schedulerList = Tools.readFromFile(config.schedulerMemFile, Charset.defaultCharset());
			setIndex(Tools.genSelfIdx(getId(), schedulerList));
			running = true;
			numZHTMsg = 0;
			initZhtClient(config.zhtConfigFile, config.zhtMemFile);
			
		}
		
		public void waitAllScheduler() {
			String key = new String("number of scheduler registered");
			String value;
			value = zc.lookup(key);
			while (Integer.parseInt(value) != schedulerList.size()) {
				try{ Thread.sleep(10000); } catch (Exception e) { }
				value = zc.lookup(key);
			}
		}

		public void waitAllTaskRecv() {
			String key = new String("num tasks recv");
			String value;
			value = zc.lookup(key);
			while (Long.parseLong(value) != config.numAllTask) {
				try{ Thread.sleep(10000); } catch(Exception e) { }
				value = zc.lookup(key);
			}
		}

		public void increZHTMsgCount(long increment) {
			numZHTMsg += increment;
		}
		
		public void sendBatchTasks(ArrayList<TaskMsg> taskList, Socket sockfd, String peer) {
			
			long numTaskLeft = taskList.size();
			int numTaskBeenSent = 0;
			long numTaskSendPerPkg = config.maxTaskPerPkg;

			MatrixMsg.Builder mmNumTask = MatrixMsg.newBuilder();
			mmNumTask.setMsgType(peer + " send tasks");
			mmNumTask.setCount(numTaskLeft);
			String strNumTask = Tools.mmToStr(mmNumTask.build());

			if (numTaskLeft <= 0) {
				MatrixTcpProxy.sendMul(sockfd, strNumTask, true);
			} else {
				Boolean end = false;
				MatrixTcpProxy.sendMul(sockfd, strNumTask, end);
				while (numTaskLeft > 0) {
					if (numTaskLeft <= config.maxTaskPerPkg) {
						numTaskSendPerPkg = numTaskLeft;
						end = true;
					}

					MatrixMsg.Builder mmTask = MatrixMsg.newBuilder();
					mmTask.setMsgType(peer + " send tasks");
					mmTask.setCount(numTaskSendPerPkg);

					for (int i = 0; i < numTaskSendPerPkg; i++) {
						mmTask.addTasks(Tools.taskMsgToStr(taskList.get(i + numTaskBeenSent)));
					}
					String strTasks = Tools.mmToStr(mmTask.build());
					MatrixTcpProxy.sendMul(sockfd, strTasks, end);
					numTaskLeft -= numTaskSendPerPkg;
					numTaskBeenSent += numTaskSendPerPkg;
				}
			}
		}
}
