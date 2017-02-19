package com.fay.ABH;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.fay.domain.Cell;
import com.fay.domain.CellSet;
import com.fay.domain.Job;
import com.fay.domain.JobSet;
import com.fay.domain.Machine;
import com.fay.domain.MachineSet;
import com.fay.measure.Makespan;
import com.fay.measure.TotalWeightedTardiness;
import com.fay.scheduler.SimpleScheduler;
import com.fay.util.Constants;
import com.fay.scheduler.AbstractScheduler;
import com.fay.localsearch.LocalSearchMethods;
import com.fay.ABH.Chromosome;
import com.fay.measure.IMeasurance;
import com.fay.statics.RuleFrequencyStatistic;

public class AntSystem{

	        //锟姐法锟斤拷锟斤拷
	        public Double a1, b1, a2, b2, p, max, min, Q, r1, r2, r3;

	        /// 循锟斤拷锟斤拷锟街�
	        public int imax;

	        /// 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷沤獠伙拷锟斤拷循锟斤拷锟斤拷锟斤拷
	        public int smax;

	        int _smax;

	        /// 每台锟斤拷锟斤拷锟斤拷锟脚癸拷锟斤拷锟斤拷锟街碉拷锟斤拷锟斤拷锟斤拷诔锟绞硷拷锟�
	        int kmax;
	      
	        public int AntNum ;
	        public int BestNum;
	        public static int MachineNum;
	        public static int JobNum;
	        public static int CellNum;
	        
	        
	        MachineSet ms;
	        JobSet js;
	        CellSet cs;
	        
	        
	        
	        /// <summary>
	        /// makespan锟斤拷锟睫ｏ拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
	        /// </summary>
	        public int lowerLimit;


	        //锟姐法锟斤拷锟斤拷锟斤拷锟斤拷
	        public List<Ant> ants;

	        //锟斤拷息锟斤拷

	        public double[][] TimeWindowPheromone;
	        public double[][] AssignmentPheromone;
	        public double[][] AssignmentDBPheromone;
	        public double[][] SeqPheromone;
	        public double[][] SeqDBPheromone;
	        public double[][] BuffPheromone;
	        public double[][] BuffDBPheromone;


	        //锟斤拷锟斤拷锟斤拷锟�
	        Random rdm;

	        //Rule-size
	        int assRuleNum = 5;
	        int seqRuleNum = 12;
	        int timewindowRuleNum = 1;
	        int buffRuleNum = 7;
	        //DB.size
	        int dbSize = 1;
	        
	        /// 全锟斤拷锟斤拷锟脚斤拷
	        public Ant BestSolution;
	        
	        AbstractScheduler scheduler;
	    //****GA*****///
	       /******** 属性域*******/
	        
	    	static IMeasurance makespan = new Makespan();
	    	static IMeasurance TWT = new TotalWeightedTardiness();
	        
	        
	        private static final int MAX_AMOUNT_OF_EACH_CHROMOS = 8;
	        private static final int MIN_AMOUNT_OF_EACH_CHROMOS = 4;
	    	/**针对具体问题的GA's evalution process**/
	    	protected AbstractScheduler evaluator;
	    	/**适应度评估方法**/
	    	protected IMeasurance measurance;
	    	/**选择的领域搜索方法**/
	    	protected LocalSearchMethods LSMethods;
	    	/**用于随机的种子**/
	    	protected Random rand = new Random(System.currentTimeMillis());
	    	/**最佳适应度函数value对应的适应度**/
	    	protected double bestFitness = 0d;
	    	/**最佳的适应度函数的value**/
	    	protected double bestFunction = 0d;
	    	
	    	//种群数据相关变量
	    	/**机器的染色体**/
	    	protected List<Chromosome> mPopulation;
	    	
	    	/**小车的染色体**/
	    	protected List<Chromosome> transPopulation;
	    	
	    	/**工件**/
	    	protected List<Chromosome> jPopulation;
	    
	    	
	    	/**最佳机器染色体序列**/
	    	protected Chromosome bestmChromosome;
	    	/**最佳小车染色体序列**/
	    	protected Chromosome bestTransChromosome;
	    	/**最佳工件序列**/
	    	protected Chromosome bestjChromosome;
//	    	protected Chromosome bestjChromosome;

	    	//算法参数
	    	/**the population's size. dafault=48**/
	    	protected  int POPULATION_SIZE = 48;
	    	/**the probablity of crossover. dafault=0.6**/
	    	protected double CROSSOVER_PROBABILITY = 0.6;
	    	/**the probablity of mutation. dafault=0.18**/
	    	protected double MUTATION_PROBABILITY = 0.18;
	    	/**the threshold of generation numbers. dafault=100**/
	    	protected  int MAX_GENERATION = 10;
	    	/**the threshold of chromosome numbers that have no improvement. dafault=100**/
	    	protected  int MAX_NONIMPROVE;
//	    	/** (father + children)'s size/population size . dafault=2**/
//	    	protected final int MAX_POPULATION_RATE=2;
	    	/**the probablity of father population that being reserved to next population . default=0.2**/
	    	protected double RESERVEDPROBABLITY=0.3;
	    	
	    	/**实验记录参数**/
	    	double MAX_POPULATION;
	        double MIN_POPULATION;	
	    	
	    	//the input data for inter-cell problems
	    	/**the machine's set **/
	    	protected MachineSet mSet;
	    	/**the job's set **/
	    	protected JobSet jSet;
	    	/**the cell's set **/
	    	protected CellSet cellSet;
	    	
	    	/**ending flag of evaluation process**/
	    	protected boolean isImproved = false;

