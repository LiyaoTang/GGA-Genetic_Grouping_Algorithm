package com.fay.rule.BufferOutRule;


import com.fay.domain.Cell;
import com.fay.domain.Job;
import com.fay.domain.Operation;


public class BufferOutWT implements IBufferOutRule {
    
	public double calPrio(Cell cell,Operation operation){
    	return -operation.getArrivalBufferOutTime();
    }
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
