package com.lm.algorithms.abc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.lm.algorithms.MetaHeuristicScheduler;
import com.lm.algorithms.abc.Chromosome;
import com.lm.algorithms.measure.Makespan;
import com.lm.algorithms.measure.MetaIMeasurance;
import com.lm.Metadomain.CellSet;
import com.lm.Metadomain.JobSet;
import com.lm.Metadomain.MachineSet;
import com.lm.Metadomain.Operation;
import com.lm.statistic.RuleFrequencyStatistic;
import com.lm.util.Constants;
import com.lm.util.HeapMaxPriorityQueue;
import com.lm.util.MapUtil;
import com.lm.util.MaxPriorityQueue;

import java.lang.StringBuffer;
/*
 * @Description:DABC class
 * 
 */

public class DABC_AgingLeader {


/*******************************************属性域*******************************************************/
	/**针对具体问题的调度过程**/
	protected MetaHeuristicScheduler evaluator;
	/**适应度评估方法**/
	protected MetaIMeasurance measurance ;
	/**用于随机的种子**/
	protected Random rand = new Random(System.currentTimeMillis());
	/**最佳的适应度函数的value**/
	protected double bestFunction = 0d;
	/**最差的适应度函数的value**/
	protected double worstFunction = 0d;
	/**写文件内容**/
	BufferedWriter bw;
	//种群相关数据
	/**每一代的种群 -- 算法中初始大小是20**/
	protected List<Chromosome> Population;
	/**每一代的pbest**/
	protected List<Chromosome> pbest_Pop;
    /**gbest**/
	protected Chromosome bestChromosome;
	/**leader**/
	protected Chromosome leader;
	/**challenger**/
	protected Chromosome challenger;
	/**LifeSpan**/
	protected int lifespan;
	/**LifeTime**/
	protected int lifeTime;
	/**last gbest's Func**/
	protected double LastgBestFunc;
	/**last pbest's Funcs**/
	protected double [] LastpBestFunc;
	/**last leader's Func**/
	protected double LastLeaderFunc;
	/**判断什么时候由Challenge影响  
	 * 0： 不被challenge影响
	 * 1，2 ：被challenge影响
	 * 超过2：说明迭代次数超过2次，重置成0
	 * **/
	protected int IsEffectByChallenge;
	/**最差染色体序列**/
	protected Chromosome worstChromosome;
	//算法参数
	protected int POPULATION_SIZE=48;                       //修改量POPULATION_SIZE
	/**the maxmum of iteration. default=100  Agingleader取值**/
	protected final int MaxCycle=100;                              //修改量MaxCycle
	
	/** rj 取0-1之间的随机数**/
	
	protected final double w = 0.4;
	protected final double c1 = 2;
	protected final double c2 = 2;
	protected final int Tsteps = 2;       	/** steps interval to evaluate the leadingPower**/
	protected final int SetaInit = 60; 								  // init lifespan
	
	//the input data for inter-cell problems
	/**the machine's set **/
	protected MachineSet mSet;
	/**the job's set **/
	protected JobSet jSet;
	/**the cell's set **/
	protected CellSet cellSet;
    
	
/***************************构造函数域***********************************************************************/
	
	/** 
	 * @Description:construction of DABC:默认参数
	 * @param mSet
	 * @param jSet
	 * @param cellSet
	 * @param scheduler
	 * @param measurance
	 */
	public DABC_AgingLeader(MachineSet mSet, JobSet jSet, CellSet cellSet,
			MetaHeuristicScheduler scheduler, MetaIMeasurance measurance) {
		this(mSet, jSet, cellSet, scheduler, measurance,100, 48);
	}

	/**
	 * @Description:construction of DABC:自定义参数
	 * @param mSet
	 * @param jSet
	 * @param cellSet
	 * @param scheduler
	 * @param measurance
     * @param populationSize
	 * DABC
	 * @exception:
	 */
	public DABC_AgingLeader(MachineSet mSet, JobSet jSet, CellSet cellSet,
			MetaHeuristicScheduler scheduler, MetaIMeasurance measurance, int Maxcycle,
			int populationSize) {
		this.mSet = mSet;
		this.jSet = jSet;
		this.cellSet = cellSet;
		this.measurance = measurance;
		this.POPULATION_SIZE = populationSize;
		this.evaluator = scheduler;
		this.Population = new ArrayList<Chromosome>();
		this.pbest_Pop = new ArrayList<Chromosome>();
		this.LastpBestFunc = new double[populationSize];
		this.IsEffectByChallenge = 0;
	}

/*****************************************************************************************************/

/****************************方法域*********************************************************************/
	/**目标函数&适应度函数：makespan或者totalweightedtardiness*/

	/**获取bestfunctionvalue**/
	public  double getBestFunctionValue() {
	    return bestFunction; 
	}
	
	/**获取worstfunctionvalue**/
    public double getWorstFunctionValue(){
		return worstFunction;
	}


