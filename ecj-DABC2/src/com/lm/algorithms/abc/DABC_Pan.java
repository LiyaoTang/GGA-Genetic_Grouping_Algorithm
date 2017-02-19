package com.lm.algorithms.abc;


import java.lang.Math;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.lm.algorithms.MetaHeuristicScheduler;
import com.lm.algorithms.abc.Chromosome;
import com.lm.algorithms.measure.Makespan;
import com.lm.algorithms.measure.MetaIMeasurance;
import com.lm.Metadomain.CellSet;
import com.lm.Metadomain.JobSet;
import com.lm.Metadomain.MachineSet;
import com.lm.statistic.RuleFrequencyStatistic;
import com.lm.util.Constants;
import com.lm.util.Timer;

import java.lang.StringBuffer;
import java.util.Arrays;

/*
 * @Description:DABC
 * 本版本专门用于进行：（random初始解+不同EmployedBee）策略的对比
 *              ①潘老师self-adaptive
 *              ②VNS
 *             
 * 
 */

public class DABC_Pan {                                   //对比实验：random+不同的EmployedBee


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
	
	
	
	//种群相关数据
	/**染色体**/
	protected List<Chromosome> Population;
    /**最佳染色体序列**/
	protected Chromosome bestChromosome;
	/**最差染色体序列**/
	protected Chromosome worstChromosome;
	//算法参数
	/**the population's size. default=48**/
	protected int POPULATION_SIZE=48;                       //修改量POPULATION_SIZE
	/**the maxmum of iteration. default=100**/
	protected final int MaxCycle=100;                              //修改量MaxCycle
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
	public DABC_Pan(MachineSet mSet, JobSet jSet, CellSet cellSet,
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
	public DABC_Pan(MachineSet mSet, JobSet jSet, CellSet cellSet,
			MetaHeuristicScheduler scheduler, MetaIMeasurance measurance, int Maxcycle,
			int populationSize) {
		this.mSet = mSet;
		this.jSet = jSet;
		this.cellSet = cellSet;
		this.measurance = measurance;
		this.POPULATION_SIZE = populationSize;
		this.evaluator = scheduler;
		Population = new ArrayList<Chromosome>();
		
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
	 * @Description framework for DABC
	 * 
	 */
	public void schedule() throws CloneNotSupportedException {
		
		int iter=0;
		init_population();
		updateBestChromosome();                       //保存种群中最好的那个调度解
		
		int[] NL =new int[200]; 						//NLlist和NLlast初始化，1代表swap，2代表insert，3代表two-swap，4代表two-insert
		int[] NLL =new int[200];
		for(int i=0;i<200;i++){
			int num = ((int)(Math.random()*4)+1) ;
			NL[i] = num;
			NLL[i] = num;
		}
		
		ArrayList WNL = new ArrayList();               //WNLlist初始化
		
		
		for (iter=0;iter<MaxCycle;iter++){                               //迭代数
//			EmployedBees();
			EmployedBees(NL,NLL,WNL);
		    OnlookerBees();
		    updateBestChromosome();
		    ScoutBees();

			if(iter==MaxCycle-1){
//			    System.out.println("该种群中最优秀的调度解：");
				System.out.print("\t"+bestFunction);
			}
		
		}
	}

	
	/**
	 * @Description update the value of bestmChromosome&&bestTransChromosome&&bestInterCellSequence
	 * @throws CloneNotSupportedException
	 */
	private void updateBestChromosome() throws CloneNotSupportedException {
		bestChromosome = bestSoFar(Population, bestChromosome);
	}
		

	
	/**
	 * @Description evalution process for GA
	 * @param trans_chromosome chromosome for trans part 
	 * @param m_chromosome chromosome for machine part
	 */
	protected double evaluation(Chromosome chromosome) {
		    
//		    machine.setPriorSequence(chromosome.MachineSegment[machine.id]);
//		    c.setPriorSequence(chromosome.VehicleSegment[c.id]);
//		    c.setIntercellPartSequences(chromosome.IntercellPartSequences[c.id]);
	     	int mSetSize = mSet.size();
		    int vSetSize = cellSet.size();	
		    for(int i=0;i<mSetSize;i++){

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
		Chromosome chromosome1 ;
		Chromosome chromosome2 ;
		Chromosome currentBest = new Chromosome(mSet.size(), cellSet.size()) ;
//		Chromosome currentBest = Collections.min(population).clone();
		for(int i=0;i<POPULATION_SIZE-1;i++) {
			chromosome1=Population.get(i);
			chromosome2=Population.get(i+1);
			if(i==0){
				if(chromosome1.getFunction() <= chromosome2.getFunction()){
					currentBest=chromosome1;
				}
				else {
					currentBest=chromosome2;
//				Population.set(i+1,currentBest);
				}
			}
			else if(currentBest.getFunction() >= chromosome2.getFunction()){
					currentBest=chromosome2;
//				Population.set(i,currentBest);
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
	
	
	/**初始化：
		* 初始解的GP产生接口，初始解通过rules产生，初始解通过random产生
	    * 应该是单独建立3个部分，可以切换，从而形成3组初始解产生机制
		* ①GP产生；②rules产生；③random
		* */
	private void init_population() throws CloneNotSupportedException {                                   //单个解的初始
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
	

	void EmployedBees() throws CloneNotSupportedException
	{
		
		/**Employed Bee Phase：
	   * 对每个解用localsearch1得到neighbour，neighbour要与之前保证不同
	   * 用评估函数比较source和neighbour的优劣，取优秀者赋给neighbour
	   */

//		    LocalSearch3();
//		    LocalSearch1();
            VNSLs();
		  

		    
		    
	        /*end of employed bee phase*/
    }
	
	void EmployedBees(int[] nl,int[] nll,ArrayList<Integer> wnl) throws CloneNotSupportedException
	{
		
		/**Employed Bee Phase：
	   * 对每个解用localsearch1得到neighbour，neighbour要与之前保证不同
	   * 用评估函数比较source和neighbour的优劣，取优秀者赋给neighbour
	   */


		    PanSelfAdaptive(nl,nll,wnl);
		  

		    
		    
	        /*end of employed bee phase*/
    }

	void OnlookerBees() throws CloneNotSupportedException
	{
		

	  /**onlooker Bee Phase
      
	   * 对从employed bee phase中得出的解采用localsearch2()，得到neighbour2
	   * 比较对应neighbour和neighbour2，取优秀的赋给neighbour2
	   */
        LocalSearch2();

	  
	  
	   /*end of onlooker bee phase   */
	}

	
	void ScoutBees() throws CloneNotSupportedException
	{
		/**
		 *scout bee phase
		 *
		 *选出onlooker中赋完值的neighbour2中最差的设为scout
		 *对scout舍弃，利用random重新生成一个解加入neighbour2中
		 *将neighbour2[][]赋值给foodnumber[][]
		 */
		int mSetSize = mSet.size();
		int vSetSize = cellSet.size();
//		int mSetSize = 6;
//		int vSetSize = 3;



		
		worstChromosome=worstSoFar(Population,worstChromosome);         //在所有neighbor2的population中找出最差的解，记为worstneighbor2
		int m=0;
		for(int i=0;i<POPULATION_SIZE;i++){
			if(worstChromosome.getFunction()==Population.get(i).getFunction()){
				m=i;
			}
		}
		for(int i=0;i<POPULATION_SIZE;i++) {
			for (int index = 0; index <mSetSize+1; index++) {                      
				worstChromosome.setMachineSegment(index, RandomPriors(Constants.MachineToParts[index]).clone());
			}
			                                                                      
			for (int SourceIndex = 0; SourceIndex <vSetSize+1; SourceIndex++) {
				int[] VehicleCellSquence = RandomPriors(Constants.CellToNextCells[SourceIndex]);
//				if(VehicleCellSquence.length !=0){
				worstChromosome.setVehicleSegment(SourceIndex,VehicleCellSquence);
//				}
				
				
				
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
							worstChromosome.setPartSequence(SourceIndex,TargetCell,RandomPriors(temp));
						}
						else{
							Constants.CellToParts[SourceIndex][TargetCell]=null;
						}
					}
					     //System.out.println(SourceIndex+TargetCell);
				}
			}
		}	
		worstChromosome.setFunction(evaluation(worstChromosome));
		for(int i=1;i<worstChromosome.IntercellPartSequences.length;i++){
			worstChromosome.IntercellPartSequences[i][0]=null;
		}
		Population.set(m, worstChromosome);
//		System.out.println("替换worstChromosome而新加入的调度解:");
//		System.out.println(worstChromosome.getFunction());

	}



	




        /**
         * @throws CloneNotSupportedException 
         * @Description localsearch1 for ： 采用
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
	    	neighbor1.MachineSegment = swap1(chromosome.clone().getMachineSegment(),neighbor1.MachineSegment);
			neighbor1.VehicleSegment =  swap1(chromosome.clone().getVehicleSegment(),neighbor1.VehicleSegment);
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
     * @throws CloneNotSupportedException 
     * @Description localsearch1 for ： 采用
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
	private void LocalSearch3() throws CloneNotSupportedException {                    //两个位置都往后的交换

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
	    	neighbor1.MachineSegment = swap2(chromosome.clone().getMachineSegment(),neighbor1.MachineSegment);
			neighbor1.VehicleSegment =  swap2(chromosome.clone().getVehicleSegment(),neighbor1.VehicleSegment);
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

	 private Chromosome NSearch1(Chromosome cur) throws CloneNotSupportedException {


		 	Chromosome neighbor = new Chromosome(mSet.size(), cellSet.size()) ;

    	
		    neighbor.MachineSegment = swap1(cur.clone().getMachineSegment(),neighbor.MachineSegment);
			neighbor.VehicleSegment =  swap1(cur.clone().getVehicleSegment(),neighbor.VehicleSegment);
			neighbor.IntercellPartSequences = swap(cur.clone().getPartSequence(),neighbor.IntercellPartSequences);                   //swap的error是由于intersequence的类型引起的
			


			return neighbor;
	 }
	 
	 private Chromosome NSearch2(Chromosome cur) throws CloneNotSupportedException {


		 	Chromosome neighbor = new Chromosome(mSet.size(), cellSet.size()) ;

 	
		    neighbor.MachineSegment = insert(cur.clone().getMachineSegment(),neighbor.MachineSegment);
			neighbor.VehicleSegment =  insert(cur.clone().getVehicleSegment(),neighbor.VehicleSegment);
			neighbor.IntercellPartSequences = insert(cur.clone().getPartSequence(),neighbor.IntercellPartSequences);                   //swap的error是由于intersequence的类型引起的
			


			return neighbor;
	 }
	 

	
	
	
	private void PanSelfAdaptive(int[] nl,int[] nll,ArrayList<Integer> w) throws CloneNotSupportedException {
		

		 for(int i=0;i<POPULATION_SIZE;i++) {
			 AdaptiveLS(nl,nll,w, Population.get(i));
		 }
		
		
	}
	
	
	private void VNSLs() throws CloneNotSupportedException {
		 for(int i=0;i<POPULATION_SIZE;i++) {
			 Chromosome n1= new Chromosome(mSet.size(), cellSet.size()) ;
			 Chromosome n2= new Chromosome(mSet.size(), cellSet.size()) ;
			 n1=NSearch1(Population.get(i));
			 n2=NSearch2(Population.get(i));
			 
			 for(int n=0;n<25;n++){				//外层循环
				 for(int k=0;k<2;k++){			//内层循环
					 Chromosome n11= new Chromosome(mSet.size(), cellSet.size()) ;
					 Chromosome n22= new Chromosome(mSet.size(), cellSet.size()) ;
					 if(k==0){
					 	n11=NSearch1(n1);
					 	if(n11.getFunction()>n1.getFunction()){
					 		k=0;
					 	}else{
					 		
					 		n1=n11;
					 	}
					 }
					 if(k==1){
						 n22=NSearch1(n2);
						 if(n22.getFunction()>n2.getFunction()){
						 		k=0;
						 }else{
							 n2=n22;
						 }
					 }
					 
					
					 
				 }
			 }
			 
			 
		 }
	}


//	private String[][] swap (String[][] Sequences){
//		// 对Sequences 进行操作
//		for (int index = 0; index < Sequences.length; index++) {
//			for( int j=0;j<Sequences[index].length;j++){
//				if(Sequences[index][j]!=null){
//					if(Sequences[index][j].length()!=1){
//						char[] cur =  Sequences[index][j].toCharArray();
//						//找两个位置
//						int pos1=(int) (Math.random () * Sequences[index][j].length());
//						int pos2 = pos1;
//						while (pos2 == pos1)
//						{
//							pos2 = (int) ( Math.random () * Sequences[index][j].length() );
//						}
//				    
//						char temp = cur[pos2];  
//						cur[pos2] = cur[pos1];
//						cur[pos1] = temp;                          
//					
//						String Result ="";
//						for(char t:cur){
//							Result+=t;
//						}
//						Sequences[index][j]=Result;
//				
//					}
//				}
//			}
//		}
//		return Sequences;
//	}
	
	
	 private void AdaptiveLS(int[] nl,int[] nll,ArrayList<Integer> wnl,Chromosome cur) throws CloneNotSupportedException {
		 int check=0;
		 for(int i=0;i<200;i++){
			 if(nl[i]!=0){
				 check=1;
			 }
		 }
		 if(check==0){
			 refill(nl,nll,wnl);
		 }
		 
		 for(int i=0;i<200;i++){
			 if(nl[i]==0){
				 
			 }else{
				 if(nl[i]==1){
					 swap(nl[i],wnl,cur);
					 nl[i]=0;
				 }else if(nl[i]==2){
					 insert(nl[i],wnl,cur);
					 nl[i]=0;
				 }else if(nl[i]==3){
					 swap(nl[i],wnl,cur);
					 swap(nl[i],wnl,cur);
					 nl[i]=0;
				 }else if(nl[i]==4){
					 insert(nl[i],wnl,cur);
					 insert(nl[i],wnl,cur);
					 nl[i]=0;
				 }
				return; 
			 }
		 }
	 }
	
	 private void refill(int[] nl,int[] nll,ArrayList<Integer> wnl) throws CloneNotSupportedException {
		 if(wnl.size()==0){
			 for(int i=0;i<200;i++){
				 nl[i] = nll[i];
 			 }
		 }else{
			 for(int i=0;i<200;i++){
				 if(Math.random()<0.75){
					 nl[i] = wnl.get((int)Math.random()*wnl.size());
 
				 }else{
					 nl[i] = (int) ( Math.random () * 4 )+1;
				 }
			 }
		 }
		 
		 for(int i=0;i<200;i++){
			 nll[i]=nl[i];
		 }
	 }
	 
	 private void insert(int n,ArrayList<Integer> w,Chromosome cur) throws CloneNotSupportedException{                    //针对一个解的isnert
	    	Chromosome neighbor = new Chromosome(mSet.size(), cellSet.size()) ;
	    	neighbor.MachineSegment = insert(cur.clone().getMachineSegment(),neighbor.MachineSegment);
			neighbor.VehicleSegment = insert(cur.clone().getVehicleSegment(),neighbor.VehicleSegment);
			neighbor.IntercellPartSequences = insert(cur.clone().getPartSequence(), neighbor.IntercellPartSequences);            
			double tmp = evaluation(neighbor);
			
			if( tmp <= cur.getFunction()){
				cur.MachineSegment = neighbor.clone().getMachineSegment();
				cur.VehicleSegment = neighbor.clone().getVehicleSegment();
				cur.IntercellPartSequences = neighbor.clone().getPartSequence();
				cur.setFunction(tmp);
				cur.clearCount();
				w.add(n);
			}
			return ;
	    }
	    
	    private void swap(int n,ArrayList<Integer> w,Chromosome cur) throws CloneNotSupportedException{                     //针对一个解的swap
	    	Chromosome neighbor = new Chromosome(mSet.size(), cellSet.size()) ;
	    	neighbor.MachineSegment = swap1(cur.clone().getMachineSegment(),neighbor.MachineSegment);
			neighbor.VehicleSegment = swap1(cur.clone().getVehicleSegment(),neighbor.VehicleSegment);
			neighbor.IntercellPartSequences = swap(cur.clone().getPartSequence(), neighbor.IntercellPartSequences);
			double tmp = evaluation(neighbor);
			if( tmp <= cur.getFunction()){
				cur.MachineSegment = neighbor.clone().getMachineSegment();
				cur.VehicleSegment = neighbor.clone().getVehicleSegment();
				cur.IntercellPartSequences = neighbor.clone().getPartSequence();
				cur.setFunction(tmp);
				cur.clearCount();
				w.add(n);
			}
			return;
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
	

    private  int[][] swap1 ( int[][] segment ,int[][] segment2)             //两个位置都随机的swap
    {

    	for(int i =0;i<segment.length;i++){
    	    segment2[i] =new int[segment[i].length];
    		for(int j=0;j<segment[i].length;j++){
    			segment2[i][j] =segment[i][j];
    		}
    	}
    	for (int index = 0; index < segment2.length; index++) {
    		if(segment2[index].length!=0){
    		if(segment[index]!=null){
    				if(segment2[index].length!=2){
    					int[] randoms = getRandomIndex (segment2[index].length);
    		
    					int temp = segment2[index][randoms[0]];
    					segment2[index][randoms[0]] = segment2[index][randoms[1]];
    					segment2[index][randoms[1]] = temp;
    		
    				}
    		}
    		}
    	}
    		
    	return segment2;
    }
	
	   private  int[][] swap2 ( int[][] segment ,int[][] segment2)                    //一个位置随机固定向后冒泡交换
	    {

	    	for(int i =0;i<segment.length;i++){
	    	    segment2[i] =new int[segment[i].length];
	    		for(int j=0;j<segment[i].length;j++){
	    			segment2[i][j] =segment[i][j];
	    		}
	    	}
	    	for (int index = 0; index < segment2.length; index++) {
	    		if(segment2[index].length!=0){
	    		if(segment[index]!=null){
	    				if(segment2[index].length!=2&&segment2[index].length<5){
	    					int[] randoms = getRandomIndex (segment2[index].length);
	    		
	    					int temp = segment2[index][randoms[0]];
	    					segment2[index][randoms[0]] = segment2[index][randoms[1]];
	    					segment2[index][randoms[1]] = temp;
	    		
	    				}else if(segment2[index].length>5){
	    					int[] randoms = getRandomIndex (segment2[index].length-3);
	    					int temp1 = segment2[index][randoms[0]];
	    					int temp2 = segment2[index][randoms[0]+2];
	    					segment2[index][randoms[0]] = segment2[index][randoms[0]+1];
	    					segment2[index][randoms[0]+1] = temp1;
	    					segment2[index][randoms[0]+2] = segment2[index][randoms[0]+3];
	    					segment2[index][randoms[0]+3] = temp2;
	    				}
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
	
  
	
	
//	private int[][] insert ( int[][] chromosome ,int[][] chromosome2)
//    {
//
//	    	for(int i =0;i<chromosome.length;i++){
//	    		chromosome2[i] =new int[chromosome[i].length];
//	    		for(int j=0;j<chromosome[i].length;j++){
//	    			chromosome2[i][j] =chromosome[i][j];
//	    		}
//	    	}
//        
//        for ( int index = 0; index < chromosome2.length; index++ )
//        {
//        	   
//        	if(chromosome2[index].length!=0){
////        	if(chromosome[index]!=null){
//        		if(chromosome2[index].length!=2){
//        			int[] randoms = getRandomIndex (chromosome2[index].length);         //获取chromosome的优先级序列的长度
//        			if (randoms[0] < randoms[1]){
//            	
//            		
//            			int temp=chromosome2[index][randoms[0]];
//            			for ( int i = randoms[0]; i < randoms[1]; i++ ){
//                	
//           					chromosome2[index][i] = chromosome2[index][i + 1];
//           				}
//           				chromosome2[index][randoms[1]] = temp;
//            		
//            	
//        			}	
//        			else
//        			{
//            	
//            	    
//            	
//            	    	int temp=chromosome2[index][randoms[1]];
//            	    	for ( int i = randoms[1]; i < randoms[0]; i++ )
//            	    	{
//            	    		chromosome2[index][i] = chromosome2[index][i +1];
//            	    	}
//            	    	chromosome2[index][randoms[0]] = temp;
//            	   
//            	           
//        			}
//        		}
//        	}
//        }
//        
//        return chromosome2;
//    }
    private int[][] insert ( int[][] chromosome ,int[][] chromosome2)
    {

	    	for(int i =0;i<chromosome.length;i++){
	    		chromosome2[i] =new int[chromosome[i].length];
	    		for(int j=0;j<chromosome[i].length;j++){
	    			chromosome2[i][j] =chromosome[i][j];
	    		}
	    	}
        
        for ( int index = 0; index < chromosome2.length; index++ )
        {
        	   
        	if(chromosome2[index].length!=0){
//        	if(chromosome[index]!=null){
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
			if(chromosome.equals(be)) return;
			
			else if(be.equals(Population.get(Population.size()-1))){
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




  

