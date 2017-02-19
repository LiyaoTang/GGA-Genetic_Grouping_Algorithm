package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP1_13 extends GPRuleBase implements IMachineRule {

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
    Mul(Add(Div(Sub(Add(DD, OP), Div(W, OP)), Div(DD, Add(DD, RPT))), Add(Add(Add(DD, DD), Sub(Div(Div(W, 0.8723855103424603), Sub(RPT, OP)), Div(Div(W, W), Add(RPT, PT)))), Mul(Mul(RT, RT), Sub(OP, W)))), Div(Add(Mul(Div(OP, W), PT), Add(Div(AT, PT), Add(RT, AT))), Div(Div(Div(OP, 0.8723855103424603), Div(0.8723855103424603, 0.8723855103424603)), Mul(Mul(Add(W, PT), Div(RPT, 0.8723855103424603)), Sub(W, 0.8723855103424603)))))


    		;
    }
}
