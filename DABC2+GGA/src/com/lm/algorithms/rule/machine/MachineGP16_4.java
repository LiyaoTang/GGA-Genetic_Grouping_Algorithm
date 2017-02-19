package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP16_4 extends GPRuleBase implements IMachineRule {

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
    Sub(Sub(Div(Sub(Sub(Div(Mul(W, OP), Div(PT, Add(Sub(W, PT), Div(PT, OP)))), PT), Mul(Sub(RPT, OP), PT)), Mul(Div(W, OP), Mul(W, OP))), Mul(Sub(Div(Sub(Sub(Div(Sub(Sub(Sub(Add(Mul(W, W), RT), PT), Add(Div(DD, DD), Add(OP, OP))), Add(DD, OP)), Add(W, W)), PT), Mul(Sub(RPT, OP), PT)), Mul(Div(W, OP), Div(Mul(Add(Mul(W, W), Add(W, W)), Mul(RPT, W)), Sub(Sub(Add(Mul(W, W), Mul(W, OP)), PT), Add(Div(DD, DD), Add(OP, OP)))))), Mul(Sub(W, Mul(W, OP)), Sub(W, Mul(Div(W, OP), Div(Mul(Add(Div(0.8553269765290448, RPT), Add(W, W)), Mul(RPT, W)), Sub(Sub(Add(Mul(W, W), Mul(W, OP)), PT), Add(Div(DD, DD), Div(0.8553269765290448, RPT)))))))), Mul(Div(0.8553269765290448, RPT), Add(Div(PT, DD), 0.8553269765290448)))), Div(Sub(Div(Sub(Sub(Div(Mul(Add(Mul(W, W), Mul(W, OP)), Mul(RPT, W)), Add(W, W)), PT), Mul(Sub(RPT, OP), PT)), Mul(Div(W, OP), Mul(W, OP))), Add(DD, OP)), Div(Add(RT, 0.8553269765290448), Div(PT, Add(Sub(W, PT), Div(PT, OP))))))
    		;
    }
}
