package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP12_3 extends GPRuleBase implements IMachineRule {

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
    Mul(W, Div(Mul(Mul(Div(W, OP), Div(W, PT)), Div(W, PT)), Sub(Add(Add(DD, OP), Mul(Sub(Add(Add(DD, OP), Mul(Div(Sub(AT, PT), Sub(Sub(Sub(AT, 0.3043791119777566), Add(Mul(Div(OP, OP), Sub(0.3043791119777566, PT)), Div(Add(Sub(Div(W, PT), Add(DD, OP)), Mul(Sub(AT, PT), OP)), Mul(DD, 0.3043791119777566)))), Div(Mul(Mul(Div(OP, OP), Sub(0.3043791119777566, PT)), Div(Sub(DD, RT), Sub(PT, RPT))), Mul(DD, Add(0.3043791119777566, OP))))), Div(W, PT))), Mul(Mul(Div(OP, OP), Sub(0.3043791119777566, PT)), Div(W, Sub(Mul(Sub(0.3043791119777566, PT), Div(Sub(Sub(PT, RPT), RT), Sub(DD, RT))), RPT)))), Div(W, PT))), Mul(Sub(Sub(OP, DD), Mul(Mul(Div(OP, OP), Sub(0.3043791119777566, PT)), Div(Sub(DD, RT), Sub(PT, RPT)))), Div(W, Sub(PT, RPT))))))
    		;
    }
}
