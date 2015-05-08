package matrix.util;

import java.util.ArrayList;
import java.util.Map;

public class Declarations {
	
	Map<Long, ArrayList<Long>> adjList;
	Map<Long, Long> inDegree;
	
	public final static int BUF_SIZE = 64;
	public static final Boolean ZHT_STORAGE = true;
	public static final Boolean DATA_CACHE = true;
}
