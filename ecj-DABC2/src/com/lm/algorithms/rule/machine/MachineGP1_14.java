package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP1_14 extends GPRuleBase implements IMachineRule {

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
    Sub(Mul(Mul(Add(DD, W), Sub(Mul(DD, PT), W)), Div(Sub(Mul(RT, AT), DD), Mul(Add(OP, Div(RT, OP)), Div(W, 0.8723855103424603)))), Add(Mul(Add(Div(0.8723855103424603, OP), Mul(OP, AT)), Div(Sub(Sub(Add(W, RPT), Add(AT, DD)), PT), Mul(PT, AT))), Mul(Add(Mul(0.8723855103424603, DD), Sub(W, RT)), Add(DD, PT))))
    ;
    }
}
