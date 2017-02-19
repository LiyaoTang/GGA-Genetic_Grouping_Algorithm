package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP4_14 extends GPRuleBase implements IMachineRule {

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
    Sub(Add(Add(Add(Div(Div(PT, W), Sub(RT, W)), Div(Sub(Div(W, Div(Div(Sub(Sub(Add(Sub(Div(PT, AT), Sub(RT, RPT)), RT), Div(RPT, W)), Div(PT, W)), Sub(RT, RPT)), Add(Div(W, Div(Div(Sub(Div(PT, AT), Div(PT, W)), Sub(RT, RPT)), Add(Div(PT, W), Div(Add(Sub(W, RT), Div(DD, PT)), Add(Sub(W, RT), W))))), Sub(RPT, RPT)))), RPT), Add(Div(PT, W), Div(RT, 0.44700106909664716)))), RT), Div(Sub(Div(W, Div(Div(Sub(W, Div(PT, W)), Sub(RT, RPT)), Add(Div(PT, W), Div(Sub(Div(W, Div(Div(Sub(Div(PT, AT), Div(PT, W)), Sub(RT, RPT)), Add(Div(PT, W), Div(Add(Sub(W, RT), Div(DD, PT)), Add(0.44700106909664716, W))))), RPT), Add(W, W))))), RPT), Add(Add(W, RT), Div(RT, 0.44700106909664716)))), Div(RPT, W))
    		;
    }
}
