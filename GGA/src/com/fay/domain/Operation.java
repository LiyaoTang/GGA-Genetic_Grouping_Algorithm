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
	
	private final int id;     		 //锟斤拷锟斤拷ID
	
	private final String name;    //锟斤拷锟斤拷锟斤拷锟�
	
	private Operation prev;   		 //锟斤拷一锟斤拷锟斤拷锟斤拷
	
	private Operation next;		//锟斤拷一锟斤拷锟斤拷锟斤拷
	
	private Map<Machine, Integer> procTimes;			//锟斤拷选锟斤拷锟斤拷锟斤拷锟斤拷时锟斤拷锟斤拷斜锟�     key 锟斤拷锟斤拷id
	
	private Machine selectedMachine;                      //锟斤拷锟斤拷锟斤拷选锟斤拷幕锟斤拷锟�
	
	private Job job;		//锟斤拷锟斤拷锟斤拷锟斤拷
	
	private int startTime;		//锟斤拷始时锟斤拷
	
	private int endTime;		//锟斤拷锟斤拷时锟斤拷
	
	private int startTransferTime;
	
	private int arrivalTime;		//锟斤拷锟斤拷时锟斤拷          //0,锟斤拷锟斤拷锟斤拷   
	
	private Machine processingMachine;		//锟斤拷锟饺该癸拷锟斤拷幕锟斤拷锟�
	
	private double score;	//锟斤拷锟斤拷值
	
	private int processingTime;
	
	private int[] procmachine = null;
	//锟斤拷锟斤拷锟窖★拷锟斤拷锟斤拷锟斤拷锟�
	
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

	    /** 锟斤拷取锟斤拷锟斤拷时锟斤拷锟斤拷锟斤拷准锟斤拷时锟斤拷锟杰猴拷  */
	    public int getProcessingTime(Machine m) {
	        return procTimes.get(m);
	    }

	    /** 锟斤拷取锟缴加癸拷锟斤拷锟斤拷锟叫憋拷 */
	    public List<Machine> getProcessMachineList() {
	        return new ArrayList<Machine>(procTimes.keySet());
	    }

	    /** 锟斤拷取剩锟斤拷时锟斤拷 */
	    public int getRemainingTime() {
	        return this.getRemainingTime();
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
			}// 锟斤拷取锟矫癸拷锟斤拷杉庸锟斤拷幕锟斤拷锟斤拷锟絀D
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

	
	

