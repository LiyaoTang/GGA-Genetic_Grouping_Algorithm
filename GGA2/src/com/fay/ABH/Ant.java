package com.fay.ABH;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fay.domain.Machine;

public class Ant implements Comparable<Ant>{
    /// <summary>
    /// 锟斤拷锟斤拷锟斤拷锟�
    /// </summary>
    public int AntNo;
    
    public AntSystem temp;

	/// <summary>
    /// 锟斤拷锟斤拷锟斤拷晒锟斤拷锟斤拷锟絒i]=n锟斤拷示锟斤拷锟斤拷i锟侥凤拷锟缴癸拷锟斤拷为n
    /// </summary>
    public List<Integer> AssignmentRule;
    /*DB-Assignment */
    public List<Integer> AssignmentDB;
    
    /// <summary>
    /// 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟絒m]=n锟斤拷示锟斤拷锟斤拷m锟斤拷锟斤拷锟斤拷锟斤拷锟轿猲
    /// </summary>
    public List<Integer> SeqRule;
    public List<Integer> SeqDB;

    /// <summary>
    /// 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
    /// </summary>
    public List<Integer> BatchRule;
    public List<Integer> BatchDB;
    
    /// <summary>
    /// 时锟戒窗锟斤拷锟斤拷
    /// </summary>
    public List<Integer> TimeWindowRule;


    /// <summary>
    /// 锟斤拷锟疥工时锟斤拷
    /// </summary>
    public double MakeSpan;

    /// <summary>
    /// 锟杰等达拷时锟斤拷
    /// </summary>
    public double TotalWaitTime;

    /// <summary>
    /// 锟斤拷锟斤拷锟斤拷
    /// </summary>
    public double Score;

    public int opTotalNum;
    public int opLeft;
    
    public void Reset()
    {

        MakeSpan = 0;
        TotalWaitTime = 0;
        Score = 0;

    }

    public int CompareTo(Ant other)
    {
		if(this.Score == other.Score)
			return 0;
		else if(this.Score > other.Score)
			return 1;
		else
			return -1;
    }

    public Ant Clone()
    {
        Ant newant = new Ant();

        newant.AntNo = AntNo;
        newant.MakeSpan = MakeSpan;
        newant.TotalWaitTime = TotalWaitTime;
        newant.Score = Score;

        newant.AssignmentRule = new ArrayList<Integer>();
        newant.AssignmentRule.addAll(AssignmentRule);
        newant.AssignmentDB = new ArrayList<Integer>();
        newant.AssignmentDB.addAll(AssignmentDB);
        
        newant.SeqRule = new ArrayList<Integer>();
        newant.SeqRule.addAll(SeqRule);
        newant.SeqDB = new ArrayList<Integer>();
        newant.SeqDB.addAll(SeqDB);
        
     //   newant.TimeWindowRule = new ArrayList<Integer>();
     //   newant.TimeWindowRule.addAll(TimeWindowRule);
        
  //      System.out.print(1);
   //     for(Integer i : TimeWindowRule){
   //     	System.out.print(i);
   //     }
        
        newant.BatchRule = new ArrayList<Integer>();
        newant.BatchRule.addAll(BatchRule);
        newant.BatchDB = new ArrayList<Integer>();
        newant.BatchDB.addAll(BatchDB);
        
        return newant;
    }

	@Override
	public int compareTo(Ant o) {
		// TODO Auto-generated method stub
		return 0;
	}
 //printDB
	public void printDB(){
		//System.out.println(SeqDB.get(i));
		int [] countDB= new int [10];
		 int allDB=0;
		for(int i=0;i<SeqDB.size();i++){
		//	System.out.print(SeqDB.get(i)+"  ");
			countDB[SeqDB.get(i)]++;
			allDB+=SeqDB.get(i);
			if(allDB > AntSystem.MachineNum)
				break;
		}
	    allDB=0;
		for(int i=0;i<AssignmentDB.size();i++){
			//	System.out.print(SeqDB.get(i)+"  ");
				countDB[AssignmentDB.get(i)]++;
				allDB+= AssignmentDB.get(i);
				if(allDB > AntSystem.JobNum)
					break;
			}
		 allDB=0;
		for(int i=0;i< BatchDB.size();i++){
			//	System.out.print(SeqDB.get(i)+"  ");
				countDB[BatchDB.get(i)]++;
				allDB+= BatchDB.get(i);
				if(allDB > AntSystem.CellNum)
					break;
			}
		//sum
		double sum=0.0;
		for(int i=1 ; i<= 4; i++){
		 sum+=countDB[i];
		}
		
		//every
		for(int i=1 ; i<= 4; i++){
			System.out.print(String.format("%.2f", countDB[i]/sum*100)+"\t");
		//	System.out.print(countDB[i]+"\t");
		}
	}

}


