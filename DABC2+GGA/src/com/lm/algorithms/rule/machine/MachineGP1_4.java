package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP1_4 extends GPRuleBase implements IMachineRule {

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

    		Max(Max(Add(Add(Div(DD, Div(Sub(Add(PT, PT), Max(W, OP)), Max(Max(W, PT), Div(RT, OP)))), Sub(OP, W)), Max(Div(AT, PT), Sub(PT, AT))), Mul(Max(Div(Sub(Add(PT, PT), Add(RPT, W)), Max(Max(W, PT), Div(RT, OP))), Sub(PT, AT)), Div(Sub(Add(Div(DD, Div(Sub(Add(PT, PT), Max(W, OP)), Max(Max(W, PT), Div(RT, OP)))), Sub(OP, W)), Sub(RPT, Max(Mul(W, PT), Mul(W, PT)))), Max(DD, OP)))), Max(Max(Max(Mul(Max(Sub(RT, W), 0.8039775361822307), Div(Sub(Sub(RPT, Mul(RT, AT)), Sub(RPT, Max(Mul(W, PT), Add(0.8039775361822307, RT)))), Max(DD, OP))), Add(0.8039775361822307, RT)), Sub(Sub(RPT, Max(Div(AT, PT), Sub(PT, AT))), Sub(0.8039775361822307, RPT))), Div(Sub(Max(W, OP), Max(W, OP)), Max(Div(Sub(Add(PT, PT), Max(W, OP)), Div(Sub(AT, DD), Add(Div(0.8039775361822307, W), Sub(OP, RT)))), Div(RT, OP)))))
    		;
    }
}
