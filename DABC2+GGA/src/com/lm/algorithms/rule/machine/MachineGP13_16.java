package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP13_16 extends GPRuleBase implements IMachineRule {

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
    Div(Div(Mul(W, Div(Add(W, Mul(Mul(Mul(0.09582669536940647, W), Div(Mul(W, 0.09582669536940647), Mul(Sub(0.09582669536940647, RT), Add(W, 0.09582669536940647)))), Div(Div(Add(W, Add(Mul(Div(W, PT), Mul(0.09582669536940647, W)), Add(Add(Div(Mul(W, Mul(W, Div(W, PT))), OP), Sub(W, RT)), Div(PT, W)))), Div(DD, W)), Div(Add(W, 0.09582669536940647), Div(DD, Mul(W, Div(Div(Mul(Add(Div(DD, W), Div(W, PT)), Div(Add(W, 0.09582669536940647), Mul(Mul(PT, 0.09582669536940647), RPT))), Div(DD, W)), Add(RPT, PT)))))))), Mul(Mul(DD, PT), Div(Add(RPT, PT), DD)))), Div(DD, W)), Add(W, Add(Mul(Div(W, PT), Mul(0.09582669536940647, W)), Add(Add(Mul(0.09582669536940647, W), Sub(W, RT)), Div(PT, W)))))
   	;
   }
}
