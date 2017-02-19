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
public class EM_add {
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
	
	/**记忆池**/
	protected HeapMaxPriorityQueue<Chromosome> Memory;
	/**CurHeap**/
	protected HeapMaxPriorityQueue<Chromosome> CurHeap;
	/**Archive B**/
	protected HeapMaxPriorityQueue<Chromosome> ArchiveB; 
	
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
	/**factor for x(best) - x(i)**/
	protected final double MutateFactor1 = 0.5;
	/**factor for x(r1) - x(2^)**/
	protected final double MutateFactor2 = 0.5;

	protected final double LeadingFactor = 5;
	
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
	public EM_add(MachineSet mSet, JobSet jSet, CellSet cellSet,
			AbstractMetaScheduler scheduler, MetaIMeasurance measurance) {
		this(mSet, jSet, cellSet, scheduler, measurance,100, 48);
	}
	
	
	public EM_add(MachineSet mSet, JobSet jSet, CellSet cellSet,
			AbstractMetaScheduler scheduler, MetaIMeasurance measurance, int Maxcycle,
			int populationSize) {
		MUTATION_PROBABILITY = 0.5;
		this.mSet = mSet;
		this.jSet = jSet;
		this.cellSet = cellSet;
		this.measurance = measurance;
		this.evaluator = scheduler;
		this.Population = new ArrayList<Chromosome>();
		this.CurHeap    = new HeapMaxPriorityQueue<Chromosome>(populationSize/5);
		this.ArchiveB   = new HeapMaxPriorityQueue<Chromosome>(populationSize/5);
		this.Memory     = new HeapMaxPriorityQueue<Chromosome>(populationSize/5);
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
		init_population(caseNo);
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
			
			if(Memory.size()!=0){
				for(int i = 0;i<Population.size();i++){
					Chromosome origin = Population.get(i);
					Chromosome New = Mutation(origin,i);
			    	New.setFunction(evaluation(New));
			    	if(New.getFunction()<=origin.getFunction()){
						Population.set(i,New);
			    	}
				}
			}else{
				//mutation操作			
				for(int i = 0;i<Population.size();i++){
					Chromosome origin = Population.get(i);
					Chromosome New = mutationOperator(origin);
			    	New.setFunction(evaluation(New));
			    	if(New.getFunction()<=origin.getFunction()){
						Population.set(i,New);
			    	}
				}
				updateMemory();

			}
		updateBestChromosome();
//		System.out.println(bestFunction);
		}

		if(iter==(MaxCycle-1)){
//			System.out.println("该种群中最优秀的调度解：");
//			System.out.println(bestFunction);
		}
		    