	/** MAIN PROCESS
	 * @throws CloneNotSupportedException 
	 * @throws IOException 
	 * @Description framework for DABC
	 * 
	 */
	public void schedule(int caseNo) throws CloneNotSupportedException, IOException {
		
		int iter=0;
		init_population();
		
		updatePop();
		
		InitLife();
		
		//if the dir is exists
		File file = new File(Constants.AG_ITER_DIR);
		if (!file.exists()) {
			   file.mkdir();
		}
		
		//write the file
		this.bw = new BufferedWriter(new FileWriter(
				Constants.AG_ITER_DIR+ "/"+ caseNo
				));

		for (iter=0;iter<MaxCycle;iter++){                               //迭代数
			
			EmployedBees();
		    OnlookerBees();
		    
		    updatePop();
		    
		    //judging whether adopts the Challenge according to the life_time
		    AdjustLifeSpan();
		    
		    lifeTime = lifeTime+1;
		    if(lifeTime >=lifespan){
		    	GenerateChallenger();
		    	IsEffectByChallenge = 1;
		    }else {
		    	lifeTime = lifespan - 1;
		    }
		    
		    ScoutBees();
			if(iter==MaxCycle-1){
//			    System.out.println("该种群中最优秀的调度解：");
				System.out.println(bestFunction);
			}
			System.out.println(bestFunction);
			bw.write(bestFunction+"\n");
		}
		bw.flush();
		bw.close();
	}

	/**
	 * According the Decision Tree to update the lifeSpan
	 */
	private void AdjustLifeSpan() {
		if(bestChromosome.getFunction() - LastgBestFunc < 0){ // Good Leading Power
			lifespan = lifespan+2;
		}else if(SumDivPbest()<0) { // Good Leading Power
			lifespan = lifespan+1;
		}else{
			if(leader.getFunction() - LastLeaderFunc <0) {  // Fair Leading Power
				lifespan = lifespan;	
			}else{ //Poor Leading Power
				lifespan = lifespan - 1;
			}
		}
	}

	private double SumDivPbest() {
		double sum = 0;
		for(int i = 0; i< POPULATION_SIZE; i++){
			sum+= pbest_Pop.get(i).getFunction() - LastpBestFunc[i];
		}
		return sum;
	}

	/**
	 * Init lifespan and lifeTime
	 */
	private void InitLife() {
		lifeTime=0;
		lifespan=SetaInit;
	}

	private void GenerateChallenger() throws CloneNotSupportedException {
		
		int mSetSize = mSet.size();
		int vSetSize = cellSet.size();

		challenger = leader.clone();
		
		/** 每个维度在L,U之间取得random值 -- 在0,1间random取得 **/
		for (int index = 0; index <mSetSize+1; index++) {     
			double Lj = rand.nextDouble();
			double Lu = rand.nextDouble()*(1.0-Lj)+Lj;
			
			double value = rand.nextDouble();
			if(value>=Lj && value <= Lu) {  //范围内就random一个结果
				challenger.setMachineSegment(index, RandomPriors(Constants.MachineToParts[index]).clone());
			}
		}
		for (int SourceIndex = 0; SourceIndex <vSetSize+1; SourceIndex++) {
			double Lj = rand.nextDouble();
			double Lu = rand.nextDouble()*(1.0-Lj)+Lj;
			
			double value = rand.nextDouble();
			if(value>=Lj && value <= Lu) {  //范围内就random一个结果
				int[] VehicleCellSquence = RandomPriors(Constants.CellToNextCells[SourceIndex]);
				challenger.setVehicleSegment(SourceIndex,VehicleCellSquence);
				
				for (int TargetIndex = 0; TargetIndex <VehicleCellSquence.length; TargetIndex++){
					int TargetCell = VehicleCellSquence[TargetIndex];
					if(Constants.CellToParts[SourceIndex][TargetCell]!=null){//在这里对从source到target的所对应序列是否为空进行判断
						if(Constants.CellToParts[SourceIndex][TargetCell].size()!=0){
							int[] temp =new int[Constants.CellToParts[SourceIndex][TargetCell].size()];                                                  //Arraylist<Integer>转换为数组
					        int k=0;
			        		for(int o :Constants.CellToParts[SourceIndex][TargetCell] ){
	                              temp[k]=o;
	                              k++;
						    }
			        		challenger.setPartSequence(SourceIndex,TargetCell,RandomPriors(temp));
						}
						else{
							Constants.CellToParts[SourceIndex][TargetCell]=null;
						}
					}
				}
		     }// end if
		}
		
		challenger.setFunction(evaluation(challenger));
	}
	/**
	 * @Description update pBest && value
	 * @throws CloneNotSupportedException
	 */
	private void updatePop() throws CloneNotSupportedException {
		//update pbest
		for(int i=0;i<POPULATION_SIZE;i++){
			LastpBestFunc[i] = pbest_Pop.get(i).getFunction();
			
			if(Population.get(i).getFunction() < pbest_Pop.get(i).getFunction()){
				pbest_Pop.set(i, Population.get(i).clone());
				
				if(IsEffectByChallenge!=0) { //表明此时在testing Whether替换challenge,按算法设计此时要替换challenge
					leader = challenger.clone();
					IsEffectByChallenge = 0;
					InitLife();
				}
			}
		}
		
		//update gbest
		LastgBestFunc = bestChromosome.getFunction();
		bestChromosome = bestSoFar(Population, bestChromosome);
		
		if(IsEffectByChallenge!=0) { //经过pbest后，没有更新.说明当前还是Challenger个体
			//update challenge
			if(challenger.getFunction() > bestChromosome.getFunction()){
			   challenger = bestChromosome.clone();
			}
			IsEffectByChallenge++;
			
			if(IsEffectByChallenge == Tsteps){ //达到测试极限，未达到预期效果，回归原leader
				IsEffectByChallenge = 0;
				lifeTime = lifespan - 1;
			}
		} else {
			//update leader
			LastLeaderFunc = leader.getFunction();
			if(leader.getFunction() > bestChromosome.getFunction()){
				leader = bestChromosome.clone();
			}
		}
		
	}
		
