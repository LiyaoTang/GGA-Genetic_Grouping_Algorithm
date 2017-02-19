package com.fay.GGA;

import com.fay.domain.*;
import com.fay.localsearch.LocalSearchMethods;
import com.fay.measure.IMeasurance;
import com.fay.measure.Makespan;
import com.fay.measure.TotalWeightedTardiness;
import com.fay.scheduler.AbstractScheduler;
import com.fay.scheduler.SimpleScheduler;
import com.fay.statics.RuleFrequencyStatistic;
import com.fay.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AntSystem {
    private static final int MAX_AMOUNT_OF_EACH_CHROMOS = 8;
    private static final int MIN_AMOUNT_OF_EACH_CHROMOS = 4;
    public static int MachineNum;
    public static int JobNum;
    public static int CellNum;
    /******** 属性域*******/

    static IMeasurance makespan = new Makespan();
    static IMeasurance TWT = new TotalWeightedTardiness();
    public Double a1, b1, a2, b2, p, max, min, Q, r1, r2, r3;
    public int imax;
    public int smax;
    public int AntNum;
    public int BestNum;
    public int lowerLimit;
    public List<Ant> ants;
    public double[][] TimeWindowPheromone;
    public double[][] AssignmentPheromone;
    public double[][] AssignmentDBPheromone;
    public double[][] SeqPheromone;
    public double[][] SeqDBPheromone;
    public double[][] BuffPheromone;
    public double[][] BuffDBPheromone;
    public Ant BestSolution;
    /**
     * 针对具体问题的GA's evalution process
     **/
    protected AbstractScheduler evaluator;
    /**
     * 适应度评估方法
     **/
    protected IMeasurance measurance;
    /**
     * 选择的领域搜索方法
     **/
    protected LocalSearchMethods LSMethods;
    /**
     * 用于随机的种子
     **/
    protected Random rand = new Random(System.currentTimeMillis());
    /**
     * 最佳适应度函数value对应的适应度
     **/
    protected double bestFitness = 0d;
    /**
     * 最佳的适应度函数的value
     **/
    protected double bestFunction = 0d;
    /**
     * 机器的染色体
     **/
    protected List<Chromosome> mPopulation;
    /**
     * 小车的染色体
     **/
    protected List<Chromosome> transPopulation;
    //****GA*****///
    /**
     * 工件
     **/
    protected List<Chromosome> jPopulation;
    /**
     * 最佳机器染色体序列
     **/
    protected Chromosome bestmChromosome;
    /**
     * 最佳小车染色体序列
     **/
    protected Chromosome bestTransChromosome;
    /**
     * 最佳工件序列
     **/
    protected Chromosome bestjChromosome;
    /**
     * the population's size. dafault=48
     **/
    protected int POPULATION_SIZE = 48;
    /**
     * the probablity of crossover. dafault=0.6
     **/
    protected double CROSSOVER_PROBABILITY = 0.6;
    /**
     * the probablity of mutation. dafault=0.18
     **/
    protected double MUTATION_PROBABILITY = 0.18;
    /**
     * the threshold of generation numbers. dafault=100
     **/
    protected int MAX_GENERATION = 10;
    /**
     * the threshold of chromosome numbers that have no improvement. dafault=100
     **/
    protected int MAX_NONIMPROVE;
    protected int[] benchmark = new int[]{0, 666, 655, 597, 590, 593, 926, 890, 863, 951, 958, 1222, 1039, 1150, 1292, 1207, 945, 784, 848, 842, 902, 1046, 927, 1032, 935, 977, 1218, 1235, 1216, 1153, 1355, 1784, 1850, 1719, 1721, 1888, 1268, 1397, 1196, 1233, 1222};

    //种群数据相关变量
    /**
     * the probablity of father population that being reserved to next population . default=0.2
     **/
    protected double RESERVEDPROBABLITY = 0.3;
    /**
     * the machine's set
     **/
    protected MachineSet mSet;
    /**
     * the job's set
     **/
    protected JobSet jSet;
    /**
     * the cell's set
     **/
    protected CellSet cellSet;
    /**
     * ending flag of evaluation process
     **/
    protected boolean isImproved = false;
    int _smax;
//	    	protected Chromosome bestjChromosome;

    //算法参数
    int kmax;
    MachineSet ms;
    JobSet js;
    CellSet cs;
    Random rdm;
    int assRuleNum = 5;
//	    	/** (father + children)'s size/population size . dafault=2**/
//	    	protected final int MAX_POPULATION_RATE=2;
    int seqRuleNum = 12;
    int timewindowRuleNum = 1;
    int buffRuleNum = 7;

    //the input data for inter-cell problems
    //DB.size
    int dbSize = 1;
    AbstractScheduler scheduler;
    /**
     * 实验记录参数
     **/
    double MAX_POPULATION;
    double MIN_POPULATION;
    /**
     * 规则分配频率统计类
     * Frequency statistics of heuristic rules will be used
     */
    private RuleFrequencyStatistic stat = new RuleFrequencyStatistic();

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

    public void Init() {

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

        HSGA(ms, js, cs, scheduler, TWT, 0.6, 0.18, 150, 73);
        ants = new ArrayList<Ant>(AntNum);

    }


    public void schedule(int caseNo) throws CloneNotSupportedException {

        init_popula_withcheck();
        int nonImporveCount = 0;
        double func_value, fitness;
        for (int currentGen = 0; currentGen < MAX_GENERATION; currentGen++) {//迭代进化过程

			//LocalSearch();
        	//System.out.println("!Generation = " + currentGen );
          

            for (int index = 0; index < POPULATION_SIZE; index++) {
//			System.out.println("currenGen = " + currentGen+ " index of population" + index);
                //System.out.println("!!!!index"+index+"???"+mPopulation.get(index).RuleSize());
                //	System.out.println("!!! index = " + index );
   
            	func_value = evaluation(mPopulation.get(index), transPopulation.get(index), jPopulation.get(index));
                fitness = 1.0 / (1 + func_value);
                mPopulation.get(index).setFitness(fitness).setFunction(func_value);
                transPopulation.get(index).setFitness(fitness).setFunction(func_value);
                jPopulation.get(index).setFitness(fitness).setFunction(func_value);

            }
            //for(Chromosome ch:mPopulation ){
           // 	ch.Print_Gene();
            //}

            updateBestChromosome();        //更新调度规则和函数值，并判断调度结果是否有效
            //bestmChromosome.Print_Gene();
            //bestTransChromosome.Print_Gene();
            //bestjChromosome.Print_Gene();

            //localSearch();


            //System.out.println("bestmChromosome" + this.bestmChromosome.getFitness());
			//System.out.println("bestTransChromosome= " + this.bestTransChromosome .getFitness());
			//System.out.println("bestjChromosome = " + this.bestjChromosome .getFitness() );
            if (benchmark[caseNo] == bestmChromosome.getFunction()) {
                break;
            }
            if (isImproved) {
                nonImporveCount = 0;
                isImproved = false;
            } else {
                nonImporveCount++;
            }

            if (nonImporveCount > MAX_NONIMPROVE) {
                break;
            }
            // 为了取得种群里面的结果设置的
            if (currentGen == MAX_GENERATION - 2) {
                break;
            }
            //System.out.println("111111");
            //GA 进化操作

            selectOperator();
            crossOverOperator();
            GGAmutationOperator();
            

            //System.out.println("Gene:" + );

//			VirusOperator();					//后期扩展来做，病毒变异
            //Catastrophe(currentGen);			//choose to change the args or not?
        }
    }


    
    
    
    
    public double Findbest() {
        double ans = 1.7976931348623157E308;
        for (int i = 0; i < mPopulation.size(); i++) {
            Chromosome cur = mPopulation.get(i);
            if (ans > cur.getFunction()) {
                ans = cur.getFunction();
            }

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
        for (int i = 0; i < mPopulation.size(); i++) {
            Chromosome cur = mPopulation.get(i);
            //bw.write(i+":\t"+cur.getFunction()+"\t"+Outputchromos(cur)+":\t"+Outputchromos(transPopulation.get(i))+"\n");
            System.out.println(i + ":\t" + cur.getFunction() + "\t" + Outputchromos(cur) + ":\t" + Outputchromos(transPopulation.get(i)) + ":\t" + Outputchromos(jPopulation.get(i)) + "\n");
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

    private void init_popula_withcheck() throws CloneNotSupportedException {
        //int Size = sumOperation();
        //System.out.println("Size="+Size);
    	int cellNum = cellSet.size();
    	int machineNum = mSet.size();
    	int jobNum = jSet.size();
    	//System.out.println("Size="+jSet.size());
        Chromosome mchromosome = new Chromosome(machineNum);
        Chromosome bchromosome = new Chromosome(cellNum);
        Chromosome jchromosome = new Chromosome(jobNum);
        
        
        
        mchromosome.SetRulesize((int) Math.ceil((double) machineNum * 5 / 100)+2);
//		bchromosome.SetRulesize(batchNum);
        jchromosome.SetRulesize((int) Math.ceil((double) jobNum * 5 / 100)+5);
        //mchromosome.SetRulesize(5);
        
        bchromosome.SetRulesize((int) Math.ceil(cellNum));
        //	jchromosome.SetRulesize(5);

        while (mPopulation.size() < POPULATION_SIZE) {    //运输段的染色体添加与否 是 和机器段一起确定的

//			System.out.println("populationsize = " + mPopulation.size());
			/*给M染色体分配规则*/
            for (int index = 0; index < mchromosome.RuleSize(); index++) {
                mchromosome.SetRuleNo(index, rand.nextInt(Constants.mRules.length));
            }

            //System.out.println("mSetSize"+mSetSize);
            for (int index = 0; index < machineNum; index++) {
                mchromosome.set(index, rand.nextInt(mchromosome.RuleSize()));
            }
            //System.out.println("populationsize = " + mPopulation.size());
            //mchromosome.Print_Gene();
//				 System.out.println(mchromosome.getGene()+mchromosome.getRule());
				/*给B染色体分配规则*/
//				System.out.println(" bchromosome.size =" + bchromosome.RuleSize());
            for (int index = 0; index < bchromosome.RuleSize(); index++) {
                bchromosome.SetRuleNo(index, rand.nextInt(Constants.bRules.length));
            }

            for (int index = 0; index < cellNum; index++) {
                bchromosome.set(index, rand.nextInt(bchromosome.RuleSize()));
            }
				
				
				/*给J染色体分配规则*/

            for (int index = 0; index < jchromosome.RuleSize(); index++) {

                jchromosome.SetRuleNo(index, rand.nextInt(Constants.jRules.length));

            }
            for (int index = 0; index < jobNum; index++) {
                jchromosome.set(index, rand.nextInt(jchromosome.RuleSize()));
            }


            evaluator = new SimpleScheduler(mSet, jSet, cellSet, mchromosome, jchromosome, bchromosome); ////!!!!!!!!!!!!!!!!!


            //evaluator.reset();
            evaluator.schedule();    ///catch!!!!!!!!!!!!!!11
            mchromosome.clearIndex();
            bchromosome.clearIndex();
            jchromosome.clearIndex();
            double func_value = measurance.getMeasurance(evaluator);
            mchromosome.setFunction(func_value);
            bchromosome.setFunction(func_value);
            jchromosome.setFunction(func_value);

//				System.out.println("个体"+mPopulation.size()+":"+bchromosome.getFunction());
            mchromosome.setFitness(1.0 / (1 + func_value));
            bchromosome.setFitness(1.0 / (1 + func_value));
            jchromosome.setFitness(1.0 / (1 + func_value));
            //System.out.println("!!!!!");
            SelectionAdd(mPopulation, mchromosome.clone(), MIN_AMOUNT_OF_EACH_CHROMOS);
            SelectionAdd(transPopulation, bchromosome.clone(), MIN_AMOUNT_OF_EACH_CHROMOS);
            SelectionAdd(jPopulation, jchromosome.clone(), MIN_AMOUNT_OF_EACH_CHROMOS);
            evaluator.reset();
        }

        /**找出初始解中最差值，并记录**/
        double currentWorstFunc = mPopulation.get(0).getFunction();
        Chromosome currentWorst;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Chromosome temp = mPopulation.get(i);
            if (temp.getFunction() >= currentWorstFunc) {
                currentWorst = temp;
                currentWorstFunc = temp.getFunction();
            }
        }

        MAX_POPULATION = currentWorstFunc;
       // mchromosome.Print_Gene();
//		System.out.println("初始化结束");
    }


    protected double evaluation(Chromosome m_chromosome, Chromosome trans_chromosome, Chromosome job_chromosome) {
        double ans;
        evaluator = new SimpleScheduler(ms, js, cs, m_chromosome, job_chromosome, trans_chromosome); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //updateMachineRules(m_chromosome);
        //updateTransRules(trans_chromosome);
        //updateJobRules(job_chromosome);
        //evaluator.reset();
        evaluator.schedule();              ///catch!!!!!!!!!!!!
        m_chromosome.clearIndex();
        trans_chromosome.clearIndex();
        job_chromosome.clearIndex();
        ans = measurance.getMeasurance(evaluator);


        evaluator.reset();
        /**确定fitness的过程很重要，比例太接近不利于决定进化方向**/
        return ans;
    }

    protected void updateMachineRules(Chromosome chromosome) {
        int index = 0;
        String MachineGene = "";
        for (Machine m : mSet) {
            MachineGene += chromosome.get(index) + ",";
            m.setMachineRule(Constants.mRules[chromosome.GetRuleNo(chromosome.get(index++))]);
        }
        // System.out.println(MachineGene);
    }

    protected void updateTransRules(Chromosome chromosome) {
        int index = 0;
        String CellGene = "";
        for (Cell m : cellSet) {
            CellGene += chromosome.get(index) + ",";
            m.setBufferOutRule(Constants.bRules[chromosome.GetRuleNo(chromosome.get(index++))]);
            //	System.out.println("INdex"+chromosome.GetRuleNo(chromosome.get(index++) ));
        }
        //System.out.println(CellGene);
    }


    protected void updateJobRules(Chromosome chromosome) {
        int index = 0;
        String JobGene = "";
        for (Job j : jSet) {
            JobGene += chromosome.get(index) + ",";
            j.setJobRule(Constants.jRules[chromosome.GetRuleNo(chromosome.get(index++))]);
        }
        //	System.out.println(JobGene);
    }


    private void SelectionAdd(List<Chromosome> Population, Chromosome chromos, int chromos_num_upper_bound) {
        // TODO Auto-generated method stub
        if (Population.size() == 0) {
            Population.add(chromos);
            return;
        }

        Double func_value = new Double(chromos.getFunction());
        int count = 0;
        for (Chromosome be : Population) {
            if (func_value.equals(be.getFunction())) {
                if (count == chromos_num_upper_bound) break;
                else count++;
            }
        }
        if (count < chromos_num_upper_bound) Population.add(chromos);
    }


    private void updateBestChromosome() throws CloneNotSupportedException {
        bestmChromosome = bestSoFar(mPopulation, bestmChromosome);
        bestTransChromosome = bestSoFar(transPopulation, bestTransChromosome);
        bestjChromosome = bestSoFar(jPopulation, bestjChromosome);
       //bestmChromosome.Print_Gene();
       // bestTransChromosome.Print_Gene();
       // bestjChromosome.Print_Gene();
        
    }

    public void localSearch(){
    	
    	int cellNum = cellSet.size();
    	int machineNum = mSet.size();
    	int jobNum = jSet.size();
        Chromosome msearcher = new Chromosome(machineNum);
        Chromosome bsearcher = new Chromosome(cellNum);
        Chromosome jsearcher = new Chromosome(jobNum);
       
    	msearcher.setGene(bestmChromosome.getGene());
    	bsearcher.setGene(bestTransChromosome.getGene());
    	jsearcher.setGene(bestjChromosome.getGene());
    	
    	msearcher.setRule(bestmChromosome.getRule());
    	bsearcher.setRule(bestTransChromosome.getRule());
    	jsearcher.setRule(bestjChromosome.getRule());
    	//System.out.println("m:");
    	//msearcher.Print_Gene();
    	//bsearcher.Print_Gene();
    	//jsearcher.Print_Gene();
    	
    	msearcher.localSearch(msearcher.getGene());
        bsearcher.localSearch(bsearcher.getGene());
    	jsearcher.localSearch(jsearcher.getGene());
    	//msearcher.Print_Gene();
    	//bsearcher.Print_Gene();
    	//jsearcher.Print_Gene();

    	
    	double func_value = evaluation(msearcher,
    			bsearcher,
    			jsearcher);
    	
    	
    	
    	
    	//evaluator = new SimpleScheduler(mSet, jSet, cellSet, msearcher, bsearcher, jsearcher); ////!!!!!!!!!!!!!!!!!


        //evaluator.reset();
        //evaluator.schedule();    ///catch!!!!!!!!!!!!!!11


        msearcher.setFunction(func_value);
        bsearcher.setFunction(func_value);
        jsearcher.setFunction(func_value);

//			System.out.println("个体"+mPopulation.size()+":"+bchromosome.getFunction());
        msearcher.setFitness(1.0 / (1 + func_value));
        bsearcher.setFitness(1.0 / (1 + func_value));
        jsearcher.setFitness(1.0 / (1 + func_value));
        
        if ( msearcher.compareTo(bestmChromosome) < 0) {
            bestmChromosome =  msearcher;
            bestTransChromosome =  bsearcher;
            bestjChromosome =  jsearcher;
            bestFitness = bestmChromosome.getFitness();
            bestFunction = bestmChromosome.getFunction();
            System.out.println("Local Search!");
            isImproved = true;
            mPopulation.set(0, bestmChromosome);
            transPopulation.set(0, bestTransChromosome);
            jPopulation.set(0, bestjChromosome);
        }
    	
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
            //bestChromosome.Print_Gene();
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
        int reserved_size = (int) (POPULATION_SIZE * RESERVEDPROBABLITY);
        //System.out.println(reserved_size);
        int i = 0;
        while (newPopulation.size() < reserved_size) {
//			/**
            double prob = rand.nextDouble();
            double sum = 0.0;
            for (int j = 0; j < population.size(); j++) {
                sum += population.get(j).getProb();
                if (sum > prob) {
                    try {
                        //执行重复元素检测，newpopulation中不能超过5个重复元素
                        int count = 0;
                        for (int k = 0; k < newPopulation.size(); k++) {
                            if (newPopulation.get(k).getFunction() == population.get(j).getFunction()) {
                                count++;
                            }
                        }
                        if (count < 5) {
                            newPopulation.add(population.get(j).clone());
                        } else {
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

        GGAcross(mPopulation);
        GGAcross(transPopulation);
        GGAcross(jPopulation);
    }

    private void GGAcross(List<Chromosome> population) throws CloneNotSupportedException {
//		/** for two point cross
        //Population_size 增加？？？？？？？
        for (int count = 0; count < POPULATION_SIZE - POPULATION_SIZE * RESERVEDPROBABLITY; ) {
            Chromosome one, two, three;


            one = population.get(rand.nextInt((int) (POPULATION_SIZE * RESERVEDPROBABLITY))).clone();
            two = population.get(rand.nextInt((int) (POPULATION_SIZE * RESERVEDPROBABLITY))).clone();

            three = population.get(rand.nextInt((int) (POPULATION_SIZE * RESERVEDPROBABLITY))).clone();
            //	System.out.println("!!!!!"+ population.get(1).RuleSize());
            //Add 之前需要重新清空function值
//				one.clearFunction();
//				two.clearFunction();
            three.ClearRule();
            if (rand.nextDouble() <= CROSSOVER_PROBABILITY) {// 满足概率要求则运行交叉算子

                int pos1 = rand.nextInt(one.RuleSize());
                int pos2 = rand.nextInt(one.RuleSize());
                int pos3 = rand.nextInt(two.RuleSize());
                int pos4 = rand.nextInt(two.RuleSize());
                //swapChromosome(one, two, pos1, pos2);
                if (pos2 < pos1) {
                    int temp = pos1;
                    pos1 = pos2;
                    pos2 = temp;
                }
                if (pos4 < pos3) {
                    int temp = pos3;
                    pos3 = pos4;
                    pos4 = temp;
                }

//					System.out.println("pos1="+pos1+"  pos2="+pos2+"  pos3="+pos3+"  pos4="+pos4);

//					for (int index = 0; index < one.size(); index++) {
//					      System.out.println( "~~~~"+three.get(index) +"!!!" );
//					}

                for (int index = 0; index < one.size(); index++) {

                    if (one.get(index) >= pos1 && one.get(index) <= pos2) {
                        //three.SetRuleNo(index, one.get(index));
                        three.set(index, one.get(index) - pos1);
                    } else if (two.get(index) >= pos3 && two.get(index) <= pos4) {
                        three.set(index, two.get(index) + pos2 - pos1 + 1 - pos3);
                    } else {
                        three.set(index, rand.nextInt(pos2 - pos1 + pos4 - pos3 + 1));
                    }
                }

                for (int index = pos1; index <= pos2; index++) {
                    three.AddRule(one.GetRuleNo(index));
                }
                for (int index = pos3; index <= pos4; index++) {
                    three.AddRule(two.GetRuleNo(index));
                }


                population.add(three);
//				System.out.println("three.rulesize = " + three.RuleSize());
                //System.out.println("population.size = "+population.size());
                count += 1;
            }
            population.add(one);
            population.add(two);

            count += 2;
        }

    }

    private void cross(List<Chromosome> population) throws CloneNotSupportedException {

//		/** for two point cross
        for (int count = 0; count < POPULATION_SIZE - POPULATION_SIZE * RESERVEDPROBABLITY; count += 2) {
            Chromosome one, two;


            one = population.get(rand.nextInt((int) (POPULATION_SIZE * RESERVEDPROBABLITY))).clone();
            two = population.get(rand.nextInt((int) (POPULATION_SIZE * RESERVEDPROBABLITY))).clone();
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

    /*
     *计算一共有多少工序
     */
    private int sumOperation() {
        int sumO = 0;
        for (Job job : jSet) {
            sumO += job.getOperationNum();
        }
        //	System.out.println("总工序"+"= "+sumO);
        return sumO;
    }

    private void swapChromosome(Chromosome left, Chromosome right, int pos1, int pos2) {
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
    private void GGAmutationOperator() {
        rand = new Random(System.currentTimeMillis());
        for (Chromosome chromosome : mPopulation) {
            if (rand.nextDouble() <= MUTATION_PROBABILITY) {// 满足概率要求则运行变异算子
//				chromosome.set(rand.nextInt(mSet.size()), rand
//						.nextInt(Constants.mRules.length));
                if (rand.nextDouble() < 0.5) {
                    ///默认分类概率0.5！！！！！！！！！！！！！！！！！！！！！！！

                    GGAsplit(chromosome, 'm');
                } else {
                    if (chromosome.RuleSize() == 1)
                        continue;
                    GGAmerging(chromosome);
                }

            }
        }
        for (Chromosome chromosome : transPopulation) {
            if (rand.nextDouble() <= MUTATION_PROBABILITY) {// 满足概率要求则运行变异算子
//				chromosome.set(rand.nextInt(cellSet.size()), rand
//						.nextInt(Constants.bRules.length));
                if (rand.nextDouble() < 0.5) {
                    GGAsplit(chromosome, 'b');
                } else {
                    if (chromosome.RuleSize() == 1)
                        continue;
                    GGAmerging(chromosome);
                }

            }
        }
        for (Chromosome chromosome : jPopulation) {
            if (rand.nextDouble() <= MUTATION_PROBABILITY) {// 满足概率要求则运行变异算子
//				chromosome.set(rand.nextInt(jSet.size()), rand
//						.nextInt(Constants.jRules.length));
                if (rand.nextDouble() < 0.5) {
                    GGAsplit(chromosome, 'j');
                } else {
                    if (chromosome.RuleSize() == 1)
                        continue;
                    GGAmerging(chromosome);
                }

            }
        }


    }

    private void GGAsplit(Chromosome Chromos, char c) {
        rand = new Random(System.currentTimeMillis());
        int select = rand.nextInt(Chromos.RuleSize());

        for (int i = 0; i < Chromos.size(); i++) {
            if (Chromos.get(i) == select) {
                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                if (rand.nextDouble() < 0.5) {
                    Chromos.set(i, Chromos.RuleSize());
                }
            }

        }
        if (c == 'm')
            Chromos.AddRule(rand.nextInt(Constants.mRules.length));
        if (c == 'b')
            Chromos.AddRule(rand.nextInt(Constants.bRules.length));
        if (c == 'j')
            Chromos.AddRule(rand.nextInt(Constants.jRules.length));

    }

    private void GGAmerging(Chromosome Chromos) {
        rand = new Random(System.currentTimeMillis());

        int delete = rand.nextInt(Chromos.RuleSize());
        int merge = rand.nextInt(Chromos.RuleSize());
        while (delete == merge) {
            delete = rand.nextInt(Chromos.RuleSize());
            merge = rand.nextInt(Chromos.RuleSize());
        }


        for (int i = 0; i < Chromos.size(); i++) {
            if (Chromos.get(i) == delete) {
                Chromos.set(i, merge);
            }
            if (Chromos.get(i) > delete)
                Chromos.set(i, Chromos.get(i) - 1);
        }

        Chromos.DeleteRule(delete);

    }

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

    private String Outputchromos(Chromosome Chromos) {
        if (Chromos == null)
            return "null";
        return Chromos.toString();
    }


    //BESt
    public double getBest() {
        return BestSolution.TotalWaitTime;
    }

    //best DB
    public void getBestDB() {
        // return BestSolution.TotalWaitTime;

        BestSolution.printDB();
        // System.out.print(";");
    }

    public void setProblemSize(MachineSet ms, JobSet js, CellSet cs) {
        MachineNum = ms.size();
        JobNum = js.size();
        CellNum = cs.size();
        this.ms = ms;
        this.js = js;
        this.cs = cs;
    }


}

