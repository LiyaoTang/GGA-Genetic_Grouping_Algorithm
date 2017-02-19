package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP4_10 extends GPRuleBase implements IMachineRule {

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
    Add(Sub(Add(Div(RPT, W), W), Div(Add(0.45017389958494025, W), RT)), Sub(Sub(Sub(Mul(Div(Add(Div(RPT, W), W), Mul(0.45017389958494025, W)), Mul(Mul(Div(Div(PT, W), W), Sub(Add(Add(RPT, W), Sub(0.45017389958494025, 0.45017389958494025)), Div(Add(RPT, W), Mul(0.45017389958494025, W)))), 0.45017389958494025)), 0.45017389958494025), W), Mul(PT, Sub(Add(Sub(Add(Div(W, AT), Div(PT, RPT)), Add(Mul(W, 0.45017389958494025), Sub(PT, RT))), W), 0.45017389958494025))))
	;
    }
}
