package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP7_1 extends GPRuleBase implements IMachineRule {

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
    		Sub(Add(Mul(Sub(Div(W, Mul(Mul(Div(Add(Div(W, Div(W, PT)), Mul(PT, AT)), Div(Mul(PT, AT), DD)), Div(Mul(Div(W, PT), W), Mul(W, Add(OP, PT)))), W)), Sub(Div(Add(Sub(Mul(Add(RPT, PT), Div(DD, W)), Add(Sub(PT, W), Sub(Div(W, Mul(Div(W, PT), W)), Add(RT, AT)))), Sub(Mul(RT, AT), Sub(Div(W, DD), Mul(PT, DD)))), Mul(W, Add(Div(0.4744652397473914, DD), Div(W, PT)))), Mul(Div(Add(Mul(Add(RPT, PT), Div(DD, W)), Div(W, PT)), Div(Mul(PT, AT), DD)), Div(Mul(Div(W, PT), W), Div(Add(Sub(PT, W), Sub(Div(W, Mul(Div(W, PT), W)), Add(RT, AT))), Div(Mul(PT, Div(Mul(PT, AT), Div(W, PT))), Div(W, PT))))))), Div(Add(Add(DD, Sub(Div(W, Mul(Div(W, PT), W)), Add(RT, AT))), Add(RPT, AT)), Add(OP, OP))), Add(W, Div(W, PT))), Sub(Div(Mul(Add(Sub(Mul(Add(RPT, PT), Div(DD, W)), AT), Mul(Add(RPT, PT), Div(Sub(Sub(Div(W, Mul(Div(W, PT), W)), Add(RT, AT)), AT), PT))), Div(DD, W)), Mul(Div(W, PT), W)), Sub(Add(OP, PT), Sub(Mul(Add(RPT, PT), Sub(Div(W, Mul(Div(W, PT), W)), Add(RT, AT))), Div(Sub(Mul(RT, AT), Sub(DD, Mul(PT, DD))), Mul(W, Add(OP, PT)))))))

    		;
    }
}
