package matrix;

import java.io.IOException;
import java.nio.charset.Charset;
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
		
		public OverallPeer(String configFile) throws IOException{
			config = new Configuration(configFile);
			setId(Tools.getHostId(config.hostIdType));
			schedulerList = Tools.readFromFile(config.schedulerMemFile, Charset.defaultCharset());
			setIndex(Tools.genSelfIdx(getId(), schedulerList));
			running = true;
			numZHTMsg = 0;
			initZhtClient(config.zhtConfigFile, config.zhtMemFile);
			
		}

}
