package com.fay.rule.MachineRule;
import com.fay.domain.Operation;
import com.fay.util.Timer;

/** ����ѡ�񹤼���Time In System��ϵͳʱ��Խ��Խ���� */
public class MachineTIS implements IMachineRule {

	
	public double calPrio(Operation operation){
    	return Timer.currentTime()-operation.getArrivalTime();
    }
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
