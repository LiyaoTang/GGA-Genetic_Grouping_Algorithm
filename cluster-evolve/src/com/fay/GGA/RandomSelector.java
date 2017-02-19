package com.fay.GGA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomSelector {

    public static Random rdm = new Random();
    //选择概率
    List<Double> Pr;
    //编号
    List<Integer> No;
    //概率总和
    double total;

    /// <summary>
    /// 构造函数
    /// </summary>
    public RandomSelector()
    {
        Pr = new ArrayList<Double>();
        No = new ArrayList<Integer>();
        total = 0;
    }

    /// <summary>
    /// 加入选项
    /// </summary>
    public void AddItem(double pr, int no)
    {
        Pr.add(pr);
        No.add(no);
        total += pr;
    }

    public int GetResult()
    {	
    //	 System.out.println("hi1");
        double times = 1;
        if (total < 1)
            times = 100000;
        else if (total < 100)
            times = 1000;
        else if (total < 10000)
            times = 100;
        else if (total > 429496000)
            times = 0.00001;

        int _total = (int)(total * times) + 1;
        double r = rdm.nextInt(_total);
        for (int i = 0; i < Pr.size(); i++)
        {
            r -= Pr.get(i) * times;
            if (r <= 0)
                return No.get(i);
        }
      //  System.out.println("hi2");
        return No.get(No.size()-1);
    }
}
