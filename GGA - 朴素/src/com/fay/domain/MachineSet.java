package com.fay.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MachineSet implements Iterable<Machine> {

   private List<Machine> machines;
	
   public MachineSet() {
        machines = new ArrayList<Machine>();
    }

    public void add(Machine machine) {
        machines.add(machine);
    }

    public Machine get(int index) {
        return machines.get(index);
    }

    public int size(){
    	return machines.size();
    }

    public Iterator<Machine> iterator() {
        return machines.iterator();
    }
}
