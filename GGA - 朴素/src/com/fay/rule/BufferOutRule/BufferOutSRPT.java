package com.fay.rule.BufferOutRule;

import java.util.List;





import com.fay.domain.Cell;
import com.fay.domain.Machine;
import com.fay.domain.Operation;
import com.fay.util.Timer;

/** Shorest Remaining Processing Time */
public class BufferOutSRPT implements IBufferOutRule {

//    @Override
    public double calPrio(Cell cell,Operation operation) {
        return -(operation.getJob().getDuedate()-Timer.currentTime());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
