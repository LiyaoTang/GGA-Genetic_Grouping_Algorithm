package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP1_1 extends GPRuleBase implements IMachineRule {

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
    		Add(Sub(Div(Add(Div(RPT, PT), Mul(W, DD)), Mul(PT, W)), Add(Mul(Sub(0.6181923390917892, W), Add(RPT, RT)), Div(RPT, W))), Div(Add(RT, OP), Add(Add(Sub(Add(Div(OP, RPT), W), Add(Max(OP, RPT), Div(Add(RT, OP), Add(Add(Sub(Div(RPT, Div(Add(Div(RPT, PT), Mul(W, DD)), Max(Mul(PT, W), Add(0.6181923390917892, AT)))), Add(Max(OP, RPT), Div(RPT, W))), Div(Add(Div(RPT, PT), PT), Max(Mul(PT, W), Div(RPT, W)))), Div(Div(Add(Div(RPT, PT), Mul(W, DD)), Add(Div(RPT, PT), Mul(W, DD))), Max(Mul(W, DD), Max(Mul(PT, W), Max(AT, RT)))))))), Add(Div(RPT, PT), Mul(W, DD))), Div(Div(Add(Div(OP, RPT), Mul(W, DD)), Max(Mul(PT, W), Add(0.6181923390917892, AT))), Max(Mul(W, DD), DD)))))
    		
    		;
    }
}
