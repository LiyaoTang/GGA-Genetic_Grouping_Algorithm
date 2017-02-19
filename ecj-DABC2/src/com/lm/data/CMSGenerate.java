package com.lm.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import com.lm.util.Constants;

public class CMSGenerate {
	public CMSGenerate(int fileIndex) throws IOException {
		String Filename = "data/Trans/data_GGA/"+(fileIndex+1);
		BufferedWriter writer = new BufferedWriter(new FileWriter(Filename)); //用BufferedWriter一定要在finally中flush()并close()
		try {
			int jobNum  = 0;
			int machNum = 0;
			int cellNum = 0;
			int []Layout = null;
			/**机器数  作业数 单元数 工件族数 小车容量--单元布局**/
			switch(fileIndex+1){
			case 1:{
				jobNum  =5;
				machNum =6;
				cellNum =3;
				break;
			}
			case 2:{
				jobNum  =15;
				machNum =8;
				cellNum =3;
				break;
			}
			case 3:{
				jobNum  =20;
				machNum =11;
				cellNum =3;
				break;
			}
			case 4:{
				jobNum  =40;
				machNum =13;
				cellNum =5;
				break;
			}
			case 5:{
				jobNum  =50;
				machNum =15;
				cellNum =5;
				break;
			}
			case 6:{
				jobNum  =60;
				machNum =16;
				cellNum =5;
				break;
			}
			case 7:{
				jobNum  =70;
				machNum =20;
				cellNum =7;
				break;
			}
			case 8:{
				jobNum  =80;
				machNum =21;
				cellNum =7;
				break;
			}
			case 9:{
				jobNum  =90;
				machNum =21;
				cellNum =7;
				break;
			}
			case 10:{
				jobNum  =100;
				machNum =25;
				cellNum =9;
				break;
			}
			case 11:{
				jobNum  =120;
				machNum =30;
				cellNum =9;
				break;
			}
			case 12:{
				jobNum  =140;
				machNum =35;
				cellNum =11;
				break;
			}
			case 13:{
				jobNum  =160;
				machNum =40;
				cellNum =11;
				break;
			}
			case 14:{
				jobNum  =180;
				machNum =45;
				cellNum =13;
				break;
			}
			case 15:{
				jobNum  =200;
				machNum =50;
				cellNum =15;
				break;
			}
			case 16:{
				jobNum  =250;
				machNum =65;
				cellNum =15;
				break;
			}
			case 17:{
				jobNum  =300;
				machNum =75;
				cellNum =15;
				break;
			}
			case 18:{
				jobNum  =350;
				machNum =90;
				cellNum =15;
				break;
			}
			case 19:{
				jobNum  =400;
				machNum =100;
				cellNum =15;
				break;
			}
			case 20:{
				jobNum  =450;
				machNum =120;
				cellNum =15;
				break;
			}
			}//end switch

			Layout = GenerateCellLayout(cellNum,machNum);
			int familyNum = cellNum;		//工件族的编号是从0开始的
			int transportorSize = 1000;
			Random r=new Random(1);			//1代表种子数
			
			//单元间的转移关系--可以做到根据生成的数据来修改得到转移信息
			int TransMatrix[][]= new int [cellNum][cellNum];
			for(int i = 0; i < cellNum; i++) {
				for(int j = 0; j < cellNum; j++){
					if(i==j){
						TransMatrix[i][j] = 0;
					}else{
						TransMatrix[i][j] = 1;
					}
				}
			}

/***************************具体写文件内容***********************************************************************/
			writer.write(machNum+" "+jobNum+" "+
					cellNum+" "+familyNum+" "+
					 transportorSize+"\n"
					);
			writer.newLine();// 写入空格行  
			
			for (int i = 1; i < Layout.length; i++) {
				writer.write(Layout[i]+" ");
			}
			writer.newLine();// 写入行尾结束符
			writer.newLine();// 写入空格行  
			
			//单元间转移时间矩阵
			int TranTime[][]=new int[cellNum+1][cellNum+1];
			for (int i = 1; i <= cellNum; i++) {
				for (int j = 1; j <= cellNum; j++) {
					if(i==j){
						TranTime[i][j]=0;
						writer.write(0+" ");
					}
					else{
						TranTime[i][j]=r.nextInt(46)+6;		//TransTime U[6,50]						
						writer.write(TranTime[i][j]+" ");
					}
				}
				writer.newLine();// 写入行尾结束符
			}
			writer.newLine();// 写入空格行  


			for (int i = 1; i <= cellNum; i++) {
				for (int j = 1; j <= cellNum; j++) {
					//System.out.println(TransMatrix[i-1][j-1]);
					writer.write(TransMatrix[i-1][j-1]+" ");
				}
				writer.newLine();// 写入行尾结束符
			}
			writer.newLine();// 写入空格行  
			
			// setupTime——矩阵
			int SetupTime[][]=new int[familyNum][machNum+1];
			for (int i = 0; i < familyNum; i++) {
				for (int j = 1; j <= machNum; j++) {
					int CurTime=r.nextInt(5)+5;//setupTime U[5,10]
					SetupTime[i][j]=CurTime;
					writer.write(CurTime+" ");
				}
				writer.newLine();// 写入行尾结束符
			}
			writer.newLine();// 写入空格行  

			// 工件属性
			for (int jobNo = 0; jobNo < jobNum; jobNo++) {
				/** 工序数 	工件族	 duedate 	weight
				 *  其中duedate是通过df*avg_tt计算得出来的
				 *  df因子
				 * **/
				int operNum =    r.nextInt(14)+5; // 工序数目 U[5,19]
				int family  =    r.nextInt(familyNum); // 工件族号
				double weight = (r.nextInt(9)+1)/10.0;// 工件权重 U[0,1]
				
				int OperTime[][]=new int[operNum+1][machNum+1];//默认初始化为0
				int OperMachine[][]=new int[operNum+1][];//每道工件能够加工的机器数
				float aveg_tt=0;//统计duedate
				/**
				 * 初始化加工时间
				 * 根据OperMachine序列和加工时间 来计算DUTDATE
				 */
				for (int j = 1; j <= operNum; j++) {
					
					/**
					 * 当前工序可以加工的机器数目 U[1,(单元数目,5)的最小值]
					 * 这个地方应该根据小车模型有所改变，我们要保证这些机器都是在不同单元内的
					 */
					int AbleMachineNum = Math.abs(r.nextInt())%(Math.min(cellNum,5)) + 1;
					OperMachine[j]=new int[AbleMachineNum];
					int[] AbleCell=new int[AbleMachineNum];
					int sum=0;
					
					//先random单元号
					Set<Integer> set = new TreeSet<Integer>();
					while(set.size()<AbleMachineNum){//产生不重复的随机数
						int c=r.nextInt(cellNum)+1;
						set.add(c);//n 是随机出的单元数
					}
					int num = 0;
					for(Integer digit: set){
						AbleCell[num++] = digit;
					}
					
					for (int t = 0; t < AbleMachineNum; t++) {
						//再在指定单元里面，random机器号
						int sumOfMachines=0;
						int AbleMachine=0;
						while(Layout[++AbleMachine]!=AbleCell[t]);
						if(AbleCell[t]!=cellNum){
							while(Layout[AbleMachine+(++sumOfMachines)]==AbleCell[t]);
						}
						else{
							sumOfMachines=machNum-AbleMachine+1;
						}
						AbleMachine+=r.nextInt(sumOfMachines);
						 
						 int OperatTime=r.nextInt(30)+1;		//加工时间 U[1,30]
						 OperMachine[j][t]=AbleMachine;
						 OperTime[j][AbleMachine]=OperatTime;
						 sum+=OperatTime+SetupTime[family][AbleMachine];
						 
					}//end 当前工序抉择
				    aveg_tt+=(sum*1.0)/AbleMachineNum;

				    /** due date不考虑 waiting time的耗费了
				    if(j!=1){
				    	int TransSum=0;
				    	int Num=0;
				    	//比较前一道工序可以加工的机器和当前工序可以加工的机器
				    	for (int i = 0; i < OperMachine[j-1].length; i++) {
				    		for (int k = 0; k < OperMachine[j].length; k++) {
								if(Layout[OperMachine[j-1][i]] != Layout[OperMachine[j][k]]){
									TransSum+=TranTime[ Layout[OperMachine[j-1][i]] ][ Layout[OperMachine[j][k]] ];
									Num++;
								}
							}
				    	}
				    if(Num!=0) TransSum/=Num;
				    aveg_tt+=TransSum;
				    }//end if(j!=1)
				    **/
				}//end for当前工件的所有工序
				
				double Arrival_Time = r.nextInt(50); //arriveTime [0,50);
				int TightDueDate=(int)(Constants.DUE_FACTOR_TIGHT*aveg_tt+Arrival_Time);	
				int LooseDueDate=(int)(Constants.DUE_FACTOR_LOOSE*aveg_tt+Arrival_Time);
				
/***************************开始写工序内容***********************************************************************/
				writer.write(operNum+"    "+family+"    "+TightDueDate+"    "+weight+"    "+LooseDueDate+"\n");
				for(int i=1;i<=operNum;i++){
					for(int j=1;j<=machNum;j++){
//						System.out.println(OperTime[i][j]);
						writer.write(OperTime[i][j]+" ");
					}
					writer.newLine();// 写入行尾结束符
				}
			}//end for 工件属性
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
		//Close the BufferedWriter
		try {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}catch (IOException ex) {
			ex.printStackTrace();
		}//end try
		}//end finally
	}//end Construction

	/**
	 * generate cell layout for machines
	 * @param cellNum
	 * @param machNum
	 * @return
	 */
	private int[] GenerateCellLayout(int cellNum, int machNum) {
		int []result  = new int[machNum+1];
		int avg_count = machNum/cellNum;
		//除了最后一个cell外，每一个cell中machine数量在avg_count范围内来random确定每个单元的布局
		int cur_flag = 1;
		for(int i = 1;i < cellNum; i++) {
			for(int j = 0;j < avg_count; j++){
				result[cur_flag++] = i;
			}
		}
		//最后一个cell包含所有剩余的mach数量
		do{
			result[cur_flag++] = cellNum;
		}while(cur_flag!=machNum+1);
		
		return result;
	}

	/**
	 * @Description copy the array cellLayout to layout
	 * @param layout
	 */
	private int[] CopyArrary(int[] layout) {
			int []result = new int[layout.length];
			for( int i =0; i < layout.length; i++){
				result[i] =layout[i];
			}
			return result;
	}
	
	
	public static void main(String[] args) throws IOException {
		System.out.println("begin//--------------------------------------");
		for(int i = 0; i< 20; i++) {
			CMSGenerate resource = new CMSGenerate(i);
			System.out.println("success for"+i);
		}
	}
}
