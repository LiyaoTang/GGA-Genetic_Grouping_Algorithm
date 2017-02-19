package com.fay.comparaEx;

import java.util.ArrayList;
import java.util.Random;

import com.fay.CMS.MetaCMSReader;
import com.fay.measure.TotalWeightedTardiness;
import com.fay.rule.BufferOutRule.IBufferOutRule;
import com.fay.rule.JobRule.IJobRule;
import com.fay.rule.MachineRule.IMachineRule;
import com.fay.rule.TimeWindowRule.ITimeWindowRule;
import com.fay.scheduler.SimpleScheduler;
import com.fay.util.Constants;

public class chromosome {
	static int nCell;
	static int nJob;
	static int nMachine;
	static int nAssRule;
	static int nSeqRule;
	static int nBatchRule;
	static int nTWRule;
	static int nRule;
	static MetaCMSReader dataSource;
	static Random rand;
	static int[] ruleNum;

	double fitness;
	int[] ruleCode;

	
	public chromosome() {
		ruleCode = new int[nRule];
				
		Initialize();
	}
	
	public static void setProblemCase(int no){
		dataSource = new MetaCMSReader("data/case"+no+".txt");
		nJob = dataSource.getJobSet().size();
		nMachine = dataSource.getMachineSet().size();
		nCell = dataSource.getCellSet().size();
		nAssRule = 7;
		nSeqRule = 10;
		nBatchRule = 10;
		nTWRule = 6;
		nRule = nCell*2 + nMachine + nJob; 		
//		localSearchRatio = 0.05;
//		PathRelinkingRatio = 0.05;
		
		rand = new Random();
		
	//	maxLSLoop = 50;
	//	maxStabLoop = 20;
		
		ruleNum = new int[nRule];
		int i = 0;
		for(; i < nJob ; i++){
			ruleNum[i]= nAssRule; 
		}
		for(; i < nJob+nMachine ; i++){
			ruleNum[i]= nSeqRule; 
		}
		for(; i < nJob+nMachine+nCell ; i++){
			ruleNum[i]= nBatchRule; 
		}
		for(; i < nJob+nMachine+nCell+nCell ; i++){
			ruleNum[i] = nTWRule;
		}
	}
	
	//鍒濆鍖栵紝璁剧疆鏁伴噺灞炴�銆傜紪鐮�
	public void Initialize() {
		int r = 0;
		
		for (int i = 0; i < ruleCode.length; i++) {
			r = rand.nextInt(ruleNum[i]);
			ruleCode[i]= r; 
		}
	}
	
	public double GetFitness() {
		return fitness;
	}
	
	//璁＄畻閫傚簲搴﹀嚱鏁�
	public void CalFitness() {
		IJobRule jobRule = null;
		IMachineRule machineRule = null;
		IBufferOutRule buffRule = null;
		ITimeWindowRule twRule = null;
//		this.dataSource = new CMSReader("data/case1.txt");			 //姣忔璋冪敤鏃堕渶瑕侀噸鏂拌闂妯″瀷锛屽惁鍒欐棤娉曡繍琛�
		SimpleScheduler simpleScheduler = new SimpleScheduler(dataSource.getMachineSet(),dataSource.getJobSet(),dataSource.getCellSet());
		
		int i = 0;
		for(; i < nJob ; i++){				//灏咼OB鐨勮鍒欒繘琛岃缃�
			jobRule = Constants.jRules[this.ruleCode[i]];
			dataSource.getJobSet().get(i).setJobRule(jobRule);
		}
		for(; i < nJob+nMachine ; i++){		//灏哅ACHINE鐨勮鍒欒繘琛岃缃�
			machineRule = Constants.mRules[this.ruleCode[i]];
			dataSource.getMachineSet().get(i-nJob).setMachineRule(machineRule);
		}
		for(; i < nJob+nMachine+nCell ; i++){		//灏嗙粍鎵圭殑瑙勫垯杩涜璁剧疆
			buffRule = Constants.bRules[this.ruleCode[i]];
			dataSource.getCellSet().get(i-nJob-nMachine).setBufferOutRule(buffRule);
		}
		for(; i < nJob+nMachine+nCell+nCell ; i++){		//灏員IMEWINDOW鐨勮鍒欒繘琛岃缃�
			twRule = Constants.tRules[this.ruleCode[i]];
			dataSource.getCellSet().get(i-nJob-nMachine-nCell).GetVehicle().setTimeWindowRule(twRule);;
		}
		simpleScheduler.schedule();
		this.fitness = new TotalWeightedTardiness().getMeasurance(simpleScheduler);
		simpleScheduler.reset();
		
	}
	
	public void printInfo(){
		System.out.print("Code:\t");
		for(int i = 0 ; i < nRule ; i++){
			if(i == nJob || i == nJob+nMachine || i == nJob+nMachine+nCell)
				System.out.print("\t");
			System.out.print(this.ruleCode[i]);
		}
		System.out.print("\n");		
		System.out.println("Fitness:\t"+this.fitness);
	}
	
	



}
