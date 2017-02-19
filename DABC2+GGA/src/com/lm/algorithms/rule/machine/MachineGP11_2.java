package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP11_2 extends GPRuleBase implements IMachineRule {

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
    Add(Sub(Div(Mul(DD, AT), Add(PT, W)), Mul(Div(DD, Sub(Add(Sub(DD, RPT), Mul(Sub(OP, PT), Div(Div(Div(Add(0.009972918673652131, RT), Div(Add(0.009972918673652131, RT), Sub(Add(RT, OP), OP))), Add(PT, Mul(Sub(Sub(DD, RPT), Sub(OP, Div(RPT, W))), DD))), PT))), Sub(Add(RT, OP), Div(Div(Add(0.009972918673652131, RT), Sub(0.009972918673652131, OP)), Div(Mul(Sub(Div(Sub(Sub(Sub(Sub(RPT, PT), Mul(W, W)), RT), Sub(Div(OP, RT), Sub(OP, Div(RPT, W)))), Sub(DD, RPT)), RT), W), DD))))), Div(Mul(PT, DD), Mul(W, W)))), Sub(Add(Sub(DD, RPT), W), Sub(Div(Add(0.009972918673652131, RT), Sub(0.009972918673652131, OP)), Div(Div(Add(0.009972918673652131, RT), Sub(0.009972918673652131, OP)), Div(Mul(Sub(0.009972918673652131, RT), W), DD)))))
    		;
    }
}
