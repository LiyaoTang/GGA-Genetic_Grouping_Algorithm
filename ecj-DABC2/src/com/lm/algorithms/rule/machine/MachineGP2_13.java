package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP2_13 extends GPRuleBase implements IMachineRule {

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
    Add(Sub(Add(Sub(OP, W), Sub(Div(Add(PT, 0.3015320606078309), Add(Sub(Sub(Add(W, PT), Add(Sub(Add(Sub(OP, W), Sub(Div(DD, RPT), Div(Add(0.3015320606078309, OP), Mul(DD, Mul(W, W))))), Div(Div(W, RPT), Div(Mul(W, W), Add(W, PT)))), Add(Mul(Sub(Sub(AT, OP), Add(OP, W)), Sub(Add(W, AT), Div(PT, 0.3015320606078309))), Sub(AT, Div(Sub(Div(Div(RPT, W), RPT), RPT), Div(Add(RT, W), Add(W, PT))))))), Div(Add(Div(PT, W), Add(DD, 0.3015320606078309)), Add(Sub(Add(Sub(OP, W), Sub(Div(DD, RPT), Div(Add(0.3015320606078309, OP), Mul(DD, Div(OP, Add(0.3015320606078309, OP)))))), Div(Add(Sub(AT, OP), Div(RPT, W)), Div(Mul(W, W), Sub(AT, RT)))), Add(Mul(Sub(Sub(AT, OP), Add(OP, W)), Sub(Add(W, AT), Div(PT, 0.3015320606078309))), Sub(Div(Add(PT, 0.3015320606078309), Div(Div(DD, RPT), Sub(RT, RPT))), Div(Add(0.3015320606078309, OP), Div(0.3015320606078309, Add(W, PT)))))))), Add(Mul(Sub(Sub(AT, Sub(PT, PT)), Add(OP, W)), Add(PT, 0.3015320606078309)), Sub(Div(Div(Sub(DD, 0.3015320606078309), Div(OP, RT)), Div(Add(Sub(AT, RT), Div(RPT, W)), Div(Mul(W, W), Add(W, PT)))), Div(Div(DD, RPT), Sub(RT, RPT)))))), Div(Sub(Add(W, PT), Div(Mul(Sub(Div(Add(PT, 0.3015320606078309), Add(RT, OP)), Add(Add(0.3015320606078309, OP), Div(Add(PT, 0.3015320606078309), RT))), 0.3015320606078309), Div(PT, W))), Mul(DD, Div(OP, AT))))), Div(Add(Sub(AT, OP), Div(RPT, W)), Div(Mul(W, W), Add(W, PT)))), Add(Mul(Sub(Sub(AT, OP), 0.3015320606078309), Sub(Add(W, AT), Div(PT, 0.3015320606078309))), Sub(Div(Add(PT, 0.3015320606078309), Div(Div(DD, RPT), Sub(RT, RPT))), Div(W, Div(Div(PT, 0.3015320606078309), Div(Mul(Sub(Sub(AT, OP), Add(OP, W)), Sub(Add(W, AT), Div(PT, 0.3015320606078309))), Sub(RT, RPT)))))))


    		;
    }
}
