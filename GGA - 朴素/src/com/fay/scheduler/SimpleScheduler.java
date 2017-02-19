package com.fay.scheduler;

import java.util.ArrayList;
import java.util.List;

import com.fay.GGA.Chromosome;
import com.fay.domain.Machine;
import com.fay.domain.BufferOut;
import com.fay.domain.Operation;
import com.fay.domain.CellSet;
import com.fay.domain.Cell;
import com.fay.domain.Job;
import com.fay.domain.JobSet;
import com.fay.domain.MachineSet;
import com.fay.domain.Vehicle;
import com.fay.rule.BufferOutRule.*;
import com.fay.rule.JobRule.*;
import com.fay.rule.MachineRule.*;
import com.fay.util.Timer;

import ec.EvolutionState;
import ec.Individual;
import ec.app.fayApp.DoubleData;
import ec.app.fayApp.EvolveAssignmentRule;
import ec.app.fayApp.EvolveBufferOutRule;
import ec.app.fayApp.EvolveTimeWindowRule;
import ec.gp.ADFStack;

public  class SimpleScheduler extends AbstractScheduler{    
	
	private List<Job> unArriJobs;
	
	public static class GPInfo {
        public static EvolutionState state;
        public static Individual ind;
        public static int subpopulation;
        public static ADFStack stack;
        public static int threadnum;
        public static DoubleData input;
        public static EvolveTimeWindowRule timeProblem;
        public static EvolveBufferOutRule buffProblem;
        public static EvolveAssignmentRule assiProblem; 
    } 
		
    public SimpleScheduler(MachineSet machineSet, JobSet jobSet, CellSet cellSet, Chromosome m, Chromosome j, Chromosome b) {
        super(machineSet, jobSet, cellSet , m ,j , b);
        unArriJobs = new ArrayList<Job>();
        for (Job job : jobSet) {
            unArriJobs.add(job);
        }
    }
    
    public void assignOperationToMachine(Job job){            //��ݹ�����ѡ�Ļ����ѹ�����뵽�û����Ļ�������
        Operation nextScheduleOperation = job.getNextScheduleOperation();
        if (nextScheduleOperation != null) {
            Machine selectedMachine = nextScheduleOperation.getSelectedMachine();
            selectedMachine.addOperationToBufferIn(nextScheduleOperation);        //��������뵽�����Ļ�����
            
            nextScheduleOperation.setArrivalTime(Timer.currentTime());
        }
    }

    public void ruleBufferOut(Cell cell){
    	Operation selectedOperation = null;

    	IBufferOutRule rule = (IBufferOutRule)bChromosome.getEntityRule(3,cell.GetID());
    	BufferOut newBufferOut = new BufferOut();
    	int size = cell.getBufferOut().size();
    	for(int i = 0;i < size;i++){
    		double max = -Double.MAX_VALUE;
    		for (Operation operation : cell.getBufferOut()){
    			double score = rule.calPrio(cell,operation);
    			if (score > max) {
                max = score;
                selectedOperation = operation;
    			}
    		}
    		newBufferOut.addOperation(selectedOperation);
    		cell.getBufferOut().removeOperation(selectedOperation);
    	}
    	cell.setBufferOut(newBufferOut);
    }
    
    public void ruleTimeWindow(Cell cell){
    	int timeWindow;
    	timeWindow = 0;
    	cell.GetVehicle().setTimeWindow(timeWindow+Timer.currentTime());
		cell.GetVehicle().setTimeWindowIdle(true);
		Timer.addTrigger(timeWindow+Timer.currentTime());
    }
    
    public void jobRule(IJobRule rule,Operation operation){
        double max = -Double.MAX_VALUE;
        Machine selectedMachine = null;

		// 遍历 machineSet 依据rule选择最优score
        for (Machine machine : operation.getProcessMachineList()) { 
            double score = rule.calPrio(operation, machine);

            if (score > max) {
                max = score;
                selectedMachine = machine;
            }
            else if(score == max && selectedMachine.getId() > machine.getId())
            	selectedMachine = machine;
        }
    	operation.setSelectedMachine(selectedMachine);
    }
    
    public void machineRule(IMachineRule rule,Machine machine){
        double max = -Double.MAX_VALUE;
        Operation selectedOperation = null;
        for (Operation operation : machine.getBufferIn().getOperations()) {
            double priority = rule.calPrio(operation);
            if (priority > max) {
                max = priority;
                selectedOperation = operation;
            }
        }
        machine.setOperation(selectedOperation);
    }
    
