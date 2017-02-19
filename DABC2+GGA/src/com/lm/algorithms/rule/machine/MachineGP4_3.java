package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP4_3 extends GPRuleBase implements IMachineRule {

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
    		Sub(Add(Div(Sub(Mul(Add(Div(Sub(Mul(OP, 0.4876631468684328), Add(0.4876631468684328, RPT)), Mul(Mul(0.4876631468684328, W), Add(W, 0.4876631468684328))), Div(Add(Mul(RPT, RT), Sub(Mul(RPT, RT), Add(0.4876631468684328, Div(Sub(Mul(RPT, RT), Add(0.4876631468684328, RPT)), Mul(Mul(0.4876631468684328, W), Add(W, 0.4876631468684328)))))), Sub(Div(Sub(PT, 0.4876631468684328), Add(W, W)), Sub(Sub(0.4876631468684328, AT), Mul(RT, Mul(Sub(Add(RT, RPT), Div(OP, W)), Sub(Add(0.4876631468684328, RPT), RT))))))), RT), Add(0.4876631468684328, RPT)), Mul(Add(0.4876631468684328, Sub(W, 0.4876631468684328)), Add(W, 0.4876631468684328))), Div(Sub(0.4876631468684328, Add(Add(Sub(W, 0.4876631468684328), RT), RPT)), Mul(Sub(Add(Sub(RT, 0.4876631468684328), Mul(Add(0.4876631468684328, Sub(W, 0.4876631468684328)), Sub(Add(Div(RPT, W), Sub(PT, RT)), Sub(Add(Div(Sub(Mul(RPT, RT), Add(0.4876631468684328, RPT)), Mul(Mul(0.4876631468684328, W), Add(W, 0.4876631468684328))), Div(Add(Sub(DD, PT), Sub(Add(0.4876631468684328, Sub(W, 0.4876631468684328)), Sub(Mul(RPT, RT), Add(Div(OP, W), RPT)))), Sub(Div(Sub(PT, 0.4876631468684328), Add(W, W)), Div(W, W)))), Mul(0.4876631468684328, W))))), Sub(Add(OP, RPT), Div(W, Sub(Add(Div(Sub(Mul(RPT, RT), Add(0.4876631468684328, RPT)), Mul(Mul(0.4876631468684328, W), Add(W, 0.4876631468684328))), Div(Add(Div(Sub(Mul(RPT, RT), Add(W, 0.4876631468684328)), Mul(Add(0.4876631468684328, Sub(W, 0.4876631468684328)), Add(W, 0.4876631468684328))), Div(W, RPT)), Sub(Sub(Add(RT, RPT), Div(OP, W)), Sub(Mul(0.4876631468684328, W), Mul(RT, Mul(RPT, RT)))))), Mul(W, W))))), Div(Mul(0.4876631468684328, W), Div(PT, W))))), Div(Sub(Add(Sub(RT, 0.4876631468684328), Mul(W, 0.4876631468684328)), Sub(0.4876631468684328, RT)), Sub(Add(Sub(W, 0.4876631468684328), Sub(Add(RT, RPT), Div(OP, W))), Sub(Add(RT, RPT), Div(OP, W)))))


    		;
    }
}
