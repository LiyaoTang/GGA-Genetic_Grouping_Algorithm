using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace ACODataMaker
{
    class Program
    {
        static int phase = 0;
        internal static double NormalDistribution()
        {
          Random rand = new Random();
            double u1, u2, v1=0, v2=0, s = 0, z=0;
            if (phase == 0)
            {
                while (s > 1 || s == 0)
                {
                    u1 = rand.NextDouble();
                    u2 = rand.NextDouble();
                    v1 = 2 * u1 - 1;
                    v2 = 2 * u2 - 1;
                    s = v1 * v1 + v2 * v2;
                }
                z = Math.Sqrt(-2 * Math.Log(s) / s) * v1;
            }
            else
            {
                z = Math.Sqrt(-2 * Math.Log(s) / s) * v2;
            }
            phase = 1 - phase;
            return z; //返回服从正态分布N(0,1)的随机数
        }
        static int Correct(int num, int max){

            if (num < 0)
            {
                num = 0;
            }
            else if(num > max)
            {
                num = max;
            }
            return num;      
        }

        static void Main(string[] args)
        {
            Random rdm = new Random();
            StreamWriter output = new StreamWriter("G:\\GGA2\\Case\\10.txt", false);
            output.AutoFlush = true;

            int[] MachineNumPerCell = { 2, 1, 3 };
            int[] CellCount = { 0, 9, 24, 30, 37, 45, 55, 64 };
            Boolean[] cellavailable = { true, true, true, true, true, true, true, true };

            int JobNum = 5;
            int MachineNum = 6;
            int CellNum = 3;
            int Capacity = 400;
            int TransferTime = rdm.Next(3) + 1;     //1 - 3

            output.WriteLine("{0} {1} {2} {3}", JobNum, MachineNum, CellNum, Capacity);
        
            for (int i = 0; i < CellNum; i++)
            {
                for (int j = 0; j < MachineNumPerCell[i]; j++)
                {
                    output.Write(i + " ");
                }
            }
            output.WriteLine();
            
            for (int i = 0; i < JobNum; i++)
            {
                int[][] re;
                 int OpNum = rdm.Next(15) + 5;      // 5 - 19
                // int Size = rdm.Next(21) + 20;    // 20 - 40
                int weight = rdm.Next(5) + 1;       // 1 - 5
                int duedate = rdm.Next(2) + 0;      // 0 - 1
                
                re = new int[OpNum][];
                for (int j = 0; j < OpNum; j++)
                {
                    re[j] = new int[MachineNum];
                }

                for (int j = 0; j < OpNum ; j++)
                {
                    int AbleMachineNum = rdm.Next(CellNum - 2) + 2;
                    for (int k = 0; k < AbleMachineNum; k++)
                    {
                        int temp = (int)(Math.Sqrt(5) * NormalDistribution()+(CellNum/2)); // ????????????
                        int AbleCell = Correct(temp, CellNum-1);

                        while(cellavailable[AbleCell] == false)
                        {
                            AbleCell = (int)(Math.Sqrt(5) * NormalDistribution() + (CellNum / 2));
                            AbleCell = Correct(AbleCell, CellNum-1);
                            if (cellavailable[AbleCell] == true)
                            {
                                break;
                            }
                        }
                        cellavailable[AbleCell] = false;

                        temp = (int)(Math.Sqrt(5) * NormalDistribution() + ((MachineNumPerCell[AbleCell]) - 5));
                        int AbleMachine = Correct(temp, MachineNumPerCell[AbleCell]) + CellCount[AbleCell];
                        if (re[j][AbleMachine] != 0)
                        {
                            k--;
                            continue;
                        }
                        re[j][AbleMachine] = rdm.Next(49) + 2;  // 2 - 50
                    }
                    for (int k = 0; k < cellavailable.Length; k++) 
                    {
                        cellavailable[k] = true;
                    }
                }

                output.WriteLine("{0} {1} {2}", OpNum, weight, duedate);
                for (int j = 0; j < OpNum; j++)
                {
                    for (int k = 0; k < MachineNum; k++)
                    {
                        output.Write(re[j][k] + " ");
                    }
                    output.WriteLine();
                }
                //Console.Write(i);
            }

           

            int[][] tr = new int[CellNum][];
            for (int j = 0; j < CellNum; j++)
            {
                tr[j] = new int[CellNum];
            }
            for (int j = 0; j < CellNum; j++)
            {
                for (int k = j + 1; k < CellNum; k++)
                {
                    tr[j][k] = tr[k][j] = rdm.Next(45) + 6;   //6 - 50
                }
            }

            for (int j = 0; j < CellNum; j++)
            {
                for (int k = 0; k < CellNum - 1; k++)
                {
                    output.Write(tr[j][k] + " ");
                }
                output.WriteLine(tr[j][CellNum - 1]);
            }
        }
    }
}
