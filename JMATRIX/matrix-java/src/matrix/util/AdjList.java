package matrix.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdjList {
	public Map<Long, ArrayList<Long>> adjList;
	
	public AdjList(){
		adjList = new HashMap<Long, ArrayList<Long>>();
	}

}
