package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP3_7 extends GPRuleBase implements IMachineRule {

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
    Sub(Add(Div(Div(Mul(OP, Mul(OP, Sub(Mul(0.7000330304541551, RPT), Add(Div(Mul(OP, Mul(OP, Sub(AT, DD))), Add(Div(PT, W), Div(W, 0.7000330304541551))), Mul(OP, OP))))), Add(Add(Mul(RT, AT), Mul(Sub(Mul(Mul(RT, AT), RPT), Div(AT, DD)), Mul(DD, PT))), Div(W, AT))), Add(DD, DD)), Mul(Div(Sub(0.7000330304541551, RPT), W), Add(Add(Div(PT, W), Div(W, DD)), RT))), Add(Sub(Add(Mul(DD, PT), Add(DD, DD)), Add(Sub(Add(Mul(RPT, PT), Add(W, 0.7000330304541551)), Add(Mul(RT, AT), Mul(OP, OP))), Mul(Sub(Mul(0.7000330304541551, RPT), Add(Mul(RT, AT), Mul(OP, OP))), Add(Add(W, Mul(Sub(OP, OP), Div(Div(Add(Sub(Add(Add(RPT, Div(OP, AT)), RT), Sub(RT, Div(W, DD))), Sub(Add(Div(Mul(RT, AT), PT), Mul(OP, OP)), Add(Sub(Div(Sub(0.7000330304541551, RPT), W), Mul(RT, AT)), Mul(Sub(Mul(0.7000330304541551, RPT), Mul(OP, OP)), Add(Mul(RPT, PT), AT))))), Mul(0.7000330304541551, W)), Div(Sub(0.7000330304541551, RPT), W)))), Add(RPT, RT))))), Mul(Sub(Mul(0.7000330304541551, RPT), Mul(OP, OP)), Add(Mul(OP, OP), Add(RPT, RT)))))
    		;
    }
}
