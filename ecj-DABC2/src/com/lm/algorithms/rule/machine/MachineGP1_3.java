package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP1_3 extends GPRuleBase implements IMachineRule {

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
    		Max(Max(Add(Div(Sub(AT,DD),Div(Sub(Add(PT,PT),Max(Max(Mul(W,PT),Add(0.8039775361822307, RT)), Sub(Sub(Mul(DD, RT), Max(W, DD)), Div(0.8039775361822307, OP)))), Max(Max(Add(AT, W), PT), Div(RT, OP)))), Max(Max(DD, OP), Sub(PT, AT))), Mul(Div(AT, PT), Max(Add(Add(Div(DD, 0.8039775361822307), Sub(OP, W)), Max(Div(AT, PT), Sub(PT, AT))), Mul(Max(Div(DD, OP), Mul(RT, AT)), Div(Sub(AT, DD), Max(DD, OP)))))), Max(Max(Max(Mul(W, PT), Add(DD, Add(RPT, AT))), Sub(Sub(OP, 0.8039775361822307), Sub(0.8039775361822307, RPT))), Max(W, OP)))
    		;
    }
}
