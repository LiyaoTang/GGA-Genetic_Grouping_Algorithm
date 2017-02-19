package com.fay.scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fay.measure.IMeasurance;
import com.fay.measure.Makespan;
import com.fay.measure.TotalWeightedTardiness;
import com.fay.GGA.Chromosome;
import com.fay.comparaEx.chromosome;
import com.fay.domain.Machine;
import com.fay.domain.BufferOut;
import com.fay.domain.Operation;
import com.fay.domain.CellSet;
import com.fay.domain.Cell;
import com.fay.domain.Job;
import com.fay.domain.JobSet;
import com.fay.domain.MachineSet;
import com.fay.domain.Vehicle;
import com.fay.scheduler.AbstractScheduler;
import com.fay.rule.BufferOutRule.*;
import com.fay.rule.JobRule.*;
import com.fay.rule.MachineRule.*;
import com.fay.rule.TimeWindowRule.ITimeWindowRule;
import com.fay.rule.TimeWindowRule.TimeWindowGP1;
import com.fay.rule.TimeWindowRule.TimeWindow_0;
import com.fay.rule.TimeWindowRule.TimeWindow_20;
import com.fay.util.Timer;

import ec.EvolutionState;
import ec.Individual;
import ec.app.fayApp.DoubleData;
import ec.app.fayApp.EvolveAssignmentRule;
import ec.app.fayApp.EvolveBufferOutRule;
import ec.app.fayApp.EvolveSequencingRule;
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
        public static EvolveSequencingRule seqProblem;
        public static EvolveTimeWindowRule timeProblem;
        public static EvolveBufferOutRule buffProblem;
        public static EvolveAssignmentRule assiProblem; 
    } 
		
    public SimpleScheduler(MachineSet machineSet, JobSet jobSet,
            CellSet cellSet , Chromosome m ,Chromosome j, Chromosome b) {
        super(machineSet, jobSet, cellSet , m ,j , b);               //time = 0
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
    
    
    public void ruleBufferOut(Cell cell){              //����cell��������Ĺ��򣬶Ի������������
    	Operation selectedOperation = null;
    	//IBufferOutRule rule = cell.getBufferOutRule();  //   
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
    		}										//�ҳ����еĻ���������ֵ����
    		newBufferOut.addOperation(selectedOperation);				//�����»�����
    		cell.getBufferOut().removeOperation(selectedOperation);      //��ԭ����������ɾ��
    	}
    	//newBufferOut�Ѱ���������
    	
    	cell.setBufferOut(newBufferOut);
    }
    
    public void ruleTimeWindow(Cell cell){            //����С����timeWindow,С����ʱ�䴰�Ǹ���ֵ�����й���õ�һ����
    	//ITimeWindowRule rule = cell.GetVehicle().getTimeWindowRule();
    	int timeWindow;
    //	timeWindow = (int)rule.calPrio(cell);
    	timeWindow = 0;
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
    		job.scheduleOperation();               //
    		if(job.getNextScheduleOperation() != null){
    		//	jobRule(job.getJobRule(),job.getNextScheduleOperation());     //���߸�job����һ��operation��ѡ�Ļ���
    		    jobRule((IJobRule)jChromosome.getEntityRule(2,job.getId()), job.getNextScheduleOperation());
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
    //			System.out.println(job_job.getName()+"Finish");
    			job_job.setJobIdle(true);
    			job_job.setFinishTime(Timer.currentTime());
    		}

    	}
    }
    
    public void load(Machine machine){			//�ø�����ڻ����Ļ�������ѡ��һ��������е���
    	if(machine.getBufferIn().size() != 0 && machine.getState() == 0){				//���������뻺�岻Ϊ��
    		//machineRule(machine.getMachineRule(),machine);
    		machineRule((IMachineRule)mChromosome.getEntityRule(1,machine.getId()), machine);
    	//	machineRule(mChromosome.getEntityRule(1), machine);
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
    
    public void scanMachine(){                      //ɨ���
    	for(Machine machine : machineSet){
            if (machine.getNextIdleTime() <= Timer.currentTime()) {		// 
            	unload(machine);		//            
                load(machine);			// �
                
            }
            else {      // 
           //     machine.addWorkingTime();	
            } 		
    	}
    	//��Է
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
    				//	desTime.add(desTime.get(i-1)+cell.GetTransferTime(cell.getBufferOut().get(0).getSelectedMachine().getNumInCell()));
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
	        			//�ҳ�������󵽴��cell
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
    			//��װ�ص�С���ϵĹ�����ӿ�ʼ�����ʱ��
				}
    			int size_size = cell.GetVehicle().getCapacity(); 				
				for(i = 0;i < size_size;i++){
					Operation operation = cell.getBufferOut().get(0);      				
					cell.getBufferOut().removeOperation(operation);
    			//��װ�ص�С���ϵĹ���ӻ�������ɾ��
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
        			//��С�����������Ĺ���װ�ص�С����
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
    					}//֮ǰû��һ���
    					if(j == i){
    					//desTime.add(desTime.get(i-1)+cell.GetTransferTime(cell.getBufferOut().get(i).getSelectedMachine().getNumInCell()));
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
    	        			//�ҳ�������󵽴��cell
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
        			//��װ�ص�С���ϵĹ�����ӿ�ʼ�����ʱ��
    				}
    				
    				int size = cell.getBufferOut().size(); 				
    				for(i = 0;i < size;i++){
    					Operation operation = cell.getBufferOut().get(0);      				
    					cell.getBufferOut().removeOperation(operation);
        			//��װ�ص�С���ϵĹ���ӻ�������ɾ��
    				}
    				
    				
    			}
    	/*		else{
    				cell.GetVehicle().reduceTimeWindow();
    			}
    			*/
    		}    
    	}
    }
    
    
    public void scanVehicleForVehicle(){				  //ɨ��С��
    	for(Cell cell : cellSet){     
    		Vehicle cellVehicle = cell.GetVehicle();						//ÿ����Ԫ��С��
    		if(cellVehicle.IsVehicleBack(Timer.currentTime()) == true){
    			cellVehicle.setVehicleAvailable();          //С�������ˣ����ÿ���
    			cellVehicle.rest();
    		}
    		if(cellVehicle.getIdle() == true){          //С����ǰ����
    			ruleBufferOut(cell);                //�����������,�ѻ������������
    			scheduleVehicleForVehicle(cell);                          //��������
    		}
    		
    	/*	if(cellVehicle.getTimeWindowIdle() == true){
    			cell.GetVehicle().reduceTimeWindow();
    		}
    		*/
    		
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
    
    public void scheduleVehicleForVehicle(Cell cell){
    	
    	if(cell.getBufferOut().size() != 0){
    		
    		if(cell.getBufferOut().size() > cell.GetVehicle().getCapacity()){     //��������������С������
    			
    			for(int i = 0;i < cell.GetVehicle().getCapacity();i++){
    				Operation operation = cell.getBufferOut().get(i);
    				cell.GetVehicle().addOperation(operation);
    			//��С�����������Ĺ���װ�ص�С����
    			}
    		//��С����DesTime������
    			List<Integer> desCell = new ArrayList<Integer>();
    			List<Integer> desTime = new ArrayList<Integer>();
    			
    			for(int i = 0;i < cell.GetVehicle().getCapacity();i++){
					desCell.add(cell.getBufferOut().get(i).getSelectedMachine().getNumInCell());
				}
    			
    			desTime.add(cell.GetTransferTime(cell.getBufferOut().get(0).getSelectedMachine().getNumInCell())+Timer.currentTime());
    			
    			Timer.addTrigger(cell.GetTransferTime(cell.getBufferOut().get(0).getSelectedMachine().getNumInCell())+Timer.currentTime());
    			
    			int i,j;
    			for(i = 1;i < cell.GetVehicle().getCapacity();i++){
    				for(j = 0; j<i;j++){
    					if(desCell.get(i) == desCell.get(j)){           //��֮ǰ��Ŀ��Cellһ��
    						desTime.add(desTime.get(j));
    						
    						Timer.addTrigger(j);
    						
    						break;
    					}
    				}
        			         //֮ǰû��һ���
    				if(j == i){
    				//	desTime.add(desTime.get(i-1)+cell.GetTransferTime(cell.getBufferOut().get(0).getSelectedMachine().getNumInCell()));
    					int max = 0;    	    				
	        			for(int k : desTime){
	        				if(k > max)  
	        					max = k;
	        			}
	        	//�ҳ�������󵽴�   time:max  cell:desCell.get(desTime.indexof(max))�ڴ�cell���ҵ���һ��cell---desCell.get(i)��ʱ��
	        			Cell lastCell = new Cell();
	        			for(Cell c : cellSet){
	        				if(c.GetID() == desCell.get(desTime.indexOf(max)))
	        					lastCell = c;
	        			}
	        			//�ҳ�������󵽴��cell
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
    			//��װ�ص�С���ϵĹ�����ӿ�ʼ�����ʱ��
				}
    			int size_size = cell.GetVehicle().getCapacity(); 				
				for(i = 0;i < size_size;i++){
					Operation operation = cell.getBufferOut().get(0);      				
					cell.getBufferOut().removeOperation(operation);
    			//��װ�ص�С���ϵĹ���ӻ�������ɾ��
				}
				
				cell.GetVehicle().setCapacity(0);
				
				cell.GetVehicle().setUseRate(1.0);
				cell.GetVehicle().usePlus();
				
				
				
    		}
    		
    		
    		
    	
    		else if(cell.getBufferOut().size() <= cell.GetVehicle().getCapacity()){		//����������С��С������
    			
    			if(cell.GetVehicle().getTimeWindowIdle() == false){
    			ruleTimeWindow(cell);                 //С��û��ʱ�䴰����Ҫ����һ������ȷ��һ�������ʱ��         				
    			}
    			if((cell.GetVehicle().getTimeWindowIdle() == true)&&(cell.GetVehicle().getTimeWindow() == Timer.currentTime())){	
    			//С����ʱ�䴰��С����ʱ�䴰Ϊ0������Ҫ�ȴ�ֱ������
        		
    				
    				
    				for(int i = 0;i < cell.getBufferOut().size();i++){
    					Operation operation = cell.getBufferOut().get(i);	
        				cell.GetVehicle().addOperation(operation);
        			//��С�����������Ĺ���װ�ص�С����
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
    					}//֮ǰû��һ���
    					if(j == i){
    					//desTime.add(desTime.get(i-1)+cell.GetTransferTime(cell.getBufferOut().get(i).getSelectedMachine().getNumInCell()));
    						int max = 0;    	    				
    	        			for(int k : desTime){
    	        				if(k > max)  
    	        					max = k;
    	        			}
    	        	//�ҳ�������󵽴�   time:max  cell:desCell.get(desTime.indexof(max))�ڴ�cell���ҵ���һ��cell---desCell.get(i)��ʱ��
    	        			Cell lastCell = new Cell();
    	        			for(Cell c : cellSet){
    	        				if(c.GetID() == desCell.get(desTime.indexOf(max)))
    	        					lastCell = c;
    	        			}
    	        			//�ҳ�������󵽴��cell
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
    						
    				cell.GetVehicle().setUseRate((double)cell.getBufferOut().size()/cell.GetVehicle().getCapacity());
    				cell.GetVehicle().usePlus();		
    				
					cell.GetVehicle().setCapacity(cell.GetVehicle().getCapacity()-cell.getBufferOut().size());   
					
    								
    				for(i = 0;i < cell.getBufferOut().size();i++){
    					cell.getBufferOut().get(i).setStartTransferTime(Timer.currentTime());
        			//��װ�ص�С���ϵĹ�����ӿ�ʼ�����ʱ��
    				}
    				
    				int size = cell.getBufferOut().size(); 				
    				for(i = 0;i < size;i++){
    					Operation operation = cell.getBufferOut().get(0);      				
    					cell.getBufferOut().removeOperation(operation);
        			//��װ�ص�С���ϵĹ���ӻ�������ɾ��
    					
    					
    				}
    				
    				
    			}
    	/*		else{
    				cell.GetVehicle().reduceTimeWindow();
    			}
    			*/
    		}
    	}
    }
//扫描小车   
    public void scanVehicle(){				  //ɨ��С��
    	for(Cell cell : cellSet){     
    		Vehicle cellVehicle = cell.GetVehicle();						//ÿ����Ԫ��С��
    		if(cellVehicle.IsVehicleBack(Timer.currentTime()) == true){
    			cellVehicle.setVehicleAvailable();          //С�������ˣ����ÿ���
    			cellVehicle.rest();
    		}//小车已经回来
    		if(cellVehicle.getIdle() == true){          //С����ǰ����
    			ruleBufferOut(cell);                //�����������
    			scheduleVehicle(cell);                          //��������
    		}
    		
    	/*	if(cellVehicle.getTimeWindowIdle() == true){
    			cell.GetVehicle().reduceTimeWindow();
    		}
    		*/
    		
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
    //	int c=0;
    	if(Timer.currentTime() == 0){
			for (Job job : jobSet) {
			              //ÿһ��������ͬһ�����򣬴˴�ѡ�����Ľӿڣ��ɻ���job��ʼ����ʱ���
			//	System.out.print(c++ +"   ");
			//System.out.println(job.getId());
				
			//jobRule(job.getJobRule(),job.getNextScheduleOperation());   //����jobruleΪ����ѡ�����
				jobRule((IJobRule)jChromosome.getEntityRule(2,job.getId()),job.getNextScheduleOperation()); 
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
    		cell.GetVehicle().restForVehicle();
    	}
    	Timer.resetTimer();
    }
   
    
    /*public void reset() {
        super.reset();
        unArriJobs.clear();
        for (Job job : jobSet) {
            unArriJobs.add(job);
        }
    }
     */
    
//调度方法
	public void schedule() {		

		boolean state = false;
		double makeSpan = 0;
		//Makespan m = new Makespan();
		
		
		//reset();  //   ！！！！！！！！！！！！！！！！！！！！！！！！！！！
		
		
		
		
		
		Timer.resetTimer();		
		//分配job第一道工序
		scheduleStart();
		
		//System.out.println("11111********");
	//	for (Job job : jobSet) {
	//		System.out.println(job.getNextScheduleOperation());
	//	}
	//	
		scanMachine();	
		
		
	//	System.out.println("2222********");
	//	for (Job job : jobSet) {
	//		System.out.println(job.getNextScheduleOperation());
	//	}
	//	System.out.println("222********");
		
		scanVehicle();					
		
	//	System.out.println("3333********");
	//	for (Job job : jobSet) {
	//		System.out.println(job.getNextScheduleOperation());
	//	}
	//	System.out.println("3333********");
int i = 1;
		while(!state){		
			
			/*Timer.stepTimer();
			System.out.println(i+"00000");
			for (Job job : jobSet) {
				System.out.println(job.getNextScheduleOperation());
			}
			System.out.println(i+"0000");
			
			
			scanMachine();            //scanmachine
			System.out.println(i+"1111");
			for (Job job : jobSet) {
				System.out.println(job.getNextScheduleOperation());
			}
			System.out.println(i+"1111");
			
			
			
			
			scanVehicle();	          //scanVehicle
			System.out.println(i+"22222");
			for (Job job : jobSet) {
				System.out.println(job.getNextScheduleOperation());
			}
			System.out.println(i+"22222");
			
			
			state = isEnd();	      // state
			//System.out.println(i+"33333");
		//	for (Job job : jobSet) {
		//		System.out.println(job.getNextScheduleOperation());
		///	}
		//	System.out.println(i+"33333");
			*/
			Timer.stepTimer();
			scanMachine();            //scanmachine
			scanVehicle();	          //scanVehicle
			state = isEnd();
			
			i++;
		
		}
	//	makeSpan = m.getMeasurance(this);
	//	System.out.println("Finish"+makeSpan);	
				
	//	printFinalInfo();
		
	}
	
	public void scheduleForVehicle() {		//һ�ξ���ĵ���

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
		scanVehicleForVehicle();						//ɨ��С��

		while(!state){		
			Timer.stepTimer();
			scanMachine();
			scanVehicleForVehicle();	
			state = isEnd();			
		}
	//	makeSpan = m.getMeasurance(this);
	//	System.out.println("Finish"+makeSpan);	
/*		
		for(Cell cell : cellSet){
			System.out.println(cell.GetVehicle().getUseTime());
			for(Double i : cell.GetVehicle().getUseRate()){
				System.out.println(i+"\t");
			}
		}
		
*/		
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
