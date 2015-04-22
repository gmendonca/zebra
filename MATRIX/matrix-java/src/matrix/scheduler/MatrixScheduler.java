package matrix.scheduler;

import java.io.IOException;

import matrix.protocol.Metamatrix.MatrixMsg;
import matrix.util.MatrixEpollServer;

public class MatrixScheduler extends PeerScheduler{

	public MatrixScheduler(String configFile) throws IOException {
		super(configFile);
		// TODO Auto-generated constructor stub
	}
	
	/* processing requests received by the epoll server */
	public int procReq(int sockfd, String buf) {
		String bufStr = new String(buf);
		/* this is client submitting tasks */
		String prefix = "client send tasks";
		if (bufStr.substring(0, prefix.length()) == prefix) {
			recvTaskFromClient(bufStr, sockfd);
		} else {
			MatrixMsg mm;

			mm = str_to_mm(bufStr);

			long increment = 0;

			/* message type is stored in pkg.virtualpath(), and contents
			 * are stored in pkg.readfullpath() */
			String msg = mm.msgtype();
			if (msg.equals("query load")) { 	// thief querying load
				int load = wsQueue.size();
				MatrixMsg mmLoad;
				mmLoad.set_msgtype("send load");
				mmLoad.set_count(load);
				String strLoad = mm_to_str(mmLoad);
				send_bf(sockfd, strLoad);
			} else if (msg.compare("steal task") == 0) {	// thief steals tasks
				send_task(sockfd);
			} else if (msg.compare("scheduler push task") == 0) {
				recv_pushing_task(mm, sockfd);
			} else if (msg.compare("scheduler require data") == 0) {
				String dataPiece;
				ldMutex.lock();

				if (localData.find(mm.extrainfo()) == localData.end()) {
					//cout << "What is the hell!" << endl;
					dataPiece = "shit, that is wrong!";
				} else {
					dataPiece = localData.find(mm.extrainfo())->second;
				}
				ldMutex.unlock();

				MatrixMsg mmDataPiece;
				mmDataPiece.set_msgtype("scheduler send data");
				mmDataPiece.set_extrainfo(dataPiece);
				String dataStr = mm_to_str(mmDataPiece);
				//mmDataPiece.SerializeAsString();
				//send_bf(sockfd, dataStr);
				send_big(sockfd, dataStr);
			}
		}
		close(sockfd);
		return 1;
	}

	/* fork epoll server thread */
	public void forkEsThread() {
		MatrixEpollServer mes = new MatrixEpollServer(config.schedulerPortNo, this);
		
		Thread t = new Thread(new Runnable() { public void run() { 
				mes.serve();
			}});
		
		t.start();
	}

	/* reset the bitmap of neighbors chosen, "false"
	 * means hasn't been chosen */
	void resetChoosebm() {
		for (int i = 0; i < schedulerVec.size(); i++) {
			chooseBitMap[i] = false;
		}
	}

	/* choose candidate neighbors to steal tasks,
	 * for simplicity, we randomly choose neighbors
	 * */
	void chooseNeigh() {
		int idx = -1;
		for (int i = 0; i < numNeigh; i++) {
			idx = rand() % schedulerVec.size();
			while (idx == get_index() || chooseBitMap[idx]) {
				idx = rand() % schedulerVec.size();
			}
			neighIdx[i] = idx;
			chooseBitMap[idx] = true;
			//cout << "The neighbor to choose is:" << idx << endl;
		}

		reset_choosebm();
	}

	/* find the neighbor with the maximum load by quering
	 * the load information of each scheduler one by one
	 * */
	void findMostLoadedNeigh() {
		MatrixMsg mm;
		mm.set_msgtype("query load");

		String strLoadQuery = mm_to_str(mm);

		long load = -1;

		for (int i = 0; i < numNeigh; i++) {
			String result;
			sockMutex.lock();
			int sockfd = send_first(schedulerVec.at(neighIdx[i]),
					config.schedulerPortNo, strLoadQuery);
			recv_bf(sockfd, result);

			close(sockfd);
			sockMutex.unlock();
			if (result.empty()) {
				continue;
			}
			MatrixMsg mmLoad = str_to_mm(result);

			load = mmLoad.count();
			if (maxLoad < load) {
				maxLoad = load;
				maxLoadedIdx = neighIdx[i];
			}
		}
	}

