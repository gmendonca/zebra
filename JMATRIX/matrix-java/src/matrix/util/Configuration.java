package matrix.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Configuration {
	
	public Map<String, String> configMap;
	
	public long numTaskPerClient;	// number of task per client
	public long numAllTask;	// number of all tasks
	public long numMapTask;
	public long numReduceTask;
	public int numCorePerExecutor;	// number of cores per executor
	public long maxTaskPerPkg;	// maximum number of tasks per package
	public long monitorInterval;	//monitor interval in microsecond
	public int schedulerPortNo;	// scheduler port number
	public long sleepLength;	// time duration in microsecond
	public int workStealingOn;	// indicate whether to do work staling (1) or not (0)
	public long wsPollIntervalStart;// the initial value of poll interval in microsecond
	public long wsPollIntervalUb;	// the upper bound of poll interval in microsecond

	public String policy;
	public long dataSizeThreshold;
	public long estTimeThreadshold;

	public String schedulerMemFile;	// the memberlist file of all the schedulers
	public String netProtoc;	// network protocol type: TCP, UDP, UDT, etc
	public String dagType;	// the type of workload DAGs: BOT, Fan-In, Fan-Out, Pipeline
	public long dagArg;	// the argument to the workload DAG
	public String hostIdType;// the host identity type: localhost, hostname, ip address
	public String submitMode;// the mode that clients submit tasks: best case, worst case
	public String workloadFile;	// the workload file
	public String schedulerWorkloadPath;// the workload root directory for all the schedulers
	public int clientLog;	// indicate whether to logging (1) for client or not (0)
	public int taskLog;// indicate whether to logging (1) for each individual task or not (0)
	public int systemLog;// indicate whether to logging (1) for the system status or not (0)
	public int schedulerLog;// indicate whether to logging (1) for scheduler or not (0)
	public String zhtMemFile;	// the ZHT memberlist file
	public String zhtConfigFile;	// the ZHT configuration file


	
	public Configuration(String configFile){
		configMap = new HashMap<String, String>();
		parseConfiguration(configFile);
		
	}

	private void parseConfiguration(String configFile) {
		
		FileInputStream fStream;
		try {
			fStream = new FileInputStream(configFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(fStream));
			
			String line, key, value;
			
			while ((line = br.readLine()) != null)   {
				if(line.isEmpty()) continue;
				key = line.split("\\s+")[0];
				value = line.split("\\s+")[1];
				if (!key.isEmpty() && !key.startsWith("#"))
					configMap.put(key, value);
				  //System.out.println (line);
				}
			fStream.close();
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		//numTaskPerClient = str_to_num<long>(configMap.find("NumTaskPerClient")->second);
		numTaskPerClient = Long.parseLong(configMap.get("NumTaskPerClient"), 10);

		numAllTask = Long.parseLong(configMap.get("NumAllTask"), 10);

		numMapTask = Long.parseLong(configMap.get("NumMapTask"), 10);

		numReduceTask = Long.parseLong(configMap.get("NumReduceTask"), 10);

		numCorePerExecutor = Integer.parseInt(configMap.get("NumCorePerExecutor"), 10);

		maxTaskPerPkg = Long.parseLong(configMap.get("MaxTaskPerPkg"), 10);

		monitorInterval = Long.parseLong(configMap.get("MonitorInterval"), 10);

		schedulerPortNo = Integer.parseInt(configMap.get("SchedulerPortNo"), 10);

		sleepLength = Long.parseLong(configMap.get("SleepLength"), 10);

		workStealingOn = Integer.parseInt(configMap.get("WorkStealOn"), 10);

		wsPollIntervalStart = Long.parseLong(configMap.get("WorkStealPollIntervalStart"), 10);

		wsPollIntervalUb = Long.parseLong(configMap.get("WorkStealPollIntervalUpperBound"), 10);

		policy = configMap.get("Policy");

		dataSizeThreshold = Long.parseLong(configMap.get("DataSizeThreshold"), 10);

		estTimeThreadshold = Long.parseLong(configMap.get("EstimatedTimeThreshold"), 10);

		schedulerMemFile = configMap.get("SchedulerMemlistFile");

		netProtoc = configMap.get("NetworkProtocol");

		dagType = configMap.get("DagType");

		dagArg = Long.parseLong(configMap.get("DagArgument"), 10);

		hostIdType = configMap.get("HostIdentityType");

		submitMode = configMap.get("SubmissionMode");

		workloadFile = configMap.get("WorkloadFile");

		schedulerWorkloadPath = configMap.get("SchedulerWorkloadPath");

		clientLog = Integer.parseInt(configMap.get("ClientLog"), 10);

		taskLog = Integer.parseInt(configMap.get("TaskLog"));

		systemLog = Integer.parseInt(configMap.get("SystemLog"));

		schedulerLog = Integer.parseInt(configMap.get("SchedulerLog"));

		zhtMemFile = configMap.get("ZhtMemlistFile");

		zhtConfigFile = configMap.get("ZhtConfigFile");

		
	}

}
