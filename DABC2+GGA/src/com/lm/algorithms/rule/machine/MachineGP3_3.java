package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP3_3 extends GPRuleBase implements IMachineRule {

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
    		Add(Div(W, Sub(Sub(Mul(Sub(Sub(Sub(RT, RT), Add(RPT, RPT)), Add(RPT, RPT)), Div(0.8362019453506385, Mul(OP, W))), Div(Sub(OP, W), Mul(Mul(OP, W), Div(Add(AT, DD), Sub(Div(Mul(Add(Mul(OP, DD), DD), Div(RPT, Add(PT, PT))), Add(PT, RT)), Add(Sub(W, PT), Div(W, PT))))))), Sub(Sub(Mul(Sub(Sub(RT, RT), Add(RPT, RPT)), Sub(Add(Add(Sub(PT, Div(AT, PT)), Sub(Add(RT, RPT), Mul(PT, W))), Div(Mul(OP, DD), Sub(W, PT))), Div(Add(RPT, OP), Add(RPT, RPT)))), Div(Sub(OP, W), Sub(Sub(Mul(DD, W), Mul(Mul(Div(Add(PT, RT), Add(W, 0.8362019453506385)), W), W)), Mul(DD, DD)))), Div(Sub(PT, 0.8362019453506385), Sub(Add(Mul(Sub(Add(RPT, OP), Sub(W, PT)), Mul(W, 0.8362019453506385)), Add(Mul(Add(AT, 0.8362019453506385), Mul(0.8362019453506385, AT)), Div(Mul(W, 0.8362019453506385), Sub(W, Div(Mul(Add(W, DD), Div(RPT, AT)), Add(PT, RT)))))), Mul(PT, W)))))), Add(PT, Sub(Sub(Add(Mul(DD, W), Div(Sub(Add(RT, RPT), Mul(OP, DD)), Sub(W, PT))), Div(0.8362019453506385, Mul(DD, PT))), Add(DD, Add(PT, PT)))))
    		;
    }
}
