package com.fay.domain;

import java.util.ArrayList;

import com.fay.util.Timer;

import com.fay.rule.MachineRule.*;

public class Machine {
	
	private int id;			//锟斤拷锟斤拷ID

	private String name;		//锟斤拷锟斤拷锟斤拷锟�

	private int numInCell;		//锟斤拷锟斤拷锟斤拷锟节碉拷元锟斤拷

	private int nextIdleTime;		//锟斤拷锟斤拷锟斤拷锟斤拷锟酵凤拷时锟斤拷

	private BufferIn bufferIn;		//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�

//	private BufferOut bufferOut;	//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷

	private Operation selectedOperation;	//锟斤拷锟斤拷选锟斤拷锟斤拷鹊墓锟斤拷锟�

	private Operation preOperation;	//锟斤拷锟斤拷锟斤拷一锟轿加癸拷锟侥癸拷锟斤拷

	private Operation processingOperation;	//锟斤拷锟斤拷锟斤拷锟节加癸拷锟侥癸拷锟斤拷

	private int state;	//锟斤拷锟斤拷状态,1为锟斤拷锟节加癸拷锟斤拷0为锟斤拷锟斤拷
	
	private IMachineRule machineRule;
	
	private Cell cell;      //锟斤拷锟节碉拷元
	

	
	public Machine(int id, String name){
		this.id = id;
		this.name = name;
		this.nextIdleTime = 0;
		this.bufferIn = new BufferIn(this);
		selectedOperation = null;
		processingOperation = null;
		this.state = 0;	
		this.machineRule = new MachineSPT();
		this.cell = new Cell();

	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getNumInCell() {
		return numInCell;
	}

	public void setNumInCell(int numInCell) {
		this.numInCell = numInCell;
	}

	public void addOperationToBufferIn(Operation o){
		bufferIn.addOperation(o);
	}
	
	public boolean isBufferEmpty(){
		return bufferIn.isEmpty();
	}

	public BufferIn getBufferIn() {
		return bufferIn;
	}
	
/*	public void AddOperationToBufferOut(Operation o){
		bufferOut.addOperation(o);
		//锟斤拷状态
	}
	*/
	
/*	public boolean IsBufferOutEmpty(){
		return bufferOut.isEmpty();
	}
	*/

/*	public BufferOut GetBufferOut() {
		return bufferOut;
	}
	*/

	public Operation getSelectedOperation() {
		return selectedOperation;
	}
	
	public void setSelectedOperation(Operation o) {
		this.selectedOperation = o;
	}

	public void setOperation(Operation operation) {
		this.selectedOperation = operation;
	}

	public Operation getProcessingOperation() {
		return processingOperation;
	}

	public void setProcessingOperation(Operation processingOperation) {
		this.processingOperation = processingOperation;
	}
	public void setProcessingOperationNull() {
		this.processingOperation = null;
	}
	public void setSelectedOperationNull() {
		this.selectedOperation = null;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getNextIdleTime() {
		return nextIdleTime;
	}

	public void setNextIdleTime(int nextIdleTime) {
		this.nextIdleTime = nextIdleTime;
	}
	
	
	public void removeOperationFromBufferIn(Operation operation) {
		bufferIn.removeOperation(operation);
	}
	
/*	public void RemoveOperationFromBufferOut(Operation operation) {
		bufferOut.removeOperation(operation);
	}
	*/

	@Override
	public String toString() {
		return name;
	}
	
	public IMachineRule getMachineRule(){
		return this.machineRule;
	}
	
	public void setMachineRule(IMachineRule m){
		this.machineRule = m;
	}
	
	public void setCell(Cell c){
		this.cell = c;
	}
	
	public Cell getCell(){
		return this.cell;
	}
	
	public void reset(){
		this.nextIdleTime = 0;
		this.selectedOperation = null;
		this.state = 0;
		this.machineRule = null;
		this.bufferIn = new BufferIn(this);
	}
}
