package com.lm.algorithms.abc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.lm.algorithms.AbstractMetaScheduler;
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

/**
 * @author lm
 *
 * EM-like 方法的比对
 */
public class EM {
/*******************************************属性域*******************************************************/
	/**针对具体问题的调度过程**/
	protected AbstractMetaScheduler evaluator;
	/**适应度评估方法**/
	protected MetaIMeasurance measurance ;
	/**用于随机的种子**/
	protected Random rand = new Random(System.currentTimeMillis());
	/**最佳的适应度函数的value**/
	protected double bestFunction = 0d;
	/**最差的适应度函数的value**/
	protected double worstFunction = 0d;
	
	/**每一代的种群**/
	protected List<Chromosome> Population;
    /**最佳染色体序列**/
	protected Chromosome bestChromosome;
	/**最差染色体序列**/
	protected Chromosome worstChromosome;
	/**同个个体允许出现的次数**/
	private int chromos_num_upper_bound = 48;
	
	//算法参数
	protected final int POPULATION_SIZE=48;
	/**the maxmum of iteration. default=100**/
	protected final int MaxCycle=100;
	/**mutation's factor **/
	protected final double MutateFactor = 0.5;
	/**the probablity of mutation. dafault=0.18**/
	protected double MUTATION_PROBABILITY;
	/**Electric's factor **/
	protected final double EMFactor = 0.5;
	
	//the input data for inter-cell problems
	/**the machine's set **/
	protected MachineSet mSet;
	/**the job's set **/
	protected JobSet jSet;
	/**the cell's set **/
	protected CellSet cellSet;
/*****************************************************************************************************/

/****************************方法域*********************************************************************/

	/** 
	 * @Description:construction of EM:默认参数
	 * @param mSetp
	 * @param jSet
	 * @param cellSet
	 * @param scheduler
	 * @param measurance
	 */
	public EM(MachineSet mSet, JobSet jSet, CellSet cellSet,
			AbstractMetaScheduler scheduler, MetaIMeasurance measurance) {
		MUTATION_PROBABILITY = 0.5;
		this.mSet = mSet;
		this.jSet = jSet;
		this.cellSet = cellSet;
		this.measurance = measurance;
		this.evaluator = scheduler;
		this.Population = new ArrayList<Chromosome>();
	}
	
	/**获取bestfunctionvalue**/
	public  double getBestFunctionValue() {
	    return bestFunction; 
	}
	
	/**获取worstfunctionvalue**/
    public double getWorstFunctionValue(){
		return worstFunction;
	}
    
	/** MAIN PROCESS for EM
	 * @throws CloneNotSupportedException 
	 * @Description framework for DABC
	 * 
	 */
	public void schedule(int caseNo) throws CloneNotSupportedException, IOException{
		int iter=0;
		//初始化
		init_population();
		updateBestChromosome();
	
		//迭代进化
		for (iter=0;iter<MaxCycle;iter++){
			
			//mutation操作			
			for(int i = 0;i<Population.size();i++){
				Chromosome origin = Population.get(i);
				Chromosome New = mutationOperator(origin);
		    	New.setFunction(evaluation(New));
		    	if(New.getFunction()<=origin.getFunction()){
					Population.set(i,New);
		    	}
			}
			
			//计算电量公式，并改变对应个体
			for(int i = 0;i<Population.size();i++){
				Chromosome origin = Population.get(i);
				Chromosome New = EMCalculate(origin,i,bestChromosome);
		    	New.setFunction(evaluation(New));
		    	if(New.getFunction()<=origin.getFunction()){
					Population.set(i,New);
		    	}
			}
			updateBestChromosome();
//			System.out.println(bestFunction);
		}
		updateBestChromosome();
		if(iter==(MaxCycle-1)){
//			System.out.println("该种群中最优秀的调度解：");
//			System.out.println(bestFunction);
		}
		    
		System.out.println(bestFunction);
	}
	
