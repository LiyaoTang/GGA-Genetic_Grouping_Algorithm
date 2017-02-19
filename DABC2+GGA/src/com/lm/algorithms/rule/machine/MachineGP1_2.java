package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP1_2 extends GPRuleBase implements IMachineRule {

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
    		Mul(Add(Add(Max(PT, RPT), Max(Add(Add(Div(Div(W, PT), Mul(OP, OP)), Add(Div(W, 0.5969778411451434), Div(Sub(0.5969778411451434, RPT), Sub(RT, RPT)))), Add(Max(Div(RT, RPT), Max(W, PT)), PT)), PT)), Max(Max(Add(Max(Add(Div(Mul(PT, RPT), 0.5969778411451434), Max(Div(RT, RPT), Sub(PT, OP))), Sub(PT, OP)), Div(Sub(0.5969778411451434, RPT), Div(W, PT))), PT), Div(Mul(W, OP), Add(RPT, Add(Add(Sub(Sub(Sub(Max(Max(Div(Add(DD, DD), Max(W, PT)), RPT), RPT), Div(RPT, W)), Max(DD, RPT)), Div(Div(W, 0.5969778411451434), Add(AT, DD))), Add(Max(Div(W, PT), Sub(PT, OP)), Div(Sub(0.5969778411451434, RPT), Div(W, PT)))), Max(Mul(Max(Div(Sub(RT, RPT), Div(0.5969778411451434, W)), Max(Max(RPT, RPT), RPT)), Max(Add(OP, RT), Div(RPT, W))), Max(Sub(RT, RPT), Sub(Sub(W, PT), Max(W, RPT))))))))), Sub(Max(Div(Add(DD, DD), Max(W, PT)), RPT), Div(RPT, W)))
    		;
    }
}
