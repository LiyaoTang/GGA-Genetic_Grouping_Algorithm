package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP11_7 extends GPRuleBase implements IMachineRule {

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
    Mul(Div(Div(W, Div(W, PT)), Add(Div(Add(Sub(Mul(W, W), Sub(Mul(Add(Div(W, Div(Mul(PT, RPT), Mul(W, RPT))), DD), Sub(W, Div(Mul(Div(Mul(PT, RPT), Add(OP, W)), RPT), Mul(W, RPT)))), Div(RPT, Div(W, PT)))), Add(Sub(Add(Add(Div(W, Div(W, Div(PT, 0.8108706010560626))), Mul(W, W)), RPT), Mul(Mul(RT, PT), Mul(RT, PT))), Mul(Add(OP, W), Div(W, Div(Mul(PT, RPT), Mul(W, RPT)))))), Sub(Mul(Add(OP, W), Add(Add(RT, RPT), Add(Add(0.8108706010560626, DD), Div(RPT, Div(RPT, PT))))), Mul(OP, Mul(RT, Sub(Div(W, Mul(Add(OP, W), Div(W, Div(Mul(PT, RPT), Mul(W, RPT))))), Mul(PT, RPT)))))), Mul(Add(OP, W), Div(W, Mul(Add(OP, W), Div(W, Div(Mul(PT, RPT), Mul(W, RPT)))))))), Div(Div(W, Div(PT, 0.8108706010560626)), Add(OP, Add(RT, RPT))))
    		;
    }
}
