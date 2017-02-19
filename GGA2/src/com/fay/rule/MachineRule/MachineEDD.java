package com.fay.rule.MachineRule;


import com.fay.domain.Cell;
import com.fay.domain.Job;
import com.fay.domain.Operation;


public class MachineEDD implements IMachineRule {
    
	public double calPrio(Operation operation){
    	return -operation.getJob().getDuedate();
    }
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
