package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP18_15 extends GPRuleBase implements IMachineRule {

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
    Sub(Div(Sub(Mul(Add(Add(AT, DD), Div(Div(RPT, W), W)), Div(Div(PT, W), Mul(W, W))), Mul(Div(Sub(PT, RT), Mul(W, W)), Sub(Sub(Sub(RT, 0.9410328664153133), Div(PT, W)), Mul(Add(Add(Div(RPT, W), DD), Div(0.9410328664153133, DD)), Sub(Sub(RT, 0.9410328664153133), Div(PT, W)))))), Div(Sub(W, RT), 0.9410328664153133)), Mul(Add(Add(AT, DD), Div(Div(RPT, W), W)), Sub(Add(0.9410328664153133, Div(PT, 0.9410328664153133)), Sub(0.9410328664153133, DD))))
    		;
    }
}
