package com.fay.GA;

import java.io.IOException;

import com.fay.CMS.CMSReader;
import com.fay.domain.CellSet;
import com.fay.domain.JobSet;
import com.fay.domain.MachineSet;
import com.fay.measure.IMeasurance;
import com.fay.measure.TotalWeightedTardiness;
import com.fay.scheduler.SimpleScheduler;
import com.fay.statics.MySummaryStat;
import com.fay.statics.RuleFrequencyStatistic;
import com.fay.util.Constants;
import com.fay.CMS.CMSReader;







public class HSGA_main{
	
//	private static Logger log = LoggerHelper.getLogger("Main");


	static IMeasurance TWT 		= new TotalWeightedTardiness();
	
	/**
	 * @Description 主函数入口
	 * @param args
	 * @throws IOException
	 * @throws CloneNotSupportedException
	 */
	public static void main(String[] args) throws IOException, CloneNotSupportedException  {
		long totalTime = 0;

		for(int i = 1 ; i <21 ; i++){
			
			CMSReader dr = new CMSReader("data/case"+i+".txt");		
			//CMSReader dr = new CMSReader("data/case2_5.txt");		
			double func = 0,funa = 0;
			
			for(int j = 0 ; j < 5 ; j++){
			
			long start = System.currentTimeMillis();
				
			SimpleScheduler scheduler = new SimpleScheduler(dr.getMachineSet(), dr.getJobSet(), dr.getCellSet());
			HSGA ga = new HSGA(dr.getMachineSet(), dr.getJobSet(), dr.getCellSet(), scheduler, TWT,
					0.6, 0.18, 200, 100);
			func = ga.schedule();
			
			totalTime += (System.currentTimeMillis() - start);
			
			funa += func;
			
			}
			funa /= 5;
			System.out.println(funa+"\t"+totalTime/5);
			totalTime = 0;
		}

	}


}
