package matrix.util;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Tools {
	
	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	public static ArrayList<String> readWorkloadFromFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		//return new String(encoded, encoding);
		return new ArrayList<String>();
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
		return null;
	}
}
