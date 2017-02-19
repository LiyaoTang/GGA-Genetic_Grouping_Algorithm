package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP7_7 extends GPRuleBase implements IMachineRule {

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
    Mul(Add(Div(Div(Add(RPT, OP), W), Add(OP, RT)), Div(Add(Div(Sub(DD, 0.2070473162235501), Div(Add(Div(Sub(DD, 0.2070473162235501), Add(OP, RT)), Mul(RT, RPT)), PT)), Mul(W, W)), PT)), Sub(Add(W, Sub(Mul(Add(Div(Mul(Add(Sub(DD, 0.2070473162235501), Add(Div(Sub(Add(Div(Sub(DD, 0.2070473162235501), Add(OP, RT)), Mul(W, W)), Div(Mul(OP, W), Div(Div(W, RPT), W))), Add(OP, RT)), Div(Add(Div(OP, DD), Div(RPT, W)), Sub(DD, DD)))), Div(Mul(PT, RPT), Mul(W, W))), Add(Div(OP, 0.2070473162235501), RT)), Add(OP, RT)), Sub(Div(W, Mul(DD, AT)), Div(RPT, Div(W, Add(Div(Div(Add(RPT, OP), W), Add(OP, RT)), Div(Add(Div(Sub(DD, 0.2070473162235501), RPT), Mul(W, W)), PT)))))), Div(W, RPT))), Add(RPT, Div(Div(OP, 0.2070473162235501), W))))
	;
    }
}
