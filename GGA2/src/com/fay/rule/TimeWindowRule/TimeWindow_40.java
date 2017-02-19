package com.fay.rule.TimeWindowRule;

import com.fay.domain.Cell;
import com.fay.domain.Job;

public class TimeWindow_40 implements ITimeWindowRule{
	
	public double calPrio(Cell cell){
		return 40;
	}
	public String toString() {
        return getClass().getSimpleName();
    }
}
