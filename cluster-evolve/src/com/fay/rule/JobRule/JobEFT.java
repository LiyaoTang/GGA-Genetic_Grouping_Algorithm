package com.fay.rule.JobRule;
import com.fay.domain.Machine;
import com.fay.domain.Operation;

public class JobEFT implements IJobRule {

	public double calPrio(Operation operation , Machine m) {
	        return - (m.getNextIdleTime() + operation.getProcessingTime(m));
	}
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
