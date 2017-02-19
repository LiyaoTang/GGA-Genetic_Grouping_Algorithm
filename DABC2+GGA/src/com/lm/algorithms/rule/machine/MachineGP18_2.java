package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP18_2 extends GPRuleBase implements IMachineRule {

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
    Sub(Add(Div(W, PT), Mul(Add(Div(Add(Mul(OP, W), Div(Mul(OP, 0.27879960110643864), Mul(OP, W))), Div(W, PT)), Div(W, Div(W, PT))), Sub(Sub(RPT, RT), Add(AT, RPT)))), Add(Add(Add(Div(Add(Add(Mul(PT, RPT), Add(Div(Div(W, PT), W), Div(Sub(Div(DD, W), Div(0.27879960110643864, Add(RT, RT))), Mul(Div(Sub(Div(DD, W), Div(DD, W)), Div(W, PT)), W)))), Div(Div(W, PT), Div(W, PT))), Div(DD, W)), Sub(0.27879960110643864, 0.27879960110643864)), Add(Sub(Div(DD, W), Div(0.27879960110643864, RPT)), Div(Add(Sub(0.27879960110643864, 0.27879960110643864), Div(Sub(Div(DD, W), Div(0.27879960110643864, RPT)), Mul(Div(W, PT), W))), Div(W, PT)))), Div(Sub(Div(DD, W), Div(Add(Add(Mul(PT, RPT), Add(Div(Div(W, PT), Add(Mul(Div(W, PT), W), Div(Add(Sub(0.27879960110643864, 0.27879960110643864), Div(Sub(Div(DD, W), Sub(0.27879960110643864, 0.27879960110643864)), Mul(Div(W, PT), W))), Div(W, PT)))), Div(Sub(Div(DD, W), Div(0.27879960110643864, Add(RT, RT))), Mul(Div(W, PT), W)))), Div(Sub(Sub(RPT, RT), Add(AT, RPT)), Div(W, PT))), Div(DD, W))), Div(W, PT))))
    		;
    }
}
