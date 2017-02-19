package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP8_6 extends GPRuleBase implements IMachineRule {

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
    Mul(Mul(Add(Div(PT, W), Mul(DD, RT)), Sub(Add(Add(Add(Mul(DD, W), Add(W, Sub(Sub(0.9169920168800951, RPT), Sub(DD, 0.9169920168800951)))), Add(PT, Sub(Sub(0.9169920168800951, RPT), Sub(DD, 0.9169920168800951)))), Sub(Div(Add(Add(Mul(DD, W), Add(W, Div(DD, W))), Add(PT, Sub(Sub(0.9169920168800951, Add(Add(W, 0.9169920168800951), Add(Sub(PT, RPT), Sub(Sub(0.9169920168800951, RPT), Sub(DD, 0.9169920168800951))))), Add(Div(PT, W), PT)))), Mul(0.9169920168800951, RPT)), Sub(Sub(Div(DD, W), Div(Add(W, Sub(Sub(Sub(Div(PT, W), RPT), Sub(DD, 0.9169920168800951)), Div(Add(W, W), W))), Sub(Add(W, 0.9169920168800951), Add(Add(W, 0.9169920168800951), Add(Sub(PT, RPT), Add(Mul(DD, W), Add(W, W))))))), Div(Mul(DD, W), Sub(Div(OP, 0.9169920168800951), Add(Div(Div(Add(W, 0.9169920168800951), Sub(DD, 0.9169920168800951)), Div(DD, W)), Mul(Mul(Mul(Sub(Div(W, DD), Sub(Add(Mul(0.9169920168800951, RT), Mul(0.9169920168800951, RPT)), 0.9169920168800951)), Mul(DD, W)), Mul(Div(0.9169920168800951, PT), Div(W, DD))), W))))))), Sub(Div(DD, W), W))), Div(Add(RPT, W), W))
 	;
    }
}
