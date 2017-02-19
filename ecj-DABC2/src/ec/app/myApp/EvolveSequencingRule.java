package ec.app.myApp;

import java.io.IOException;
/**
import com.lqq.algorithms.SimpleScheduler;
import com.lqq.algorithms.rule.machine.IMachineRule;
import com.lqq.algorithms.rule.machine.MachineGP;
import com.lqq.domain.JobSet;
import com.lqq.domain.Machine;
import com.lqq.domain.MachineSet;
import com.lqq.domain.StageSet;
import com.lqq.io.DataReader;
import com.lqq.util.Constants;
**/
import com.lm.algorithms.SchedulerForOneDestinCell;
import com.lm.algorithms.SimpleScheduler;
import com.lm.algorithms.measure.TotalWeightedTardiness;
import com.lm.algorithms.rule.machine.IMachineRule;
import com.lm.algorithms.rule.machine.MachineGP;
import com.lm.algorithms.rule.transportor.ITransportorRule;
import com.lm.algorithms.rule.transportor.TransOperAndTrans;
import com.lm.domain.Cell;
import com.lm.domain.CellSet;
import com.lm.domain.JobSet;
import com.lm.domain.Machine;
import com.lm.domain.MachineSet;
import com.lm.data.CMSReader;
import com.lm.data.MetaCMSReader;
import com.lm.data.MetaCMSReaderForLooseDueDate;
import com.lm.util.Constants;
import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;

public class EvolveSequencingRule extends GPProblem implements
		SimpleProblemForm {

	public static final String P_DATA = "data";

	// 所有输入参数的定义，GPNode X Y直接链接至此

	public double duedate;
	public double weight;
	public double procTime;
	public double opNumber;
	public double remainingProcTime;
	public double releaseTime;
	public double arrivalTime;
	public double fullnessOfBatch;
	public double currentTime;

	public DoubleData input;

	public Object clone() {
		EvolveSequencingRule newobj = (EvolveSequencingRule) super.clone();
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
			
			System.out.println("GP-based    LOOSE"+"\t"+count);
			for (int caseNo = 0; caseNo < Constants.TOTAL_PROBLEMS; caseNo += 1) {
				
//				CMSReader dr = new CMSReader(""+(caseNo + 1));
				MetaCMSReader dr = new MetaCMSReader(Constants.CMS_SOURCE+(caseNo + 1));
//				MetaCMSReaderForLooseDueDate dr = new MetaCMSReaderForLooseDueDate(Constants.GPCMS_SOURCE+(caseNo + 1));
				
				MachineSet machineSet = dr.getMachineSet();
				JobSet jobSet = dr.getJobSet();
				CellSet cellSet =dr.getCellSet();
				
				/** 
				 * cell默认采用TransOperAndTrans();
				 */
				ITransportorRule crule =new TransOperAndTrans();
				for (Cell c : cellSet) {
					c.setRule(crule);
				}
				for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
					count++;
					IMachineRule rule = new MachineGP();
					for (Machine machine : machineSet) {
						machine.setRule(rule);
					}
					/**
					 * 其他的设置默认一组
					 */
//					SchedulerForOneDestinCell scheduler = new SchedulerForOneDestinCell(
//							machineSet, jobSet, cellSet);
//
//					// set GPInfo
//					SchedulerForOneDestinCell.GPInfo.ind = ind;
//					SchedulerForOneDestinCell.GPInfo.input = input;
//					SchedulerForOneDestinCell.GPInfo.seqProblem = this;
//					SchedulerForOneDestinCell.GPInfo.stack = stack;
//					SchedulerForOneDestinCell.GPInfo.state = state;
//					SchedulerForOneDestinCell.GPInfo.subpopulation = subpopulation;
//					SchedulerForOneDestinCell.GPInfo.threadnum = threadnum;
					
					SimpleScheduler scheduler = new SimpleScheduler(
							machineSet, jobSet, cellSet);

					// set GPInfo
					SimpleScheduler.GPInfo.ind = ind;
					SimpleScheduler.GPInfo.input = input;
					SimpleScheduler.GPInfo.seqProblem = this;
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
						System.out.println("case" + caseNo + "\t"+obj);
						dev += inst;
					}
					else{
//						System.out.println("case " + caseNo + " ins " + ins
//								+ " obj " + obj + " ref " + 0 + " inst "
//								+ 0);
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
