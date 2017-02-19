package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP1_8 extends GPRuleBase implements IMachineRule {

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
    Add(Add(Add(Mul(Div(W, 0.9407928655128824), Mul(DD, 0.9407928655128824)), Sub(Sub(OP, DD), Mul(PT, Div(AT, AT)))), Add(Div(0.9407928655128824, Mul(Div(W, 0.9407928655128824), Sub(PT, Mul(Div(Mul(AT, PT), Div(AT, AT)), Add(Mul(OP, W), Add(PT, OP)))))), Mul(Mul(Div(Sub(0.9407928655128824, AT), Mul(AT, PT)), Div(Div(0.9407928655128824, PT), Mul(0.9407928655128824, PT))), Div(Div(PT, Div(W, DD)), Div(PT, Sub(PT, 0.9407928655128824)))))), Mul(Div(Add(Add(Add(Mul(AT, AT), Add(Mul(OP, RT), Add(Div(Div(AT, AT), Div(W, DD)), Mul(Mul(RT, RPT), Div(Add(Mul(AT, PT), Div(RPT, OP)), Sub(Mul(AT, RT), Add(OP, Add(Div(DD, 0.9407928655128824), Add(0.9407928655128824, W))))))))), Sub(0.9407928655128824, Add(Div(Div(RT, OP), Add(Div(Sub(PT, PT), Mul(Div(W, 0.9407928655128824), Div(RT, AT))), Mul(Mul(RT, Mul(Mul(RT, RPT), Div(AT, Sub(RT, W)))), Mul(OP, RT)))), Mul(Add(Sub(OP, DD), Div(PT, W)), Div(DD, DD))))), Add(Add(Mul(Div(W, 0.9407928655128824), Mul(DD, 0.9407928655128824)), PT), Add(Div(Div(AT, AT), Div(DD, 0.9407928655128824)), Mul(Mul(Div(Div(Sub(OP, DD), W), Mul(AT, PT)), Div(Div(0.9407928655128824, PT), Mul(0.9407928655128824, PT))), Div(Div(PT, Div(W, DD)), Div(PT, Sub(PT, 0.9407928655128824))))))), Mul(Add(Mul(OP, RT), Add(Mul(OP, PT), Mul(Mul(OP, PT), Div(Add(Mul(Mul(Mul(0.9407928655128824, PT), RPT), Add(PT, OP)), Div(RPT, OP)), Sub(Sub(AT, Mul(Sub(PT, PT), Div(0.9407928655128824, W))), Sub(Sub(OP, DD), Add(Add(PT, OP), Sub(PT, PT)))))))), Sub(Add(Add(Add(Mul(Div(W, 0.9407928655128824), Mul(DD, Sub(Sub(OP, DD), Add(Add(PT, OP), Sub(Add(Add(PT, OP), Div(W, DD)), PT))))), Div(AT, AT)), Sub(PT, PT)), Div(Mul(Div(Mul(OP, RT), DD), Mul(Sub(Div(PT, AT), Div(W, 0.9407928655128824)), RT)), PT)), AT))), Mul(Div(Add(AT, RPT), Add(RT, PT)), Div(Div(PT, AT), Add(OP, W)))))
    ;
    }
}
