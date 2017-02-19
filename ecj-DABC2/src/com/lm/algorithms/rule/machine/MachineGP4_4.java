package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP4_4 extends GPRuleBase implements IMachineRule {

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
    		Sub(Add(Div(Sub(Mul(Add(Div(Sub(Div(PT, W), Add(0.4876631468684328, Sub(0.4876631468684328, Add(Mul(0.4876631468684328, W), RPT)))), Mul(Mul(0.4876631468684328, W), Add(Add(Div(Sub(Mul(RPT, RT), Add(0.4876631468684328, RPT)), Sub(DD, PT)), Div(Sub(W, 0.4876631468684328), Div(W, W))), 0.4876631468684328))), Div(Add(Sub(DD, Div(Sub(Mul(RPT, RT), Add(0.4876631468684328, RPT)), Div(OP, W))), Div(W, RPT)), Sub(Div(Sub(PT, 0.4876631468684328), Add(W, W)), Sub(Add(0.4876631468684328, RPT), Add(Sub(RT, 0.4876631468684328), Mul(W, 0.4876631468684328)))))), RT), Add(0.4876631468684328, RPT)), Mul(Add(0.4876631468684328, Sub(W, 0.4876631468684328)), Add(W, 0.4876631468684328))), Div(Sub(0.4876631468684328, Add(Add(Sub(W, 0.4876631468684328), RT), RPT)), Mul(Sub(Add(Sub(RT, 0.4876631468684328), Mul(Add(0.4876631468684328, Sub(W, 0.4876631468684328)), Sub(Add(Div(RPT, W), Sub(PT, RT)), Sub(Add(Div(Sub(Mul(RPT, RT), Add(0.4876631468684328, RPT)), Mul(Mul(0.4876631468684328, W), Add(W, 0.4876631468684328))), Div(Add(Sub(DD, PT), Sub(Add(0.4876631468684328, Sub(W, 0.4876631468684328)), Sub(Mul(RPT, RT), Add(Div(OP, W), RPT)))), Sub(Div(Sub(PT, 0.4876631468684328), Add(W, W)), Div(W, W)))), Mul(W, W))))), Sub(Add(RT, RPT), Div(OP, W))), Div(Mul(0.4876631468684328, W), Div(PT, W))))), Div(Sub(Add(Sub(RT, 0.4876631468684328), Mul(W, 0.4876631468684328)), Sub(0.4876631468684328, RT)), Sub(Add(Sub(W, 0.4876631468684328), Sub(Add(RT, RPT), Div(OP, W))), Sub(Add(RT, RPT), Div(OP, W)))))


    		;
    }
}
