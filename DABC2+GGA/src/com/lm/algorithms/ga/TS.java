package com.lm.algorithms.ga;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.lm.algorithms.AbstractScheduler;
import com.lm.algorithms.LocalSearch.LocalSearchMethods;
import com.lm.algorithms.ga.Chromosome;
import com.lm.algorithms.measure.IMeasurance;
import com.lm.domain.Cell;
import com.lm.domain.CellSet;
import com.lm.domain.Job;
import com.lm.domain.JobSet;
import com.lm.domain.Machine;
import com.lm.domain.MachineSet;
import com.lm.domain.Operation;
import com.lm.statistic.RuleFrequencyStatistic;
import com.lm.util.Constants;
/**
 * @Description:TS对比实验

 * @author:zrx

 * @time:2014-10-15 23:07:21

 */

public class TS{
	
/***************************属性域***********************************************************************/	
	/**针对具体问题的GA's evalution process**/
	protected AbstractScheduler evaluator;
	/**适应度评估方法**/
	protected IMeasurance measurance;
	/**最佳适应度函数value对应的适应度**/
	protected double bestFitness = 0d;
	/**最佳的适应度函数的value**/
	protected double bestFunction = 0d;
	/**用于随机的种子**/
	protected Random rand = new Random(System.currentTimeMillis());
	/**机器的tabulist**/
	protected List<Chromosome> mTabuList;
	/**小车的tabulist**/
	protected List<Chromosome> transTabuList;
	/**机器的候选解list**/
	protected List<Chromosome> mPopulation;
	/**小车的候选解list**/
	protected List<Chromosome> transPopulation;
	/**最佳机器染色体序列**/
	protected Chromosome bestmChromosome;
	/**最佳小车染色体序列**/
	protected Chromosome bestTransChromosome;
	/**目前最优value**/
	protected double bestSoFar;
	/**the threshold of generation numbers. dafault=100**/
	protected final int MAX_GENERATION;
	/**候选解集长度. dafault=5**/
	protected final int CandidateSize = 5;
	
	
	
	//the input data for inter-cell problems
	/**the machine's set **/
	protected MachineSet mSet;
	/**the job's set **/
	protected JobSet jSet;
	/**the cell's set **/
	protected CellSet cellSet;
	
	private RuleFrequencyStatistic stat = new RuleFrequencyStatistic();
	
/***************************构造函数域***********************************************************************/	
	/** 
	 * @Description:construction of TS:默认参数
	 * @param mSet
	 * @param jSet
	 * @param cellSet
	 * @param scheduler
	 * @param measurance
	 */
	public TS(MachineSet mSet, JobSet jSet, CellSet cellSet,
			AbstractScheduler scheduler, IMeasurance measurance) {
		this(mSet, jSet, cellSet, scheduler, measurance, 100);
	}

	/**
	 * @Description:construction of TS :自定义GA参数
	 * @param mSet
	 * @param jSet
	 * @param cellSet
	 * @param scheduler
	 * @param measurance
	 * @param cross
	 * @param mutation
	 * @param maxGeneration
	 * @param populationSize
	 * TS
	 * @exception:
	 */
	public TS(MachineSet mSet, JobSet jSet, CellSet cellSet,
			AbstractScheduler scheduler, IMeasurance measurance, int maxGeneration) {

		this.mSet = mSet;
		this.jSet = jSet;
		this.cellSet = cellSet;
		this.measurance = measurance;
		MAX_GENERATION = maxGeneration;
//		RESERVEDPROBABLITY=0.3;
		this.evaluator = scheduler;
		mPopulation = new ArrayList<Chromosome>();
		transPopulation = new ArrayList<Chromosome>();
		
	}
	
/*****************************************************************************************************/

/****************************方法域*********************************************************************/
	
	
	public void schedule(int caseNo) throws CloneNotSupportedException {
		/**给定算法参数，随机产生初始解，设置禁忌表为空**/
		int mSetSize = mSet.size();
		int batchNum = cellSet.size();
		Chromosome mchromosome = new Chromosome(mSetSize);
		Chromosome bchromosome = new Chromosome(batchNum);
		
		int bestIndex = 0;          //记录每代的最优解对应的index
		double func_value,fitness;	//记录每个染色体对应的函数值和适应度
	    
		/**tabulist长度设置为3**/	
		mTabuList = new ArrayList<Chromosome>();
		transTabuList = new ArrayList<Chromosome>();
		int TScheck = 0;    //用于tabulist的循环存入
		
		init_popula_withcheck(mchromosome,bchromosome);
		
		for (int iter = 0; iter < MAX_GENERATION; iter++) {
			
			LocalSearch(mchromosome,bchromosome);
			
			for (int index = 0; index <CandidateSize ; index++) {
				func_value = evaluation(mPopulation.get(index),transPopulation.get(index));
				fitness = 1.0 / (1 + func_value);
				mPopulation.get(index).setFitness(fitness).setFunction(func_value);
				transPopulation.get(index).setFitness(fitness).setFunction(func_value);
			}
			
			bestIndex = bestIn(mPopulation,transPopulation);
			
			Aspiration_Tabu_Check(mPopulation,transPopulation,mchromosome,bchromosome,bestIndex,TScheck);
			
			if(iter==MAX_GENERATION-1){
//			    System.out.println("该种群中最优秀的调度解：");
				bestFunction = bestSoFar;
				System.out.println(bestFunction);
			}
		}
		
		
	}
	
