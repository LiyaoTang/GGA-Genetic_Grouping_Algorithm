package com.fay.rule.MachineRule;

import java.util.List;



import com.fay.domain.Machine;
import com.fay.domain.Operation;

public class MachineWEDD implements IMachineRule {

//	@Override
    public double calPrio(Operation operation) {
        return operation.getJob().getWeight() / operation.getJob().getDueDate();
    }

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
