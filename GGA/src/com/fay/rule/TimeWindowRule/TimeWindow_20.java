package com.fay.rule.TimeWindowRule;

import com.fay.domain.Cell;
import com.fay.domain.Job;

public class TimeWindow_20 implements ITimeWindowRule{
	
	public double calPrio(Cell cell){
		return 20;
	}
	public String toString() {
        return getClass().getSimpleName();
    }
}