	/* receive several tasks (numTask) from another scheduler */
	public Boolean recvTaskFromScheduler(int sockfd) {
		String taskStr;
		recv_mul(sockfd, taskStr);

		String taskStrLs = taskStr.substr(0, taskStr.length() - 1);

		vector<String> stealVec = tokenize(taskStrLs, "##");
		if (stealVec.size() == 1) {
			return false;
		}

		MatrixMsg mmNumTask = str_to_mm(stealVec.at(0));
		int numTask = mmNumTask.count();

		for (int i = 1; i < stealVec.size(); i++) {
			MatrixMsg mm = str_to_mm(stealVec.at(i));

			vector<TaskMsg> tmVec;
			String time = num_to_str<long>(get_time_usec());

			for (long j = 0; j < mm.count(); j++) {
				tmVec.push_back(str_to_taskmsg(mm.tasks(j)));
			}

			tteMutex.lock();
			for (long j = 0; j < mm.count(); j++) {
				taskTimeEntry.push_back(
						tmVec.at(j).taskid() + "\tWorkStealQueuedTime\t" + time);
			}
			tteMutex.unlock();

			wsqMutex.lock();
			for (long j = 0; j < mm.count(); j++) {
				wsQueue.push(tmVec.at(j));
			}
			wsqMutex.unlock();
		}

		return true;
	}

	/* try to steal tasks from the most-loaded neighbor. The thief first
	 * sends a message ("steal tasks") to the neighbor, and then waits
	 * for the neighbor's response. The neighbor first sends a message
	 * notifying how many tasks could be migrated, then sends all the
	 * tasks batch by batch.
	 * */
	public Boolean stealTask() {
		/* if no neighbors have ready tasks */
		if (maxLoad <= 0) {
			return false;
		}

		MatrixMsg mm;
		mm.set_msgtype("steal task");
		//String strStealTask = mm.SerializeAsString();
		String strStealTask = mm_to_str(mm);

		String numTaskPkgStr;
		//cout << "OK, before sending stealing task message!" << endl;
		sockMutex.lock();
		//cout << "OK, I am sending stealing task message!" << endl;
		int sockfd = send_first(schedulerVec.at(maxLoadedIdx),
				config.schedulerPortNo, strStealTask);

		bool ret = recv_task_from_scheduler(sockfd);
		close(sockfd);
		sockMutex.unlock();
		return ret;
	}

	/* fork work stealing thread */
	public void forkWsThread() {
		if (config.workStealingOn == 1) {
			pthread_t wsThread;
			while (pthread_create(&wsThread, NULL, workstealing, this) != 0) {
				sleep(1);
			}
		}
	}

	/* executing a task. A task's specification has several fields:
	 * taskId, users, directory, command and arguments. They are
	 * delimited with space. After a task is done, move it to the
	 * complete queue.
	 * */
	public void execTask(TaskMsg tm) {
		String taskDetail;
		sockMutex.lock();
		zc.lookup(tm.taskid(), taskDetail);
		sockMutex.unlock();
		Value value = str_to_value(taskDetail);

		long startTime = get_time_usec();

		String data("");

	//#ifdef ZHT_STORAGE
		String dataPiece;
		for (int i = 0; i < value.parents_size(); i++)
		{
			zc.lookup(value.datanamelist(i), dataPiece);
			data += dataPiece;
		}
	//#else
		for (int i = 0; i < value.parents_size(); i++) {
			if (value.datasize(i) > 0) {
				if (value.parents(i).compare(get_id()) == 0) {
					ldMutex.lock();
					//data += "what ever!";
					data += localData.find(value.datanamelist(i))->second;
					//cout << tm.taskid() << " find the data" << endl;
					ldMutex.unlock();
				} else {
					bool dataReq = true;
					if (cache) {
						ldMutex.lock();
						if (localData.find(value.datanamelist(i))
								!= localData.end()) {
							data += localData.find(value.datanamelist(i))->second;
							dataReq = false;
						} else {
							dataReq = true;
						}
						ldMutex.unlock();
					}
					if (dataReq) {
						MatrixMsg mm;
						mm.set_msgtype("scheduler require data");
						//cout << "The require data is:" << value.datanamelist(i) << endl;
						mm.set_extrainfo(value.datanamelist(i));
						String mmStr = mm_to_str(mm);
						//mmStr = mm.SerializeAsString();
						//cout << tm.taskid() << "\trequires " << i << "\tdata!" << endl;

						sockMutex.lock();
						int sockfd = send_first(value.parents(i),
								config.schedulerPortNo, mmStr);
						//sockMutex.unlock();

						//cout << tm.taskid() << "\tit takes " << diff.tv_sec << "s, and " << diff.tv_nsec
						//		<< "ns to send the " << i << "\tdata to scheduler " << value.parents(i) << endl;

						String dataPiece;
						//recv_bf(sockfd, dataPiece);
						recv_big(sockfd, dataPiece);
						close(sockfd);
						sockMutex.unlock();
						//cout << tm.taskid() << "\tit takes " << diff.tv_sec << "s, and " << diff.tv_nsec
						//		<< "ns to receive the " << i << "\tdata from scheduler " << value.parents(i) << endl;
						MatrixMsg mmData = str_to_mm(dataPiece);
						//cout << "The data piece is:" << dataPiece << ", task id is:" << tm.taskid() << ", before pasre!" << endl;
						//mmData.ParseFromString(dataPiece);
						//cout << "After parse, extra info is:" << mmData.extrainfo() << endl;
						data += mmData.extrainfo();
						if (cache) {
							ldMutex.lock();
							localData.insert(
									make_pair(value.datanamelist(i),
											mmData.extrainfo()));
							ldMutex.unlock();
						}
					}
				}
			}
		}
	//#endif

		
		String execmd = tm.cmd().c_str();
		String result = exec(execmd);
		String key = get_id() + tm.taskid();

	//#ifdef ZHT_STORAGE
		//sockMutex.lock();
		zc.insert(key, result);
		//sockMutex.unlock();
	//#else
		ldMutex.lock();
		localData.insert(make_pair(key, result));
		//cout << "key is:" << key << ", and value is:" << result << endl;
		ldMutex.unlock();
	//#endif

		long finTime = get_time_usec();
		tteMutex.lock();
		taskTimeEntry.push_back(
				tm.taskid() + "\tStartTime\t" + num_to_str<long>(startTime));
		taskTimeEntry.push_back(
				tm.taskid() + "\tFinTime\t" + num_to_str<long>(finTime));
		tteMutex.unlock();

		cqMutex.lock();
		//completeQueue.push_back(CmpQueueItem(tm.taskid(), key, result.length()));
		completeQueue.push_back(CmpQueueItem(tm.taskid(), key, value.outputsize()));
		cqMutex.unlock();

		numTaskFinMutex.lock();
		numTaskFin++;
		//cout << tm.taskid() << "\tNumber of task fin is:" << numTaskFin << endl;
		numTaskFinMutex.unlock();

		ZHTMsgCountMutex.lock();
		incre_ZHT_msg_count(1);
		ZHTMsgCountMutex.unlock();
	}

