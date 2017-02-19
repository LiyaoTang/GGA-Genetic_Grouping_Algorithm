package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP9_15 extends GPRuleBase implements IMachineRule {

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
    Sub(Mul(Add(Add(Add(Sub(Div(AT, PT), RT), Add(RT, Div(DD, OP))), Sub(Add(PT, PT), Div(OP, W))), Mul(Mul(Div(OP, Mul(Add(Mul(0.014273520296233966, RT), Div(AT, PT)), Sub(Sub(Mul(Add(DD, 0.014273520296233966), W), Div(OP, Mul(Div(OP, RPT), W))), Div(OP, DD)))), W), Add(Add(RT, Div(DD, OP)), Add(RT, Div(DD, OP))))), Sub(Add(Add(Add(Sub(Sub(Div(0.014273520296233966, RT), Sub(Div(Sub(Mul(Div(OP, RPT), W), Div(AT, Div(AT, RPT))), Div(Sub(DD, PT), Div(W, PT))), Div(Div(OP, W), Mul(Div(OP, RPT), W)))), Sub(Sub(PT, 0.014273520296233966), Div(AT, DD))), Div(DD, W)), Sub(Div(RT, AT), Add(RT, RT))), Div(W, RPT)), Mul(Div(OP, W), Sub(Add(Add(Add(Sub(OP, RT), RPT), Div(DD, W)), Sub(Div(RT, AT), Add(RT, RT))), Sub(Sub(Div(0.014273520296233966, RT), Sub(Div(Mul(Div(OP, RPT), W), Div(Sub(DD, PT), Div(W, PT))), Div(Div(OP, W), Mul(Div(OP, RPT), W)))), Sub(Sub(PT, 0.014273520296233966), Add(Add(Sub(Sub(AT, AT), Mul(RT, AT)), Add(Mul(0.014273520296233966, RT), Div(AT, PT))), Add(OP, W)))))))), Mul(Div(Div(OP, W), Mul(Div(OP, RPT), W)), Div(Sub(DD, PT), Div(W, PT))))
    		;
    }
}
