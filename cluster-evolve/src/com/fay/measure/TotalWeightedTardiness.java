package com.fay.measure;

import com.fay.scheduler.AbstractScheduler;
import com.fay.domain.Job;

public class TotalWeightedTardiness implements IMeasurance {

    //@Override
    public double getMeasurance(AbstractScheduler scheduler) {
		double twt = 0.0f;
		for (Job job : scheduler.jobSet) {
			//System.out.println("Job"+job.getId()+":"+job.getFinishedTime());
			twt += job.getTardiness() * job.getWeight();
		}
		//System.out.println("twt"+"=="+twt);
		return twt;
    }


}