	void init_popula_withcheck(Chromosome mchromosome,Chromosome bchromosome) throws CloneNotSupportedException
	{
		int mSetSize = mSet.size();
		int batchNum = cellSet.size();
		for (int index = 0; index < mSetSize; index++) {
			mchromosome.set(index, rand.nextInt(Constants.mRules.length));
		}

		for (int index = 0; index < batchNum; index++) {
			bchromosome.set(index, rand.nextInt(Constants.TRules.length));
		}


		try {
			evaluator.schedule();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		double func_value = measurance.getMeasurance(evaluator);
		mchromosome.setFunction(func_value);
		bchromosome.setFunction(func_value);
		
		bestSoFar = func_value;
		bestmChromosome = mchromosome.clone();
		bestTransChromosome = bchromosome.clone();
 //		  System.out.println("个体"+mPopulation.size()+":"+bchromosome.getFunction());
		mchromosome.setFitness(1.0 / (1 + func_value));	
		bchromosome.setFitness(1.0 / (1 + func_value));	
				

		
//		System.out.println("初始化结束");
	}
	
	void LocalSearch(Chromosome mchromosome,Chromosome bchromosome) throws CloneNotSupportedException
	{
		for(int index=0;index<CandidateSize;index++){
			//local search for mPop 
//			HillClimbing(0,mchromosome,bchromosome,index);
			swap(0,mchromosome,bchromosome,index);
			//local search for transPop
//			HillClimbing(1,mchromosome,bchromosome,index);
			swap(1,mchromosome,bchromosome,index);
		}
	}
	
	private void swap(int change_popula_flag,Chromosome mchromosome,Chromosome bchromosome,int index) throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		Chromosome m_chr = mchromosome.clone();
		Chromosome trans_chr = bchromosome.clone();

		if(change_popula_flag==0){	// for machine part
			
			Chromosome neighbor1 = new Chromosome(mSet.size());
			Chromosome Curchr = m_chr;
			neighbor1 = swap(Curchr,neighbor1);	
			
			mPopulation.add(neighbor1);
		
		}else if(change_popula_flag==1){	//for trans part
			
			Chromosome neighbor1 = new Chromosome(cellSet.size());
			Chromosome Curchr = trans_chr;
			neighbor1 = swap(Curchr,neighbor1);	
			
			transPopulation.add( neighbor1);
		}
	}
	
	 private  Chromosome swap ( Chromosome segment ,Chromosome segment2)
	    {

	    	for(int i =0;i<segment.size();i++){
	    	    segment2.set(i, segment.get(i));
	    	}
	    	for (int index = 0; index < segment2.size(); index++) {
	    		if(segment2.size()!=0){
//	    		if(segment[index]!=null){
	    				
	    				int[] randoms = getRandomIndex (segment2.size());
	    				int temp = segment2.get(randoms[0]);
	    				segment2.set(randoms[0], segment2.get(randoms[1]));
	    				segment2.set(randoms[1], temp);
	    		}
	    	}
	    		
	    	return segment2;
	    }
	 
