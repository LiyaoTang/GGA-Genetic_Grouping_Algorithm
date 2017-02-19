package com.lm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.lm.algorithms.SimpleScheduler;
import com.lm.algorithms.SchedulerForOneDestinCell;
import com.lm.algorithms.ga.HSGA;
import com.lm.algorithms.ga.TS;
import com.lm.algorithms.measure.IMeasurance;
import com.lm.algorithms.measure.Makespan;
import com.lm.algorithms.measure.TotalWeightedTardiness;
import com.lm.algorithms.rule.machine.IMachineRule;
import com.lm.algorithms.rule.machine.MachineEDD;
import com.lm.algorithms.rule.machine.MachinePT_TIS;
import com.lm.algorithms.rule.machine.MachineSPT;
import com.lm.algorithms.rule.machine.MachineSRPT;
import com.lm.algorithms.rule.transportor.ITransportorRule;
import com.lm.algorithms.rule.transportor.TransEDD;
import com.lm.algorithms.rule.transportor.TransFIFO;
import com.lm.algorithms.rule.transportor.TransGP1;
import com.lm.algorithms.rule.transportor.TransGP2;
import com.lm.algorithms.rule.transportor.TransOperAndTrans;
import com.lm.algorithms.rule.transportor.TransOpersAndFIFO;
import com.lm.data.CMSReader;
import com.lm.data.MetaCMSReader;
import com.lm.data.MetaCMSReaderForLooseDueDate;
import com.lm.domain.Cell;
import com.lm.domain.CellSet;
import com.lm.domain.JobSet;
import com.lm.domain.Machine;
import com.lm.domain.MachineSet;
import com.lm.statistic.MySummaryStat;
import com.lm.statistic.RuleFrequencyStatistic;
import com.lm.util.Constants;

public class Main {
	
//	private static Logger log = LoggerHelper.getLogger("Main");
	private static MetaCMSReader dr;
	private static MetaCMSReaderForLooseDueDate r;
//	private static CMSReader dr;
	static IMeasurance makespan = new Makespan();
	static IMeasurance TWT 		= new TotalWeightedTardiness();
	
	/**
	 * @Description 主函数入口
	 * @param args
	 * @throws IOException
	 * @throws CloneNotSupportedException
	 */
	public static void main(String[] args) throws IOException,
			CloneNotSupportedException {
//		RulePriors();
//		bestRule_twt();
	    HSGA(TWT);
//		TS(TWT);
//		FullFactorExperiment(TWT);
//	    TransPriors();
	}

	/**
	 * @throws IOException 
	 * @throws CloneNotSupportedException 
	 * @Description 把生成的规则组合转换成启发式序列
	 */
	private static void TransPriors() throws IOException, CloneNotSupportedException {
		 for (int caseNo = 0; caseNo < Constants.TOTAL_PROBLEMS; caseNo++) {
				dr = new MetaCMSReader("data/Trans/CACO1/" + (caseNo + 1));
		        
				File file = new File(Constants.RULESQUENCE_DIR+"/"+(caseNo+1));
		        BufferedReader reader  = new BufferedReader(new FileReader(file));
		        String line;			
		        for(int TransNo =0; TransNo < 24; TransNo++) {
					MachineSet machineSet = dr.getMachineSet();
			        JobSet jobSet= dr.getJobSet();
			        CellSet cellSet=dr.getCellSet();
			        SimpleScheduler simpleScheduler = new SimpleScheduler(machineSet, jobSet ,cellSet);
			        Constants.RULE_SEQUENCES_COUNT = TransNo+1;
			       
			        //read the rule
					line = reader.readLine();
					String[] seq  = line.split("\\s++");
					String[] mchs = seq[2].split(",");
					String[] trans = seq[3].split(",");
					// Set the rule
					for(int i= 0 ; i < machineSet.size(); i ++ ){
			        	Machine m = machineSet.get(i);
			        
			        	m.setRule(Constants.mGPRules[
			        	                           Integer.parseInt(mchs[i])
			        	          ]);
			        	
			        }
		        	for(int i= 0 ; i < cellSet.size(); i ++ ){
				        Cell c = cellSet.get(i);
			        	c.setRule(Constants.TRules[
			   			        	            Integer.parseInt(trans[i])
			   			        	          ]
			   			          );
		        	}
			        //get origin ones
			        String origin = Constants.RULESET_DIR;
	//		        Constants.RULE_SEQUENCES_COUNT = 1;
			        Constants.RULESET_DIR +="/"+(caseNo+1);
			        simpleScheduler.scheduleWithCaseNo(caseNo,TransNo);
			        Constants.RULESET_DIR= origin;
				}
		        System.out.println("success for"+(caseNo+1));
		 }
	}

