package matrix.util;

import java.util.ArrayList;

public class Value {
	private String taskId;
	private long inDegree;
	private ArrayList<String> children;
	
	public Value(){
		children = new ArrayList<String>();
	}
	
	public String getTaskId() {
		return taskId;
	}
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public long getInDegree() {
		return inDegree;
	}
	
	public void setInDegree(long inDegree) {
		this.inDegree = inDegree;
	}
	
	public ArrayList<String> getChildren() {
		return children;
	}
	
	public void setChildren(ArrayList<String> children) {
		this.children = children;
	}
	
	public void addChild(String child){
		children.add(child);
	}

}
