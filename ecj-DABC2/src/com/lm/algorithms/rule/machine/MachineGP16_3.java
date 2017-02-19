package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP16_3 extends GPRuleBase implements IMachineRule {

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
    Sub(Sub(Div(Sub(Mul(Div(0.8553269765290448, Div(Sub(Div(Sub(Sub(Div(Mul(W, Mul(RPT, W)), Add(W, W)), PT), Mul(W, OP)), Mul(Div(W, OP), Mul(Div(W, OP), Mul(W, OP)))), Add(DD, OP)), Div(Add(RT, 0.8553269765290448), Div(PT, Add(Mul(W, OP), Div(PT, OP)))))), Sub(Mul(W, OP), Mul(Div(0.8553269765290448, RPT), Sub(Sub(Div(W, OP), PT), Mul(Div(Div(W, OP), OP), PT))))), Mul(Sub(RPT, OP), PT)), Mul(Div(W, OP), Mul(W, OP))), Mul(Sub(Div(Sub(Sub(Div(Sub(Sub(Sub(Add(Mul(W, W), RT), PT), Add(Div(DD, DD), Add(OP, OP))), Add(DD, OP)), Add(W, W)), PT), Mul(Sub(RPT, OP), PT)), Mul(Div(W, OP), Div(Mul(Add(Mul(W, W), Add(W, W)), Mul(RPT, W)), Sub(Sub(Add(Mul(W, W), Mul(W, OP)), PT), Add(Div(DD, DD), Add(Add(Mul(W, W), Mul(Div(W, OP), Mul(W, OP))), OP)))))), RPT), Mul(Div(0.8553269765290448, RPT), Add(Div(PT, DD), 0.8553269765290448)))), Div(Sub(Div(Sub(Sub(Div(Mul(Add(Mul(W, W), Mul(W, OP)), Mul(RPT, W)), Add(W, W)), PT), Mul(Sub(RPT, OP), PT)), Mul(Div(W, OP), Mul(W, OP))), Add(DD, OP)), Div(Add(RT, 0.8553269765290448), Div(PT, Add(Sub(W, PT), Div(PT, OP))))))
    		;
    }
}