	/**
	 * 生成各种基于启发式信息的优先级序列
	 * @param bia
	 * @throws IOException
	 * @throws CloneNotSupportedException 
	 */
	static void RulePriors() throws IOException, CloneNotSupportedException {

		dr = new MetaCMSReader("data/Trans/HSGA/1");
		MachineSet machineSet = dr.getMachineSet();
		JobSet jobSet= dr.getJobSet();
		CellSet cellSet=dr.getCellSet();

		//set machine rule
		for(Machine m:machineSet){
			m.setRule(Constants.mGPRules[0]);
		}
		//set vehicle rule
		for(Cell c:cellSet){	
			c.setRule(new TransFIFO());
		}

		SimpleScheduler simpleScheduler = new SimpleScheduler(machineSet, jobSet ,cellSet);
		simpleScheduler.schedule();
		double CurPerf=makespan.getMeasurance(simpleScheduler);

		System.out.print(CurPerf+"\t");
		System.out.print("\n");

	}
	/**
	 * 模拟GA进化-使用默认参数
	 * @param measure
	 * @param machineSet
	 * @param jobSet
	 * @param stageSet
	 * @param stat
	 * @param caseNo
	 * @throws CloneNotSupportedException 
	 */
	static void simulationGA(IMeasurance measure,MySummaryStat stat,MachineSet machineSet, JobSet jobSet,CellSet cellSet, int caseNo) throws CloneNotSupportedException {
		simulationGA(measure,stat, machineSet, jobSet, cellSet, 0.6, 0.18, 10, 48, caseNo);
    }
	
	static void simulationTS(IMeasurance measure,MySummaryStat stat,MachineSet machineSet, JobSet jobSet,CellSet cellSet, int caseNo) throws CloneNotSupportedException {
		simulationTS(measure,stat, machineSet, jobSet, cellSet, 100,  caseNo);
    }

	/**
	 * @Description 模拟GA进化-带参数的 
	 * @param measure
	 * @param stat
	 * @param machineSet
	 * @param jobSet
	 * @param cellSet
	 * @param pc	crossProb
	 * @param pm	mutationProb
	 * @param gm	maxGeneration
	 * @param ps	populationSize
	 * @throws CloneNotSupportedException
	 */
	static void simulationGA(IMeasurance measure,MySummaryStat stat, MachineSet machineSet, JobSet jobSet,CellSet cellSet,
			   					double pc,double pm, int gm, int ps, int caseNo) throws CloneNotSupportedException {
		double meanperformance = 0, totalperformance = 0, totalTime = 0, meanTime = 0;
		for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {

			SimpleScheduler scheduler = new SimpleScheduler(machineSet, jobSet,cellSet);
			HSGA ga = new HSGA(machineSet, jobSet, cellSet, scheduler, measure, pc, pm, gm, ps);
			ga.setStat(new RuleFrequencyStatistic());

			long start = System.currentTimeMillis();

			ga.schedule(caseNo);

			totalTime += (System.currentTimeMillis() - start);
			totalperformance += ga.getFunctionValue();
			System.out.println(ga.getFunctionValue());

		}//end for instances

		System.out.println("mean time: " + totalTime/Constants.INSTANCES_PER_PROBLEM);
		System.out.println("mean func: " + totalperformance/Constants.INSTANCES_PER_PROBLEM);
	}
	
