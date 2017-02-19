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
public class TransGP2 extends GPRuleBase implements ITransportorRule {

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
    Add(Mul(Mul(0.8912148186209627, PT), Mul(DD, DD)), Sub(Add(IT, DD), Sub(PT, 0.8912148186209627)));
  }
}
