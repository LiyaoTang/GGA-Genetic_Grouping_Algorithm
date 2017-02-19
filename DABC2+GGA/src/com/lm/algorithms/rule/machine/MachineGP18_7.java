package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP18_7 extends GPRuleBase implements IMachineRule {

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
    Sub(Div(Sub(Mul(Add(Add(AT, DD), Div(Div(RPT, W), W)), Div(Div(PT, W), Mul(W, W))), Mul(Div(Sub(PT, RT), Mul(W, W)), Sub(Sub(Div(Sub(Add(Add(Sub(Add(Div(0.9410328664153133, DD), W), RPT), Mul(Add(Add(Div(RPT, W), DD), Mul(Mul(Div(W, RPT), Mul(PT, Div(PT, 0.9410328664153133))), RPT)), Sub(Sub(RT, 0.9410328664153133), Div(PT, W)))), W), Sub(Sub(W, RT), Sub(Add(0.9410328664153133, W), W))), Add(RPT, Div(RPT, W))), Div(PT, W)), Mul(Add(Add(Div(RPT, W), DD), Mul(PT, Mul(RPT, RT))), Sub(Sub(RT, 0.9410328664153133), Div(PT, W)))))), Div(Sub(W, RT), 0.9410328664153133)), Mul(Add(Add(AT, DD), Div(Div(RPT, W), W)), Sub(Add(0.9410328664153133, Div(PT, 0.9410328664153133)), Sub(0.9410328664153133, DD))))
    ;
    }
}
