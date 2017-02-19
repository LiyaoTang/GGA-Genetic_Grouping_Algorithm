package com.lm.algorithms;

import java.io.IOException;

import com.lm.Metadomain.CellSet;
import com.lm.Metadomain.JobSet;
import com.lm.Metadomain.MachineSet;
import com.lm.algorithms.abc.Chromosome;

/**
 * @Description schedule class for all job shop problems in evaluation process

 * @author:lm

 * @time:2014-12-22 下午05:46:43

 */

public abstract class AbstractMetaScheduler {
/***************************属性域***********************************************************************/
	/**job's set **/
	public JobSet jobSet;
	/**machine's set **/
	protected MachineSet machineSet;
	/**cell's set **/
    protected CellSet cellSet;
    

/***************************方法域***********************************************************************/
    /**
	 * @Description construct functions
	 * @param machineSet
	 * @param jobSet
	 * @param cellSet
	 * @exception:
	 */
	public AbstractMetaScheduler(MachineSet machineSet, JobSet jobSet,CellSet cellSet) {
		this.jobSet =jobSet;
		this.cellSet=cellSet;
		this.machineSet=machineSet;
		
	}

	/**
	 * @throws IOException 
	 * @Description this Concrete schedule process for different job shop problems 
	 */
	public abstract void schedule() throws IOException;
	
	/**
	 * @Description reset the state for all attributes
	 */
	public void reset() {
		jobSet.reset();
		machineSet.reset();
		cellSet.reset();
	}

	
	/**
	 * schedule with the strategy
	 * @param chromosome
	 */
	public void scheduleWithStrategy(Chromosome chromosome) {
		// TODO Auto-generated method stub
		
	}
	
	/** 
	public int getMakespan() {
		int max = Integer.MIN_VALUE;
		for (Job job : jobSet) {
			max = Math.max(max, job.getFinishedTime());
		}
		return max;
	}

	public double getTotalWeightedTardiness() {
		//统计twt结果
		double twt = 0.0f;
		for (Job job : jobSet) {
			twt += job.getTardiness() * job.getWeight();
		}
		return twt;
	}
	 **/
}
