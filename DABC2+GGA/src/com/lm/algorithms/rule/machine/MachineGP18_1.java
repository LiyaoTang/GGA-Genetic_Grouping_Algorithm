package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP18_1 extends GPRuleBase implements IMachineRule {

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
    Sub(Add(Div(W, PT), Mul(Add(Div(Add(Add(Mul(OP, W), Sub(0.27879960110643864, 0.27879960110643864)), Sub(0.27879960110643864, 0.27879960110643864)), Div(W, PT)), Div(W, Mul(Div(W, PT), W))), Sub(Sub(RPT, RT), Add(AT, RPT)))), Add(Add(Add(Mul(OP, W), Sub(OP, OP)), Add(Mul(0.27879960110643864, DD), Div(Add(Div(Sub(0.27879960110643864, 0.27879960110643864), Mul(OP, W)), Div(Sub(Div(DD, W), Div(0.27879960110643864, RPT)), Mul(Div(W, PT), W))), Div(W, PT)))), Div(Sub(Div(DD, W), Div(Add(Add(Mul(PT, RPT), Add(Div(Add(PT, 0.27879960110643864), Mul(OP, W)), Div(Sub(Div(DD, W), Div(0.27879960110643864, Mul(Div(W, PT), W))), Mul(Div(W, PT), W)))), PT), Div(DD, W))), Div(W, PT))))
    		;
    }
}
