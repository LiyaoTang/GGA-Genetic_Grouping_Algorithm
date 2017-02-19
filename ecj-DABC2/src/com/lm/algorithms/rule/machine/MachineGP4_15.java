package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP4_15 extends GPRuleBase implements IMachineRule {

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
    Sub(Add(Add(Add(Div(Div(PT, Sub(W, RT)), Sub(RT, W)), Div(Sub(Div(W, Div(Div(Sub(Sub(Add(Div(DD, PT), RT), Div(RPT, W)), Div(PT, W)), Sub(RT, RPT)), Add(Sub(RPT, OP), Sub(RPT, RPT)))), RPT), Add(Add(W, RT), Div(RT, 0.44700106909664716)))), RT), Div(Sub(Div(W, Div(Div(Sub(Div(PT, AT), Div(PT, W)), Sub(RT, RPT)), Add(Div(PT, W), Div(Sub(Div(W, Div(Div(Sub(Div(PT, AT), Div(PT, W)), Sub(RT, RPT)), Add(Div(PT, W), Div(Add(Sub(W, RT), Div(DD, PT)), Add(0.44700106909664716, W))))), RPT), Add(W, W))))), RPT), Add(Add(W, RT), Div(RT, 0.44700106909664716)))), Div(RPT, W))
    ;
    }
}