    public void unload(Machine machine){
    	Operation operation = machine.getProcessingOperation();

    	if(machine.getState() == 1 && operation != null){	
    		Job job = operation.getJob();
    		job.scheduleOperation();
    		if(job.getNextScheduleOperation() != null){
    		    jobRule((IJobRule)jChromosome.getEntityRule(2,job.getId()), job.getNextScheduleOperation());
    			if(machine.getNumInCell() == job.getNextScheduleOperation().getSelectedMachine().getNumInCell()){
    				assignOperationToMachine(job.getNextScheduleOperation().getJob());
    				
    				
    			}
    			else{
    				Cell locatedCell = null;
    				for(Cell cell:cellSet){
    					if(cell.GetID() == machine.getNumInCell()){
    						locatedCell = cell;
    					}
    				}
    				locatedCell.AddOperationToBufferOut(job.getNextScheduleOperation());
    				
    				job.getNextScheduleOperation().setTransferCarId_to();       //运输问题
    				job.getNextScheduleOperation().setTransferCarId_from(locatedCell.GetID());
    				job.getNextScheduleOperation().setArrivalBufferOutTime(Timer.currentTime());
    				
    			}
    		
    			machine.setProcessingOperationNull();
    			machine.setSelectedOperationNull();
    			machine.setNextIdleTime(0);
    			machine.setState(0);
    		
    		}
    		else {
    			Job job_job = operation.getJob();
    			machine.setProcessingOperationNull();
    			machine.setSelectedOperationNull();
    			machine.setNextIdleTime(0);
    			machine.setState(0);
    			job_job.setJobIdle(true);
    			job_job.setFinishTime(Timer.currentTime());
    		}

    	}
    }
    
    public void load(Machine machine){
    	if(machine.getBufferIn().size() != 0 && machine.getState() == 0){
    		machineRule((IMachineRule)mChromosome.getEntityRule(1,machine.getId()), machine);

    		Operation selectedOperation = machine.getSelectedOperation();    		    		
    		machine.setProcessingOperation(selectedOperation);
    		machine.getBufferIn().removeOperation(selectedOperation);
    		selectedOperation.setStartTime(Timer.currentTime());
    		selectedOperation.setEndTime(Timer.currentTime()+selectedOperation.getProcessingTime());
    		machine.setNextIdleTime(selectedOperation.getEndTime());
    		machine.setState(1);
    		
    		Timer.addTrigger(Timer.currentTime()+selectedOperation.getProcessingTime());
    		
    	}
    }
    
    public void scanMachine(){
    	for(Machine machine : machineSet){
            if (machine.getNextIdleTime() <= Timer.currentTime()) {
            	unload(machine);
                load(machine);
            }
    	}
    }
    