	/**
	 * @Description evalution process for GA
	 * @param trans_chromosome chromosome for trans part 
	 * @param m_chromosome chromosome for machine part
	 */
	protected double evaluation(Chromosome chromosome) {
	     	int mSetSize = mSet.size();
		    int vSetSize = cellSet.size();	
//		    System.out.println("Msize"+ mSetSize);
		    for(int i=0;i<mSetSize;i++){
//		    	System.out.println(chromosome.MachineSegment[i+1].toString());
		        int[] temp = new int[chromosome.MachineSegment[i+1].length-1];
		        for(int j=0;j<chromosome.MachineSegment[i+1].length-1;j++){
		        	temp[j]=chromosome.MachineSegment[i+1][j+1];
		        }
		    	mSet.get(i).setPriorSequence(temp);
		    }
		    for(int i=0;i<vSetSize;i++){
		    	int[] temp = new int[chromosome.VehicleSegment[i+1].length-1];
//		    	String[] Temp = new String[chromosome.IntercellPartSequences[i+1].length-1];
		    	for(int j=0;j<chromosome.VehicleSegment[i+1].length-1;j++){
		    		temp[j]=chromosome.VehicleSegment[i+1][j+1];
		    	}
		    	cellSet.get(i).setPriorSequence(temp);
//		    	for(int j=0;j<chromosome.IntercellPartSequences[i+1].length-1;j++){
//		    		Temp[j]=chromosome.IntercellPartSequences[i+1][j+1];
//		    	}
//		    	cellSet.get(i).setIntercellPartSequences(Temp);
		    	cellSet.get(i).setIntercellPartSequences(chromosome.IntercellPartSequences[i+1]);
		    }
//    		long start = System.currentTimeMillis();
//    		System.out.println("初始Timer:"+Timer.currentTime());
			evaluator.schedule();
//			long end   = System.currentTimeMillis();
//			System.out.println("本次时间:"+(end-start)+"ms");
	
			
			return measurance.getMeasurance(evaluator);
	}

	
	/**
	 * @Description Find the best chromosome in population
	 * @param population
	 * @param bestChromosome
	 * @return bestChromosome
	 * @throws CloneNotSupportedException
	 */
	private Chromosome bestSoFar(List<Chromosome> population, Chromosome bestChromosome)
			throws CloneNotSupportedException {
		Chromosome temp ;
		
		Chromosome currentBest = Population.get(0);
		double currentBestFunc = Population.get(0).getFunction();
		
		for(int i=1;i<POPULATION_SIZE;i++) {
			temp=Population.get(i);
			if(temp.getFunction() <= currentBestFunc){
				currentBest 	= temp;
				currentBestFunc = temp.getFunction();
			}
		}
		
		if (bestChromosome == null) {
			bestChromosome = currentBest;
			bestFunction  = evaluation(bestChromosome);
		} else if (currentBest.getFunction() <= bestChromosome.getFunction()) {
				bestChromosome = currentBest;
				bestFunction  = evaluation(bestChromosome);
		}
		bestChromosome.setFitness(bestFunction);
		return bestChromosome;
	}
	
	
	/**
	 * @Description insert the candidate into the Collect
	 * @param temp
	 * @param curHeap2 
	 * @throws CloneNotSupportedException 
	 */
	private void InsertInCollect(Chromosome temp, HeapMaxPriorityQueue<Chromosome> set) throws CloneNotSupportedException {
		for(Chromosome cur: set){
			if(cur.equals(temp)){
				return;
			}
		}	
		set.insert(temp.clone());	
	}

