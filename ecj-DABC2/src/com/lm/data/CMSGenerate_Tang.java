package com.lm.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import com.lm.util.Constants;

public class CMSGenerate_Tang {
	public CMSGenerate_Tang(int fileIndex) throws IOException {
		
		String Filename = "data/Trans/Tang/" + (fileIndex+1);
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
				machNum =4;
				cellNum =2;
				int []Cell_Layout = {0, 1, 2, 2, 1};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 2:{
				jobNum  =8;
				machNum =5;
				cellNum =2;
				int Cell_Layout[]={0,1, 1, 2, 2, 2};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 3:{
				jobNum  =10;
				machNum =6;
				cellNum =3;
				int Cell_Layout[]={0,2, 1, 3, 2, 1, 3};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 4:{
				jobNum  =12;
				machNum =7;
				cellNum =3;
				int Cell_Layout[]={0,2, 1, 1, 1, 3, 2};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 5:{
				jobNum  =16;
				machNum =8;
				cellNum =3;
				int Cell_Layout[]={0,3, 3, 1, 1, 1, 2, 3, 2};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 6:{
				jobNum  =20;
				machNum =10;
				cellNum =4;
				int Cell_Layout[]={0, 3, 2, 1, 3, 1, 4, 2, 1, 4, 3};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 7:{
				jobNum  =22;
				machNum =14;
				cellNum =4;
				int Cell_Layout[]={0,2, 1, 4, 3, 4, 4, 1, 3, 3, 2, 4, 1};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 8:{
				jobNum  =26;
				machNum =14;
				cellNum =5;
				int Cell_Layout[]={0,3, 5, 4, 3, 1, 2, 2, 5, 4, 4, 3, 1, 1, 4};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 9:{
				jobNum  =30;
				machNum =16;
				cellNum =5;
				int Cell_Layout[]={0,1, 1, 5, 5, 5, 3, 4, 4, 3, 2, 3, 5, 2, 2, 2, 3};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 10:{
				jobNum  =35;
				machNum =18;
				cellNum =5;
				int Cell_Layout[]={0,1, 4, 5, 2, 4, 3, 3, 2, 1, 1, 4, 4, 5, 2, 2, 3, 3, 4};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 11:{
				jobNum  =40;
				machNum =20;
				cellNum =6;
				int Cell_Layout[]={0,2, 1, 5, 3, 1, 4, 4, 6, 1, 3, 6, 2, 2, 6, 1, 3, 4, 6, 6, 5};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 12:{
				jobNum  =45;
				machNum =22;
				cellNum =6;
				int Cell_Layout[]={0,4, 4, 3, 5, 2, 2, 6, 5, 1, 3, 5, 1, 2, 4, 3, 1, 2, 6, 2, 4, 1, 6};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 13:{
				jobNum  =50;
				machNum =24;
				cellNum =6;
				int Cell_Layout[]={0,2, 3, 4, 2, 1, 1, 2, 3, 2, 4, 3, 3, 1, 3, 5, 1, 6, 6, 4, 5, 5, 4, 4, 3};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			}//end switch
			
			int Cell_Layout_Nums[] = ReadLayOutInfo(Layout,cellNum);
			int familyNum = cellNum;//工件族的编号是从0开始的
			int transportorSize = cellNum;
			Random r=new Random(1);//1代表种子数
			
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
						//TransTime U[2,20]  [2,3]
						TranTime[i][j]=r.nextInt(1)+2;
						//统一*10,最后一并处理
						TranTime[i][j]*=10;
						writer.write(TranTime[i][j]+" ");
					}
				}
				writer.newLine();// 写入行尾结束符
			}
			writer.newLine();// 写入空格行  

			//单元间转移关系矩阵
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
					int CurTime=0;//setupTime 均为0
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
				 *  df是因子
				 * **/
				int operNum ;
				if(machNum >5){
					operNum =    r.nextInt(machNum-3)+ 3; 	// 工序数目 U[3,machNum] 
				}else{
					operNum = 	 r.nextInt(machNum-2)+ 2;	// 工序数目 U[2,machNum]
				}
				int family  =    r.nextInt(familyNum); 		// 工件族号
				// 工件的DUEDATE U[1,25]  [10,25]
				int DueDate =    r.nextInt(15)+10;
				////统一*10,最后一并处理
				DueDate*=10;
				double weight =  r.nextInt(9)+1;			// 工件权重 U[1,10]

				int Time_For_Ops[]   =new int[operNum+1];	//每道工序的加工时间
				int Machine_For_Ops[]=new int[operNum+1];	//每道工序的加工机器
				
				// 初始化工序的加工信息表
				int OpsNum_In_Cell[] = GetOpsForCell(Cell_Layout_Nums,cellNum,operNum);
				
				//初始化跨单元的顺序
				int[] Ops_Seq_For_Cell = GetSeqForCell(cellNum);
				//初始化各单元内的机器加工表
				int ops_point = 1;
				int sum_time  = 0;
				for(int index = 0; index < Ops_Seq_For_Cell.length; index++){
					int cur_cell    = Ops_Seq_For_Cell[index];
					int cur_opsNum	= OpsNum_In_Cell[cur_cell];
					if(cur_opsNum!=0) {
						int[] machinesForEachCell = GetOperateMahinesInCell(Cell_Layout_Nums,cur_cell,cur_opsNum);
						
						for(int temp = 0; temp < cur_opsNum; temp++){
							//加工时间 U[0.1,0.9] *10倍读取，处理的时候/10倍来处理 
							Time_For_Ops[ops_point]    = r.nextInt(8)+1;	
							Machine_For_Ops[ops_point] = machinesForEachCell[temp];
							sum_time+= Time_For_Ops[ops_point];//先不加SetupTime[family][machinesForEachCell.get(temp)];
							ops_point++;
						}
					}
				}

		/***************************开始写工序内容***********************************************************************/
				writer.write(operNum+"    "+family+"    "+DueDate+"    "+weight+"\n");
				for(int i=1;i<=operNum;i++){
					for(int j=1;j<=machNum;j++){
						if(j!=Machine_For_Ops[i]) {
							writer.write(0+" ");
						}else{
							writer.write(Time_For_Ops[i]+" ");
						}
					}
					writer.newLine();// 写入行尾结束符
				}
//			System.out.println("end for job"+jobNo);
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

	/**
	 * @Description getting machine nums in each cell according to the layout
	 * @param cellLayout
	 * @param cellNum 
	 * @return
	 */
	private int[] ReadLayOutInfo(int[] cellLayout, int cellNum) {
		int []result =new int[cellNum+1];

		for(int i = 1; i <= cellNum; i++){
			int sum=0;
			for (int j = 0; j < cellLayout.length; j++) {
				if(cellLayout[j] == i) {
					sum++;
				}
			}
			result[i]=sum;
		}
		return result;
	}

	/**
	 * @Description get the machine sequences to operate in each cell
	 * @param cellLayoutNums
	 * @param curCell
	 * @param curOpsNum
	 * @return
	 */
	private int[] GetOperateMahinesInCell(int[] cellLayoutNums,
			int curCell, int curOpsNum) {
		//找出机器编号的范围
		int begin = 0;
		for(int temp=1;temp< curCell;temp++){
			begin += cellLayoutNums[temp];
		}
		//从中挑选出curOpsNum个随机的机器数量
		int[] result = new int[curOpsNum];
		Random r=new Random(1);//1代表种子数
		int index = 0;
        while (true) {
            if(index==curOpsNum){
                break;
            }
            int it = (int) (r.nextInt(cellLayoutNums[curCell])+begin+1);
            //如果list存在这个数组就继续循环
            if (ArrayContain(result,it,index)) {
                continue;
            } else {
                //如果list不存在这个数字，那么就把这个数字给数组，并且在list中加入这个数字
            	result[index++]=it;
            }
        }
        Arrays.sort(result);
		return result;
	}

	/**
	 * @param result 
	 * @Description to judge if there is already a it in the array
	 * @param it
	 * @param index
	 * @return
	 */
	private boolean ArrayContain(int[] result, int it, int index) {
		for(int cur = 0; cur <= index; cur++){
			if(result[cur]== it) return true;
		}
		return false;
	}

	/**
	 * @Description get random nums of ops for each cell
	 * @param cellLayoutNums
	 * @param cellNum
	 * @param operNum
	 * @return
	 */
	private int[] GetOpsForCell(int[] cellLayoutNums, int cellNum,int operNum) {
        int[] ary = new int[cellNum+1];
		while(true){
			int sum = 0;
			for(int CellIndex = cellNum; CellIndex > 1 ; CellIndex--){
				ary[CellIndex] =  (int)(Math.random()*(cellLayoutNums[CellIndex]+1));
				sum+=ary[CellIndex];
			}
			
			//看最后一道工序是否可以满足要求
			int remainOps = operNum-sum;
			if(remainOps >0 && remainOps <= cellLayoutNums[1]) {
				ary[1] = remainOps;
				return ary;
			}else{
				continue;
			}
		}	
	}

	/**
	 * @Description get the random operate sequences of cell for the job
	 * @param cellNum
	 * @return
	 */
	private int[] GetSeqForCell(int cellNum) {
        // 数组
        int[] ary = new int[cellNum];
        // 集合 ,临时集合temp存放1~10个数字
        ArrayList<Integer> temp = new ArrayList<Integer>();
        //list集合存放需要的数字
        ArrayList<Integer> list = new ArrayList<Integer>();
        // 给集合添加1~10的数字
        for (int i = 1; i < 11; i++) {
            temp.add(i);
        }
        // while循环 随即抽取集合的数字给数组
        int index = 0;
        while (true) {
            if(list.size()==cellNum){
                break;
            }
            int it = temp.get((int) (Math.random() * cellNum));
            //如果list存在这个数组就继续循环
            if (list.contains(it)) {
                continue;
            } else {
                //如果list不存在这个数字，那么就把这个数字给数组，并且在list中加入这个数字
                ary[index] = it;
                list.add(it);
                index++;
            }
        }
		return ary;
	}

	public static void main(String[] args) throws IOException {
		System.out.println("begin//--------------------------------------");
		for(int i = 0; i< 13; i++) {
			CMSGenerate_Tang dataSource = new CMSGenerate_Tang(i);
			System.out.println("success for"+i);
		}
	}
}
