package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP5_4 extends GPRuleBase implements IMachineRule {

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
    		Div(Add(Div(Div(Mul(PT, RT), Div(Mul(Add(PT, DD), OP), Mul(Mul(Add(Add(Sub(Mul(Div(0.6878727733602243, PT), Mul(RT, Mul(Mul(RT, 0.6878727733602243), Mul(PT, OP)))), Sub(Mul(PT, RT), Mul(0.6878727733602243, W))), Mul(RPT, W)), Div(PT, Mul(PT, PT))), Mul(RPT, DD)), DD))), Mul(Mul(0.6878727733602243, W), Div(Div(Add(PT, RPT), Mul(AT, RPT)), Add(Add(W, RT), W)))), Sub(Mul(OP, RT), Div(Sub(RPT, Add(Div(Div(DD, Sub(Mul(OP, OP), Div(Sub(RPT, Div(W, PT)), Add(Sub(RPT, Div(PT, Mul(PT, PT))), Mul(RPT, DD))))), Div(DD, W)), Add(Sub(W, Sub(Mul(OP, OP), Div(Sub(RPT, Div(W, PT)), Add(Sub(RPT, Div(W, PT)), Mul(RPT, DD))))), RT))), Add(Div(W, DD), Div(W, PT))))), Sub(Mul(Div(0.6878727733602243, PT), Mul(RT, Mul(OP, Add(Add(Div(W, 0.6878727733602243), Sub(RPT, Div(W, PT))), RT)))), Sub(Div(W, PT), Sub(Mul(Div(0.6878727733602243, PT), Sub(Div(W, PT), Sub(Mul(PT, RT), Sub(Div(W, PT), Mul(0.6878727733602243, W))))), Sub(Div(W, PT), Mul(0.6878727733602243, W))))))


    		
    		;
    }
}
