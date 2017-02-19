package com.fay.rule.BufferOutRule;

import com.fay.domain.Cell;
import com.fay.domain.Job;
import com.fay.domain.Machine;
import com.fay.domain.Operation;
import com.fay.scheduler.SimpleScheduler;

import ec.gp.GPIndividual;

public class BufferOutGP implements IBufferOutRule{

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public double calPrio(Cell cell,Operation operation) {
    	 // 端点变量赋值
        SimpleScheduler.GPInfo.buffProblem.duedate = operation.getJob().getDuedate();
        SimpleScheduler.GPInfo.buffProblem.weight = operation .getJob().getWeight();
        SimpleScheduler.GPInfo.buffProblem.transTime = cell.getTransferTime(operation.getSelectedMachine().getNumInCell());
        SimpleScheduler.GPInfo.buffProblem.procTime = operation.getProcessingTime();
        SimpleScheduler.GPInfo.buffProblem.remainOpNum = operation.getJob().getRemainOpNumber();
        SimpleScheduler.GPInfo.buffProblem.waitTime = operation.getSelectedMachine().getNextIdleTime();
        SimpleScheduler.GPInfo.buffProblem.arrivalTime = operation.getArrivalBufferOutTime();
    	
        ((GPIndividual) SimpleScheduler.GPInfo.ind).trees[0].child.eval(
                SimpleScheduler.GPInfo.state, SimpleScheduler.GPInfo.threadnum,
                SimpleScheduler.GPInfo.input, SimpleScheduler.GPInfo.stack,
                (GPIndividual) SimpleScheduler.GPInfo.ind,
                SimpleScheduler.GPInfo.buffProblem);
        double result = SimpleScheduler.GPInfo.input.x;
        return result;
    }	

}
