package com.fay.measure;

import com.fay.scheduler.AbstractScheduler;
import com.fay.domain.Job;

public class Makespan implements IMeasurance {

    //@Override
    public double getMeasurance(AbstractScheduler scheduler) {
    		int max = Integer.MIN_VALUE;
    		for (Job job : scheduler.jobSet) {
    			max = Math.max(max, job.getFinishedTime());
    		}
    		return max;
    }

}
