package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP7_12 extends GPRuleBase implements IMachineRule {

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
    Mul(Add(Div(Div(Add(RPT, OP), W), Add(OP, RT)), Div(Add(Div(Sub(DD, 0.2070473162235501), Add(OP, RT)), Mul(W, W)), PT)), Sub(Add(W, Sub(Mul(Add(Div(Mul(Add(Sub(DD, 0.2070473162235501), Add(Div(Sub(Add(Div(Sub(DD, 0.2070473162235501), Add(OP, RT)), Mul(W, W)), Div(Mul(OP, W), Div(Div(W, RPT), W))), Add(OP, RT)), Div(Add(Div(Mul(PT, RPT), DD), Div(RPT, Add(OP, RT))), Sub(DD, DD)))), Div(Mul(PT, RPT), Mul(W, W))), Add(Div(OP, 0.2070473162235501), RT)), Add(OP, RT)), Sub(Div(W, Mul(DD, AT)), Div(RPT, Mul(W, W)))), Div(W, RPT))), Add(Div(Div(Mul(Add(Sub(DD, 0.2070473162235501), Add(W, Div(Add(Div(Mul(PT, RPT), Mul(DD, AT)), Div(RPT, W)), Sub(DD, DD)))), Mul(RT, RPT)), Add(Div(Sub(Div(Sub(DD, 0.2070473162235501), Add(OP, RT)), Div(Mul(OP, W), Div(Div(W, RPT), W))), Add(OP, RT)), RT)), Sub(Div(Div(Mul(DD, PT), W), W), Div(RPT, W))), Div(Div(Mul(DD, PT), W), W))))
    		;
    }
}
