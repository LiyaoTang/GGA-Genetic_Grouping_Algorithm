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

/**
 * @Description:Chromosome的gene�?��示的�?

 * @author:lm

 * @time:2013-11-6 下午03:53:33

 */
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
	/** GGA arrays of rule (denoted by index of rules) for corresponding divisions in gene **/
	public ArrayList rule; ;
	/**GGA max ruleno of rule[]**/
	private int MaxRuleNo ;
/*************************** Method **********************************************************************/

	public void Print_Gene(){
		String arrString = Arrays.toString(gene);
		
		System.out.print("gene = "+arrString+"   "+ "rule = ");
		for(int index = 0 ;index < rule.size();index++){
			System.out.print(rule.get(index)+", ");
			//System.out.print(index+", ");
		}
		System.out.println();
	}

	/** @Description clear gene */
	public void ClearGene() {
	}

	/** @Description clear rule */
	public void ClearRule() {
			rule.clear();
	}

	/** @Description add rule */
	public void AddRule(int value) {
			rule.add(value);
	}
	
	/** @Description set rulesize */
	public void SetRulesize(int lenth ) {
		for(int i = 0 ;i < lenth;i++) rule.add(i);
	}
	
	/** @Description delete  index of rule */
	public void DeleteRule(int index )
	{
		rule.remove(index);
	}

	/** @Description get index values of rule */
	public int GetRuleNo(int index) {
		return (int) rule.get(index);
	}

	/** @Description set index values of rule */
	public void SetRuleNo(int index , int RuleNo)
	{
		rule.set(index, RuleNo);		
	}

	 /** @Description get size of rule[] */
    public int RuleSize() {
        return rule.size();
    }  
	
    public Chromosome(int size) {
        super();
        this.size = size;
        this.fitness = 0.0;
        this.index = 0;
        gene = new int[size];
        rule = new ArrayList();
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
        cloned.rule = new ArrayList();
        for(int i =0 ;i < this.RuleSize(); i++) cloned.rule.add(this.GetRuleNo(i) );
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
    	if (rule.size() != o.rule.size()) return  false;

    	 for (int i = 0; i < gene.length; i++) {
    		 if(gene[i]!=o.get(i)) return false;
    	 }
    	 for (int i = 0; i < rule.size(); i++) {
    	 	if (rule.get(i) != o.rule.get(i)) return false;
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
    
    public ArrayList getRule(){
    	return rule;
    }
    
    public void setRule(ArrayList rule){
    	this.rule = rule;
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
			mRule = Constants.mRules[this.GetRuleNo(this.get(--index))];
			return mRule;
		case 2://jChromesone
			IJobRule jobRule;
			jobRule = Constants.jRules[this.GetRuleNo(this.get(--index))];
			return jobRule;
			
		default://bch
			IBufferOutRule bOutRule;
			bOutRule = Constants.bRules[this.GetRuleNo(this.get(--index))];
			return bOutRule;
		}		
	}
	public void clearIndex() {
		this.index = 0;
	}

}