		System.out.println(bestFunction);
	}
	
	/**
	 * @throws CloneNotSupportedException 
	 * @Description 根据这一代的迭代结果CurHeap，更新全局的memory
	 */
	private void updateMemory() throws CloneNotSupportedException {
		// Age gets older
		for(Chromosome cur:Memory){
			cur.setAge(cur.getAge()+1);
		}
		
		//Add new ones
		for(Chromosome cur:CurHeap){
			cur.setAge(0);
			InsertInCollect(cur, Memory);
//			Memory.insert(cur);
		}
		CurHeap = new HeapMaxPriorityQueue<Chromosome>(POPULATION_SIZE/5);
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


	/**初始化：
	* 初始解的GP产生接口，初始解通过rules产生，初始解通过random产生
    * 应该是单独建立3个部分，可以切换，从而形成3组初始解产生机制
	* ①GP产生；②rules产生；③random
	 * @throws IOException 
	* */
private void init_population(int caseIndex) throws CloneNotSupportedException, IOException {                                   
    int i;		
    int mSetSize = mSet.size();
	int vSetSize = cellSet.size();	              
	Population = new ArrayList<Chromosome>();
	Chromosome chromosome = new Chromosome(mSetSize,vSetSize);
	
		
	for(i=0;i<POPULATION_SIZE;i++) {
		if(i<16){
			RulePrioirsReader(mSetSize,vSetSize,Constants.GPSET_DIR+"/" +caseIndex+"/"+(i+1));
			
			for (int index = 1; index <mSetSize+1; index++) {                      
				chromosome.setMachineSegment(index, Constants.MachineToParts[index]);
			}
		                                                                      
			for (int SourceIndex = 1; SourceIndex <vSetSize+1; SourceIndex++) {
				int[] VehicleCellSquence = Constants.CellToNextCells[SourceIndex];
//				if(VehicleCellSquence.length !=0){
				chromosome.setVehicleSegment(SourceIndex,VehicleCellSquence);
//				}
				for (int TargetIndex = 0; TargetIndex <VehicleCellSquence.length; TargetIndex++){
					int TargetCell = VehicleCellSquence[TargetIndex];

				//Arraylist<Integer>转换为数组
//		      	  StringBuffer strBuffer = new StringBuffer();
					if(Constants.CellToParts[SourceIndex][TargetCell]!=null){
						if(Constants.CellToParts[SourceIndex][TargetCell].size()!=0){
							int[] temp = new int [Constants.CellToParts[SourceIndex][TargetCell].size()]; 
							int k=0;
							for(int o :Constants.CellToParts[SourceIndex][TargetCell] ){
//					    	    	strBuffer.append(o);
//					   		     	temp = new int[]{Integer.parseInt(strBuffer.toString())};
									temp[k]=o;
									k++;
//					 		       	strBuffer.delete(0, strBuffer.length());
							}
							chromosome.setPartSequence(SourceIndex,TargetCell,temp);
							chromosome.setMark(1);
						}		
					}			       			    			         
				}
			}
			
		}else if(i<32){
			RulePrioirsReader(mSetSize,vSetSize,Constants.RULESET_DIR+"/" +caseIndex+"/"+caseIndex+"/"+(i-15));
			
			for (int index = 1; index <mSetSize+1; index++) {                      
				chromosome.setMachineSegment(index, Constants.MachineToParts[index]);
			}
		                                                                      
			for (int SourceIndex = 1; SourceIndex <vSetSize+1; SourceIndex++) {
				int[] VehicleCellSquence = Constants.CellToNextCells[SourceIndex];
//				if(VehicleCellSquence.length !=0){
				chromosome.setVehicleSegment(SourceIndex,VehicleCellSquence);
//				}
				for (int TargetIndex = 0; TargetIndex <VehicleCellSquence.length; TargetIndex++){
					int TargetCell = VehicleCellSquence[TargetIndex];

																	//Arraylist<Integer>转换为数组
//		      	  StringBuffer strBuffer = new StringBuffer();
					if(Constants.CellToParts[SourceIndex][TargetCell]!=null){
						if(Constants.CellToParts[SourceIndex][TargetCell].size()!=0){
							int[] temp = new int [Constants.CellToParts[SourceIndex][TargetCell].size()]; 
							int k=0;
							for(int o :Constants.CellToParts[SourceIndex][TargetCell] ){
//					    	    	strBuffer.append(o);
//					   		     	temp = new int[]{Integer.parseInt(strBuffer.toString())};
									temp[k]=o;
									k++;
//					 		       	strBuffer.delete(0, strBuffer.length());
							}
							chromosome.setPartSequence(SourceIndex,TargetCell,temp); 
							chromosome.setMark(2);
						}		
					}			       			    			         
				}
			}
			
		}else{
			
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
			        		 chromosome.setMark(3);
			        	}		
			        }			       			    			         
				}
			}
			
		}
			
		double func_value = evaluation(chromosome);
//	double func_value = 100.00;
	chromosome.setFunction(func_value);
	
	AddToPopulation(Population,chromosome.clone());		
//	System.out.println("该种群中调度解：");
//	System.out.println("第"+(i+1)+"个调度解的函数值:"+func_value);
		}	
	}


