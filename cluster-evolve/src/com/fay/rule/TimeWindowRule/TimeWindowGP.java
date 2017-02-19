package com.fay.rule.TimeWindowRule;

import com.fay.domain.Cell;
import com.fay.domain.Job;
import com.fay.domain.Machine;
import com.fay.domain.Operation;
import com.fay.rule.MachineRule.IMachineRule;
import com.fay.scheduler.SimpleScheduler;
import com.fay.util.Timer;

import ec.gp.GPIndividual;

public class TimeWindowGP implements ITimeWindowRule{
	 @Override
	    public String toString() {
	        return getClass().getSimpleName();
	    }

	    public double calPrio(Cell cell) {
	    	 // 绔偣鍙橀噺璧嬪�
	    	
	        SimpleScheduler.GPInfo.timeProblem.BufferNum = cell.getBufferOut().size();
	        if(cell.getNextReadyMachine() == null)	SimpleScheduler.GPInfo.timeProblem.nextDuedate = 1000;
	        else
	        	SimpleScheduler.GPInfo.timeProblem.nextDuedate = cell.getNextReadyMachine().getProcessingOperation().getJob().getDuedate();
	        if(cell.getNextReadyMachine() == null)	SimpleScheduler.GPInfo.timeProblem.nextReadyTime = 0;
	        else
	        	SimpleScheduler.GPInfo.timeProblem.nextReadyTime = cell.getNextReadyMachine().getNextIdleTime() - Timer.currentTime();
	        if(cell.getNextReadyMachine() == null)	SimpleScheduler.GPInfo.timeProblem.nextWeight = 0;
	        else
	        	SimpleScheduler.GPInfo.timeProblem.nextWeight = cell.getNextReadyMachine().getProcessingOperation().getJob().getWeight();
	        if(cell.getNextReadyMachine() == null)	SimpleScheduler.GPInfo.timeProblem.nextRemainTime = 0; 
	        else
	        	SimpleScheduler.GPInfo.timeProblem.nextRemainTime = cell.getNextReadyMachine().getProcessingOperation().getJob().getDuedate() - Timer.currentTime();
	        
	        int sumWeight = 0,sumDueDate = 0,sumRemainTime = 0;
	        for(Operation operation : cell.GetVehicle().getTransingOperation()){
	        	sumWeight +=operation.getJob().getWeight();
	        	sumDueDate +=operation.getJob().getDuedate();
	        	sumRemainTime += operation.getJob().getDuedate() - Timer.currentTime();
	        }
	        SimpleScheduler.GPInfo.timeProblem.sumWeight = sumWeight;
	        SimpleScheduler.GPInfo.timeProblem.sumDuedate = sumDueDate;
	        SimpleScheduler.GPInfo.timeProblem.sumRemainTime = sumRemainTime;
	        
        	SimpleScheduler.GPInfo.timeProblem.currentNum = cell.getProcessingMachine().size();
	        
	        int currentSumWeight = 0,currentSumDueDate = 0,currentSumRemainTime = 0,maxReadyTime = 0;
	        if(cell.getProcessingMachine().size() == 0){
		        SimpleScheduler.GPInfo.timeProblem.currentSumWeight = 0;
		        SimpleScheduler.GPInfo.timeProblem.currentSumDueDate = 0;
		        SimpleScheduler.GPInfo.timeProblem.currentSumRemainTime = 0;
		        SimpleScheduler.GPInfo.timeProblem.nextMaxReadyTime = 0;
	        }
	        else{
	        	for(Machine machine : cell.getProcessingMachine()){		
	        		currentSumWeight +=machine.getProcessingOperation().getJob().getWeight();
	        		currentSumDueDate +=machine.getProcessingOperation().getJob().getDuedate();
	        		currentSumRemainTime +=machine.getProcessingOperation().getJob().getDuedate() - Timer.currentTime();
	        		if(machine.getNextIdleTime() - Timer.currentTime() > maxReadyTime)
	        			maxReadyTime = machine.getNextIdleTime() - Timer.currentTime();
	        	}
	        	SimpleScheduler.GPInfo.timeProblem.currentSumWeight = currentSumWeight;
	        	SimpleScheduler.GPInfo.timeProblem.currentSumDueDate = currentSumDueDate;
	        	SimpleScheduler.GPInfo.timeProblem.currentSumRemainTime = currentSumRemainTime;
	        	SimpleScheduler.GPInfo.timeProblem.nextMaxReadyTime = maxReadyTime;
	        }

	        // 璁＄畻琛ㄨ揪寮忕粨鏋�
	        ((GPIndividual) SimpleScheduler.GPInfo.ind).trees[0].child.eval(
	                SimpleScheduler.GPInfo.state, SimpleScheduler.GPInfo.threadnum,
	                SimpleScheduler.GPInfo.input, SimpleScheduler.GPInfo.stack,
	                (GPIndividual) SimpleScheduler.GPInfo.ind,
	                SimpleScheduler.GPInfo.timeProblem);
	        double result = SimpleScheduler.GPInfo.input.x;
	        if(result < 0)  result = 0;
	        	
	        if(result > 100)
	        	result = 100;
	        return result;
	    }	

}
