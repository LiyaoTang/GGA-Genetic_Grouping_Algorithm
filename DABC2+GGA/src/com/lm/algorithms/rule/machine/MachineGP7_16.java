package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP7_16 extends GPRuleBase implements IMachineRule {

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
    Sub(Sub(Sub(Div(Mul(Add(Div(Mul(RPT, DD), Mul(W, W)), Add(PT, Sub(Div(Sub(Div(Mul(RPT, DD), Mul(W, W)), PT), Add(Mul(W, Div(W, PT)), W)), Div(Mul(RPT, DD), Mul(W, Mul(RT, 0.16774893863035956)))))), DD), 0.16774893863035956), Div(Mul(Div(Mul(RPT, DD), Mul(W, W)), DD), Mul(W, Div(W, PT)))), Sub(Div(W, Add(Div(RPT, Mul(W, W)), Add(PT, OP))), Div(Sub(Sub(Div(Sub(Mul(W, Add(PT, Sub(Div(Sub(Div(Mul(RPT, DD), Div(Mul(RPT, DD), Mul(W, W))), PT), Add(Mul(W, Div(W, PT)), W)), Div(Mul(RPT, DD), Mul(W, Mul(RT, 0.16774893863035956)))))), Sub(Div(Sub(Div(Sub(Div(Mul(RPT, DD), Mul(W, W)), PT), Add(RT, W)), Div(Mul(RPT, DD), W)), Sub(Mul(W, Div(W, PT)), Mul(RT, 0.16774893863035956))), PT)), RPT), Div(Div(Mul(RPT, DD), Mul(W, W)), Mul(W, W))), Div(Div(Mul(RPT, DD), Mul(W, W)), Mul(W, W))), Sub(Mul(W, Div(W, PT)), Mul(RT, 0.16774893863035956))))), Sub(Sub(Div(Sub(Sub(Div(Mul(RPT, DD), Mul(W, W)), PT), Sub(Div(Sub(Div(Sub(Div(Mul(RPT, DD), Mul(W, W)), Div(Mul(RPT, DD), Div(Mul(RPT, DD), Mul(W, W)))), Add(RT, W)), Div(Mul(W, Div(W, PT)), Mul(W, DD))), Sub(Mul(W, Div(W, PT)), Mul(RT, 0.16774893863035956))), PT)), RPT), Div(Mul(W, Mul(W, W)), Mul(W, W))), Div(Sub(Div(Sub(Add(Div(Mul(RPT, DD), Mul(W, W)), Sub(W, PT)), Sub(Mul(Div(Mul(W, Mul(W, W)), AT), Sub(Div(OP, W), Div(Sub(Div(Mul(Div(Mul(RPT, DD), Mul(W, W)), W), RPT), Div(Div(Mul(RPT, DD), Mul(W, W)), Mul(W, W))), Sub(Mul(Mul(W, W), Div(W, PT)), Mul(RT, 0.16774893863035956))))), Sub(Mul(RT, Div(OP, W)), PT))), RPT), Div(Div(Mul(RPT, DD), Add(RT, W)), Mul(W, W))), Sub(Mul(W, Mul(W, W)), Mul(RT, 0.16774893863035956)))))
    		;
    }
}
