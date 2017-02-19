package com.fay.rule.JobRule;

import com.fay.domain.Machine;
import com.fay.domain.Operation;


public class JobLPT implements IJobRule {

    //@Override
	public double calPrio(Operation operation , Machine m) {
	        return  operation.getProcessingTime(m);
	}
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
