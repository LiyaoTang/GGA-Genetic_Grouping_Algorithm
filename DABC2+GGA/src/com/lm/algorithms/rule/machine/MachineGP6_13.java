package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP6_13 extends GPRuleBase implements IMachineRule {

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
    Sub(Mul(Div(Sub(Div(RPT, W), Div(Div(Add(Div(Add(OP, W), W), Div(Div(Mul(Div(RPT, W), Div(RPT, W)), AT), Div(Mul(Div(Div(Sub(0.9185244575398441, PT), W), W), Div(Sub(0.9185244575398441, PT), W)), Sub(0.9185244575398441, PT)))), Div(Mul(Div(Sub(Div(RPT, W), Div(Mul(Div(RPT, W), Div(RPT, W)), AT)), AT), Add(OP, W)), Div(AT, DD))), W)), AT), Div(Sub(Div(RPT, W), Div(Div(Add(Div(RPT, W), Mul(Div(RPT, W), Div(Div(Mul(0.9185244575398441, Mul(RT, W)), Div(Mul(Sub(Div(RPT, W), Div(Div(Sub(0.9185244575398441, PT), W), W)), Div(Sub(0.9185244575398441, PT), W)), Div(AT, DD))), W))), Div(Mul(Div(Sub(Div(RPT, W), Div(Mul(Div(RPT, W), Div(RPT, W)), AT)), AT), Add(OP, W)), Div(AT, DD))), W)), AT)), Sub(Div(RPT, W), Div(Div(Sub(0.9185244575398441, PT), W), W)))
    		;
    }
}
