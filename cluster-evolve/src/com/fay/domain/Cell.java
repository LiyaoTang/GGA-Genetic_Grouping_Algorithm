package com.fay.domain;

import java.util.ArrayList;
import java.util.List;

import com.fay.domain.Machine;
import com.fay.domain.MachineSet;
import com.fay.domain.Vehicle;
import com.fay.rule.BufferOutRule.*;
import com.fay.util.Timer;

public class Cell {
	
	private int id;    //锟斤拷元ID
	
	private String name;    //锟斤拷元锟斤拷锟�
	
	private int[] transferTime;    //转锟斤拷时锟斤拷
	
	private Vehicle transCar;  //小锟斤拷锟斤拷锟斤拷
	
	private BufferOut cellBufferOut;    //锟斤拷锟斤拷元锟斤拷锟斤拷锟斤拷
	
	private List<Machine> cellMachine;      //锟矫碉拷元锟斤拷拥锟叫的伙拷锟斤拷
	
	private int NextArrivalJobID;			//锟斤拷一锟斤拷锟斤拷锟斤拷墓锟斤拷锟�
	
	private IBufferOutRule bufferOutRule;
	
	private List<Machine> processingMachine; 
	
	private Machine nextReadyMachine;
	
	public List<Machine> getProcessingMachine(){
		this.processingMachine = new ArrayList<Machine>();
		for(Machine machine : cellMachine){
			if(machine.getNextIdleTime() != 0){
				processingMachine.add(machine);
			}
		}
		return this.processingMachine;
	}
	
	public Machine getNextReadyMachine(){
		int min = Integer.MAX_VALUE;
    	Machine minMachine = null;
    	for(Machine machine : cellMachine){
    		if(machine.getNextIdleTime() != 0 && machine.getNextIdleTime() < min){
    			min = machine.getNextIdleTime();
    			minMachine = machine;
    		}
    	}
    	return minMachine;
	}
	
	public Cell(int Id, String name) {
		this.id = Id;
		this.name = name;
		this.cellMachine = new ArrayList<Machine>();
		this.transCar =new Vehicle();	
		this.cellBufferOut = new BufferOut();
		this.bufferOutRule = new BufferOutEDD();
		this.processingMachine = new ArrayList<Machine>();
	}
	
	public void setCellMachineSet(MachineSet set){
		for(Machine m :set){
			if(m.getNumInCell()==this.id)
				this.cellMachine.add(m);
		}
	}
	
	public List<Machine> getCellMachine(){
		return this.cellMachine;
	}
	
	public void setBufferOutRule(IBufferOutRule rule){
		this.bufferOutRule = rule;
	}
	
	public IBufferOutRule getBufferOutRule(){
		return this.bufferOutRule;
	}

	public Cell() {
		// TODO Auto-generated constructor stub
	}

	public int GetTransferTime(int destNumInCell) {
		return this.transferTime[destNumInCell-1];
	}
	
	public int[] GetTransferTimes() {
		return this.transferTime;
	}


	public Vehicle GetVehicle(){
		return this.transCar;
	}

	public int GetID(){
		return this.id;
	}

	public String getname(){
		return this.name;
	}

	
	public void RemoveFromCellBufferOut(Operation operation) {
		cellBufferOut.removeOperation(operation);
	}
	
	public void AddOperationToBufferOut(Operation o){
		cellBufferOut.addOperation(o);
	}
	
	public BufferOut getBufferOut(){
		return this.cellBufferOut;
	}
	
	public void setBufferOut(BufferOut newBufferOut){
		this.cellBufferOut = newBufferOut;
	}
	
	public void setTransferTime(int[] transferTime){
		this.transferTime = transferTime;
	}
	
	public int getTransferTime(int cellNum){
		return this.transferTime[cellNum-1];
	}
	
	public void setCellVehicle(Vehicle v){
		this.transCar = v;
	}
	
	public void reset(){
		this.transCar.rest_1();
		this.cellBufferOut = new BufferOut();
		this.bufferOutRule = null;
		this.processingMachine = new ArrayList<Machine>();
		this.NextArrivalJobID = 0;
		this.nextReadyMachine = null;
	}
		
}
	
	
	
	


