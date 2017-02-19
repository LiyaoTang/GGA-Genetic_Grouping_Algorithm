package com.fay.rule.TimeWindowRule;

import com.fay.domain.Cell;
import com.fay.domain.Job;
import com.fay.domain.Operation;
import com.fay.rule.IRule;

public interface ITimeWindowRule extends IRule{
	
	public double calPrio(Cell cell);
	
}
