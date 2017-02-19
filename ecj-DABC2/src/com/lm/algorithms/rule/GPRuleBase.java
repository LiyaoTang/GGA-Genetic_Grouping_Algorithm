package com.lm.algorithms.rule;

/**
 * @Description function that will be used to explain the rules generated by GP

 * @author:lm

 * @time:2013-11-6 下午05:41:21

 */
public abstract class GPRuleBase {
	public static final double Add(final double v1, final double v2) {
		return v1 + v2;
	}

	public static final double Mul(final double v1, final double v2) {
		return v1 * v2;
	}

	public static final double Div(final double v1, final double v2) {
		if (v2 <0.0000001 && v2>-0.00000001) {
			return 1;
		}
		return v1 / v2;
	}

	public static final double Sub(final double v1, final double v2) {
		return v1 - v2;
	}
	
	public static final double Max(final double v1, final double v2) {
		if(v1>v2){
			return v1;
		}else{
			return v2;
		}
	}
}
