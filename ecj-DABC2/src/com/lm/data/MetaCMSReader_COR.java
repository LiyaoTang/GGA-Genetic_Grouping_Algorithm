package com.lm.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

//import com.lm.domain.Cell;                   //用于产生规则时
//import com.lm.domain.CellSet;
//import com.lm.domain.Job;
//import com.lm.domain.JobSet;
//import com.lm.domain.Machine;
//import com.lm.domain.MachineSet;
//import com.lm.domain.Operation;
import com.lm.Metadomain.Cell;			//用于元启发调度
import com.lm.Metadomain.CellSet;
import com.lm.Metadomain.Job;
import com.lm.Metadomain.JobSet;
import com.lm.Metadomain.Machine;
import com.lm.Metadomain.MachineSet;
import com.lm.Metadomain.Operation;
import com.lm.util.Constants;
import com.lm.util.ListHelper;

public class MetaCMSReader_COR {
	private MachineSet machineSet;
	private JobSet jobSet;
	private CellSet cellSet;
	private ArrayList<Integer>[] machines;
	
	/**
	 * @Description constructor
	 * @param filename
	 * @exception:
	 */
	public MetaCMSReader_COR(String filename1,String filename2) {
		File file = new File(filename1);
		Scanner sc = null;
		try {
			sc = new Scanner(new File(filename1)); 
			machineSet     			  = new MachineSet();
			jobSet 		   			  = new JobSet();
			cellSet 	   			  = new CellSet();
			
			//单元内机器数
			Constants.CellForm = new HashMap<Integer, Integer>();   // layout of cells
			String line;	
			line = sc.nextLine();
			String[] num = line.split("\\s+");
			
			int machNum = 1;
			int cellNum = num.length;
			int transportorSize = cellNum;
			
			for (int cellIndex = 0; cellIndex < num.length; cellIndex++) {
				int machinenum = Integer.parseInt(num[cellIndex]);
				//init machine 
				for (int j = 0; j < machinenum; j++) {
					Machine m = new Machine(machNum, "M" + machNum, 1);
					m.setCellID(cellIndex+1);
					Constants.CellForm.put(machNum, cellIndex+1);
					machineSet.add(m);
					machNum++;
				}
			}
			
			// 初始化单元信息&&机器对象
			Constants.MachineToParts  = new int[machNum+1][];
			Constants.CellToNextCells = new int[cellNum+1][];
			Constants.CellToParts	  = new ArrayList[cellNum+1][cellNum+1];
			for(int i = 1; i < cellNum+1; i++){
				for(int j = 1; j < cellNum+1; j++){
					Constants.CellToParts[i][j] = new ArrayList<Integer>();
				}
			}
			machines	   			  = new ArrayList[machNum+1];
			for(int i = 0; i <=machNum; i++){
				machines[i] = new ArrayList<Integer>();
			}

			// 单元间的转移关系-- 主对角上是0，其余均为1
			for (int i = 0; i < cellNum; i++) {
				Cell c=new Cell(i + 1, cellNum, transportorSize);
				c.setCellLink(cellNum);
				cellSet.addCell(c);
				AddToCellMessage(i,c);
			}
			
			// 固定familyNum 为 5  每个工件的family固定为2
			int familyNum = 5;
			Constants.setupTime = new int[familyNum][machNum + 1];
			for (int i = 0; i < familyNum; i++) {
				for (int j = 1; j <= machNum; j++) {
					//setup 均为0
					Constants.setupTime[i][j] = 0;
				}
			}
			
			int jobNum = 1;
			int operNo = 0;
			Vector<Integer> PreCells   = new Vector<Integer>();
			Vector<Integer> CurCells   = new Vector<Integer>();
			List<Operation> operations = new ArrayList<Operation>();
			
			//第一个工件对象的初始化
			Job job = new Job(jobNum, "J" + jobNum);
			jobSet.addJob(job);
			job.setFamily(2);
			
			//读取加工工序信息
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				if ("".equals(line)) {
					
					//set 上一个工件的operations
					job.setOperations(operations);
					
					//new 新的工件对象，重新开始统计内容
					PreCells =new Vector<Integer>();
					CurCells =new Vector<Integer>();
					operations = new ArrayList<Operation>();
					jobNum++;
					job = new Job(jobNum, "J" + jobNum);
					jobSet.addJob(job);
					job.setFamily(2);
					double weight = Math.random();  //random 0-1当中的一个数
					job.setWeight(weight); // 工件的权重
					operNo=0;
					continue;
				}
				//遍历当前工件的所有工序信息
				Map<Machine, Integer> procTimeMap = new HashMap<Machine, Integer>();
				int ProcessTimeForOperation = 0;
				String[] timeList = line.split("\\s+");
				for (int i = 0; i < timeList.length; i++) {
					if (!timeList[i].equals("-")) {
						int processTime = Integer.parseInt(timeList[i]);
						int machineIndex = i;
						AddToMachineMessage(jobNum,machineSet.get(machineIndex).getId());
						CurCells.add(Constants.CellForm.get(machineIndex+1));
						procTimeMap.put(machineSet.get(machineIndex),processTime);
						ProcessTimeForOperation+=processTime;
					}
				}
				//对operation进行相关记录
				Operation operation = new Operation(operNo, jobNum + "-" + (operNo + 1));
				operNo++;
				operation.setJob(job);
				operation.setProcTimes(procTimeMap);
				int histTime = ProcessTimeForOperation/procTimeMap.size();
				operation.setHistoryprocessingtime(histTime);
				Operation prev = ListHelper.getLast(operations);
				if (prev != null) {
					operation.setPrev(prev);
					prev.setNext(operation);
				}
				operations.add(operation);
				//对所在单元内容进行相关记录
				PreCells.clear();
				for(int cur:CurCells){
					PreCells.add(cur);
				}
				CurCells.clear();
			}//end while for 工序
			 
			//对于最后一个工件，set 它的operations
			job.setOperations(operations);
			
			//读取工件的arrivalTime&& transferTime 
			sc = new Scanner(new File(filename2));
			for (int i = 0; i < jobNum; i++) {
				line = sc.nextLine();
				String[] timeList = line.split("\\s+");
				jobSet.get(i).setRelDate(Integer.parseInt(timeList[0]));
				jobSet.get(i).setTransferTime(Integer.parseInt(timeList[1]));
				int time = 0;
				for (int j = 0; j < jobSet.get(i).getOperationNum(); j++) {
					time += jobSet.get(i).getOperation(j).getHistoryprocessingtime();
				}
				int duedate = (int) (jobSet.get(i).getRelDate() + 1.7 * time);
				jobSet.get(i).setDuedate(duedate);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		TransferMachineMessageArray();
	}

	/**
	 * @Description 添加每条路线上加工信息的信息
	 * @param jobNo
	 * @param machineNo
	 * @param preCells 
	 */
	private void AddpartsRoutesMessage(int jobNo, int machineNo, Vector<Integer> preCells) {
		int cur = Constants.CellForm.get(machineNo);
		
		for(int pre:preCells){
			if(cur!=pre){
				int i=0;
				for(;i <Constants.CellToParts[pre][cur].size();i++){
					if(Constants.CellToParts[pre][cur].get(i)==jobNo){ 
						break;
					}
				}
				if(i == Constants.CellToParts[pre][cur].size()){
					Constants.CellToParts[pre][cur].add(jobNo);
				}
			}
		}
	}

	/**
	 * @Description 添加机器可加工工件的信息
	 * @param jobNo
	 * @param MachineNo
	 */

		private void AddToMachineMessage(int jobNo, int MachineNo) {
		//transfer the machineid to the index in array
		int indexNo = MachineNo - 1; 
		//check if exists
		for(int i=0; i<machines[indexNo].size();i++){
			if(machines[indexNo].get(i) == jobNo) return;
		}
		//if not,add it
		machines[indexNo].add(jobNo);
	}


	/**
	 * @Description 添加单元可转移到的信息
	 * @param i
	 * @param c
	 */
	private void AddToCellMessage(int i,Cell c) {
		Constants.CellToNextCells[i+1] = new int[c.NextCell.size()+1];
		for(int j = 0 ; j < c.NextCell.size()+1; j++){
			if(j==0){
				Constants.CellToNextCells[i+1][j] =0;
			}
			else{
				Constants.CellToNextCells[i+1][j] = c.NextCell.get(j-1);
			}
		}
	}

	
	/**
	 * @Description Transfer the Vector message to Constants.MachineToParts
	 */
	public void TransferMachineMessageArray() {
		for(int MID = 1; MID < machines.length; MID++){
			Constants.MachineToParts[MID] = new int[machines[MID-1].size()+1];
			for(int curChar = 0; curChar < machines[MID-1].size()+1; curChar++){
//				Constants.MachineToParts[MID][curChar] = machines.get(MID-1).charAt(curChar)-'0';
				if(curChar==0){
					Constants.MachineToParts[MID][curChar] =0;
				}
				else{
					Constants.MachineToParts[MID][curChar] = machines[MID-1].get(curChar-1);
				}
			}
		}
	
	}
	
	/**
	 * @Description get machine set
	 * @return
	 */
	public MachineSet getMachineSet() {
		return machineSet;
	}

	/**
	 * @Description get job set
	 * @return
	 */
	public JobSet getJobSet() {
		return jobSet;
	}

	/**
	 * @Description get cell set
	 * @return
	 */
	public CellSet getCellSet() {
		return cellSet;
	}

	/**
	 * @Description print machine's information
	 */
	private void printMachineInfo() {
		for (Machine m : machineSet) {
			System.out.println("[machine]" + m.getName());
			System.out.println("id:" + m.getId() + " cell:" + m.getCellID()
					+ " capacity:" + m.getCapacity());
		}
	}

	/**
	 * @Description print job's information
	 */
	private void printJobInfo() {
		for (Job job : jobSet) {
			System.out.println("[job]" + job.getName());
			System.out.println("id:" + job.getId() + " weight:"
					+ job.getWeight() + " duedate:" + job.getDuedate()
					+ " releasetime:" + job.getRelDate() + " size:"
					+ job.getSize() + " transferTime:" + job.getTransferTime());
//			for (Operation operation : job) {
//				System.out.println("[operation]" + operation.getName());
//				for (Map.Entry<Machine, Integer> entry : operation
//						.getProcTimes().entrySet()) {
//					System.out.print(entry.getValue() + "@" + entry.getKey()
//							+ " ");
//				}
//				System.out.println();
//			}// End for operation
		}// End for job
	}

	public static void main(String[] args) {
		MetaCMSReader_COR dataSource = new MetaCMSReader_COR("data/Trans/COR/simple/OperMachMatrix11op_four",
															 "data/Trans/COR/simple/partTime");
		System.out.println("begin//--------------------------------------");
//		 dataSource.printMachineInfo();
		 dataSource.printJobInfo();
		System.out.println("success!//--------------------------------------");
	}

}
