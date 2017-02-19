package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP6_12 extends GPRuleBase implements IMachineRule {

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
    Mul(Div(Div(Add(RPT, Add(Mul(Add(W, Div(Div(Add(OP, RPT), Div(0.22773273871329391, 0.22773273871329391)), Add(OP, Sub(Mul(W, W), Mul(W, 0.22773273871329391))))), Mul(0.22773273871329391, W)), OP)), Div(DD, 0.22773273871329391)), Sub(Div(Add(RPT, Add(Mul(W, W), Div(Div(Add(RPT, Add(RPT, OP)), Div(DD, 0.22773273871329391)), Sub(Div(Add(RPT, Add(Mul(W, W), Mul(Mul(W, W), Div(Add(OP, RPT), Div(0.22773273871329391, 0.22773273871329391))))), Div(DD, 0.22773273871329391)), Div(W, Mul(Mul(Div(PT, RT), Mul(W, W)), Add(RPT, RT))))))), Div(DD, 0.22773273871329391)), Div(DD, AT))), Div(Mul(Add(Div(Add(OP, RPT), Mul(W, 0.22773273871329391)), Mul(Div(0.22773273871329391, W), Add(Mul(PT, RPT), Mul(W, W)))), Add(RPT, Div(W, Mul(Mul(W, W), Div(Add(OP, RPT), Div(0.22773273871329391, 0.22773273871329391)))))), Mul(Div(Add(OP, RPT), RT), Mul(W, W))))
	;
    }
}
