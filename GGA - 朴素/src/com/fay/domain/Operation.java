package com.fay.domain;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fay.domain.Machine;
import com.fay.domain.Operation;
import com.fay.rule.JobRule.*;
import com.fay.util.Timer;

public class Operation {
	
	private final int id;
	
	private final String name;

	private Operation prev;

	private Operation next;
	
	private Map<Machine, Integer> procTimes;
	
	private Machine selectedMachine;
	
	private Job job;
	
	private int startTime;
	
	private int endTime;

	private int startTransferTime;
	
	private int arrivalTime;

	private Machine processingMachine;

	private double score;

	private int processingTime;
	
	private int[] procmachine = null;
	
	private int nextCellID;
	
	private int transferCarId_from;             
	
	private int transferCarId_to;  
	
	private int arrivalBufferOutTime;
	
	public int getArrivalBufferOutTime(){
		return this.arrivalBufferOutTime;
	}
	
	public void setArrivalBufferOutTime(int time){
		this.arrivalBufferOutTime = time;
	}
	
	public void setArrivalTime(int time){
		this.arrivalTime = time;
	}

	public void setTransferCarId_to(){
		this.transferCarId_to = this.selectedMachine.getNumInCell();
	}
	
	public int getTransferCarId_from(){
		return this.transferCarId_from;
	}
	
	public void setTransferCarId_from(int id){
		this.transferCarId_from = id;
	}
	
	public int getTransferCarId_to(){
		return this.transferCarId_to;
	}

	public void setStartTransferTime(int time){
		this.startTransferTime = time;
	}

	public int getStartTransferTime(){
		return this.startTransferTime;
	}

	public Operation(int id, String name) {
	        this.id = id;
	        this.name = name;
	        startTime = 0;
	        endTime = 0;
	        arrivalTime = 0;
	        prev = null;
	        next = null;
	        procTimes = new HashMap<Machine, Integer>();
	        selectedMachine = null;
	        transferCarId_from = 0;
	        transferCarId_to = 0;
	    }

	    public int getProcessingTime(Machine m) {
	        return procTimes.get(m);
	    }

	    public List<Machine> getProcessMachineList() {
	        return new ArrayList<Machine>(procTimes.keySet());
	    }

	    public int getArrivalTime() {
	        return this.arrivalTime;
	    }

	    public int hashCode() {
	        final int prime = 31;
	        int result = 1;
	        result = prime * result + id;
	        result = prime * result + ((name == null) ? 0 : name.hashCode());
	        return result;
	    }

	    public Operation getPrev() {
	        return prev;
	    }

	    public void setPrev(Operation prev) {
	        this.prev = prev;
	    }

	    public Operation getNext() {
	        return next;
	    }

	    public void setNext(Operation next) {
	        this.next = next;
	    }

	    public Map<Machine, Integer> getProcTimes() {
	        return procTimes;
	    }

	    public void setProcTimes(Map<Machine, Integer> procTimes) {
	        this.procTimes = procTimes;
	    }

	    public int getStartTime() {
	        return startTime;
	    }

	    public void setStartTime(int startTime) {
	        this.startTime = startTime;
	    }

	    public int getEndTime() {
	        return endTime;
	    }

	    public void setEndTime(int endTime) {
	        this.endTime = endTime;
	    }

	    public Machine getprocessingMachine() {
	        return processingMachine;
	    }

	    public void setprocessingMachine(Machine machine) {
	        this.processingMachine = machine;
	    }

	    public int getId() {
	        return id;
	    }

	    public String getName() {
	        return name;
	    }
	    
		public boolean isArrived(){
		    return arrivalTime <= Timer.currentTime();
		}
	    
	    public void setSelectedMachine(Machine m){
	    	this.selectedMachine = m;
	    }
	    
	    public Machine getSelectedMachine(){
	    	return this.selectedMachine;
	    }
	    
	    public Job getJob(){
	    	return this.job;
	    }
	    
	    public int getProcessingTime() {
	    	if(selectedMachine == null)  return 0;
	        return this.procTimes.get(selectedMachine);
	    }
	    
	    public void setProcmachine() {
			ArrayList<Machine> arrList = new ArrayList<Machine>();
			Set set = this.getProcTimes().keySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				arrList.add((Machine) iterator.next());
			}
			int arr[] = new int[arrList.size()];
			for (int length = 0; length < arrList.size(); length++) {
				arr[length] = arrList.get(length).getId();
			}
			this.procmachine = arr;
		}
	    
	    public int[] getProcmachine() {
			return procmachine;
		}
	    
	    public void setJob(Job job) {
			this.job = job;
		}

	    public void setNextCell(int id){
	    	this.nextCellID = id;
	    }

	    public int getNectCell(){
	    	return this.nextCellID;
	    }

	    public void reset() {
	        startTime = 0;
	        endTime = 0;
	        arrivalTime = 0;
	        processingMachine = null;
	        this.prev = null;
	        this.next = null;
	        this.selectedMachine = null;
	        this.startTransferTime = 0;
	        this.arrivalTime = 0;
	    	this.processingTime = 0;
	    	this.nextCellID = 0;
	    	this.transferCarId_from = 0;             	    	
	    	this.transferCarId_to = 0;  	    	
	    	this.arrivalBufferOutTime = 0;
	    }
	}

	
	

