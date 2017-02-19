package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP16_5 extends GPRuleBase implements IMachineRule {

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
    Mul(Div(0.856436884448583, W), Sub(Add(Sub(Add(Div(Div(0.856436884448583, W), Mul(Div(Mul(W, W), Div(RPT, W)), Sub(RPT, 0.856436884448583))), Sub(Div(Add(PT, OP), Mul(Div(Mul(W, W), Div(RPT, W)), DD)), Mul(Add(PT, OP), Mul(W, Sub(W, PT))))), Div(Add(DD, PT), Div(W, PT))), Mul(Div(0.856436884448583, W), Sub(Div(Div(RPT, W), Sub(Div(W, PT), Div(W, 0.856436884448583))), Mul(Add(PT, Div(Div(Div(0.856436884448583, W), Mul(Div(Mul(W, W), Div(RPT, W)), Sub(RPT, 0.856436884448583))), Mul(Add(PT, Div(Add(PT, Div(W, PT)), Mul(Div(Sub(W, PT), Mul(Div(Mul(W, W), Div(RPT, W)), Sub(RPT, 0.856436884448583))), Sub(RPT, 0.856436884448583)))), Sub(RPT, 0.856436884448583)))), Div(RPT, W))))), Mul(Add(PT, OP), Div(RPT, W))))
    		;
    }
}
