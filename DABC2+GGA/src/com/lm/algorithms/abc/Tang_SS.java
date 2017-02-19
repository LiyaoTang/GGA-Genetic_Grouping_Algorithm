package com.lm.algorithms.abc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.lm.Metadomain.CellSet;
import com.lm.Metadomain.Job;
import com.lm.Metadomain.JobSet;
import com.lm.Metadomain.Machine;
import com.lm.Metadomain.MachineSet;
import com.lm.Metadomain.Operation;
import com.lm.algorithms.AbstractMetaScheduler;
import com.lm.algorithms.encode.OpEncode;
import com.lm.algorithms.abc.Chromosome;
import com.lm.algorithms.measure.MetaIMeasurance;
import com.lm.util.Constants;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

/**
 * @author lm
 *
 * Tang老师SS方法 的比对
 */
public class Tang_SS {
/*******************************************属性域*******************************************************/
	/**针对具体问题的调度过程**/
	protected AbstractMetaScheduler evaluator;
	/**适应度评估方法**/
	protected MetaIMeasurance measurance;
	/**用于随机的种子**/
	protected Random rand = new Random(System.currentTimeMillis());
	/**最佳的适应度函数的value**/
	protected double  bestFunction = 0d;
    /**初始解集**/
	protected List<OpEncode> Population;
	/**Reference Set**/
	protected List<OpEncode> RefSet;
	/**Current Solutions for every iteration**/
	protected List<OpEncode> CurSolutions;
	/**OpEncode的长度**/
    protected static int Encodelen;
	/**RefSet大小**/
	protected static int B=48;
	/**RefSet中 高质量个体的数量**/
	protected static int B1=B/2;
	/**RefSet中 多样性个体的数量**/
	protected static int B2=B/2;
	/**初始种群的大小**/
	protected static int POP_SIZE= 2*B;
	/**当代生成小群体的大小**/
	protected static int CUR_SIZE= B;
	
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
	public Tang_SS(MachineSet mSet, JobSet jSet, CellSet cellSet,
			AbstractMetaScheduler scheduler, MetaIMeasurance measurance) {
		this.mSet    = mSet;
		this.jSet    = jSet;
		this.cellSet = cellSet;
		this.measurance   = measurance;
		this.evaluator    = scheduler;
		this.Population   = new ArrayList<OpEncode>(POP_SIZE);
		this.RefSet       = new ArrayList<OpEncode>(B);
		this.CurSolutions = new ArrayList<OpEncode>(CUR_SIZE);
		
		int opNum         = 0;
		for (int o = 0; o < jSet.size(); o ++) {
			opNum += jSet.get(o).getOperationNum();
		}
		this.Encodelen    = opNum;
	}
	
	/** MAIN PROCESS for EM
	 * @throws CloneNotSupportedException 
	 * @Description framework for DABC
	 * 
	 */
	public void schedule(int caseNo) throws CloneNotSupportedException, IOException{
		
		double[] TimeList = new double[20];
//		TimeList[0] = 3058;
//		TimeList[1] = 8783;
//		TimeList[2] = 17472;
//		TimeList[3] = 41326;
//		TimeList[4] = 68611;
//		TimeList[5] = 122568;
//		TimeList[6] = 169593;
//		TimeList[7] = 251836;
//		TimeList[8] = 319713;
//		TimeList[9] = 488111;
//		TimeList[10] = 56288;
//		TimeList[11] = 68152;
//		TimeList[12] = 82989;
//		TimeList[13] = 112063;
//		TimeList[14] = 129663;
//		TimeList[15] = 145715;
//		TimeList[16] = 175329;
//		TimeList[17] = 194241;
//		TimeList[18] = 222493;
//		TimeList[19] = 255764;

		TimeList[0] = 451;
		TimeList[1] = 651;
		TimeList[2] = 1751;
		TimeList[3] = 2091;
		TimeList[4] = 3351;
		TimeList[5] = 5812;
		TimeList[6] = 9241;
		TimeList[7] = 12426;
		TimeList[8] = 15622;
		TimeList[9] = 20927;
		TimeList[10] = 30173;
		TimeList[11] = 40521;
		TimeList[12] = 50193;
		TimeList[13] = 70686;
		TimeList[14] = 98271;
		TimeList[15] = 101495;
		TimeList[16] = 119184;
		TimeList[17] = 125231;
		TimeList[18] = 132091;
		TimeList[19] = 140022;
		

		
		InitPop();
		
		InitRefSet();
		double timer;
		double start = System.currentTimeMillis();
		while(true){
			
			CombineSolutionFromSubSet();
			
			ImproveSolution();
			UpdateRefSet();
//			if(UpdateRefSet()==true){
//				break;
//			}
			timer = System.currentTimeMillis() - start;
			if(timer > TimeList[caseNo]){
				break;
			}
		}
	}
	

