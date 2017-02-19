package com.fay.rule.MachineRule;

import com.fay.util.Timer;
import com.fay.domain.Cell;
import com.fay.domain.Job;
import com.fay.domain.Operation;

//ATC

public class MachineATC implements IMachineRule {
    
	public double calPrio(Operation operation){
		double w = operation.getJob().getWeight();
	    int  p  = 	operation.getProcessingTime();
		int k=3;
		int d= operation.getJob().getDueDate();
		int dpt = Math.max(d-p-Timer.currentTime(),0);
		double ans =( w/p)* Math.exp(-dpt/(k*p)) ;
    	return -ans;
    }
	
	
	
	
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
