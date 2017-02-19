package com.fay.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fay.domain.Job;

public class JobSet implements Iterable<Job> {

	private List<Job> jobs;

    public JobSet() {
        jobs = new ArrayList<Job>();
    }

    public int size(){
    	return jobs.size();
    }


    public void addJob(Job job) {
        jobs.add(job);
    }
    
    
    public Job get(int index){
        return jobs.get(index);
    }


    public boolean isScheduleAll() {
        for (Job job : jobs) {
   //         if (job.isCompleted() == false) return false;
        }
        return true;
    }


    public Iterator<Job> iterator() {
        return jobs.iterator();
    }
    
    public void reset() {
        for (Job job : jobs) {
      //      job.reset();
        }
    }
    
    
    
}
