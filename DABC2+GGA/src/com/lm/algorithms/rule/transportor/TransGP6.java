package com.lm.algorithms.rule.transportor;

import java.util.List;

import com.lm.domain.Entity;
import com.lm.domain.Machine;
import com.lm.domain.Operation;
import com.lm.util.Timer;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class TransGP6 extends GPRuleBase implements ITransportorRule {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public double calPrio(Operation e,int CurCellID,int NextCellID){

   	 List<Machine> a=e.getProcessMachineList();
	   	int MachineIndex=0;
	    while(a.get(MachineIndex).getCellID()!=NextCellID){
	    	MachineIndex++;
	    }
	    Machine m = a.get(MachineIndex);
	    double PT=e.getProcessingTime(m);
	    double DD=e.getDueDate();
	    double RT=e.getRelDate();
	    double IT=m.getNextIdleTime()-Timer.currentTime();
    return 
    		 Add(
    					Sub(
    						Add(
    							Mul(
    								Mul(
    									0.2907465516007708
    									,
    									PT
    									)
    								,
    								Div( 
    									DD
    									,
    									RT)
    								)
    							,
    							Sub( 
    								Sub(
    									DD
    									,
    									PT
    									)
    								,
    								Mul(
    									RT
    									,
    									0.2907465516007708
    									)
    								)
    							)
    						,
    						Sub(
    							Mul(
    								Add(
    									PT,
    									0.2907465516007708
    									)
    								,
    								Div(
    									0.2907465516007708
    									,
    									0.2907465516007708
    									)
    								)
    							,
    							Sub(
    								Sub(
    									DD 
    									,
    									0.2907465516007708
    									)
    								,
    								Sub( 
    									PT
    									,
    									0.2907465516007708
    									)
    								)
    							)
    						)
    					,
    					Add( 
    						Add(
    							Add(
    								Sub(
    									DD
    									,
    									RT
    									)
    								,
    								Div(
    									RT
    									,
    									0.2907465516007708
    									)
    								)
    							,
    							Add(
    								Sub(
    									PT
    									,
    									DD
    									)
    								,
    								Add(
    									DD
    									,
    									0.2907465516007708
    									)
    								)
    							)
    						,
    						Add(
    							Sub(
    								Mul(
    									0.2907465516007708
    									,
    									PT
    									)
    								,
    								Mul(
    									PT
    									,
    									PT
    									)
    								)
    							,
    							Sub( 
    								Add(
    									DD
    									,
    									0.2907465516007708
    									)
    								,
    								Div( 
    									RT
    									,
    									DD
    									)
    								)
    							)
    						)
    					)
    		;
  }
}