	    	/** 规则分配频率统计类
	    	 *  Frequency statistics of heuristic rules will be used
	    	*/
	    	private RuleFrequencyStatistic stat = new RuleFrequencyStatistic();
	        
	        
	    	
	/**********重置******  	
	    	public void reset() {
	            super.reset();
	            js.clear();
	            for (Job job :js) {
	                js.addJob(job);
	            }
	        }

	    	
	   **/   	
	        
	   public void HSGA(MachineSet mSet, JobSet jSet, CellSet cellSet,
	    			AbstractScheduler scheduler, IMeasurance measurance, double cross,
	    			double mutation, int maxGeneration, int populationSize) {
	    		CROSSOVER_PROBABILITY = cross;
	    		MUTATION_PROBABILITY = mutation;
	    		this.mSet = mSet;
	    		this.jSet = jSet;
	    		this.cellSet = cellSet;
	    		this.measurance = measurance;
	    		MAX_GENERATION = maxGeneration;
	    		POPULATION_SIZE = populationSize;
	    		MAX_NONIMPROVE = MAX_GENERATION;
//	    		RESERVEDPROBABLITY=0.3;
	    		this.evaluator = scheduler;
	    		mPopulation = new ArrayList<Chromosome>();
	    		transPopulation = new ArrayList<Chromosome>();
	    		jPopulation = new ArrayList<Chromosome>();
	    	}      
	        
	   /**
   	 * 
   	 *	SimpleScheduler scheduler = new SimpleScheduler(ms,js,cs);	
  			
  			scheduler.schedule();
        	ant.MakeSpan = new Makespan().getMeasurance(scheduler);
        	ant.TotalWaitTime = new TotalWeightedTardiness().getMeasurance(scheduler);
        	ant.Score = ant.TotalWaitTime;
       // 	ant.Score = ant.MakeSpan;
        	scheduler.reset();
   	 **/
   	