	static void simulationTS(IMeasurance measure,MySummaryStat stat, MachineSet machineSet, JobSet jobSet,CellSet cellSet,
				 int ps, int caseNo) throws CloneNotSupportedException {
				for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {

				SchedulerForOneDestinCell scheduler = new SchedulerForOneDestinCell(machineSet, jobSet,cellSet);
				TS ts = new TS(machineSet, jobSet, cellSet, scheduler, measure,
						 ps);
				ts.setStat(new RuleFrequencyStatistic());

				double meanperformance = 0, totalperformance = 0, totalTime = 0, meanTime = 0;
				for (int i = 0; i < Constants.REPLICATIONS_PER_INSTANCE; i++) {
					long start = System.currentTimeMillis();
					ts.schedule(caseNo);
					totalTime += (System.currentTimeMillis() - start);
					totalperformance += ts.getFunctionValue();
					System.out.println("func: " + ts.getFunctionValue());
				}//end for replications

				meanperformance = totalperformance
						/ Constants.REPLICATIONS_PER_INSTANCE;
				meanTime = totalTime / Constants.REPLICATIONS_PER_INSTANCE;
				stat.value(meanperformance);
				stat.addTime(meanTime);
				stat.setParameter(ts.parameter());
				//System.out
				//.println(ins + "\t" + ga.parameter() + "\t"
				//        + measure.toString() + "\t" + meanperformance + "\t"
				//        + meanTime);
				}//end for instances
	}
	
	
	
	/**
	 * 
	 * @param measure GP+GA实验
	 * @throws IOException
	 * @throws CloneNotSupportedException 
	 */
	static void HSGA(IMeasurance measure) throws IOException, CloneNotSupportedException {
		    StringBuilder resultFileName = new StringBuilder(80);
	        resultFileName.append(Constants.RULESQUENCE_DIR).append(
	                new Throwable().getStackTrace()[0].getMethodName());

	        for (int caseNo = 0; caseNo < 20; caseNo++) {
				System.out.println();
				System.out.println("case" + (caseNo+1));
	        	dr = new MetaCMSReader(Constants.CMS_SOURCE+(caseNo+1));
//	        	r = new MetaCMSReaderForLooseDueDate(Constants.CMS_SOURCE+(caseNo+1));
				MachineSet machineSet = dr.getMachineSet();
		        JobSet jobSet= dr.getJobSet();
		        CellSet cellSet= dr.getCellSet();
		        
	           MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
	           simulationGA(measure,stat,machineSet,jobSet,cellSet,caseNo+1);
	           printResult(resultFileName.toString(), stat);
	        }
	}
	
	static void TS(IMeasurance measure) throws IOException, CloneNotSupportedException {
	    StringBuilder resultFileName = new StringBuilder(80);
        resultFileName.append(Constants.RULESQUENCE_DIR).append(
                new Throwable().getStackTrace()[0].getMethodName());
        System.out.println("TS");
        
        for (int caseNo = 0; caseNo < 20; caseNo++) {
			System.out.println();
			System.out.println("case"+(caseNo+1)+":");
        	dr = new MetaCMSReader(Constants.CMS_SOURCE+(caseNo+1));
	       	MachineSet machineSet = dr.getMachineSet();
	        JobSet jobSet= dr.getJobSet();
	        CellSet cellSet=dr.getCellSet();
	        
           MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
           simulationTS(measure,stat,machineSet,jobSet,cellSet,caseNo+1);
           printResult(resultFileName.toString(), stat);
        }
}
	 
