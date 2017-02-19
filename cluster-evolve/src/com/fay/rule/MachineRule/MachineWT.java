package com.fay.rule.MachineRule;


import com.fay.domain.Cell;
import com.fay.domain.Job;
import com.fay.domain.Operation;


public class MachineWT implements IMachineRule {
    
	public double calPrio(Operation operation){
    	return -operation.getArrivalTime();
    }
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
