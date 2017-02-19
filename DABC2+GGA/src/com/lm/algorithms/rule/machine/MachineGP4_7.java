package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP4_7 extends GPRuleBase implements IMachineRule {

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
    Add(Sub(Add(RPT, W), Add(Add(Add(0.45017389958494025, W), Div(DD, W)), Div(Add(Sub(Sub(Sub(Div(Add(RPT, W), Mul(0.45017389958494025, W)), Div(Div(OP, 0.45017389958494025), Div(Div(PT, W), W))), Div(RPT, W)), Mul(Add(Mul(Sub(Mul(Div(RPT, Mul(Mul(0.45017389958494025, W), W)), Mul(Mul(Div(Div(PT, W), W), Sub(Add(Add(RPT, W), Add(0.45017389958494025, RPT)), Div(Add(RPT, W), Mul(0.45017389958494025, W)))), 0.45017389958494025)), Add(Sub(Sub(RPT, RT), Add(RPT, W)), Mul(0.45017389958494025, PT))), W), Mul(0.45017389958494025, PT)), Div(Div(Div(Sub(RT, PT), Div(RPT, W)), RT), Sub(RPT, 0.45017389958494025)))), Add(Sub(Sub(Mul(0.45017389958494025, Add(0.45017389958494025, RPT)), Div(RPT, W)), Mul(Add(Div(Div(Add(RPT, W), 0.45017389958494025), Sub(RPT, 0.45017389958494025)), Add(RPT, RT)), Div(Sub(Sub(Mul(Div(RPT, Add(RPT, RT)), Mul(0.45017389958494025, 0.45017389958494025)), Div(W, AT)), Div(RPT, W)), Add(0.45017389958494025, W)))), Sub(Sub(RT, PT), Div(RPT, W)))), Add(RPT, PT)))), Sub(Sub(Sub(Mul(Div(Div(Add(RPT, W), Mul(0.45017389958494025, W)), Mul(0.45017389958494025, W)), Mul(Mul(Div(Div(PT, W), W), Sub(Add(Sub(RPT, 0.45017389958494025), Sub(0.45017389958494025, 0.45017389958494025)), Div(Add(RPT, W), Mul(0.45017389958494025, W)))), 0.45017389958494025)), Mul(0.45017389958494025, W)), Div(RPT, Div(RPT, Add(RPT, PT)))), Mul(PT, Div(0.45017389958494025, Sub(RPT, Sub(Sub(Sub(Mul(Div(Add(RPT, W), Mul(Mul(0.45017389958494025, W), W)), Mul(Mul(Div(Div(PT, W), W), Sub(Add(Add(RPT, W), 0.45017389958494025), Div(Add(RPT, W), Mul(0.45017389958494025, W)))), 0.45017389958494025)), Div(Add(RPT, W), Mul(0.45017389958494025, W))), Sub(0.45017389958494025, 0.45017389958494025)), Mul(PT, Div(Div(OP, 0.45017389958494025), Div(Add(RPT, W), Mul(0.45017389958494025, W))))))))))
	;
    }
}
