package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP3_16 extends GPRuleBase implements IMachineRule {

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
    Sub(Mul(Div(Sub(Sub(RPT, DD), Mul(DD, 0.9328095883853869)), Mul(Mul(W, W), Add(PT, OP))), Add(OP, PT)), Add(Add(Mul(Mul(Sub(Div(Mul(RPT, DD), Mul(Mul(0.9328095883853869, RPT), Mul(W, W))), Sub(Sub(RPT, DD), Sub(Div(W, DD), Sub(OP, DD)))), Mul(W, W)), Mul(W, W)), Add(Mul(Mul(PT, 0.9328095883853869), Mul(W, W)), Mul(Div(Sub(W, AT), Sub(0.9328095883853869, PT)), Div(RPT, W)))), Sub(Mul(Div(Mul(RPT, DD), Add(OP, PT)), Mul(W, W)), Sub(Div(W, DD), Add(Add(Add(Mul(Mul(W, W), Mul(W, W)), Sub(OP, DD)), Sub(Div(Mul(RPT, DD), Add(OP, PT)), Sub(Mul(0.9328095883853869, RT), Add(Add(Add(Mul(Add(RPT, OP), Mul(W, W)), Mul(Add(OP, PT), Div(RPT, W))), Mul(DD, W)), Div(RPT, RPT))))), Mul(OP, RPT))))))
    ;
    }
}
