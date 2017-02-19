package com.fay.GGA;
import java.text.DecimalFormat;
import java.io.*;
import com.fay.CMS.MetaCMSReader;

public class GGA_main {
	public static void main(String[] args) throws IOException, CloneNotSupportedException {

		double TWT_total = 0;
		long totalTime;
		DecimalFormat  df = new DecimalFormat("######0"); 

		FileOutputStream fos=null;
		BufferedWriter bw=null ;
		try {
			fos = new FileOutputStream("data_ABH/ans.txt");
		    bw = new BufferedWriter(new OutputStreamWriter(fos));

            for (int i = 1; i <= 16; i++) {
                System.out.println("case" + i + "\t");
                AntSystem AS = new AntSystem();
                MetaCMSReader dr = new MetaCMSReader("G:\\IntelliJ IDEA 2016.2\\Projects\\GGA CMS Int\\EM-LIKE\\"+i); // read from txt initialize machineSet, jobSet, cellSet, vehicleSet in CMSReader

                AS.setProblemSize(dr.getMachineSet(), dr.getJobSet(), dr.getCellSet()); // copy to ms, js, cs in AntSystem
				long start = System.currentTimeMillis();

				double initSum = 0;
				for (int j = 0; j < 4; j++) {
					AS.Init(0.90, 0.18, 0.3, 100, 48);
					initSum += AS.schedule(i);
					TWT_total += AS.Findbest();
				}

                totalTime = (System.currentTimeMillis() - start);
				System.out.println("Init avg=" + initSum/4);
				System.out.println("time\t" + totalTime / 4000.0);
                System.out.println("average\t" + df.format(TWT_total / 4));
                TWT_total = 0;
                System.out.println();
            }

		} catch (FileNotFoundException e) {
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