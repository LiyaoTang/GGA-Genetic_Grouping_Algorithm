package com.fay.CMS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fay.util.ListHelper;
import com.fay.domain.Cell;
import com.fay.domain.CellSet;
import com.fay.domain.Job;
import com.fay.domain.JobSet;
import com.fay.domain.Machine;
import com.fay.domain.MachineSet;
import com.fay.domain.Operation;
import com.fay.domain.Vehicle;
import com.fay.domain.VehicleSet;

public class CMSReader {
	MachineSet machineSet = new MachineSet();
	JobSet jobSet = new JobSet();
	CellSet cellSet = new CellSet();
	VehicleSet vehicleSet = new VehicleSet();

	public CMSReader(String filename) {
		File file = new File(filename);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			String[] seq = line.split("\\s++");

			int jobNum = Integer.parseInt(seq[0]);
			int machNum = Integer.parseInt(seq[1]);
			int cellNum = Integer.parseInt(seq[2]);
			int vehicleSize = Integer.parseInt(seq[3]);
			
			for(int cellNo = 0; cellNo < cellNum; cellNo++){
				Cell c = new Cell(cellNo+1, "C" + (cellNo+1));
				cellSet.addCell(c);
			}
			for(int vehicleNo = 0; vehicleNo < cellNum; vehicleNo++){
				Vehicle v = new Vehicle(vehicleNo+1, "V" + (vehicleNo+1), vehicleSize);
				vehicleSet.addVehicle(v);
			}

			/** 每个Cell 对应一个 Vehicle */
			for(Cell cell : cellSet){
				cell.setCellVehicle(vehicleSet.get(cell.GetID()));
			}

			line = reader.readLine();
			seq = line.split("\\s+");

			int numInCell[];
			numInCell = new int[machNum]; /** Cell# for each machine */
			for (int mi = 0; mi < machNum; mi++) {
				numInCell[mi] = Integer.parseInt(seq[mi]);
			}

			for (int machNo = 0; machNo < machNum; machNo++) { /** add machines into machine set */
				Machine m = new Machine(machNo + 1, "M" + (machNo + 1));
				machineSet.add(m);
			}
			for (int mi = 0; mi < machNum; mi++) {
				machineSet.get(mi).setNumInCell(numInCell[mi]+1);
				machineSet.get(mi).setCell(cellSet.get(numInCell[mi]));
			}

			for(Cell cell : cellSet){
				cell.setCellMachineSet(machineSet);
			}

			for (int jobNo = 0; jobNo < jobNum; jobNo++) {
				line = reader.readLine();
				seq = line.split("\\s+");
				int operNum = Integer.parseInt(seq[0]);
				double weight = Double.parseDouble(seq[1]);

				Job job = new Job(jobNo + 1, "J" + (jobNo + 1));
				jobSet.addJob(job);
				job.setOperationNum(operNum);
				job.setWeight(weight);

				/** 一个工件的操作 [Op#][Mach#]:=Time consumed */
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
						procTimeMap.put(machineSet.get(MachineIndex), processTime);
						MachineIndex++;
					}	
					operation.setProcTimes(procTimeMap);
					operation.setProcmachine();
					if (operation.getProcmachine() == null) {
						throw new NullPointerException("operation.setProcmachine(); error!!!");
					}

					operation.setJob(job);

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

			/** transfer time from cell to cell */
			int[][] transferTime = new int[cellNum][cellNum];
			for (int i = 0; i < cellNum; i++) {
				line = reader.readLine();
				seq = line.split("\\s+");
				for (int j = 0; j < seq.length; j++) {
					transferTime[i][j] = Integer.parseInt(seq[j]);
				}
			}
			
			for(int i = 0; i < cellNum; i++){
				cellSet.get(i).setTransferTime(transferTime[i]);
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
		CMSReader dataSource = new CMSReader("data_simbatch1/case1.txt");
		System.out.println("success!");
		for(Job job : dataSource.jobSet){
			System.out.println("JobID:\t"+job.getId()+"\tDueDate:\t"+job.getCalculateDuedate());
		}
	}
	

}