	/**
	 * @Description Find the worst chromosome
	 * @param population
	 * @param worstChromosome
	 * @return worstChromosome
	 * @throws CloneNotSupportedException
	 */
	private Chromosome worstSoFar(List<Chromosome> population, Chromosome worstChromosome)
			throws CloneNotSupportedException {
//		Chromosome currentWorst = Collections.max(population).clone();
		Chromosome chromosome1 ;
		Chromosome chromosome2 ;
		Chromosome currentWorst = new Chromosome(mSet.size(), cellSet.size()) ;
		for(int i=0;i<POPULATION_SIZE-1;i++) {
			chromosome1=Population.get(i);
			chromosome2=Population.get(i+1);
			if(i==0){
				if(chromosome1.getFunction() >= chromosome2.getFunction()){
					currentWorst=chromosome1;
				}
				else {
					currentWorst=chromosome2;
//				Population.set(i+1,currentBest);
				}
			}
			else if(currentWorst.getFunction() <= chromosome2.getFunction()){
				currentWorst=chromosome2;
//				Population.set(i,currentBest);
				}
		}
		if (worstChromosome == null) {
			worstChromosome = currentWorst;
			worstFunction  = worstChromosome.getFunction();
		} else if (currentWorst.getFunction() >= worstChromosome.getFunction()) {
			    worstChromosome = currentWorst;
				worstFunction  = worstChromosome.getFunction();
		}
		
//		System.out.println("该种群中最差的调度解：");
////		System.out.println("MachineSegment:"+Arrays.toString(worstChromosome.MachineSegment));
////		System.out.println("VehicleSegment:"+Arrays.toString(worstChromosome.VehicleSegment));
////		System.out.println("InterCellSequence:"+Arrays.toString(worstChromosome.IntercellPartSequences));
//		System.out.println("最差解的函数值:"+worstFunction);
//		worstFunction=0;
		
		return worstChromosome;
	}
	
	/**
	 * @Description 
	 * 初始化：
	 * 初始解的GP产生接口，初始解通过rules产生，初始解通过random产生
	 * 应该是单独建立3个部分，可以切换，从而形成3组初始解产生机制
     * ①GP产生；②rules产生；③random
	 * @throws CloneNotSupportedException
	 */
	private void init_population() throws CloneNotSupportedException {                                   //单个解的初始
        int i;		
        int mSetSize = mSet.size();
		int vSetSize = cellSet.size();	              
		Chromosome chromosome = new Chromosome(mSetSize,vSetSize);
			
		for(i=0;i<POPULATION_SIZE;i++) {
			for (int index = 0; index <mSetSize+1; index++) {                      
                 chromosome.setMachineSegment(index, RandomPriors(Constants.MachineToParts[index]));
			}
			                                                                      
			for (int SourceIndex = 0; SourceIndex <vSetSize+1; SourceIndex++) {
				int[] VehicleCellSquence = RandomPriors(Constants.CellToNextCells[SourceIndex]);
//				if(VehicleCellSquence.length !=0){
				chromosome.setVehicleSegment(SourceIndex,VehicleCellSquence);
//				}
				for (int TargetIndex = 0; TargetIndex <VehicleCellSquence.length; TargetIndex++){
					int TargetCell = VehicleCellSquence[TargetIndex];

			                                                   //Arraylist<Integer>转换为数组
//			        StringBuffer strBuffer = new StringBuffer();
			        if(Constants.CellToParts[SourceIndex][TargetCell]!=null){
			        	if(Constants.CellToParts[SourceIndex][TargetCell].size()!=0){
			        		 int[] temp = new int [Constants.CellToParts[SourceIndex][TargetCell].size()]; 
			        		 int k=0;
			        		 for(int o :Constants.CellToParts[SourceIndex][TargetCell] ){
//						        	strBuffer.append(o);
//						        	temp = new int[]{Integer.parseInt(strBuffer.toString())};
                                    temp[k]=o;
                                    k++;
//						        	strBuffer.delete(0, strBuffer.length());
						     }
			        		 chromosome.setPartSequence(SourceIndex,TargetCell,RandomPriors(temp));  
			        	}		
			        }			       			    			         
				}
			}
			
			double func_value = evaluation(chromosome);
//			double func_value = 100.00;
			chromosome.setFunction(func_value);
					
			//create pop
			AddToPopulation(Population,chromosome.clone());	
			
			//update pbest
			AddToPopulation(pbest_Pop,chromosome.clone());
			}	
		
		//update gbest&&leader
		bestChromosome = bestSoFar(Population, bestChromosome);
		leader = bestChromosome.clone();
		
//		PrintPop();
	}
	
	
	/**
	 * print the whole pop
	 */
	private void PrintPop() {
		for(int i =0; i < POPULATION_SIZE; i++){
			System.out.println("第i个个体\t"+Population.get(i).getFunction());
		}
	}

	/**
	 * @Description Employed Bee Phase 
     * 对每个解用localsearch1得到neighbour，neighbour要与之前保证不同
     * 用评估函数比较source和neighbour的优劣，取优秀者赋给neighbour
	 * @throws CloneNotSupportedException
	 */
	void EmployedBees() throws CloneNotSupportedException
	{
		    LocalSearch1();
		    //LocalSearch2();  //加不加这个，视情况而定
    }
	
