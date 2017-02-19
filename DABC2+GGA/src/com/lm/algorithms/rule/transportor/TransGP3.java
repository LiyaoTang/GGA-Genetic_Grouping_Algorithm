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
public class TransGP3 extends GPRuleBase implements ITransportorRule {

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
    Mul(Sub(Add(Sub(Mul(DD, 0.8912148186209627), Mul(0.8912148186209627, 0.8912148186209627)), Add(Sub(PT, 0.8912148186209627), Div(0.8912148186209627, IT))), Sub(Mul(Div(DD, PT), Div(RT, PT)), Sub(Add(IT, DD), IT))), Sub(Add(Div(Div(DD, DD), Sub(IT, DD)), Add(Mul(DD, 0.8912148186209627), Mul(PT, DD))), Sub(Mul(Add(RT, RT), Sub(PT, PT)), Sub(Div(RT, DD), Add(RT, PT)))));

  }
}
