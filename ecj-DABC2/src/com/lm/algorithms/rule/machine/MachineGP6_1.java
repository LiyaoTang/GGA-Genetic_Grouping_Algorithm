package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP6_1 extends GPRuleBase implements IMachineRule {

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
    		Sub(Div(Add(Mul(RPT, 0.10021748968631827), Div(Sub(0.10021748968631827, PT), Div(W, Div(Add(PT, DD), Mul(Add(PT, DD), W))))), Mul(0.10021748968631827, W)), Mul(Div(OP, W), Add(Div(Div(Mul(Div(Sub(Div(Add(Add(PT, DD), DD), Mul(Add(PT, DD), W)), PT), Div(W, Div(Add(PT, DD), Mul(Mul(Add(W, 0.10021748968631827), Div(Mul(0.10021748968631827, W), Div(Mul(RPT, Div(W, Mul(RPT, 0.10021748968631827))), Mul(Add(PT, DD), W)))), W)))), OP), RPT), RPT), DD)))
    		;
    }
}