	/* forking execute task threads. The number of executing threads is
	 * given by the configuration file, and it is usually eaqual to the
	 * number of cores a machine has.
	 * */
	public void forkExecTaskThread() {
		pthread_t *execThread = new pthread_t[config.numCorePerExecutor];

		for (int i = 0; i < config->numCorePerExecutor; i++) {
			while (pthread_create(&execThread[i], NULL, executing_task, this) != 0) {
				sleep(1);
			}
		}
	}

	public int taskReadyProcess(Value valuePkg, TaskMsg tm) {
		/* flag = 0, keep it in the work stealing queue
		 * flag = 1, keep it in the local queue
		 * flag = 2, push it to other scheduler's local queue
		 * */
		int flag = 2;

	//#ifdef ZHT_STORAGE
		tm.set_datalength(valuePkg.alldatasize());
		flag = 0;
	//#else
		if (valuePkg.alldatasize() <= config.dataSizeThreshold) {
			tm.set_datalength(valuePkg.alldatasize());
			flag = 0;
		} else {
			long maxDataSize = -1000000;
			String maxDataScheduler, key;
			for (int i = 0; i < valuePkg.datasize_size(); i++) {
				if (valuePkg.datasize(i) > maxDataSize) {
					maxDataSize = valuePkg.datasize(i);
					maxDataScheduler = valuePkg.parents(i);
					key = valuePkg.datanamelist(i);
				}
			}
			tm.set_datalength(maxDataSize);
			if (maxDataScheduler.compare(get_id()) == 0) {
				flag = 1;
			} else {
				bool taskPush = true;
				if (cache) {
					ldMutex.lock();
					if (localData.find(key) != localData.end()) {
						flag = 1;
						taskPush = false;
					} else {
						taskPush = true;
					}
					ldMutex.unlock();
				}
				if (taskPush) {
					MatrixMsg mm;
					mm.set_msgtype("scheduler push task");
					mm.set_count(1);
					mm.add_tasks(taskmsg_to_str(tm));
					//String mmStr = mm.SerializeAsString();
					String mmStr = mm_to_str(mm);
					sockMutex.lock();
					int sockfd = send_first(maxDataScheduler,
							config.schedulerPortNo, mmStr);
					//sockMutex.unlock();
					String ack;
					recv_bf(sockfd, ack);
					close(sockfd);
					sockMutex.unlock();
					flag = 2;
				}
			}
		}
	//#endif

		return flag;
	}
	/* check to see whether a task is ready to run or not. A task is
	 * ready only if all of its parants are done (the indegree counter
	 * equals to 0).
	 * */
	Boolean checkReadyTask(TaskMsg tm) {
		String taskDetail;
		bool ready = false;
		sockMutex.lock();
		zc.lookup(tm.taskid(), taskDetail);
		sockMutex.unlock();
		Value value = str_to_value(taskDetail);
		//cout << "task indegree:" << tm.taskid() << "\t" << value.indegree() << endl;

		if (value.indegree() == 0) {
			ready = true;
			int flag = task_ready_process(value, tm);
			if (flag != 2) {
				tteMutex.lock();
				taskTimeEntry.push_back(
						tm.taskid() + "\tReadyQueuedTime\t"
								+ num_to_str<long>(get_time_usec()));
				tteMutex.unlock();

			}
			if (flag == 0) {
				wsqMutex.lock();
				wsQueue.push(tm);
				wsqMutex.unlock();
			} else if (flag == 1) {
				lqMutex.lock();
				localQueue.push(tm);
				lqMutex.unlock();
			}
		}

		return ready;
	}

	

