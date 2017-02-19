package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP5_10 extends GPRuleBase implements IMachineRule {

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
    Div(Mul(Div(Div(W, RPT), Div(PT, Add(Div(W, Add(Mul(RPT, Mul(Div(Mul(Div(Sub(Mul(W, DD), Div(Div(W, RPT), Add(DD, DD))), Sub(PT, Div(Sub(RT, DD), W))), Div(Add(DD, DD), Div(PT, W))), Sub(RT, DD)), DD)), PT)), W))), Add(Add(DD, DD), Add(Mul(Div(PT, DD), Div(RPT, W)), Mul(Div(Add(Div(Div(PT, Div(PT, DD)), Div(W, PT)), DD), Div(PT, W)), Add(Div(W, Add(Mul(RPT, Mul(Div(Mul(Div(Sub(Mul(W, DD), Div(Div(W, RPT), Add(DD, DD))), Sub(PT, Div(PT, W))), Div(OP, Div(PT, W))), Mul(Div(Sub(Mul(W, DD), Div(Div(W, RPT), Add(DD, DD))), Div(W, PT)), Div(OP, Div(PT, W)))), DD)), PT)), W))))), Sub(PT, Div(Sub(RT, DD), W)))
	;
    }
}