	/**
	 * @Description onlooker Bee Phase
	 * EC论文的AgingLeader方法
	 */
	void OnlookerBees() throws CloneNotSupportedException
	{
		 for(int i = 0;i<Population.size();i++){
				Population.set(i, Mutation(i));
		 }
	}

	/**
	 * @Description Scoutbees' generation
	 */
	void ScoutBees() throws CloneNotSupportedException
	{
		//在所有neighbor2的population中找出最差的解，记为worstneighbor2
		worstChromosome=worstSoFar(Population,worstChromosome);
		
		int m=0;
		for(int i=0;i<POPULATION_SIZE;i++){
			if(worstChromosome.getFunction()==Population.get(i).getFunction()){
				m=i;
				break;
			}
		}
		
//		if(Memory.size()!=0){
//			/*****Mutationg generate*****/
//			Population.set(m, Mutation(worstChromosome,m));
//		}else{
			/*****Randomly generate**/    
			Population.set(m, RandomlyGenerate());
//		}// end else
	}

	private Chromosome RandomlyGenerate() {
		    int mSetSize = mSet.size();
		    int vSetSize = cellSet.size();

		    Chromosome cur = new Chromosome(mSetSize,vSetSize);
		    
			for (int index = 0; index <mSetSize+1; index++) {                      
				cur.setMachineSegment(index, RandomPriors(Constants.MachineToParts[index]).clone());
			}
			                                                                      
			for (int SourceIndex = 0; SourceIndex <vSetSize+1; SourceIndex++) {
				int[] VehicleCellSquence = RandomPriors(Constants.CellToNextCells[SourceIndex]);
				cur.setVehicleSegment(SourceIndex,VehicleCellSquence);
				
				for (int TargetIndex = 0; TargetIndex <VehicleCellSquence.length; TargetIndex++){
					int TargetCell = VehicleCellSquence[TargetIndex];
					if(Constants.CellToParts[SourceIndex][TargetCell]!=null){//在这里对从source到target的所对应序列是否为空进行判断
						if(Constants.CellToParts[SourceIndex][TargetCell].size()!=0){
							int[] temp =new int[Constants.CellToParts[SourceIndex][TargetCell].size()];                                                  //Arraylist<Integer>转换为数组
//					        StringBuffer strBuffer = new StringBuffer();
					        int k=0;
			        		for(int o :Constants.CellToParts[SourceIndex][TargetCell] ){
//						        	strBuffer.append(o);
//						        	temp = new int[]{Integer.parseInt(strBuffer.toString())};
                                  temp[k]=o;
                                  k++;
//						        	strBuffer.delete(0, strBuffer.length());
						    }
			        		cur.setPartSequence(SourceIndex,TargetCell,RandomPriors(temp));
						}
						else{
							Constants.CellToParts[SourceIndex][TargetCell]=null;
						}
					}
					     //System.out.println(SourceIndex+TargetCell);
				}
			}
			cur.setFunction(evaluation(worstChromosome));
			for(int i=1;i<worstChromosome.IntercellPartSequences.length;i++){
				cur.IntercellPartSequences[i][0]=null;
			}
		return cur;
	}

	/**
	 * @Description Mutation Process For The Whole Population
	 * @param origin
	 * @return
	 */
    private Chromosome Mutation(int index) {
		Chromosome origin  = Population.get(index);
		Chromosome pbest_i = pbest_Pop.get(index);
		Chromosome best;
		if(IsEffectByChallenge == 0){
			best = leader;
		}else {
			best = challenger;
		}
		
		int msize = origin.getMachineSize();
    	int vsize = origin.getVehicleSize();
		int[] tmp = new int[0];
    	Chromosome New 	  = new Chromosome(msize,vsize);
		// 针对机器段
		New.setMachineSegment(0,tmp);
    	for(int i = 1; i <= msize; i++){
    		New.setMachineSegment(
    				i, 
    				MutateOperate(
    						origin.getMachineSegment()[i],
    						pbest_i.getMachineSegment()[i],
    						best.getMachineSegment()[i]
    				)
    		);		
    	}
    	//针对小车段
    	New.setVehicleSegment(0, tmp);
    	for(int i = 1; i <= vsize; i++){
    		New.setVehicleSegment(
    				i, 
    				MutateOperate(
    						origin.getVehicleSegment()[i],
    						pbest_i.getVehicleSegment()[i],
    						best.getVehicleSeq(i)
    				)
    		);		
    	}
    	//针对单元间的工件
    	for(int i = 1; i <= vsize; i++){
    		for(int j = 1; j <= vsize; j++){
    			if(i!=j){
    				New.setPartSequence(i, j, 
    						MutateOperate(
    								ConvertToIntArray(origin.getPartSequence()[i][j]),
    								ConvertToIntArray(pbest_i.getPartSequence()[i][j]),
    								ConvertToIntArray(best.getPartSequence()[i][j])
    						)
    				);
    			}
    		}
    	}  
    	New.setFunction(evaluation(New));
    	//return the changed one
		return New;
	}

