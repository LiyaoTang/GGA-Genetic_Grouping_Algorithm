package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP20_5 extends GPRuleBase implements IMachineRule {

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
    Sub(Add(Sub(RPT, DD), Add(Mul(W, Div(Sub(RPT, DD), Div(Div(Mul(Add(Add(PT, Div(Add(RPT, OP), Add(RT, 0.9452255238577562))), Mul(W, Mul(0.9452255238577562, DD))), Add(PT, Div(Add(RPT, OP), Add(Mul(PT, 0.9452255238577562), Div(W, RT))))), Add(Div(Sub(W, W), Div(OP, RPT)), W)), DD))), Sub(Div(Div(Mul(Add(Add(PT, Div(Add(RPT, OP), Add(Mul(PT, 0.9452255238577562), Mul(Mul(W, AT), Sub(RT, Mul(Div(Sub(RPT, DD), DD), Mul(W, W))))))), Mul(W, Mul(0.9452255238577562, DD))), Div(Add(RPT, OP), Mul(W, OP))), Add(Div(Sub(W, W), Div(Div(Div(Mul(W, W), Add(PT, Div(Sub(RPT, DD), Mul(PT, RPT)))), Mul(Div(Sub(RPT, DD), DD), Div(PT, W))), RPT)), W)), DD), Mul(Mul(PT, 0.9452255238577562), Div(DD, Div(Add(RPT, OP), Mul(W, OP))))))), Div(Div(Mul(Add(Add(PT, Mul(RT, RT)), Add(RT, 0.9452255238577562)), Div(Add(RPT, OP), Mul(W, OP))), Add(Add(W, RT), W)), Add(Sub(W, W), W)))
    		;
    }
}
