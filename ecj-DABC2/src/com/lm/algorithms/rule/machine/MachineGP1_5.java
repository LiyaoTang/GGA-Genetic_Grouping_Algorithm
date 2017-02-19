package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP1_5 extends GPRuleBase implements IMachineRule {

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
    Mul(Mul(Mul(Div(DD, W), Sub(W, PT)), Add(Mul(PT, 0.9407928655128824), Sub(W, OP))), Sub(Add(Add(RPT, 0.9407928655128824), Add(Sub(Sub(Mul(OP, AT), Sub(RT, RT)), Sub(Sub(Sub(Mul(OP, AT), Sub(RT, RT)), Mul(Sub(RPT, W), Div(0.9407928655128824, W))), Add(Div(Div(RT, OP), Mul(W, RPT)), Mul(Add(Sub(RPT, 0.9407928655128824), Div(PT, W)), Div(DD, DD))))), AT)), Mul(PT, OP)))


    		;
    }
}
