package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP8_11 extends GPRuleBase implements IMachineRule {

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
    Mul(Add(Sub(Mul(Add(Add(W, Div(OP, W)), Sub(RPT, 0.6185684576927603)), Sub(Mul(RT, PT), Div(OP, W))), Div(DD, W)), Sub(Add(PT, OP), Sub(DD, 0.6185684576927603))), Add(Mul(Mul(W, Div(AT, RT)), Div(Sub(Add(Sub(Sub(Div(W, PT), Div(DD, W)), Sub(Mul(AT, OP), Add(Mul(Add(Mul(Div(Sub(Mul(RT, Div(OP, W)), Div(Div(DD, W), W)), RPT), Div(RT, AT)), Add(Div(RPT, W), Add(Sub(Mul(Div(RT, RPT), Div(RT, AT)), Div(OP, W)), Sub(DD, 0.6185684576927603)))), Div(Div(W, Mul(OP, Sub(DD, 0.6185684576927603))), Div(RT, AT))), RT))), Mul(Sub(PT, Div(DD, W)), Add(PT, OP))), Div(Add(Add(Add(Sub(Mul(Add(Add(W, Div(OP, W)), Sub(RPT, 0.6185684576927603)), Div(DD, W)), OP), Sub(Add(PT, OP), Sub(DD, 0.6185684576927603))), PT), Add(RPT, Div(Div(DD, W), W))), Mul(Add(OP, W), Mul(Add(Sub(Div(RT, AT), Mul(RT, PT)), Sub(Mul(Mul(W, W), Mul(Div(RT, RPT), Div(RT, AT))), Div(RPT, Mul(Div(W, PT), W)))), Add(Mul(Div(RT, RPT), Div(RT, AT)), Add(Div(RPT, W), Div(AT, OP))))))), OP)), Mul(Div(Div(DD, W), W), PT)))
    		;
    }
}