    public void scheduleVehicle(Cell cell){
    	
    	if(cell.getBufferOut().size() != 0){
    		
    		if(cell.getBufferOut().size() > cell.GetVehicle().getCapacity()){     //大于运输能力    			
    			for(int i = 0;i < cell.GetVehicle().getCapacity();i++){
    				Operation operation = cell.getBufferOut().get(i);
    				cell.GetVehicle().addOperation(operation);
    			//如果缓冲区的大小大于小车的容积，那么从缓冲区中选取小车体积个
    			}
    		
   			
    			List<Integer> desCell = new ArrayList<Integer>();
    			List<Integer> desTime = new ArrayList<Integer>();
    			
    			for(int i = 0;i < cell.GetVehicle().getCapacity();i++){
					desCell.add(cell.getBufferOut().get(i).getSelectedMachine().getNumInCell());
				}
    			//送达时间
    			desTime.add(cell.GetTransferTime(cell.getBufferOut().get(0).getSelectedMachine().getNumInCell())+Timer.currentTime());
    			
    			Timer.addTrigger(cell.GetTransferTime(cell.getBufferOut().get(0).getSelectedMachine().getNumInCell())+Timer.currentTime());
    			
    			int i,j;
    			//根据已得到的到达目标单元所用的时间   逐渐得到已经装到小车中的工件从当前单元到达目的单元各自所需的时间。
    			for(i = 1;i < cell.GetVehicle().getCapacity();i++){
    				for(j = 0; j<i;j++){
    					if(desCell.get(i) == desCell.get(j)){           //
    						desTime.add(desTime.get(j));
    						
    						Timer.addTrigger(j);
    						
    						break;
    					}
    				}
    				//之前没有与之相同的目的单元
    				if(j == i){
    					int max = 0;
	        			for(int k : desTime){
	        				if(k > max)  
	        					max = k;
	        			}
	        	//最远的单元到小车缓冲区中的第i个工序的目的单元的时间+之前工件到达目的单元的最大值。
	        			Cell lastCell = new Cell();
	        			for(Cell c : cellSet){
	        				if(c.GetID() == desCell.get(desTime.indexOf(max)))
	        					lastCell = c;
	        			}
	        			desTime.add(max+lastCell.GetTransferTime(desCell.get(i)));
	        			
	        			Timer.addTrigger(max+lastCell.GetTransferTime(desCell.get(i)));
    				}
    			}
    			  			    			
    			int max = 0;
    			
    			for(int k : desTime){
    				if(k > max)  
    					max = k;
    			}

    			
    			Cell lastCell = new Cell();
    			for(Cell c : cellSet){
    				if(c.GetID() == desCell.get(desTime.indexOf(max)))
    					lastCell = c;
    			}
    			
    			cell.GetVehicle().SetArriveDesTime(desTime);
    			cell.GetVehicle().setDesCell(desCell);
    			cell.GetVehicle().setVehicleBusy();
    			cell.GetVehicle().SetBackTime(max+lastCell.GetTransferTime(cell.GetID()));
    			
    			Timer.addTrigger(max+lastCell.GetTransferTime(cell.GetID()));
    			
    			
    			int size = cell.GetVehicle().getCapacity();
    			for(i = 0;i < cell.GetVehicle().getCapacity();i++){
					cell.getBufferOut().get(i).setStartTransferTime(Timer.currentTime());
				}
    			int size_size = cell.GetVehicle().getCapacity(); 				
				for(i = 0;i < size_size;i++){
					Operation operation = cell.getBufferOut().get(0);      				
					cell.getBufferOut().removeOperation(operation);
				}
				
				cell.GetVehicle().setCapacity(0);
    		
    		}
    		
    		
    	
    		else if(cell.getBufferOut().size() <= cell.GetVehicle().getCapacity()){		
    			
    			if(cell.GetVehicle().getTimeWindowIdle() == false){
    			ruleTimeWindow(cell);                     				
    			}
    			if((cell.GetVehicle().getTimeWindowIdle() == true)&&(cell.GetVehicle().getTimeWindow() == Timer.currentTime())){	
    		   				   				
    				for(int i = 0;i < cell.getBufferOut().size();i++){
    					Operation operation = cell.getBufferOut().get(i);	
        				cell.GetVehicle().addOperation(operation);
    				}
    				List<Integer> desCell = new ArrayList<Integer>();
    				List<Integer> desTime = new ArrayList<Integer>();
    				
					for(int i = 0;i < cell.getBufferOut().size();i++){
						desCell.add(cell.getBufferOut().get(i).getSelectedMachine().getNumInCell());
					}
					
					desTime.add(cell.GetTransferTime(
							cell.getBufferOut().get(0).getSelectedMachine().getNumInCell())+Timer.currentTime());
					
					Timer.addTrigger(cell.GetTransferTime(
							cell.getBufferOut().get(0).getSelectedMachine().getNumInCell())+Timer.currentTime());
					
    				int i,j;
    				for(i = 1;i < cell.getBufferOut().size();i++){
    					for(j = 0; j < i;j++){
    						if(desCell.get(i) == desCell.get(j)){           //��֮ǰ��Ŀ��Cellһ��
    							desTime.add(desTime.get(j));
    							
    							Timer.addTrigger(desTime.get(j));
    							
    							break;
    						}			
    					}
    					if(j == i){
    						int max = 0;
    	        			for(int k : desTime){
    	        				if(k > max)  
    	        					max = k;
    	        			}
    	        	
    	        			Cell lastCell = new Cell();
    	        			for(Cell c : cellSet){
    	        				if(c.GetID() == desCell.get(desTime.indexOf(max)))
    	        					lastCell = c;
    	        			}
    	        			desTime.add(max+lastCell.GetTransferTime(desCell.get(i)));
    	        			
    	        			Timer.addTrigger(max+lastCell.GetTransferTime(desCell.get(i)));
    	        			
    					}
    				}	
    				
    				int max = 0;	
    				
        			for(int k : desTime){
        				if(k > max)  
        					max = k;
        			}
        			
        			Cell lastCell = new Cell();
        			for(Cell c : cellSet){
        				if(c.GetID() == desCell.get(desTime.indexOf(max)))
        					lastCell = c;
        			}
        			
        			cell.GetVehicle().SetArriveDesTime(desTime);
        			cell.GetVehicle().setDesCell(desCell);
        			cell.GetVehicle().SetBackTime(max+lastCell.GetTransferTime(cell.GetID()));
        			
        			Timer.addTrigger(max+lastCell.GetTransferTime(cell.GetID()));
        			
    				cell.GetVehicle().setVehicleBusy();
    				
    				cell.GetVehicle().setCapacity(cell.GetVehicle().getCapacity()-cell.getBufferOut().size());
    								
    				for(i = 0;i < cell.getBufferOut().size();i++){
    					cell.getBufferOut().get(i).setStartTransferTime(Timer.currentTime());
    				}
    				
    				int size = cell.getBufferOut().size(); 				
    				for(i = 0;i < size;i++){
    					Operation operation = cell.getBufferOut().get(0);      				
    					cell.getBufferOut().removeOperation(operation);
    				}
    				
    				
    			}
    		}    
    	}
    }

