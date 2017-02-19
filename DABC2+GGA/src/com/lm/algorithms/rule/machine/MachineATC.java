package com.lm.algorithms.rule.machine;

import java.util.List;

import com.lm.domain.Entity;
import com.lm.domain.Machine;
import com.lm.util.Timer;
import java.lang.Math.* ;

/**
 * ATC = (Wj/pj)*exp(-(max(dj-pj-t,0)/(k*avgp)))
 */
public class MachineATC implements IMachineRule {

//    @Override
	   public double calPrio(Entity e) {
	        // x = (due date - processing time-current time)/ K*avg(processing time)

			   double k = 3;              //k的确定根据是V&M1987论文
			   
			   int n = e.getMachine().getBufferSize();
			   int p = 0;                         // p:总的剩余工件加工时间
			   for(int i=0;i<n;i++){
				   p=p + e.getMachine().getBuffer().get(i).getProcessingTime(e.getMachine());
			   }
			   double avgp = p/n;          //avgp:平均剩余工件加工时间
			  
			   double x = Math.max((e.getDueDate()-e.getProcessingTime()-Timer.currentTime()),0)/k*avgp;
			   
			   
			   return (e.getWeight() / e.getProcessingTime()) * Math.exp(-x);

	    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
