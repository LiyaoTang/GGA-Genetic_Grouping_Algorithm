package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP4_5 extends GPRuleBase implements IMachineRule {

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
    Add(Sub(Sub(Add(Div(W, AT), Div(PT, RPT)), Add(Add(W, Mul(RPT, 0.45017389958494025)), Sub(PT, RT))), Add(Add(Sub(OP, 0.45017389958494025), Div(DD, W)), Div(Add(Sub(Sub(Sub(Mul(Div(RPT, Add(RPT, RT)), Mul(0.45017389958494025, 0.45017389958494025)), Div(Mul(W, 0.45017389958494025), Mul(0.45017389958494025, W))), Div(RPT, W)), Mul(Add(Mul(0.45017389958494025, 0.45017389958494025), Add(RPT, RT)), Div(Div(Add(0.45017389958494025, W), RT), Sub(RPT, 0.45017389958494025)))), Sub(Sub(RT, PT), Div(RPT, W))), Add(RPT, PT)))), Sub(Sub(Sub(Mul(Div(Add(RPT, W), Mul(Mul(0.45017389958494025, W), W)), Mul(Mul(Div(Div(PT, W), W), Sub(Add(Add(RPT, W), Sub(0.45017389958494025, 0.45017389958494025)), Div(Add(RPT, W), Mul(0.45017389958494025, W)))), 0.45017389958494025)), Mul(0.45017389958494025, W)), Div(RPT, Div(OP, Div(Mul(0.45017389958494025, W), Div(RPT, W))))), Mul(PT, Div(Div(OP, 0.45017389958494025), Sub(RPT, 0.45017389958494025)))))
    		;
    }
}
