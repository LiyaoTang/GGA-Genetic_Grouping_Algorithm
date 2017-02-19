package com.fay.ABH;

import java.util.ArrayList;
import java.util.Collections;
import java.io.*;

//import java.util.List;
//import com.fay.ABH.MyComparator;
import com.fay.CMS.CMSReader;
import com.fay.scheduler.SimpleScheduler;
import com.fay.scheduler.SimpleScheduler;

public class ABH_main {
	public static void main(String[] args) throws IOException, CloneNotSupportedException {

		double TWT_total = 0;
		double DB_total = 0;
		long totalTime;
		// ����
		double pr_p[] = { 0.01, 0.05, 0.2, 0.8 };
		double pr_Q[] = { 0.05, 0.25, 1.0, 4.0 };
		// д���ļ�
		FileOutputStream fos=null;
		BufferedWriter bw=null ;
		try {
			fos = new FileOutputStream("data_ABH/ans.txt");
		    bw = new BufferedWriter(new OutputStreamWriter(fos));

			for (int jj = 0; jj < 1; jj++)
				for (int k = 1; k <  2 ; k++) {
					for (int i = 1; i < 21; i++) { // case ����
						System.out.print("case"+i+"\t");
						AntSystem AS = new AntSystem();

						// CMSReader dr = new
						// CMSReader("data_simbatch/case"+i+".txt");
						// CMSReader dr = new
						// CMSReader("data_simbatch1/case"+i+".txt");
						CMSReader dr = new CMSReader("data_ABH/" + i);
						AS.setProblemSize(dr.getMachineSet(), dr.getJobSet(),
								dr.getCellSet());
						// ���ÿ�ʼʱ��
						long start = System.currentTimeMillis();

						for (int j = 0; j < 4; j++) {
							// System.out.println("case"+i+"_"+j+"running");
							// ��ʼ��
							AS.Init();
							AS.schedule(i);

					/*		AS.imax = 200;// ��������
							AS.smax = 100;

							AS.p = pr_p[jj];
							AS.Q = pr_Q[k];

							AS.MainLoop();
							System.out.print(AS.getBest()+"\t");
							//AS.getBestDB();
							TWT_total += AS.getBest();// ������
					*/
							System.out.print(AS.Findbest()+"\t");
							TWT_total += AS.Findbest();// ������
							
						}
						// TWT_total = TWT_total/5;
						totalTime = (System.currentTimeMillis() - start);
					 System.out.println("average\t"+TWT_total/4);
					//	bw.write(TWT_total+"");
					//	bw.newLine();
					//	bw.flush();
						// �����д���ļ�ans.txt
						TWT_total = 0;
						DB_total = 0;
						totalTime = 0;
					}
					System.out.println();
				}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(bw!=null)
				bw.close();
			if(fos!=null){
				fos.close();
			}
		}

	}
}