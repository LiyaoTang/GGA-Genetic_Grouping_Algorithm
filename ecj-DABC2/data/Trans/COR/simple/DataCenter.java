package scheduler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataCenter {

	//private String fileName1 = "resource/OperMachMatrix32p05cells";
	//private String fileName1 = "resource/OperMachMatrix32op_fifth6";
	//private String fileName1 = "resource/OperMachMatrix32op_mon5";
	//private String fileName1 = "resource/OperMachMatrix32op_mon12";
	//private String fileName1 = "resource/OperMachMatrix32op_tues17";
	//private String fileName2 = "resource/PartTime";
	
	//private String fileName1 = "resource/OperMachMatrix39";
	//private String fileName1 = "resource/OperMachMatrix39op_mon18";
	//private String fileName1 = "resource/OperMachMatrix39op_fifth15";
	//private String fileName1 = "resource/OperMachMatrix39op_com_plus4";
	//private String fileName1 = "resource/OperMachMatrix39op_com_plus3";
	//private String fileName2 = "resource/PartTime 39";
	//private String fileName1 = "resource/OperMachMatrix39op_com_thi";
	//private String fileName1 = "resource/OperMachMatrix39op_tues16";
	
	//private String fileName1 = "resource/OperMachMatrix46";
	//private String fileName1 = "resource/OperMachMatrix46op_mon9";
	//private String fileName1 = "resource/OperMachMatrix46op_tues5";
	//private String fileName2 = "resource/PartTime 46";
	
	//private String fileName1 = "resource/OperMachMatrix53";
	//private String fileName1 = "resource/OperMachMatrix53op_mon37";
	//private String fileName1 = "resource/OperMachMatrix53op_com_four4";
	//private String fileName2 = "resource/PartTime 53";
	//private String fileName1 = "resource/OperMachMatrix53op_com_thi";
	//private String fileName1 = "resource/OperMachMatrix53op_tues78";
	
	//private String fileName1 = "resource/OperMachMatrix72";
//	private String fileName1 = "resource/OperMachMatrix72op_five1";
	//private String fileName1 = "resource/OperMachMatrix72op_com_four14";
	//private String fileName1 = "resource/OperMachMatrix72op_5";
	//private String fileName1 = "resource/OperMachMatrix60_new1";
	//private String fileName1 = "resource/OperMachMatrix72op_mon1";
	//private String fileName1 = "resource/OperMachMatrix90op_tues165";
	private String fileName2 = "resource/PartTime 72";
	//private String fileName1 = "resource/OperMachMatrix25op_tues13";
	//private String fileName1 = "resource/OperMachMatrix60";
   // private String fileName1 = "resource/OperMachMatrix60op_mon5";
   // private String fileName1 = "resource/OperMachMatrix60op_com_plus3";
	//private String fileName1 = "resource/OperMachMatrix60op_tues64";
	//private String fileName2 = "resource/PartTime 60";
	//private String fileName1 = "resource/OperMachMatrix60op_tues";
	
	//private String fileName1 = "resource/zd1";
	//private String fileName2 = "resource/zdt";
	
	//private String fileName1 = "resource/OperMachMatrix25op_four6";
	//private String fileName1 = "resource/OperMachMatrix18op_mon11";
	private String fileName1 = "resource/OperMachMatrix4op_mon3";
//	private String fileName1 = "resource/OperMachMatrix25op_mon23";
	//private String fileName2 = "resource/PartTime";
	
	//private String fileName1 = "resource/OperMachMatrix11p04cells";
	//private String fileName1 = "resource/OperMachMatrix11op_mon6";
//	private String fileName2 = "resource/PartTime 11";
	
	//private String fileName1 = "resource/OperMachMatrix04p04cells";
	//private String fileName2 = "resource/PartTime 04";
	//private String fileName1 = "resource/OperMachMatrix60op_com";
//	private String fileName1 = "resource/OperMachMatrix60op_com_plus";
//	private String fileName2 = "resource/PartTime 60";


	private int machineNumber = 0;

	private int partNumber = 0;

	private int cellNumber = 0;

	private List<Part> parts = new ArrayList<Part>(); // 所有信息都在parts中

	private List<CellAgent> cells = new ArrayList<CellAgent>();

	public DataCenter() {
		int operationNum = 0;
		Scanner sc = null;
		try {
			sc = new Scanner(new File(fileName1)); // 读取第一个文件
		} catch (Exception e) {
			System.err.println(fileName1 + " does not exists!");
			System.exit(1);
		}

		String line = sc.nextLine(); // 读取首行单元信息
		String[] num = line.split("\\s+");
		for (int i = 0; i < num.length; i++) {
			CellAgent ca = new CellAgent(++cellNumber); // 创建新的CellAgent
			int machinenum = Integer.parseInt(num[i]);
			for (int j = 0; j < machinenum; j++) { // 创建多个Machine
				Machine m = new Machine(++machineNumber, cellNumber);
				ca.addMachine(m);
			}
			cells.add(ca);
		}

		Part p = new Part(++partNumber);
		parts.add(p);
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			//System.out.println();
			if ("".equals(line)) {
				p = new Part(++partNumber);
				parts.add(p);
				operationNum = 0;
				continue;
			}
			Operation op = new Operation(partNumber, ++operationNum); // 创建新的工序
			if (operationNum == 1) {
				op.setIsready(true); // 第一道工序可以被加工
				op.setAvailabletime(0);
			}
			String[] timeList = line.split("\\s+");
			if (machineNumber < timeList.length) {
				machineNumber = timeList.length;
			}
			for (int i = 0; i < timeList.length; i++) {
				try {
					if (!timeList[i].equals("-")) {
						int time = Integer.parseInt(timeList[i]);
						int machinecellId = 0;
						for (int j = 0; j < cells.size(); j++) { // 查找机器i+1所在的单元号
							if (cells.get(j).findMachine(i + 1)) {
								machinecellId = cells.get(j).getCellid();
								break;
							}
						}
						MachineTime mt = new MachineTime(i + 1, time,
								machinecellId); // 创建新的机器加工时间
						op.addMachineTime(mt);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			p.addOperation(op);
		}
		try {
			sc = new Scanner(new File(fileName2)); // 读取第二个文件
		} catch (Exception e) {
			System.err.println(fileName1 + " does not exists!");
			System.exit(1);
		}
		for (int i = 0; i < partNumber; i++) {
			line = sc.nextLine();
			String[] timeList = line.split("\\s+");
			// parts.get(i).setDuedate(Integer.parseInt(timeList[0]));
			parts.get(i).setArrivaltime(Integer.parseInt(timeList[0]));
			parts.get(i).setTransfertime(Integer.parseInt(timeList[1]));
			int time = 0;
			for (int j = 0; j < parts.get(i).getOperations().size(); j++) {
				time += parts.get(i).getOperations().get(j)
						.getHistoryprocessingtime();
			} // 工件的历史加工时间为所有工序的历史加工时间之和
			parts.get(i).setHistoryprocessingtime(time);
			int duedate = (int) (parts.get(i).getArrivaltime() + 1.7 * parts
					.get(i).getHistoryprocessingtime());
			parts.get(i).setDuedate(duedate);
		}
		System.out.println("PartNumber = " + partNumber + " MachineNumer = "
				+ machineNumber + " CellNumber = " + cellNumber);
	}

	public int getMachineNumber() {
		return machineNumber;
	}

	public int getPartNumber() {
		return partNumber;
	}

	public List<Part> getParts() {
		return parts;
	}

	public List<CellAgent> getCellAgent() {
		return cells;
	}

	public int getCellNumber() {
		return cellNumber;
	}

	public void PrintTest() {
		int totalOperation=0;
		System.out.println("PartNumber" + partNumber + " MachineNumber"
				+ machineNumber + " CellNumber" + cellNumber);
		for (int i = 0; i < parts.size(); i++) {
			totalOperation +=parts.get(i).getOperations().size();
		//System.out.println("编号 "+parts.get(i).getPartid()+"的工件的工序数目为 "+parts.get(i).getOperations().size());
		}
		System.out.println("总的工序数目为"+totalOperation);
		
//		for (int i = 0; i < parts.size(); i++) {
//			for (int j = 0; j < parts.get(i).getOperations().size(); j++)
//				for (int k = 0; k < parts.get(i).getOperations().get(j)
//						.getMachineTimes().size(); k++)
//					System.out.println("PartId"
//							+ parts.get(i).getPartid()
//							+ " OpId"
//							+ parts.get(i).getOperations().get(j)
//									.getOperationid()
//							+ " MachineId"
//							+ parts.get(i).getOperations().get(j)
//									.getMachineTimes().get(k).getMachineId()
//							+ " CellId"
//							+ parts.get(i).getOperations().get(j)
//									.getMachineTimes().get(k).getCellId()
//							+ " Time"
//							+ parts.get(i).getOperations().get(j)
//									.getMachineTimes().get(k).getTime());
//		}

//		for (int i = 0; i < cells.size(); i++) {
//			for (int j = 0; j < cells.get(i).getMachines().size(); j++) {
//				System.out.println("Cellid"
//						+ cells.get(i).getMachines().get(j).getCellid()
//						+ " MachineId"
//						+ cells.get(i).getMachines().get(j).getMachineid());
//			}
//		}
	}

}
