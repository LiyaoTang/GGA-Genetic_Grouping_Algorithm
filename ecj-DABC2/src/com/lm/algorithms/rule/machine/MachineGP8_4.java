package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP8_4 extends GPRuleBase implements IMachineRule {

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
    		Div(Mul(Div(W, Add(Mul(Sub(Div(0.6379037649207191, 0.6379037649207191), Div(W, Mul(W, OP))), Div(DD, W)), Sub(RPT, W))), Sub(Div(0.6379037649207191, 0.6379037649207191), Div(Div(W, Add(Mul(Div(W, Add(Mul(Sub(Div(W, Sub(Div(0.6379037649207191, 0.6379037649207191), Div(W, Mul(W, OP)))), W), Div(Div(W, Mul(Sub(Div(0.6379037649207191, 0.6379037649207191), Div(W, Mul(W, OP))), Div(DD, W))), Sub(Mul(W, OP), OP))), Sub(RPT, Add(PT, OP)))), Div(Sub(Div(0.6379037649207191, 0.6379037649207191), Div(Div(W, PT), Sub(OP, OP))), Add(Mul(Mul(Sub(Div(0.6379037649207191, 0.6379037649207191), Div(W, Mul(W, OP))), Div(DD, W)), Add(Mul(Div(RPT, PT), Div(W, PT)), Sub(RPT, W))), Add(Mul(Sub(Div(0.6379037649207191, 0.6379037649207191), Div(W, Mul(W, OP))), Div(DD, W)), Sub(RPT, W))))), Sub(RPT, W))), Sub(Mul(W, OP), OP)))), Div(Add(RPT, Add(Sub(Add(RPT, Add(Sub(W, RT), Sub(0.6379037649207191, OP))), RT), Sub(Mul(W, OP), OP))), Div(W, PT)))


    		;
    }
}
