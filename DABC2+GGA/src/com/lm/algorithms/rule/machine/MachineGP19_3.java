package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP19_3 extends GPRuleBase implements IMachineRule {

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
    Mul(Mul(Div(Div(W, PT), Div(RPT, Mul(W, 0.36963050186656987))), Mul(Div(Sub(Sub(Sub(Sub(Div(Mul(Add(W, 0.36963050186656987), W), Div(Add(DD, Add(Div(RT, W), Sub(RT, Sub(PT, RT)))), Div(Mul(RPT, RT), Sub(0.36963050186656987, W)))), Div(Div(RT, W), Add(W, Mul(Sub(PT, Mul(PT, RPT)), Div(W, PT))))), Div(Add(Div(Sub(PT, RT), W), Div(Div(Div(Div(RT, W), Sub(0.36963050186656987, RPT)), Mul(PT, DD)), Mul(PT, DD))), Mul(PT, DD))), Div(Add(Div(Sub(PT, RT), W), Mul(PT, Div(Add(OP, W), Div(Sub(W, OP), Div(Div(W, PT), Div(RPT, Mul(W, 0.36963050186656987))))))), Sub(Div(Mul(Add(Add(RT, W), W), W), Sub(Add(Mul(W, PT), Mul(RPT, RPT)), Sub(RPT, W))), Mul(PT, RPT)))), Div(Div(Div(RT, W), Sub(Div(RPT, Sub(Add(Mul(Add(RT, W), PT), Mul(RPT, RPT)), Sub(RPT, W))), Mul(PT, RPT))), Mul(PT, DD))), Sub(Mul(PT, DD), Sub(Sub(Div(Mul(Sub(PT, RT), W), Div(Add(Add(Div(OP, DD), Sub(RT, Add(0.36963050186656987, PT))), Add(Div(OP, DD), Div(Div(W, PT), Div(RPT, Mul(W, 0.36963050186656987))))), Div(Mul(RPT, RT), Sub(0.36963050186656987, W)))), Div(Div(RT, W), Sub(Div(Mul(Add(Add(RT, W), W), W), 0.36963050186656987), Sub(0.36963050186656987, W)))), Div(W, PT)))), W)), Mul(Div(Mul(W, W), Sub(Mul(PT, DD), Mul(Div(Sub(PT, RT), Sub(Div(Div(W, PT), Mul(0.36963050186656987, Mul(0.36963050186656987, W))), Sub(Sub(DD, W), Div(Mul(Add(RT, W), Mul(PT, Div(Sub(RT, Add(0.36963050186656987, PT)), Div(Div(RT, W), Div(Sub(W, OP), Sub(0.36963050186656987, W)))))), Div(Div(RT, W), Div(Sub(Div(Sub(DD, W), Mul(0.36963050186656987, Mul(0.36963050186656987, W))), RT), 0.36963050186656987)))))), Div(W, PT)))), W))
    		;
    }
}