	/**
	 * 利用公式计算,并修改值
	 */
	private Chromosome EMCalculate(Chromosome origin, int index,Chromosome X_best) {
		
		Chromosome X_k    =  GetXkfromPopulation();
		
		Chromosome X_t    =  GetXtfromPopulation(X_k);
		
		//初始化新的个体对象
		int msize = origin.getMachineSize();
    	int vsize = origin.getVehicleSize();
		int[] tmp = new int[0];
    	Chromosome New 	  = new Chromosome(msize,vsize);
		// 针对机器段
		New.setMachineSegment(0,tmp);
		New.setMachineSegment(0,tmp);
    	for(int i = 1; i <= msize; i++){
    		New.setMachineSegment(
    				i, 
    				EMOperate(
    						origin.getMachineSegment()[i],origin,
    						X_k.getMachineSegment()[i],X_k,
    						X_t.getMachineSegment()[i],X_t,
    						X_best.getMachineSegment()[i],X_best
    				)
    		);		
    	}
    	New.setVehicleSegment(0, tmp);
    	for(int i = 1; i <= vsize; i++){
    		New.setVehicleSegment(
    				i, 
    				EMOperate(
    						origin.getVehicleSegment()[i],origin,
    						X_k.getVehicleSegment()[i],X_k,
    						X_t.getVehicleSegment()[i],X_t,
    						X_best.getVehicleSegment()[i],X_best
    				)
    		);		
    	}
    	//针对单元间的工件
    	for(int i = 1; i <= vsize; i++){
    		for(int j = 1; j <= vsize; j++){
    			if(i!=j){
    				New.setPartSequence(i, j, 
    						EMOperate(
    								ConvertToIntArray(origin.getPartSequence()[i][j]),origin,
    								ConvertToIntArray(X_k.getPartSequence()[i][j]),X_k,
    								ConvertToIntArray(X_t.getPartSequence()[i][j]),X_t,
    								ConvertToIntArray(X_best.getPartSequence()[i][j]),X_best
    						)
    				);
    			}
    		}
    	}  
    	
		return New;
	}

	private Chromosome GetXkfromPopulation() {
		// TODO Auto-generated method stub
		int index = (int) (Math.random()*Population.size());
    	return Population.get(index);
	}
	
	private Chromosome GetXtfromPopulation(Chromosome X_k) {
		// TODO Auto-generated method stub
		int index = (int) (Math.random()*Population.size());
		int k = Population.indexOf(X_k);
		if(index == k){
			index = (int) (Math.random()*Population.size());
		}
    	return Population.get(index);
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
	 * 具体的计算公式的操作
	 * @param origin
	 * @param X_k
	 * @param X_t
	 * @return
	 */
	private int[] EMOperate(int[] origin,Chromosome Origin, int[] X_k,Chromosome Xk, int[] X_t,Chromosome Xt, int[] X_best ,Chromosome Xbest) {
		
		/**具体公式的处理过程，可参照Multipan的MutateOperate**/
		Map<Integer, Double> Result        = new HashMap<Integer, Double>();
		
		//优先级数组转化成可以比较数值大小的数组
    	Map<Integer, Integer> Priors_Xk = new HashMap<Integer, Integer>();
    	Map<Integer, Integer> Priors_Xt = new HashMap<Integer, Integer>();
    	
    	int count = 1;
    	for(int i = origin.length - 1; i >=1; i--){	//第0位是0，无用数据，不用存储
    		Priors_Xk.put(X_k[i], count);
    		Priors_Xt.put(X_t[i], count);
    		count++;
    	}
    	
    	//计算qk、qt电量
    	double sum1 = 0;
    	double sum2 = 0;
    	for(int k=0;k<Population.size();k++){
    		sum1 = sum1+(Population.get(k).getFunction()-Xbest.getFunction());
    	}
    	double qk = Math.exp((-jSet.size())*(Xk.getFunction()-Xbest.getFunction())/sum1);
   
    	for(int k=0;k<Population.size();k++){
    		sum2 = sum2+(Population.get(k).getFunction()-Xbest.getFunction());
    	}
    	double qt = Math.exp((-jSet.size())*(Xt.getFunction()-Xbest.getFunction())/sum2);
    	
    	
    	//引力、斥力公式
    	double Fk = 0;
    	double g1 = 1;
    	double g2 = 1;

    	if(Xt.getFunction() <= Xk.getFunction()){
    		for(int i = 1;i < origin.length; i++){
    			
    			g1 = g1+Math.abs(Priors_Xk.get(origin[i])-Priors_Xt.get(origin[i]));
    			
    		}
    		if(g1<0){
    			g2 = 0-g1;
    		}else{
    			g2=g1;
    		}
    		Fk = g1*(qk*qt)/(g2*g2);
    	}else{
    		for(int i = 1;i < origin.length; i++){
    			g1 = g1+Math.abs(Priors_Xk.get(origin[i])-Priors_Xt.get(origin[i]));
    		}
    		if(g1<0){
    			g2 = 0-g1;
    		}else{
    			g2 = g1;
    		}
    		Fk = g1*(qk*qt)/(g2*g2);
    	}
    	
    	//force vector
    	double v=Math.random()*1;
    	double Fk_abs =0;
    	if(Fk<0){
    		 Fk_abs=(-Fk);
    	}{
    		 Fk_abs=Fk;
    	}
    	for(int i = 1;i < origin.length; i++){
    		Result.put(
    				i, 
    				Priors_Xk.get(origin[i])+v*Fk/(Fk_abs));
    	}
    	
    	
    	
    	//Make It Feasible---dispatch the job according the values
    	Map<Integer, Integer> Sort = MapUtil.ReturnValueSequences(Result);      	//sort
    	if(origin[0]==0){	//if head with 0
        	int [] New = new int[Result.size()+1];
	    	New[0] = 0;
	    	for(int i = 0 ;i < Result.size(); i++){
//	    		New[i+1] = Sort.get(i);
	    		New[i+1] = origin[Sort.get(i)];
	    	}
	    	return New;
	    }else{			//if head without 0,means it is Sequence
	    	int [] New = new int[Result.size()];
	    	for(int i = 0 ;i < Result.size(); i++){
	    		New[i] = origin[Sort.get(i)];
	    	}
	    	
	    	return New;
	    }

	}

	/**
	 * @Description Mutation Process For The Whole Population
	 * @param origin
	 * @return
	 */
    private Chromosome mutationOperator(Chromosome origin) {
    	 
    	rand = new Random(System.currentTimeMillis());
		/*MachineSegment、VehicleSegment、PartSequence3个部分来写*/
    	
    	int mSetSize = mSet.size();
		int vSetSize = cellSet.size();	              
    	
			for (int index = 0; index <mSetSize+1; index++) {  
				if (rand.nextDouble() <= MUTATION_PROBABILITY) {// 满足概率要求则运行变异算子
					origin.setMachineSegment(index,
							swap_mutation(origin.getMachintSeq(index)));
				}
			}
			                                                                      
			for (int SourceIndex = 0; SourceIndex <vSetSize+1; SourceIndex++) {
				int[] VehicleCellSquence = swap_mutation(Constants.CellToNextCells[SourceIndex]);
				if(VehicleCellSquence.length !=0){
				origin.setVehicleSegment(SourceIndex,
						swap_mutation(origin.getVehicleSeq(SourceIndex)));
				}
				for (int TargetIndex = 0; TargetIndex <VehicleCellSquence.length; TargetIndex++){
					int TargetCell = VehicleCellSquence[TargetIndex];

			                                                   //Arraylist<Integer>转换为数组
//			        StringBuffer strBuffer = new StringBuffer();
			        if(Constants.CellToParts[SourceIndex][TargetCell]!=null){
			        	if(Constants.CellToParts[SourceIndex][TargetCell].size()!=0){
			        		 int[] temp = new int [Constants.CellToParts[SourceIndex][TargetCell].size()]; 
			        		 int k=0;
			        		 for(int o :origin.getPartSeq(SourceIndex, TargetCell) ){
//						        	strBuffer.append(o);
//						        	temp = new int[]{Integer.parseInt(strBuffer.toString())};
                                    temp[k]=o;
                                    k++;
//						        	strBuffer.delete(0, strBuffer.length());
						     }
			        		 origin.setPartSequence(SourceIndex,TargetCell,swap_mutation(temp));  
			        	}		
			        }			       			    			         
				}
			}
		return origin;
	}


	/**初始化种群
	 * @throws CloneNotSupportedException
	 */
	private void init_population() throws CloneNotSupportedException {                        
        //随机产生
		int i;		
        int mSetSize = mSet.size();
		int vSetSize = cellSet.size();	              
		Population = new ArrayList<Chromosome>();
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
					
			AddToPopulation(Population,chromosome.clone());		
//			System.out.println("该种群中调度解：");
//
//			System.out.println("第"+(i+1)+"个调度解的函数值:"+func_value);
//			


			}	
		}
	
