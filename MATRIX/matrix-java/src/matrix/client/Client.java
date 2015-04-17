package matrix.client;

import java.io.IOException;
import java.nio.charset.Charset;

import matrix.util.AdjList;
import matrix.util.Tools;

public class Client {

	
	public static void main(String args[]){
		
		if (args.length != 2) {
			System.out.println("The usage is: client\tconfiguration_file!\n");
			System.exit(1);
		}
		
		
		MatrixClient mc;
		
		try {
			String configFile = Tools.readFile(args[1],Charset.defaultCharset());
			 mc = new MatrixClient(configFile);
		} catch (IOException e) {
			System.out.println("The usage is: client\tconfiguration_file!\n");
			System.exit(1);
		}
		
		/* generate task dag adjecent list (children) */
		AdjList dagAdjList;
		generateDagAdjList(dagAdjList, mc.config.dagType,
				mc.config.dagArg, mc.config.numTaskPerClient);

		/* calculate indegrees (number of parents) for every tasks */
		InDegree dagInDegree;
		gen_dag_indegree(dagAdjList, dagInDegree);
		//adjList dagParentList;
		//gen_dag_parents(dagAdjList, dagParentList);

		/* wait until all schedulers have registered to ZHT */

		System.out.println("--------------------------------" + "\n----------------------------");
		System.out.println("Now, I am waiting until all the schedulers are running!");


		if (mc.clientLogOS.isOpen()) {
			System.out.println("--------------------------------" + "\n----------------------------");
			//mc.clientLogOS = "Now, I am waiting until all the schedulers are running!";
		}


		long startTime = System.currentTimeMillis();
		mc.waitAllScheduler();

		long stopTime = System.currentTimeMillis();
		long diff = stopTime - startTime;

		
		System.out.println("It took " + diff.tv_sec + "s, and " + diff.tv_nsec + " ns");
		System.out.println("--------------------------------" + "\n----------------------------");


		if (mc.clientLogOS.isOpen()) {
			//mc.clientLogOS << "It took " << diff.tv_sec << "s, and "
			//		<< diff.tv_nsec << " ns" << endl;
			//mc->clientLogOS << "--------------------------------"
			//		"----------------------------" << endl;
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
