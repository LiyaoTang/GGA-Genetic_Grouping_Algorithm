package ec.app.myApp;

import java.io.IOException;

import com.lm.algorithms.measure.TotalWeightedTardiness;
import com.lm.algorithms.rule.machine.IMachineRule;
import com.lm.algorithms.rule.machine.MachineGP;
import com.lm.algorithms.rule.machine.MachineSPT;
import com.lm.data.CMSReader;
import com.lm.domain.CellSet;
import com.lm.algorithms.SimpleScheduler;
import com.lm.algorithms.rule.machine.IMachineRule;
import com.lm.algorithms.rule.machine.MachineGP;
import com.lm.algorithms.rule.transportor.ITransportorRule;
import com.lm.algorithms.rule.transportor.TransGP;
import com.lm.algorithms.rule.transportor.TransOperAndTrans;
import com.lm.domain.Cell;
import com.lm.domain.CellSet;
import com.lm.domain.JobSet;
import com.lm.domain.Machine;
import com.lm.domain.MachineSet;
import com.lm.data.CMSReader;
import com.lm.util.Constants;
import com.lm.util.Utils;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;

public class EvolveBatchingRule extends GPProblem implements SimpleProblemForm {

    public static final String P_DATA = "data";


    // 所有输入参数的定义，GPNode X Y直接链接至此
    public double duedate;
    public double procTime;
	public double transTime;
    public double releaseTime;
	public double SumNum;
	public double IntervalTime;
   
	public DoubleData input;




    public Object clone() {
        EvolveBatchingRule newobj = (EvolveBatchingRule) super.clone();
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
            for (int caseNo = 19; caseNo < Constants.TOTAL_PROBLEMS; caseNo += 1) {

            	CMSReader dr = new CMSReader(""+(caseNo + 1));
				
				MachineSet machineSet = dr.getMachineSet();
				JobSet jobSet = dr.getJobSet();
				CellSet cellSet =dr.getCellSet();
				
				/** 
				 * machine默认采用SPT();
				 */
				IMachineRule mrule =new MachineSPT();
				for (Machine machine : machineSet) {
					machine.setRule(mrule);
				}
				for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
					count++;
					ITransportorRule rule = new TransGP();
					for (Cell c : cellSet) {
						c.setRule(rule);
					}
					SimpleScheduler scheduler = new SimpleScheduler(
							machineSet, jobSet, cellSet);

					// set GPInfo
					SimpleScheduler.GPInfo.ind = ind;
					SimpleScheduler.GPInfo.input = input;
					SimpleScheduler.GPInfo.batProblem = this;
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
//				if(caseNo==8){
//	            	  Utils.echo("呵呵");//当前断点
//	            }
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
