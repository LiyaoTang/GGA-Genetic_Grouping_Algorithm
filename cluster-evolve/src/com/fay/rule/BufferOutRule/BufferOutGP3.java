package com.fay.rule.BufferOutRule;

import com.fay.domain.Cell;
import com.fay.domain.Job;
import com.fay.domain.Machine;
import com.fay.domain.Operation;
import com.fay.rule.GPRuleBase;
import com.fay.scheduler.SimpleScheduler;

import ec.gp.GPIndividual;

public class BufferOutGP3 extends GPRuleBase implements IBufferOutRule{

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
    	
        double result =  Sub (Add( DD, W) ,Div( TT, Mul (Add (Sub (Sub
        	     (Mul( W ,W), 0.004323499668245523) ,OP), Div
        	    	    ( W ,W)), Add (Add (Div( W, W), OP) ,Mul (Mul( W
        	    	     ,W) ,Add (Add (Sub (Add( W, Sub (Sub (Add (Add
        	    	     (W, DD), Div( W ,W)), OP), OP)), OP) ,OP), Add (Add
        	    	     (Add (Sub (Add( W, Sub (Mul( W ,W), OP)), OP),
        	    	         OP) ,Add (Add( W, Add( W ,Sub( W ,Mul( W, W)))),
        	    	     Div (Div( W ,OP), Mul (0.004323499668245523,
        	    	         0.004323499668245523)))),Add (Add (Sub (Sub
        	    	    ( W ,OP), OP), OP), Add (Add (W ,Add (Add (Sub
        	    	     (Mul( W, W), 0.004323499668245523) ,Add (Sub
        	    	     (Mul (W ,W), 0.004323499668245523), Sub( W ,OP))),
        	    	     Add (Sub( W ,OP), Div( W, W)))), Div (Sub (Mul
        	    	     (W, W) ,0.004323499668245523), Add (Add (Sub
        	    	     (Mul( W, W), 0.004323499668245523) ,Sub( W ,OP)),
        	    	     Add (Sub (Mul( W, W), 0.004323499668245523),
        	    	         Sub( W ,OP)))))))))))));
        return result;
    }	

}
