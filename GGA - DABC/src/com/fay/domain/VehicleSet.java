package com.fay.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VehicleSet implements Iterable<Vehicle> {
	public List<Vehicle> vehicles;

	public VehicleSet(){
		vehicles = new ArrayList<Vehicle>();
	}
	
	public int size(){
		return vehicles.size();
	}
	
	public void addVehicle(Vehicle vehicle){
		vehicles.add(vehicle);
	}
	
	/**���ID��ȡС��*/
	public Vehicle get(int index){
		return vehicles.get(index-1);
	}
	
	public Iterator<Vehicle> iterator() {
		return vehicles.iterator();
	}

	
}
