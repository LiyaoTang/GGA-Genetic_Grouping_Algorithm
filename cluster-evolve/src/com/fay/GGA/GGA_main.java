package com.fay.GGA;
import java.text.DecimalFormat;
import java.io.*;
import com.fay.CMS.CMSReader;

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

            for (int i = 1; i <= 5; i++) {
                System.out.println("case" + i + "\t");
                AntSystem AS = new AntSystem();

                CMSReader dr = new CMSReader("data_\\" + i + ".txt"); // read from txt and initialize machineSet, jobSet, cellSet, vehicleSet in CMSReader
                AS.setProblemSize(dr.getMachineSet(), dr.getJobSet(), dr.getCellSet()); // copy to ms, js, cs in AntSystem

                long start = System.currentTimeMillis();
                for (int j = 0; j < 4; j++) {
                    AS.Init(0.90, 0.18, 0.3, 100, 48);
                    AS.schedule(i);
                    TWT_total += AS.Findbest();
                }

                totalTime = (System.currentTimeMillis() - start);
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