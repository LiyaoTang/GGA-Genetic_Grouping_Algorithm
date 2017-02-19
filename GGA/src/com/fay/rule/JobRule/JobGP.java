package com.fay.rule.JobRule;

import com.fay.domain.Machine;
import com.fay.domain.Operation;
import com.fay.scheduler.SimpleScheduler;
import com.fay.util.Timer;

import ec.gp.GPIndividual;

public class JobGP implements IJobRule{

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public double calPrio(Operation operation, Machine m) {
    	 // 绔偣鍙橀噺璧嬪�
        SimpleScheduler.GPInfo.assiProblem.duedate = operation.getJob().getDuedate();
        SimpleScheduler.GPInfo.assiProblem.procTime = operation.getProcessingTime(m);
        if(operation.getJob().getNextScheduleNo() == 1)
        	SimpleScheduler.GPInfo.assiProblem.transTime = 0;
        else SimpleScheduler.GPInfo.assiProblem.transTime = operation.getJob().getCurrentOperation().getSelectedMachine().getCell()
        												.getTransferTime(m.getNumInCell());
        if(m.getCell().GetVehicle().getBackTime() == 0)
        	SimpleScheduler.GPInfo.assiProblem.vehicleReturnTime = 0;
        else
        	SimpleScheduler.GPInfo.assiProblem.vehicleReturnTime = m.getCell().GetVehicle().getBackTime() - Timer.currentTime();
        if(m.getNextIdleTime() == 0)
        	SimpleScheduler.GPInfo.assiProblem.waitTime = 0;
        else 
        	SimpleScheduler.GPInfo.assiProblem.waitTime = m.getNextIdleTime() - Timer.currentTime();
        SimpleScheduler.GPInfo.assiProblem.bufferNum = m.getBufferIn().size();
        
    	
        ((GPIndividual) SimpleScheduler.GPInfo.ind).trees[0].child.eval(
                SimpleScheduler.GPInfo.state, SimpleScheduler.GPInfo.threadnum,
                SimpleScheduler.GPInfo.input, SimpleScheduler.GPInfo.stack,
                (GPIndividual) SimpleScheduler.GPInfo.ind,
                SimpleScheduler.GPInfo.assiProblem);
        double result = SimpleScheduler.GPInfo.input.x;
        return result;
    }	
}
