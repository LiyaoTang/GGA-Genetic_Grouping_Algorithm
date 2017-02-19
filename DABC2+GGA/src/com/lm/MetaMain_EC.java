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
import com.lm.algorithms.abc.DABC_add;
import com.lm.algorithms.abc.EM;
import com.lm.algorithms.abc.DABC_Init;
import com.lm.algorithms.abc.DABC_AgingLeader;
import com.lm.algorithms.abc.DABC_Pan;
import com.lm.algorithms.abc.DABC_Random;
import com.lm.algorithms.abc.DABC_Strategy;
import com.lm.algorithms.abc.EM_add;
import com.lm.algorithms.abc.EM_add_onlyALEG;
import com.lm.algorithms.abc.EM_add_onlyGP;
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

/**T-EC第一次返稿实验**/

public class MetaMain_EC {
	
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
//		RunHybrid(TWT);
//		RunAgingLeader(TWT);

		
//		RunMutation(TWT);
//		RunDABC_Add(TWT);   //T-EC返稿第一次的算法版本，只保留了GP初始解+Aging leader
//		RunDABC_Random(TWT);
		
//		RunEM_like(TWT);
//		RunEM_like_add_onlyGP(TWT);  //只含有GP初始解的EM-like对比算法
//		RunEM_like_add_onlyALEG(TWT);	//只含有Aging leader的EM-like对比算法
//		RunEM_like_add(TWT); //含有GP初始解+Aging leader的EM-like对比算法
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
			 System.out.println("case"+(caseNo+1)+":");
			 for(int iter=0;iter < 5;iter++){
//	            dr = new MetaCMSReader("New20/" + (caseNo + 1));
	        	dr = new MetaCMSReader("data/Trans/EM-LIKE/" + (caseNo + 1));
//	        	r = new MetaCMSReaderForLooseDueDate("data/Trans/EM-LIKE/" + (caseNo + 1));
		       	MachineSet machineSet = dr.getMachineSet();
		        JobSet jobSet= dr.getJobSet();
		        CellSet cellSet=dr.getCellSet();
		        
	           MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
	           for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
	        	   MetaHeuristicScheduler_Notrans scheduler = new MetaHeuristicScheduler_Notrans(machineSet, jobSet,cellSet,true);
	        	   
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

	//加入了GP+Aging leader的EM-like算法实验
	private static void RunEM_like_add(MetaIMeasurance measure)throws IOException, CloneNotSupportedException {
		// TODO Auto-generated method stub
		StringBuilder resultFileName = new StringBuilder(80);
		resultFileName.append("result/ABC/").append(
	               new Throwable().getStackTrace()[0].getMethodName());	        
		System.out.println(resultFileName.toString());
		
		 printTitle(resultFileName.toString());
		 System.out.println("Em-like例子:加入了GP+Agingleader");
		 for (int caseNo = 0; caseNo < Constants.TOTAL_PROBLEMS; caseNo++) {
			 System.out.println("case"+(caseNo+1)+":");
			 for(int iter=0;iter < 5;iter++){
//	        	 System.out.println("case"+(caseNo+1)+":");
//	            dr = new MetaCMSReader("New20/" + (caseNo + 1));
	        	dr = new MetaCMSReader("data/Trans/EM-LIKE/" + (caseNo + 1));
//	        	r = new MetaCMSReaderForLooseDueDate("data/Trans/EM-LIKE/" + (caseNo + 1));
		       	MachineSet machineSet = dr.getMachineSet();
		        JobSet jobSet= dr.getJobSet();
		        CellSet cellSet=dr.getCellSet();
		        
	           MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
	           for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
	        	   MetaHeuristicScheduler_Notrans scheduler = new MetaHeuristicScheduler_Notrans(machineSet, jobSet,cellSet,true);
	        	   
	        	   EM_add abc = new EM_add(machineSet, jobSet, cellSet, scheduler, measure);
	        	   long start = System.currentTimeMillis();
	        	   
	        	   abc.schedule(caseNo+1);
	        	   
	        	   stat.addTime(System.currentTimeMillis() - start);
	        	   stat.value(abc.getBestFunctionValue()); 
	  		 	}
	 		 printResult(resultFileName.toString(), stat);
			 } 
		}
	}
	
