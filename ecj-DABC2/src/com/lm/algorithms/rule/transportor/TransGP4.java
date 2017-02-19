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
public class TransGP4 extends GPRuleBase implements ITransportorRule {

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
    Add(Div(Sub(Mul(Add(PT, 0.8912148186209627), Mul(RT, IT)), Mul(Sub(0.8912148186209627, PT), Sub(DD, RT))), Div(Add(Add(0.8912148186209627, IT), Mul(IT, PT)), Div(Mul(DD, IT), Div(Mul(Add(Add(0.8912148186209627, DD), Mul(0.8912148186209627, 0.8912148186209627)), Sub(Mul(IT, 0.8912148186209627), Mul(PT, PT))), RT)))), Mul(Mul(Mul(PT, 0.8912148186209627), Add(Mul(Div(Mul(DD, RT), Mul(PT, 0.8912148186209627)), Sub(Mul(PT, DD), Add(IT, DD))), Mul(DD, RT))), Mul(Div(Mul(IT, PT), Add(DD, DD)), Mul(Sub(PT, IT), Mul(PT, IT)))));

  }
}
