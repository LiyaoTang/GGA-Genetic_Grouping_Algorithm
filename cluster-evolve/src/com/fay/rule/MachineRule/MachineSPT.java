package com.fay.rule.MachineRule;

import java.util.List;

import com.fay.domain.Operation;
import com.fay.domain.Machine;

public class MachineSPT implements IMachineRule {

//    @Override
    public double calPrio(Operation operation) {
        return -operation.getProcessingTime();
    }


    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
