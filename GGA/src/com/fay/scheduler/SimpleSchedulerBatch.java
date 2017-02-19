package com.fay.scheduler;

import java.util.ArrayList;
import java.util.List;

import com.fay.domain.BufferOut;
import com.fay.domain.Cell;
import com.fay.domain.CellSet;
import com.fay.domain.Job;
import com.fay.domain.JobSet;
import com.fay.domain.Machine;
import com.fay.domain.MachineSet;
import com.fay.domain.Operation;
import com.fay.domain.Vehicle;
import com.fay.measure.IMeasurance;
import com.fay.measure.TotalWeightedTardiness;
import com.fay.rule.BufferOutRule.BufferOutEDD;
import com.fay.rule.BufferOutRule.IBufferOutRule;
import com.fay.rule.JobRule.IJobRule;
import com.fay.rule.JobRule.JobSPT;
import com.fay.rule.MachineRule.IMachineRule;
import com.fay.rule.MachineRule.MachineSPT;
import com.fay.rule.TimeWindowRule.ITimeWindowRule;
import com.fay.rule.TimeWindowRule.TimeWindow_0;
import com.fay.util.Timer;

import ec.EvolutionState;
import ec.Individual;
import ec.app.fayApp.DoubleData;
import ec.app.fayApp.EvolveAssignmentRule;
import ec.app.fayApp.EvolveBufferOutRule;
import ec.app.fayApp.EvolveSequencingRule;
import ec.app.fayApp.EvolveTimeWindowRule;
import ec.gp.ADFStack;

public class SimpleSchedulerBatch extends AbstractScheduler{
	
	public static class GPInfo {
        public static EvolutionState state;
        public static Individual ind;
        public static int subpopulation;
        public static ADFStack stack;
        public static int threadnum;
        public static DoubleData input;
        public static EvolveSequencingRule seqProblem;
        public static EvolveTimeWindowRule timeProblem;
        public static EvolveBufferOutRule buffProblem;
        public static EvolveAssignmentRule assiProblem;
    }
		
    public SimpleSchedulerBatch(MachineSet machineSet, JobSet jobSet,
            CellSet cellSet) {
        super(machineSet, jobSet, cellSet);               //time = 0
        
    }
    
