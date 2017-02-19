package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP5_16 extends GPRuleBase implements IMachineRule {

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
    Add(Div(Sub(RT, Div(OP, Add(Div(Div(Div(Div(Div(Add(Mul(Div(RT, RPT), W), Sub(Sub(Sub(Add(W, AT), Div(PT, RPT)), Div(PT, RPT)), Div(RPT, W))), Add(OP, 0.6508699512251822)), OP), Mul(PT, RPT)), PT), Div(Sub(PT, W), W)), Div(W, DD)))), Add(W, AT)), Mul(Add(RT, Sub(Sub(RT, OP), Div(RPT, W))), Div(PT, W)))
    		;
    }
}
