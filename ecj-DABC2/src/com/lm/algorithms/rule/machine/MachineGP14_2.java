package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP14_2 extends GPRuleBase implements IMachineRule {

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
    Add(Add(Sub(Add(Div(Mul(Sub(RT, 0.7320559251079132), Mul(W, W)), Sub(Mul(PT, RPT), Sub(RPT, Add(Add(RT, W), Div(W, RPT))))), Add(Mul(Sub(PT, W), Add(W, PT)), Div(Add(Add(Sub(Mul(Div(RPT, Div(W, PT)), Add(RT, RT)), Div(Mul(W, W), Sub(RPT, AT))), Div(Add(Add(Div(Div(RPT, Div(W, PT)), Mul(Sub(RT, 0.7320559251079132), Mul(W, W))), Mul(Div(Div(W, RPT), 0.7320559251079132), Mul(Add(Mul(Sub(PT, W), Add(W, PT)), Div(Div(W, 0.7320559251079132), AT)), Div(W, RPT)))), Div(Div(RPT, Div(Div(W, 0.7320559251079132), AT)), Mul(Sub(RT, 0.7320559251079132), Mul(W, W)))), Sub(RPT, Add(Sub(Mul(PT, RPT), Mul(PT, PT)), Div(W, RPT))))), Div(Div(RPT, Div(Mul(W, W), PT)), Mul(Sub(RT, 0.7320559251079132), Mul(W, W)))), Sub(RPT, Div(Sub(RT, 0.7320559251079132), Sub(RPT, AT)))))), Mul(DD, PT)), Mul(Div(Add(Add(RT, RT), Div(W, RPT)), Sub(Mul(PT, RPT), Mul(PT, PT))), Add(Mul(Mul(Sub(RT, 0.7320559251079132), Mul(W, W)), Add(W, PT)), Div(Add(Add(Div(Div(RPT, Div(Mul(W, W), PT)), Mul(Sub(RT, 0.7320559251079132), Mul(W, W))), Mul(Div(Div(W, RPT), 0.7320559251079132), Mul(Add(Mul(Sub(PT, W), Add(W, PT)), Div(Div(W, 0.7320559251079132), AT)), Div(W, RPT)))), Div(Div(RPT, Div(Div(W, 0.7320559251079132), AT)), Mul(Sub(RT, 0.7320559251079132), Mul(W, W)))), Sub(RPT, Div(Sub(RT, 0.7320559251079132), Sub(RPT, AT))))))), Div(Div(RPT, Div(W, PT)), Mul(Sub(RT, 0.7320559251079132), Mul(W, W))))
    		;
    }
}
