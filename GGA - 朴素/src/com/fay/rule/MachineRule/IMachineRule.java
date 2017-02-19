package com.fay.rule.MachineRule;

import com.fay.rule.IRule;
import com.fay.domain.Operation;

public interface IMachineRule extends IRule {

    public double calPrio(Operation operation);
}
