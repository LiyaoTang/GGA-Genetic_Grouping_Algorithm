package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP1_9 extends GPRuleBase implements IMachineRule {

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
    Add(Add(Mul(PT, Add(Add(Add(Mul(Div(W, 0.9407928655128824), Mul(DD, 0.9407928655128824)), Sub(Sub(OP, DD), Add(Add(PT, OP), Add(Mul(Div(W, 0.9407928655128824), Mul(DD, 0.9407928655128824)), Sub(Sub(OP, DD), Div(W, 0.9407928655128824)))))), Add(Div(Add(Div(Div(AT, AT), Div(DD, 0.9407928655128824)), Mul(Div(Add(PT, Mul(Div(RT, OP), Div(Div(0.9407928655128824, PT), Mul(0.9407928655128824, PT)))), Mul(Mul(Div(Div(Div(AT, AT), W), Mul(AT, PT)), Div(Div(0.9407928655128824, PT), Mul(0.9407928655128824, PT))), Div(Div(PT, Div(W, DD)), Div(PT, Sub(PT, 0.9407928655128824))))), Mul(OP, RT))), Div(DD, Mul(RPT, Add(Sub(PT, PT), Sub(PT, PT))))), Mul(OP, RT))), Div(Add(Mul(Div(W, 0.9407928655128824), Mul(DD, 0.9407928655128824)), Mul(Div(Div(W, 0.9407928655128824), DD), Mul(RT, RT))), Sub(Mul(AT, RT), Add(OP, Mul(OP, Add(OP, Mul(Mul(DD, 0.9407928655128824), Mul(OP, RT))))))))), Add(Div(Div(RT, OP), Mul(Mul(Div(Sub(0.9407928655128824, AT), Mul(AT, PT)), Div(Div(0.9407928655128824, PT), Mul(0.9407928655128824, PT))), 0.9407928655128824)), Mul(Add(Mul(Div(W, 0.9407928655128824), Mul(DD, 0.9407928655128824)), Sub(Sub(OP, DD), Add(Add(PT, OP), Sub(PT, PT)))), Div(Div(PT, Div(W, DD)), Div(Sub(PT, 0.9407928655128824), Sub(PT, 0.9407928655128824)))))), Mul(Mul(OP, RT), Mul(Add(Div(DD, 0.9407928655128824), Add(Div(Sub(OP, Div(W, 0.9407928655128824)), OP), Mul(Mul(Mul(Mul(Mul(RT, RPT), Sub(OP, DD)), RPT), Add(Add(Add(PT, OP), Sub(PT, PT)), Sub(PT, PT))), Sub(OP, DD)))), Div(Div(PT, AT), Add(OP, W)))))


    		;
    }
}
