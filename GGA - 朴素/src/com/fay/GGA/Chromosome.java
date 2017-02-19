package com.fay.GGA;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;

import com.fay.rule.IRule;
import com.fay.rule.BufferOutRule.IBufferOutRule;
import com.fay.rule.JobRule.IJobRule;
import com.fay.rule.MachineRule.*;
import com.fay.util.Constants;

import ec.rule.RuleSpecies;

public class Chromosome implements Comparable<Chromosome>, Cloneable {
/*************************** Attribute **********************************************************************/
	/** arrays that denotes tht group division of each entities */
	private int[] gene;
	/** index of gene*/
	private int index;
    /** size of Chromosome**/
    private int size;
    /** fitness of Chromosome*/
    private double fitness;
    /** probability of Chromosome that being selected*/
    private double prob;
	/** function values of Chromosome*/
	private double function;
	/**GGA max ruleno of rule[]**/
	private int MaxRuleNo ;
/*************************** Method **********************************************************************/

	public void Print_Gene(){
		String arrString = Arrays.toString(gene);
		
		System.out.print("gene = "+arrString+"   "+ "rule = ");
		for(int index = 0 ;index < rule.size();index++){
			System.out.print(rule.get(index)+", ");
		}
		System.out.println();
	}
	
    public Chromosome(int size) {
        super();
        this.size = size;
        this.fitness = 0.0;
        this.index = 0;
        gene = new int[size];
    }
    
    /** @Description get function values of Chromosomes */
    public double getFunction() {
		return function;
	}

	/** @Description set function values of Chromosomes */
	public void setFunction(double function) {
		this.function = function;
	}

    /** clone methods to copy a get function values of Chromosomes */
    public Chromosome clone() throws CloneNotSupportedException {
    	Chromosome cloned = (Chromosome) super.clone();

        cloned.setGene(Arrays.copyOf(cloned.getGene(), size));
        cloned.setFitness(this.getFitness());
        cloned.index = this.index;
        cloned.prob = this.prob;
        cloned.fitness = this.fitness;
        cloned.MaxRuleNo = this.MaxRuleNo;
        cloned.function = this.function;

        return cloned;
    }

    /** @Description Indicates whether the gene is "equal to" this one. */
    public boolean equals(Chromosome o){
    	 for (int i = 0; i < gene.length; i++) {
    		 if(gene[i]!=o.get(i)) return false;
    	 }
    	 return true;
    }

    /** @Description get gene of particular position on Chromosomes */
    public int get(int index) {
        if (index < 0 || index >=size) throw new IllegalArgumentException("out of gene arrays boundary");
        return gene[index];
    }

    /** @Description set gene values of particular position on Chromosomes */
    public void set(int index, int value) {
        if (index < 0 || index >= size) throw new IllegalArgumentException("out of gene arrays boundary");
        gene[index] = value;
    }           

    /** @Description get size of Chromosomes */
    public int size() {
        return size;
    }  

    /** @Description get Chromosomes */
    public int[] getGene() {
        return gene;
    }

    /** @Description set Chromosomes */
    public void setGene(int[] gene) {
        this.gene = gene;
    }
    
    public void localSearch(int[] gene){
    	Random rand = new Random(System.currentTimeMillis());
    	for(int i = 0;i < 3;i++){
    	int position = rand.nextInt(gene.length);
    	gene[position] = rand.nextInt(this.RuleSize());
    	}
    }

    /** @Description get fitness */
    public double getFitness() {
        return fitness;
    }

    /** @Description set fitness */
    public Chromosome setFitness(double fitness) {
        this.fitness = fitness;
        return this;
    }

    /** @Description get probablity of Chromosome that being selected */
    public double getProb() {
        return prob;
    }

    /** @Description set probablity of Chromosome that being selected */
    public void setProb(double prob) {
        this.prob = prob;
    }


	/** override the compareTo for Chromosome */
	public int compareTo(Chromosome o) {
	    if (o == this) return 0;
	    return new Double(function).compareTo(new Double(o.getFunction()));
	}

	/** @Description clear the Function inherited from parent */
	public void clearFunction() {
		this.fitness  = 0.0;
		this.function = 0.0;
	}
	
	/** override the toString for Chromosome */
	@Override
	public String toString() {	
	    StringBuffer sb = new StringBuffer(80);
	    for (int i = 0; i < gene.length; i++) {
	        sb.append(gene[i]);
	        sb.append(",");
	    }
	    return sb.toString();
	}

	public IRule getEntityRule(int style, int index)
	{
		switch (style) {
		case 1://mChromesone
			IMachineRule mRule;
			mRule = Constants.mRules[this.gene[--index]];
			return mRule;
		case 2://jChromesone
			IJobRule jobRule;
			jobRule = Constants.jRules[this.gene[--index]];
			return jobRule;
		default://bch
			IBufferOutRule bOutRule;
			bOutRule = Constants.bRules[this.gene[--index]];
			return bOutRule;
		}
	}
	public void clearIndex() {
		this.index = 0;
	}

}