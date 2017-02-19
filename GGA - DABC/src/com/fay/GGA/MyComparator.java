package com.fay.GGA;

import java.util.Comparator;

public class MyComparator implements Comparator<Ant>{  
		  
	    public int compare(Ant o1,Ant o2) {   

	    	double i = o1.Score - o2.Score;
	    	return (int)i;
	       }  

}
