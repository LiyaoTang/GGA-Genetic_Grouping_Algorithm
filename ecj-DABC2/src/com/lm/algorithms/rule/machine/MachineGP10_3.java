package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP10_3 extends GPRuleBase implements IMachineRule {

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
    Mul(Mul(Add(Div(OP, Sub(Div(Div(Sub(Add(OP, PT), Sub(PT, W)), Sub(Div(Div(PT, OP), Div(W, RPT)), Div(Mul(Mul(AT, PT), W), Sub(Mul(Div(W, RPT), Div(Add(0.7484418042669487, Sub(PT, W)), Div(W, PT))), Sub(Sub(Mul(PT, 0.7484418042669487), Div(AT, Div(RPT, W))), OP))))), Div(W, PT)), Div(Mul(Div(W, Mul(Add(OP, Mul(Add(Mul(Add(OP, PT), Div(W, PT)), Div(W, PT)), Add(Mul(Add(OP, PT), Add(OP, DD)), Mul(PT, 0.7484418042669487)))), Div(Add(OP, DD), Div(Add(RPT, OP), Mul(RPT, PT))))), W), Sub(Div(W, PT), Sub(Sub(Mul(PT, 0.7484418042669487), Div(AT, Div(RPT, W))), OP))))), Add(DD, DD)), Sub(Div(W, Div(W, RPT)), Add(Div(Mul(Mul(Div(RT, RT), Add(OP, DD)), Add(Mul(Add(OP, PT), Div(W, PT)), Div(W, PT))), Sub(Div(Div(PT, OP), Div(W, RPT)), Div(Mul(Mul(AT, PT), W), Sub(Mul(Div(W, RPT), Div(Add(0.7484418042669487, Sub(PT, W)), Div(W, PT))), Sub(Sub(Mul(PT, 0.7484418042669487), Div(AT, Div(RPT, W))), OP))))), Mul(RPT, PT)))), Div(Add(Sub(Mul(Div(W, 0.7484418042669487), Add(Sub(Add(RT, 0.7484418042669487), Div(Div(RT, AT), DD)), Div(Add(RPT, OP), Mul(W, W)))), Div(AT, DD)), Div(Add(Sub(Div(Div(PT, OP), Div(W, RPT)), Div(Mul(Sub(AT, RT), Div(W, PT)), Div(W, PT))), OP), Mul(W, W))), Mul(Mul(Div(RT, RT), Add(OP, DD)), Add(Mul(Add(OP, PT), Div(W, PT)), Div(W, PT)))))
    		;
    }
}
