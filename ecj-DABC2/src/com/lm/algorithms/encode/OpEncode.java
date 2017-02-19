package com.lm.algorithms.encode;

import java.util.ArrayList;

import javax.xml.transform.Source;

import com.lm.algorithms.abc.Chromosome;


/**
 * @Description operations'permutation-based encoding method

 * @author: lm

 * @time:2014-12-20

 */
public class OpEncode implements Comparable<OpEncode>, Cloneable {
/***************************属性域***********************************************************************/
    /** numbers of parts**/
    private int PartSize;
    /** the length of encode**/
    private int EncodeLength;
    /** the encode**/
    private int[] Encode;
    /** fitness of encode*/
    private double fitness;
    /** function value of encode*/
    private double function;
	/** probability of Chromosome that being selected*/
    private double prob;
/***************************方法域***********************************************************************/
    /**
     * construct method -- by length
     */
    public OpEncode(int EncodeLength) {
    	this.EncodeLength = EncodeLength;
    	this.Encode     = new int[EncodeLength];
    	this.function     = -1*Double.MAX_VALUE;
	}
    
    /**
     * construct method
     */
    public OpEncode(int[] len) {
    	this.EncodeLength = len.length;
    	this.Encode       = len.clone();
    	this.function     = -1*Double.MAX_VALUE;
	}
    
	public double getFunction() {
		return function;
	}
	
	public int[] GetEncode() {
		return Encode;
	}
	
	public void setencode(int[] source) {
		Encode = source;
	}

    public OpEncode clone() throws CloneNotSupportedException {
        OpEncode cloned = new OpEncode(EncodeLength);
        cloned.setencode(Encode);
        
        return cloned;
    }
	
 	@Override
	public int compareTo(OpEncode o) {
		if (o == this) return 0;
	    return new Double(function).compareTo(new Double(o.getFunction()));

	}

	/**
	 * @param d
	 */
	public void setProb(double d) {
		this.prob = d;
	}

	public double getProb() {
		return this.prob;
	}

	public int getsize() {
		return EncodeLength;
	}

	public int getPos(int index) {
		return Encode[index];
	}

	public void setPos(int finalPlace, int jobIndex) {
		Encode[finalPlace] = jobIndex;
	}

	public void setFunc(double evaluate) {
		this.function = evaluate;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double d) {
		this.fitness = d;
		
	}
	
	@Override
	public String toString() {	
	    StringBuffer sb = new StringBuffer(80);
	    for (int i = 0; i < Encode.length; i++) {
	        sb.append(Encode[i]);
	        sb.append(",");
	    }
	    return sb.toString();
	}

}