    public void assignOperationToMachine(Job job){            //��ݹ�����ѡ�Ļ����ѹ�����뵽�û����Ļ�������
        Operation nextScheduleOperation = job.getNextScheduleOperation();
        if (nextScheduleOperation != null) {
            Machine selectedMachine = nextScheduleOperation.getSelectedMachine();
            selectedMachine.addOperationToBufferIn(nextScheduleOperation);        //��������뵽�����Ļ�����
            
            nextScheduleOperation.setArrivalTime(Timer.currentTime());
        }
    }
    
    
    public void ruleBufferOut(Cell cell){              //����cell��������Ĺ��򣬶Ի������������
    	Operation selectedOperation = null;
    	IBufferOutRule rule = cell.getBufferOutRule();             
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
    		}										//�ҳ����еĻ���������ֵ����
    		newBufferOut.addOperation(selectedOperation);				//�����»�����
    		cell.getBufferOut().removeOperation(selectedOperation);      //��ԭ����������ɾ��
    	}
    	//newBufferOut�Ѱ���������
    	
    	cell.setBufferOut(newBufferOut);
    }
    
    public void ruleTimeWindow(Cell cell){            //����С����timeWindow,С����ʱ�䴰�Ǹ���ֵ�����й���õ�һ����
    	ITimeWindowRule rule = cell.GetVehicle().getTimeWindowRule();
    	int timeWindow;
    	timeWindow = (int)rule.calPrio(cell);
    	cell.GetVehicle().setTimeWindow(timeWindow+Timer.currentTime());
		cell.GetVehicle().setTimeWindowIdle(true);
//		if(timeWindow != 0) System.out.print('!');
		Timer.addTrigger(timeWindow+Timer.currentTime());
    }
    
    public void jobRule(IJobRule rule,Operation operation){            //������ѡ��jobrule����
        double max = -Double.MAX_VALUE;
        Machine selectedMachine = null;
        for (Machine machine : operation.getProcessMachineList()) { 
            double score = rule.calPrio(operation, machine);
            if (score > max) {
                max = score;
                selectedMachine = machine;
            }
            if(score == max){
            	if(selectedMachine.getId() > machine.getId()){
            		selectedMachine = machine;
            	}
            }                                    //��֤�ӹ�����ʱ����ͬѡ�����idС�ģ���֤���н��һ��
        }
    	operation.setSelectedMachine(selectedMachine);           //��Ҫ�ı乤����ѡ��Ļ���
    }
    
    public void machineRule(IMachineRule rule,Machine machine){       //������ѡ��machinerule����
        double max = -Double.MAX_VALUE;
        Operation selectedOperation = null;
        for (Operation operation : machine.getBufferIn().getOperations()) {
            double priority = rule.calPrio(operation);
            if (priority > max) {
                max = priority;
                selectedOperation = operation;
            }
        }
        machine.setOperation(selectedOperation);                      //��Ҫ�ı估����ѡ����
    }
    
    public void unload(Machine machine){		//����ɵĹ������ڹ���������һ������ĵ��ȣ�
    	Operation operation = machine.getProcessingOperation();     //��������ɵĹ���
    	//if(operation != null){
    	if(machine.getState() == 1 && operation != null){	
    		Job job = operation.getJob();
    		job.scheduleOperation();               //��job���´ε��Ⱥ�+1
    		if(job.getNextScheduleOperation() != null){
    			jobRule(job.getJobRule(),job.getNextScheduleOperation());     //���߸�job����һ��operation��ѡ�Ļ���
    		      
    			//		����ɹ�����ѡ�������ڵ�Ԫ					�´ε��ȵĹ�����ѡ�����ĵ�Ԫ
    			if(machine.getNumInCell() == job.getNextScheduleOperation().getSelectedMachine().getNumInCell()){
    				assignOperationToMachine(job.getNextScheduleOperation().getJob());          //����û�п絥Ԫ,���µ����������ѡ�����Ļ�����
    				
    				
    			}
    			else{	//�絥Ԫ��������뵽�絥Ԫ�ĳ�������
    				Cell locatedCell = null;     //���һ�������cell
    				for(Cell cell:cellSet){
    					if(cell.GetID() == machine.getNumInCell()){
    						locatedCell = cell;
    					
    					}
    				}
    				locatedCell.AddOperationToBufferOut(job.getNextScheduleOperation());
    				
    				
    				job.getNextScheduleOperation().setTransferCarId_to();       //������絥Ԫ��
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
    //			System.out.println(job_job.getName()+"Finish");
    			job_job.setJobIdle(true);
    			job_job.setFinishTime(Timer.currentTime());
    		}

    	}
    }
    
    public void load(Machine machine){			//�ø�����ڻ����Ļ�������ѡ��һ��������е���
    	if(machine.getBufferIn().size() != 0 && machine.getState() == 0){				//���������뻺�岻Ϊ��
    		machineRule(machine.getMachineRule(),machine);
    		Operation selectedOperation = machine.getSelectedOperation();    		    		
    		machine.setProcessingOperation(selectedOperation);		//���û������ڼӹ��Ĺ���
    		machine.getBufferIn().removeOperation(selectedOperation);    	//�ѹ���ӻ������뻺��ɾ��
    		selectedOperation.setStartTime(Timer.currentTime());
    		selectedOperation.setEndTime(Timer.currentTime()+selectedOperation.getProcessingTime()); //���ù���Ŀ�ʼ����ʱ��
    		machine.setNextIdleTime(selectedOperation.getEndTime());  //���û������´��ͷ�ʱ��
    		machine.setState(1);
    		
    		Timer.addTrigger(Timer.currentTime()+selectedOperation.getProcessingTime());
    		
    	}
    }
    
    public void scanMachine(){                      //ɨ�����
    	for(Machine machine : machineSet){
            if (machine.getNextIdleTime() <= Timer.currentTime()) {		// ��������
            	unload(machine);		// ж�ص�ǰ����Ĺ�������һ��������               
                load(machine);			// ������������һ������
                
            }
            else {      // ��������--���Ȳ���
           //     machine.addWorkingTime();	
            } 		
    	}
    	//��Է��䵽�����������Ͻ��е��ȵ��Ż�
    }
    
    public void scheduleVehicle(Cell cell){
    	if(cell.getBufferOut().size() != 0){
    		
    		Operation opera = cell.getBufferOut().get(0);
    		int op = 0;
    		
    		for(Operation operation : cell.getBufferOut()){
    			if(cell.getBufferOut().get(0).getSelectedMachine().getNumInCell() == operation.getSelectedMachine().getNumInCell()){
    				op++;
    			}
    		}
    	
    		if(op > cell.GetVehicle().getCapacity()){  
    			  			
    			List<Integer> desCell = new ArrayList<Integer>();
    			List<Integer> desTime = new ArrayList<Integer>();
    			
    			for(Operation operation : cell.getBufferOut()){
    				if(cell.getBufferOut().get(0).getSelectedMachine().getNumInCell() == operation.getSelectedMachine().getNumInCell()){
    					cell.GetVehicle().addOperation(operation);
    					desTime.add(cell.GetTransferTime(cell.getBufferOut().get(0).getSelectedMachine().getNumInCell())+Timer.currentTime());
    					desCell.add(cell.getBufferOut().get(0).getSelectedMachine().getNumInCell());
    				}
    			}
    			   			
    			Timer.addTrigger(cell.GetTransferTime(cell.getBufferOut().get(0).getSelectedMachine().getNumInCell())+Timer.currentTime());
    		
    			cell.GetVehicle().SetArriveDesTime(desTime);
    			cell.GetVehicle().setDesCell(desCell);
    			cell.GetVehicle().setVehicleBusy();
    			cell.GetVehicle().SetBackTime(cell.GetTransferTime(cell.getBufferOut().get(0).getSelectedMachine().getNumInCell())*2+Timer.currentTime());
				
				Timer.addTrigger(cell.GetTransferTime(cell.getBufferOut().get(0).getSelectedMachine().getNumInCell())*2+Timer.currentTime());
    				   			
				for(Operation operation : cell.GetVehicle().getTransingOperation()){
					cell.getBufferOut().removeOperation(operation);
				}
				
    			cell.GetVehicle().setCapacity(0);
    			
    		}
    		
    		
    		
    	
    		else if(op <= cell.GetVehicle().getCapacity()){		//����������С��С������
    			
    			if(cell.GetVehicle().getTimeWindowIdle() == false){
    			ruleTimeWindow(cell);                 //С��û��ʱ�䴰����Ҫ����һ������ȷ��һ�������ʱ��         				
    			}
    			if((cell.GetVehicle().getTimeWindowIdle() == true)&&(cell.GetVehicle().getTimeWindow() == Timer.currentTime())){	
    			//С����ʱ�䴰��С����ʱ�䴰Ϊ0������Ҫ�ȴ�ֱ������
		  			
    				List<Integer> desCell = new ArrayList<Integer>();
    				List<Integer> desTime = new ArrayList<Integer>();
		
    				for(Operation operation : cell.getBufferOut()){
    					if(cell.getBufferOut().get(0).getSelectedMachine().getNumInCell() == operation.getSelectedMachine().getNumInCell()){
    						cell.GetVehicle().addOperation(operation);
    						desTime.add(cell.GetTransferTime(cell.getBufferOut().get(0).getSelectedMachine().getNumInCell())+Timer.currentTime());
    						desCell.add(cell.getBufferOut().get(0).getSelectedMachine().getNumInCell());
    					}
    				}
		
    				Timer.addTrigger(cell.GetTransferTime(cell.getBufferOut().get(0).getSelectedMachine().getNumInCell())+Timer.currentTime());
	
    				cell.GetVehicle().SetArriveDesTime(desTime);
    				cell.GetVehicle().setDesCell(desCell);
    				cell.GetVehicle().setVehicleBusy();
        			cell.GetVehicle().SetBackTime(cell.GetTransferTime(cell.getBufferOut().get(0).getSelectedMachine().getNumInCell())*2+Timer.currentTime());
    				
    				Timer.addTrigger(cell.GetTransferTime(cell.getBufferOut().get(0).getSelectedMachine().getNumInCell())*2+Timer.currentTime());
        				   			
    				for(Operation operation : cell.GetVehicle().getTransingOperation()){
    					cell.getBufferOut().removeOperation(operation);
    				}
        			
    				cell.GetVehicle().setCapacity(cell.GetVehicle().getCapacity() - cell.GetVehicle().getTransingOperation().size());
    				}
    				

    		}
    	}
    }
    
    public void scanVehicle(){				  //ɨ��С��
    	for(Cell cell : cellSet){     
    		Vehicle cellVehicle = cell.GetVehicle();						//ÿ����Ԫ��С��
    		if(cellVehicle.IsVehicleBack(Timer.currentTime()) == true){
    			cellVehicle.setVehicleAvailable();          //С�������ˣ����ÿ���
    			cellVehicle.rest();
    		}
    		if(cellVehicle.getIdle() == true){          //С����ǰ����
    			ruleBufferOut(cell);                //�����������,�ѻ������������
    			scheduleVehicle(cell);                          //��������
    		}
    		
    		if((cellVehicle.getIdle() == false )&&(cellVehicle.IsVehicleArrival(Timer.currentTime()) != 0)){ //С����ǰ������,����Ŀ�ĵ�
    			
    			for(int i=cellVehicle.getDesCell().size()-1; i >= 0; i--){
    				if(cellVehicle.getDesTime().get(i)== Timer.currentTime()){
    					
    					cellVehicle.getTransingOperation().get(i).setArrivalTime(Timer.currentTime());
    					  					
    					cellVehicle.getTransingOperation().get(i).getSelectedMachine().getBufferIn().addOperation(cellVehicle.getTransingOperation().get(i));
    					//������cell�Ĺ���ж��
    					cellVehicle.getTransingOperation().remove(i);   //��ж�صĹ����С����ɾ��
    					//����ѡ���С��destime��descell���ɾ��,���ܻ��������
    					cellVehicle.getDesCell().remove(i);
    					cellVehicle.getDesTime().remove(i);
    					cellVehicle.setCapacityPlus();     					
    					
    				}
    			}
    			
    		}
    		//С����������
    	}
    }
    
 /*   public void printInfoStart(){				//�����Ϣ 	
    	System.out.println("CurrentTime:"+Timer.currentTime());
    	for(Job job : jobSet){
    		System.out.println("jobId:"+job.getId()+" jobNextScheduleOperation:"+job.getNextScheduleOperation().getId()+" jobSelectedMachine:"+job.getNextScheduleOperation().getSelectedMachine());
    	}
    	for(Machine machine : machineSet){
    		System.out.println("Machine"+machine.getId());
    		for(Operation operation : machine.getBufferIn().getOperations()){
    			System.out.println("JobId"+operation.getJob().getId()+"OperationId"+operation.getId());
    		}
    	}
    }
    */
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
    	/*	System.out.println("CellTransferTime");
    		for(int i = 0; i< cell.GetTransferTimes().length; i++){
    			System.out.println("cellId"+i+" time"+cell.GetTransferTime(i));
    		}
    		*/                                              //�鿴��ʼ����Ԫʱ��
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
    	if(Timer.currentTime() == 0){
			for (Job job : jobSet) {
			              //ÿһ��������ͬһ�����򣬴˴�ѡ�����Ľӿڣ��ɻ���job��ʼ����ʱ���
				jobRule(job.getJobRule(),job.getNextScheduleOperation());   //����jobruleΪ����ѡ�����
				assignOperationToMachine(job);
				
			}
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
    	}
    	Timer.resetTimer();
    }

	public void schedule() {		//һ�ξ���ĵ���

		boolean state = false;
		double makeSpan = 0;
		//Makespan m = new Makespan();
		
		Timer.resetTimer();		
	/*	
		for(Job job : jobSet){
			job.setJobRule(new JobGP1()); 
		}
		
		for(Cell cell : cellSet){
			cell.setBufferOutRule(new BufferOutEDD());
			cell.GetVehicle().setTimeWindowRule(new TimeWindow_20());
		}
		
		for(Machine machine : machineSet){
			machine.setMachineRule(new MachineSPT());
		}
		*/
		
		scheduleStart();		
		scanMachine();							//ɨ�����			
		scanVehicle();						//ɨ��С��

		while(!state){		
			Timer.stepTimer();
			scanMachine();
			scanVehicle();	
			state = isEnd();			
		}
	//	makeSpan = m.getMeasurance(this);
	//	System.out.println("Finish"+makeSpan);	
		
		
		
	//	printFinalInfo();
		

		
	}
	
	public void schedule_3() {		//һ�ξ���ĵ���
		// TODO Auto-generated method stub
		boolean state = false;
		double result = 0;
		IMeasurance m = new TotalWeightedTardiness();
		
        Timer.resetTimer();
		
		for(Job job : jobSet){
			job.setJobRule(new JobSPT()); 
		}
		
		for(Cell cell : cellSet){
			cell.setBufferOutRule(new BufferOutEDD());
			cell.GetVehicle().setTimeWindowRule(new TimeWindow_0());
		}
		
		for(Machine machine : machineSet){
			machine.setMachineRule(new MachineSPT());
		}
		
		
		
		scheduleStart();		
		scanMachine();							//ɨ�����			
		scanVehicle();						//ɨ��С��
		
		while(!state){	

			Timer.stepTimer();
			scanMachine();
			scanVehicle();	
			state = isEnd();			
		}
		result = m.getMeasurance(this);
		System.out.println("Finish"+result);	
//		Timer.resetTimer();
		
		
	//	printInfo_1();
	//	printFinalInfo_1();
	//	printFinalInfo();
		

		
	}

}
