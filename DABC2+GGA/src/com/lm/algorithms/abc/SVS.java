package com.lm.algorithms.abc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import com.lm.Metadomain.CellSet;
import com.lm.Metadomain.Job;
import com.lm.Metadomain.JobSet;
import com.lm.Metadomain.Machine;
import com.lm.Metadomain.MachineSet;
import com.lm.Metadomain.Operation;
import com.lm.algorithms.AbstractMetaScheduler;
import com.lm.algorithms.measure.MetaIMeasurance;
import com.lm.util.Constants;
import com.lm.util.MapUtil;

/**
 * @author lm
 *
 * EM-like 方法的比对
 */
public class SVS {
/*******************************************属性域*******************************************************/
	/**针对具体问题的调度过程**/
	protected AbstractMetaScheduler evaluator;
	/**适应度评估方法**/
	protected MetaIMeasurance measurance ;
	/**用于随机的种子**/
	protected Random rand = new Random(System.currentTimeMillis());
	
    /**染色体序列**/
	protected Chromosome bestChromosome;
	
	protected ArrayList<Integer> [] MSegment;
	
	/**Intracell--每个单元各工件加工时间的和 **/
	protected Map<Integer, Integer> [] Tp;
	/**Intercell--每两个单元连起来加工的 makespan和**/
	protected int [][] Sita;
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
	public SVS(MachineSet mSet, JobSet jSet, CellSet cellSet,
			AbstractMetaScheduler scheduler, MetaIMeasurance measurance) {
		this.mSet = mSet;
		this.jSet = jSet;
		this.cellSet = cellSet;
		this.measurance = measurance;
		this.evaluator  = scheduler;
		this.bestChromosome =  new Chromosome(mSet.size(),cellSet.size());
		this.Sita = new int[this.cellSet.size()+1][this.cellSet.size()+1];

		this.Tp   = new Map[this.cellSet.size()+1];
		for (int index = 0; index < Tp.length; index ++) {
			Tp[index] = new HashMap<Integer, Integer>();
		}
		
		this.MSegment   = new ArrayList[mSet.size()+1];
		for (int index = 0; index < MSegment.length; index ++) {
			MSegment[index] = new ArrayList<Integer>();
		}
	}
	
	/** MAIN PROCESS for EM
	 * @throws CloneNotSupportedException 
	 * @Description framework for DABC
	 * 
	 */
	public void schedule() throws CloneNotSupportedException, IOException{
		
		intra_cell();
		
		/**因为新的模型当中固定了加工顺序，所以就没必要考虑inter_cell了**/
//		inter_cell();

		/**根据调度结果，把它transfer 成一个编码**/
		updateChromosome();
	
		evaluation(bestChromosome);
	}

	/**
	 * 对于单元内的part，根据确定的工件加工顺序。
	 */
	private void intra_cell() {

		for (Job job : jSet) {
			// add operate time
			for(int OpIndex = 0; OpIndex < job.getOperationNum(); OpIndex++) {
				Operation op = job.getOperation(OpIndex);
				/**因为 现在都是非柔性模型，所以List里面的第一个机器应该就是当前加工机器**/
				Machine m =op.getProcessMachineList().get(0);
				Tp[m.getCellID()].put(job.getId(), job.getOperation(OpIndex).getProcTimes().get(m));
			}
		} 
		
		// add parts to cell
		for(int cellIndex = 1; cellIndex <= cellSet.size(); cellIndex++) {
			//sorted -- test whether it sorts as descend 
			Tp[cellIndex] = MapUtil.sortByValue(Tp[cellIndex]);

			//cell result initial
			Set<Integer> parts= Tp[cellIndex].keySet();
			int [] CellOpResult = new int[parts.size()];
			Vector<Integer> partsSequences = new Vector<Integer>();
			for (Integer p : parts) {
				partsSequences.add(p);
			}
			
			//deal with first part 
			CellOpResult[0]     = partsSequences.get(0);
			//deal with second part 
			if(parts.size() >=2){
				CellOpResult[1] = partsSequences.get(1);
				//deal with remaining parts
				if(parts.size() >=3){

					int iter_time = 2;
					int []tmp = CellOpResult.clone();
					do{
						double min = Double.MAX_VALUE;
						int pos = 0;
						//foreach pos in the array
						for(int i = 0; i <= iter_time; i++) {
//							System.out.println(partsSequences.get(iter_time));
							double partMakespan = EvaForIntraCell(tmp.clone(),iter_time,i,partsSequences.get(iter_time),cellIndex);
							if(partMakespan < min) {
								min = partMakespan;
								pos = i;
							}
						}

						// update the sequence of parts for the cell
						tmp = insertInIndex(tmp.clone(), iter_time, pos, partsSequences.get(iter_time));

					}while(++iter_time < parts.size());

					CellOpResult = tmp.clone();
				}
			}

			//transfer the sequence message into the chromosome
			ChangeChromos(CellOpResult,cellIndex);

		}//end of cellIndex
		
		UpdateChromos();
	}