  public void Init()
 {

	            a1 = 1.0;
	            b1 = 2.0;
	            a2 = 1.0;
	            b2 = 1.0;
	            p = 0.2;
	            max = 5.0;
	            min = 0.1;
	            Q = 1.0;
	            r1 = 0.7;
	            r2 = 0.3;
	            lowerLimit = 10000;

	            imax = 200;
	            smax = 50;
	            kmax = 400; 
	            AntNum = 100;
	            BestNum = 5;
	            
	            
	   //         MachineNum;
		//        JobNum;
		//        CellNum;
	            SimpleScheduler scheduler = new SimpleScheduler(ms,js,cs);		
	            HSGA(ms, js, cs, scheduler, TWT, 0.6, 0.18, 10, 48);
	            ants = new ArrayList<Ant>(AntNum);

	            for (int i = 0; i < AntNum; i++)
	            {
	                ants.add(new Ant());
	                ants.get(i).AntNo = i;
	                
	                ants.get(i).AssignmentDB = new ArrayList<Integer>(JobNum);
	                ants.get(i).AssignmentRule = new ArrayList<Integer>(JobNum);
	           
	                for(int j = 0 ; j <  JobNum ; j ++){
	                	ants.get(i).AssignmentDB.add(1);
	                }	
	                for(int j = 0 ; j < JobNum ; j ++){
	                	ants.get(i).AssignmentRule.add(1);
	                }	                
	                ants.get(i).SeqRule = new ArrayList<Integer>(MachineNum);
	                ants.get(i).SeqDB = new ArrayList<Integer>(MachineNum);
	                for(int j = 0 ; j <  MachineNum ; j ++){
	                	ants.get(i).SeqDB.add(1);
	                }
	                for(int j = 0 ; j < MachineNum ; j ++){
	                	ants.get(i).SeqRule.add(1);
	                }
	                ants.get(i).BatchDB = new ArrayList<Integer>(CellNum);
	                ants.get(i).BatchRule = new ArrayList<Integer>(CellNum);
	                for(int j = 0 ; j <  CellNum ; j ++){
	                	ants.get(i).BatchDB.add(1);
	                }
	                for(int j = 0 ; j < CellNum ; j ++){
	                	ants.get(i).BatchRule.add(1);
	                }
	                //时锟戒窗
//	                ants.get(i).TimeWindowRule = new ArrayList<Integer>(CellNum);
//	                for(int j = 0 ; j < CellNum; j ++){
//	                	ants.get(i).TimeWindowRule.add(1);
//	                }
	                
	            }

	            //锟斤拷始锟斤拷锟斤拷锟斤拷锟斤拷锟�
	            rdm = new Random();
	            BestSolution = new Ant();
	            BestSolution.MakeSpan = Double.MAX_VALUE;
	            BestSolution.TotalWaitTime = Double.MAX_VALUE;
	            BestSolution.Score = Double.MAX_VALUE;
            

	            AssignmentPheromone = new double[JobNum][];
	            AssignmentDBPheromone = new double[JobNum][];
	            for (int i = 0; i < JobNum; i++)
	            {
	                AssignmentPheromone[i] = new double[assRuleNum];
	                for (int j = 0; j < assRuleNum; j++)
	                {
	                    AssignmentPheromone[i][j] = min;
	                }
	            }
	           //   AssignmentDB锟斤拷始锟斤拷
	            for (int i = 0; i < JobNum; i++)
	            {
	                AssignmentDBPheromone[i] = new double[dbSize];
	                for (int j = 0; j < dbSize; j++)
	                {
	                    AssignmentDBPheromone[i][j] = min;
	                }
	            }
	            
	            SeqPheromone = new double[MachineNum][];
	            SeqDBPheromone = new double[MachineNum][];
	            for (int i = 0; i < MachineNum; i++)
	            {
	                SeqPheromone[i] = new double[seqRuleNum];
	                for (int j = 0; j < seqRuleNum; j++)
	                {
	                    SeqPheromone[i][j] = min;
	                }
	            }
		        //   SeqDB锟斤拷始锟斤拷
	            for (int i = 0; i < MachineNum; i++)
	            {
	            	SeqDBPheromone[i] = new double[dbSize];
	                for (int j = 0; j < dbSize; j++)
	                {
	                	SeqDBPheromone[i][j] = min;
	                }
	            }
	            BuffPheromone = new double[CellNum][];
	            BuffDBPheromone = new double[CellNum][];
	            for (int i = 0; i < CellNum; i++)
	            {
	                BuffPheromone[i] = new double[buffRuleNum];
	                for (int j = 0; j < buffRuleNum; j++)
	                {
	                    BuffPheromone[i][j] = min;
	                }
	            }
		        //   BuffDB锟斤拷始锟斤拷
	            for (int i = 0; i < CellNum; i++)
	            {
	            	BuffDBPheromone[i] = new double[dbSize];
	                for (int j = 0; j < dbSize; j++)
	                {
	                	BuffDBPheromone[i][j] = min;
	                }
	            }
//	            TimeWindowPheromone = new double[CellNum][];
//	            for (int i = 0; i < CellNum; i++)
//	            {
//	            	TimeWindowPheromone[i] = new double[timewindowRuleNum];
//	                for (int j = 0; j < timewindowRuleNum; j++)
//	                {
//	                	TimeWindowPheromone[i][j] = min;
//	                }
//	            }

 }

  
  public void schedule(int caseNo) throws CloneNotSupportedException {
		
//		initialPopulation();
		init_popula_withcheck();
		
	//	System.out.println("out-init");
		int nonImporveCount = 0;	// 未改善代数计数器
		double func_value,fitness;	//记录每个染色体对应的函数值和适应度
		for (int currentGen = 0; currentGen < MAX_GENERATION; currentGen++) {//迭代进化过程

//			LocalSearch();
			
//			System.out.println("代 " + currentGen + "\t" + bestFunction+ "\t"+
//					Outputchromos(bestmChromosome)+"\t"+
//					Outputchromos(bestTransChromosome)
//			);
			for (int index = 0; index < POPULATION_SIZE; index++) {
				func_value = evaluation(mPopulation.get(index),transPopulation.get(index),jPopulation.get(index));
				fitness = 1.0 / (1 + func_value);
				
//				System.out.println("个体"+index+"的Function值："+func_value+"——————"
//						+"染色体片段"+mPopulation.get(index)+":"+transPopulation.get(index));

				mPopulation.get(index).setFitness(fitness).setFunction(func_value);
				transPopulation.get(index).setFitness(fitness).setFunction(func_value);
				jPopulation.get(index).setFitness(fitness).setFunction(func_value);
			
				
			//	System.out.println(index);
				
			//	System.out.println(mPopulation.get(index).getFunction());
			//	System.out.println(mPopulation.get(index).getFunction());
			//	System.out.println(mPopulation.get(index).getFunction());
				
			}

			updateBestChromosome();		//更新调度规则和函数值，并判断调度结果是否有效
		//	System.out.println("updatebest-out");
			if(isImproved){
				nonImporveCount = 0;
				isImproved = false;
			}else{
				nonImporveCount++;
			}
			
			if (nonImporveCount > MAX_NONIMPROVE) {
				break;
			}
		//	System.out.println("if-out");

			// 为了取得种群里面的结果设置的
			if(currentGen == MAX_GENERATION-2){
				break;  
			}
			//System.out.println("111111");
			//GA 进化操作
			selectOperator();
			//System.out.println("111111");
			crossOverOperator();
			//System.out.println("22222");
			mutationOperator();
			//System.out.println("33333");
			
//			VirusOperator();					//后期扩展来做，病毒变异
			Catastrophe(currentGen);			//choose to change the args or not?
		}
		//进化结束
	/*	System.out.println(bestFunction+ "\t"+
				Outputchromos(bestmChromosome)+"\t"+
				Outputchromos(bestTransChromosome)+"\t"+
				Outputchromos(bestjChromosome)
				
		);
		*/
		//打印出最后的群体
		//PrintThePop(caseNo);
	}
  
  public double Findbest()
  {
	  double ans =0;
		for ( int i =0 ; i < mPopulation.size(); i++) {
  			Chromosome cur = mPopulation.get(i);
  			if(max<cur.getFunction())
  			{
  				ans = cur.getFunction();
  			}
				//bw.write(i+":\t"+cur.getFunction()+"\t"+Outputchromos(cur)+":\t"+Outputchromos(transPopulation.get(i))+"\n");
  		//	System.out.println(i+":\t"+cur.getFunction()+"\t"+Outputchromos(cur)+":\t"+Outputchromos(transPopulation.get(i))+":\t"+Outputchromos(jPopulation.get(i))+"\n");
  		}
	  
		//System.out.println(max+"!!!!!");
	  return ans;
			  
  }
  