	private static void RunEM_like_add_onlyALEG(MetaIMeasurance measure)throws IOException, CloneNotSupportedException {
		// TODO Auto-generated method stub
		StringBuilder resultFileName = new StringBuilder(80);
		resultFileName.append("result/ABC/").append(
	               new Throwable().getStackTrace()[0].getMethodName());	        
		System.out.println(resultFileName.toString());
		
		 printTitle(resultFileName.toString());
		 System.out.println("Em-like_add对比实验:加入了GP+Agingleader");
		 for (int caseNo = 2; caseNo < Constants.TOTAL_PROBLEMS; caseNo++) {
			 for(int iter=0;iter < 1;iter++){
	        	 System.out.println("case"+(caseNo+1)+":");
//	            dr = new MetaCMSReader("New20/" + (caseNo + 1));
	        	dr = new MetaCMSReader("data/Trans/EM-LIKE/" + (caseNo + 1));
//	        	r = new MetaCMSReaderForLooseDueDate("data/Trans/EM-LIKE/" + (caseNo + 1));
		       	MachineSet machineSet = dr.getMachineSet();
		        JobSet jobSet= dr.getJobSet();
		        CellSet cellSet=dr.getCellSet();
		        
	           MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
	           for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
	        	   MetaHeuristicScheduler_Notrans scheduler = new MetaHeuristicScheduler_Notrans(machineSet, jobSet,cellSet,true);
	        	   
	        	   EM_add_onlyALEG abc = new EM_add_onlyALEG(machineSet, jobSet, cellSet, scheduler, measure);
	        	   long start = System.currentTimeMillis();
	        	   
	        	   abc.schedule(caseNo+1);
	        	   
	        	   stat.addTime(System.currentTimeMillis() - start);
	        	   stat.value(abc.getBestFunctionValue()); 
	  		 	}
	 		 printResult(resultFileName.toString(), stat);
			 } 
		}
	}
	

	private static void RunEM_like_add_onlyGP(MetaIMeasurance measure)throws IOException, CloneNotSupportedException {
		// TODO Auto-generated method stub
		StringBuilder resultFileName = new StringBuilder(80);
		resultFileName.append("result/ABC/").append(
	               new Throwable().getStackTrace()[0].getMethodName());	        
		System.out.println(resultFileName.toString());
		
		 printTitle(resultFileName.toString());
		 System.out.println("Em-like_add对比实验:加入了GP");
		 for (int caseNo = 0; caseNo < Constants.TOTAL_PROBLEMS; caseNo++) {
			 for(int iter=0;iter < 3;iter++){
//	        	 System.out.println("case"+(caseNo+1)+":");
	            dr = new MetaCMSReader("New20/" + (caseNo + 1));
//	        	dr = new MetaCMSReader("data/Trans/EM-LIKE/" + (caseNo + 1));
//	        	r = new MetaCMSReaderForLooseDueDate("data/Trans/EM-LIKE/" + (caseNo + 1));
		       	MachineSet machineSet = dr.getMachineSet();
		        JobSet jobSet= dr.getJobSet();
		        CellSet cellSet=dr.getCellSet();
		        
	           MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
	           for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
	        	   MetaHeuristicScheduler scheduler = new MetaHeuristicScheduler(machineSet, jobSet,cellSet);
	        	   
	        	   EM_add_onlyGP abc = new EM_add_onlyGP(machineSet, jobSet, cellSet, scheduler, measure);
	        	   long start = System.currentTimeMillis();
	        	   
	        	   abc.schedule(caseNo+1);
	        	   
	        	   stat.addTime(System.currentTimeMillis() - start);
	        	   stat.value(abc.getBestFunctionValue()); 
	  		 	}
	 		 printResult(resultFileName.toString(), stat);
			 } 
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
	



	
	static void RunMutation(MetaIMeasurance measure) throws IOException, CloneNotSupportedException {
		StringBuilder resultFileName = new StringBuilder(80);
		resultFileName.append("result/ABC/").append(
	               new Throwable().getStackTrace()[0].getMethodName());	        
		System.out.println(resultFileName.toString());
		
		 printTitle(resultFileName.toString());
		 for (int caseNo = 0; caseNo < Constants.TOTAL_PROBLEMS; caseNo++) {
	        	 System.out.println("case"+(caseNo+1)+":");
	            dr = new MetaCMSReader("data/Trans/EM-LIKE/" + (caseNo + 1));
	            
		       	MachineSet machineSet = dr.getMachineSet();
		        JobSet jobSet= dr.getJobSet();
		        CellSet cellSet=dr.getCellSet();
		        
	           MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
	           for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
	        	   MetaHeuristicScheduler_Notrans scheduler = new MetaHeuristicScheduler_Notrans(machineSet, jobSet,cellSet,true);
	        	   DABC_Mutation abc = new DABC_Mutation(machineSet, jobSet, cellSet, scheduler, measure);
	        	   long start = System.currentTimeMillis();
	        	   
	        	   abc.schedule(caseNo+1);
	        	   
	        	   stat.addTime(System.currentTimeMillis() - start);
	        	   stat.value(abc.getBestFunctionValue()); 
	  		 	}
	 		 printResult(resultFileName.toString(), stat);
		 }
	}
	
	static void RunDABC_Random(MetaIMeasurance measure) throws IOException, CloneNotSupportedException {

        StringBuilder resultFileName = new StringBuilder(80);
        resultFileName.append("result/ANOVA/")
                .append(new Throwable().getStackTrace()[0].getMethodName())
                .append("+").append(measure.toString());
        System.out.println(resultFileName.toString());

        BufferedWriter br = new BufferedWriter(new FileWriter(
                resultFileName.toString()));

        int[] ps = { 48 };
        int[] gm = { 100 };
        int[] af = {0};
        double[] sf = {0.4};
        
        
        for (int iPs = 0; iPs < ps.length; iPs++) {
        	for (int iGm = 0; iGm < gm.length; iGm++) {
        		for (int iAf = 0; iAf < af.length; iAf++) {
        			for (int iSf = 0; iSf < sf.length; iSf++) {
        				
        		 for (int caseNo = 0; caseNo < 20; caseNo++) {
//    	            dr = new MetaCMSReader("data/Trans/IABC_0919/" + (caseNo + 1));
    	            dr = new MetaCMSReader("New20/" + (caseNo + 1));
    		       	MachineSet machineSet = dr.getMachineSet();
    		        JobSet jobSet= dr.getJobSet();
    		        CellSet cellSet=dr.getCellSet();
    	           MySummaryStat stat =new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
    	           double meanperformance = 0, totalperformance = 0, totalTime = 0, meanTime = 0;
    	           for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
    	        	   MetaHeuristicScheduler scheduler = new MetaHeuristicScheduler(machineSet, jobSet,cellSet);
    	        	   DABC_add abc = new DABC_add(machineSet, jobSet, cellSet, scheduler, measure,gm[iGm],ps[iPs],af[iAf],sf[iSf]);
    	        	   long start = System.currentTimeMillis();
    	        	   
    	        	   abc.schedule(caseNo*3+1);
    	        	   
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
    	           System.out.println(ps[iPs]+"\t"+gm[iGm]+"\t"+af[iAf]+"\t"+sf[iSf]+"\t"+meanperformance);
        		 }
        	}
       }
	}
	}
}
	
	
	static void RunDABC_Add(MetaIMeasurance measure) throws IOException, CloneNotSupportedException {
		StringBuilder resultFileName = new StringBuilder(80);
        resultFileName.append("result/Add/")
                .append(new Throwable().getStackTrace()[0].getMethodName())
                .append("+").append(measure.toString());
        System.out.println(resultFileName.toString());

        BufferedWriter br = new BufferedWriter(new FileWriter(
                resultFileName.toString()));

        int[] ps = { 48 };
        int[] gm = { 100 };
        int[] af = {0};
        double[] sf = {0.4};
        
        System.out.println("自己例子下，DABC_add:");
        for (int iPs = 0; iPs < ps.length; iPs++) {
        	for (int iGm = 0; iGm < gm.length; iGm++) {
        		for (int iAf = 0; iAf < af.length; iAf++) {
        			for (int iSf = 0; iSf < sf.length; iSf++) {
        				
        		 for (int caseNo = 19; caseNo < 20; caseNo++) {
        			 System.out.println("case"+(caseNo+1)+":");
//    	            dr = new MetaCMSReader("data/Trans/EM-LIKE/" + (caseNo + 1));
    	            dr = new MetaCMSReader("New20/" + (caseNo + 1));
//    	            dr = new MetaCMSReader("data/Trans/CACO1/" + (caseNo + 1));
    		       	MachineSet machineSet = dr.getMachineSet();
    		        JobSet jobSet= dr.getJobSet();
    		        CellSet cellSet=dr.getCellSet();
//    	           MySummaryStat stat =new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
    	           double meanperformance = 0, totalperformance = 0, totalTime = 0, meanTime = 0;
    	           for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
    	        	   MetaHeuristicScheduler_Notrans scheduler = new MetaHeuristicScheduler_Notrans(machineSet, jobSet,cellSet,true);
    	        	   DABC_add abc = new DABC_add(machineSet, jobSet, cellSet, scheduler, measure,gm[iGm],ps[iPs],af[iAf],sf[iSf]);
    	        	   long start = System.currentTimeMillis();
    	        	   
    	        	   abc.schedule(caseNo+1);
    	        	   
    	        	   totalTime += (System.currentTimeMillis() - start);
    	        	   System.out.println(totalTime);
   	        		   totalperformance += abc.getBestFunctionValue();
    	  		   }
        		 }
        	}
       }
	}
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
//	        int[] ps = { 6 ,12 ,24
////	        			,48 
//	        			};
//	        int[] gm = {  100 };
//	        int[] af = {0, 2, 4};
//	        double[] sf = {0.1, 0.2, 0.4};
	        
	        
	        int[] ps = { 12
//        			
        			};
	        int[] gm = {  100 };
	        int[] af = {4};
	        double[] sf = {  0.2, 0.4};
	        
	        for (int iPs = 0; iPs < ps.length; iPs++) {
	        	for (int iGm = 0; iGm < gm.length; iGm++) {
	        		for (int iAf = 0; iAf < af.length; iAf++) {
	        			for (int iSf = 0; iSf < sf.length; iSf++) {
	        				
	        		 for (int caseNo = 0; caseNo < 6; caseNo++) {
//	    	            dr = new MetaCMSReader("data/Trans/IABC_0919/" + (caseNo + 1));
	    	            dr = new MetaCMSReader("New20/" + (caseNo*3 + 1));     // case:1 4 7 10 13 16
	    		       	MachineSet machineSet = dr.getMachineSet();
	    		        JobSet jobSet= dr.getJobSet();
	    		        CellSet cellSet=dr.getCellSet();
	    	           MySummaryStat stat =new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
	    	           double meanperformance = 0, totalperformance = 0, totalTime = 0, meanTime = 0;
	    	           for (int ins = 0; ins < Constants.REPLICATIONS_PER_INSTANCE; ins++) {
	    	        	   MetaHeuristicScheduler_Notrans scheduler = new MetaHeuristicScheduler_Notrans(machineSet, jobSet,cellSet,true);
	    	        	   DABC_add abc = new DABC_add(machineSet, jobSet, cellSet, scheduler, measure,gm[iGm],ps[iPs],af[iAf],sf[iSf]);
	    	        	   long start = System.currentTimeMillis();
	    	        	   
	    	        	   abc.schedule(caseNo*3+1);
	    	        	   
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
	    	           System.out.println(ps[iPs]+"\t"+gm[iGm]+"\t"+af[iAf]+"\t"+sf[iSf]+"\t"+meanperformance);
	        		 }
	        	}
	       }
    	}
   	}
}
	
	
	/**
	 * 全因子(ANOVA)实验
	 * @param measure
	 * @throws IOException
	 * @throws CloneNotSupportedException 
	 */
	static void FullFactorEC_add(MetaIMeasurance measure) throws IOException, CloneNotSupportedException {

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
	        int[] ps = { 6, 12, 24, 48 };
	        int[] gm = { 10,25, 50, 100 };

	        
	        for (int iPs = 0; iPs < ps.length; iPs++) {
	        	for (int iGm = 0; iGm < gm.length; iGm++) {
	        		 for (int caseNo = 10; caseNo < 12; caseNo++) {
	    	            dr = new MetaCMSReader("data/Trans/IABC_0919/" + (caseNo + 1));
	    		       	MachineSet machineSet = dr.getMachineSet();
	    		        JobSet jobSet= dr.getJobSet();
	    		        CellSet cellSet=dr.getCellSet();
	    	           MySummaryStat stat=new MySummaryStat(Constants.PROBLEM_NAMES[caseNo]);
	    	           double meanperformance = 0, totalperformance = 0, totalTime = 0, meanTime = 0;
	    	           for (int ins = 0; ins < Constants.INSTANCES_PER_PROBLEM; ins++) {
	    	        	   MetaHeuristicScheduler scheduler = new MetaHeuristicScheduler(machineSet, jobSet,cellSet);
	    	        	   DABC_MultiPan abc = new DABC_MultiPan(machineSet, jobSet, cellSet, scheduler, measure,gm[iGm],ps[iPs]);
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
	    	           System.out.println(gm[iGm]+"\t"+ps[iPs]+"\t"+meanperformance);
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