    /**
     * @Description get a different number compared to index
     * @param index
     * @return
     */
    private int GetAnotherNumber(int index) {
    	int result;
    	while(true){
    		result = (int)(Math.random () *POPULATION_SIZE);
    		if(result!=index) break;
    	}		
    	return result;
	}
	
	/**
     * @Description convert ArrayList<Integer> to int[]
     * @param origin
     * @return
     */
    private int[] ConvertToIntArray(ArrayList<Integer> origin) {
		int []result = new int[origin.size()+1];
		result[0] = -1;
		for(int i = 1; i < result.length; i++){
			result[i] = origin.get(i-1);
		}
    	return result;
	}

	/**
     * @param MemoryBest 
	 * @param js 
	 * @Description 具体的变异操作
     * 通过将优先级转化成可以比较的数值 -- 初定每个数值之间差值为1
     * @param x1
     * @param x2
     * @return
     */
    private int[] MutateOperate(int [] X, int[] XBest, int[] X_1){
    	Map<Integer, Double> Result        = new HashMap<Integer, Double>();
    	/**优先级数组转化成可以比较数值大小的数组
    	 * Priors_X       是origin
    	 * Priors_Xbest   是pBest
    	 * Priors_X1                     是leader/ challenge
    	 */
    	Map<Integer, Integer> Priors_X     = new HashMap<Integer, Integer>();
    	Map<Integer, Integer> Priors_Xbest = new HashMap<Integer, Integer>();
    	Map<Integer, Integer> Priors_X1    = new HashMap<Integer, Integer>();
    	
    	int count = 1;
    	for(int i = X.length - 1; i >=1; i--){	//第0位是0，无用数据，不用存储
    		Priors_X.put(X[i], count);
    		Priors_Xbest.put(XBest[i], count);
    		Priors_X1.put(X_1[i], count);
    		count++;
    	}
    	
    	/**Generate r1 && r2**/
    	Double r1 = rand.nextDouble();
    	Double r2 = rand.nextDouble();
    	/** The operators begin**/
    	for(int i = 1; i < X.length; i++){
    		Result.put(X[i],
    				w*Priors_X.get(X[i])
    				+c1*r1* ( Priors_Xbest.get(X[i]) - 	Priors_X.get(X[i])) 
    				+c2*r2*( Priors_X1.get(X[i]) 	- 	Priors_X.get(X[i]))
    		);
    	}
    	/** The operators end**/
    	
    	/**Make It Feasible
    	 * dispatch the job according the values
    	 * **/
    	Map<Integer, Integer> Sort = MapUtil.ReturnValueSequences(Result);      	//sort
    	if(X[0]==0){	//if head with 0
        	int [] New = new int[Result.size()+1];
	    	New[0] = 0;
	    	for(int i = 0 ;i < Result.size(); i++){
	    		New[i+1] = Sort.get(i);
	    	}
	    	return New;
	    }else{			//if head without 0,means it is Sequence
	    	int [] New = new int[Result.size()];
	    	for(int i = 0 ;i < Result.size(); i++){
	    		New[i] = Sort.get(i);
	    	}
	    	return New;
	    }
    }
    
    /**
     * @Description localsearch1
     * @throws CloneNotSupportedException
     */
    private void LocalSearch1() throws CloneNotSupportedException {

	    int i;
//	    int a=6;
//	    int b=3;
	    Chromosome chromosome =new Chromosome(mSet.size(), cellSet.size());
	    Chromosome neighbor1 = new Chromosome(mSet.size(), cellSet.size()) ;
//	    Chromosome neighbor1 = new Chromosome(a, b) ;        //a是mSet.size(),  b是cellSet.size();
 
	    for(i=0;i<POPULATION_SIZE;i++) {
	       
	    	chromosome=Population.get(i);
	    	chromosome.setFunction(evaluation(chromosome));
//	    	func_value = 40;
	    	neighbor1.MachineSegment = swap(chromosome.clone().getMachineSegment(),neighbor1.MachineSegment);
			neighbor1.VehicleSegment =  swap(chromosome.clone().getVehicleSegment(),neighbor1.VehicleSegment);
			neighbor1.IntercellPartSequences = swap(chromosome.clone().getPartSequence(),neighbor1.IntercellPartSequences);                   //swap的error是由于intersequence的类型引起的
		
			neighbor1.setFunction(evaluation(neighbor1));
//	    	neighbor_func = 50;
		    if(neighbor1.getFunction()<=chromosome.getFunction()){
			    chromosome=neighbor1;
		    }
		    Population.set(i,chromosome.clone());
//		    System.out.println("初始种群中第"+(i+1)+"个调度解经过ls1后的函数值："+chromosome.getFunction());
	    }
    }

