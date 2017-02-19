package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP10_15 extends GPRuleBase implements IMachineRule {

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
    Add(Add(Div(OP, Mul(Sub(OP, Div(0.38704438192528834, RT)), Mul(Sub(OP, Add(W, W)), Div(Div(OP, OP), Mul(Div(Div(Add(Mul(W, W), Mul(W, Add(Div(Div(OP, OP), Mul(W, W)), Div(Sub(PT, DD), Mul(W, W))))), Add(W, 0.38704438192528834)), Div(W, PT)), Div(Sub(Mul(PT, OP), Div(Div(Add(Mul(W, W), Mul(W, Add(Div(Div(OP, OP), Mul(W, W)), Div(Sub(PT, DD), Mul(W, W))))), Add(W, 0.38704438192528834)), Div(W, PT))), Mul(W, W))))))), Div(Sub(PT, DD), Mul(W, Mul(W, W)))), Mul(Sub(Add(W, 0.38704438192528834), Mul(PT, OP)), Div(Sub(Add(RT, PT), Div(Div(Add(Mul(W, Add(Div(OP, Add(AT, Mul(W, W))), Div(Sub(PT, DD), Mul(W, W)))), Mul(W, Add(Mul(Div(W, W), Mul(DD, RT)), Div(Sub(Div(OP, Add(AT, RT)), DD), Mul(W, W))))), Add(W, 0.38704438192528834)), Div(W, PT))), Mul(W, W))))
    		;
    }
}
