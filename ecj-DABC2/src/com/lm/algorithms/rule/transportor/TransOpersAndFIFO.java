package com.lm.algorithms.rule.transportor;


import com.lm.domain.Operation;
import com.lm.util.Constants;
/** TransOpersAndFIFO : DueDate+timeForCell*/
public class TransOpersAndFIFO implements ITransportorRule {
    
	public double calPrio(Operation e,int CurCellID,int NextCellID){
        /**单元间转移时间**/
    	double timeForCell=Constants.transferTime[CurCellID][NextCellID];
    	return -e.getDueDate()-timeForCell;
    }
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
