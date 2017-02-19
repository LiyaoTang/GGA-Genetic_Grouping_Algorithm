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

            double[] reserveList = {0.3, 0.5, 0.7};
            double[] crossList = {0.05, 0.3, 0.6, 0.9};
            double[] mutList = {0, 0.02, 0.10, 0.18};
            int[] genList={25, 50, 100};
            int[] popList = {6, 12};

            for (double reserveProb: reserveList)
                for (double cross: crossList)
                    for (double mut: mutList)
                        for (int gen: genList)
                            for (int pop: popList){
                                System.out.println();
                                System.out.println("cross=" + cross + " mut=" + mut + " gen=" + gen + " pop=" + pop + " reserveProb=" + reserveProb);

                                for (int i = 1; i <= 5; i++) {
                                    System.out.println("case" + i + "\t");
                                    AntSystem AS = new AntSystem();

                                    CMSReader dr = new CMSReader("data_\\" + i + ".txt"); // read from txt initialize machineSet, jobSet, cellSet, vehicleSet in CMSReader
                                    AS.setProblemSize(dr.getMachineSet(), dr.getJobSet(), dr.getCellSet()); // copy to ms, js, cs in AntSystem

                                    long start = System.currentTimeMillis();
                                    for (int j = 0; j < 4; j++) {
                                        AS.Init(0.05, 0.1, 0.5, 100, 12);
//                                        AS.Init(cross, mut, reserveProb, gen, pop);
                                        AS.schedule(i);
                                        TWT_total += AS.Findbest();
                                    }

                                    totalTime = (System.currentTimeMillis() - start);
                                    System.out.println("time\t" + totalTime / 4000.0);
                                    System.out.println("average\t" + df.format(TWT_total / 4));
                                    TWT_total = 0;
                                    System.out.println();
                                }
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