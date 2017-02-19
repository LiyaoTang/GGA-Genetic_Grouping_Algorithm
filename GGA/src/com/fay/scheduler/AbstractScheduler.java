package com.fay.scheduler;
import java.io.IOException;
import com.fay.util.Timer;

import com.fay.GGA.Chromosome;
import com.fay.comparaEx.chromosome;
import com.fay.domain.CellSet;
import com.fay.domain.JobSet;
import com.fay.domain.MachineSet;

public  abstract class AbstractScheduler {

	public JobSet jobSet;

	protected MachineSet machineSet;

    protected CellSet cellSet;
    
    public Chromosome mChromosome;
    public Chromosome jChromosome;
    public Chromosome bChromosome;
    
	public AbstractScheduler(MachineSet machineSet, JobSet jobSet,CellSet cellSet
	,Chromosome m,Chromosome j ,Chromosome b	) {
		this.jobSet = jobSet;
		this.machineSet = machineSet;
		this.cellSet = cellSet;
		
		this.mChromosome = m;
		this.jChromosome = j;
		this.bChromosome = b;
		
		Timer.startTimer();
	}

	public abstract void schedule();
	
	
	public void reset() {
		jobSet.reset();
		machineSet.reset();
		cellSet.reset();
		mchromosome.clearIndex();
		bchromosome.clearIndex();
		jchromosome.clearIndex();
	}

}
