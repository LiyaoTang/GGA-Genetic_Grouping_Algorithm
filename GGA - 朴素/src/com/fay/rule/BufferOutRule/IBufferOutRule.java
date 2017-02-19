package com.fay.rule.BufferOutRule;


import com.fay.rule.IRule;
import com.fay.domain.Cell;
import com.fay.domain.Job;
import com.fay.domain.Operation;

public interface IBufferOutRule extends IRule {
	 public double calPrio(Cell cell,Operation operation);
}
