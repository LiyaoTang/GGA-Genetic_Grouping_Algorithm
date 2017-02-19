package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP12_9 extends GPRuleBase implements IMachineRule {

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
    Mul(Mul(0.007232254124908466, Add(OP, Add(OP, Div(OP, PT)))), Mul(Div(W, Add(Sub(Sub(Sub(Sub(Sub(Div(OP, Sub(DD, OP)), Sub(Div(RPT, RT), Sub(Sub(DD, OP), Div(DD, W)))), Add(Div(OP, PT), Add(Sub(Add(OP, RT), Mul(W, Mul(Sub(Sub(DD, OP), OP), W))), Sub(Add(OP, RT), Mul(Mul(0.007232254124908466, Div(RPT, RT)), Mul(Sub(Sub(DD, OP), OP), W)))))), Div(RPT, RT)), Add(Mul(0.007232254124908466, W), Add(Div(W, RPT), Div(OP, PT)))), Add(Div(Sub(Sub(PT, Div(DD, W)), Div(Mul(Add(OP, PT), PT), DD)), Sub(Add(OP, RT), Mul(Mul(0.007232254124908466, Add(OP, Add(W, PT))), Mul(Sub(Sub(DD, OP), OP), W)))), Mul(0.007232254124908466, W))), Add(Sub(RPT, OP), Div(Sub(Sub(Sub(DD, OP), OP), Mul(W, W)), Div(W, PT))))), Div(W, RPT)))
    		;
    }
}
