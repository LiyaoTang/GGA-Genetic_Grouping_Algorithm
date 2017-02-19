package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP2_2 extends GPRuleBase implements IMachineRule {

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
    		Add(Add(Add(Add(Add(Add(Div(Sub(0.718956666125419, Sub(Add(RT, Sub(Sub(DD, RPT), RPT)), Div(RPT, Add(AT, 0.718956666125419)))), Sub(Add(OP, W), Div(0.718956666125419, Mul(W, PT)))), Sub(Add(Div(Mul(Div(0.718956666125419, Div(0.718956666125419, Mul(W, PT))), Sub(Add(Mul(Add(Div(Add(RT, OP), Div(W, RPT)), Add(PT, AT)), Sub(Add(PT, AT), Mul(W, PT))), Sub(Div(Sub(0.718956666125419, RPT), Div(W, PT)), Mul(W, PT))), RPT)), Sub(0.718956666125419, RPT)), Div(W, DD)), Mul(W, PT))), W), Sub(Add(Div(Mul(Div(0.718956666125419, Div(0.718956666125419, AT)), Sub(Add(Mul(Div(Add(RT, OP), Div(W, RPT)), Sub(Add(PT, AT), Mul(DD, W))), Sub(Div(0.718956666125419, Mul(W, PT)), Mul(W, PT))), RPT)), Mul(DD, W)), OP), Add(AT, 0.718956666125419))), W), Sub(Div(Sub(Add(Div(Sub(0.718956666125419, RPT), Div(W, PT)), Sub(Add(Div(Mul(Div(0.718956666125419, Div(0.718956666125419, Mul(W, PT))), Sub(Add(Mul(Div(Add(RT, OP), Div(W, RPT)), Sub(Add(PT, AT), Mul(W, PT))), Sub(Mul(W, PT), Mul(W, PT))), RPT)), Sub(Mul(Mul(DD, W), Add(OP, W)), Sub(Add(W, DD), Mul(DD, W)))), Div(W, DD)), Sub(W, Mul(W, PT)))), RPT), Div(W, PT)), Sub(Add(W, DD), Add(Sub(0.718956666125419, RPT), Add(PT, AT))))), Mul(Sub(Add(Sub(0.718956666125419, DD), Sub(Sub(DD, RPT), RPT)), Div(RPT, Add(AT, 0.718956666125419))), Mul(Div(Add(RT, OP), Div(W, RPT)), Sub(Add(PT, AT), Mul(W, PT)))))


    		;
    }
}
