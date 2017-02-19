package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP19_14 extends GPRuleBase implements IMachineRule {

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
    Div(W, Div(Mul(Sub(OP, Mul(Mul(Mul(RPT, Div(PT, W)), Sub(RPT, DD)), Add(Add(W, W), Div(PT, W)))), Add(Sub(Add(Div(Sub(Mul(Mul(0.2571261519791146, 0.2571261519791146), Div(Div(W, Add(Div(Mul(Mul(Mul(0.2571261519791146, 0.2571261519791146), Mul(AT, RT)), Div(PT, AT)), PT), Div(Mul(0.2571261519791146, 0.2571261519791146), W))), Div(W, RPT))), Mul(0.2571261519791146, DD)), Mul(0.2571261519791146, RT)), Div(Div(OP, Div(W, Add(Add(W, W), Div(PT, W)))), Mul(W, 0.2571261519791146))), Sub(Div(PT, Mul(Add(Add(PT, 0.2571261519791146), Div(Mul(0.2571261519791146, RT), Mul(0.2571261519791146, RT))), Div(Div(PT, AT), Mul(PT, 0.2571261519791146)))), Div(DD, W))), Div(Add(Mul(PT, OP), W), Div(0.2571261519791146, Add(W, W))))), Mul(0.2571261519791146, 0.2571261519791146)))
    		;
    }
}
