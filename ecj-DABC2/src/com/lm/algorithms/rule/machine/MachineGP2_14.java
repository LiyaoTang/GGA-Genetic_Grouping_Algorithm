package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP2_14 extends GPRuleBase implements IMachineRule {

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
    Add(Sub(Add(Sub(Add(Sub(OP, W), Div(OP, AT)), Div(Add(Sub(AT, OP), Div(RPT, W)), AT)), Sub(Div(Add(PT, 0.3015320606078309), Add(Sub(Sub(Mul(DD, Div(OP, AT)), Div(Mul(Mul(W, OP), 0.3015320606078309), RT)), Div(Add(Div(PT, W), Div(RPT, W)), Add(Sub(Add(Sub(OP, W), Sub(Div(DD, RPT), Mul(Sub(PT, PT), Add(W, PT)))), Div(Add(Div(Mul(W, W), Div(Mul(W, W), Add(W, PT))), Div(Sub(PT, PT), W)), Div(Mul(W, W), Add(W, PT)))), RPT))), Add(Mul(Sub(Sub(AT, OP), Add(OP, W)), Sub(RT, RPT)), Sub(Div(Add(0.3015320606078309, OP), Div(Add(Sub(AT, RT), Div(RPT, W)), Mul(Sub(Sub(AT, OP), Add(OP, W)), Sub(Add(W, AT), Sub(AT, OP))))), Sub(Div(Mul(W, W), Div(Mul(W, W), Add(W, PT))), Div(Add(0.3015320606078309, OP), Div(RPT, W))))))), Div(Add(0.3015320606078309, OP), Mul(DD, Sub(Add(Mul(RPT, AT), Add(RT, W)), Div(Add(0.3015320606078309, OP), Div(Mul(W, W), Add(W, PT)))))))), Div(Add(Sub(AT, OP), Div(RPT, W)), Div(Mul(W, W), Add(W, PT)))), Add(Mul(Sub(Sub(AT, OP), Add(OP, W)), Sub(Add(W, AT), Div(PT, 0.3015320606078309))), Sub(Div(Add(PT, 0.3015320606078309), Div(Div(DD, RPT), Sub(RT, RPT))), Div(Add(0.3015320606078309, OP), Div(Mul(Sub(PT, PT), Div(OP, AT)), Add(Sub(OP, W), Sub(Add(OP, W), Div(Add(0.3015320606078309, OP), Mul(DD, Mul(Sub(Sub(AT, Div(Add(Sub(AT, OP), Div(RPT, W)), Div(Mul(W, W), Sub(AT, OP)))), OP), Add(PT, 0.3015320606078309)))))))))))


    		;
    }
}
