package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP2_15 extends GPRuleBase implements IMachineRule {

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
    Add(Sub(Add(Add(Mul(Mul(PT, Mul(W, RPT)), Div(Div(DD, RPT), Add(Sub(Div(W, RPT), RPT), OP))), Add(PT, RPT)), Sub(Div(Add(PT, 0.3015320606078309), Add(Sub(Div(OP, RT), Mul(W, W)), Add(Mul(Sub(Sub(AT, OP), Add(OP, W)), Add(W, AT)), Sub(Div(Div(W, Div(DD, RPT)), Div(Add(Sub(AT, Div(PT, 0.3015320606078309)), Div(RPT, Div(DD, RPT))), Add(Mul(Add(Mul(RPT, AT), Add(RT, W)), 0.3015320606078309), Add(W, PT)))), 0.3015320606078309)))), Div(Add(0.3015320606078309, OP), Mul(DD, Add(Add(RPT, DD), Sub(RPT, RPT)))))), Div(Add(Sub(AT, OP), Div(RPT, W)), Div(Mul(W, W), Add(W, PT)))), Add(Mul(Sub(Sub(AT, OP), Add(OP, W)), Sub(Add(W, AT), Div(PT, 0.3015320606078309))), Sub(Div(Add(PT, 0.3015320606078309), Div(Div(DD, RPT), Sub(RT, RPT))), Div(Add(0.3015320606078309, OP), Div(Mul(Sub(PT, PT), Div(OP, AT)), Add(W, PT))))))


    		;
    }
}
