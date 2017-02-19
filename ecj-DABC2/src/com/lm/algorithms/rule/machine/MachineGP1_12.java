package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP1_12 extends GPRuleBase implements IMachineRule {

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
    Mul(Sub(OP, Div(OP, Div(0.8723855103424603, W))), Mul(Mul(Mul(Add(DD, 0.8723855103424603), Div(PT, DD)), Sub(Mul(DD, PT), Mul(RPT, 0.8723855103424603))), Div(Sub(Mul(RT, AT), Mul(RPT, 0.8723855103424603)), Mul(Add(OP, W), Div(W, 0.8723855103424603)))))


    		;
    }
}
