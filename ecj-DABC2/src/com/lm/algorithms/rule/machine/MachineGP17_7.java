package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP17_7 extends GPRuleBase implements IMachineRule {

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
    Sub(Sub(Sub(Sub(Add(Sub(Sub(Mul(W, 0.45277216974647116), W), Mul(Sub(Sub(Sub(RT, W), Div(0.45277216974647116, AT)), Add(Mul(OP, OP), Mul(DD, W))), AT)), DD), Mul(DD, W)), Div(Mul(DD, PT), Sub(Sub(Mul(OP, 0.45277216974647116), W), Div(0.45277216974647116, AT)))), Add(RT, Div(Div(Sub(Sub(Sub(0.45277216974647116, Div(Sub(AT, Sub(Add(W, Add(RT, W)), Div(0.45277216974647116, AT))), W)), Add(Mul(OP, OP), Mul(DD, W))), Sub(Add(Add(W, PT), RPT), Div(DD, RT))), Div(Mul(W, 0.45277216974647116), Add(OP, PT))), Mul(W, RPT)))), Add(Add(0.45277216974647116, RPT), Mul(Sub(Div(Div(Add(Div(Sub(Sub(Sub(Sub(Sub(RT, W), OP), Div(Sub(AT, AT), W)), Add(Mul(OP, OP), Mul(DD, W))), Sub(Add(Add(W, PT), RPT), Div(DD, RT))), Mul(W, Div(Div(Mul(DD, W), Mul(0.45277216974647116, DD)), Add(Sub(PT, W), Div(OP, 0.45277216974647116))))), Add(RT, W)), Div(Mul(W, 0.45277216974647116), Add(OP, PT))), Mul(W, 0.45277216974647116)), DD), Div(Mul(OP, 0.45277216974647116), Sub(RT, OP)))))
    		;
    }
}
