package com.fay.rule.MachineRule;

import com.fay.util.Timer;
import com.fay.domain.Cell;
import com.fay.domain.Operation;

//ATC

public class MachineCOVERT implements IMachineRule {
    
	public double calPrio(Operation operation){
		double w = operation.getJob().getWeight();
	    int  p  = 	operation.getProcessingTime();
		
		int d= operation.getJob().getDueDate();
		int dpt = Math.max(d-p-Timer.currentTime(),0);
		int dpt1 = Math.max(1-dpt,0);
		
		double ans =( w/p)*dpt1 ;
    	return -ans;
    }
	
	
	
	
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
