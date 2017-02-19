package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP18_3 extends GPRuleBase implements IMachineRule {

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
    Sub(Add(Div(W, PT), Mul(Add(Div(Add(Add(Mul(OP, W), Sub(0.27879960110643864, 0.27879960110643864)), Sub(0.27879960110643864, 0.27879960110643864)), Div(W, PT)), Div(W, Mul(Div(W, PT), W))), Sub(Sub(RPT, RT), Add(AT, RPT)))), Add(Add(Add(Mul(OP, W), W), Add(Div(W, PT), Div(Add(Mul(Div(W, PT), W), Div(Sub(Div(DD, W), Div(0.27879960110643864, RPT)), Mul(Div(W, PT), W))), Div(W, PT)))), Div(Sub(Div(DD, W), Div(Add(Add(Sub(Div(DD, W), Div(0.27879960110643864, RPT)), Add(Div(Sub(Sub(Div(DD, W), Div(0.27879960110643864, RPT)), Div(0.27879960110643864, RPT)), Mul(OP, W)), Div(Sub(Sub(0.27879960110643864, 0.27879960110643864), Div(0.27879960110643864, Mul(Sub(0.27879960110643864, 0.27879960110643864), W))), Mul(Div(W, PT), W)))), PT), Div(DD, W))), Div(W, PT))))
    		;
    }
}