	/**
	 * 最优规则组合_衡量twt的
	 * @throws IOException
	 */
	static void bestRule_twt() throws IOException {

	        StringBuilder resultFileName = new StringBuilder(80);
	        resultFileName.append("result/BestRule/").append(
	                new Throwable().getStackTrace()[0].getMethodName());
	        System.out.println(resultFileName.toString());


	    	/** 机器选工件调度规则 */
	        IMachineRule[] mRule = { new MachinePT_TIS(), new MachineSPT(),
	                				 new MachineSRPT(),new MachineEDD() };
	        /**Transportor调度规则*/
	        ITransportorRule[] TRules = {new TransOperAndTrans(),new TransFIFO(),
	        							 new TransEDD(),new TransOpersAndFIFO()};
	     

	        printTitle(resultFileName.toString());
	        for (int i = 0; i < 4; i++) {

	            for (int caseNo = 0; caseNo < Constants.TOTAL_PROBLEMS; caseNo++) {
	            	MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);

//	            	dr = new CMSReader("data/Stage1/" + (caseNo + 1));//for test
	            	

	            	MetaCMSReader dr = new MetaCMSReader("data/Trans/Case1/" + (caseNo + 1));
	            	MachineSet testmachineSet = dr.getMachineSet();
	        		JobSet testjobSet = dr.getJobSet();
	        		CellSet testcellSet =dr.getCellSet();

	                double totalperformance = 0, totalTime = 0, meanTime = 0;
	                for (int in = 0; in < Constants.INSTANCES_PER_PROBLEM; in++) {
	                    for (Machine machine : testmachineSet) {
	                        machine.setRule(mRule[i]);
	                    }
	                    for (Cell c : testcellSet) {
	                        c.setRule(TRules[i]);
	                    }
	                    SimpleScheduler scheduler = new SimpleScheduler(testmachineSet, testjobSet ,testcellSet);
	                    
	                    long start = System.currentTimeMillis();	                    
	                    scheduler.schedule();
	                    totalTime += (System.currentTimeMillis() - start);
	                    
//	                    double CurPerf=makespan.getMeasurance(scheduler);//makespan
	                    double CurPerf=TWT.getMeasurance(scheduler);//getTotalWeightedTardiness
//	                    double CurPerf=scheduler.getMakespan();
	                    totalperformance += CurPerf;
			            
	                    System.out.println(caseNo+"_in"+"\t" + CurPerf + "\t");
	            
	                    stat.value(CurPerf);
	                }
		            meanTime = totalTime / Constants.INSTANCES_PER_PROBLEM;
		            stat.addTime(meanTime);
		            stat.setParameter("bestRule 无参数");

	            printResult(resultFileName.toString(), stat);
	            }//end for every case
	        }//end for each rule's component
	    }
	  
	/**
	 * 全因子(ANOVA)实验
	 * @param measure
	 * @throws IOException
	 * @throws CloneNotSupportedException 
	 */
	static void FullFactorExperiment(IMeasurance measure) throws IOException, CloneNotSupportedException {

	        StringBuilder resultFileName = new StringBuilder(80);
	        resultFileName.append("result/ANOVA/")
	                .append(new Throwable().getStackTrace()[0].getMethodName())
	                .append("+").append(measure.toString());
	        System.out.println(resultFileName.toString());

	        BufferedWriter br = new BufferedWriter(new FileWriter(
	                resultFileName.toString()));

	        /*
	         * 全因子组合
	         */
//	        int[] ps = { 6, 12, 24, 48 };
//	        double[] pc = { 0.05, 0.3, 0.6, 0.9 };
//	        double[] pm = { 0, 0.02, 0.1, 0.18 };
//	        int[] gm = { 25, 50, 100 };
	        int[] ps = { 12 };
	        double[] pc = { 0.9};
	        double[] pm = { 0.3};
	        int[] gm = { 100 };
	        printTitle(resultFileName.toString());
	        for (int iPs = 0; iPs < ps.length; iPs++) {
	            for (int iPc = 0; iPc < pc.length; iPc++) {
	                for (int iPm = 0; iPm < pm.length; iPm++) {
	                    for (int iGm = 0; iGm < gm.length; iGm++) {
	                        for (int caseNo = 0; caseNo < Constants.TOTAL_PROBLEMS; caseNo++) {
	                        	MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
	                            dr = new MetaCMSReader("data/Trans/Case1/" + (caseNo + 1));
	                            MachineSet machineSet = dr.getMachineSet();
	        	        		JobSet jobSet = dr.getJobSet();
	        	        		CellSet cellSet =dr.getCellSet();
	                            for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
	                            	simulationGA(measure,stat, machineSet, jobSet,
	                                		cellSet, pc[iPc], pm[iPm], gm[iGm],
	                                        ps[iPs],0);
	                            }
	                            printDOEResult(br, stat);
	                        }
	                    }
	                }
	            }
	        }

	    }
	
	/**
	 * 输出数据头到文件中
	 * @param fileName：文件名
	 * @param stats：当前统计的数据
	 */
	private static void printTitle(String fileName) {
		BufferedWriter br = null;
		try {
			br = new BufferedWriter(new FileWriter(fileName));
			br.write(MySummaryStat.TABLE_TAG);
			br.newLine();
			br.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出结果到文件中
	 * @param fileName：文件名
	 * @param stats：当前统计的数据
	 */
	private static void printResult(String fileName,MySummaryStat stat) {
		BufferedWriter br = null;
		try {
			br = new BufferedWriter(new FileWriter(fileName,true));
			br.write(stat.toString());
			br.newLine();

			br.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出全因子结果到文件缓存中
	 * @param br
	 * @param stat
	 */
	private static void printDOEResult(BufferedWriter br, MySummaryStat stat) {
		        try {
		            br.write(stat.DOEString());
		            br.newLine();
		            br.flush();
		        }
		        catch (IOException e) {
		            e.printStackTrace();
		        }
	  }
}
