package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP6_5 extends GPRuleBase implements IMachineRule {

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
    Mul(Div(Div(Add(RPT, Add(Add(W, OP), Add(W, 0.22773273871329391))), Div(DD, 0.22773273871329391)), Sub(Div(RT, OP), Div(DD, AT))), Div(Mul(Add(Div(Div(Add(OP, RPT), Div(0.22773273871329391, 0.22773273871329391)), Mul(W, 0.22773273871329391)), Mul(Div(0.22773273871329391, W), Add(Mul(PT, RPT), Add(W, Div(W, Mul(Mul(Div(PT, RT), Mul(Mul(W, W), Mul(W, W))), Mul(W, W))))))), Add(0.22773273871329391, RPT)), Mul(Div(PT, RT), Mul(W, W))))
    		;
    }
}
