package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP2_8 extends GPRuleBase implements IMachineRule {

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
    Sub(Mul(Div(Div(0.32939697119763023, Sub(W, RT)), Div(Sub(W, RT), Add(OP, DD))), Mul(Add(Div(Sub(PT, Mul(Div(PT, PT), Sub(W, 0.32939697119763023))), Sub(W, RT)), Sub(OP, OP)), Mul(Div(Div(Sub(PT, RT), Sub(W, RT)), Div(W, Add(OP, DD))), Mul(Add(Div(0.32939697119763023, Sub(W, RT)), Div(Div(W, OP), Mul(Div(PT, PT), Sub(W, 0.32939697119763023)))), Sub(Add(PT, W), Add(PT, DD)))))), Add(Add(Add(Mul(Mul(Mul(AT, OP), Sub(PT, Add(Div(Sub(W, RT), Add(OP, DD)), Add(OP, Sub(W, RT))))), Mul(Div(PT, Mul(Div(Div(Sub(PT, RT), Sub(W, RT)), Sub(W, RT)), Mul(Add(Div(0.32939697119763023, Sub(W, RT)), Div(Div(W, OP), Mul(Div(PT, PT), Div(Div(Sub(PT, RT), Sub(W, RT)), Div(W, Add(OP, DD)))))), Sub(Add(PT, W), Sub(W, RT))))), Sub(W, 0.32939697119763023))), W), Add(Mul(OP, Sub(Mul(Sub(Mul(Div(Div(0.32939697119763023, Sub(W, RT)), Div(Sub(W, RT), Add(OP, DD))), Mul(Add(Div(Sub(PT, RT), Sub(W, RT)), Sub(OP, OP)), Mul(Div(Div(Sub(PT, RT), Sub(W, RT)), Div(Sub(W, RT), Add(OP, DD))), Mul(Add(Div(0.32939697119763023, Sub(W, RT)), Div(Div(W, OP), OP)), Sub(Add(PT, W), Add(PT, DD)))))), Sub(OP, OP)), Mul(Add(Div(0.32939697119763023, DD), Sub(OP, OP)), Sub(Add(PT, W), Add(PT, DD)))), Div(Sub(PT, RT), Sub(W, RT)))), Sub(AT, AT))), Div(Div(0.32939697119763023, Mul(Add(Div(Sub(PT, RT), Sub(W, RT)), Sub(OP, OP)), Mul(Div(Div(Sub(PT, RT), Sub(W, RT)), Div(Sub(W, RT), Add(OP, DD))), Mul(Add(Div(0.32939697119763023, Sub(W, RT)), Div(Div(W, OP), OP)), Sub(Add(PT, W), Add(PT, DD)))))), Div(Sub(W, RT), Add(OP, DD)))))
    		;
    }
}
