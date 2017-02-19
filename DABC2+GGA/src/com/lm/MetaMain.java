package com.lm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.lm.algorithms.MetaHeuristicScheduler;
import com.lm.algorithms.MetaHeuristicScheduler_Notrans;
import com.lm.algorithms.abc.Chromosome;
import com.lm.algorithms.abc.DABC_Hyrid;
import com.lm.algorithms.abc.DABC_InitGP;
import com.lm.algorithms.abc.DABC_MultiPan;
import com.lm.algorithms.abc.DABC_Mutation;
import com.lm.algorithms.abc.DABC5;
import com.lm.algorithms.abc.EM;
import com.lm.algorithms.abc.DABC_Init;
import com.lm.algorithms.abc.DABC_AgingLeader;
import com.lm.algorithms.abc.DABC_Pan;
import com.lm.algorithms.abc.DABC_Random;
import com.lm.algorithms.abc.DABC_Strategy;
import com.lm.algorithms.abc.Tang_SS;
import com.lm.algorithms.measure.Makespan;
import com.lm.algorithms.measure.MetaIMeasurance;
import com.lm.algorithms.measure.TotalWeightedTardiness;
import com.lm.data.MetaCMSReader;
import com.lm.data.MetaCMSReaderForLooseDueDate;
import com.lm.data.MetaCMSReader_COR;
import com.lm.data.MetaCMSReader_SFLA;
import com.lm.Metadomain.CellSet;
import com.lm.Metadomain.JobSet;
import com.lm.Metadomain.MachineSet;
import com.lm.statistic.MySummaryStat;
import com.lm.util.Constants;

public class MetaMain {
	
//	private static Logger log = LoggerHelper.getLogger("Main");
	private static MetaCMSReader dr;
	private static MetaCMSReaderForLooseDueDate r;
	static MetaIMeasurance makespan = new Makespan();
	static MetaIMeasurance TWT = new TotalWeightedTardiness();
	
	/**
	 * @Description 主函数入口
	 * @param args
	 * @throws IOException
	 * @throws CloneNotSupportedException
	 */
	public static void main(String[] args) throws IOException,
			CloneNotSupportedException {
		FullFactorExperiment(TWT);
//		RunForGP(TWT);
//		RunForPureGP(TWT);
//		RunForRule(TWT);
//		RunForPureRule(TWT);
//		RunInit(TWT);
//		RunInitGP(TWT);
//		RunHybrid(TWT);
//		RunAgingLeader(TWT);
//		RunMutation(TWT);
//		RunRandom(TWT);
//		RunPan(TWT);
//		RunStrategy(TWT);
//		RunWithoutStrategy(TWT);
//		RunAll(TWT);
//		RunSFLA(TWT);        //延斌实验用这个
//		RunCOR(TWT);
//		RunEM_like(TWT);
//		RunTang(TWT);
		
		
		//T-EC翻稿实验系列
		
		
	}

