package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP3_4 extends GPRuleBase implements IMachineRule {

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
    		Add(Div(W, Sub(Sub(Mul(Sub(Sub(RT, RT), Add(RPT, RPT)), Add(DD, 0.8362019453506385)), Div(Sub(RT, RT), Sub(Mul(DD, W), Sub(Add(RPT, OP), Sub(W, PT))))), Div(Add(Sub(Div(Add(RPT, AT), Add(PT, OP)), Sub(PT, RT)), Sub(Mul(DD, W), Div(Div(Sub(Sub(W, PT), 0.8362019453506385), Mul(Add(PT, PT), W)), Div(Mul(Add(W, DD), Add(Mul(DD, W), Div(Add(AT, 0.8362019453506385), Sub(W, PT)))), Add(PT, RT))))), Sub(Add(Mul(Sub(Add(RPT, OP), Sub(W, PT)), Sub(Add(DD, 0.8362019453506385), PT)), Add(Div(Sub(PT, 0.8362019453506385), Add(DD, Mul(Add(Sub(0.8362019453506385, W), 0.8362019453506385), Div(RPT, Mul(DD, W))))), Div(Mul(W, 0.8362019453506385), DD))), Div(0.8362019453506385, Div(Mul(Sub(PT, 0.8362019453506385), Div(RPT, AT)), RT)))))), Add(PT, Sub(Sub(Add(Mul(DD, W), Div(Sub(Add(RT, RPT), Mul(OP, DD)), Sub(W, PT))), Div(0.8362019453506385, Sub(Sub(Div(W, PT), Mul(Div(Div(Mul(DD, W), Div(Mul(Add(W, DD), Div(RPT, Mul(Sub(Add(RT, RPT), Add(RPT, RPT)), Div(Add(AT, DD), Mul(OP, W))))), Add(PT, RT))), PT), W)), Add(DD, Add(RPT, OP))))), Add(DD, Add(PT, PT)))))


    		;
    }
}
