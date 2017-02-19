package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP4_9 extends GPRuleBase implements IMachineRule {

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
    Add(Sub(Sub(Div(Add(RPT, RT), Sub(RPT, 0.45017389958494025)), Add(Add(W, Mul(RPT, 0.45017389958494025)), 0.45017389958494025)), Sub(Mul(Div(Add(RPT, W), Mul(Mul(0.45017389958494025, W), W)), Mul(Mul(Div(Div(PT, W), W), Sub(Add(W, Add(0.45017389958494025, RPT)), Div(Add(RPT, W), Mul(0.45017389958494025, W)))), 0.45017389958494025)), PT)), Sub(Sub(Sub(Mul(Div(Add(RPT, W), Mul(Mul(0.45017389958494025, W), W)), Mul(Mul(Div(Div(PT, W), Mul(0.45017389958494025, W)), Sub(Add(Add(RPT, W), Sub(0.45017389958494025, 0.45017389958494025)), Div(Add(RPT, W), Mul(0.45017389958494025, W)))), 0.45017389958494025)), Mul(0.45017389958494025, W)), W), Add(RPT, W)))


    		;
    }
}
