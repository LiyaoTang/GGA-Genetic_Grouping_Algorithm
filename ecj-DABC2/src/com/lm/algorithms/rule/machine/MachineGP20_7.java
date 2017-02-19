package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP20_7 extends GPRuleBase implements IMachineRule {

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
    Mul(Mul(Div(Add(DD, RPT), Add(W, Div(Div(W, OP), OP))), Add(Div(PT, RT), W)), Sub(Mul(Mul(Div(Add(OP, PT), Add(Mul(0.024333341593756885, W), Div(Div(W, Sub(0.024333341593756885, Div(Add(DD, RPT), Add(W, Add(Mul(0.024333341593756885, W), Div(Div(W, Sub(0.024333341593756885, PT)), Div(DD, W))))))), Div(DD, W)))), Add(Add(W, RT), Div(OP, RT))), Sub(Div(Sub(RT, PT), Div(Div(W, OP), OP)), Div(Add(Add(Mul(Div(Div(W, 0.024333341593756885), Div(DD, W)), Mul(Div(Add(DD, RPT), Add(W, Div(Div(W, OP), OP))), Add(Div(PT, RT), W))), Div(Div(W, Sub(0.024333341593756885, PT)), Div(Add(OP, PT), PT))), Add(Add(Sub(RT, PT), Sub(Mul(Mul(Div(Add(OP, PT), Div(W, 0.024333341593756885)), Add(Add(Div(PT, RT), W), Div(OP, RT))), Sub(Sub(RT, PT), Add(W, RT))), Mul(RT, OP))), Add(Div(0.024333341593756885, RPT), Sub(Div(Sub(RT, PT), Mul(Mul(W, Div(W, PT)), W)), Add(Div(Div(Div(Sub(RT, PT), Mul(Mul(W, Div(W, PT)), W)), PT), W), Add(0.024333341593756885, DD)))))), Sub(0.024333341593756885, W)))), Div(Div(W, PT), Div(Add(Add(Mul(Div(Div(W, 0.024333341593756885), Div(DD, W)), Div(Div(Div(W, OP), OP), RPT)), Div(Add(W, RT), Add(Div(Div(Sub(RT, PT), Add(W, RT)), RT), Div(Add(W, RT), Div(Add(OP, PT), Div(W, Div(DD, W))))))), Add(Add(Sub(RT, PT), Sub(Mul(Mul(Div(Add(OP, PT), Div(W, 0.024333341593756885)), Add(Div(Add(OP, PT), Div(W, 0.024333341593756885)), Div(OP, RT))), Sub(Sub(RT, PT), Add(W, PT))), Mul(RT, OP))), Add(Div(0.024333341593756885, RPT), Sub(Div(Sub(RT, PT), Mul(Mul(W, Div(W, PT)), W)), Add(Div(Div(W, PT), W), Add(0.024333341593756885, DD)))))), Sub(0.024333341593756885, W)))))
    		;
    }
}