	/* fork check ready task thread */
	void forkCrtThread() {
		pthread_t crtThread;
		//cout << "The number of waiting task is:" << waitQueue.size() << endl;
		//cout << "The first one is:" << waitQueue.front().taskid() << endl;
		while (pthread_create(&crtThread, NULL, checking_ready_task, this) != 0) {
			sleep(1);
		}
	}

	/* decrease the indegree of a task by one, because one of
	 * its parents has been done.
	 * */
	public long notifyChildren(CmpQueueItem cqItem) {
		String taskDetail;
		long increment = 0;
		sockMutex.lock();
		//cout << "I got the lock, and I am notifying children!" << endl;
		zc.lookup(cqItem.taskId, taskDetail);
		//cout << "OK, the task id is:" << cqItem.taskId << ", and task detail is:" << taskDetail << endl;
		sockMutex.unlock();
		if (taskDetail.empty()) {
			cout << "I am notifying a children, that is insane:" << cqItem.taskId
					<< endl;
		}
		Value value = str_to_value(taskDetail);

		increment++;
		String childTaskId, childTaskDetail, childTaskDetailAttempt, query_value;
		Value childVal;

		//cout << "task finished, notify children:" << cqItem.taskId << "\t" << taskDetail << "\tChildren size is:" << value.children_size() << endl;
		for (int i = 0; i < value.children_size(); i++) {
			childTaskId = value.children(i);
			sockMutex.lock();
			zc.lookup(childTaskId, childTaskDetail);
			//cout << "The child task id is:" << childTaskId << "\t" << childTaskDetail << endl;
			//cout << "The size is:" << childTaskDetail.length() << endl;
			sockMutex.unlock();
			increment++;
			if (taskDetail.empty()) {
				cout << "I am notifying a children, that is insane:"
						<< cqItem.taskId << endl;
			}
			childVal = str_to_value(childTaskDetail);
			childVal.set_indegree(childVal.indegree() - 1);
			childVal.add_parents(get_id());
			childVal.add_datanamelist(cqItem.key);
			childVal.add_datasize(cqItem.dataSize);
			childVal.set_alldatasize(childVal.alldatasize() + cqItem.dataSize);
			childTaskDetailAttempt = value_to_str(childVal);

			//cout << cqItem.taskId << "\t" << childTaskId << "\t" << childTaskDetail << "\t" << childTaskDetailAttempt << endl;
			increment++;
			sockMutex.lock();
			while (zc.compare_swap(childTaskId, childTaskDetail,
					childTaskDetailAttempt, query_value) != 0) {
				if (query_value.empty()) {
					zc.lookup(childTaskId, childTaskDetail);
					increment++;
				} else {
					//cout << "The query_value is:" << query_value << endl;
					childTaskDetail = query_value;
				}
				childVal = str_to_value(childTaskDetail);
				childVal.set_indegree(childVal.indegree() - 1);
				childVal.add_parents(get_id());
				childVal.add_datanamelist(cqItem.key);
				childVal.add_datasize(cqItem.dataSize);
				childVal.set_alldatasize(childVal.alldatasize() + cqItem.dataSize);
				childTaskDetailAttempt = value_to_str(childVal);
				increment++;
			}
			sockMutex.unlock();
		}

		return increment;
	}


	/* fork check complete queue tasks thread */
	void forkCctThread() {
		pthread_t cctThread;

		while (pthread_create(&cctThread, NULL, checking_complete_task, this) != 0) {
			sleep(1);
		}
	}
	

	/* fork recording status thread */
	public void forkRecordStatThread() {
		pthread_t rsThread;

		while (pthread_create(&rsThread, NULL, recording_stat, this) != 0) {
			sleep(1);
		}
	}

	public void forkRecordTaskThread() {
		pthread_t trThread;

		while (pthread_create(&trThread, NULL, record_task_time, this) != 0) {
			sleep(1);
		}
	}

	public void forkLocalQueueMonitorThread() {
		pthread_t lqMonThread;

		while (pthread_create(&lqMonThread, NULL, localQueue_monitor, this) != 0) {
			sleep(1);
		}
	}



}
