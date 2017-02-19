package com.fay.rule.JobRule;

import com.fay.domain.Machine;
import com.fay.domain.Operation;


public class JobMA implements IJobRule {

    //@Override
	public double calPrio(Operation operation , Machine m) {
	        return - m.getBufferIn().size();
	}
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