	/**
	 * updating the RefSet using CurSolutions
	 * @throws CloneNotSupportedException 
	 * @throws IOException 
	 */
	private boolean UpdateRefSet() throws CloneNotSupportedException, IOException {
		boolean IsNotImproved;
		
		double min = Double.MAX_VALUE;
		OpEncode result = null;
		// find good ones from the CurSolutions
		for (OpEncode cur: CurSolutions) {
			cur.setFunc(Evaluate(cur.GetEncode()));
			if( min > cur.getFunction()) {
				min = cur.getFunction();
				result = cur;
			}
		}
		// replace the worst one
		double worstValue = -1;
		bestFunction  = min;
		OpEncode worstEncode = null;
		for (OpEncode cur: RefSet) {
			cur.setFunc(Evaluate(cur.GetEncode()));
			if( worstValue < cur.getFunction()) {
				worstValue = cur.getFunction();
				worstEncode = cur;
			}
			if(bestFunction > cur.getFunction()) {
				bestFunction = cur.getFunction();
			}
		}
		
		if(min < worstValue) {
			IsNotImproved = false;
			RefSet.remove(worstEncode);
			RefSet.add(result);
		}else{ // if replace process happen, it means the RefSet has improved, then flag it.
			IsNotImproved = true;
		}
		
		return 	IsNotImproved;
	}

	/**
	 * improve the solutions using the IM1 or IM2
	 */
	private void ImproveSolution() {
		for(OpEncode cur: CurSolutions){
//			IM1(cur);
			
			IM2(cur);
		}
	}

	/**
	 * Improve method 1:
	 * randomly insert into the encoding
	 */
	private void IM1(OpEncode cur) {
		int Ui = rand.nextInt(cur.getsize());
		int Uj = -1;
		while(true){
			Uj = rand.nextInt(cur.getsize());
			if(Uj != Ui){
				break;
			}
		}
		
		int P_Ui = cur.getPos(Ui);
		if(Ui < Uj) {
			// Ui is at the front of Uj : [Ui,Ui+1,....,Uj-1] -> [Ui+1,....,Uj-1,Ui]
			for(int index = Ui; index < Uj-1 ; index++ ) {
				int jobIndex = cur.getPos(index+1);
				cur.setPos(index,jobIndex);
			}
			cur.setPos(Uj-1, P_Ui);
		} else {
			//Ui is behind of Uj : [Uj+1,Uj+2,...,Ui] -> [Ui,Uj+1,...,Ui-1]
			for(int index = Ui; index > Uj+1 ; index-- ) {
				int jobIndex = cur.getPos(index-1);
				cur.setPos(index,jobIndex);
			}
			cur.setPos(Uj+1, P_Ui);
		}
	}


