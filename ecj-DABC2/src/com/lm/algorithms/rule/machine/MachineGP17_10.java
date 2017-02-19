package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP17_10 extends GPRuleBase implements IMachineRule {

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
    Sub(Sub(Sub(Sub(Add(Sub(Add(PT, W), Mul(Sub(Div(Sub(AT, AT), W), Add(Mul(OP, OP), Mul(DD, W))), AT)), Mul(Sub(OP, Add(W, Sub(Sub(RT, W), Mul(W, 0.45277216974647116)))), Sub(Sub(Mul(OP, 0.45277216974647116), W), Div(0.45277216974647116, AT)))), Mul(DD, W)), Div(Mul(DD, PT), Sub(Sub(Mul(OP, 0.45277216974647116), W), Div(0.45277216974647116, AT)))), Add(Add(Add(W, PT), RPT), Div(Sub(Mul(W, Div(Div(Add(Div(Sub(Sub(Sub(Div(Div(Mul(DD, W), Mul(0.45277216974647116, DD)), Add(Sub(PT, W), Div(OP, 0.45277216974647116))), Div(0.45277216974647116, AT)), Add(Mul(OP, OP), Mul(DD, W))), Sub(0.45277216974647116, Sub(RT, Div(0.45277216974647116, AT)))), Mul(W, Div(Div(Add(Add(W, PT), RPT), Mul(0.45277216974647116, DD)), Add(Sub(PT, W), Div(OP, 0.45277216974647116))))), Add(RT, W)), Div(Add(OP, PT), Add(OP, PT))), Mul(W, 0.45277216974647116))), AT), Mul(W, RPT)))), Add(Add(0.45277216974647116, RPT), Mul(Sub(Div(Div(Add(Div(Sub(Sub(Sub(Sub(RT, W), Div(0.45277216974647116, AT)), Add(Mul(OP, OP), Mul(DD, W))), Sub(Add(Add(W, PT), RPT), Sub(RT, OP))), Mul(W, Div(Div(Mul(DD, W), Mul(0.45277216974647116, DD)), Add(Sub(PT, W), Div(OP, 0.45277216974647116))))), Sub(RT, W)), Div(Mul(W, 0.45277216974647116), Add(OP, PT))), Mul(W, 0.45277216974647116)), DD), Div(Mul(OP, 0.45277216974647116), Sub(RT, OP)))))
    		;
    }
}
