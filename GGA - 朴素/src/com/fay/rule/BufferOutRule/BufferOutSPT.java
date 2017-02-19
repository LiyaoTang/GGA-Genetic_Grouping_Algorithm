package com.fay.rule.BufferOutRule;

import java.util.List;

import com.fay.domain.Cell;
import com.fay.domain.Operation;
import com.fay.domain.Machine;

public class BufferOutSPT implements IBufferOutRule {

//    @Override
    public double calPrio(Cell cell,Operation operation) {
        return -operation.getProcessingTime();
    }


    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