	/**
	 * Improve method 2:
	 * randomly swap two operators in the encoding
	 */
	private void IM2(OpEncode cur) {
		int Ui = rand.nextInt(cur.getsize());
		int Uj = -1;
		while(true){
			Uj = rand.nextInt(cur.getsize());
			if(Uj != Ui){
				break;
			}
		}
		
		int P_Ui = cur.getPos(Ui);
		cur.setPos(Ui, cur.getPos(Uj));
		cur.setPos(Uj, P_Ui);
	}

	
	/**
	 * Combine solutions from the generated subsets
	 * @throws CloneNotSupportedException 
	 * @throws IOException 
	 */
	private void CombineSolutionFromSubSet() throws CloneNotSupportedException, IOException {
		ArrayList<OpEncode> [] Subsets = new ArrayList[POP_SIZE];
		
		int num = 0;
		// form kinds of 2-elements subsets
		do{
			Subsets[num] = new ArrayList<OpEncode>();
			int i = rand.nextInt(RefSet.size());
			int j;
			while(true){
				j = rand.nextInt(RefSet.size());
				if(j != i){
					break;
				}
			}
			Subsets[num].add(RefSet.get(i).clone());
			Subsets[num].add(RefSet.get(j).clone());
			num++;
		}while(num <= POP_SIZE/5);
		// form kinds of 3-elements subsets
		do{
			Subsets[num] = new ArrayList<OpEncode>();
			int i = rand.nextInt(RefSet.size());
			int j;
			while(true){
				j = rand.nextInt(RefSet.size());
				if(j != i){
					break;
				}
			}
			Subsets[num].add(RefSet.get(i).clone());
			Subsets[num].add(RefSet.get(j).clone());
			//find the best solution from the Subsets and adding it
			Collections.sort(RefSet);
			Subsets[num].add(RefSet.get(0).clone());
			num++;
		}while(num <= POP_SIZE*2/5);
		// form kinds of 4-elements subsets
		do{
			Subsets[num] = new ArrayList<OpEncode>();
			int i = rand.nextInt(RefSet.size());
			int j;
			while(true){
				j = rand.nextInt(RefSet.size());
				if(j != i){
					break;
				}
			}
			int k;
			while(true){
				k = rand.nextInt(RefSet.size());
				if(k != i && k!=j){
					break;
				}
			}
			Subsets[num].add(RefSet.get(i).clone());
			Subsets[num].add(RefSet.get(j).clone());
			Subsets[num].add(RefSet.get(k).clone());
			//find the best solution from the Subsets and adding it
			Collections.sort(RefSet);
			Subsets[num].add(RefSet.get(0).clone());
			num++;
		}while(num <= POP_SIZE*4/5);		
		// form kinds of 5~b elements subsets
		do{
			Subsets[num] = new ArrayList<OpEncode>();
			int i = rand.nextInt(RefSet.size());
			int j;
			while(true){
				j = rand.nextInt(RefSet.size());
				if(j != i){
					break;
				}
			}
			int k;
			while(true){
				k = rand.nextInt(RefSet.size());
				if(k != i && k!=j){
					break;
				}
			}
			int h;
			while(true){
				h = rand.nextInt(RefSet.size());
				if(h != i && h != j && h != k){
					break;
				}
			}
			Subsets[num].add(RefSet.get(i).clone());
			Subsets[num].add(RefSet.get(j).clone());
			Subsets[num].add(RefSet.get(k).clone());
			Subsets[num].add(RefSet.get(h).clone());
			//find the best solution from the Subsets and adding it
			Collections.sort(RefSet);
			Subsets[num].add(RefSet.get(0).clone());
			num++;
		}while(num < POP_SIZE);
		
	    // combine solutions by Algorithm.4 in Tang
		while (CurSolutions.size() < CUR_SIZE) {
			
			OpEncode U = new OpEncode(Encodelen);
			
			for(int i = 1; i<= jSet.size(); i++) {
				
			  //1. randomly choose a SubSet
			  int SubsetIndex = rand.nextInt(POP_SIZE);
			  //2. determine all solutions' prob 
			  double sum = 0;
			  for (OpEncode cur : Subsets[SubsetIndex]) {
				  cur.setFunc(Evaluate(cur.GetEncode()));
				  cur.setFitness(1.0 / (1 + cur.getFunction()));	
				  sum += cur.getFitness();
			  }
			  for(OpEncode cur : Subsets[SubsetIndex]) {
				  cur.setProb(cur.getFitness()/sum);
			  }
			  //3. select a solution with roulette wheel selection
				double prob = rand.nextDouble();
				double sum_prob=0.0;
				for (OpEncode cur : Subsets[SubsetIndex]) {
					sum_prob+=cur.getProb();
					// this cur is selected 
					if (sum_prob > prob) {
				        //4. find the i-th position in the solution and put it in the final solution U
						for(int index = 0; index < cur.getsize(); index++) {
							//find the right place
							if(cur.getPos(index) == i) {
								FindNearPlace(U,index,i);
							}
						}
						break;
					}
				}
			}// end of combination
			
			CurSolutions.add(U);
		}
	}