	/**
	 * update chromos using the arrayList[mSize] MSegment
	 */
	private void UpdateChromos() {
		
		for (int index = 0; index < mSet.size(); index++) {
			int mId = mSet.get(index).getId();
			
			int[] MSeg = new int[Constants.MachineToParts[mId].length+1];
			for (int i = 1; i < MSegment[mId].size(); i++) {
				MSeg[i] = MSegment[mId].get(i);
			}
            bestChromosome.setMachineSegment(mId, MSeg);
		}
	}

	/**
	 * according to the array CellOpResult to modify the chromos 
	 * @param cellOpResult
	 */
	private void ChangeChromos(int[] cellOpResult, int cellIndex) {
		
		for (int jIndex = 0 ; jIndex < cellOpResult.length; jIndex++) {
			Job j = jSet.get(cellOpResult[jIndex]-1);
			
			//find operators needed to be done in cellIndex
			for(int opIndex = 0; opIndex < j.getOperationNum(); opIndex++) {
				Operation op = j.getOperation(opIndex);
				Machine m    = op.getProcessMachineList().get(0);
				if(m.getCellID()== cellIndex) {
					MSegment[m.getId()].add(cellOpResult[jIndex]); 
				}
			}
		}
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
	 * insert element into the i-th position in tmp
	 * @param tmp
	 * @param i
	 * @return
	 */
	private double EvaForIntraCell(int[] tmp, int tmp_size, int insertIndex, int element, int cellIndex) {
		
		tmp = insertInIndex(tmp, tmp_size, insertIndex,element);
		
		// Simulate the operation process for parts in the Cell(cellIndex) 
		int []MEndTime = new int[mSet.size()+1];
		
		for (int jobIndex = 0; jobIndex <= tmp_size; jobIndex++) {
			Job j = jSet.get(tmp[jobIndex] - 1);
			int PreOpTime = 0;
			
			//find operators needed to be done in cellIndex
			for(int opIndex = 0; opIndex < j.getOperationNum(); opIndex++) {
				Operation op = j.getOperation(opIndex);
				Machine m    = op.getProcessMachineList().get(0);
				if(m.getCellID()== cellIndex) {
					MEndTime[m.getId()] = Math.max(MEndTime[m.getId()], PreOpTime)+op.getProcessingTime(m); 
					PreOpTime = MEndTime[m.getId()];
				}
			}
		}
		
		//find the makespan of the parts
		int max = 0;
		for (int i = 0; i < MEndTime.length; i++) {
			if( MEndTime[i] > max ) {
				max = MEndTime[i];
			}
		}

		return max;
}


	/**初始化编码对象
	 * @throws CloneNotSupportedException
	 */
	private void updateChromosome() throws CloneNotSupportedException {                        
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
	 * @Description evaluation process for SVS-based heuristics
	 * @param trans_chromosome chromosome for trans part 
	 * @param m_chromosome chromosome for machine part
	 */
	protected double evaluation(Chromosome chromosome) {
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
		    
//			evaluator.schedule();
			evaluator.scheduleWithStrategy(chromosome);
	
			return measurance.getMeasurance(evaluator);
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
	 * get best Function from bestChromosome
	 * @return
	 */
	public double getBestFunctionValue() {
		return bestChromosome.getFunction();
	}
}
