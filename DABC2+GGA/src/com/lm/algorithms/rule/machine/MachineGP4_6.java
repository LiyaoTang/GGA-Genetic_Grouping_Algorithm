package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP4_6 extends GPRuleBase implements IMachineRule {

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
    Add(Sub(Mul(0.45017389958494025, W), Add(Add(Sub(OP, 0.45017389958494025), Div(DD, W)), Div(Div(Add(Sub(Sub(Mul(Div(Add(RPT, W), Mul(Mul(0.45017389958494025, W), W)), Mul(Mul(Div(Div(PT, W), W), Add(RPT, W)), 0.45017389958494025)), Mul(0.45017389958494025, W)), Div(Mul(0.45017389958494025, W), Div(RPT, W))), Sub(PT, RT)), RT), Add(RPT, PT)))), Sub(Sub(Sub(Mul(Div(Add(RPT, W), Mul(Mul(0.45017389958494025, W), W)), Mul(Mul(Div(Div(PT, W), W), Sub(Add(Add(RPT, W), Sub(0.45017389958494025, 0.45017389958494025)), Div(Add(RPT, W), Mul(0.45017389958494025, W)))), 0.45017389958494025)), Mul(0.45017389958494025, W)), Div(RPT, Div(OP, Sub(Add(Add(Div(Div(Mul(Div(Div(PT, W), W), Sub(Add(Add(RPT, W), Sub(0.45017389958494025, 0.45017389958494025)), Mul(0.45017389958494025, W))), W), Sub(RPT, 0.45017389958494025)), Sub(Sub(RT, PT), Add(0.45017389958494025, W))), Sub(0.45017389958494025, 0.45017389958494025)), Mul(Mul(RPT, 0.45017389958494025), Mul(0.45017389958494025, 0.45017389958494025)))))), Mul(PT, Div(Div(Div(Add(Mul(0.45017389958494025, PT), W), 0.45017389958494025), Mul(Div(Div(PT, W), W), Sub(Add(Add(Add(RPT, W), Sub(0.45017389958494025, 0.45017389958494025)), Sub(0.45017389958494025, 0.45017389958494025)), Div(RPT, Div(OP, Div(Mul(0.45017389958494025, W), Div(RPT, W))))))), Mul(Add(Div(PT, W), Mul(0.45017389958494025, PT)), Div(Mul(Div(Mul(0.45017389958494025, W), Div(RPT, W)), Mul(0.45017389958494025, 0.45017389958494025)), Div(Add(RPT, RT), Add(RPT, PT))))))))
    		;
    }
}
