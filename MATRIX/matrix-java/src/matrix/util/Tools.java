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
import java.util.List;

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
	
	public static void genDagAdjList(AdjList dagAdjList, String dagType, long dagArg, long numTask){
		
	}
	
	public static void genDagInDegree(AdjList dagAdjList, InDegree dagInDegree){
		
	}
	
	public static ArrayList<String> tokenizer(String source){
		return (source.isEmpty()) 
				? new ArrayList<String>() 
				: (ArrayList<String>) Arrays.asList(source.split(" "));
		
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
	
}
