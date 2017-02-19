package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP15_2 extends GPRuleBase implements IMachineRule {

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
    Div(Mul(Sub(Sub(Sub(Div(Mul(Sub(Sub(Add(RT, PT), Mul(RPT, PT)), Div(Mul(Div(Div(Div(RPT, W), Div(W, DD)), Sub(Add(0.963652219225989, W), Add(RT, PT))), Div(Add(0.963652219225989, Mul(DD, 0.963652219225989)), Sub(Add(0.963652219225989, Sub(Div(W, DD), Mul(RPT, PT))), Sub(W, RT)))), Mul(DD, W))), Div(Div(Div(DD, Div(0.963652219225989, DD)), Div(W, DD)), Sub(Add(0.963652219225989, W), Div(AT, AT)))), W), Mul(Div(AT, AT), Mul(DD, W))), W), W), Div(Div(Div(RPT, W), Sub(OP, RT)), Sub(Add(0.963652219225989, W), Div(0.963652219225989, RT)))), Mul(DD, W))
    		;
    }
}