	/**
	 * @Description localsearch2
	 * @throws CloneNotSupportedException
	 */
	private void LocalSearch2() throws CloneNotSupportedException {
	
	    int i;

	    Chromosome chromosome ;
	    Chromosome neighbor2 = new Chromosome(mSet.size(), cellSet.size()) ;
	
	    for(i=0;i<POPULATION_SIZE;i++) {
		       
	    	chromosome=Population.get(i);
//	    	chromosome.setFunction(evaluation(chromosome));
//	    	func_value=30;
	    	neighbor2.MachineSegment = insert(chromosome.clone().getMachineSegment(),neighbor2.MachineSegment);
			neighbor2.VehicleSegment = insert(chromosome.clone().getVehicleSegment(),neighbor2.VehicleSegment);
			neighbor2.IntercellPartSequences = insert(chromosome.clone().getPartSequence(), neighbor2.IntercellPartSequences);                   //swap的error是由于intersequence的类型引起的
			neighbor2.setFunction(evaluation(neighbor2));
//			neighbor_func=50;
		    
		    if(neighbor2.getFunction()<=chromosome.getFunction()){
			    chromosome=neighbor2;
		    }
		    Population.set(i,chromosome.clone());
//		    System.out.println("初始种群中第"+(i+1)+"个调度解经过ls2后的函数值："+chromosome.getFunction());
	    }
    }


	private ArrayList<Integer>[][] swap (ArrayList<Integer>[][] Sequences,ArrayList<Integer>[][] Sequences2){

		Sequences2=new ArrayList[Sequences.length][];
		for(int i =0;i<Sequences.length;i++){
			Sequences2[i]=new ArrayList[Sequences[i].length];
    		for(int j=0;j<Sequences[i].length;j++){
    			
    			if(Sequences[i][j]!=null){
        			int[] temp2 =new int[Sequences[i][j].size()];
    				Sequences2[i][j]=new ArrayList(Sequences[i][j].size());
    				if(Sequences[i][j].size()!=0){
    					if(Sequences[i][j].size()!=2){
    			
    						int[] temp =new int[Sequences[i][j].size()];                                                  //Arraylist<Integer>转换为数组

					        int k=0;
			        		for(int o :Sequences[i][j]){

                                  temp[k]=o;
                                  k++;
//						        	
						    }
    						temp2 =temp;                                                        //对数组进行swap操作
    						if(temp2.length!=0){
//    	    			if(segment[index]!=null){
    							if(temp2.length!=2){
    								int[] randoms = getRandomIndex (temp2.length);
    	    					
    								int a = temp2[randoms[0]];
    								temp2[randoms[0]] = temp2[randoms[1]];
    								temp2[randoms[1]] = a;
    	    					
    							}
    						}
    						for(int p = 0;p<temp2.length;p++){                                 //将数组转换回Arraylist<Integer>[][]
    							int b =0;
    							b = temp2[p];
    							Sequences2[i][j].add(b);	
    						}
    					}
    				}
    			}
    		}
		}
		return Sequences2;
    	
	}
	
