package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP9_1 extends GPRuleBase implements IMachineRule {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public double calPrio(Entity e) {
    double W= e.getWeight();
    double PT=e.getProcessingTime();
    double AT=e.getArrivalTime();
    double DD=e.getDueDate();
    double RPT=e.getRemainingTime();
    double RT=e.getRelDateTime();
    double OP=e.getOpNumber();
    return 
    		Add(Sub(Add(Add(W, W), Add(W, RPT)), Add(Div(DD, W), Div(0.825330467882505, 0.825330467882505))), Div(Sub(Sub(W, Add(PT, OP)), Add(RPT, 0.825330467882505)), Sub(Add(W, W), Add(W, RT))))


    		;
    }
}
