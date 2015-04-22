package matrix.util;

import java.util.HashMap;
import java.util.Map;

public class InDegree {
	
	private Map<Long, Long> inDegree;
	
	public InDegree(){
		inDegree = new HashMap<Long, Long>();
	}

	public Long get(Long l) {
		return inDegree.get(l);
	}
	
	public void put(Long key, Long value){
		inDegree.put(key, value);
	}
}
