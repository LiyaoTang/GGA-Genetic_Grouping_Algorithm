package com.fay.rule.MachineRule;
import com.fay.domain.Operation;
import com.fay.util.Timer;

/** 机器选择工件，Time In System在系统时间越长越优先 */
public class MachineTIS implements IMachineRule {

	
	public double calPrio(Operation operation){
    	return Timer.currentTime()-operation.getArrivalTime();
    }
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