	//扫描小车
    public void scanVehicle(){
    	for(Cell cell : cellSet){     
    		Vehicle cellVehicle = cell.GetVehicle();
    		if(cellVehicle.IsVehicleBack(Timer.currentTime()) == true){
    			cellVehicle.setVehicleAvailable();
    			cellVehicle.rest();
    		}//小车已经回来
    		if(cellVehicle.getIdle() == true){
    			ruleBufferOut(cell);
    			scheduleVehicle(cell);
    		}
    		
    		if((cellVehicle.getIdle() == false )&&(cellVehicle.IsVehicleArrival(Timer.currentTime()) != 0)){ //С����ǰ������,����Ŀ�ĵ�
    			
    			for(int i=cellVehicle.getDesCell().size()-1; i >= 0; i--){
    				if(cellVehicle.getDesTime().get(i)== Timer.currentTime()){
    					
    					cellVehicle.getTransingOperation().get(i).setArrivalTime(Timer.currentTime());

    					cellVehicle.getTransingOperation().get(i).getSelectedMachine().getBufferIn().addOperation(cellVehicle.getTransingOperation().get(i));
    					cellVehicle.getTransingOperation().remove(i);
    					cellVehicle.getDesCell().remove(i);
    					cellVehicle.getDesTime().remove(i);
    					cellVehicle.setCapacityPlus();     					
    					
    				}
    			}
    			
    		}
    	
    	}
 }

    public void printInfoScanMachine(){				//�����Ϣ 	
    	System.out.println("CurrentTime:"+Timer.currentTime());
    	for(Job job : jobSet){
    		if(job.getNextScheduleOperation() != null)
    			System.out.println("jobId:"+job.getId()+" jobNextScheduleOperation:"+job.getNextScheduleOperation().getId()+" jobSelectedMachine:"+job.getNextScheduleOperation().getSelectedMachine());
    		else 
    			System.out.println("jobId:"+job.getId()+"finish!");
    	}
    	for(Machine machine : machineSet){
    		System.out.println("MachineBuffer"+machine.getId());
    		for(Operation operation : machine.getBufferIn().getOperations()){
    			System.out.println("   JobId:"+operation.getJob().getId()+"   OperationId:"+operation.getId());
    		}
    		Operation operation = machine.getSelectedOperation();
    		if(operation != null)
    		System.out.println("MachineProcessing  JobId:"+machine.getSelectedOperation().getJob().getId()+" "+"MachineProssing  OperationId:"+machine.getProcessingOperation().getId());
    		
    		System.out.println(machine.getNextIdleTime());
    	}
    	for(Cell cell : cellSet){
    		System.out.println("CellBufferOut"+cell.GetID());
    		for(Operation operation : cell.getBufferOut()){
    			System.out.println("  JobId"+operation.getJob().getId()+" "+"  Operation"+operation.getId());
    		}
    		System.out.println(cell.getBufferOut().size());
    	}
    	for(Cell cell : cellSet){
    		System.out.println("Vehicle"+cell.GetVehicle().getVehicleId());
    		System.out.println("VehicleSize"+cell.GetVehicle().getCapacity());
    		if(cell.GetVehicle().getIdle() == false){
    			for(Operation operation : cell.GetVehicle().getTransingOperation()){
    				System.out.println("     JobId:"+operation.getJob().getId()+"    OperationId:"+operation.getId());
    			}
    			for(int i =0; i < cell.GetVehicle().getDesCell().size(); i++){
    				System.out.println("CellId"+cell.GetVehicle().getDesCell().get(i));
    			}
    			for(int i =0; i < cell.GetVehicle().getDesTime().size(); i++){
    				System.out.println("CellTime"+cell.GetVehicle().getDesTime().get(i));
    			}
    			System.out.println("BackTime"+cell.GetVehicle().getBackTime());
    		}
    		else{
    			System.out.println("Vehicle is available!");
    		}
    		
    	}
    	
    	
    }
    