private void RulePrioirsReader(int msize, int csize, String filename) {
	File file = new File(filename);
	BufferedReader reader = null;
	try {
		reader 		   			  = new BufferedReader(new FileReader(file));

		// 初始化单元信息&&机器对象
		

		
		
//		Constants.MachineToParts  = new int[msize+1][];
//		Constants.CellToNextCells = new int[csize+1][];
//		Constants.CellToParts	  = new ArrayList[csize+1][csize+1];
//		for(int i = 1; i < csize+1; i++){
//			for(int j = 1; j < csize+1; j++){
//				Constants.CellToParts[i][j] = new ArrayList<Integer>();
//			}
//		}
//		
		
		String line;
		String[] seq = null;
		
		/**读取机器信息**/
		for (int i = 1; i < msize+1; i++) {
			Constants.MachineToParts[i] = new int[Constants.MachineToParts[i].length];
			line = reader.readLine();
			int m = line.indexOf(":");
			String t = line.substring(m+1);
			seq  = t.split(",");
			for(int j = 0; j < Constants.MachineToParts[i].length;j++ ){
				if(j==0){
					Constants.MachineToParts[i][j] =0;
				}
				else{
					Constants.MachineToParts[i][j] = Integer.parseInt(seq[j]);
				}
			}
			
			
			
		}
		reader.readLine();// 空格行
		
		
		/**读取InterCellSequence信息**/
		for (int i = 1; i < csize+1; i++) {

			for(int j = 1; j < csize+1;j++ ){
				line = reader.readLine();
				if(i!=j){
					int m = line.indexOf(":");
					String t = line.substring(m+1);
					seq  = t.split(",");
					for(int k =0;k < Constants.CellToParts[i][j].size();k++){
						Constants.CellToParts[i][j].set(k, Integer.parseInt(seq[k]));
					}
				}
			}
		}
		reader.readLine();// 空格行
		
		/**读取单元to单元信息**/
		for (int i = 1; i < csize+1; i++) {
			Constants.CellToNextCells[i] = new int[Constants.CellToNextCells[i].length];
			line = reader.readLine();
			int m = line.indexOf(":");
			String t = line.substring(m+1);
			seq  = t.split(",");
			for(int j = 0 ; j <=seq.length ; j++){
				if(j==0){
					Constants.CellToNextCells[i][j] =0;
				}
				else{
					Constants.CellToNextCells[i][j] = Integer.parseInt(seq[j-1]);
				}
			}
		}
		

	
	} catch (IOException e) {
		e.printStackTrace();
	}

}

	
	/**
	 * @Description evalution process for EM
	 * @param trans_chromosome chromosome for trans part 
	 * @param m_chromosome chromosome for machine part
	 * @throws IOException 
	 */
	protected double evaluation(Chromosome chromosome) throws IOException {
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
//			evaluator.scheduleWithStrategy(chromosome);
			
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
				if(source.length!=1){
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
//					else{
//						double ran;
//						ran = Math.random();
//						if(ran<0.5){
//							int temp=source[0];  
//							source[0]=source[1];  
//							source[1]=temp;
//						}
//						else{
//							source[0]=source[0];  
//							source[1]=source[1];
//						}
//					}
				}
//				if(source.length==1){
//					
//				}
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
	 * @throws IOException 
	 */
	private void updateBestChromosome() throws CloneNotSupportedException, IOException {
		bestChromosome = bestSoFar(Population, bestChromosome);
	}
	

	
	/**
	 * @Description Find the best chromosome in population
	 * @param population
	 * @param bestChromosome
	 * @return bestChromosome
	 * @throws CloneNotSupportedException
	 * @throws IOException 
	 */
	private Chromosome bestSoFar(List<Chromosome> population, Chromosome bestChromosome)
			throws CloneNotSupportedException, IOException {
		Chromosome temp ;
		
		Chromosome currentBest = Population.get(0);
		double currentBestFunc = Population.get(0).getFunction();
		
		for(int i=1;i<POPULATION_SIZE;i++) {
			temp=Population.get(i);
			if(temp.getFunction() <= currentBestFunc){
				currentBest 	= temp;
				currentBestFunc = temp.getFunction();
			}
			/***添加到 每一代的Heap池中*/
			InsertInCollect(temp,CurHeap);
		
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
	 * @Description Mutation Process For The Whole Population
	 * @param origin
	 * @return
	 */
    private Chromosome Mutation(Chromosome origin, int index) {
    	Chromosome X_best = GetFromPool(); 
		Chromosome X_1    = Population.get( GetAnotherNumber(index));
//		Chromosome X_2    = GetFromPool();			// 无B集合的
		Chromosome X_2    = GetFromPoolandB();		// 有B集合的
		
		Double AgingFactor = AgingCalu(X_best);
		Double LeadingPowerFactor = LeadingPowerCalu(origin,X_best);		
//		System.out.println("("+AgingFactor+","+LeadingPowerFactor+")");

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
    						X_best.getMachineSegment()[i],
    						X_1.getMachineSegment()[i],
    						X_2.getMachineSegment()[i],
    						AgingFactor,
    						LeadingPowerFactor
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
    						X_best.getVehicleSegment()[i],
    						X_1.getVehicleSegment()[i],
    						X_2.getVehicleSegment()[i],
    						AgingFactor,
    						LeadingPowerFactor
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
    								ConvertToIntArray(X_best.getPartSequence()[i][j]),
    								ConvertToIntArray(X_1.getPartSequence()[i][j]),
    								ConvertToIntArray(X_2.getPartSequence()[i][j]),
    	    						AgingFactor,
    	    						LeadingPowerFactor
    						)
    				);
    			}
    		}
    	}  
    	
    	//return the changed one
		return New;
	}
    
	/**
     * @param MemoryBest 
	 * @param X_2 
	 * @param leadingPowerFactor 
	 * @param agingFactor 
	 * @param js 
	 * @Description 具体的变异操作
     * 通过将优先级转化成可以比较的数值 -- 初定每个数值之间差值为1
     * @param x1
     * @param x2
     * @return
     */
    private int[] MutateOperate(int [] X, int[] XBest, int[] X_1, int[] X_2, Double agingFactor, Double leadingPowerFactor){
    	Map<Integer, Double> Result        = new HashMap<Integer, Double>();

    	//优先级数组转化成可以比较数值大小的数组
    	Map<Integer, Integer> Priors_X     = new HashMap<Integer, Integer>();
    	Map<Integer, Integer> Priors_Xbest = new HashMap<Integer, Integer>();
    	Map<Integer, Integer> Priors_X1    = new HashMap<Integer, Integer>();
    	Map<Integer, Integer> Priors_X2    = new HashMap<Integer, Integer>();
    	
    	int count = 1;
    	double Factor = agingFactor * leadingPowerFactor;
//    	double Factor = 0.5;
    	for(int i = X.length - 1; i >=1; i--){	//第0位是0，无用数据，不用存储
    		Priors_X.put(X[i], count);
    		Priors_Xbest.put(XBest[i], count);
    		Priors_X1.put(X_1[i], count);
    		Priors_X2.put(X_2[i], count);
    		count++;
    	}
    	
    	/** The operators begin **/
    	if(XBest.length != 0){	//have the memory informations
	    	for(int i = 1; i < X.length; i++){
	    		Result.put(X[i],
	    			Priors_X.get(X[i])+
	    			Factor* ( Priors_Xbest.get(X[i]) - 	Priors_X.get(X[i])) 
	    			+Factor*( Priors_X1.get(X[i]) 	- 	Priors_X2.get(X[i]))
	    		);
	    	}
    	}
    	else {					// did not have the memory history
	    	for(int i = 1; i < X.length; i++){
	    		Result.put(X[i],
	    			Priors_X.get(X[i])*1.0
	    			+MutateFactor2* ( Priors_X1.get(X[i]) 	- 	Priors_X2.get(X[i]))
	    		);
	    	}
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
     * @Description Power Evaluate Mechanism
	 *   控制参数范围在 1 - 0.417  (后期可调整)
	 *   且一般来说，会落在 (可通过实验来验证下)
     * @param origin
     * @param xBest
     * @return
     */
    private Double LeadingPowerCalu(Chromosome origin, Chromosome xBest) {
//    	System.out.println(origin.getFunction()+":"+xBest.getFunction());    	
		double GAP = (origin.getFunction() - xBest.getFunction())/origin.getFunction();
//		System.out.println("GAP"+ GAP);
//		System.out.println(Math.exp(GAP));
		return LeadingFactor*(Math.exp(GAP) - 1);
	}

	/**
     * @Description Aging deteriorate Mechanism
     *   一般来说 aging年限会以100作为标尺
	 *   控 制参数范围在 0.1 - 1
     * @param xBest : global best choromos
     * @return
     */
    private Double AgingCalu(Chromosome xBest) {
    	if(xBest.getAge() == 0)
    		return 1.0;
    	else
    		return Math.exp( -1.0*
						(Math.log(xBest.getAge())/Math.log(100))
		 	   );
	}

	/**
     * @Description get an random one from the Pool
     * @return
     */
    private Chromosome GetFromPool() {
    	//Method 1--随机选取
    	int index = (int) (Math.random()*Memory.size());
    	//Method 2--改成轮盘赌的形式
    	
    	return Memory.getIndex(index);
	}
 
    /**
     * @Description get an random one from the Set of Pool and ArchiveB 
     * @return
     */
    private Chromosome GetFromPoolandB() {
    	int index = (int) (Math.random()* (Memory.size()+ArchiveB.size()) );
    	if(index <Memory.size()){
    		return Memory.getIndex(index);
    	}
    	else{
    		return ArchiveB.getIndex(index-Memory.size());
    	}
	}
	
}
