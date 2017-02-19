package com.fay.rule.TimeWindowRule;

import com.fay.domain.Cell;
import com.fay.domain.Job;
import com.fay.domain.Machine;
import com.fay.domain.Operation;
import com.fay.rule.GPRuleBase;
import com.fay.rule.MachineRule.IMachineRule;
import com.fay.scheduler.SimpleScheduler;
import com.fay.util.Timer;

import ec.gp.GPIndividual;

public class TimeWindowGP3 extends GPRuleBase implements ITimeWindowRule{
	 @Override
	 public String toString() {
	        return getClass().getSimpleName();
	    }

	    public double calPrio(Cell cell) {
	    	 // 端点变量赋�?
	    	 
	        double BN ;
	        double NDD;
	        double NW;
	        double NRT;
	        double SW;
	        double SD;
	        double SRT;
	        double CN;
	        double CSW;
	        double CSD;
	        double CSRT;
	        double NMRT;
	        double NRET;
	    	
	        BN = cell.getBufferOut().size();
	        if(cell.getNextReadyMachine() == null)	NDD = 1000;
	        else
	        	NDD = cell.getNextReadyMachine().getProcessingOperation().getJob().getDuedate();
	        if(cell.getNextReadyMachine() == null)	NRT = 0;
	        else
	        	NRT = cell.getNextReadyMachine().getNextIdleTime() - Timer.currentTime();
	        if(cell.getNextReadyMachine() == null)	NW = 0;
	        else
	        	NW = cell.getNextReadyMachine().getProcessingOperation().getJob().getWeight();
	        if(cell.getNextReadyMachine() == null)	NRET = 0; 
	        else
	        	NRET = cell.getNextReadyMachine().getProcessingOperation().getJob().getDuedate() - Timer.currentTime();
	        
	        int sumWeight = 0,sumDueDate = 0,sumRemainTime = 0;
	        for(Operation operation : cell.GetVehicle().getTransingOperation()){
	        	sumWeight +=operation.getJob().getWeight();
	        	sumDueDate +=operation.getJob().getDuedate();
	        	sumRemainTime += operation.getJob().getDuedate() - Timer.currentTime();
	        }
	        SW = sumWeight;
	        SD = sumDueDate;
	        SRT = sumRemainTime;
	        
  	CN = cell.getProcessingMachine().size();
	        
	        int currentSumWeight = 0,currentSumDueDate = 0,currentSumRemainTime = 0,maxReadyTime = 0;
	        if(cell.getProcessingMachine().size() == 0){
		        CSW = 0;
		        CSD = 0;
		        CSRT = 0;
		        NMRT = 0;
	        }
	        else{
	        	for(Machine machine : cell.getProcessingMachine()){		
	        		currentSumWeight +=machine.getProcessingOperation().getJob().getWeight();
	        		currentSumDueDate +=machine.getProcessingOperation().getJob().getDuedate();
	        		currentSumRemainTime +=machine.getProcessingOperation().getJob().getDuedate() - Timer.currentTime();
	        		if(machine.getNextIdleTime() - Timer.currentTime() > maxReadyTime)
	        			maxReadyTime = machine.getNextIdleTime() - Timer.currentTime();
	        	}
	        	CSW = currentSumWeight;
	        	CSD = currentSumDueDate;
	        	CSRT = currentSumRemainTime;
	        	NMRT = maxReadyTime;
	        }

	        double result =  Add (Add (Div (Add (NMRT, NDD) ,Div( NW, NDD)),
   			     Div (Div (Add (Div (Add (Div (Add (NMRT ,NDD),
    			         Div (NW, NDD)) ,Mul (NDD ,Mul (Sub (Div (Mul
    			        ( NMRT, NDD), Div (NRET ,Mul (Mul (Mul (NMRT, NDD),
    			         Mul (Mul( NMRT, NDD), NDD)), Mul (NW ,Div (NW,
    			         NDD))))), Div (CSD ,SW)), NDD))) ,Div (Mul (Mul
    			         (Add (Div (NW ,NDD), NW), Mul( NW, NDD)), Div
    			         (Div (NRET, Mul( NMRT, NDD)), Sub (Div (Sub
    			         (Mul (NMRT,NDD) ,Div( CSD, SW)), NW), NRT))), Add
    			         (Sub (BN, CSW) ,Sub (Div (Mul( NMRT, NDD), Div
    			         (NRET ,Mul (Mul (Mul( NMRT, NDD), Mul( NW ,NDD)),
    			         Mul (NW, NDD)))), Div (CSD, SW))))), Div (Mul
    			         (Mul (Add (Div (NW ,NDD), NW), Mul( NW, NDD)),
    			         Mul( NW ,NDD)), Mul( NMRT, NDD))), Add (Sub
    			        ( BN, CSW), Add( SRT, SD))), Add (Sub (BN ,CSW),
    			        Add  (SRT ,Add (Sub (BN ,CSW), Div (Add (SRT,
    			             Add (Sub( BN, CSW) ,Mul( NMRT, NDD))), Sub (Div
    			             (Add( NMRT ,Add (Add (Div (Add (NMRT, NDD),Div
    			                  (NW ,NDD)), CSW) ,Mul (Add (Div (Add( NMRT ,NDD),
    			                 Div( NW ,NDD)), NW), Sub( NRT, NRT)))), NW) ,NRT))))))),
    			     Div (NRET, Mul( NMRT, NDD)));
	        if(result < 0)  result = 0;
	        	
	        if(result > 100)
	        	result = 100;
	        return result;
	    }

}