    public void printInfo_1(){
    	for(Job job : jobSet){
    		System.out.println("JobID:\t"+job.getId()+"\tJobWeight\t"+job.getWeight()+"\tJobDuedate\t"+job.getDuedate());
    	}
    }
    
    public void printFinalInfo(){
    	for(Job job : jobSet){	
    		List<Operation> op = new ArrayList<Operation>();
    		op = job.getOperations();
    		for(int i = 0; i< op.size();i++){
    			System.out.print("JobID:\t"+job.getId()+"\t");
    			System.out.print("OperationID:\t"+op.get(i).getId()+"\t");
    			System.out.print("StartTime:\t"+op.get(i).getStartTime()+"\t"+"EndTime:\t"+op.get(i).getEndTime()+"\t");
    			System.out.print("Machine:\t"+op.get(i).getSelectedMachine().getId()+"\t");
    			if(op.get(i).getArrivalTime() != 0){
    				System.out.print("TransferFrom:\t"+op.get(i).getTransferCarId_from()+"\t"+"TransferTo:\t"+op.get(i).getTransferCarId_to()+"\t"+"ArrivalTime:\t"+op.get(i).getArrivalTime()+"\t");
    			}   			
    		    System.out.print("\n");
    		}
    		
    	}
    }
    
    public void printFinalInfo_1(){
    	for(Job job : jobSet){	
    		List<Operation> op = new ArrayList<Operation>();
    		op = job.getOperations();
    		for(int i = 0; i< op.size();i++){
    			System.out.print("JobID:\t"+job.getId()+"\t");
    			System.out.print("OperationID:\t"+op.get(i).getId()+"\t");
    			System.out.print("StartTime:\t"+op.get(i).getStartTime()+"\t"+"EndTime:\t"+op.get(i).getEndTime()+"\t");
    			System.out.print("Machine:\t"+op.get(i).getSelectedMachine().getId()+"\t");
    			System.out.print("ArrivalTime:\t"+op.get(i).getArrivalTime()+"\t");
    			   			
    		    System.out.print("\n");
    		}
    		
    	}
    }

    public void scheduleStart(){
		Timer.resetTimer();
    	for (Job job : jobSet) {
    		// 工件为Operation选machine
			this.jobRule( (IJobRule)jChromosome.getEntityRule(2,job.getId()) /** rule for the job in the chromosome */, job.getNextScheduleOperation() /** operation need to be done (procTime: machine->time) */);
			assignOperationToMachine(job);
		}
    }
    
    public boolean isEnd(){
    	int i = 0;
    	for(Job job : jobSet){
    		if(job.getIdle() == true)
    			i++;
    	}
    	if(i == jobSet.size()) return true;
    	else return false;
    }

    public void reset(){
    	for(Job job : jobSet){
    		job.reset();
    	}
    	for(Machine machine : machineSet){
    		machine.reset();
    	}
    	for(Cell cell : cellSet){
    		cell.reset();
    		cell.GetVehicle().restForVehicle();
    	}
    	Timer.resetTimer();
    }

	//调度方法
	public void schedule() {
		boolean state = false;

		//分配job第一道工序
		scheduleStart();
		scanMachine();
		scanVehicle();					

		while(!state){
			Timer.stepTimer();
			scanMachine();            //scanmachine
			scanVehicle();	          //scanVehicle
			state = isEnd();
		}
	}
}