	/**
	 * Find the nearest position 
	 * @param u
	 * @param index
	 */
	private void FindNearPlace(OpEncode u, int index, int JobIndex) {
		int FinalPlace = 0;;
		if(u.getPos(index) == 0) {
			 FinalPlace = index;
		}else {
			int depth = 1;
			
//			System.out.println(u.toString());
			
			while(true){
//				System.out.println(index+","+depth);
				if( index+depth<u.getsize() && u.getPos(index+depth)==0 ) {
					FinalPlace = index + depth;
					break;
				}
				else if(index-depth>=0 &&  u.getPos(index-depth)==0 ) {
					FinalPlace = index - depth;
					break;
				}
				depth++;
			}
		}
		u.setPos(FinalPlace, JobIndex);
	}

	/**
	 * check whether the RefSet has not improved between iteration
	 * @return
	 */
	private boolean IsNotImproved() {
		
		return false;
	}

	/**
	 * init the RefSet from the initial population size
	 * @throws CloneNotSupportedException 
	 * @throws IOException 
	 */
	private void InitRefSet() throws CloneNotSupportedException, IOException {
		SelectHighQuailtyFromPop();
		
		SelectDiversityFromPop();
	}

	/**
	 * select b2 numbers diversity from the pop
	 */
	private void SelectDiversityFromPop() {
		
		for(int i = 0; i< B2; i++) {

			// init the variable
			int maximum = 0;
			OpEncode result = null; 
			
			for(OpEncode s1: Population){
				// calculate the d(s1,s2) : s1在pop中，s2是当前RefSet当中的个体
				for( OpEncode s2: RefSet) {
					 int dis = CalDistance( s1.GetEncode() , s2.GetEncode());
					 // choose the maximum d(s1,s2) and add s1 to RefSet
					 if ( dis > maximum ) {
						 maximum = dis;
						 result  = s1;
					 }
				}
            }
			
			// update the RefSet
			Population.remove(result);
			RefSet.add(result);
			
		}
	}

	/**
	 * calculate the distance of s1 and s2
	 * @param the array of s1
	 * @param the array of s2
	 * @return the distance value
	 */
	private int CalDistance(int[] s1, int[] s2) {
		int d = 0;
		for(int i = 0; i< s1.length; i++) {
			d+= Math.sqrt(s1[i] - s2[i]);
		}
		return d;
	}

	/**
	 * select b1 numbers high quality from the pop
	 * @throws CloneNotSupportedException 
	 * @throws IOException 
	 */
	private void SelectHighQuailtyFromPop() throws CloneNotSupportedException, IOException {
		 // evaluate the Pop
		 for (OpEncode cur: Population) {
			Evaluate(cur.GetEncode());
		 }
		 // sort the min value ones and get the first b1 ones
		 Collections.sort(Population);
		 for(int i =0; i< B1; i++) {
			 RefSet.add(Population.get(i));
		 }
		 // remove the first b1 one
		 for(int i =0; i< B1; i++) {
			 Population.remove(0);
		 }
	}

