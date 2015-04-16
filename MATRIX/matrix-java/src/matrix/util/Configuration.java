package matrix.util;

import java.util.Map;

public class Configuration {
	
	private Map<String, String> configMap;
	
	private long numTaskPerClient;	// number of task per client
	private long numAllTask;	// number of all tasks
	private long numMapTask;
	private long numReduceTask;
	private int numCorePerExecutor;	// number of cores per executor
	private long maxTaskPerPkg;	// maximum number of tasks per package
	private long monitorInterval;	//monitor interval in microsecond
	private long schedulerPortNo;	// scheduler port number
	private long sleepLength;	// time duration in microsecond
	private int workStealingOn;	// indicate whether to do work staling (1) or not (0)
	private long wsPollIntervalStart;// the initial value of poll interval in microsecond
	private long wsPollIntervalUb;	// the upper bound of poll interval in microsecond

	private String policy;
	private long dataSizeThreshold;
	private long estTimeThreadshold;

	private String schedulerMemFile;	// the memberlist file of all the schedulers
	private String netProtoc;	// network protocol type: TCP, UDP, UDT, etc
	private String dagType;	// the type of workload DAGs: BOT, Fan-In, Fan-Out, Pipeline
	private long dagArg;	// the argument to the workload DAG
	private String hostIdType;// the host identity type: localhost, hostname, ip address
	private String submitMode;// the mode that clients submit tasks: best case, worst case
	private String workloadFile;	// the workload file
	private String schedulerWorkloadPath;// the workload root directory for all the schedulers
	private int clientLog;	// indicate whether to logging (1) for client or not (0)
	private int taskLog;// indicate whether to logging (1) for each individual task or not (0)
	private int systemLog;// indicate whether to logging (1) for the system status or not (0)
	private int schedulerLog;// indicate whether to logging (1) for scheduler or not (0)
	private String zhtMemFile;	// the ZHT memberlist file
	private String zhtConfigFile;	// the ZHT configuration file


	
	public Configuration(String configFile){
		parseConfiguration(configFile);
		
	}

	private void parseConfiguration(String configFile) {
		
		fstream fileStream(configFile.c_str());

		if (!fileStream.good())
			return;

		String line, key, value;

		while (getline(fileStream, line)) {
			stringstream ss(line);
			ss >> key >> value;
			if (!key.empty() && key[0] != '#')
				configMap.insert(make_pair(key, value));
			key.clear();
			value.clear();
		}

		fileStream.close();

		//numTaskPerClient = str_to_num<long>(configMap.find("NumTaskPerClient")->second);
		numTaskPerClient = Long.parseLong(configMap.get("NumTaskPerClient"), 10);

		numAllTask = Long.parseLong(configMap.get("NumAllTask"), 10);

		numMapTask = Long.parseLong(configMap.get("NumMapTask"), 10);

		numReduceTask = Long.parseLong(configMap.get("NumReduceTask"), 10);

		numCorePerExecutor = Integer.parseInt(configMap.get("NumCorePerExecutor"), 10);

		maxTaskPerPkg = Long.parseLong(configMap.get("MaxTaskPerPkg"), 10);

		monitorInterval = Long.parseLong(configMap.get("MonitorInterval"), 10);

		schedulerPortNo = Long.parseLong(configMap.get("SchedulerPortNo"), 10);

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
