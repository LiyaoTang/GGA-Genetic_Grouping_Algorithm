package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP11_13 extends GPRuleBase implements IMachineRule {

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
    Mul(Div(OP, RPT), Mul(Mul(Mul(Mul(Div(Div(Add(Sub(W, Mul(Div(W, RPT), Mul(Div(Mul(Div(OP, RPT), Mul(Div(W, PT), W)), PT), W))), Div(W, PT)), Add(Div(Add(DD, Mul(Div(Div(Mul(W, W), Div(W, OP)), Div(W, PT)), W)), Mul(Div(OP, RPT), Mul(Div(W, PT), W))), Div(Div(OP, Div(W, PT)), Div(W, PT)))), PT), Div(OP, RPT)), 0.374064375187943), W), W))
    		;
    }
}
