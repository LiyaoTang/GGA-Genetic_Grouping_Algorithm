package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP10_16 extends GPRuleBase implements IMachineRule {

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
    Sub(Mul(Mul(Div(W, PT), Sub(Sub(Mul(Div(Sub(Mul(Mul(Div(W, PT), Div(W, DD)), Add(Add(W, 0.0334667627495715), 0.0334667627495715)), Mul(Mul(RT, DD), Div(Mul(RT, DD), Add(W, 0.0334667627495715)))), PT), Div(W, OP)), Mul(Div(RT, RPT), Sub(W, 0.0334667627495715))), Mul(Div(RT, RPT), Mul(Sub(Sub(0.0334667627495715, 0.0334667627495715), 0.0334667627495715), Add(AT, Mul(Div(W, PT), Mul(0.0334667627495715, W))))))), Sub(Mul(Mul(Mul(Mul(Mul(Div(W, PT), Sub(Sub(Mul(Div(Sub(Mul(Mul(Div(W, PT), Div(W, DD)), Add(W, 0.0334667627495715)), RT), PT), Div(W, OP)), Mul(Div(RT, RPT), W)), Mul(Div(RT, RPT), Div(RT, PT)))), Add(W, 0.0334667627495715)), Div(W, DD)), Add(W, 0.0334667627495715)), Div(W, OP)), Mul(Div(RT, Add(AT, RPT)), Sub(Mul(Sub(W, 0.0334667627495715), Add(RT, W)), Mul(Mul(Div(W, PT), Div(W, DD)), Add(W, OP)))))), Mul(Div(Mul(RT, DD), Mul(Div(Div(RT, Div(RT, PT)), PT), Div(W, DD))), Sub(Mul(Div(W, PT), Div(Mul(RT, Add(W, 0.0334667627495715)), Mul(Div(Div(Div(RPT, Div(RT, PT)), Div(RT, PT)), PT), Div(W, DD)))), Sub(Mul(Sub(W, 0.0334667627495715), Add(Sub(Mul(Mul(Div(W, PT), Add(W, 0.0334667627495715)), Add(W, 0.0334667627495715)), Mul(Div(Sub(Mul(Mul(Div(W, PT), Div(W, DD)), Add(W, 0.0334667627495715)), Mul(Mul(RT, DD), Div(Mul(RT, DD), Div(RT, PT)))), PT), Div(W, OP))), W)), Mul(Div(W, PT), Add(W, OP))))))
    		;
    }
}
