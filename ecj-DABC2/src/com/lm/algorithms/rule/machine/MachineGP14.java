package com.lm.algorithms.rule.machine;

import com.lm.domain.Entity;
import com.lm.algorithms.rule.GPRuleBase;
/**
 * GP引入的节点
 */
public class MachineGP14 extends GPRuleBase implements IMachineRule {

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
    		Sub( 
    				Sub( 
    					Mul( 
    						PT
    						,
    						RPT
    						)
    					,
    					Mul( 
    						Sub( 
    							DD
    							,
    							PT
    							)
    						,
    						Mul(
    							RPT
    							,
    							DD
    							)
    						)
    					)
    				,
    				Sub( 
    					Mul( 
    						OP
    						,
    						AT
    						)
    					,
    					Div( 
    						Add( 
    							Div(
    								Sub(
    									AT
    									,
    									0.5141037958491357
    									)
    								,
    								Mul( 
    									OP 
    									,
    									AT
    									)
    								)
    							,
    							Sub( 
    								Sub( 
    									Sub(
    										Div( 
    											PT
    											,
    											0.5141037958491357
    											)
    										,
    										Mul( 
    											PT
    											,
    											RPT
    											)
    										)
    									,
    									Sub( 
    										DD
    										,
    										PT
    										)
    									)
    								,
    								Sub(
    									Mul(
    										OP
    										,
    										AT
    										)
    									,
    									Sub( 
    										Sub( 
    											Sub( 
    												Div( 
    													Div( 
    														OP
    														,
    														PT
    														)
    													,
    													0.5141037958491357
    													)
    												,
    												Add( 
    													RT
    													, 
    													0.5141037958491357
    													)
    												)
    											,
    											Mul( 
    												Sub(
    													DD
    													,
    													PT
    													)
    												,
    												Mul(
    													RPT 
    													,
    													DD
    													)
    												)
    											)
    										,
    										Sub(
    											PT
    											, 
    											RT
    											)
    										)
    									)
    								)
    							)
    						,
    						OP
    						)
    					)
    				)
    		;
    }
}
