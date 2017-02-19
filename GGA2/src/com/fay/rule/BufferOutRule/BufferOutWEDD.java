package com.fay.rule.BufferOutRule;

import java.util.List;




import com.fay.domain.Cell;
import com.fay.domain.Machine;
import com.fay.domain.Operation;

public class BufferOutWEDD implements IBufferOutRule {

//	@Override
    public double calPrio(Cell cell,Operation operation) {
        return operation.getJob().getWeight() / operation.getJob().getDueDate();
    }

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
