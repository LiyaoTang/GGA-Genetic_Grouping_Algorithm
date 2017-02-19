package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP4_8 extends GPRuleBase implements IMachineRule {

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
    Add(Sub(Sub(Add(Div(W, AT), Div(PT, RPT)), Add(Sub(OP, 0.45017389958494025), Mul(0.45017389958494025, W))), Add(RPT, Div(Add(Div(RPT, W), Sub(Sub(RT, PT), Div(RPT, W))), Sub(Sub(Mul(Div(Add(RPT, W), Mul(Mul(0.45017389958494025, W), W)), Add(Div(RPT, W), W)), Mul(0.45017389958494025, W)), Div(RPT, Div(OP, Div(Mul(0.45017389958494025, W), Div(RPT, W)))))))), Sub(Sub(Sub(Mul(Div(Div(Add(RPT, W), Mul(0.45017389958494025, W)), Mul(0.45017389958494025, W)), Mul(Mul(Div(Div(PT, W), W), Sub(Add(Add(RPT, W), Sub(0.45017389958494025, 0.45017389958494025)), Div(Add(RPT, W), Mul(0.45017389958494025, W)))), 0.45017389958494025)), Div(Add(RPT, W), Mul(0.45017389958494025, W))), Div(RPT, Div(OP, Div(Div(RPT, W), Sub(PT, RT))))), Mul(PT, Div(Add(0.45017389958494025, W), Sub(Sub(Mul(Div(Div(Add(RPT, W), Mul(0.45017389958494025, W)), Add(Add(RPT, W), Sub(0.45017389958494025, 0.45017389958494025))), Mul(Mul(Div(Div(PT, W), W), Sub(Add(Div(Add(RPT, W), Mul(Mul(0.45017389958494025, W), W)), Add(RPT, W)), Div(Add(RPT, W), Mul(0.45017389958494025, W)))), 0.45017389958494025)), Div(W, Mul(0.45017389958494025, W))), Div(RPT, Div(OP, Div(Sub(Mul(Div(Add(RPT, W), Mul(Mul(0.45017389958494025, W), W)), Mul(Mul(Div(Div(RPT, Add(RPT, RT)), W), Sub(Add(0.45017389958494025, W), Div(Add(RPT, W), Mul(0.45017389958494025, W)))), 0.45017389958494025)), Mul(0.45017389958494025, W)), Add(RPT, W)))))))))
	;
    }
}
