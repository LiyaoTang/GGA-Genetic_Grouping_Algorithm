package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP13_9 extends GPRuleBase implements IMachineRule {

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
    Div(Div(Mul(W, Div(Add(W, Mul(Mul(Mul(0.09582669536940647, W), Div(Mul(W, 0.09582669536940647), Mul(Sub(0.09582669536940647, RT), Add(W, 0.09582669536940647)))), Div(Mul(W, Div(Add(W, 0.09582669536940647), Mul(Mul(PT, 0.09582669536940647), RPT))), Div(Mul(Add(Div(DD, W), Div(W, PT)), Div(Add(W, 0.09582669536940647), Add(Add(W, 0.09582669536940647), PT))), Div(DD, Mul(W, Div(Div(Mul(Add(Div(DD, W), Add(Mul(0.09582669536940647, W), Sub(W, RT))), Div(Add(W, 0.09582669536940647), Mul(Mul(PT, 0.09582669536940647), RPT))), DD), Add(RPT, PT)))))))), Mul(Mul(DD, PT), Div(Add(RPT, PT), DD)))), Div(DD, W)), Add(W, Add(Mul(Div(W, PT), Div(Add(W, 0.09582669536940647), Mul(Mul(PT, 0.09582669536940647), RPT))), Add(Add(Mul(0.09582669536940647, W), Div(Div(W, PT), Div(DD, W))), Div(PT, W)))))
    		;
    }
}
