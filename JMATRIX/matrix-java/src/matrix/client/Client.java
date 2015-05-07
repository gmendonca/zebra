package matrix.client;

import java.io.IOException;

import matrix.util.AdjList;
import matrix.util.InDegree;
import matrix.util.Tools;

public class Client {

	
	public static void main(String args[]){
		
		if (args.length != 1) {
			System.out.println("The usage is: client\tconfiguration_file!\n");
			System.exit(1);
		}
		
		System.loadLibrary("cpp_zhtclient");
		
		MatrixClient mc = null;
		
		try {
			String configFile = args[0];
			 mc = new MatrixClient(configFile);
		} catch (IOException e) {
			System.out.println("The usage is: client\tconfiguration_file!\n");
			e.printStackTrace();
			System.exit(1);
		}
		
		if(mc == null) return;
		
		/* generate task dag adjecent list (children) */
		AdjList dagAdjList = new AdjList();
		Tools.genDagAdjList(dagAdjList, mc.config.dagType, mc.config.dagArg, mc.config.numTaskPerClient);

		/* calculate indegrees (number of parents) for every tasks */
		InDegree dagInDegree = new InDegree();
		Tools.genDagInDegree(dagAdjList, dagInDegree);
		//adjList dagParentList;
		//gen_dag_parents(dagAdjList, dagParentList);

		/* wait until all schedulers have registered to ZHT */

		System.out.println("------------------------------------------------------------");
		System.out.println("Now, I am waiting until all the schedulers are running!");

		try {
			mc.clientLogOS.newLine();
			mc.clientLogOS.write("------------------------------------------------------------");
			mc.clientLogOS.write("Now, I am waiting until all the schedulers are running!");
		} catch (IOException e) {
			e.printStackTrace();
		}


		long startTime = System.currentTimeMillis();
		mc.waitAllScheduler();

		long stopTime = System.currentTimeMillis();
		long diff = stopTime - startTime;

		
		System.out.println("It took " + diff + "ms");
		System.out.println("------------------------------------------------------------");

		try {
			mc.clientLogOS.newLine();
			mc.clientLogOS.write("It took " + diff + "ms");
			mc.clientLogOS.write("------------------------------------------------------------");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* insert the task information to ZHT */
		mc.insertTaskInfoToZHT(dagAdjList, dagInDegree);

		/* initalize tasks by assigning taskId information to each task */
		mc.initTask();

		/* submit tasks to the schedulers */
		mc.submitTask();

		/* do the monitoring to watch th executing progress */
		mc.doMonitoring();
		
		
		
	}
}
