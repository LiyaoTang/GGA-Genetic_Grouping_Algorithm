package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP1_6 extends GPRuleBase implements IMachineRule {

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
    Add(Add(Add(Mul(Div(W, 0.9407928655128824), Mul(DD, 0.9407928655128824)), Sub(Sub(OP, DD), Add(Add(PT, OP), Mul(Add(Add(Sub(PT, PT), Sub(Add(PT, PT), Mul(Sub(PT, Add(Div(Div(RT, OP), Mul(W, RPT)), Mul(Mul(DD, 0.9407928655128824), Div(Div(0.9407928655128824, PT), Mul(0.9407928655128824, PT))))), Div(Add(Div(RPT, OP), Div(RPT, OP)), Add(Add(PT, OP), Sub(PT, PT)))))), Add(Sub(PT, Mul(Sub(0.9407928655128824, PT), Add(0.9407928655128824, W))), Mul(Sub(OP, Add(Div(DD, 0.9407928655128824), Add(0.9407928655128824, W))), AT))), Mul(OP, RT))))), Add(Div(Div(AT, AT), W), Mul(Div(Sub(PT, RPT), Sub(OP, DD)), 0.9407928655128824))), Div(PT, Sub(Mul(AT, Div(Div(W, PT), Add(PT, RPT))), Add(OP, Mul(Add(Add(Mul(Div(W, 0.9407928655128824), Mul(DD, 0.9407928655128824)), Sub(Div(W, 0.9407928655128824), Add(Add(PT, OP), Sub(PT, PT)))), Add(Sub(PT, PT), Mul(Sub(PT, PT), Div(Add(Div(Mul(OP, RT), Div(DD, 0.9407928655128824)), Mul(Mul(Div(Sub(0.9407928655128824, AT), Mul(AT, PT)), Div(Div(W, PT), Mul(0.9407928655128824, PT))), Div(Div(PT, Div(W, DD)), Div(PT, Sub(PT, 0.9407928655128824))))), Mul(Div(W, 0.9407928655128824), Mul(DD, 0.9407928655128824)))))), Mul(OP, RT))))))


    		;
    }
}
