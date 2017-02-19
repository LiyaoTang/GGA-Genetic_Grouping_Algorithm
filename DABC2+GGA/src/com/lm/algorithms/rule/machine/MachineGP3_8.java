package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP3_8 extends GPRuleBase implements IMachineRule {

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
    Sub(Add(Div(Div(Sub(Add(Add(Mul(RT, AT), Mul(OP, OP)), Mul(Div(RT, W), Add(Add(Div(PT, W), Add(Mul(DD, PT), Sub(Mul(0.7000330304541551, RPT), Add(Mul(RT, AT), Mul(OP, OP))))), RT))), Add(Sub(Add(DD, DD), Add(Add(Add(Sub(Add(Sub(0.7000330304541551, RPT), Add(W, 0.7000330304541551)), Add(0.7000330304541551, W)), Mul(Sub(0.7000330304541551, RPT), Mul(W, Add(Div(W, AT), Add(RPT, RT))))), Add(DD, DD)), Add(DD, DD))), Mul(Sub(Mul(0.7000330304541551, RPT), Div(PT, W)), Add(Div(W, AT), Sub(0.7000330304541551, RPT))))), Add(Add(Mul(RT, AT), Mul(Sub(Mul(0.7000330304541551, RPT), Div(AT, DD)), Mul(DD, PT))), Div(W, AT))), Sub(Add(Div(Div(Mul(OP, Mul(OP, Sub(AT, DD))), Add(Div(PT, W), Div(W, Sub(PT, RT)))), Add(Sub(PT, RT), Add(DD, DD))), Mul(Div(RT, W), Add(Sub(Add(Mul(DD, PT), Add(Div(Mul(RT, AT), PT), Mul(OP, OP))), Mul(RT, AT)), Mul(Sub(Mul(OP, OP), Div(PT, W)), Add(Div(W, AT), Sub(0.7000330304541551, RPT)))))), Add(Sub(Add(Mul(DD, PT), Add(Sub(PT, RT), Add(DD, DD))), Mul(0.7000330304541551, W)), Mul(Sub(Mul(OP, Sub(Mul(0.7000330304541551, RPT), Add(Mul(RT, AT), Mul(OP, OP)))), Div(PT, W)), Add(Div(W, AT), Add(Sub(PT, RT), Add(DD, DD))))))), Mul(Div(Sub(0.7000330304541551, RPT), W), Add(Div(PT, W), Div(W, DD)))), Add(Sub(Add(Mul(DD, PT), Add(DD, DD)), Add(Sub(Add(Mul(RPT, PT), Add(W, 0.7000330304541551)), Add(Mul(RT, AT), Mul(OP, OP))), Mul(Sub(Mul(0.7000330304541551, RPT), Add(Mul(RT, AT), Mul(OP, OP))), Add(Add(W, Mul(Sub(OP, OP), Div(Div(Add(Sub(Add(Add(RPT, Div(OP, AT)), RT), Sub(RT, Div(W, DD))), Sub(Add(Div(Mul(RT, AT), PT), Mul(OP, OP)), Add(Sub(Mul(0.7000330304541551, RPT), Mul(RT, AT)), Mul(Sub(Mul(0.7000330304541551, RPT), Mul(OP, OP)), Add(RPT, RT))))), Mul(0.7000330304541551, W)), Add(Add(Add(Sub(Add(Sub(0.7000330304541551, RPT), Add(W, 0.7000330304541551)), Add(0.7000330304541551, W)), Mul(Sub(0.7000330304541551, RPT), Mul(W, Add(Div(W, AT), Add(RPT, RT))))), Add(DD, DD)), Add(DD, DD))))), Add(RPT, RT))))), Mul(Sub(Mul(0.7000330304541551, RPT), Mul(OP, OP)), Add(Mul(OP, OP), Add(RPT, RT)))))


    		;
    }
}