  private void PrintThePop(int caseNo) {
		/*BufferedWriter bw;
  	try {
  		//if the dir is exists
  		File file = new File(Constants.RULESQUENCE_DIR);
  		if (!file.exists()) {
  			   file.mkdir();
  		}
  		
  		//write the file
  		bw = new BufferedWriter(new FileWriter(
  				Constants.RULESQUENCE_DIR+ "/"+ caseNo
					));
		*/	
  		for ( int i =0 ; i < mPopulation.size(); i++) {
  			Chromosome cur = mPopulation.get(i);
				//bw.write(i+":\t"+cur.getFunction()+"\t"+Outputchromos(cur)+":\t"+Outputchromos(transPopulation.get(i))+"\n");
  			System.out.println(i+":\t"+cur.getFunction()+"\t"+Outputchromos(cur)+":\t"+Outputchromos(transPopulation.get(i))+":\t"+Outputchromos(jPopulation.get(i))+"\n");
  		}
//  		bw.write("最优解为："+bestFunction+"    ");
//  		bw.write("最差解为："+MAX_POPULATION+"    ");
//  		bw.write("最优解和最差解差值为："+(MAX_POPULATION-bestFunction));
//			bw.newLine();
  		
			//Close the BufferedWriter
		/*	if (bw != null) {
				bw.flush();
				bw.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
  private void init_popula_withcheck() throws CloneNotSupportedException{
		int mSetSize = mSet.size();
		Chromosome mchromosome = new Chromosome(mSetSize);
		int batchNum = cellSet.size();
		Chromosome bchromosome = new Chromosome(batchNum);
		int jobNum  = jSet.size();
		Chromosome jchromosome = new Chromosome(jobNum);
		
		while(mPopulation.size()<POPULATION_SIZE) {	//运输段的染色体添加与否是和机器段一起确定的
				for (int index = 0; index < mSetSize; index++) {
					mchromosome.set(index, rand.nextInt(Constants.mRules.length));
				}
				for (int index = 0; index < batchNum; index++) {
					bchromosome.set(index, rand.nextInt(Constants.bRules.length));
				}
				for (int index = 0; index < jobNum; index++) {
					jchromosome.set(index, rand.nextInt(Constants.jRules.length));
				}
				
				//System.out.println("**update-start**");
				updateMachineRules(mchromosome);
				updateTransRules(bchromosome);
				updateJobRules(jchromosome);
			//	System.out.println("**update-end**");
				
				
				double func_value = 0;
				mchromosome.setFunction(func_value);
				bchromosome.setFunction(func_value);
				jchromosome.setFunction(func_value);
				
//				System.out.println("个体"+mPopulation.size()+":"+bchromosome.getFunction());
				mchromosome.setFitness(1.0 / (1 + func_value));	
				bchromosome.setFitness(1.0 / (1 + func_value));	
				jchromosome.setFitness(1.0 / (1 + func_value));	
				
				SelectionAdd(mPopulation,mchromosome.clone(),MIN_AMOUNT_OF_EACH_CHROMOS);
				SelectionAdd(transPopulation,bchromosome.clone(),MIN_AMOUNT_OF_EACH_CHROMOS);
				SelectionAdd(jPopulation,jchromosome.clone(),MIN_AMOUNT_OF_EACH_CHROMOS);
				evaluator.reset();
		}
		
		/**找出初始解中最差值，并记录**/
		double currentWorstFunc = mPopulation.get(0).getFunction();
		Chromosome currentWorst;
		for(int i=0;i<POPULATION_SIZE;i++) {
			 Chromosome temp = mPopulation.get(i);
			 if(temp.getFunction() >= currentWorstFunc){
				currentWorst 	= temp;
				currentWorstFunc = temp.getFunction();
			}
		}
		MAX_POPULATION = currentWorstFunc;
//		System.out.println("初始化结束");
	}
  
  
  protected double evaluation(Chromosome m_chromosome, Chromosome trans_chromosome,Chromosome job_chromosome) {
	 double ans;
	  evaluator = new SimpleScheduler(ms,js,cs); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	  updateMachineRules(m_chromosome);
		updateTransRules(trans_chromosome);
		updateJobRules(job_chromosome);
		//evaluator.reset();
         evaluator.schedule();              ///catch!!!!!!!!!!!!
         ans = measurance.getMeasurance(evaluator);
     	
         
         evaluator.reset();
		/**确定fitness的过程很重要，比例太接近不利于决定进化方向**/
		return ans;
} 
 
  protected void updateMachineRules(Chromosome chromosome) {
		int index = 0;
		String MachineGene="";
		for (Machine m : mSet) {
			MachineGene+=chromosome.get(index)+",";
			m.setMachineRule(Constants.mRules[chromosome.get(index++)]);
		}
	//	 System.out.println(MachineGene);
	}
  
  protected void updateTransRules(Chromosome chromosome) {
		int index = 0;
		String CellGene="";
		for (Cell m : cellSet) {
			CellGene+=chromosome.get(index)+",";
			m.setBufferOutRule(Constants.bRules[chromosome.get(index++)]);
		}
		//System.out.println(CellGene);
	}

  
  protected void updateJobRules(Chromosome chromosome) {
		int index = 0;
		String JobGene="";
		for (Job j : jSet) {
			JobGene+=chromosome.get(index)+",";
			j.setJobRule(Constants.jRules[chromosome.get(index++)]);
		}
	//	System.out.println(JobGene);
	}

  
  
  private void SelectionAdd(List<Chromosome> Population, Chromosome chromos,int chromos_num_upper_bound) {
		// TODO Auto-generated method stub
		if (Population.size()==0){
			Population.add(chromos);
			return ;
		}
		
		Double func_value = new Double(chromos.getFunction());
		int count=0;
		for(Chromosome be: Population){
			if (func_value.equals(be.getFunction())){
				if(count==chromos_num_upper_bound) break;
				else count++;
			}
		}
		if(count<chromos_num_upper_bound) Population.add(chromos);
	} 
  
 
  private void updateBestChromosome() throws CloneNotSupportedException {
		bestmChromosome = bestSoFar(mPopulation, bestmChromosome);
		bestTransChromosome = bestSoFar(transPopulation, bestTransChromosome);
		bestjChromosome = bestSoFar(jPopulation, bestjChromosome);
	} 
  
