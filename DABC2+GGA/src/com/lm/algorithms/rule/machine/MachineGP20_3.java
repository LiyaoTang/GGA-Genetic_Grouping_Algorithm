package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP20_3 extends GPRuleBase implements IMachineRule {

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
    Add(Sub(Add(Mul(PT, RPT), Div(DD, Div(Div(W, PT), Div(0.5195789501093265, W)))), Div(Sub(RT, OP), Sub(PT, DD))), Mul(Add(Add(Add(Div(Sub(Div(Sub(Sub(RT, Div(Sub(RT, Sub(Sub(PT, DD), Div(DD, DD))), Div(Div(PT, W), W))), Div(DD, Div(Div(W, PT), Div(0.5195789501093265, W)))), Mul(Add(W, PT), Div(AT, AT))), Mul(Add(W, PT), Div(0.5195789501093265, W))), Mul(Add(W, PT), Div(Div(W, PT), Div(0.5195789501093265, W)))), Mul(Add(Mul(OP, PT), Mul(PT, RPT)), Sub(Div(Sub(Sub(RT, Div(Sub(RT, Sub(Sub(PT, DD), Div(DD, DD))), Div(Div(PT, W), W))), Div(DD, Div(Div(W, PT), Div(0.5195789501093265, W)))), Mul(Add(W, PT), Div(AT, AT))), Mul(Add(W, PT), Div(0.5195789501093265, W))))), Mul(Add(Div(PT, RPT), Sub(Add(Div(Div(0.5195789501093265, W), Div(0.5195789501093265, W)), Div(DD, Div(Div(W, PT), Div(0.5195789501093265, W)))), Add(RT, RT))), Mul(0.5195789501093265, PT))), Div(DD, Div(AT, AT))), Add(Div(W, PT), Div(Div(PT, RPT), Mul(W, W)))))
    		;
    }
}
