package com.fay.rule.JobRule;

import com.fay.domain.Machine;
import com.fay.domain.Operation;

public class JobFA implements IJobRule {

    //@Override
    public double calPrio(Operation operation, Machine m) {
        return -m.getNextIdleTime();
    }
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