	private static void RunInitGP(MetaIMeasurance measure)throws IOException, CloneNotSupportedException {
		// TODO Auto-generated method stub
		StringBuilder resultFileName = new StringBuilder(80);
		resultFileName.append("result/ABC/").append(
	               new Throwable().getStackTrace()[0].getMethodName());	        
		System.out.println(resultFileName.toString());
		
		 printTitle(resultFileName.toString());
		 System.out.println("Init");
		 for (int caseNo = 0; caseNo < Constants.TOTAL_PROBLEMS; caseNo++) {
	            dr = new MetaCMSReader("data/Trans/SFLA/" + (caseNo + 1));
		       	MachineSet machineSet = dr.getMachineSet();
		        JobSet jobSet= dr.getJobSet();
		        CellSet cellSet=dr.getCellSet();
		        
	           MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
	           for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
	        	   MetaHeuristicScheduler scheduler = new MetaHeuristicScheduler(machineSet, jobSet,cellSet);
	        	   DABC_InitGP abc = new DABC_InitGP(machineSet, jobSet, cellSet, scheduler, measure);
	        	   long start = System.currentTimeMillis();
	        	   
	        	   abc.schedule(caseNo+1);
	        	   
	        	   stat.addTime(System.currentTimeMillis() - start);
	        	   stat.value(abc.getBestFunctionValue()); 
	  		 	}
	 		 printResult(resultFileName.toString(), stat);
		 }
	}

	private static void RunEM_like(MetaIMeasurance measure)throws IOException, CloneNotSupportedException {
		// TODO Auto-generated method stub
		StringBuilder resultFileName = new StringBuilder(80);
		resultFileName.append("result/ABC/").append(
	               new Throwable().getStackTrace()[0].getMethodName());	        
		System.out.println(resultFileName.toString());
		
		 printTitle(resultFileName.toString());
		 System.out.println("Em-like对比实验");
		 for (int caseNo = 0; caseNo < Constants.TOTAL_PROBLEMS; caseNo++) {
			 for(int iter=0;iter < 3;iter++){
//	        	 System.out.println("case"+(caseNo+1)+":");
//	            dr = new MetaCMSReader("data/Trans/IABC_0919/" + (caseNo + 1));
	        	dr = new MetaCMSReader("data/Trans/EM-LIKE/" + (caseNo + 1));
//	        	r = new MetaCMSReaderForLooseDueDate("data/Trans/EM-LIKE/" + (caseNo + 1));
		       	MachineSet machineSet = dr.getMachineSet();
		        JobSet jobSet= dr.getJobSet();
		        CellSet cellSet=dr.getCellSet();
		        
	           MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
	           for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
	        	   MetaHeuristicScheduler scheduler = new MetaHeuristicScheduler(machineSet, jobSet,cellSet);
	        	   
	        	   EM abc = new EM(machineSet, jobSet, cellSet, scheduler, measure);
	        	   long start = System.currentTimeMillis();
	        	   
	        	   abc.schedule(caseNo+1);
	        	   
	        	   stat.addTime(System.currentTimeMillis() - start);
	        	   stat.value(abc.getBestFunctionValue()); 
	  		 	}
	 		 printResult(resultFileName.toString(), stat);
			 } 
		}
	}

	private static void RunInit(MetaIMeasurance measure) throws IOException, CloneNotSupportedException {
		StringBuilder resultFileName = new StringBuilder(80);
		resultFileName.append("result/ABC/").append(
	               new Throwable().getStackTrace()[0].getMethodName());	        
		System.out.println(resultFileName.toString());
		
		 printTitle(resultFileName.toString());
		 System.out.println("Init");
		 for (int caseNo = 0; caseNo < Constants.TOTAL_PROBLEMS; caseNo++) {
	            dr = new MetaCMSReader("data/Trans/IABC_0919/" + (caseNo + 1));
		       	MachineSet machineSet = dr.getMachineSet();
		        JobSet jobSet= dr.getJobSet();
		        CellSet cellSet=dr.getCellSet();
		        
	           MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
	           for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
	        	   MetaHeuristicScheduler scheduler = new MetaHeuristicScheduler(machineSet, jobSet,cellSet);
	        	   DABC_Init abc = new DABC_Init(machineSet, jobSet, cellSet, scheduler, measure);
	        	   long start = System.currentTimeMillis();
	        	   
	        	   abc.schedule(caseNo+1);
	        	   
	        	   stat.addTime(System.currentTimeMillis() - start);
	        	   stat.value(abc.getBestFunctionValue()); 
	  		 	}
	 		 printResult(resultFileName.toString(), stat);
		 }
	}
	
	private static void RunSFLA(MetaIMeasurance measure) throws IOException, CloneNotSupportedException {
		StringBuilder resultFileName = new StringBuilder(80);
		resultFileName.append("result/ABC/").append(
	               new Throwable().getStackTrace()[0].getMethodName());	        
		 System.out.println("SFLA");
		 MetaCMSReader_SFLA dr_sfla;
		 for (int caseNo = 0; caseNo < Constants.TOTAL_PROBLEMS; caseNo++) {
				 dr_sfla = new MetaCMSReader_SFLA("data/Trans/data_SFLA/case" + (caseNo + 1)+".txt");
				 MachineSet machineSet = dr_sfla.getMachineSet();
				 JobSet jobSet= dr_sfla.getJobSet();
				 CellSet cellSet=dr_sfla.getCellSet();		       
	
				 System.out.print(caseNo+1+"\t");
				 
				 MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
				 long start = 0;
				 for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
					 MetaHeuristicScheduler scheduler = new MetaHeuristicScheduler(machineSet, jobSet,cellSet);
					 DABC_MultiPan abc = new DABC_MultiPan(machineSet, jobSet, cellSet, scheduler, measure);
					 start = System.currentTimeMillis();

					 abc.schedule(caseNo+1);
                     
					 stat.addTime(System.currentTimeMillis() - start);
					 stat.value(abc.getBestFunctionValue()); 
					 System.out.print(abc.getBestFunctionValue()+"\t");
				 }
				 System.out.print((System.currentTimeMillis() - start)/Constants.INSTANCES_PER_PROBLEM
						          + "\n");
				 
				 printResult(resultFileName.toString(), stat);
		 }
	}
	
	private static void RunCOR(MetaIMeasurance measure) throws IOException, CloneNotSupportedException {
		StringBuilder resultFileName = new StringBuilder(80);
		resultFileName.append("result/ABC/").append(
	               new Throwable().getStackTrace()[0].getMethodName());	        
		 System.out.println("COR");
		 MetaCMSReader_COR dr_cor;
		 for (int caseNo = 10; caseNo < 12; caseNo++) {
			 dr_cor = new MetaCMSReader_COR("data/Trans/COR/"+(caseNo+1)+"/1",
												 "data/Trans/COR/"+(caseNo+1)+"/2");
				 
				 MachineSet machineSet = dr_cor.getMachineSet();
				 JobSet jobSet		   = dr_cor.getJobSet();
				 CellSet cellSet	   = dr_cor.getCellSet();		       
				 
				 MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
				 long start = 0;
				 for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
					 MetaHeuristicScheduler_Notrans scheduler = new MetaHeuristicScheduler_Notrans(machineSet, jobSet,cellSet,false);
					 DABC_MultiPan abc = new DABC_MultiPan(machineSet, jobSet, cellSet, scheduler, measure);
					 start = System.currentTimeMillis();

					 abc.schedule(caseNo+1);
                     
					 stat.addTime(System.currentTimeMillis() - start);
					 stat.value(abc.getBestFunctionValue()); 
					 System.out.print(abc.getBestFunctionValue()+"\t");
				 }
				 System.out.print((System.currentTimeMillis() - start)/Constants.INSTANCES_PER_PROBLEM
						          + "\n");
				 
				 printResult(resultFileName.toString(), stat);
		 }
	}
	
	private static void RunTang(MetaIMeasurance measure) throws IOException, CloneNotSupportedException {
		StringBuilder resultFileName = new StringBuilder(80);
		resultFileName.append("result/ABC/").append(
	               new Throwable().getStackTrace()[0].getMethodName());	        
		 System.out.println("Tang.SS");
		 MetaCMSReader dr_tang;
		 for (int caseNo = 0; caseNo < 20; caseNo++) {
			     dr_tang = new MetaCMSReader("New20/" + (caseNo + 1));
				 
				 MachineSet machineSet = dr_tang.getMachineSet();
				 JobSet jobSet		   = dr_tang.getJobSet();
				 CellSet cellSet	   = dr_tang.getCellSet();		       
				 
				 MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
				 long start = 0;
				 for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
					 MetaHeuristicScheduler_Notrans scheduler = new MetaHeuristicScheduler_Notrans(machineSet, jobSet,cellSet,true);
					 Tang_SS abc = new Tang_SS(machineSet, jobSet, cellSet, scheduler, measure);
					 start = System.currentTimeMillis();

					 abc.schedule(caseNo+1);
                     
					 stat.addTime(System.currentTimeMillis() - start);
					 stat.value(abc.getBestFunctionValue()/10); 
					 System.out.print(abc.getBestFunctionValue()+"\t");
					 System.out.print(abc.getBestFunctionValue()/10+"\t");
				 }
				 System.out.print((System.currentTimeMillis() - start)/Constants.INSTANCES_PER_PROBLEM
						          + "\n");
				 
				 printResult(resultFileName.toString(), stat);
		 }
	}
	
	private static void RunPan(MetaIMeasurance measure) throws IOException, CloneNotSupportedException {
		StringBuilder resultFileName = new StringBuilder(80);
		resultFileName.append("result/ABC/").append(
	               new Throwable().getStackTrace()[0].getMethodName());	        
		 System.out.println("Pan");
		 for (int caseNo = 11; caseNo < Constants.TOTAL_PROBLEMS; caseNo++) {
	            dr = new MetaCMSReader("data/Trans/IABC_0919/" + (caseNo + 1));
		       	MachineSet machineSet = dr.getMachineSet();
		        JobSet jobSet= dr.getJobSet();
		        CellSet cellSet=dr.getCellSet();
		       
		       System.out.print(caseNo+1);
	           MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
	           for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
	        	   MetaHeuristicScheduler scheduler = new MetaHeuristicScheduler(machineSet, jobSet,cellSet);
	        	   DABC_Pan abc = new DABC_Pan(machineSet, jobSet, cellSet, scheduler, measure);
	        	   long start = System.currentTimeMillis();
	        	   
	        	   abc.schedule();
	        	   
	        	   stat.addTime(System.currentTimeMillis() - start);
	        	   stat.value(abc.getBestFunctionValue()); 
	  		 	}
	         System.out.print("\n");
	 		 printResult(resultFileName.toString(), stat);
		 }
	}
	
	private static void RunHybrid(MetaIMeasurance measure) throws IOException, CloneNotSupportedException {
		StringBuilder resultFileName = new StringBuilder(80);
		resultFileName.append("result/ABC/").append(
	               new Throwable().getStackTrace()[0].getMethodName());	        
		System.out.println(resultFileName.toString());
		
		 printTitle(resultFileName.toString());
		 System.out.println("Hybrid");
		 for (int caseNo = 0; caseNo < Constants.TOTAL_PROBLEMS; caseNo++) {
	            dr = new MetaCMSReader("data/Trans/IABC_0919/" + (caseNo + 1));
		       	MachineSet machineSet = dr.getMachineSet();
		        JobSet jobSet= dr.getJobSet();
		        CellSet cellSet=dr.getCellSet();
		        
	           MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
	           for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
	        	   MetaHeuristicScheduler scheduler = new MetaHeuristicScheduler(machineSet, jobSet,cellSet);
	        	   DABC_Hyrid abc = new DABC_Hyrid(machineSet, jobSet, cellSet, scheduler, measure);
	        	   long start = System.currentTimeMillis();
	        	   
	        	   abc.schedule(caseNo+1);
	        	   
	        	   stat.addTime(System.currentTimeMillis() - start);
	        	   stat.value(abc.getBestFunctionValue()); 
	  		 	}
	 		 printResult(resultFileName.toString(), stat);
		 }
	}
	
	private static void RunStrategy(MetaIMeasurance measure) throws IOException, CloneNotSupportedException {
		
		 System.out.println("run strategy");
		 for (int caseNo = 0 ; caseNo < 20 ; caseNo++) { //Constants.TOTAL_PROBLEMS
	            dr = new MetaCMSReader(Constants.CMS_SOURCE + (caseNo +20 +1));
		       	MachineSet machineSet = dr.getMachineSet();
		        JobSet jobSet= dr.getJobSet();
		        CellSet cellSet=dr.getCellSet();
		        Chromosome chromos = new Chromosome(machineSet.size(),cellSet.size());
		        
		        for (int MIndex = 0; MIndex < 10; MIndex++) {//random for different machine Segment
	               
		           MetaHeuristicScheduler scheduler = new MetaHeuristicScheduler(machineSet, jobSet,cellSet);
	        	   DABC_Strategy abc = new DABC_Strategy(machineSet, jobSet, cellSet, scheduler, measure);
	        	   
	        	   abc.SetPrior(machineSet.size(),cellSet.size(),chromos,Constants.RULESET_DIR+"/" +(caseNo+1)+"/"+(MIndex+1));
	        	   //evaluation
	        	   System.out.print(abc.evaluation(chromos)+"\t"); 
	  		    }
		        System.out.print("\n");
		 }
	}
	
	private static void RunWithoutStrategy(MetaIMeasurance measure) throws IOException, CloneNotSupportedException {
		
//		 System.out.println("run without strategy");
		 for (int MIndex = 2; MIndex < 10; MIndex++) {//random for different machine Segment
	            System.out.println("M"+(MIndex+1));
			
				 for (int caseNo = 0 ; caseNo < Constants.TOTAL_PROBLEMS; caseNo++) {
			            dr = new MetaCMSReader(Constants.CMS_SOURCE + (caseNo + 1));
				       	MachineSet machineSet = dr.getMachineSet();
				        JobSet jobSet= dr.getJobSet();
				        CellSet cellSet=dr.getCellSet();
				        Chromosome chromos = new Chromosome(machineSet.size(),cellSet.size());
				        
		                MetaHeuristicScheduler scheduler = new MetaHeuristicScheduler(machineSet, jobSet,cellSet);
		                DABC_Strategy abc = new DABC_Strategy(machineSet, jobSet, cellSet, scheduler, measure);

		                abc.SetPrior(machineSet.size(),cellSet.size(),chromos,Constants.RULESET_DIR+"/" +(caseNo+1)+"/"+(MIndex+1));
		                
		                for (int VIndex = 0; VIndex < 10; VIndex++) {//random for different vehicle Segment
		        		 abc.InitRandomVechileSegment(chromos,cellSet.size());
		        		 //evaluation
		        		 System.out.print(abc.evaluationWithNoStragy(chromos)+"\t"); 
		        	}
		            System.out.print("\n");
				}
		 }
		 System.out.println("\n");
	}
	
	private static void RunAgingLeader(MetaIMeasurance measure) throws IOException, CloneNotSupportedException {
		StringBuilder resultFileName = new StringBuilder(80);
		resultFileName.append("result/ABC/").append(
	               new Throwable().getStackTrace()[0].getMethodName());	        
		System.out.println(resultFileName.toString());
		
		 printTitle(resultFileName.toString());
		 System.out.println("AgingLeader");
		 for (int caseNo = 0; caseNo < Constants.TOTAL_PROBLEMS; caseNo++) {
	        	 System.out.println("case"+(caseNo+1)+":");
//	            dr = new MetaCMSReader("data/Trans/IABC_0919/" + (caseNo + 1));
	            dr = new MetaCMSReader("data/Trans/New12/" + (caseNo + 1));
		       	MachineSet machineSet = dr.getMachineSet();
		        JobSet jobSet= dr.getJobSet();
		        CellSet cellSet=dr.getCellSet();
		        
	           MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
	           for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
	        	   MetaHeuristicScheduler scheduler = new MetaHeuristicScheduler(machineSet, jobSet,cellSet);
	        	   DABC_AgingLeader abc = new DABC_AgingLeader(machineSet, jobSet, cellSet, scheduler, measure);
	        	   long start = System.currentTimeMillis();
	        	   
	        	   abc.schedule(caseNo+1);
	        	   
	        	   stat.addTime(System.currentTimeMillis() - start);
	        	   stat.value(abc.getBestFunctionValue()); 
	  		 	}
	 		 printResult(resultFileName.toString(), stat);
		 }
	}
	
	static void RunRandom(MetaIMeasurance measure) throws IOException, CloneNotSupportedException {
		StringBuilder resultFileName = new StringBuilder(80);
		resultFileName.append("result/ABC/").append(
	               new Throwable().getStackTrace()[0].getMethodName());	        
		System.out.println(resultFileName.toString());
		
		 printTitle(resultFileName.toString());
		 System.out.println("random+纯GP初始解：");
		 for (int caseNo = 0; caseNo < Constants.TOTAL_PROBLEMS; caseNo++) {
	        	 System.out.println("case"+(caseNo+1)+":");
	            dr = new MetaCMSReader("New20/" + (caseNo + 1));
		       	MachineSet machineSet = dr.getMachineSet();
		        JobSet jobSet= dr.getJobSet();
		        CellSet cellSet=dr.getCellSet();
		        
	           MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
	           for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
	        	   MetaHeuristicScheduler_Notrans scheduler = new MetaHeuristicScheduler_Notrans(machineSet, jobSet,cellSet,true);
	        	   
	        	   DABC_Random abc = new DABC_Random(machineSet, jobSet, cellSet, scheduler, measure);
	        	   long start = System.currentTimeMillis();
	        	   
	        	   abc.schedule(caseNo+1);
	        	   
	        	   stat.addTime(System.currentTimeMillis() - start);
	        	   stat.value(abc.getBestFunctionValue()); 
	  		 	}
	 		 printResult(resultFileName.toString(), stat);
		 }
	}
	
	/**
	 * Run the DABC5: only GP highlight
	 * @param bia
	 * @throws IOException
	 * @throws CloneNotSupportedException 
	 */
	static void RunForGP(MetaIMeasurance measure) throws IOException, CloneNotSupportedException {
		StringBuilder resultFileName = new StringBuilder(80);
		resultFileName.append("result/ABC/").append(
	               new Throwable().getStackTrace()[0].getMethodName());	        
		System.out.println(resultFileName.toString());
		
		 printTitle(resultFileName.toString());
		 System.out.println("1/2gp的abc");
		 for (int caseNo = 0; caseNo < Constants.TOTAL_PROBLEMS; caseNo++) {
	        	 System.out.println("case"+(caseNo+1)+":");
	            dr = new MetaCMSReader("data/Trans/IABC_0915/" + (caseNo + 1));
		       	MachineSet machineSet = dr.getMachineSet();
		        JobSet jobSet= dr.getJobSet();
		        CellSet cellSet=dr.getCellSet();
		        
	           MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
	           for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
	        	   MetaHeuristicScheduler scheduler = new MetaHeuristicScheduler(machineSet, jobSet,cellSet);
	        	   DABC5 abc = new DABC5(machineSet, jobSet, cellSet, scheduler, measure);
	        	   long start = System.currentTimeMillis();
	        	   
//	        	   abc.schedule(caseNo+1,true);
	        	   
	        	   stat.addTime(System.currentTimeMillis() - start);
	        	   stat.value(abc.getBestFunctionValue()); 
	  		 	}
	 		 printResult(resultFileName.toString(), stat);
		 }
	}
	
