package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP6_15 extends GPRuleBase implements IMachineRule {

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
    Sub(Mul(Div(Sub(Div(RPT, W), OP), AT), Div(Sub(Div(RPT, W), Div(Div(Sub(Sub(Div(RPT, W), Div(Div(Div(Div(Div(AT, DD), W), W), W), W)), W), Div(Mul(Div(Sub(Sub(Div(RPT, W), OP), Div(Mul(Div(RPT, W), Div(RPT, W)), AT)), AT), Add(OP, W)), Div(Sub(Div(RPT, W), Div(Sub(0.9185244575398441, PT), W)), DD))), W)), AT)), Sub(Div(RPT, W), Div(Div(Sub(0.9185244575398441, PT), W), W)))


    		;
    }
}
