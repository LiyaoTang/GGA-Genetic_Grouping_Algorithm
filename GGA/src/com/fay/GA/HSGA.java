package com.fay.GA;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.jcp.xml.dsig.internal.dom.Utils;

import com.fay.GA.Chromosome;
import com.fay.util.Constants;
import com.fay.measure.IMeasurance;
import com.fay.measure.TotalWeightedTardiness;
import com.fay.domain.CellSet;
import com.fay.domain.Job;
import com.fay.domain.JobSet;
import com.fay.domain.MachineSet;
import com.fay.scheduler.AbstractScheduler;
import com.fay.scheduler.SimpleScheduler;
import com.fay.statics.RuleFrequencyStatistic;
import com.fay.localsearch.LocalSearchMethods;
import com.fay.domain.Machine;
import com.fay.domain.Cell;


public class HSGA {/*每一种fuction结果对应的染色体，最多允许出现在种群中的次数*/	
	private static final int MAX_AMOUNT_OF_EACH_CHROMOS = 8;
	private static final int MIN_AMOUNT_OF_EACH_CHROMOS = 2;
	/***************************属性域***********************************************************************/
		/**针对具体问题的GA's evalution process**/
		protected SimpleScheduler evaluator;
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
		/**出单元缓冲区的染色体**/
		protected List<Chromosome> cellPopulation;
		/**工序分派的染色体**/
		protected List<Chromosome> jobPopulation;
		
		
		/**最佳机器染色体序列**/
		protected Chromosome bestmChromosome;
		/**最佳小车染色体序列**/
		protected Chromosome bestTransChromosome;

		
		
		/**最佳出单元缓冲区染色体序列**/
		protected Chromosome bestcellChromosome;
		/**工序分派染色体序列**/
		protected Chromosome bestjobChromosome;

		//算法参数
		/**the population's size. dafault=48**/
		protected final int POPULATION_SIZE;
		/**the probablity of crossover. dafault=0.6**/
		protected double CROSSOVER_PROBABILITY;
		/**the probablity of mutation. dafault=0.18**/
		protected double MUTATION_PROBABILITY;
		/**the threshold of generation numbers. dafault=100**/
		protected final int MAX_GENERATION;
		/**the threshold of chromosome numbers that have no improvement. dafault=100**/
		protected final int MAX_NONIMPROVE;
//		/** (father + children)'s size/population size . dafault=2**/
//		protected final int MAX_POPULATION_RATE=2;
		/**the probablity of father population that being reserved to next population . default=0.2**/
		protected double RESERVEDPROBABLITY=0.3;
		
		
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
	/***************************构造函数域***********************************************************************/
		/** 
		 * @Description:construction of HSGA:默认参数
		 * @param mSet
		 * @param jSet
		 * @param cellSet
		 * @param scheduler
		 * @param measurance
		 */
		public HSGA(MachineSet mSet, JobSet jSet, CellSet cellSet,
				SimpleScheduler scheduler, IMeasurance measurance) {
			this(mSet, jSet, cellSet, scheduler, measurance, 0.6, 0.18, 200, 100);
		}

		/**
		 * @Description:construction of HSGA :自定义GA参数
		 * @param mSet
		 * @param jSet
		 * @param cellSet
		 * @param scheduler
		 * @param measurance
		 * @param cross
		 * @param mutation
		 * @param maxGeneration
		 * @param populationSize
		 * HSGA
		 * @exception:
		 */
		public HSGA(MachineSet mSet, JobSet jSet, CellSet cellSet,
				SimpleScheduler scheduler, IMeasurance measurance, double cross,
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
//			RESERVEDPROBABLITY=0.3;
			this.evaluator = new SimpleScheduler(mSet,jSet,cellSet);;
			mPopulation = new ArrayList<Chromosome>();
			transPopulation = new ArrayList<Chromosome>();
			cellPopulation = new ArrayList<Chromosome>();
			jobPopulation = new ArrayList<Chromosome>();

		}

