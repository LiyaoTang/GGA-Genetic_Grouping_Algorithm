package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP1_10 extends GPRuleBase implements IMachineRule {

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
    Add(Add(Mul(Div(W, 0.9407928655128824), Mul(DD, 0.9407928655128824)), Sub(Sub(OP, DD), Add(Add(PT, OP), Sub(PT, PT)))), Add(Div(Div(AT, AT), Mul(0.9407928655128824, PT)), Mul(Mul(OP, PT), Div(Add(Mul(Mul(RT, RPT), Add(PT, PT)), Div(RPT, OP)), Sub(Sub(Div(Sub(PT, RPT), Mul(OP, PT)), Mul(Sub(RPT, W), Div(0.9407928655128824, W))), Add(Div(Div(RT, OP), Mul(W, RPT)), Mul(Add(Sub(RPT, 0.9407928655128824), Div(PT, W)), Div(DD, DD))))))))


    		;
    }
}