    private  int[][] swap ( int[][] segment ,int[][] segment2)
    {

    	for(int i =1;i<segment.length;i++){
    	    segment2[i] =new int[segment[i].length];
    		for(int j=0;j<segment[i].length;j++){
    			segment2[i][j] =segment[i][j];
    		}
    	}
    	for (int index = 1; index < segment2.length; index++) {
    		if(segment2[index].length!=0){
//    		if(segment[index]!=null){
    				if(segment2[index].length!=2){
    					int[] randoms = getRandomIndex (segment2[index].length);
    		
    					int temp = segment2[index][randoms[0]];
    					segment2[index][randoms[0]] = segment2[index][randoms[1]];
    					segment2[index][randoms[1]] = temp;
    		
    				}
    		}		
    	}
    		
    	return segment2;
    }
    
	
    private ArrayList<Integer>[][] insert ( ArrayList<Integer>[][] Sequences ,ArrayList<Integer>[][] Sequences2)
    {

		Sequences2=new ArrayList[Sequences.length][];
		for(int i =0;i<Sequences.length;i++){
			Sequences2[i]=new ArrayList[Sequences[i].length];
    		for(int j=0;j<Sequences[i].length;j++){
    			if(Sequences[i][j]!=null){
    				Sequences2[i][j]=new ArrayList(Sequences[i][j].size());
    				if(Sequences[i][j].size()!=0){
    					if(Sequences[i][j].size()!=2){
    						
      						int[] temp2 =new int[Sequences[i][j].size()];
      						int[] temp =new int[Sequences[i][j].size()];                                                  //Arraylist<Integer>转换为数组

					        int k=0;
			        		for(int o :Sequences[i][j]){

                                  temp[k]=o;
                                  k++;
//						        	
						    }
    						temp2 =temp;                         
    						if(temp2.length!=0){
//        			if(chromosome[index]!=null){
    							if(temp2.length!=2){
    								int[] randoms = getRandomIndex (temp2.length);         //获取chromosome的优先级序列的长度
    								if (randoms[0] < randoms[1]){
            	
            		
    									int a=temp2[randoms[0]];
    									for ( int p = randoms[0]; p < randoms[1]; p++ ){
                	
    										temp2[p] = temp2[p + 1];
    									}
    									temp2[randoms[1]] = a;
            		
            	
    								}	
    								else
    								{
            	
     									int b=temp2[randoms[1]];
    									for ( int p = randoms[1]; p< randoms[0];p++ )
    									{
    										temp2[p] = temp2[p +1];
    									}
    									temp2[randoms[0]] = b;
            	   
     								}
    							}
    						}
    						for(int m = 0;m<temp2.length;m++){                                 //将数组转换回Arraylist<Integer>[][]
    							int c =0;
    							c = temp2[m];
    							Sequences2[i][j].add(c);	
    						}
    					}
    				}
    			}
    		}
		}
        
        return Sequences2;
    }
	
  
	
	
	private int[][] insert ( int[][] chromosome ,int[][] chromosome2)
    {
		for(int i =1;i<chromosome.length;i++){
    		chromosome2[i] =new int[chromosome[i].length];
    		for(int j=0;j<chromosome[i].length;j++){
    			chromosome2[i][j] =chromosome[i][j];
    		}
    	}
    
    for ( int index = 1; index < chromosome2.length; index++ )
    {
    	   
    	if(chromosome2[index].length!=0){
//    	if(chromosome[index]!=null){
    		if(chromosome2[index].length!=2){
    			int[] randoms = getRandomIndex (chromosome2[index].length);         //获取chromosome的优先级序列的长度
    			if (randoms[0] < randoms[1]){
        	
        		
        			int temp=chromosome2[index][randoms[0]];
        			for ( int i = randoms[0]; i < randoms[1]; i++ ){
            	
       					chromosome2[index][i] = chromosome2[index][i + 1];
       				}
       				chromosome2[index][randoms[1]] = temp;
        		
        	
    			}	
    			else
    			{
        	
        	    
        	
        	    	int temp=chromosome2[index][randoms[1]];
        	    	for ( int i = randoms[1]; i < randoms[0]; i++ )
        	    	{
        	    		chromosome2[index][i] = chromosome2[index][i +1];
        	    	}
        	    	chromosome2[index][randoms[0]] = temp;
        	   
        	           
    			}
    		}
    	}
    }
        
        return chromosome2;
    }
    
    private  int[] getRandomIndex ( int k )
    {
        int[] randoms = new int[2];
        int a = (int) ( Math.random () * (k-1) )+1;
        int b = a;
        while (b == a)
        {
            b = (int) ( Math.random () *( k-1) )+1;
        }
        randoms[0] = a;
        randoms[1] = b;
        //System.out.println ("indexs :" + Arrays.toString (randoms));
        return randoms;
    }



	
	/**
	 * @Description randomly rearrange the order of the string source
	 * @param string
	 */
//	private String RandomPriors(String source) {
//		String results="";
////		String source2 =new String("");
////		for(int i=0; i<source.length(); i++){
////    		source2+=source.charAt(i);
////    	}
//		char[] cur =  source.toCharArray();
//
//		if(source.length()!=0){
//		for (int i = 1; i < cur.length; i++) {
//			//int pos=(int)(rand.nextDouble()*(source.length-i+1)+i)-1;  
//            int pos =  (int) (Math.random () * source.length());
//            char temp=cur[i];  
//            cur[i]=cur[pos];  
//            cur[pos]=temp;  
//		}
//		for(char t:cur){
//			results+=t;
//		}
//		}
//		return results;
//	}


	/**
	 * @Description randomly rearrange the order of the array source
	 * @param source the source array
	 * @return
	 */
	private int[] RandomPriors(int[] source) { //check source鏄�浼犻�锛岃繕鏄璞′紶閫�
		
		if(source!=null){
			if(source.length!=0){
				for (int i = 1; i <source.length; i++) {
					//int pos=(int)(rand.nextDouble()*(source.length-i+1)+i)-1;  
					int pos =  (int) (Math.random () * (source.length-1))+1;
					int temp=source[i];  
					source[i]=source[pos];  
					source[pos]=temp;
				}
			}
		}
		else{
			source= new int [0];
		}
		return source;
	}

	/**
	 * @Description Add the new chromosome to current population
	 * @param Population 
	 * @param chromosome
	 */
	private void AddToPopulation(List<Chromosome> Population, Chromosome chromosome) {
		// TODO Auto-generated method stub
		if (Population.size()==0){
			Population.add(chromosome);
			return ;
		}
		
		for(Chromosome be: Population){
			if(be.equals(Population.get(Population.size()-1))){
				Population.add(chromosome);
				return;
			}
		}
	}

//	public static void main ( String[] args ) throws CloneNotSupportedException{
//		 DABC test = new DABC ();
//		 MachineSet mSet = new MachineSet();
//		 CellSet cellSet = new CellSet();
//		 
//		
//		 
//		 test.schedule();
//		 System.out.println("population："+test.Population);
//		   
//	}

}




  

