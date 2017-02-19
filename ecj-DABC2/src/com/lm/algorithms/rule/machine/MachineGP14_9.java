package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP14_9 extends GPRuleBase implements IMachineRule {

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
    Mul(Mul(Sub(Add(Add(Div(Add(Mul(Sub(Div(DD, 0.9211457460076474), Div(Div(Div(RPT, Add(Add(RT, W), Div(W, 0.9211457460076474))), Sub(RT, W)), W)), Div(Div(RPT, OP), Sub(RT, Mul(W, W)))), Div(Div(Div(RPT, Add(Add(RT, W), Div(RPT, W))), Sub(RT, W)), W)), Sub(Mul(Add(W, RT), Mul(W, DD)), RT)), Mul(W, Div(Div(Div(DD, 0.9211457460076474), Sub(Div(RT, W), Div(RPT, Add(RT, DD)))), Add(Div(RPT, W), W)))), Mul(Add(W, RT), Add(PT, RT))), Sub(Div(RPT, W), Div(RPT, OP))), Div(Add(Mul(DD, DD), 0.9211457460076474), Mul(Div(OP, PT), Add(RT, DD)))), Div(Mul(Div(Sub(Div(Add(Div(DD, 0.9211457460076474), Div(W, 0.9211457460076474)), Sub(Div(DD, W), Sub(Div(RPT, W), Mul(RT, OP)))), Div(RPT, W)), Div(Div(DD, 0.9211457460076474), Sub(Div(RT, W), Sub(Div(DD, 0.9211457460076474), Div(AT, DD))))), Mul(W, 0.9211457460076474)), W))
    		;
    }
}
