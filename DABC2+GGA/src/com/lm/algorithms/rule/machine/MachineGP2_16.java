package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP2_16 extends GPRuleBase implements IMachineRule {

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
    Add(Sub(Add(Sub(OP, W), Sub(Div(Div(W, Sub(RT, RPT)), RPT), Div(OP, Div(Mul(Sub(PT, PT), Sub(Sub(AT, OP), Add(OP, W))), Add(W, PT))))), Div(Add(Sub(AT, OP), Div(RPT, W)), Div(Mul(W, W), Add(W, PT)))), Add(Mul(Sub(Sub(AT, OP), Add(OP, W)), Sub(Add(W, AT), Div(PT, 0.3015320606078309))), Sub(Div(Add(PT, 0.3015320606078309), Div(Div(DD, RPT), Sub(RT, RPT))), Div(Add(0.3015320606078309, OP), Div(Add(Mul(Sub(Sub(AT, OP), Div(Add(Sub(AT, OP), Div(RPT, W)), Div(Mul(W, W), Add(W, PT)))), Sub(Add(PT, 0.3015320606078309), Div(PT, 0.3015320606078309))), Sub(Div(Add(PT, 0.3015320606078309), Div(Div(DD, RPT), Sub(Sub(RT, RPT), Div(Add(0.3015320606078309, Div(Mul(Sub(Add(0.3015320606078309, OP), RPT), 0.3015320606078309), RT)), Sub(RT, RPT))))), Div(Add(0.3015320606078309, OP), Div(Sub(Div(Add(Add(RPT, DD), Sub(RPT, RPT)), Div(Div(DD, RPT), Sub(RT, RPT))), Div(Add(0.3015320606078309, OP), Div(Mul(Sub(PT, PT), Div(OP, AT)), Add(W, PT)))), Add(W, PT))))), Add(W, PT))))))


    		;
    }
}