  private Chromosome bestSoFar(List<Chromosome> population, Chromosome bestChromosome)
			throws CloneNotSupportedException {
		Chromosome currentBest = Collections.min(population).clone();	
		
		if (bestChromosome == null) {
			bestChromosome = currentBest;		
			bestFitness = bestChromosome.getFitness();
			bestFunction = bestChromosome.getFunction();	
		} else if (currentBest.compareTo(bestChromosome) < 0) {
				bestChromosome = currentBest;
				bestFitness = bestChromosome.getFitness();
				bestFunction = bestChromosome.getFunction();
				isImproved = true;
		}
		
		return bestChromosome;
	}

  
  /**************************选择操作************************************/ 
  
  private void selectOperator() throws CloneNotSupportedException {
		// 计算总适应度值
		double totalFitness = 0.0f;
		for (Chromosome chromosome : mPopulation) {
			totalFitness += chromosome.getFitness();
		}
		if (Double.compare(totalFitness, 0) == 0) {
			// 对于GA_Y，fitness=0表示找到最优可行解
			return;
		}
		updateChromosomeProbability(mPopulation, totalFitness);
		updateChromosomeProbability(transPopulation, totalFitness);
		updateChromosomeProbability(jPopulation, totalFitness);
	//	System.out.println("updateChromosomeProbability -out");
		select(mPopulation, totalFitness);
		//System.out.println("mselect -out");
		select(transPopulation, totalFitness);
		select(jPopulation, totalFitness);
	//	System.out.println("select -out");
	}
  private void updateChromosomeProbability(List<Chromosome> population,
			double totalFitness) {
		// 机器规则
		double sum = 0.0f;
		if (Double.compare(totalFitness, 0) == 0)
			return;
		Collections.sort(population);
		for (Chromosome chromosome : population) {
			sum += chromosome.getFitness();
			chromosome.setProb(sum / totalFitness);
		}
	}
  

