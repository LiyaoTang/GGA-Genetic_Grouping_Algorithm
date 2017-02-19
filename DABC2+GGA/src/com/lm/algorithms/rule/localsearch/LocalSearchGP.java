package com.lm.algorithms.rule.localsearch;

import com.lm.algorithms.SimpleScheduler;
import com.lm.domain.Entity;
import ec.gp.GPIndividual;

/**
 * GP引入的节点
 */
public class LocalSearchGP implements ILocalSearchRule {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public double calPrio(Entity e) {
    	 // 端点变量赋值
    	
        SimpleScheduler.GPInfo.localProblem.insert = e.getWeight();
        SimpleScheduler.GPInfo.localProblem.swap = e.getDueDate();
        
//        SimpleScheduler.GPInfo.seqProblem.fullnessOfBatch = e.getFullnessOfBatch();

        // 计算表达式结果
        ((GPIndividual) SimpleScheduler.GPInfo.ind).trees[0].child.eval(
                SimpleScheduler.GPInfo.state, SimpleScheduler.GPInfo.threadnum,
                SimpleScheduler.GPInfo.input, SimpleScheduler.GPInfo.stack,
                (GPIndividual) SimpleScheduler.GPInfo.ind,
                SimpleScheduler.GPInfo.localProblem);
        double result = SimpleScheduler.GPInfo.input.x;
        return result;
    }	
}
