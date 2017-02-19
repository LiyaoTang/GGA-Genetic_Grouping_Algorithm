package com.fay.rule.MachineRule;

import java.util.List;

import com.fay.domain.Machine;
import com.fay.domain.Operation;

public class MachineWSPT implements IMachineRule {

//	@Override
    public double calPrio(Operation operation) {
        return operation.getJob().getWeight() / operation.getProcessingTime();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
