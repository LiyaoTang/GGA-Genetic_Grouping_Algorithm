package com.fay.rule.MachineRule;

import com.fay.util.Timer;
import com.fay.domain.Cell;
import com.fay.domain.Job;
import com.fay.domain.Operation;

//ATC

public class MachineMS implements IMachineRule {
    
	public double calPrio(Operation operation){
		double w = operation.getJob().getWeight();
	    int  p  = 	operation.getProcessingTime();
		int d= operation.getJob().getDueDate();
		int dpt = Math.max(d-p-Timer.currentTime(),0);
		
    	return -dpt;
    }
	
	
	
	
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
