package com.lm.algorithms.rule.transportor;

import java.util.List;

import com.lm.domain.Entity;
import com.lm.domain.Machine;
import com.lm.domain.Operation;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class TransGP1 extends GPRuleBase implements ITransportorRule {

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
   	double PT=e.getProcessingTime(a.get(MachineIndex));
    double DD=e.getDueDate();
    double RT=e.getRelDate();
    return 
    		Div(
    				Div(
    					PT,
    					0.1538598425459664
    					)
    				,
    				Add( 
    					Add(
    						PT
    						,
    						PT
    						)
    					,
    					Sub(
    						Sub(
    							Add(
    								RT
    								,
    								0.1538598425459664
    								)
    							,
    							Mul(
    								0.1538598425459664
    								,
    								RT
    								)
    							)
    						,
    						Mul(
    							RT
    							,
    							RT)
    						)
    					)
    				)
;
  }
}
