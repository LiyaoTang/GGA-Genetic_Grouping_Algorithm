package com.fay.rule.BufferOutRule;

import com.fay.domain.Cell;
import com.fay.domain.Job;
import com.fay.domain.Machine;
import com.fay.domain.Operation;
import com.fay.rule.GPRuleBase;
import com.fay.scheduler.SimpleScheduler;

import ec.gp.GPIndividual;

public class BufferOutGP1 extends GPRuleBase implements IBufferOutRule{

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
    double	DD;
    double	W;
    double	TT;
    double	PT;
    double	OP;
    double	WT;
    double  AT;
    
    public double calPrio(Cell cell,Operation operation) {
    	 // 端点变量赋�?
        DD = operation.getJob().getDuedate();
        W = operation .getJob().getWeight();
        TT = cell.getTransferTime(operation.getSelectedMachine().getNumInCell());
        PT = operation.getProcessingTime();
        OP = operation.getJob().getRemainOpNumber();
        WT = operation.getSelectedMachine().getNextIdleTime();
        AT = operation.getArrivalBufferOutTime();
    	
        double result =    Add( 0.004323499668245523 ,Sub (Add (Sub
        	     (Div (Sub (Mul( W, W) ,OP), TT), Div (W ,W)), Div
        	    	     (Sub (DD ,OP), Mul (Add( W ,W), Mul( 0.004323499668245523
        	    	    , W)))), W));
        return result;
    }	

}
