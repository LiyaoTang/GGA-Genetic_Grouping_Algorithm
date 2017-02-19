package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP3_2 extends GPRuleBase implements IMachineRule {

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
    		Add(Div(W, Add(Sub(Add(DD, 0.8362019453506385), Add(PT, OP)), Add(Mul(Add(AT, 0.8362019453506385), Add(RPT, AT)), Div(Mul(W, 0.8362019453506385), Sub(W, Mul(OP, DD)))))), Add(PT, Sub(Sub(Add(Mul(DD, W), Div(Sub(Add(RT, RPT), Mul(OP, DD)), Sub(W, PT))), Div(0.8362019453506385, Mul(DD, PT))), Add(DD, Add(PT, PT)))))


    		;
    }
}
