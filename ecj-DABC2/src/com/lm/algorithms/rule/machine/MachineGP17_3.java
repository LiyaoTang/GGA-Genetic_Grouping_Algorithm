package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP17_3 extends GPRuleBase implements IMachineRule {

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
    Sub(Add(Div(Add(Div(Add(Div(Mul(PT, RPT), Mul(Mul(0.8101171926223868, W), W)), Add(Sub(W, 0.8101171926223868), Mul(Div(Sub(Add(Sub(W, 0.8101171926223868), Sub(PT, RT)), Add(Div(Sub(Sub(Add(Sub(0.8101171926223868, 0.8101171926223868), Div(PT, W)), RPT), RPT), Div(Mul(PT, RPT), PT)), RT)), Div(Div(Mul(PT, RPT), Sub(0.8101171926223868, 0.8101171926223868)), PT)), RT))), Div(W, 0.8101171926223868)), Add(Sub(RPT, DD), Sub(Div(RPT, W), Sub(W, Add(DD, RT))))), Div(Sub(Sub(Sub(Sub(Add(Sub(0.8101171926223868, 0.8101171926223868), Div(PT, W)), RPT), RPT), Sub(W, 0.8101171926223868)), Add(DD, RT)), Add(Div(Div(Div(Add(Div(Add(Div(Mul(PT, RPT), Mul(Mul(0.8101171926223868, W), W)), Mul(PT, RPT)), Div(W, 0.8101171926223868)), Add(Add(Div(Mul(PT, RPT), Mul(Mul(0.8101171926223868, W), W)), Sub(0.8101171926223868, 0.8101171926223868)), Sub(Div(RPT, W), DD))), Div(Sub(Sub(Sub(Sub(Add(Sub(0.8101171926223868, 0.8101171926223868), Div(PT, W)), RPT), RPT), Sub(W, 0.8101171926223868)), 0.8101171926223868), Add(Div(Div(Div(DD, W), Sub(W, 0.8101171926223868)), PT), Add(PT, DD)))), Sub(Div(RPT, W), Sub(W, Add(DD, RT)))), PT), Add(PT, DD)))), Mul(Add(Mul(0.8101171926223868, W), Div(PT, W)), Sub(Sub(Sub(Div(Mul(Sub(RT, W), DD), Div(Div(Mul(PT, RPT), Mul(0.8101171926223868, W)), DD)), Div(Div(DD, W), W)), RPT), W))), Div(W, PT))
    		;
    }
}
