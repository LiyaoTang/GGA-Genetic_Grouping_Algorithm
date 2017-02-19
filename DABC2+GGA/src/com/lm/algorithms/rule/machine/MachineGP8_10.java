package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP8_10 extends GPRuleBase implements IMachineRule {

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
    Mul(Add(Sub(Mul(Add(Add(W, Div(OP, W)), Sub(RPT, 0.6185684576927603)), Sub(Mul(RT, PT), Div(OP, W))), Div(DD, W)), Sub(Add(PT, OP), Add(Div(Div(W, Mul(OP, DD)), Div(RT, AT)), Sub(DD, 0.6185684576927603)))), Add(Mul(Mul(W, Div(AT, RT)), Div(Sub(Add(Sub(Sub(Add(W, PT), Div(DD, W)), Sub(Mul(AT, OP), Add(Mul(Add(Mul(Add(PT, OP), Div(RT, AT)), Add(Div(RPT, W), Add(Div(DD, W), Div(AT, RT)))), Div(Div(W, Div(DD, W)), Div(RT, AT))), RT))), Mul(Sub(PT, Div(DD, W)), Add(W, PT))), Div(Add(Div(DD, W), Mul(Div(Sub(Mul(RT, PT), Div(OP, W)), RPT), Div(RT, AT))), Mul(Sub(Div(DD, W), Div(DD, W)), Sub(Add(0.6185684576927603, W), Add(Sub(Mul(Add(Add(W, Div(OP, W)), Mul(Div(W, Mul(OP, DD)), W)), Sub(Mul(RT, PT), Div(OP, W))), Div(DD, W)), Sub(Add(PT, OP), Sub(DD, 0.6185684576927603))))))), OP)), Mul(Div(Div(DD, W), W), PT)))
    		;
    }
}