	/**
	 * init the population
	 */
	private void InitPop() {
		
//		RGM();
		
		PGM();
	}

	/**
	 * permutation generation method
	 */
	private void PGM() {
		while ( Population.size() <= POP_SIZE ) {
			OpEncode s = new OpEncode(RandomPriors()); 
			
			int M  = rand.nextInt(Encodelen/2 -3) +3;
			int N2 = Encodelen/M;
			
			for(int j = 1; j<= N2; j++) {
				int [] Pj  = GeneratePj(Encodelen,j);
				int [] LPj = GenerateLPj(Encodelen,s.GetEncode(),Pj);
				
				if (Population.size() <= POP_SIZE) {
					Population.add(new OpEncode(LPj));
				} else {
					return;
				}
			}
		}
	}

	/**
	 * Generating L(s|P(j)) 
	 * @param encodelen
	 * @param s
	 * @param pj
	 * @return
	 */
	private int[] GenerateLPj(int encodelen, int[] s, int[] pj) {
		int [] LPj = new int[encodelen];
		
		// using []s, []pj to get LPj
		for(int i = 0; i < encodelen; i++) {
			LPj[i] = s[ pj[i]-1 ];
		}
		
		return LPj;
	}

	/**
	 * Generating Pj for PGM
	 * @param encodelen2
	 * @param j
	 * @return
	 */
	private int[] GeneratePj(int encodelen, int h) {
		
		int [] Pj = new int[encodelen];
		
		//permutation P(h) = (P(h:h), P(h:h-1),..., P(h:1))
		int flag = 0;
		for(int s = h; s >= 1; s--) {
			for(int r = 0; r <= (encodelen - s)/h; r++) {
				Pj[flag++] =s+r*h;
			}
		}
		
		return Pj;
	}

	/**
	 * randomly generated method
	 */
	private void RGM() {
		// random the source
 		while ( Population.size() <= POP_SIZE ) {
			OpEncode cur = new OpEncode(Encodelen); 
			cur.setencode(RandomPriors());

			Population.add(cur);
		}
		
	}

	/**初始化编码对象
	 * @throws CloneNotSupportedException
	 * @throws IOException 
	 */
	private void InitChromosome(Chromosome bestChromosome) throws CloneNotSupportedException, IOException {                        
        	//随机产生
			for (int index = 0; index <mSet.size()+1; index++) {                      
                 bestChromosome.setMachineSegment(index, RandomPriors(Constants.MachineToParts[index]));
			}
			                                                                      
			for (int SourceIndex = 0; SourceIndex <cellSet.size()+1; SourceIndex++) {
				int[] VehicleCellSquence = RandomPriors(Constants.CellToNextCells[SourceIndex]);
				bestChromosome.setVehicleSegment(SourceIndex,VehicleCellSquence);
				
				for (int TargetIndex = 0; TargetIndex <VehicleCellSquence.length; TargetIndex++){
					int TargetCell = VehicleCellSquence[TargetIndex];
					
			        //Arraylist<Integer>转换为数组
			        if(Constants.CellToParts[SourceIndex][TargetCell]!=null){
			        	if(Constants.CellToParts[SourceIndex][TargetCell].size()!=0){
			        		 int[] temp = new int [Constants.CellToParts[SourceIndex][TargetCell].size()]; 
			        		 int k=0;
			        		 for(int o :Constants.CellToParts[SourceIndex][TargetCell] ){
                                    temp[k]=o;
                                    k++;
						     }
			        		 bestChromosome.setPartSequence(SourceIndex,TargetCell,RandomPriors(temp));  
			        	}		
			        }			       			    			         
				}
			}
			
			double func_value = evaluation(bestChromosome);
			bestChromosome.setFunction(func_value);
					
	}	
	