	 private  int[] getRandomIndex ( int k )
	    {
	        int[] randoms = new int[2];
	        int a = (int) ( Math.random () * (k) );
	        int b = a;
	        while (b == a)
	        {
	            b = (int) ( Math.random () * (k) );
	        }
	        randoms[0] = a;
	        randoms[1] = b;
	        //System.out.println ("indexs :" + Arrays.toString (randoms));
	        return randoms;
	    }

	private int bestIn(List<Chromosome> mPopulation,List<Chromosome> transPopulation)throws CloneNotSupportedException {
		
		Chromosome temp ;
		Chromosome currentBest = mPopulation.get(0);
		double currentBestFunc = mPopulation.get(0).getFunction();

		for(int i=1;i<mPopulation.size();i++) {
			temp=mPopulation.get(i);
			if(temp.getFunction() <= currentBestFunc){
				currentBest 	= temp;
				currentBestFunc = temp.getFunction();
			}
		}
		int index = 0;
		for(int i=0;i<mPopulation.size();i++){
			if(mPopulation.get(i).getFunction()==currentBest.getFunction()){
				index = i;
			}
		}
		
		
		
		return index;
	}
	
	void Aspiration_Tabu_Check(List<Chromosome> mPopulation,List<Chromosome> transPopulation,Chromosome mchromosome,Chromosome bchromosome,int bestIndex,int check) throws CloneNotSupportedException{
		
		if(mPopulation.get(bestIndex).getFunction() < bestSoFar){
			mchromosome = mPopulation.get(bestIndex);
			bchromosome = transPopulation.get(bestIndex);
			
			mTabuList.set(check,mchromosome.clone());
			transTabuList.set(check,bchromosome.clone());
			check = check%3+1;
			bestSoFar = mPopulation.get(bestIndex).getFunction();
			bestmChromosome = mchromosome.clone();
			bestTransChromosome = bchromosome.clone();
		}else{
			for(int i=0;i<mPopulation.size();i++){
				for(int j=0;j<mTabuList.size();j++){
					if(mPopulation.get(i)==mTabuList.get(j)&&transPopulation.get(i)==transTabuList.get(j)){
						mPopulation.remove(i);
						transPopulation.remove(i);
					}
				}
			}
			int bestindex = bestIn(mPopulation,transPopulation);
			mchromosome = mPopulation.get(bestindex);
			bchromosome = transPopulation.get(bestindex);
			mTabuList.set(check,mchromosome.clone());
			transTabuList.set(check,bchromosome.clone());
			check = check%3+1;
			if(mchromosome.getFunction()<bestSoFar){
				bestSoFar = mchromosome.getFunction();
				bestmChromosome = mchromosome.clone();
				bestTransChromosome = bchromosome.clone();
			}
		}
		
		
	}
	
	private void HillClimbing(int change_popula_flag,Chromosome mchromosome,Chromosome bchromosome,int index) throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		Chromosome m_chr = mchromosome.clone();
		Chromosome trans_chr = bchromosome.clone();

