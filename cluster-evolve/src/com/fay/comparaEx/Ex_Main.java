package com.fay.comparaEx;

public class Ex_Main {
	public static void main(String[] args) {
		double TWT_best = Double.MAX_VALUE;
		double TWT_total = 0;
		chromosome ch_best = new chromosome();
		
/*		for(int j = 0 ; j < 12 ; j ++){
			chromosome.setProblemCase(j+1);
			TWT_best = Double.MAX_VALUE;
			for(int k = 0; k < 5 ; k++){
				for(int i = 0 ; i < 200 ; i++){
					chromosome ch = new chromosome();
					ch.CalFitness();
					if(ch.GetFitness() < TWT_best){
						TWT_best = ch.GetFitness();
					}
				}
				TWT_total += TWT_best;
			}
			TWT_total = TWT_total/5;
			System.out.println(TWT_total);
			TWT_total = 0;
		}
		*/
		chromosome.setProblemCase(12);
		TWT_best = Double.MAX_VALUE;
		for(int i = 0 ; i < 200 ; i++){
			chromosome ch = new chromosome();
			ch.CalFitness();
			if(ch.GetFitness() < TWT_best){
				TWT_best = ch.GetFitness();
			}
			if(i%10 == 0){
				System.out.println(TWT_best);
			}
		}

	}

}
