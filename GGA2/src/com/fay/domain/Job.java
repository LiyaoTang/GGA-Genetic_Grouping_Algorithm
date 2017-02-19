package com.fay.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fay.rule.JobRule.*;
import com.fay.domain.Operation;
import com.fay.util.ListHelper;
import com.fay.util.Timer;

public class Job<JobRule> implements Iterable<Operation> {

    private int id;		//锟斤拷锟斤拷ID

    private String name;		//锟斤拷锟斤拷锟斤拷锟�

    private List<Operation> operations;		//锟斤拷锟斤拷锟斤拷锟津集猴拷

    private int relDate;		//锟斤拷锟斤拷锟酵凤拷时锟斤拷 ,锟斤拷锟斤拷锟斤拷系统锟斤拷时锟斤拷

    private double weight;		//锟斤拷锟斤拷权锟斤拷 

    private int duedate;		//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
    
    private int calculateDuedate;

    private int family;		//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷 

    private int nextScheduleNo; //锟铰次碉拷锟饺癸拷锟斤拷锟�
    
    private int operationNum;    //锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
    
    private IJobRule jobRule;
    
    private boolean jobIdle;         //锟斤拷锟斤拷状态
    
    private int jobFinishTime;
    
    private int remainOp;
    
    private double processratio;
    
    public int getRemainOpNumber(){
    	return (this.operationNum-this.nextScheduleNo+1);
    }

    public Job(int id, String name) {
        this.id = id;
        this.name = name;
        this.nextScheduleNo = 1;
        operations = new ArrayList<Operation>();
        this.jobIdle = false;
    }

	public Operation getCurrentOperation() {
		// TODO Auto-generated method stub
		if (nextScheduleNo > operations.size()) { return null; }
		return operations.get(nextScheduleNo-1 -1);
	}
	
	public Operation getPreviousOperation() {
		// TODO Auto-generated method stub
		if (nextScheduleNo > operations.size()) { return null; }
		if (nextScheduleNo == 1) 	return null;           //没锟斤拷锟斤拷一锟斤拷锟斤拷锟斤拷
		return operations.get(nextScheduleNo-1 -1 -1);
	}
	
    public Operation getNextScheduleOperation() {
        if (nextScheduleNo > operations.size()) { return null; }
        return operations.get(nextScheduleNo - 1);
    }

    public int getTardiness() {
        return Math.max(operations.get(operations.size() - 1).getEndTime()
                - duedate, 0);
    }

    public boolean isCompleted() {
        return nextScheduleNo > operations.size();
    }

    public void scheduleOperation() {
        nextScheduleNo++;// 锟斤拷锟斤拷锟斤拷一锟斤拷锟斤拷锟斤拷裙锟斤拷锟�
    }

    public Operation getOperation(int index) {
        if (index < 0 || index >= operations.size()) { throw new ArrayIndexOutOfBoundsException(
                index); }
        return operations.get(index);
    }

    public int getOperationNum() {
        return operations.size();
    }


    public String toString() {
        return name;
    }


