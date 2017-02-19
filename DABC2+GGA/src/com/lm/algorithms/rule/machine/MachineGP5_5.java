package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP5_5 extends GPRuleBase implements IMachineRule {

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
    Add(Div(Sub(RT, Div(OP, Add(Div(RT, Mul(Mul(PT, RPT), Add(RPT, OP))), Div(W, DD)))), Add(W, AT)), Mul(Add(Mul(Sub(Mul(DD, 0.6508699512251822), Div(W, PT)), Div(Add(Div(Sub(RT, OP), Sub(Add(W, AT), Div(PT, RPT))), Div(Div(RPT, W), Sub(Add(Sub(W, OP), Div(PT, W)), Div(PT, RPT)))), Sub(Mul(DD, 0.6508699512251822), Add(W, AT)))), Sub(Sub(Add(W, Div(Div(RPT, W), Sub(Add(Mul(Div(Mul(DD, 0.6508699512251822), Div(OP, W)), Sub(RT, OP)), Sub(Add(W, AT), Div(PT, RPT))), Div(RPT, W)))), RT), Div(RPT, W))), Div(PT, W)))
	;
    }
}
