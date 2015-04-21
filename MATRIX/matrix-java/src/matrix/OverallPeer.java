package matrix;

import java.util.ArrayList;

import matrix.client.ZHTClient;
import matrix.util.Configuration;
import matrix.util.Tools;

public abstract class OverallPeer implements Peer{
	
	//Attributes from Interface Peer
		public ZHTClient zc;
		public Configuration config;
		public ArrayList<String> schedulerList;
		public Boolean running;
		public long numZHTMsg;
		private String id;
		private int index;
		
		public OverallPeer(String configFile){
			config = new Configuration(configFile);
			set_id(Tools.getHostId(config.hostIdType));
			schedulerList = Tools.readFromFile(config.schedulerMemFile);
			set_index(Tools.getSelfIdx(getId(), schedulerList));
			running = true;
			numZHTMsg = 0;
			initZhtClient(config.zhtConfigFile, config.zhtMemFile);
			
		}

}
