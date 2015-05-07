package matrix.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import matrix.protocol.Metamatrix.MatrixMsg;
import matrix.protocol.Metatask.TaskMsg;
import matrix.protocol.Metazht.Value;

public class Tools {
	
	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	public static ArrayList<String> readFromFile(String path, Charset encoding) throws IOException {
		List<String> list = Files.readAllLines(Paths.get(path),encoding);
		return (ArrayList<String>) list;
	}
	
	public static ArrayList<String> readWorkloadFromFile(String path, Charset encoding) throws IOException {
		List<String> list = Files.readAllLines(Paths.get(path),encoding);
		return (ArrayList<String>) list;
	}
	
	public static ArrayList<String> tokenizer(String source, String regex){
		return (source.isEmpty()) 
				? new ArrayList<String>() 
				: new ArrayList <String>(Arrays.asList(source.split(regex)));
		
	}
	
	public static String getIP(){
		
		//TODO: not sure if this has the same effect than the matrix one
		String ip = null;
		try {
			InetAddress ia = InetAddress.getLocalHost();
			ip = ia.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return ip;
	}
	
	public static String exec(String cmd){
		
		Process pr = null;
		try {
			pr = Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(pr == null)
			return null;
		//TODO: need to check if this is going to work
		BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		
		String result = null;
		try {
			result = in.readLine();
			pr.waitFor();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		if(result == null)
			return null;
		
		if(!result.isEmpty() && result.endsWith("\n"))
			result = result.substring(0, result.length() -1);
		
		return result;
	}
	
	public static String getHostId(String type){
		String id = null;
		
		if(type.equals("localhost")){
			id = "localhost";
		} else if (type.equals("ip")){
			id = getIP();
		}else if(type.equals("hostname")){
			id = exec("hostname");
		}
		
		return id;
	}
	
	public static int genSelfIdx(String str, ArrayList<String> strList){
		int idx = -1;
		
		for(int i = 0; i < strList.size(); i++){
			if(str.equals(strList.get(i))){
				idx = i;
				break;
			}
		}
		
		return idx;
	}
	
	/* generate adjecency list for BOT independent tasks */
	public static AdjList genBotAdjList(AdjList dagAdjList, long numTask){
		for(long i = 0; i < numTask; i++){
			ArrayList<Long> newList = new ArrayList<Long>();
			dagAdjList.adjList.put(i, newList);
		}
		
		return dagAdjList;
	}
	
	/*
	 * generate adjacency list for fanout dags,
	 * the argument is the fan out degree
	 * */
	public static AdjList genFanoutAdjList(AdjList dagAdjList, long dagArg, long numTask){
		long next = -1;

		for (long i = 0; i < numTask; i++) {
			ArrayList<Long> newList = new ArrayList<Long>();

			for (long j = 1; j <= dagArg; j++) {
				next = i * dagArg + j;

				if (next >= numTask) {
					break;
				} else {
					newList.add(next);
				}
			}

			dagAdjList.adjList.put(i, newList);
		}
		
		return dagAdjList;
		
	}
	
	/*
	 * generate adjacency list for fan in dags,
	 * the argument is the fan in degree
	 * */
	public static AdjList genFaninAdjList(AdjList dagAdjList, long dagArg, long numTask){
		AdjList tmpAdjList = new AdjList();

		/* first generate an adjacency list for fan out dag,
		 * and then flip it over, and switch the left with the
		 * right to get the adjacency list for fan in dags */
		genFanoutAdjList(tmpAdjList, dagArg, numTask);

		for (long i = 0; i < numTask; i++) {
			long reverseId = numTask - 1 - i;

			ArrayList<Long> newList = new ArrayList<Long>();
			newList.add(reverseId);

			ArrayList<Long> tmpList = tmpAdjList.adjList.get(i);

			for (int j = 0; j < tmpList.size(); j++) {
				dagAdjList.adjList.put(numTask - 1 - tmpList.get(tmpList.size()-1-j), newList);
			}
		}

		ArrayList<Long> lastList = new ArrayList<Long>();
		dagAdjList.adjList.put(numTask - 1, lastList);
		
		return dagAdjList;
	}
	
	public static AdjList genPipelineAdjList(AdjList dagAdjList, long dagArg, long numTask){
		long numPipe = numTask / dagArg, index = -1, next = -1;

		for (long i = 0; i < numPipe; i++) {
			for (long j = 0; j < dagArg; j++) {
				index = i * dagArg + j;
				next = index + 1;
				ArrayList<Long> newList = new ArrayList<Long>();

				if (next % dagArg != 0 && next < numTask) {
					newList.add(next);
				}

				dagAdjList.adjList.put(index, newList);
			}
		}

		for (index = numPipe * dagArg; index < numTask; index++) {
			next = index + 1;
			ArrayList<Long> newList = new ArrayList<Long>();

			if (next % dagArg != 0 && next < numTask) {
				newList.add(next);
			}

			dagAdjList.adjList.put(index, newList);
		}
		
		return dagAdjList;
	}
	
	public static AdjList genDagAdjList(AdjList dagAdjList, String dagType, long dagArg, long numTask){
		if (dagType.equals("BOT"))
			genBotAdjList(dagAdjList, numTask);
		else if (dagType.equals("FanOut")) 
			genFanoutAdjList(dagAdjList, dagArg, numTask);
		else if (dagType.equals("FanIn"))
			genFaninAdjList(dagAdjList, dagArg, numTask);
		else if (dagType.equals("Pipeline"))
			genPipelineAdjList(dagAdjList, dagArg, numTask);
		
		return dagAdjList;
	}
	
	public static InDegree genDagInDegree(AdjList dagAdjList, InDegree dagInDegree){
		for (long i = 0; i < dagAdjList.adjList.size(); i++) {
			dagInDegree.put(i, 0L);
		}
		Iterator<Entry<Long, ArrayList<Long>>> it = dagAdjList.adjList.entrySet().iterator();
		long inc;
		while(it.hasNext()){
			Map.Entry<Long, ArrayList<Long>> pair = (Map.Entry<Long, ArrayList<Long>>)it.next();
			
			for (int j = 0; j < pair.getValue().size(); j++) {
				inc = dagInDegree.get(pair.getValue().get(j));
				dagInDegree.put(pair.getValue().get(j), ++inc);
			}
		}
		
		return dagInDegree;
	}
	
	public static AdjList genDagParents(AdjList dagAdjList, AdjList dagParentList){
		for(long i = 0; i < dagAdjList.adjList.size(); i++){
			ArrayList<Long> parents = new ArrayList<Long>();
			dagParentList.adjList.put(i, parents);
		}
		Iterator<Entry<Long, ArrayList<Long>>> it = dagAdjList.adjList.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry<Long, ArrayList<Long>> pair = (Map.Entry<Long, ArrayList<Long>>)it.next();
			
			for (int j = 0; j < pair.getValue().size(); j++) {
				dagParentList.adjList.get(pair.getValue().get(j)).add(pair.getKey());
			}
		}
		
		return dagParentList;
	}
	
	//TODO: there are a lot of time methods, but I don't think its extremely necessary
	
	public static String taskMsgToStr(TaskMsg taskMsg){
		StringBuffer str = new StringBuffer("");
		
		str.append(taskMsg.getTaskId());
		str.append("@@");
		str.append(taskMsg.getUser());
		str.append("@@");
		str.append(taskMsg.getDir());
		str.append("@@");
		str.append(taskMsg.getCmd());
		str.append("@@");
		str.append(taskMsg.getDataLength());
		str.append("@@");
		
		return str.toString();
	}
	
	public static TaskMsg strToTaskMsg(String str){
		ArrayList<String> listStr = tokenizer(str,"@@");
		
		if(listStr.size() == 0){
			System.out.println("It has some problem!");
			return null;
		}
		
		TaskMsg.Builder tm = TaskMsg.newBuilder();
		tm.setUser(listStr.get(0));
		if (listStr.size() > 1) {
			tm.setUser(listStr.get(1));
		} else {
			System.out.println("has problem, the vector size is:1");
			tm.setUser("kwang");
		}
		if (listStr.size() > 2) {
			tm.setDir(listStr.get(2));
		} else {
			System.out.println("has problem, the vector size is:2");
			tm.setDir("/home/kwang/Documents");
		}
		if (listStr.size() > 3) {
			tm.setCmd(listStr.get(3));
		} else {
			System.out.println("has problem, the vector size is:3");
			tm.setCmd("hostname");
		}
		if (listStr.size() > 4) {
			tm.setDataLength(Long.parseLong(listStr.get(4)));
		} else {
			System.out.println("has problem, the vector size is:4");
			tm.setDataLength(0);
		}
		return tm.build();
	}
	
	public static String valueToStr(Value value) {
		StringBuffer str = new StringBuffer("");

		str.append(value.getId());
		str.append("~~");

		if (value.hasInDegree()) {
			str.append(value.getInDegree());
		} else {
			str.append("noindegree");
		}
		str.append("~~");
		
		//TODO: parents_size turn parents_count, not sure if it's correct
		if (value.getParentsCount() > 0) {
			for (int i = 0; i < value.getParentsCount(); i++) {
				str.append(value.getParents(i));
				str.append("@@@");
			}
		} else {
			str.append("noparents");
		}
		str.append("~~");
		
		//TODO: children_size -> count
		if (value.getChildrenCount() > 0) {
			for (int i = 0; i < value.getChildrenCount(); i++) {
				str.append(value.getChildren(i));
				str.append("@@@");
			}
		} else {
			str.append("nochildren");
		}
		str.append("~~");
		
		//TODO:again size -> count
		if (value.getDataNameListCount() > 0) {
			for (int i = 0; i < value.getDataNameListCount(); i++) {
				str.append(value.getDataNameList(i));
				str.append("@@@");
			}
		} else {
			str.append("nodataname");
		}
		str.append("~~");
		
		//TODO:again size -> count
		if (value.getDataSizeCount() > 0) {
			for (int i = 0; i < value.getDataSizeCount(); i++) {
				str.append(value.getDataSize(i));
				str.append("@@@");
			}
		} else {
			str.append("nodatasize");
		}
		str.append("~~");

		if (value.hasAllDataSize()) {
			str.append(value.getAllDataSize());
		} else {
			str.append("noalldatasize");
		}
		str.append("~~");

		if (value.hasTaskLength())
			str.append(value.getTaskLength());
		else
			str.append("notasklength");
		str.append("~~");

		if (value.hasNumTaskFin()) {
			str.append(value.getNumTaskFin());
		} else {
			str.append("nonumtaskfin");
		}
		str.append("~~");

		if (value.hasNumWorkSteal()) {
			str.append(value.getNumWorkSteal());
		} else {
			str.append("nonumworksteal");
		}
		str.append("~~");

		if (value.hasNumWorkStealFail()) {
			str.append(value.getNumWorkStealFail());
		} else {
			str.append("nonumworkstealfail");
		}
		str.append("~~");

		if (value.hasNumTaskWait()) {
			str.append(value.getNumTaskWait());
		} else {
			str.append("nonumtaskwait");
		}
		str.append("~~");

		if (value.hasNumTaskReady()) {
			str.append(value.getNumTaskReady());
		} else {
			str.append("nonumtaskready");
		}
		str.append("~~");

		if (value.hasNumCoreAvilable()) {
			str.append(value.getNumCoreAvilable());
		} else {
			str.append("nonumcoreavail");
		}
		str.append("~~");

		if (value.hasNumAllCore()) {
			str.append(value.getNumAllCore());
		} else {
			str.append("nonumallcore");
		}
		str.append("~~");

		if (value.hasOutputSize())
			str.append(value.getOutputSize());
		else
			str.append("nooutputsize");
		str.append("~~");

		if (value.hasSubmitTime())
			str.append(value.getSubmitTime());
		else
			str.append("nosubmittime");
		str.append("~~");

		return str.toString();
	}

	public static Value strToValue(String str) {
		Value.Builder value = Value.newBuilder();
		ArrayList<String> list = tokenizer(str, "~~");

		if (list.size() < 17) {
			System.out.println("have some problem, the value to be converted is:" + str);
			return null;
		}

		value.setId(list.get(0));

		if (list.get(1).equals("noindegree")) {
			value.setInDegree(Long.parseLong(list.get(1)));
		}

		if (list.get(2).equals("noparents")) {
			ArrayList<String> parentVec = tokenizer(list.get(2), "@@@");
			for (int i = 0; i < parentVec.size(); i++) {
				value.addParents(parentVec.get(i));
			}
		}

		if (list.get(3).equals("nochildren")) {
			ArrayList<String> childVec = tokenizer(list.get(3), "@@@");
			for (int i = 0; i < childVec.size(); i++) {
				value.addChildren(childVec.get(i));
			}
		}

		if (list.get(4).equals("nodataname")) {
			ArrayList<String> dataNameVec = tokenizer(list.get(4), "@@@");
			for (int i = 0; i < dataNameVec.size(); i++) {
				value.addDataNameList(dataNameVec.get(i));
			}
		}

		if (list.get(5).equals("nodatasize")) {
			ArrayList<String> dataSizeList = tokenizer(list.get(5), "@@@");
			for (int i = 0; i < dataSizeList.size(); i++) {
				value.addDataSize(Long.parseLong(dataSizeList.get(i)));
			}
		}

		if (list.get(6).equals("noalldatasize")) {
			value.setAllDataSize(Long.parseLong(list.get(6)));
		}

		if (list.get(7).equals("notasklength"))
			value.setTaskLength(Long.parseLong(list.get(7)));

		if (list.get(8).equals("nonumtaskfin")) {
			value.setNumTaskFin(Long.parseLong(list.get(8)));
		}

		if (list.get(9).equals("nonumworksteal")) {
			value.setNumWorkSteal(Long.parseLong(list.get(9)));
		}

		if (list.get(10).equals("nonumworkstealfail")) {
			value.setNumWorkStealFail(Long.parseLong(list.get(10)));
		}

		if (list.get(11).equals("nonumtaskwait")) {
			value.setNumTaskWait(Integer.parseInt(list.get(11)));
		}

		if (list.get(12).equals("nonumtaskready")) {
			value.setNumTaskReady(Integer.parseInt(list.get(12)));
		}

		if (list.get(13).equals("nonumcoreavail")) {
			value.setNumCoreAvilable(Integer.parseInt(list.get(13)));
		}

		if (list.get(14).equals("nonumallcore")) {
			value.setNumAllCore(Integer.parseInt(list.get(14)));
		}

		if (list.get(15).equals("nooutputsize"))
			value.setOutputSize(Integer.parseInt(list.get(15)));

		if (list.get(16).equals("nosubmittime"))
			value.setSubmitTime(Long.parseLong(list.get(16)));
		return value.build();
	}

	/*required string msgType = 1;
	 optional string extraInfo = 2;
	 optional int64 count = 3;
	 repeated string tasks = 4;*/
	public static String mmToStr(MatrixMsg mm) {
		StringBuffer str = new StringBuffer("");
		str.append(mm.getMsgType());
		str.append("&&");

		if (mm.hasExtraInfo()) {
			str.append(mm.getExtraInfo());
		} else {
			str.append("noextrainfo");
		}
		str.append("&&");

		if (mm.hasCount()) {
			str.append(mm.getCount());
		} else {
			str.append("nocount");
		}
		str.append("&&");
		
		//TODO: not sure, it was task_size, used getTasksCount
		if (mm.getTasksCount() > 0) {
			for (int i = 0; i < mm.getTasksCount(); i++) {
				str.append(mm.getTasks(i));
				str.append("!!");
			}
		} else {
			str.append("notask");
		}
		str.append("&&");

		return str.toString();
	}

	public static MatrixMsg strToMm(String str) {
		ArrayList<String> list = tokenizer(str,"&&");
		MatrixMsg.Builder mm = MatrixMsg.newBuilder();
		mm.setMsgType(list.get(0));

		if (list.get(1).equals("noextrainfo")) {
			mm.setExtraInfo(list.get(1));
		}
		if (list.get(2).equals("nocount")) {
			mm.setCount(Integer.parseInt(list.get(2)));
		}
		if (list.get(3).equals("notask")) {
			ArrayList<String> taskList = tokenizer(list.get(3), "!!");
			for (int i = 0; i < taskList.size(); i++) {
				mm.addTasks(taskList.get(i));
			}
		}

		return mm.build();
	}
	
}
