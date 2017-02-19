package com.fay.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CellSet implements Iterable<Cell> {

	private List<Cell> cells;

	
	public CellSet() {
		cells = new ArrayList<Cell>();
	}

	public int size() {
		return cells.size();
	}

	public void addCell(Cell c) {
		cells.add(c);
	}

	public Cell get(int index) {
		return cells.get(index);
	}

	public Iterator<Cell> iterator() {
		return cells.iterator();
	}
	
	public void reset() {
		for (Cell c : cells) {
//			c.reset();
		}
	}

}