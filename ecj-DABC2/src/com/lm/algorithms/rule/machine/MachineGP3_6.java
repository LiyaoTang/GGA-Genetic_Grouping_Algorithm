package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP3_6 extends GPRuleBase implements IMachineRule {

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
    Sub(Add(Div(Div(Sub(Mul(0.7000330304541551, RPT), Mul(OP, OP)), Add(Add(Sub(0.7000330304541551, RPT), OP), Sub(OP, OP))), Sub(Add(Div(Div(Mul(OP, Mul(OP, Sub(AT, DD))), Add(Div(PT, W), Div(W, Add(Add(Add(RPT, RT), Sub(OP, OP)), W)))), Add(Sub(PT, RT), Add(DD, DD))), Div(Div(Add(Sub(Add(Add(RPT, Div(OP, AT)), RT), Sub(RT, Div(W, DD))), Div(W, DD)), Mul(0.7000330304541551, W)), Add(Add(Add(Sub(Add(Sub(0.7000330304541551, RPT), Add(W, 0.7000330304541551)), Div(Mul(RT, AT), PT)), Mul(Sub(PT, RT), Mul(0.7000330304541551, RPT))), Add(DD, DD)), Add(Mul(RT, AT), DD)))), Add(Sub(Add(Mul(DD, PT), Add(Div(PT, W), Mul(OP, OP))), Mul(RT, AT)), Mul(Sub(Mul(0.7000330304541551, RPT), Div(PT, W)), Add(Div(W, AT), Sub(Mul(RPT, PT), RPT)))))), Mul(Div(Sub(0.7000330304541551, RPT), W), Add(Add(Div(PT, W), Div(W, DD)), RT))), Add(Sub(Add(Mul(DD, PT), Add(DD, DD)), Add(Sub(Add(Mul(RPT, PT), Add(W, 0.7000330304541551)), Add(Mul(RT, AT), Mul(OP, OP))), Mul(Sub(Mul(0.7000330304541551, RPT), Add(Mul(RT, AT), Mul(OP, OP))), Add(Add(W, Mul(Sub(OP, OP), Div(Add(DD, DD), Div(W, AT)))), Add(RPT, RT))))), Mul(Sub(Mul(0.7000330304541551, RPT), Mul(OP, OP)), Add(Mul(OP, OP), Add(RPT, RT)))))
    		;
    }
}
