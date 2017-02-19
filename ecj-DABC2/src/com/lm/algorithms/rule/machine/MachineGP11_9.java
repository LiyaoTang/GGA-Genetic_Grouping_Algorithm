package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP11_9 extends GPRuleBase implements IMachineRule {

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
    Mul(Div(Div(W, Div(W, PT)), Add(Div(Add(Sub(W, Sub(Mul(Add(0.8108706010560626, DD), Sub(W, Div(Mul(Div(Mul(PT, RPT), Add(OP, W)), RPT), Mul(W, RPT)))), Mul(PT, RPT))), Add(Mul(Add(OP, W), Add(Add(RT, RPT), Add(Add(0.8108706010560626, DD), Div(RPT, Add(Add(RT, RPT), Add(Add(0.8108706010560626, DD), Div(RPT, Div(RPT, PT)))))))), Add(0.8108706010560626, DD))), Sub(Mul(Add(OP, W), Add(RPT, Add(Add(0.8108706010560626, DD), Div(RPT, Div(RPT, PT))))), Mul(OP, Mul(RT, Div(RPT, PT))))), Mul(Add(OP, Div(PT, PT)), Div(W, Mul(Add(OP, W), Div(W, Div(Mul(PT, RPT), Mul(W, RPT)))))))), Div(Div(W, Div(PT, 0.8108706010560626)), Add(Div(W, Div(PT, 0.8108706010560626)), Add(RT, RPT))))
    		;
    }
}
