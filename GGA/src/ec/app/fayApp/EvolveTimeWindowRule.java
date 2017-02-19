package ec.app.fayApp;

import java.io.IOException;

import com.fay.scheduler.SimpleScheduler;
import com.fay.measure.*;
import com.fay.rule.MachineRule.*;
import com.fay.rule.JobRule.*;
import com.fay.rule.TimeWindowRule.*;
import com.fay.rule.BufferOutRule.*;
import com.fay.domain.*;
import com.fay.CMS.CMSReader;
import com.fay.util.Constants;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;

public class EvolveTimeWindowRule extends GPProblem implements
		SimpleProblemForm {

	public static final String P_DATA = "data";



	public double nextDuedate;
	public double nextWeight;
	public double nextReadyTime;  //鍗曞厓鏈哄櫒涓笉涓洪浂鐨勬満鍣ㄧ殑鏈�棭閲婃斁鏃堕棿
	public double nextMaxReadyTime;  
	public double nextRemainTime;
	public double BufferNum;
	public double sumWeight;
	public double sumDuedate;
	public double sumRemainTime;
	public double currentSumWeight;
	public double currentSumDueDate;
	public double currentSumRemainTime;
	public double currentNum;
	

	public DoubleData input;

	public Object clone() {
		EvolveTimeWindowRule newobj = (EvolveTimeWindowRule) super.clone();
		newobj.input = (DoubleData) input.clone();
		return newobj;
	}

	public void setup(final EvolutionState state, final Parameter base) {
		// very important, remember this
		super.setup(state, base);

		// setup up our input -- don't want to use the default base, it's unsafe
		// here
		input = (DoubleData) state.parameters.getInstanceForParameterEq(
				base.push(P_DATA), null, DoubleData.class);
		input.setup(state, base.push(P_DATA));
	}

	//@Override
	public void evaluate(final EvolutionState state, final Individual ind,
			final int subpopulation, final int threadnum) {
		if (!ind.evaluated) {
			double dev = 0.0;
			int count = 0;
			for (int caseNo = 1; caseNo < Constants.TOTAL_PROBLEMS+1; caseNo ++) {
				
				CMSReader dr = new CMSReader("data/case"+caseNo+".txt");
				
				MachineSet machineSet = dr.getMachineSet();
				JobSet jobSet = dr.getJobSet();
				CellSet cellSet =dr.getCellSet();
				
				for(Machine machine : machineSet){
					machine.setMachineRule(new MachineSPT());
				}
				
				for(Job job : jobSet){
					job.setJobRule(new JobSPT()); 
				}
				
				for(Cell cell : cellSet){
					cell.setBufferOutRule(new BufferOutEDD());
				}
				
				
				for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
					count++;
					ITimeWindowRule rule = new TimeWindowGP();
					for(Cell cell : cellSet){
						cell.GetVehicle().setTimeWindowRule(rule);
					}

					SimpleScheduler scheduler = new SimpleScheduler(
							machineSet, jobSet, cellSet);

					// set GPInfo
					SimpleScheduler.GPInfo.ind = ind;
					SimpleScheduler.GPInfo.input = input;
					SimpleScheduler.GPInfo.timeProblem = this;
					SimpleScheduler.GPInfo.stack = stack;
					SimpleScheduler.GPInfo.state = state;
					SimpleScheduler.GPInfo.subpopulation = subpopulation;
					SimpleScheduler.GPInfo.threadnum = threadnum;

					// start to schedule
					scheduler.schedule();
					//get the answer
					double ref = Reference.REF_Test[caseNo][ins];
					double obj = new TotalWeightedTardiness().getMeasurance(scheduler);//scheduler.getTotalWeightedTardiness();
					if (Double.compare(ref, 0) != 0) {
						double inst = (obj - ref) / ref;
						System.out.println("case" + caseNo+ "\t"+obj);
						dev += inst;
					}
					else{
						System.out.println("case" + caseNo + "\t"+obj);
						dev += obj;
					}
				}
				dev /= count;
			}

			// the fitness better be KozaFitness!
			KozaFitness f = (KozaFitness) ind.fitness;
			f.setStandardizedFitness(state, (float) dev);
			// f.hits = hits;
			ind.evaluated = true;
		}
	}

}
