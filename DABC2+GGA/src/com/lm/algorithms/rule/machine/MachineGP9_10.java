package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP9_10 extends GPRuleBase implements IMachineRule {

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
    Mul(Add(Sub(Mul(Add(Add(W, Div(OP, W)), Sub(RPT, Mul(Div(W, PT), W))), Sub(Mul(RT, PT), Div(OP, W))), Div(DD, W)), Sub(Add(PT, OP), Sub(DD, 0.6185684576927603))), Add(Mul(Mul(W, Div(AT, RT)), Div(Sub(Add(Sub(Sub(Mul(Add(Add(DD, PT), RT), Sub(OP, Add(W, Div(OP, W)))), Div(DD, W)), Sub(Mul(AT, OP), Add(Mul(Add(Mul(Div(Div(Div(DD, W), W), RPT), Add(W, Div(OP, W))), Add(Div(RPT, W), Add(Sub(Mul(RT, PT), Div(OP, W)), Sub(DD, 0.6185684576927603)))), Div(Mul(Div(W, Mul(OP, DD)), W), Div(RT, AT))), RT))), Mul(Sub(PT, Div(DD, W)), Add(PT, OP))), Div(Add(Add(W, W), Add(RPT, Mul(Sub(PT, Add(Add(W, Div(OP, W)), Sub(RPT, 0.6185684576927603))), Div(Div(DD, W), W)))), Mul(Add(OP, W), Sub(Mul(Sub(PT, Div(DD, W)), Add(RPT, Mul(Add(OP, W), Div(DD, W)))), Add(RPT, Mul(Add(OP, W), Mul(Add(Sub(Mul(0.6185684576927603, W), Add(W, Div(OP, W))), Sub(Mul(Mul(W, W), Mul(Div(RT, RPT), Div(RT, AT))), Div(RPT, Mul(Div(W, PT), W)))), Add(Mul(Div(RT, RPT), Mul(Sub(Mul(RT, PT), Div(OP, W)), Div(AT, RT))), Add(Div(RPT, W), Div(AT, OP)))))))))), OP)), Mul(Div(Div(DD, W), W), PT)))
    		;
    }
}