		if(change_popula_flag==0){	// for machine part
			double Current = evaluation(m_chr, trans_chr);
			double neighbor1,neighbor2;
			Chromosome chr1,chr2;
			int count=0;
			Chromosome Curchr = m_chr;
			while(true){
				if(count==Curchr.size()-1) break;
				
				chr2 = Neighbor2(Curchr,count);
				neighbor2=evaluation(chr2,trans_chr);
				chr1 = Neighbor(Curchr,count++);
				neighbor1=evaluation(chr1,trans_chr);

				if(neighbor1 > Current && neighbor2 > Current) break;
//				if(neighbor1 > Current) break;
				
				if(neighbor1 > neighbor2){
					Current = neighbor2;
					Curchr  = chr2;
				}
				else{
					Current = neighbor1;
					Curchr  = chr1;
				}
			}	
			mPopulation.add(Curchr);
		}else if(change_popula_flag==1){	//for trans part
			double Current = evaluation(m_chr, trans_chr);
			double neighbor1,neighbor2;
			Chromosome chr1,chr2;
			int count=0;
			Chromosome Curchr = trans_chr;
			while(true){
				if(count==Curchr.size()-1) break;
				
				chr2 = Neighbor2(Curchr,count);
				neighbor2=evaluation(m_chr,chr2);
				chr1 = Neighbor(Curchr,count++);
				neighbor1=evaluation(m_chr,chr1);

				if(neighbor1 > Current && neighbor2 > Current) break;
//				if(neighbor1 > Current) break;
				
				if(neighbor1 > neighbor2){
					Current = neighbor2;
					Curchr  = chr2;
				}
				else{
					Current = neighbor1;
					Curchr  = chr1;
				}
			}
			transPopulation.add( Curchr);
		}
	}
	
	/**
	 * @Description evalution process for TS
	 * @param trans_chromosome chromosome for trans part 
	 * @param m_chromosome chromosome for machine part
	 */
	protected double evaluation(Chromosome m_chromosome, Chromosome trans_chromosome) {
			updateMachineRules(m_chromosome);
			updateTransRules(trans_chromosome);
			
//    		long start = System.currentTimeMillis();
//    		System.out.println("初始Timer:"+Timer.currentTime());
			try {
				evaluator.schedule();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			long end   = System.currentTimeMillis();
//			System.out.println("本次时间:"+(end-start)+"ms");
			
			/**确定fitness的过程很重要，比例太接近不利于决定进化方向**/
			return measurance.getMeasurance(evaluator);
	}
	
	/** 根据染色体更新机器规则 */
	/**
	 * @Description update rules for machine of mset
	 * @param chromosome
	 */
	protected void updateMachineRules(Chromosome chromosome) {
		int index = 0;
		String MachineGene="";
		for (Machine m : mSet) {
			MachineGene+=chromosome.get(index)+",";
			m.setRule(Constants.mRules[chromosome.get(index++)]);
		}
//		Utils.echo(MachineGene);
	}
	
	/**
	 * @Description update rules for vehicles of cellSet
	 * @param chromosome
	 */
	protected void updateTransRules(Chromosome chromosome) {
		int index = 0;
		String CellGene="";
		for (Cell m : cellSet) {
			CellGene+=chromosome.get(index)+",";
			m.setRule(Constants.TRules[chromosome.get(index++)]);
		}
	}
	
	private Chromosome Neighbor2(Chromosome Chr, int i) throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		if(i<0 || i>=Chr.size()-1)
			throw new IllegalArgumentException("index out of boundary");
		Chromosome chr_new = Chr.clone();
		int left = Chr.get(i);
		if(left ==0) 	chr_new.set(i, left+1);
		else 			chr_new.set(i, left-1);
		return chr_new;
	}

	/**
	 * @Description 染色体的领域结构 ————这里采取交换 左右两个基因的领域结构
	 * @param chromosome
	 * @param i
	 * @return
	 * @throws CloneNotSupportedException 
	 */
	private Chromosome Neighbor(Chromosome Chr, int i) throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		if(i<0 || i>=Chr.size()-1)
			throw new IllegalArgumentException("index out of boundary");
		Chromosome chr_new = Chr.clone();
		int left = chr_new.get(i);
		chr_new.set(i, chr_new.get(i+1));
		chr_new.set(i+1, left);
		return chr_new;
	}
	
	/**
	 * @Description 获取stat变量(各个规则的频率)
	 * @return stat变量
	 */
	public RuleFrequencyStatistic getStat() {
		return stat;
	}

	/**
	 * @Description 设置stat变量
	 * @param stat
	 */
	public void setStat(RuleFrequencyStatistic stat) {
		this.stat = stat;
	}
	
	/**
	 * @Description 将各参数转化为一串字符串
	 * @return 一个String变量
	 */
	public String parameter() {
		StringBuilder sb = new StringBuilder(30);
		sb.append(MAX_GENERATION);
		return sb.toString();
	}
	
	/**
	 * @Description 获取 bestFunction变量的值
	 * @return value of bestFunction
	 */
	public double getFunctionValue() {
		return bestFunction;
	}
	
}