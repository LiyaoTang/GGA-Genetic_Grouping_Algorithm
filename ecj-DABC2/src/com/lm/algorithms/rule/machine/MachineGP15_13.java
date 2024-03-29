package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP15_13 extends GPRuleBase implements IMachineRule {

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
    Sub(Add(Mul(Add(Sub(Div(Mul(Mul(Div(Add(DD, PT), Sub(RPT, W)), Div(OP, W)), PT), Mul(W, OP)), DD), Div(Mul(Mul(Div(Div(Add(PT, DD), Mul(RT, Add(DD, PT))), Mul(Mul(Mul(Div(W, RPT), Add(Add(RPT, PT), DD)), Div(OP, DD)), W)), Mul(Div(Add(DD, Mul(Div(W, RPT), Add(Add(RPT, PT), DD))), Sub(RPT, W)), Div(OP, W))), Div(Add(Div(0.6648472695131162, RPT), Add(RPT, W)), Div(Mul(W, OP), Mul(PT, OP)))), Mul(Mul(Mul(Mul(Div(W, RPT), Add(Add(RPT, PT), DD)), PT), Add(Sub(Mul(W, RPT), Sub(RT, RPT)), Div(Mul(0.6648472695131162, W), Mul(PT, OP)))), OP))), Add(Add(Mul(Div(0.6648472695131162, W), Add(Div(OP, DD), DD)), Mul(W, OP)), Div(0.6648472695131162, W))), Mul(Div(W, RPT), Add(RPT, PT))), Mul(Mul(Div(Div(Sub(Div(DD, RT), Sub(RT, RPT)), Mul(RT, W)), Mul(Mul(Mul(Div(W, RPT), Add(Add(RPT, PT), DD)), Div(OP, DD)), W)), Mul(Div(Add(DD, PT), Sub(RPT, W)), Div(OP, W))), Div(Add(Div(Div(Div(Add(PT, DD), Mul(RT, W)), Mul(Mul(Add(RPT, PT), Div(Mul(Mul(Div(Add(DD, PT), Sub(RPT, W)), Div(OP, W)), PT), Mul(W, OP))), W)), RPT), Add(RPT, W)), Div(Mul(W, OP), Mul(PT, OP)))))
    		;
    }
}
