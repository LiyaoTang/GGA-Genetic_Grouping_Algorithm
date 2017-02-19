package com.fay.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fay.domain.Machine;
import com.fay.domain.Operation;

public class BufferIn {
	
    private List<Operation> operations;       //�������������
    
    public Machine machine;					//��������
    
    public BufferIn() {
    	operations = new ArrayList<Operation>();
	}

    public BufferIn(Machine machine) {
        operations = new ArrayList<Operation>();
        this.machine = machine;
    }

    public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

    public void addOperation(Operation oper) {
        operations.add(oper);
    }

    public void clear() {
        operations.clear();
    }

    public Operation get(int index) {
        if (index < 0 || index >= operations.size()) { throw new ArrayIndexOutOfBoundsException(
                "Buffer size is " + operations.size() + " but index is "
                        + index); }
        return operations.get(index);
    }

    public boolean isEmpty() {
        return getArrivedOperations().size() <= 0;
    }

    public void removeOperation(Operation opera) {
            operations.remove(opera);
    }

    public int size() {
        return operations.size();
    }

   public List<Operation> getArrivedOperations() {
        List<Operation> ret = new ArrayList<Operation>();
        for (Operation o : operations) {
            if(o.isArrived()){
                ret.add(o);
            }
        }
        return ret;
    }
  

    public Iterator<Operation> iterator() {
        return operations.iterator();
    }

	public void operationClear() {
		// TODO Auto-generated method stub
		operations.clear();
	}


}
