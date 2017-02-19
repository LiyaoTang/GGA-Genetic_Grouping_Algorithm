package com.fay.rule.JobRule;

import com.fay.rule.IRule;
import com.fay.domain.Machine;
import com.fay.domain.Operation;

public interface IJobRule extends IRule {

	public double calPrio(Operation operation, Machine m);
}
