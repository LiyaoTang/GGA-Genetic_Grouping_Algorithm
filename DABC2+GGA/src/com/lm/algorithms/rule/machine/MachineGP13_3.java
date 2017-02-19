package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP13_3 extends GPRuleBase implements IMachineRule {

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
    Div(Div(Add(Add(Div(Div(Div(PT, W), W), W), Div(Add(DD, RT), W)), Div(Add(DD, Add(Mul(Mul(RT, RPT), Div(Div(PT, W), W)), Sub(W, Div(0.4598847838056539, Div(W, PT))))), W)), Div(Div(W, PT), Div(Sub(RT, RPT), Sub(W, Div(Div(Add(Mul(0.4598847838056539, Mul(Div(W, RPT), Div(Div(PT, W), W))), 0.4598847838056539), Div(Add(DD, Mul(RT, OP)), Sub(W, RT))), Div(Div(PT, Add(W, Add(Div(Div(Div(PT, W), W), W), Div(Add(DD, Add(Mul(Div(W, RPT), W), Sub(W, Div(0.4598847838056539, Div(W, PT))))), W)))), W)))))), Div(W, PT))
    		;
    }
}
