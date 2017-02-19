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

public class CMSGenerate_IABCForGP {
	public CMSGenerate_IABCForGP(int fileIndex) throws IOException {
		
		String Filename = Constants.GPCMS_SOURCE + (fileIndex+1);
		BufferedWriter writer = new BufferedWriter(new FileWriter(Filename)); //用BufferedWriter一定要在finally中flush()并close()
		try {
			int jobNum  = 0;
			int machNum = 0;
			int cellNum = 0;
			int []Layout = null;
			/**机器数  作业数 单元数 工件族数 小车容量--单元布局**/
			switch(fileIndex+1){
			case 1:{
				jobNum  =10;
				machNum =3;
				cellNum =2;
				int []Cell_Layout = {0, 1, 1, 2};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 2:{
				jobNum  =20;
				machNum =3;
				cellNum =2;
				int Cell_Layout[]={0,1, 1, 2};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 3:{
				jobNum  =30;
				machNum =3;
				cellNum =2;
				int Cell_Layout[]={0,1, 1, 2};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 4:{
				jobNum  =40;
				machNum =4;
				cellNum =3;
				int Cell_Layout[]={0,1, 1, 2, 3};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 5:{
				jobNum  =50;
				machNum =4;
				cellNum =3;
				int Cell_Layout[]={0,1, 1, 2, 3};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 6:{
				jobNum  =60;
				machNum =6;
				cellNum =3;
				int Cell_Layout[]={0,1, 1, 2, 2, 3, 3};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 7:{
				jobNum  =70;
				machNum =6;
				cellNum =4;
				int Cell_Layout[]={0,1, 1, 2, 3, 3, 4};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 8:{
				jobNum  =80;
				machNum =8;
				cellNum =4;
				int Cell_Layout[]={0,1, 1, 2, 2, 3, 3, 4, 4};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 9:{
				jobNum  =90;
				machNum =8;
				cellNum =4;
				int Cell_Layout[]={0,1, 1, 2, 2, 3, 3, 4, 4};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 10:{
				jobNum  =100;
				machNum =10;
				cellNum =5;
				int Cell_Layout[]={0,1, 1, 2, 2, 3, 3, 4, 4, 5, 5};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 11:{
				jobNum  =110;
				machNum =11;
				cellNum =5;
				int Cell_Layout[]={0,1, 1, 2, 2, 3, 3, 3, 4, 4, 5, 5};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 12:{
				jobNum  =120;
				machNum =12;
				cellNum =5;
				int Cell_Layout[]={0,1, 1, 2, 2, 3, 3, 3, 4, 4, 5, 5, 5};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 13:{
				jobNum  =130;
				machNum =13;
				cellNum =6;
				int Cell_Layout[]={0,1, 1, 2, 2, 3, 3, 3, 4, 4, 5, 5, 6, 6};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 14:{
				jobNum  =140;
				machNum =14;
				cellNum =6;
				int Cell_Layout[]={0,1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 15:{
				jobNum  =150;
				machNum =15;
				cellNum =6;
				int Cell_Layout[]={0,1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 6};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 16:{
				jobNum  =160;
				machNum =16;
				cellNum =6;
				int Cell_Layout[]={0,1, 1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 6};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 17:{
				jobNum  =170;
				machNum =17;
				cellNum =7;
				int Cell_Layout[]={0,1, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 6, 7, 7, 7};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 18:{
				jobNum  =180;
				machNum =18;
				cellNum =7;
				int Cell_Layout[]={0,1, 1, 1, 2, 2, 3, 3, 4, 4, 4, 5, 5, 6, 6, 6, 7, 7, 7};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 19:{
				jobNum  =190;
				machNum =19;
				cellNum =8;
				int Cell_Layout[]={0,1, 1, 1, 2, 2, 3, 3, 3, 4, 4, 5, 5, 6, 6, 6, 7, 7, 8, 8};
				Layout=CopyArrary(Cell_Layout);
				break;
			}
			case 20:{
				jobNum  =200;
				machNum =20;
				cellNum =8;
				int Cell_Layout[]={0,1, 1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 6, 7, 7, 8, 8};
				Layout=CopyArrary(Cell_Layout);
				break;			
			}
			}//end switch
			
			int Cell_Layout_Nums[] = ReadLayOutInfo(Layout,cellNum);
			int familyNum = cellNum;//工件族的编号是从0开始的
			int transportorSize = cellNum;
			Random r=new Random(1);//1代表种子数
			
			/**  
//			Layout for random
			int []Cell_Lay_Nums = new int[cellNum];
			int []Cell_Layout   = new int[machNum+1];
			int sum_cell_num = 0;
			for(int count = 0; count< cellNum; count++){	//随机生成单元分布
				int cur_cell_num 	   =  r.nextInt((machNum-sum_cell_num)/2 -1 )+1;
				Cell_Lay_Nums[count]   =  cur_cell_num;
				sum_cell_num		   += cur_cell_num;
			}
			
			int cur_head = 1;	//初始时Cell_Layout头指针的位置
			for(int count = 0; count< cellNum; count++){	//把机器从头按数量顺序安排到单元中
				for(int machine_index = 0; machine_index < Cell_Lay_Nums[count]; machine_index++){ // for each cell
					Cell_Layout[machine_index+cur_head] = count+1; 
				}
				cur_head+=Cell_Lay_Nums[count];
			}
			**/

			
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
						TranTime[i][j]=r.nextInt(25)+5;//TransTime U[5,30]
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
				 *  df是因子
				 * **/
				int operNum ;
				if(machNum >5){
					operNum =    r.nextInt(machNum-5)+ 5; 	// 工序数目 U[5,19] -- 非柔性时应小于机器数目
				}else{
					operNum = 	 machNum;
				}
				int family  =    r.nextInt(familyNum); 		// 工件族号
				int DueDate ;								// 工件的DUEDATE
				float aveg_tt=0;							// 统计duedate
				double weight = (r.nextInt(9)+1)/10.0;		// 工件权重 U[0,1]

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
							Time_For_Ops[ops_point]    = r.nextInt(100)+1;	//加工时间 U[1,100]
							Machine_For_Ops[ops_point] = machinesForEachCell[temp];
							sum_time+= Time_For_Ops[ops_point];//先不加SetupTime[family][machinesForEachCell.get(temp)];
							ops_point++;
						}
						aveg_tt+=sum_time;
					}
				}

				//aveg_tt 加上转移时间
				int cur_cell =1;
				while(true){
					int temp=cur_cell+1;
					for(;temp< Ops_Seq_For_Cell.length && Ops_Seq_For_Cell[temp]==0; temp++);
					if(temp < Ops_Seq_For_Cell.length){
						aveg_tt += TranTime[Ops_Seq_For_Cell[cur_cell-1]][Ops_Seq_For_Cell[temp-1]];
					}else{
						break;
					}
					cur_cell = temp;
				}
//				DueDate=(int)(Constants.DUE_FACTOR_DEFAULT*aveg_tt+0);//0是表示arriveTime		
				
				int TightDueDate=(int)(Constants.DUE_FACTOR_TIGHT*aveg_tt+0);	
				int LooseDueDate=(int)(Constants.DUE_FACTOR_LOOSE*aveg_tt+0);

		/***************************开始写工序内容***********************************************************************/
//				writer.write(operNum+"    "+family+"    "+DueDate+"    "+weight+"\n");
				writer.write(operNum+"    "+family+"    "+TightDueDate+"    "+weight+"    "+LooseDueDate+"\n");
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
		int Layout_flag=1;
		int Layout_end = cellLayout.length;
		for(int i = 1; i <= cellNum; i++){
			int sum=0;
			while(Layout_flag<Layout_end && cellLayout[Layout_flag]==i){
				Layout_flag++;
				sum++;
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
	 * @Description get the random operate sequeces of cell for the job
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
		for(int i = 0; i< Constants.TOTAL_PROBLEMS; i++) {
			CMSGenerate_IABCForGP dataSource = new CMSGenerate_IABCForGP(i);
			System.out.println("success for"+i);
		}
	}
}
