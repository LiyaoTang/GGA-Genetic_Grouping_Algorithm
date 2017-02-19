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
/***************************属�?�?**********************************************************************/
	/** arrays that denotes the Chromosome **/
	private int[] gene;
	/*index of gene*/
	private int index;
    /** size of Chromosome**/
    private int size;
    /** fitness of Chromosome*/
    private double fitness;
    /** probability of Chromosome that being selected*/
    private double prob;
	/** function values of Chromosome*/
	private double function;
	/** GGA  arrays of rule index **/
	public ArrayList rule;
	/** GGA  arrays of rule[] lenth **/
	//private int rulesize ;
	/**GGA max ruleno of rule[]**/
	private int MaxRuleNo ;
/***************************方法�?**********************************************************************/
	private Object i;
	
	
	public void Print_Gene(){
		String arrString = Arrays.toString(gene);
		
		System.out.print("gene = "+arrString+"   "+ "rule = ");
		for(int index = 0 ;index < rule.size();index++){
			System.out.print(rule.get(index)+", ");
			//System.out.print(index+", ");
		}
		System.out.println();
	}
	
	
//	 public int[] getRule() {
//	        return rule;
//	    }
//	
	/**
     * @Description clear gene
     * @return
     */
	public void ClearGene()
	{
		
			;
	}
	/**
     * @Description clear rule
     * @return
     */
	public void ClearRule()
	{
		
			rule.clear();
	}
	/**
     * @Description add rule
     * @return
     */
	
	public void AddRule(int value)
	{
		
			rule.add(value);
	}
	
	/**
     * @Description set rulesize
     * @return
     */
	
	public void SetRulesize(int lenth )
	{
		for(int i = 0 ;i < lenth;i++)
			rule.add(i);
	}
	
	/**
     * @Description delete  index of rule
     * @return
     */
	
	public void DeleteRule(int index )
	{
		rule.remove(index);
	}
	
	
	
	/**
     * @Description find  postion of ruleno
     * @return
     */
	
//	public int GetRulePos(int ruleno)
//	{
//		int i = 0 ; 
//		for( i = 0 ; i < RuleSize(); i++)
//		{
//			if (this.GetRuleNo(i) == ruleno){
//				break;
//			}
//		}
//		
//		return i;
//	}
	
	
	/**
     * @Description get index values of rule
     * @return
     */
	public int GetRuleNo(int index)
	{
		return (int) rule.get(index);
	}
	/**
     * @Description set index values of rule
     * @return
     */
	public void SetRuleNo(int index , int RuleNo )
	{
		rule.set(index, RuleNo);		
	}
	/**
     * @Description constructed function
     * @param size
     * @exception:
     */
	
	 /**
     * @Description get size of rule[]
     * @return
     */
    public int RuleSize() {
       // return rulesize;
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
    
    /**
     * @Description get function values of Chromosomes
     * @return
     */
    public double getFunction() {
		return function;
	}

	/**
	 * @Description set function values of Chromosomes
	 * @param function
	 */
	public void setFunction(double function) {
		this.function = function;
	}

    /* (non-Javadoc):clone methods to copy a get function values of Chromosomes
     * @see java.lang.Object#clone()
     * override
     */
    public Chromosome clone() throws CloneNotSupportedException {
    	Chromosome cloned = (Chromosome) super.clone();

        cloned.setGene(Arrays.copyOf(cloned.getGene(), size));
        cloned.rule = new ArrayList();
        for(int i =0 ;i < this.RuleSize(); i++)
        	cloned.rule.add(this.GetRuleNo(i) );
        cloned.setFitness(this.getFitness());
        cloned.index = this.index;
        cloned.prob = this.prob;
        cloned.fitness = this.fitness;
        cloned.MaxRuleNo = this.MaxRuleNo;
        cloned.function = this.function;
        //cloned.Print_Gene();
        return cloned;
    }

    /**
     * @Description Indicates whether the gene is "equal to" this one.
     * @param o
     * @return
     */
//    @Override
    public boolean equals(Chromosome o){
    	 for (int i = 0; i < gene.length; i++) {
    		 if(gene[i]!=o.get(i)) return false;
    	 }
    	 return true;
    }
    /**
     * @Description get gene of particular position on Chromosomes
     * @param index
     * @return
     */
    public int get(int index) {
        if (index < 0 || index >=size) throw new IllegalArgumentException("out of gene arrays boundary");
       //System.out.println(index);
        return gene[index];
    }

    /**
     * @Description set gene values of particular position on Chromosomes
     * @param index
     * @param value
     */
    public void set(int index, int value) {
        if (index < 0 || index >= size) throw new IllegalArgumentException("out of gene arrays boundary");
        gene[index] = value;
    }           

    /**
     * @Description get size of Chromosomes
     * @return
     */
    public int size() {
        return size;
    }  

    /**
     * @Description get Chromosomes
     * @return
     */
    public int[] getGene() {
        return gene;
    }

    /**
     * @Description set Chromosomes
     * @param gene
     */
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

    /**
     * @Description get fitness
     * @return
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * @Description set fitness
     * @param fitness
     * @return
     */
    public Chromosome setFitness(double fitness) {
        this.fitness = fitness;
        return this;
    }

    /**
     * @Description get probablity of Chromosome that being selected
     * @return
     */
    public double getProb() {
        return prob;
    }

    /**
     * @Description set probablity of Chromosome that being selected
     * @param prob
     */
    public void setProb(double prob) {
        this.prob = prob;
    }

	
	/* override the compareTo for Chromosome
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Chromosome o) {
	    if (o == this) return 0;
	    return new Double(function).compareTo(new Double(o.getFunction()));
	}

	/**
	 * @Description clear the Function inherited from parent
	 */
	public void clearFunction() {
		this.fitness  = 0.0;
		this.function = 0.0;
	}
	
	/*  override the toString for Chromosome
	 * @see java.lang.Object#toString()
	 */
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
		case 2:
			IJobRule jobRule;
			//System.out.println(index);
			jobRule = Constants.jRules[this.GetRuleNo(this.get(--index))];
			return jobRule;
			
		default://bch
			IBufferOutRule bOutRule;
			bOutRule = Constants.bRules[this.GetRuleNo(this.get(--index))];
			//bOutRule = Constants.bRules[this.GetRuleNo(this.get(((this.index)++)% rule.size()))];
			
			return bOutRule;
		}		
	}
	public void clearIndex() {
		this.index = 0;
	}

}
