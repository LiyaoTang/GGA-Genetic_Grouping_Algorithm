package com.fay.CMS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.fay.domain.Cell;
import com.fay.domain.CellSet;
import com.fay.domain.Job;
import com.fay.domain.JobSet;
import com.fay.domain.Machine;
import com.fay.domain.MachineSet;
import com.fay.domain.Operation;
import com.fay.domain.Vehicle;
import com.fay.domain.VehicleSet;
import com.fay.util.ListHelper;


public class MetaCMSReader {
	MachineSet machineSet = new MachineSet();
	JobSet jobSet = new JobSet();
	CellSet cellSet = new CellSet();
	VehicleSet vehicleSet = new VehicleSet();

	/**
	 * @Description constructor
	 * @param filename
	 * @exception:
	 */
	public MetaCMSReader(String filename) {
		File file = new File(filename);
		BufferedReader reader = null;
		try {
//			reader 		   			  = new BufferedReader(new FileReader(file));
//			machineSet     			  = new MachineSet();
//			jobSet 		   			  = new JobSet();
//			cellSet 	   			  = new CellSet();
			reader = new BufferedReader(new FileReader(file));

			String line;
			line = reader.readLine();
			String[] seq = line.split("\\s++");
			int machNum = Integer.parseInt(seq[0]);
			int jobNum = Integer.parseInt(seq[1]);
			int cellNum = Integer.parseInt(seq[2]);
			int familyNum = Integer.parseInt(seq[3]);
			int vehicleSize = Integer.parseInt(seq[4]);
			reader.readLine();

			for(int cellNo = 0; cellNo < cellNum; cellNo++){
				Cell c = new Cell(cellNo+1, "C" + (cellNo+1));
				cellSet.addCell(c);
			} //add cell

			for(int vehicleNo = 0; vehicleNo < cellNum; vehicleNo++){
				Vehicle v = new Vehicle(vehicleNo+1, "V" + (vehicleNo+1), vehicleSize);
				vehicleSet.addVehicle(v);
				v.setCell(cellSet.get(vehicleNo));
			}//add one vehicle into each cell

			for(Cell cell : cellSet){
				cell.setCellVehicle(vehicleSet.get(cell.GetID()));
			}


			line = reader.readLine();
			seq = line.split("\\s++");

			int numInCell[] = new int[machNum];
			for (int mi = 0; mi < machNum; mi++) {
				numInCell[mi] = Integer.parseInt(seq[mi]);
			}

			for (int machNo = 0; machNo < machNum; machNo++) {
				Machine m = new Machine(machNo + 1, "M" + (machNo + 1));
				machineSet.add(m);
			}// initialize machine into set
			for (int mi = 0; mi < machNum; mi++) {
				machineSet.get(mi).setNumInCell(numInCell[mi]);
				machineSet.get(mi).setCell(cellSet.get(numInCell[mi]-1));
			}// so that can find cell from machine

			//閿熸枻鎷烽敓绱篹ll閿熻妭纰夋嫹MachineSet
			for(Cell cell : cellSet){
				cell.setCellMachineSet(machineSet);
			}// so that can find belonging machines from cell


			reader.readLine();
			/** set transfer time from cell to cell */
			int[][] transferTime = new int[cellNum][cellNum];
			for (int i = 0; i < cellNum; i++) {
				line = reader.readLine();
				seq = line.split("\\s+");
				for (int j = 0; j < cellNum; j++) {
					transferTime[i][j] = Integer.parseInt(seq[j]); // /2;
					}
			}
			for(int i = 0; i < cellNum; i++){
				cellSet.get(i).setTransferTime(transferTime[i]);
			}

			// rubbish
			reader.readLine();
			for (int i = 0; i < cellNum; i++) reader.readLine();
			reader.readLine();

			// [family#][mach#] = setup time
			int [][] setupTime = new int[familyNum][machNum];
			for (int i = 0; i < familyNum; i++) {
				line = reader.readLine();
				seq = line.split("\\s+");
				for(int j = 0; j < machNum; j++)
					setupTime[i][j] = Integer.parseInt(seq[j]);
			}
			reader.readLine();


			// add job into set
			for (int jobNo = 0; jobNo < jobNum; jobNo++) {
				line = reader.readLine();
				seq = line.split("\\s++");
				int operNum = Integer.parseInt(seq[0]);
				int familyNo  = Integer.parseInt(seq[1]);
				int TightDueDate = Integer.parseInt(seq[2]);
				double weight = Double.parseDouble(seq[3]);

				Job job = new Job(jobNo + 1, "J" + (jobNo + 1));
				jobSet.addJob(job);

				job.setOperationNum(operNum);
				job.setWeight(weight);
				job.setDuedate(TightDueDate);

				List<Operation> operations = new ArrayList<Operation>();
				for (int operNo = 0; operNo < operNum; operNo++) {
					Operation operation = new Operation(operNo + 1, "O" + (operNo + 1));
					line = reader.readLine();
					seq = line.split("\\s+");
					int MachineIndex = 0;
					Map<Machine, Integer> procTimeMap = new HashMap<Machine, Integer>();
					for (int i = 0; i < seq.length; i++) {
						int processTime = Integer.parseInt(seq[i]);
						if (processTime == 0) {
							MachineIndex++;
							continue;
						}

						procTimeMap.put(machineSet.get(MachineIndex), processTime + setupTime[familyNo][MachineIndex]);
						MachineIndex++;
					}
					operation.setProcTimes(procTimeMap);// 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋椂閿熸枻鎷�
					operation.setProcmachine();//閿熸枻鎷烽敓鏂ゆ嫹鏉夊焊閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿燂拷
					if (operation.getProcmachine() == null) {
						throw new NullPointerException("operation.setProcmachine(); error!!!");
					}// 閿熸枻鎷峰焊閿熸枻鎷烽敓锟�

					operation.setJob(job);// 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟

					Operation prev = ListHelper.getLast(operations);
					if (prev != null) {
						operation.setPrev(prev);
						prev.setNext(operation);
					}
					operations.add(operation);


				}
				job.setOperations(operations);
			}

			for(Job job : jobSet){
				job.calculateDuedate();
			}


		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public MachineSet getMachineSet() {
		return machineSet;
	}

	public JobSet getJobSet() {
		return jobSet;
	}

	public CellSet getCellSet() {
		return cellSet;
	}

	public VehicleSet getVehicleSet() {
		return vehicleSet;
	}

	public void printMachineInfo() {
		for (Machine m : machineSet) {
			System.out.println("[machine]" + m.getName());
			System.out.println("id:" + m.getId() + " cell:" + m.getNumInCell());
		}
	}

	public void printJobInfo() {
		for (Job job : jobSet) {
			System.out.println("[job]" + job.getName());
			System.out.println("id:" + job.getId());
			List<Operation> operations = new ArrayList<Operation>();
			operations = job.getOperations();
			for (Operation operation : operations) {
				System.out.println("[operation]" + operation.getName());
				for (Map.Entry<Machine, Integer> entry : operation
						.getProcTimes().entrySet()) {
					System.out.print(entry.getValue() + "@"
							+ entry.getKey().getName() + " ");
				}
				System.out.println();
			}// End for operation
		}// End for job

	}

	public void printCellInfo(){
		for(Cell cell : cellSet){
			System.out.println("[cell]" + cell.getname());
			System.out.println("id:" + cell.GetID());
			System.out.println("transferTime:");
			int[] transferTime = cell.GetTransferTimes();
			for(int i = 0; i<transferTime.length; i++){
				System.out.println("cellId:"+i+" "+transferTime[i]);
			}
			for(Machine machine : cell.getCellMachine()){
				System.out.println("[cellMachine]" + machine.getName());
				System.out.println("id:" + machine.getId());
			}
		}
	}

	public static void main(String[] args) {
		MetaCMSReader dataSource = new MetaCMSReader("data_compare/EM_training/1");
	//	dataSource.printMachineInfo();
	//	dataSource.printJobInfo();
		System.out.println("success!");
		for(Job job : dataSource.jobSet){
			System.out.println("JobID:\t"+job.getId()+"\tDueDate:\t"+job.getDuedate());
		}
	}

}