	/**
	 * @Description randomly rearrange the order of the array source
	 * @param source the source array
	 * @return
	 */
	private int[] RandomPriors(int[] source) { 
		
		if(source!=null){
			if(source.length!=0){
				for (int i = 1; i <source.length; i++) {
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
	 * according to the array CellOpResult to modify the chromos, and evaluate using chromos
	 * @param cellOpResult
	 * @throws CloneNotSupportedException 
	 * @throws IOException 
	 */
	private double Evaluate(int[] curEncode) throws CloneNotSupportedException, IOException {
		Chromosome result = new Chromosome(mSet.size(),jSet.size());
		InitChromosome(result);
		
		ArrayList<Integer> []MSegment   = new ArrayList[mSet.size()+1];
		for (int index = 0; index < MSegment.length; index ++) {
			MSegment[index] = new ArrayList<Integer>();
		}
		
		//transfer the []curEncode to chromosome
		int[] JobOpFlag = new int[jSet.size()+1];
		for(int job = 0; job < JobOpFlag.length; job++) {
			JobOpFlag[job] = 1;
		}
		for (int i = 0; i < curEncode.length; i++) {
			int jobIndex = curEncode[i];
			Job j        = jSet.get(jobIndex - 1);
			int opPos    = JobOpFlag[jobIndex];
			JobOpFlag[jobIndex]++;
			
			//find operators needed to be done in cellIndex
			for(int opIndex = 0; opIndex < j.getOperationNum(); opIndex++) {
				Operation op = j.getOperation(opIndex);
				Machine m    = op.getProcessMachineList().get(0);
				if( !MSegment[m.getId()].contains(jobIndex) ) {
						MSegment[m.getId()].add(jobIndex);
				}
			}
		}
		
		//using MSemgent to update the chromos
		for (int index = 0; index < mSet.size(); index++) {
			int mId = mSet.get(index).getId();
			
			int[] MSeg = new int[Constants.MachineToParts[mId].length+1];
			for (int i = 1; i <= MSegment[mId].size(); i++) {
				MSeg[i] = MSegment[mId].get(i-1);
			}
			result.setMachineSegment(mId, MSeg);
		}

		return evaluation(result);
	}

	/**
	 * insert element into the pos-th position in tmp
	 * @param tmp
	 * @param pos
	 * @return
	 */
	private int[] insertInIndex(int[] tmp, int tmp_size, int pos, int element) {
		// Backward one position for tmp, get the insert pos out
		for (int j = tmp_size-1 ; j >= pos; j--) {
			 tmp[j+1] = tmp [j];			 
		}
		
		// Insert the element into the insert position
		tmp[pos] = element;
		
		return tmp;
	}
	
	/**
	 * @Description evaluation process for SVS-based heuristics
	 * @param trans_chromosome chromosome for trans part 
	 * @param m_chromosome chromosome for machine part
	 * @throws IOException 
	 */
	protected double evaluation(Chromosome chromosome) throws IOException {
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
		    	for(int j=0;j<chromosome.VehicleSegment[i+1].length-1;j++){
		    		temp[j]=chromosome.VehicleSegment[i+1][j+1];
		    	}
		    	cellSet.get(i).setPriorSequence(temp);
		    	cellSet.get(i).setIntercellPartSequences(chromosome.IntercellPartSequences[i+1]);
		    }
		    
			evaluator.schedule();
//			evaluator.scheduleWithStrategy(chromosome);
	
			return measurance.getMeasurance(evaluator);
	}
	
	/**
	 * @Description randomly rearrange the order of the array source
	 * @param source the source array
	 * @return
	 */
	private int[] RandomPriors() { 
		//init the source
		int[] source = new int[Encodelen];
		int flag = 0;
		for (int i = 0; i < jSet.size(); i++) {
			for(int j = 0; j < jSet.get(i).getOperationNum(); j++) {
				source[flag++] = i+1 ;
			}
		}
		
		if(source!=null){
			if(source.length!=0){
				for (int i = 1; i <source.length; i++) {
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

	public double getBestFunctionValue() {
		return bestFunction;
	}

}