  private void select(List<Chromosome> population, double totalFitness) throws CloneNotSupportedException {
		List<Chromosome> newPopulation = new ArrayList<Chromosome>();
		Random rand = new Random(System.currentTimeMillis());
		Collections.sort(population);
		
		newPopulation.add(population.get(0).clone());
		newPopulation.add(population.get(1).clone());
		//System.out.println("im in select");
		int reserved_size = (int)(POPULATION_SIZE * RESERVEDPROBABLITY);
		//System.out.println(reserved_size);
		int i=0;
		while (newPopulation.size() < reserved_size ) {
//			/**
			double prob = rand.nextDouble();
			double sum=0.0;
			for (int j = 0; j < population.size(); j++) {
				sum+=population.get(j).getProb();
				if (sum > prob) {
					try {
						//执行重复元素检测，newpopulation中不能超过5个重复元素
						int count=0;
						for(int k=0;k<newPopulation.size();k++){
							if(newPopulation.get(k).getFunction()==population.get(j).getFunction()){
								count++;
							}
						}
						if(count<5){
							newPopulation.add(population.get(j).clone());
						}else{
							continue;
						}
						
//						newPopulation.add(population.get(j).clone());
//						AddToPopulation(newPopulation,population.get(j).clone());
//						SelectionAdd(newPopulation,population.get(j).clone(),MAX_AMOUNT_OF_EACH_CHROMOS);
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
					break;
				}
			}
//			**/
//			newPopulation.add(population.get(i++).clone());
			//System.out.println(newPopulation.size());
		}
		//System.out.println("while of select   -out");
		population.clear();
		population.addAll(newPopulation);
	}
 
  
 /******************************交换操作************************/ 
  private void crossOverOperator() throws CloneNotSupportedException {
		//List是引用传递的
		cross(mPopulation);
		cross(transPopulation);
		cross(jPopulation);
	}
  
  
  private void cross(List<Chromosome> population) throws CloneNotSupportedException {

//		/** for two point cross
		for (int count = 0; count < POPULATION_SIZE - POPULATION_SIZE*RESERVEDPROBABLITY; count += 2) {
			Chromosome one, two;

				
				one = population.get(rand.nextInt((int) (POPULATION_SIZE*RESERVEDPROBABLITY))).clone();
				two = population.get(rand.nextInt((int) (POPULATION_SIZE*RESERVEDPROBABLITY))).clone();
				//Add 之前需要重新清空function值
				one.clearFunction();
				two.clearFunction();
				if (rand.nextDouble() <= CROSSOVER_PROBABILITY) {// 满足概率要求则运行交叉算子
					
					int pos1 = rand.nextInt(one.size());
					int pos2 = rand.nextInt(one.size());
					swapChromosome(one, two, pos1, pos2);
				}

				population.add(one);
				population.add(two);
		}
  }
  

  private void swapChromosome(Chromosome left, Chromosome right, int pos1,int pos2) {
		if (pos2 < pos1) {
			int temp = pos1;
			pos1 = pos2;
			pos2 = temp;
		}
		for (int index = pos1; index < pos2; index++) {
			int temp = left.get(index);
			left.set(index, right.get(index));
			right.set(index, temp);
		}
	}
  
  /******************************变异操作************************/ 
  
  private void mutationOperator() {
		rand = new Random(System.currentTimeMillis());
		for (Chromosome chromosome : mPopulation) {
			if (rand.nextDouble() <= MUTATION_PROBABILITY) {// 满足概率要求则运行变异算子
				chromosome.set(rand.nextInt(mSet.size()), rand
						.nextInt(Constants.mRules.length));
			}
		}
		for (Chromosome chromosome : transPopulation) {
			if (rand.nextDouble() <= MUTATION_PROBABILITY) {// 满足概率要求则运行变异算子
				chromosome.set(rand.nextInt(cellSet.size()), rand
						.nextInt(Constants.bRules.length));
			}
		}
		for (Chromosome chromosome : jPopulation) {
			if (rand.nextDouble() <= MUTATION_PROBABILITY) {// 满足概率要求则运行变异算子
				chromosome.set(rand.nextInt(jSet.size()), rand
						.nextInt(Constants.jRules.length));
			}
		}
		
		
		
	}
  
  
  /************************灾变**************************/
  private void Catastrophe(int current_generation) {
		// TODO Auto-generated method stub
		
		//灾变操作主要有两种：1.对整个种群进行灭绝，随机产生一些新的个体 2.大幅提高遗传算法的变异率
		//这一次采取第2种方式
		int K = MAX_GENERATION/5;
		if(current_generation<K){
			this.MUTATION_PROBABILITY=0.3;
			this.CROSSOVER_PROBABILITY=0.6;
		}else if(current_generation<3*K){
			this.MUTATION_PROBABILITY=0.5;
			this.CROSSOVER_PROBABILITY=0.7;
		}else{
			this.MUTATION_PROBABILITY=0.7;
			this.CROSSOVER_PROBABILITY=0.9;
		}
	}
  

	private String Outputchromos(Chromosome Chromos) {
		if(Chromos == null)
			return "null";
		return Chromos.toString();
	}
	
	
	
  
  
  public void MainLoop()
{
	            int c;
	            for (c = 0; c < imax; c++)
	            {
	               FindSolution();
	                //锟斤拷锟斤拷锟斤拷息锟斤拷
	              if (UpdatePheromone() == 1)
	                    break;
	              if (c % 100 == 0 && c != 0){
	              //    System.out.println("Loop"+c+"Done.");
	              //    System.out.println(BestSolution.TotalWaitTime);
	                 }
	            }
	          //   System.out.println("Loop"+c+"Done.");
	          //   System.out.println(BestSolution.TotalWaitTime);
}
 //BESt 
  public double getBest(){
	  return BestSolution.TotalWaitTime;
  }
  //best DB
  public void getBestDB(){
	 // return BestSolution.TotalWaitTime;
	 
	  BestSolution.printDB();
	 // System.out.print(";");
  }
  public void setProblemSize(MachineSet ms, JobSet js, CellSet cs)
  			{
	  		MachineNum = ms.size();
	  		JobNum = js.size();
	  		CellNum = cs.size();
	  		this.ms = ms;
	  		this.js = js;
	  		this.cs = cs;
  			}

   protected void FindSolution()
  {
	   		for(Ant ant : ants){
	   		
	   		//锟斤拷锟矫癸拷锟斤拷
	   		 for (Job job : js){
	   			 RandomSelector rs = new RandomSelector();
	   			 for (int i = 0; i < dbSize; i++)
                 {
                     rs.AddItem(AssignmentDBPheromone[job.getId()-1][i], i);
                 }
	   			 int ru = rs.GetResult()+1;
	   			//System.out.println("jrule锟斤拷锟教赌ｏ拷result"+ru);
	   			 ant.AssignmentDB.set(job.getId()-1, ru);
	   			//System.out.println("jobid-1="+(job.getId()-1));
	   		 }
	   		
	   			 for (Job job : js)
	                {
	   				 RandomSelector rs = new RandomSelector();

	                    for (int i = 0; i < assRuleNum; i++)
	                    {
	                        rs.AddItem(AssignmentPheromone[job.getId()-1][i], i);
	                    }

	                    int ru = rs.GetResult();
	                   // System.out.println("jrule锟斤拷锟教赌ｏ拷result"+ru);
	                 //   job.setJobRule(Constants.jRules[ru]);

	                    ant.AssignmentRule.set(job.getId()-1, ru);
	                }
	   			//JOBALL
	   			 int j=0;
	   			 for (int i=0 ;i< js.size() ; i++){
	   				 
	   				 for(int k=0; k< ant.AssignmentDB.get(i); k++){
	   					 //job.setJobRule(Constants.jRules[ru]);
	   					// js.get(j).setJobRule( ant.AssignmentRule.get(i));
	   					 js.get(j).setJobRule( Constants.jRules[ant.AssignmentRule.get(i)]);
	   					 j++;
	   					 if(j>=js.size() )
	   					 {
	   						i=js.size();
	   			        	break;		 
	   					 }
	   				 }
	   			 }
	   			
	   			 
	   		/**模锟斤拷assignment写 **/
	   	    //machine
	   		for (Machine machine : ms)
	   		{
	   			 RandomSelector rs = new RandomSelector();
	   			 for (int i = 0; i < dbSize; i++)
                 {
	   				rs.AddItem(SeqDBPheromone[machine.getId()-1][i], i);
                 }
	   			 int ru = rs.GetResult()+1;
	   			//System.out.println("jrule锟斤拷锟教赌ｏ拷result"+ru);
	   			 ant.SeqDB.set(machine.getId()-1, ru);
	   			//System.out.println("jobid-1="+(job.getId()-1));
	   		 }
	   		
	   		for (Machine machine : ms)
	                {
	   				 RandomSelector rs = new RandomSelector();

	                    for (int i = 0; i < seqRuleNum; i++)
	                    {
	                        rs.AddItem(SeqPheromone[machine.getId()-1][i], i);
	                    }

	                    int ru = rs.GetResult();
	                   // System.out.println("jrule锟斤拷锟教赌ｏ拷result"+ru);
	                 //   job.setJobRule(Constants.jRules[ru]);

	                    ant.SeqRule.set(machine.getId()-1, ru);
	                }
	   			//MACHINEALL
	   			 j=0;
	   			 for (int i=0 ;i< ms.size() ; i++){
	   				 
	   				 for(int k=0; k< ant.SeqDB.get(i); k++){
	   					 //job.setJobRule(Constants.jRules[ru]);
	   					// js.get(j).setJobRule( ant.AssignmentRule.get(i));
	   					  ms.get(j).setMachineRule( Constants.mRules[ant.SeqRule.get(i)]);
	   					 j++;
	   					 if(j>=ms.size() )
	   					 {
	   						i=ms.size();
	   			        	break;		 
	   					 }
	   				 }
	   			 }
	   		   
	   			//cell
	   			for (Cell cell : cs)
		   		{
		   			 RandomSelector rs = new RandomSelector();
		   			 for (int i = 0; i < dbSize; i++)
	                 {
		   				 rs.AddItem(BuffDBPheromone[cell.GetID()-1][i], i);
	                 }
		   			 int ru = rs.GetResult()+1;
		   			//System.out.println("jrule锟斤拷锟教赌ｏ拷result"+ru);
		   			 ant.BatchDB.set(cell.GetID()-1, ru);
		   			//System.out.println("jobid-1="+(job.getId()-1));
		   		 }
		   		
	   			for (Cell cell : cs)
		                {
		   				 RandomSelector rs = new RandomSelector();

		                    for (int i = 0; i < buffRuleNum; i++)
		                    {
				   				 rs.AddItem(BuffPheromone[cell.GetID()-1][i], i);
		                    }

		                    int ru = rs.GetResult();
		                   // System.out.println("jrule锟斤拷锟教赌ｏ拷result"+ru);
		                 //   job.setJobRule(Constants.jRules[ru]);

		                    ant.BatchRule.set(cell.GetID()-1, ru);
		                }
		   			//CELLALL
		   			 j=0;
		   			 for (int i=0 ;i< cs.size() ; i++){
		   				 for(int k=0; k< ant.BatchDB.get(i); k++){
		   					 //job.setJobRule(Constants.jRules[ru]);
		   					// js.get(j).setJobRule( ant.AssignmentRule.get(i));
		   					 cs.get(j).setBufferOutRule( Constants.bRules[ant.BatchRule.get(i)]);
		   					 j++;
		   					 if(j>=cs.size() )
		   					 {
		   						i=cs.size();
		   			        	break;		 
		   					 }
		   				 }
		   			 }
//	   			for (Cell cell : cs)
//                {
//   				 RandomSelector rs = new RandomSelector();
//
//                    for (int i = 0; i < timewindowRuleNum; i++)
//                    {
//                        rs.AddItem(AssignmentPheromone[cell.GetID()-1][i], i);
//                    }
//
//                    int ru = rs.GetResult();
//
//                    cell.GetVehicle().setTimeWindowRule(Constants.tRules[ru]);
//
//                    ant.TimeWindowRule.set(cell.GetID()-1, ru);
//                }

	   			SimpleScheduler scheduler = new SimpleScheduler(ms,js,cs);	
	   			//System.out.println("this is find solution!");
	   			//锟斤拷锟斤拷	
	   			scheduler.schedule();
	         	ant.MakeSpan = new Makespan().getMeasurance(scheduler);
	         	ant.TotalWaitTime = new TotalWeightedTardiness().getMeasurance(scheduler);
	         	ant.Score = ant.TotalWaitTime;
	        // 	ant.Score = ant.MakeSpan;
	         	scheduler.reset();
	   			}   
  }

   protected int UpdatePheromone()
{

	            //锟斤拷锟斤拷全锟斤拷锟斤拷锟脚斤拷锟铰�
	            Collections.sort(ants,new MyComparator());
	            
	            List<Ant> toUpdate = new ArrayList<Ant>();
	            

	            if (ants.get(0).Score < BestSolution.Score)
	            {
	                BestSolution = ants.get(0).Clone();

	                _smax = smax;
	                
	            }
	            else
	            {
	               toUpdate.add(BestSolution);
	                
	            }
	            
	            //锟接凤拷
	            
	            //DB
	            for (int i = 0; i < JobNum; i++)
	            {
	                for (int j = 0; j < dbSize; j++)
	                {
	                    AssignmentDBPheromone[i][j] = (1 - p) * AssignmentDBPheromone[i][j];
	                    if (AssignmentDBPheromone[i][j] < min)
	                        AssignmentDBPheromone[i][j] = min;
	                }
	            }
	            
	            for (int i = 0; i < JobNum; i++)
	            {
	                for (int j = 0; j < assRuleNum; j++)
	                {
	                    AssignmentPheromone[i][j] = (1 - p) * AssignmentPheromone[i][j];
	                    if (AssignmentPheromone[i][j] < min)
	                        AssignmentPheromone[i][j] = min;
	                }
	            }

	            for (int i = 0; i < MachineNum; i++)
	            {
	                for (int j = 0; j < seqRuleNum; j++)
	                {
	                    SeqPheromone[i][j] = (1 - p) * SeqPheromone[i][j];
	                    if (SeqPheromone[i][j] < min)
	                        SeqPheromone[i][j] = min;
	                }
	            }
	            for (int i = 0; i < CellNum; i++)
	            {
	                for (int j = 0; j < buffRuleNum; j++)
	                {
	                    BuffPheromone[i][j] = (1 - p) * BuffPheromone[i][j];
	                    if (BuffPheromone[i][j] < min)
	                    	BuffPheromone[i][j] = min;
	                }
	            }
//	            for (int i = 0; i < CellNum; i++)
//	            {
//	                for (int j = 0; j < timewindowRuleNum; j++)
//	                {
//	                    TimeWindowPheromone[i][j] = (1 - p) * TimeWindowPheromone[i][j];
//	                    if (TimeWindowPheromone[i][j] < min)
//	                    	TimeWindowPheromone[i][j] = min;
//	                }
//	            }

	            for (int i = 0; i < BestNum; i++)        //锟斤拷锟斤拷锟斤拷虾玫募锟斤拷锟�5
	            {
	                toUpdate.add(ants.get(i)); 
	            }
	            
	            
	            for (int best = 0; best < toUpdate.size(); best++)
	            {

	                toUpdate.get(best).Score = (double)toUpdate.get(best).TotalWaitTime;

	                double _t = Q / ((double)toUpdate.get(best).TotalWaitTime/ lowerLimit);

	                //时锟戒窗锟斤拷息锟截革拷锟斤拷
//	                for (int i = 0; i < CellNum; i++)
//	                {
//	                	
//	                	
//	                    int twrule =toUpdate.get(best).TimeWindowRule.get(i);
//	                    double temp = TimeWindowPheromone[i][twrule] + p * _t;
//	                    if (temp > max)
//	                        temp = max;
//	                    TimeWindowPheromone[i][twrule]= temp;
//	                }
	                
	                //锟斤拷锟斤拷锟斤拷息锟截革拷锟斤拷
	                for (int i = 0; i < CellNum; i++)
	                {
	                    int buffrule =toUpdate.get(best).BatchRule.get(i);
	                    double temp = BuffPheromone[i][buffrule] + p * _t;
	                    if (temp > max)
	                        temp = max;
	                    BuffPheromone[i][buffrule]= temp;
	                }
	                
	                //锟斤拷锟斤拷锟斤拷息锟截革拷锟斤拷
	                int allDB=0;
	                for (int i = 0; i < JobNum; i++)
	                {	
	                	
	                    int assrule =toUpdate.get(best).AssignmentRule.get(i);
	                //    System.out.println(toUpdate.get(best).AssignmentDB.get(11));
	               //     System.out.println("Jnum"+JobNum);
	                    int db      =toUpdate.get(best).AssignmentDB.get(i);
	                   
	                    double temp = AssignmentPheromone[i][assrule] + p * _t;
	                 //   System.out.println("i="+i+"=DB"+db+"assign_db=="+AssignmentDB.length);
	                    double temp2 = AssignmentDBPheromone[i][db-1] + p * _t;
	                    if (temp > max)
	                        temp = max;
	                    if (temp2 > max)
	                        temp2 = max;
	                    AssignmentPheromone[i][assrule] = temp;
	                    AssignmentDBPheromone[i][db-1]   = temp2;
	                    allDB+=db;
	                    if(allDB > JobNum)
	                    break;
	                }
	                /**To be done -- 模锟斤拷assignment写 **/
	                //锟斤拷锟斤拷锟斤拷息锟截革拷锟斤拷
	                allDB=0;
	                for (int i = 0; i < MachineNum; i++)
	                {
	                    int assrule =toUpdate.get(best).SeqRule.get(i);
	                //    System.out.println(toUpdate.get(best).AssignmentDB.get(11));
	               //     System.out.println("Jnum"+JobNum);
	                    int db      =toUpdate.get(best).SeqDB.get(i);
	                   
	                    double temp = SeqPheromone[i][assrule] + p * _t;
	                 //   System.out.println("i="+i+"=DB"+db+"assign_db=="+AssignmentDB.length);
	                    double temp2 = SeqDBPheromone[i][db-1] + p * _t;
	                    if (temp > max)
	                        temp = max;
	                    if (temp2 > max)
	                        temp2 = max;
	                    SeqPheromone[i][assrule] = temp;
	                    SeqDBPheromone[i][db-1]   = temp2;
	                    allDB+=db;
	                    if(allDB > MachineNum)
	                    break;
	                }
	                //锟斤拷锟斤拷锟斤拷息锟截革拷锟斤拷
	                /**
	                for (int i = 0; i < MachineNum; i++)
	                {
	                    int seqrule = toUpdate.get(best).SeqRule.get(i);
	                    double temp = SeqPheromone[i][seqrule] + p * _t;
	                    if (temp > max)
	                        temp = max;
	                    SeqPheromone[i][seqrule] = temp;
	                }
	                **/
	                allDB=0;
	                for (int i = 0; i < CellNum; i++)
	                {
	                    int assrule =toUpdate.get(best).BatchRule.get(i);
	                //    System.out.println(toUpdate.get(best).AssignmentDB.get(11));
	               //     System.out.println("Jnum"+JobNum);
	                    int db      =toUpdate.get(best).BatchDB.get(i);
	                   
	                    double temp = BuffPheromone[i][assrule] + p * _t;
	                 //   System.out.println("i="+i+"=DB"+db+"assign_db=="+AssignmentDB.length);
	                    double temp2 = BuffDBPheromone[i][db-1] + p * _t;
	                    if (temp > max)
	                        temp = max;
	                    if (temp2 > max)
	                        temp2 = max;
	                    BuffPheromone[i][assrule] = temp;
	                    BuffDBPheromone[i][db-1]   = temp2;
	                    allDB+=db;
	                    if(allDB > CellNum)
	                    break;
	                }
	            }
	            
	            _smax--;
	            if (_smax < 0)
	                return 1;

	            for(Ant ant : ants)
	            {
	                ant.Reset();
	            }
  return 0;
}

}

