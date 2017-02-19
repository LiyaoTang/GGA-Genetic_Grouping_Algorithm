package com.fay.rule.MachineRule;

import java.util.List;

import com.fay.domain.Cell;
import com.fay.domain.Operation;
import com.fay.domain.Machine;

public class MachineSPTR implements IMachineRule {

//    @Override
    public double calPrio(Operation operation) {
        return -operation.getJob().getProcessRatio();
    }


    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