//	static void RunForPureGP(MetaIMeasurance measure) throws IOException, CloneNotSupportedException {
//		StringBuilder resultFileName = new StringBuilder(80);
//		resultFileName.append("result/ABC/").append(
//	               new Throwable().getStackTrace()[0].getMethodName());	        
//		System.out.println(resultFileName.toString());
//		
//		 printTitle(resultFileName.toString());
//		 System.out.println("纯gp的abc");
//		 for (int caseNo = 0; caseNo < Constants.TOTAL_PROBLEMS; caseNo++) {
//	        	 System.out.println("case"+(caseNo+1)+":");
//	            dr = new MetaCMSReader("data/Trans/IABC_0915/" + (caseNo + 1));
//		       	MachineSet machineSet = dr.getMachineSet();
//		        JobSet jobSet= dr.getJobSet();
//		        CellSet cellSet=dr.getCellSet();
//		        
//	           MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
//	           for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
//	        	   MetaHeuristicScheduler scheduler = new MetaHeuristicScheduler(machineSet, jobSet,cellSet);
//	        	   DABC_Pure abc = new DABC_Pure(machineSet, jobSet, cellSet, scheduler, measure);
//	        	   long start = System.currentTimeMillis();
//	        	   
//	        	   abc.schedule(caseNo+1,true);
//	        	   
//	        	   stat.addTime(System.currentTimeMillis() - start);
//	        	   stat.value(abc.getBestFunctionValue()); 
//	  		 	}
//	 		 printResult(resultFileName.toString(), stat);
//		 }
//	}
//	
//	private static void RunForRule(MetaIMeasurance measure) throws CloneNotSupportedException {
//		StringBuilder resultFileName = new StringBuilder(80);
//		resultFileName.append("result/ABC/").append(
//	               new Throwable().getStackTrace()[0].getMethodName());	        
//		System.out.println(resultFileName.toString());
//		
//		 printTitle(resultFileName.toString());
//		 System.out.println("1/2rule的abc");
//		 for (int caseNo = 0; caseNo < Constants.TOTAL_PROBLEMS; caseNo++) {
//	        	 System.out.println("case"+(caseNo+1)+":");
//	            dr = new MetaCMSReader("data/Trans/IABC_0915/" + (caseNo + 1));
//		       	MachineSet machineSet = dr.getMachineSet();
//		        JobSet jobSet= dr.getJobSet();
//		        CellSet cellSet=dr.getCellSet();
//		        
//	           MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
//	           for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
//	        	   MetaHeuristicScheduler scheduler = new MetaHeuristicScheduler(machineSet, jobSet,cellSet);
//	        	   DABC5 abc = new DABC5(machineSet, jobSet, cellSet, scheduler, measure);
//	        	   long start = System.currentTimeMillis();
//	        	   
////	        	   abc.schedule(caseNo+1,false);
//	        	   
//	        	   stat.addTime(System.currentTimeMillis() - start);
//	        	   stat.value(abc.getBestFunctionValue()); 
//	  		 	}
//	 		 printResult(resultFileName.toString(), stat);
//		 }
//	}
//	
//	private static void RunForPureRule(MetaIMeasurance measure) throws CloneNotSupportedException {
//		StringBuilder resultFileName = new StringBuilder(80);
//		resultFileName.append("result/ABC/").append(
//	               new Throwable().getStackTrace()[0].getMethodName());	        
//		System.out.println(resultFileName.toString());
//		
//		 printTitle(resultFileName.toString());
//		 System.out.println("纯rule的abc");
//		 for (int caseNo = 0; caseNo < Constants.TOTAL_PROBLEMS; caseNo++) {
//	        	 System.out.println("case"+(caseNo+1)+":");
//	            dr = new MetaCMSReader("data/Trans/IABC_0915/" + (caseNo + 1));
//		       	MachineSet machineSet = dr.getMachineSet();
//		        JobSet jobSet= dr.getJobSet();
//		        CellSet cellSet=dr.getCellSet();
//		        
//	           MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
//	           for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
//	        	   MetaHeuristicScheduler scheduler = new MetaHeuristicScheduler(machineSet, jobSet,cellSet);
//	        	   DABC_Pure abc = new DABC_Pure(machineSet, jobSet, cellSet, scheduler, measure);
//	        	   long start = System.currentTimeMillis();
//	        	   
//	        	   abc.schedule(caseNo+1,false);
//	        	   
//	        	   stat.addTime(System.currentTimeMillis() - start);
//	        	   stat.value(abc.getBestFunctionValue()); 
//	  		 	}
//	 		 printResult(resultFileName.toString(), stat);
//		 }
//	}

	static void RunAll(MetaIMeasurance measure) throws IOException, CloneNotSupportedException {
		StringBuilder resultFileName = new StringBuilder(80);
		resultFileName.append("result/ABC/").append(
	               new Throwable().getStackTrace()[0].getMethodName());	        
		System.out.println("runfornostrategy");
		
		 printTitle(resultFileName.toString());
		 for (int caseNo = 0; caseNo < Constants.TOTAL_PROBLEMS; caseNo=caseNo+4) {
//	            dr = new MetaCMSReader("data/Trans/IABC_0919/" + (caseNo + 1));
	            dr = new MetaCMSReader("data/Trans/New12/" + (caseNo + 1));
//	            r = new MetaCMSReaderForLooseDueDate("data/Trans/IABC_GP/" + (caseNo + 1));
		       	MachineSet machineSet = dr.getMachineSet();
		        JobSet jobSet= dr.getJobSet();
		        CellSet cellSet=dr.getCellSet();
		        
	           MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
	           for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
	        	   MetaHeuristicScheduler scheduler = new MetaHeuristicScheduler(machineSet, jobSet,cellSet);
	        	   DABC_MultiPan abc = new DABC_MultiPan(machineSet, jobSet, cellSet, scheduler, measure);
	        	   long start = System.currentTimeMillis();
	        	   
	        	   abc.schedule(caseNo+1);
	        	   
	        	   stat.addTime(System.currentTimeMillis() - start);
	        	   stat.value(abc.getBestFunctionValue()); 
	  		 	}
	 		 printResult(resultFileName.toString(), stat);
		 }
	}
	
	static void RunMutation(MetaIMeasurance measure) throws IOException, CloneNotSupportedException {
		StringBuilder resultFileName = new StringBuilder(80);
		resultFileName.append("result/ABC/").append(
	               new Throwable().getStackTrace()[0].getMethodName());	        
		System.out.println(resultFileName.toString());
		
		 printTitle(resultFileName.toString());
		 for (int caseNo = 0; caseNo < Constants.TOTAL_PROBLEMS; caseNo++) {
	        	 System.out.println("case"+(caseNo+1)+":");
	            dr = new MetaCMSReader("data/Trans/IABC_0919/" + (caseNo + 1));
		       	MachineSet machineSet = dr.getMachineSet();
		        JobSet jobSet= dr.getJobSet();
		        CellSet cellSet=dr.getCellSet();
		        
	           MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
	           for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
	        	   MetaHeuristicScheduler scheduler = new MetaHeuristicScheduler(machineSet, jobSet,cellSet);
	        	   DABC_Mutation abc = new DABC_Mutation(machineSet, jobSet, cellSet, scheduler, measure);
	        	   long start = System.currentTimeMillis();
	        	   
	        	   abc.schedule(caseNo+1);
	        	   
	        	   stat.addTime(System.currentTimeMillis() - start);
	        	   stat.value(abc.getBestFunctionValue()); 
	  		 	}
	 		 printResult(resultFileName.toString(), stat);
		 }
	}
	 
	/**
	 * 全因子(ANOVA)实验
	 * @param measure
	 * @throws IOException
	 * @throws CloneNotSupportedException 
	 */
	static void FullFactorExperiment(MetaIMeasurance measure) throws IOException, CloneNotSupportedException {

	        StringBuilder resultFileName = new StringBuilder(80);
	        resultFileName.append("result/ANOVA/")
	                .append(new Throwable().getStackTrace()[0].getMethodName())
	                .append("+").append(measure.toString());
	        System.out.println(resultFileName.toString());

	        BufferedWriter br = new BufferedWriter(new FileWriter(
	                resultFileName.toString()));

//	        /*
//	         * 全因子组合
//	         */
	        int[] gm = { 100 };
	        int[] ps = { 6, 12, 24, 48 };
	        double[] ad = {1.02, 1.04, 1.06, 1.08, 1.10};
	        double[] th = {0.1, 0.2, 0.3};
	        
	        for (int iPs = 0; iPs < ps.length; iPs++) {
	        	for (int iGm = 0; iGm < gm.length; iGm++) {
	        		for(int iAd = 0; iAd < ad.length; iAd++){
	        			for(int iTh = 0; iTh < th.length; iTh++){
	        				for (int caseNo = 0; caseNo < 4; caseNo++) {
			    	            dr = new MetaCMSReader("New20/" + (3*caseNo + 1));
			    		       	MachineSet machineSet = dr.getMachineSet();
			    		        JobSet jobSet= dr.getJobSet();
			    		        CellSet cellSet=dr.getCellSet();
			    	           MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
			    	           double meanperformance = 0, totalperformance = 0, totalTime = 0, meanTime = 0;
			    	           for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
			    	        	   MetaHeuristicScheduler_Notrans scheduler = new MetaHeuristicScheduler_Notrans(machineSet, jobSet,cellSet,true);
			    	        	   DABC_MultiPan abc = new DABC_MultiPan(machineSet, jobSet, cellSet, scheduler, measure,gm[iGm],ps[iPs],ad[iAd],th[iTh]);
			    	        	   long start = System.currentTimeMillis();
			    	        	   
			    	        	   abc.schedule(caseNo+1);
			    	        	   
			    	        	   totalTime += (System.currentTimeMillis() - start);
			   	        		   totalperformance += abc.getBestFunctionValue();
			    	  		   }
			    	           meanperformance = totalperformance
			    	           / Constants.REPLICATIONS_PER_INSTANCE;
			    	           meanTime = totalTime / Constants.REPLICATIONS_PER_INSTANCE;
			    	           /**
			    	           stat.value(meanperformance);
			    	           stat.addTime(meanTime);
			    	           stat.setParameter(ga.parameter());
			    	 		   printResult(br, stat);
			    	 		   **/
			    	           System.out.println(gm[iGm]+"\t"+ps[iPs]+"\t"+ad[iAd]+"\t"+th[iTh]+"\t"+meanperformance);
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
