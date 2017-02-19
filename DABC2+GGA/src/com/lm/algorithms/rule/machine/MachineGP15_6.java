package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP15_6 extends GPRuleBase implements IMachineRule {

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
    Sub(Add(Mul(Add(Sub(Div(Mul(Mul(Div(Add(DD, PT), Sub(RPT, W)), Div(OP, W)), PT), Mul(W, OP)), DD), Div(Mul(Mul(Div(Div(Add(PT, DD), Mul(RT, RT)), Mul(Mul(Mul(Div(W, RPT), Add(Add(RPT, PT), DD)), Div(OP, DD)), W)), Mul(Div(Add(DD, PT), Sub(RPT, W)), Div(OP, W))), Div(Add(Div(0.6648472695131162, RPT), Add(RPT, W)), Div(Mul(W, OP), Mul(PT, OP)))), Mul(Mul(Mul(Mul(Div(W, RPT), Add(Add(RPT, PT), DD)), PT), Add(Sub(Mul(W, RPT), Sub(RT, RPT)), Div(Mul(0.6648472695131162, Mul(Mul(RT, W), RT)), Mul(PT, OP)))), OP))), Add(Add(Mul(Div(0.6648472695131162, W), Add(Div(OP, DD), DD)), Add(Div(OP, DD), W)), PT)), Mul(Div(W, RPT), Add(RPT, PT))), Mul(Mul(Div(Div(Sub(Mul(PT, OP), Mul(Div(W, RPT), Add(Add(RPT, PT), DD))), Mul(RT, W)), Mul(Mul(Mul(Div(W, RPT), Add(Add(RPT, PT), DD)), Div(OP, DD)), W)), Mul(Div(Add(DD, PT), Sub(RPT, W)), Div(OP, W))), Div(Add(Div(Div(Div(OP, W), Mul(Mul(Mul(W, Add(Add(RPT, OP), Add(Mul(Div(Add(DD, PT), Sub(RPT, W)), Add(RPT, W)), DD))), Div(OP, DD)), W)), RPT), Add(RPT, W)), Div(Mul(W, OP), Mul(PT, OP)))))
    		;
    }
}