	/**
	 * @Description evalution process for EM
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
		    
//			evaluator.schedule();
			evaluator.scheduleWithStrategy(chromosome);
			
//			long end   = System.currentTimeMillis();
//			System.out.println("本次时间:"+(end-start)+"ms");
	
			
			return measurance.getMeasurance(evaluator);
	}
	
	/**
	 * @Description Add the new chromosome to current population
	 * @param Population 
	 * @param chromosome
	 */
	private void AddToPopulation(List<Chromosome> Population, Chromosome chromosome) {
		if (Population.size()==0){
			Population.add(chromosome);
			return ;
		}
		
		Double func_value = new Double(chromosome.getFunction());
		int count=0;
		for(Chromosome be: Population){
			if (func_value.equals(be.getFunction())){
				if(count==chromos_num_upper_bound ) break;
				else count++;
			}
		}
		if(count<chromos_num_upper_bound) Population.add(chromosome);
		
		return;
	}
	
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
	
private int[] swap_mutation(int[] source) { 
		
		if(source!=null){
			if(source.length!=0){
				if(source.length!=2){
					int i = (int) (Math.random () * (source.length-1))+1;	
					int pos =  (int) (Math.random () * (source.length-1))+1;
					while(i==pos){
						pos =  (int) (Math.random () * (source.length-1))+1;
					}
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
	 * @Description update the value of bestmChromosome&&bestTransChromosome&&bestInterCellSequence
	 * @throws CloneNotSupportedException
	 */
	private void updateBestChromosome() throws CloneNotSupportedException {
		bestChromosome = bestSoFar(Population, bestChromosome);
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
		
		for(int i=1;i<population.size();i++) {
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
//		System.out.println("该种群中最优秀的调度解：");
//		System.out.println("最优解的函数值:"+bestFunction);
		return bestChromosome;
	}
	
}
