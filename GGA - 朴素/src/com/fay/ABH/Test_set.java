package com.fay.ABH;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class Test_set {

	/**
	 * @param args 参数实验 求平均 2015.3.26
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hi");
		String str = "", temp = null;
		double answer[] = new double [330];
		double end[] = new double [20];
		int i=0;
		try {
		File file = new File("data_ABH/ans-tight.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);//文件读IO
		while((temp = br.readLine())!=null){//读到结束为止
			str += (temp+"\n");
			 answer[i]=Double.parseDouble(temp);
			 i++;
		}
		
		}
		 catch (IOException e) {
				e.printStackTrace();
			}
		int k=0;
		for(int j=0;j<320;j++)
		{
		//System.out.println(answer[j]);
			end[k]+=answer[j];
			if((j>0) &&j%19==0){
				//avg
				end[k]=end[k]/20;
			//	System.out.println("k=="+k+":"+end[k]);
				System.out.println(end[k]);
				k++;
			}
		}
		
	}

}