    public Iterator<Operation> iterator() {
        return operations.iterator();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRelDate() {
        return relDate;
    }

    public void setRelDate(int relDate) {
        this.relDate = relDate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getDuedate() {
        return duedate;
    }

    public void setDuedate(int duedate) {
        this.duedate = duedate;
    }

    public int getNextScheduleNo() {
        return nextScheduleNo;
    }

    public int getFamily() {
        return family;
    }

    public void setFamily(int family) {
        this.family = family;
    }
    
    public int getFinishedTime() {
        if (!getIdle()) { // 未锟斤拷锟�
            return -1;
        }
        return ListHelper.getLast(operations).getEndTime();
    }
    
  /*  public void reset() {
        nextScheduleNo = 1;
        for (Operation operation : operations) {
            operation.reset();
        }
    }
    */
    
    public void setJobRule(IJobRule jobRule){
    	this.jobRule = jobRule;
    }
    
    public IJobRule getJobRule(){
    	return this.jobRule;
    }
    
    public int getDueDate() {
        return this.getDuedate();
    }
    
    public void setOperationNum(int operationNum) {
		this.operationNum = operationNum;
	}
    
    public void setOperations(List<Operation> operations) {
		this.operations = operations;
		for (Operation operation : operations) {
			operation.setJob(this);
		}// 锟斤拷锟斤拷锟叫癸拷锟斤拷锟斤拷锟斤拷锟揭碉拷锟斤拷锟斤拷锟�
	}
    
    public List<Operation> getOperations() {
		return this.operations;
	}
    
    public Operation get(int index) {
		return operations.get(index);
	}// 锟斤拷取某一锟斤拷锟斤拷锟斤拷
    
    public boolean getIdle(){
    	return this.jobIdle;
    }
    
    public void setJobIdle(boolean i){
    	this.jobIdle = i;
    }
    
    public void setFinishTime(int f){
    	this.jobFinishTime = f;
    }
    
    public int getFinishTime(){
    	return this.jobFinishTime;
    }
    
    public void calculateDuedate(){
    	int duedate = 0,minProcTime = Integer.MAX_VALUE;
    	for(Operation operation : this.operations){
    		for(Machine machine : operation.getProcessMachineList()){
    			if(operation.getProcessingTime(machine) < minProcTime){
    				minProcTime = operation.getProcessingTime(machine);
    			}
    		}
    		duedate +=minProcTime;
    		minProcTime = Integer.MAX_VALUE;
    	}
    	switch(this.getId()%5){
    	case 0:
    		this.calculateDuedate = duedate;
    		this.duedate = duedate;
    		break;
    	case 1:
    		this.calculateDuedate = 3*duedate;
    		this.duedate = 3*duedate;
    		break;
    	case 2:
    		this.calculateDuedate = 6*duedate;
    		this.duedate = 6*duedate;
    		break;
    	case 3:
    		this.calculateDuedate = 9*duedate;
    		this.duedate = 9*duedate;
    		break;
    	case 4:
    		this.calculateDuedate = 12*duedate;
    		this.duedate = 12*duedate;
    		break;
    	case 5:
    		this.calculateDuedate = 15*duedate;
    		this.duedate = 15*duedate;
    		break;
    	}
    	/*switch(this.getId()%10){
    	case 0:
    		this.calculateDuedate = duedate;
    		this.duedate = duedate;
    		break;
    	case 1:
    		this.calculateDuedate = 3*duedate;
    		this.duedate = 3*duedate;
    		break;
    	case 2:
    		this.calculateDuedate = 6*duedate;
    		this.duedate = 6*duedate;
    		break;
    	case 3:
    		this.calculateDuedate = 9*duedate;
    		this.duedate = 9*duedate;
    		break;
    	case 4:
    		this.calculateDuedate = 12*duedate;
    		this.duedate = 12*duedate;
    		break;
    	case 5:
    		this.calculateDuedate = 15*duedate;
    		this.duedate = 15*duedate;
    		break;
    	case 6:
    		this.calculateDuedate = 18*duedate;
    		this.duedate = 18*duedate;
    		break;
    	case 7:
    		this.calculateDuedate = 21*duedate;
    		this.duedate = 21*duedate;
    		break;
    	case 8:
    		this.calculateDuedate = 24*duedate;
    		this.duedate = 24*duedate;
    		break;
    	case 9:
    		this.calculateDuedate = 27*duedate;
    		this.duedate = 27*duedate;
    		break;    		
    	}
    	/*
    	if(this.getId()%3 == 0){
    		this.calculateDuedate = duedate;
        	this.duedate = duedate;
    	}
    	else if(this.getId()%3 == 1){
    		this.calculateDuedate = 3*duedate;
        	this.duedate = 3*duedate;
    	}
        else if(this.getId()%10 == 2){
        	this.calculateDuedate = 6*duedate;
        	this.duedate = 6*duedate;
    	}*/
    	
    }
    
    public int getCalculateDuedate(){
    	return this.calculateDuedate;
    }
    
    public double getProcessRatio(){
    	int processTime = 0;
    	if(this.nextScheduleNo == 1)  return 0;
    	if(Timer.currentTime() == 0)  return 0;
    	for(int i = 0 ; i < this.nextScheduleNo-1 ; i++){
    		processTime += this.operations.get(i).getProcessingTime();
    	}
    	return processTime/Timer.currentTime();
    }
    
    public void reset(){
    	for(Operation operation : this.getOperations()){
    		operation.reset();
    	}
    	this.jobIdle = false;
    	this.nextScheduleNo = 1;
    	this.jobRule = null;
    	this.jobFinishTime = 0;
    	this.processratio = 0.0;
    	this.remainOp = this.operationNum;
    }

}

