package com.fay.rule.MachineRule;

import java.util.List;




import com.fay.domain.Machine;
import com.fay.domain.Operation;
import com.fay.util.Timer;

/** Shorest Remaining Processing Time */
public class MachineSRPT implements IMachineRule {

//    @Override
    public double calPrio(Operation operation) {
        return -(operation.getJob().getDuedate()-Timer.currentTime());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
