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
    private static final int MIN_AMOUNT_OF_EACH_CHROMOS = 4;
    public static int MachineNum;
    public static int JobNum;
    public static int CellNum;

    /******** 属性域*******/
    static IMeasurance TWT = new TotalWeightedTardiness();
    public Double a2, p, max, min, r2;

    /** 针对具体问题的GA's evaluation process **/
    protected AbstractScheduler evaluator;
    /** 适应度评估方法 **/
    protected IMeasurance measurance;
    /** 用于随机的种子 **/
    protected Random rand = new Random(System.currentTimeMillis());
    /** 最佳适应度函数value对应的适应度 **/
    protected double bestFitness = 0d;
    /** 最佳的适应度函数的value */
    protected double bestFunction = 0d;

    /** 机器、小车。工件 染色体 **/
    protected List<Chromosome> mPopulation;
    protected List<Chromosome> transPopulation;
    protected List<Chromosome> jPopulation;

    /** 最佳机器、小车、工件 染色体序列 **/
    protected Chromosome bestmChromosome;
    protected Chromosome bestTransChromosome;
    protected Chromosome bestjChromosome;

    /** population's size **/
    protected int POPULATION_SIZE = 48;
    /** probability of crossover **/
    protected double CROSSOVER_PROBABILITY = 0.6;
    /** probability of mutation **/
    protected double MUTATION_PROBABILITY = 0.18;
    /** threshold of generation numbers **/
    protected int MAX_GENERATION = 10;
    /** threshold of chromosome numbers that have no improvement **/
    protected int MAX_NONIMPROVE;
    protected int[] benchmark = new int[]{0, 666, 655, 597, 590, 593, 926, 890, 863, 951, 958, 1222, 1039, 1150, 1292, 1207, 945, 784, 848, 842, 902, 1046, 927, 1032, 935, 977, 1218, 1235, 1216, 1153, 1355, 1784, 1850, 1719, 1721, 1888, 1268, 1397, 1196, 1233, 1222};

    //种群数据相关变量
    /** probability of father population that being reserved to next population **/
    protected double RESERVEDPROBABLITY = 0.3;

    protected MachineSet mSet;
    protected JobSet jSet;
    protected CellSet cellSet;

    /** ending flag of evaluation process **/
    protected boolean isImproved = false;

    //算法参数
    MachineSet ms;
    JobSet js;
    CellSet cs;

    AbstractScheduler scheduler;

    public void Init(double cross, double mutation, double resProb, int maxGeneration, int populationSize) {
        CROSSOVER_PROBABILITY = cross;
        MUTATION_PROBABILITY = mutation;
        this.mSet = ms;
        this.jSet = js;
        this.cellSet = cs;
        this.measurance = TWT;
        MAX_GENERATION = maxGeneration;
        POPULATION_SIZE = populationSize;
        MAX_NONIMPROVE = MAX_GENERATION;
        RESERVEDPROBABLITY = resProb;

        this.evaluator = scheduler;
        mPopulation = new ArrayList<Chromosome>();
        transPopulation = new ArrayList<Chromosome>();
        jPopulation = new ArrayList<Chromosome>();
    }

    public void schedule(int caseNo) throws CloneNotSupportedException {
        init_popula_withcheck();

        int nonImporveCount = 0;
        double func_value, fitness;

        /** 迭代进化过程 */
        for (int currentGen = 0; currentGen < MAX_GENERATION; currentGen++) {
            for (int index = 0; index < POPULATION_SIZE; index++) {
            	func_value = evaluation(mPopulation.get(index), transPopulation.get(index), jPopulation.get(index));
                fitness = 1.0 / (1 + func_value);

                mPopulation.get(index).setFitness(fitness).setFunction(func_value);
                transPopulation.get(index).setFitness(fitness).setFunction(func_value);
                jPopulation.get(index).setFitness(fitness).setFunction(func_value);
            }

            //更新调度规则和函数值，并判断调度结果是否有效
            updateBestChromosome();

            if (isImproved) { nonImporveCount = 0; isImproved = false; }
            else nonImporveCount++;
            if (nonImporveCount > MAX_NONIMPROVE ||
                    benchmark[caseNo] == bestmChromosome.getFunction() ||
                    currentGen == MAX_GENERATION - 2 /** 为了取得种群里面的结果设置的*/) {
                break;
            }

            //GA 进化操作
            selectOperator(0);
            crossOverOperator(0);
            GGAmutationOperator();
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

        for (int i = 0; i < mPopulation.size(); i++) {
            Chromosome cur = mPopulation.get(i);
            //bw.write(i+":\t"+cur.getFunction()+"\t"+Outputchromos(cur)+":\t"+Outputchromos(transPopulation.get(i))+"\n");
            System.out.println(i + ":\t" + cur.getFunction() + "\t" + Outputchromos(cur) + ":\t" + Outputchromos(transPopulation.get(i)) + ":\t" + Outputchromos(jPopulation.get(i)) + "\n");
        }
//  		bw.write("最优解为："+bestFunction+"    ");
//  		bw.write("最差解为："+MAX_POPULATION+"    ");
//  		bw.write("最优解和最差解差值为："+(MAX_POPULATION-bestFunction));
//			bw.newLine();
    }

    /** Initialize population */
    private void init_popula_withcheck() throws CloneNotSupportedException {
    	int cellNum = cellSet.size();
        int machineNum = mSet.size();
        int jobNum = jSet.size();

        Chromosome mchromosome = new Chromosome(machineNum);
        Chromosome bchromosome = new Chromosome(cellNum);
        Chromosome jchromosome = new Chromosome(jobNum);

        /** 初始决策块数目 */
        mchromosome.SetRulesize((int) Math.ceil((double) machineNum * 5 / 100)+2);
        jchromosome.SetRulesize((int) Math.ceil((double) jobNum * 5 / 100)+5);
        bchromosome.SetRulesize((int) Math.ceil(cellNum));

        double min = Double.MAX_VALUE;
        int failToAddCnt = 0;
        while (mPopulation.size() < POPULATION_SIZE) {
			/*给M染色体分配规则*/
            for (int index = 0; index < mchromosome.RuleSize(); index++) {
                mchromosome.SetRuleNo(index, rand.nextInt(Constants.mRules.length)); // rule
            }
            for (int index = 0; index < machineNum; index++) {
                mchromosome.set(index, rand.nextInt(mchromosome.RuleSize())); // gene
            }
            /*给B染色体分配规则*/
            for (int index = 0; index < bchromosome.RuleSize(); index++) {
                bchromosome.SetRuleNo(index, rand.nextInt(Constants.bRules.length)); // rule
            }
            for (int index = 0; index < cellNum; index++) {
                bchromosome.set(index, rand.nextInt(bchromosome.RuleSize())); // gene
            }
            /*给J染色体分配规则*/
            for (int index = 0; index < jchromosome.RuleSize(); index++) {
                jchromosome.SetRuleNo(index, rand.nextInt(Constants.jRules.length)); // rule
            }
            for (int index = 0; index < jobNum; index++) {
                jchromosome.set(index, rand.nextInt(jchromosome.RuleSize())); // gene
            }

            /**Evaluate*/
            evaluator = new SimpleScheduler(mSet, jSet, cellSet, mchromosome, jchromosome, bchromosome);
            evaluator.schedule();

            mchromosome.clearIndex();
            bchromosome.clearIndex();
            jchromosome.clearIndex();

            /** set assessment value */
            double func_value = measurance.getMeasurance(evaluator);
            if(func_value < min) min = func_value;

            mchromosome.setFunction(func_value);
            bchromosome.setFunction(func_value);
            jchromosome.setFunction(func_value);

            mchromosome.setFitness(1.0 / (1 + func_value));
            bchromosome.setFitness(1.0 / (1 + func_value));
            jchromosome.setFitness(1.0 / (1 + func_value));

            /** add with duplicate checked*/
            // SelectionAdd(mPopulation, mchromosome, transPopulation, bchromosome, jPopulation, jchromosome, MIN_AMOUNT_OF_EACH_CHROMOS);
            if (failToAddCnt < MIN_AMOUNT_OF_EACH_CHROMOS) {
                if (SelectionAdd(mPopulation, mchromosome.clone(), transPopulation, bchromosome.clone(), jPopulation, jchromosome.clone(), MIN_AMOUNT_OF_EACH_CHROMOS)) failToAddCnt++;
                else failToAddCnt = 0;
            }
            else {
                failToAddCnt = 0;
                mPopulation.add(mchromosome.clone());
                transPopulation.add(bchromosome.clone());
                jPopulation.add(jchromosome.clone());
            }

            evaluator.reset();
        }

        System.out.println("initial best = " + min);
    }

    protected double evaluation(Chromosome m_chromosome, Chromosome trans_chromosome, Chromosome job_chromosome) {
        double ans;
        evaluator = new SimpleScheduler(ms, js, cs, m_chromosome, job_chromosome, trans_chromosome);
        evaluator.schedule();

        m_chromosome.clearIndex();
        trans_chromosome.clearIndex();
        job_chromosome.clearIndex();

        ans = measurance.getMeasurance(evaluator);
        evaluator.reset();

        /** 确定fitness的过程很重要，比例太接近不利于决定进化方向 */
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

    /** add chromosome into population with duplicate checked (Succ => rnt 0, fail => 1)*/
    private int SelectionAdd(List<Chromosome> Population, Chromosome chromos, int chromos_num_upper_bound) {
        if (Population.size() == 0) {
            Population.add(chromos);
            return 0;
        }

        /** num of duplicates no more than upper bound */
        Double func_value = new Double(chromos.getFunction());
        int count = 0;
        for (Chromosome be : Population) {
            if (func_value.equals(be.getFunction())) {
                if (count == chromos_num_upper_bound) break;
                else count++;
            }
        }
        if (count < chromos_num_upper_bound) {
            Population.add(chromos);
            return 0;
        }
        else return 1;
    }

    /** add chromosome into population with duplicate checked (Succ => truw, fail => false)*/
    private boolean SelectionAdd(List<Chromosome> p1, Chromosome c1, List<Chromosome> p2, Chromosome c2, List<Chromosome> p3, Chromosome c3 , int upperBound) {
        int cnt = 0;
        for (int i = 0; i < p1.size(); i++){
            if (cnt >= upperBound) return false;
            else if (p1.get(i).equals(c1) && p2.get(i).equals(c2) && p3.get(i).equals(c3)) cnt++;
        }

        p1.add(c1);
        p2.add(c2);
        p3.add(c3);
        return true;
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
    	
    	msearcher.localSearch(msearcher.getGene());
        bsearcher.localSearch(bsearcher.getGene());
    	jsearcher.localSearch(jsearcher.getGene());

    	double func_value = evaluation(msearcher, bsearcher, jsearcher);

        msearcher.setFunction(func_value);
        bsearcher.setFunction(func_value);
        jsearcher.setFunction(func_value);

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

    private void selectOperator(int i) throws CloneNotSupportedException {
        // 计算总适应度值
        double totalFitness = 0.0f;
        for (Chromosome chromosome : mPopulation) {
            totalFitness += chromosome.getFitness();
        }
        // 对于GA_Y，fitness=0表示找到最优可行解
        if (Double.compare(totalFitness, 0) == 0) return;

        updateChromosomeProbability(mPopulation, totalFitness);
        updateChromosomeProbability(transPopulation, totalFitness);
        updateChromosomeProbability(jPopulation, totalFitness);

        select();
    }

    private void updateChromosomeProbability(List<Chromosome> population, double totalFitness) {
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

    private void select() throws CloneNotSupportedException {
        Random rand = new Random(System.currentTimeMillis());

        List<Chromosome> newMachinePopulation = new ArrayList<Chromosome>();
        List<Chromosome> newTransPopulation = new ArrayList<Chromosome>();
        List<Chromosome> newJobPopulation = new ArrayList<Chromosome>();

        Collections.sort(mPopulation);
        Collections.sort(transPopulation);
        Collections.sort(jPopulation);

        newMachinePopulation.add(mPopulation.get(0).clone());
        newTransPopulation.add(transPopulation.get(0).clone());
        newJobPopulation.add(jPopulation.get(0).clone());

        newMachinePopulation.add(mPopulation.get(1).clone());
        newTransPopulation.add(transPopulation.get(1).clone());
        newJobPopulation.add(jPopulation.get(1).clone());

        int reserved_size = (int) (POPULATION_SIZE * RESERVEDPROBABLITY);
        int count = 0;
        while (newMachinePopulation.size() < reserved_size) {
            System.out.println("");
            double prob = rand.nextDouble();
            double sum = 0.0;
            for (int j = 0; j < mPopulation.size(); j++) {
                sum += mPopulation.get(j).getProb();

                if (sum > prob) {
                    try {
                        //check duplicate，no more than 5 in newpopulation
                        if (count < 5)
                            if (SelectionAdd(newMachinePopulation, mPopulation.get(j).clone(), newTransPopulation, transPopulation.get(j).clone(), newJobPopulation, jPopulation.get(j).clone(), 5)) count++;
                            else count = 0;
                        else{
                            count = 0;
                            newMachinePopulation.add(mPopulation.get(j).clone());
                            newTransPopulation.add(transPopulation.get(j).clone());
                            newJobPopulation.add(jPopulation.get(j).clone());
                        }

                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        mPopulation.clear();
        transPopulation.clear();
        jPopulation.clear();

        mPopulation.addAll(newMachinePopulation);
        transPopulation.addAll(newTransPopulation);
        jPopulation.addAll(newJobPopulation);
    }

    /******************************交换操作************************/
    private void crossOverOperator() throws CloneNotSupportedException {

        GGAcross(mPopulation);
        GGAcross(transPopulation);
        GGAcross(jPopulation);
    }

    private void crossOverOperator(int i) throws CloneNotSupportedException {

       while(mPopulation.size() < POPULATION_SIZE){
            int first = rand.nextInt((int) (POPULATION_SIZE * RESERVEDPROBABLITY));
            int second = rand.nextInt((int) (POPULATION_SIZE * RESERVEDPROBABLITY));
            int third = rand.nextInt((int) (POPULATION_SIZE * RESERVEDPROBABLITY));

            if(rand.nextDouble() < CROSSOVER_PROBABILITY){
                /** int temp: to test they should have same behaviors */
                int temp = GGAcross(first, second, third, mPopulation);
                if (temp != GGAcross(first, second, third, transPopulation)) System.out.println("WTF!!!!!!!!!!???????");
                if (temp != GGAcross(first, second, third, jPopulation)) System.out.println("WTF!!!!!!!!!!!???????");
            }
        }
    }

    private int GGAcross(int first, int second, int third, List<Chromosome> population) throws CloneNotSupportedException {
        Chromosome one, two, three;

        one = population.get(first).clone();
        two = population.get(second).clone();
        three = population.get(third).clone();
        three.ClearRule();

        int pos1 = rand.nextInt(one.RuleSize());
        int pos2 = rand.nextInt(one.RuleSize());
        int pos3 = rand.nextInt(two.RuleSize());
        int pos4 = rand.nextInt(two.RuleSize());
        if (pos2 < pos1) { int temp = pos1; pos1 = pos2; pos2 = temp; }
        if (pos4 < pos3) { int temp = pos3; pos3 = pos4; pos4 = temp; }

        for (int index = 0; index < one.size(); index++) {

             if (one.get(index) >= pos1 && one.get(index) <= pos2) three.set(index, one.get(index) - pos1);
              else if (two.get(index) >= pos3 && two.get(index) <= pos4) three.set(index, two.get(index) + pos2 - pos1 + 1 - pos3);
              else three.set(index, rand.nextInt(pos2 - pos1 + pos4 - pos3 + 1));
        }

        for (int index = pos1; index <= pos2; index++) {
            three.AddRule(one.GetRuleNo(index));
        }
        for (int index = pos3; index <= pos4; index++) {
            three.AddRule(two.GetRuleNo(index));
        }

        int cnt = 0;
        if (POPULATION_SIZE - population.size() >= 1) { population.add(three); cnt++; }
        if (POPULATION_SIZE - population.size() >= 1) { population.add(one); cnt++; }
        if (POPULATION_SIZE - population.size() >= 1) { population.add(two); cnt++; }

        return cnt;
    }

    /** 2-point crossover*/
    private void GGAcross(List<Chromosome> population) throws CloneNotSupportedException {
        for (int count = 0; count < POPULATION_SIZE - POPULATION_SIZE * RESERVEDPROBABLITY; ) {
            Chromosome one, two, three;

            one = population.get(rand.nextInt((int) (POPULATION_SIZE * RESERVEDPROBABLITY))).clone();
            two = population.get(rand.nextInt((int) (POPULATION_SIZE * RESERVEDPROBABLITY))).clone();
            three = population.get(rand.nextInt((int) (POPULATION_SIZE * RESERVEDPROBABLITY))).clone();

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

    /** 计算一共有多少工序 */
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
            /** mutation */
            if (rand.nextDouble() <= MUTATION_PROBABILITY) {
                if (rand.nextDouble() < 0.5) {
                    GGAsplit(chromosome, 'm'); // 分裂 (决策块++)
                } else {
                    if (chromosome.RuleSize() == 1) continue;
                    GGAmerging(chromosome); // 合并 (决策块--1)
                }

            }
        }

        for (Chromosome chromosome : transPopulation) {
            if (rand.nextDouble() <= MUTATION_PROBABILITY) {
                if (rand.nextDouble() < 0.5) {
                    GGAsplit(chromosome, 'b');
                } else {
                    if (chromosome.RuleSize() == 1) continue;
                    GGAmerging(chromosome);
                }

            }
        }
        for (Chromosome chromosome : jPopulation) {
            if (rand.nextDouble() <= MUTATION_PROBABILITY) {
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
            if (Chromos.get(i) == select && rand.nextDouble() < 0.5) Chromos.set(i, Chromos.RuleSize());
        }

        if (c == 'm') Chromos.AddRule(rand.nextInt(Constants.mRules.length));
        if (c == 'b') Chromos.AddRule(rand.nextInt(Constants.bRules.length));
        if (c == 'j') Chromos.AddRule(rand.nextInt(Constants.jRules.length));

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
            if (Chromos.get(i) == delete) Chromos.set(i, merge);
            if (Chromos.get(i) > delete) Chromos.set(i, Chromos.get(i) - 1);
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

    public void setProblemSize(MachineSet ms, JobSet js, CellSet cs) {
        MachineNum = ms.size();
        JobNum = js.size();
        CellNum = cs.size();
        this.ms = ms;
        this.js = js;
        this.cs = cs;
    }
}

