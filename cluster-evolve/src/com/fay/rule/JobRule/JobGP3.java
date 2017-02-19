package com.fay.rule.JobRule;

import com.fay.domain.Machine;
import com.fay.domain.Operation;
import com.fay.rule.GPRuleBase;
import com.fay.scheduler.SimpleScheduler;
import com.fay.util.Timer;

import ec.gp.GPIndividual;

public class JobGP3 extends GPRuleBase implements IJobRule{

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    double	BN;
    double	DD;
    double	PT;
    double	TT;
    double	VRT;
    double	WT;
    
    public double calPrio(Operation operation, Machine m) {
    	 // 端点变量赋�?
        DD = operation.getJob().getDuedate();
        PT = operation.getProcessingTime(m);
        if(operation.getJob().getNextScheduleNo() == 1)
        	TT = 0;
        else TT = operation.getJob().getCurrentOperation().getSelectedMachine().getCell()
        												.getTransferTime(m.getNumInCell());
        if(m.getCell().GetVehicle().getBackTime() == 0)
        	VRT = 0;
        else
        	VRT = m.getCell().GetVehicle().getBackTime() - Timer.currentTime();
        if(m.getNextIdleTime() == 0)
        	WT = 0;
        else 
        	WT = m.getNextIdleTime() - Timer.currentTime();
        BN = m.getBufferIn().size();
        
        
        double result =  Sub (Div (Sub (Sub (Sub( BN, Add (VRT, WT)),
        	     Div (Add (Sub (Add (DD ,BN) ,Add( BN, TT)),Sub
        	             (Sub ( DD, TT), Div (Sub (Sub (Sub( 0.27916365837418466,
        	             WT), Add (VRT, WT)), Sub (Div (0.27916365837418466,
        	             WT), Div( WT ,PT))), Add (TT ,DD)))), Mul (VRT,
        	             Mul (Mul (WT ,BN), Sub (TT ,DD))))) ,Mul (Add
        	         (Mul (TT, BN), PT), BN)), TT), TT);
        return result;
    }	
}
