package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP3_14 extends GPRuleBase implements IMachineRule {

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
    Sub(Add(W, Mul(Div(Sub(0.7000330304541551, RPT), W), Add(Add(Div(PT, W), RT), Mul(OP, RT)))), Add(Sub(Add(Mul(RPT, PT), Add(W, 0.7000330304541551)), Add(Mul(RT, AT), Mul(Div(W, Mul(Mul(0.7000330304541551, RPT), Add(Add(Add(Mul(0.7000330304541551, W), Div(RPT, RT)), Mul(Sub(OP, OP), Div(Div(Add(Sub(AT, Sub(RT, Sub(OP, OP))), Add(W, 0.7000330304541551)), Add(Div(PT, W), RT)), Add(Sub(PT, Add(Add(Div(PT, W), Div(W, DD)), RT)), Add(DD, DD))))), Add(RPT, RT)))), Div(Div(Add(Sub(AT, Sub(RT, W)), DD), Div(PT, W)), Add(Sub(PT, RT), Add(DD, DD)))))), Mul(Sub(Mul(0.7000330304541551, RPT), Add(Mul(RT, AT), Mul(OP, OP))), Add(Add(Add(Mul(0.7000330304541551, W), Sub(PT, RT)), Mul(Div(W, AT), Div(Div(Add(Sub(AT, Sub(RT, Div(W, DD))), Add(W, 0.7000330304541551)), Div(PT, W)), Add(Add(Sub(Add(Mul(DD, PT), Add(W, 0.7000330304541551)), Add(0.7000330304541551, W)), Mul(W, Add(Div(W, AT), Add(RPT, RT)))), Add(DD, DD))))), Add(RPT, Add(Mul(RT, AT), Mul(W, Div(Div(Add(Sub(AT, Sub(RT, W)), DD), Div(PT, W)), Add(Sub(PT, RT), Sub(AT, Add(Sub(Add(Mul(DD, PT), Add(W, 0.7000330304541551)), Add(Div(W, AT), Add(RPT, RT))), Mul(W, Div(W, DD)))))))))))))
;
    }
}
