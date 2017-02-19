package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP1_7 extends GPRuleBase implements IMachineRule {

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
    Add(Add(Add(Mul(Div(W, 0.9407928655128824), Mul(DD, 0.9407928655128824)), Sub(Sub(OP, DD), Add(Add(PT, OP), Sub(PT, PT)))), Add(Div(Mul(Add(Div(DD, 0.9407928655128824), Add(Sub(PT, PT), Add(Add(Mul(Div(W, 0.9407928655128824), Mul(DD, 0.9407928655128824)), Sub(Sub(OP, DD), Mul(PT, Div(AT, AT)))), Div(Div(AT, AT), Mul(0.9407928655128824, PT))))), Mul(OP, RT)), Div(Add(Div(Mul(OP, RT), Add(Add(Div(AT, Div(PT, W)), Sub(Div(AT, AT), Add(Mul(DD, 0.9407928655128824), Mul(OP, AT)))), Div(RPT, OP))), Mul(Add(Add(Mul(Div(W, 0.9407928655128824), Mul(DD, 0.9407928655128824)), Mul(AT, PT)), Add(Mul(OP, RT), Add(Div(PT, Div(W, DD)), Mul(Add(0.9407928655128824, W), Add(AT, RPT))))), Mul(Mul(Div(Div(Sub(OP, DD), W), Mul(AT, PT)), Div(Div(0.9407928655128824, PT), Mul(0.9407928655128824, PT))), Div(Div(PT, Add(Add(Add(Mul(Div(W, 0.9407928655128824), Mul(DD, 0.9407928655128824)), Sub(Sub(OP, DD), Add(Div(RPT, OP), Sub(PT, PT)))), Sub(Sub(OP, DD), Mul(PT, Div(AT, Mul(RPT, RT))))), Add(Div(Div(AT, AT), PT), Mul(Mul(Div(Sub(0.9407928655128824, AT), Mul(AT, PT)), Div(Div(0.9407928655128824, PT), Mul(0.9407928655128824, PT))), Div(Div(PT, Div(W, DD)), Div(PT, Sub(PT, 0.9407928655128824))))))), Mul(Div(W, 0.9407928655128824), Mul(DD, 0.9407928655128824)))))), Mul(OP, RT))), Mul(Mul(OP, PT), Mul(OP, RT)))), Div(Add(Div(DD, 0.9407928655128824), Add(0.9407928655128824, W)), Sub(Mul(AT, RT), Add(OP, Mul(Mul(DD, 0.9407928655128824), Mul(OP, RT))))))


    		;
    }
}