		public void init() throws CloneNotSupportedException{           //防止重复添加
			
			int mSetSize = mSet.size();
			Chromosome mchromosome = new Chromosome(mSetSize);						//机器数量
			
			int vechicleNum = cellSet.size();
			Chromosome vchromosome = new Chromosome(vechicleNum);           //小车数量（时间窗）
			
			int cellNum = cellSet.size();								//单元数量（缓冲区）
			Chromosome cchromosome = new Chromosome(cellNum);   
			
			int jobNum = jSet.size();							//工件数量（工序分派）
			Chromosome jchromosome = new Chromosome(jobNum); 
			
			while(mPopulation.size()<POPULATION_SIZE) {	//运输段的染色体添加与否是和机器段一起确定的
					/**For test**/

					for (int index = 0; index < mSetSize; index++) {
						mchromosome.set(index, rand.nextInt(Constants.mRules.length));
					}

					for (int index = 0; index < vechicleNum; index++) {
						vchromosome.set(index, rand.nextInt(Constants.tRules.length));
					}
					
					for (int index = 0; index < cellNum; index++) {
						cchromosome.set(index, rand.nextInt(Constants.bRules.length));
					}
					
					for (int index = 0; index < jobNum; index++) {
						jchromosome.set(index, rand.nextInt(Constants.jRules.length));
					}
					
					updateMachineRules(mchromosome);
					updateTransRules(vchromosome);
					updateCellRules(cchromosome);
					updateJobRules(jchromosome);
					

						
						
						evaluator.schedule();
						
						

								
						double func_value= new TotalWeightedTardiness().getMeasurance(evaluator);

						evaluator.reset();
						
						mchromosome.setFunction(func_value);
						vchromosome.setFunction(func_value);
						cchromosome.setFunction(func_value);
						jchromosome.setFunction(func_value);
						
						
						mchromosome.setFitness(1.0 / (1 + func_value));	
						vchromosome.setFitness(1.0 / (1 + func_value));	
						cchromosome.setFitness(1.0 / (1 + func_value));	
						jchromosome.setFitness(1.0 / (1 + func_value));	
						
						mPopulation.add(mchromosome);
						transPopulation.add(vchromosome);
						cellPopulation.add(cchromosome);
						jobPopulation.add(jchromosome);

			}
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
			m.setMachineRule(Constants.mRules[chromosome.get(index++)]);
			
		}

	}
	
	/**
	 * @Description update rules for vehicles of cellSet        //小车时间窗的规则
	 * @param chromosome
	 */
	protected void updateTransRules(Chromosome chromosome) {
		int index = 0;
		String VehicleGene="";
		for (Cell cell : cellSet) {
			VehicleGene+=chromosome.get(index)+",";
			cell.GetVehicle().setTimeWindowRule(Constants.tRules[chromosome.get(index++)]);
		}
	}

	protected void updateCellRules(Chromosome chromosome) {        //出单元缓冲区rule
		int index = 0;
		String CellGene="";
		for (Cell cell : cellSet) {
			CellGene+=chromosome.get(index)+",";
			cell.setBufferOutRule(Constants.bRules[chromosome.get(index++)]);
		}
	}
	
	protected void updateJobRules(Chromosome chromosome) {			//工序分派rule
		int index = 0;
		String JobGene="";
		for (Job job : jSet) {
			JobGene+=chromosome.get(index)+",";
			job.setJobRule(Constants.jRules[chromosome.get(index++)]);
		}
	}
	
	public void printChromosome(ArrayList<Chromosome> population){
		for(Chromosome ch : population){
			System.out.print(ch);
		}
		
	}
	
	/****************************方法域*********************************************************************/

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
	 * @Description 获取 bestFunction变量的值
	 * @return value of bestFunction
	 */
	public double getFunctionValue() {
		return bestFunction;
	}

	/** MAIN PROCESS
	 * @throws CloneNotSupportedException 
	 * @Description framework for GA
	 * 
	 */
	


	public double schedule() throws CloneNotSupportedException {
		
//		initialPopulation();		//可重复添加
		init();         
		
		int nonImporveCount = 0;	// 未改善代数计数器
		double func_value = 0,fitness;	//记录每个染色体对应的函数值和适应度
		for (int currentGen = 0; currentGen < MAX_GENERATION; currentGen++) {//迭代进化过程

			
			for (int index = 0; index < POPULATION_SIZE; index++) {
				func_value = evaluation(mPopulation.get(index),transPopulation.get(index),cellPopulation.get(index),jobPopulation.get(index));
				evaluator.reset();
				fitness = 1.0 / (1 + func_value);
				


				mPopulation.get(index).setFitness(fitness).setFunction(func_value);
				transPopulation.get(index).setFitness(fitness).setFunction(func_value);		
				cellPopulation.get(index).setFitness(fitness).setFunction(func_value);
				jobPopulation.get(index).setFitness(fitness).setFunction(func_value);	
			}

			updateBestChromosome();		//更新调度规则和函数值，并判断调度结果是否有效
			
			if(isImproved){
				nonImporveCount = 0;
				isImproved = false;
			}else{
				nonImporveCount++;
			}
			
			if (nonImporveCount > MAX_NONIMPROVE) {
				break;
			}
			
			//GA 进化操作
			selectOperator();
			crossOverOperator();
			mutationOperator();

		}
		
		return  bestFunction;
	}



	/**
	 * @Description update the value of bestmChromosome&&bestTransChromosome
	 * @throws CloneNotSupportedException
	 */
	private void updateBestChromosome() throws CloneNotSupportedException {
		bestmChromosome = bestSoFar(mPopulation, bestmChromosome);
		bestTransChromosome = bestSoFar(transPopulation, bestTransChromosome);
		bestcellChromosome = bestSoFar(cellPopulation, bestmChromosome);
		bestjobChromosome = bestSoFar(jobPopulation, bestTransChromosome);
	}

	/**
	 * @Description evalution process for GA
	 * @param trans_chromosome chromosome for trans part 
	 * @param m_chromosome chromosome for machine part
	 */
	protected double evaluation(Chromosome m_chromosome, Chromosome trans_chromosome, Chromosome cell_chromosome, Chromosome job_chromosome) {        //需要修改
			updateMachineRules(m_chromosome);
			updateTransRules(trans_chromosome);
			updateCellRules(cell_chromosome);
			updateJobRules(job_chromosome);
			
//    		long start = System.currentTimeMillis();
//    		System.out.println("初始Timer:"+Timer.currentTime());
//			try {

				evaluator.schedule();

//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			long end   = System.currentTimeMillis();
//			System.out.println("本次时间:"+(end-start)+"ms");
				double fitness = measurance.getMeasurance(evaluator);
				evaluator.reset();
			/**确定fitness的过程很重要，比例太接近不利于决定进化方向**/
			return fitness;										//调度过程
	}

	/**
	 * @Description Find the best chromosome
	 * @param population
	 * @param bestChromosome
	 * @return bestChromosome
	 * @throws CloneNotSupportedException
	 */
	private Chromosome bestSoFar(List<Chromosome> population, Chromosome bestChromosome)
			throws CloneNotSupportedException {
		Chromosome currentBest = Collections.min(population).clone();
		//bestFitness = currentBest.getFitness();
		//bestFunction = currentBest.getFunction();
		
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
	
	/**
	 * @throws CloneNotSupportedException 
	 * @Description select process for GA
	 */
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
		updateChromosomeProbability(cellPopulation, totalFitness);
		updateChromosomeProbability(jobPopulation, totalFitness);
		select(mPopulation, totalFitness);
		select(transPopulation, totalFitness);
		select(cellPopulation, totalFitness);
		select(jobPopulation, totalFitness);
	}

	/**
	 * @Description select methods : roulette methods(效果不好，直接sort，选前几个出来，然后用病毒遗传)
 	 * @param population
	 * @param totalFitness
	 * @return
	 * @throws CloneNotSupportedException 
	 */
	private void select(List<Chromosome> population, double totalFitness) throws CloneNotSupportedException {
		List<Chromosome> newPopulation = new ArrayList<Chromosome>();
		Random rand = new Random(System.currentTimeMillis());
		Collections.sort(population);
		
		newPopulation.add(population.get(0).clone());
		newPopulation.add(population.get(1).clone());
		
		int reserved_size = (int)(POPULATION_SIZE * RESERVEDPROBABLITY);
		int i=0;
		while (newPopulation.size() < reserved_size ) {
//			/**
			double prob = rand.nextDouble();
			double sum=0.0;
			for (int j = 0; j < population.size(); j++) {
				sum+=population.get(j).getProb();
				if (sum > prob) {
					try {
						newPopulation.add(population.get(j).clone());
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
		}

		population.clear();
		population.addAll(newPopulation);
	}


	/**
	 * @Description calculate the Probability of Chromosome that being selected
	 * @param population
	 * @param totalFitness
	 */
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
	
	/**
	 * @throws CloneNotSupportedException 
	 * @Description cross process for GA
	 */
	private void crossOverOperator() throws CloneNotSupportedException {
		//List是引用传递的
		cross(mPopulation);
		cross(transPopulation);
		cross(cellPopulation);
		cross(jobPopulation);
	}

	/**
	 * @Description corss methods : 改成平移变异
	 * @param population
	 * @throws CloneNotSupportedException 
	 */
	private void cross(List<Chromosome> population) throws CloneNotSupportedException {
		for (int count = 0; count < POPULATION_SIZE - POPULATION_SIZE*RESERVEDPROBABLITY; count += 2) {
			Chromosome one, two;

				one = population.get(rand.nextInt((int) (POPULATION_SIZE*RESERVEDPROBABLITY))).clone();
				two = population.get(rand.nextInt((int) (POPULATION_SIZE*RESERVEDPROBABLITY))).clone();
				if (rand.nextDouble() <= CROSSOVER_PROBABILITY) {// �������Ҫ�������н�������
					
					int pos1 = rand.nextInt(one.size());
					int pos2 = rand.nextInt(one.size());
					swapChromosome(one, two, pos1, pos2);
				}
				population.add(one);
				population.add(two);
		}
	}

	/** 突变操作 **/
	/**
	 * @Description mutate process for GA : two point mutation
	 */
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
						.nextInt(Constants.tRules.length));
			}
		}
		for (Chromosome chromosome : cellPopulation) {
			if (rand.nextDouble() <= MUTATION_PROBABILITY) {// 满足概率要求则运行变异算子
				chromosome.set(rand.nextInt(cellSet.size()), rand
						.nextInt(Constants.tRules.length));
			}
		}
		for (Chromosome chromosome : jobPopulation) {
			if (rand.nextDouble() <= MUTATION_PROBABILITY) {// 满足概率要求则运行变异算子
				chromosome.set(rand.nextInt(cellSet.size()), rand
						.nextInt(Constants.tRules.length));
			}
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


	public void printfChromosome(Chromosome ch){
		for(int i : ch.getGene())
			System.out.print(i);
	}

	
	/**
	 * @Description output best machine chromosome
	 * @return
	 */
	public String getBestmChromosome() {
		return "machine:" + bestmChromosome.toString();
	}

	/**
	 * @Description output best trans chromosome
	 * @return
	 */
	public String getBestbChromosome() {
		return "Trans:" + bestTransChromosome.toString();
	}
    

	
	
	

}
