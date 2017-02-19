package com.lm.util;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.lm.algorithms.rule.machine.IMachineRule;
import com.lm.algorithms.rule.machine.MachineAT_RPT;
import com.lm.algorithms.rule.machine.MachineCR;
import com.lm.algorithms.rule.machine.MachineEDD;
import com.lm.algorithms.rule.machine.MachineFIFO;
import com.lm.algorithms.rule.machine.MachineATC;

import com.lm.algorithms.rule.machine.MachineGP1_1;
import com.lm.algorithms.rule.machine.MachineGP1_2;
import com.lm.algorithms.rule.machine.MachineGP1_3;
import com.lm.algorithms.rule.machine.MachineGP1_4;
import com.lm.algorithms.rule.machine.MachineGP2_1;
import com.lm.algorithms.rule.machine.MachineGP2_2;
import com.lm.algorithms.rule.machine.MachineGP2_3;
import com.lm.algorithms.rule.machine.MachineGP2_4;
import com.lm.algorithms.rule.machine.MachineGP3_1;
import com.lm.algorithms.rule.machine.MachineGP3_2;
import com.lm.algorithms.rule.machine.MachineGP3_3;
import com.lm.algorithms.rule.machine.MachineGP3_4;
import com.lm.algorithms.rule.machine.MachineGP4_1;
import com.lm.algorithms.rule.machine.MachineGP4_2;
import com.lm.algorithms.rule.machine.MachineGP4_3;
import com.lm.algorithms.rule.machine.MachineGP4_4;
import com.lm.algorithms.rule.machine.MachineGP5_1;
import com.lm.algorithms.rule.machine.MachineGP5_2;
import com.lm.algorithms.rule.machine.MachineGP5_3;
import com.lm.algorithms.rule.machine.MachineGP5_4;
import com.lm.algorithms.rule.machine.MachineGP6_1;
import com.lm.algorithms.rule.machine.MachineGP6_2;
import com.lm.algorithms.rule.machine.MachineGP6_3;
import com.lm.algorithms.rule.machine.MachineGP6_4;
import com.lm.algorithms.rule.machine.MachineGP7_1;
import com.lm.algorithms.rule.machine.MachineGP7_2;
import com.lm.algorithms.rule.machine.MachineGP7_3;
import com.lm.algorithms.rule.machine.MachineGP7_4;
import com.lm.algorithms.rule.machine.MachineGP8_1;
import com.lm.algorithms.rule.machine.MachineGP8_2;
import com.lm.algorithms.rule.machine.MachineGP8_3;
import com.lm.algorithms.rule.machine.MachineGP8_4;
import com.lm.algorithms.rule.machine.MachineGP9_1;
import com.lm.algorithms.rule.machine.MachineGP9_2;
import com.lm.algorithms.rule.machine.MachineGP9_3;
import com.lm.algorithms.rule.machine.MachineGP9_4;
import com.lm.algorithms.rule.machine.MachineGP10_1;
import com.lm.algorithms.rule.machine.MachineGP10_2;
import com.lm.algorithms.rule.machine.MachineGP10_3;
import com.lm.algorithms.rule.machine.MachineGP10_4;
import com.lm.algorithms.rule.machine.MachineGP11_1;
import com.lm.algorithms.rule.machine.MachineGP11_2;
import com.lm.algorithms.rule.machine.MachineGP11_3;
import com.lm.algorithms.rule.machine.MachineGP11_4;
import com.lm.algorithms.rule.machine.MachineGP12_1;
import com.lm.algorithms.rule.machine.MachineGP12_2;
import com.lm.algorithms.rule.machine.MachineGP12_3;
import com.lm.algorithms.rule.machine.MachineGP12_4;
import com.lm.algorithms.rule.machine.MachineGP13_1;
import com.lm.algorithms.rule.machine.MachineGP13_2;
import com.lm.algorithms.rule.machine.MachineGP13_3;
import com.lm.algorithms.rule.machine.MachineGP13_4;
import com.lm.algorithms.rule.machine.MachineGP14_1;
import com.lm.algorithms.rule.machine.MachineGP14_2;
import com.lm.algorithms.rule.machine.MachineGP14_3;
import com.lm.algorithms.rule.machine.MachineGP14_4;
import com.lm.algorithms.rule.machine.MachineGP15_1;
import com.lm.algorithms.rule.machine.MachineGP15_2;
import com.lm.algorithms.rule.machine.MachineGP15_3;
import com.lm.algorithms.rule.machine.MachineGP15_4;
import com.lm.algorithms.rule.machine.MachineGP16_1;
import com.lm.algorithms.rule.machine.MachineGP16_2;
import com.lm.algorithms.rule.machine.MachineGP16_3;
import com.lm.algorithms.rule.machine.MachineGP16_4;
import com.lm.algorithms.rule.machine.MachineGP17_1;
import com.lm.algorithms.rule.machine.MachineGP17_2;
import com.lm.algorithms.rule.machine.MachineGP17_3;
import com.lm.algorithms.rule.machine.MachineGP17_4;
import com.lm.algorithms.rule.machine.MachineGP18_1;
import com.lm.algorithms.rule.machine.MachineGP18_2;
import com.lm.algorithms.rule.machine.MachineGP18_3;
import com.lm.algorithms.rule.machine.MachineGP18_4;
import com.lm.algorithms.rule.machine.MachineGP19_1;
import com.lm.algorithms.rule.machine.MachineGP19_2;
import com.lm.algorithms.rule.machine.MachineGP19_3;
import com.lm.algorithms.rule.machine.MachineGP19_4;
import com.lm.algorithms.rule.machine.MachineGP20_1;
import com.lm.algorithms.rule.machine.MachineGP20_2;
import com.lm.algorithms.rule.machine.MachineGP20_3;
import com.lm.algorithms.rule.machine.MachineGP20_4;
import com.lm.algorithms.rule.machine.MachinePT_TIS;
import com.lm.algorithms.rule.machine.MachineSL;
import com.lm.algorithms.rule.machine.MachineSPT;
import com.lm.algorithms.rule.machine.MachineSRPT;
import com.lm.algorithms.rule.machine.MachineTIS;
import com.lm.algorithms.rule.machine.MachineWEDD;
import com.lm.algorithms.rule.machine.MachineWSPT;

import com.lm.algorithms.rule.transportor.ITransportorRule;
import com.lm.algorithms.rule.transportor.TransEDD;
import com.lm.algorithms.rule.transportor.TransFIFO;
import com.lm.algorithms.rule.transportor.TransGP3;
import com.lm.algorithms.rule.transportor.TransGP5;
import com.lm.algorithms.rule.transportor.TransGP6;
import com.lm.algorithms.rule.transportor.TransOperAndTrans;
import com.lm.algorithms.rule.transportor.TransOpersAndFIFO;
import com.lm.algorithms.rule.transportor.TransGP1;
import com.lm.algorithms.rule.transportor.TransGP2;

public class Constants {
    public static final int TOTAL_CASE = 1;//为了测试修改成1.原来是18
    public static final double DUE_FACTOR_DEFAULT = 0.5;//1或者-2;
//    public static final String CMS_SOURCE = "testInstances/cms-Trans/";
//    public static final String CMS_SOURCE = "data/Trans/HSGA-Trainset/";
//    public static final String CMS_SOURCE = "data/Trans/IABC_0919/";
//    public static final String CMS_SOURCE = "data/Trans/strategy/";
//    public static final String CMS_SOURCE = "data/Trans/NewStrategy/";
//    public static final String CMS_SOURCE = "data/Trans/EM-LIKE/";
//    public static final String CMS_SOURCE = "data/Trans/CPLEX_add/new_c2/";
//    public static final String CMS_SOURCE = "data/Trans/CACO1/";
public static final String CMS_SOURCE = "data/Trans/data_GGA/";

	public static final String HSGA_SOURCE = "data/Trans/CPLEX_add/new/";
	/** 机器选工件调度规则 */
    public static final IMachineRule[] mRules = {
    		new MachineSPT(),
            new MachineEDD(), new MachineTIS(),new MachineSRPT(),new MachineSL(),
            new MachineCR(),
            new MachineWEDD(),new MachineWSPT(),
            new MachineAT_RPT(),new MachinePT_TIS(),
    	    new MachineATC(),
    };
    
    public static final IMachineRule[] mGPRules = {    //带GP20个，不带GP11个
		new MachineSPT(),new MachineATC(), 
        new MachineEDD(), new MachineTIS(),new MachineSRPT(),new MachineSL(),new MachineCR(),
        new MachineWEDD(),new MachineWSPT(),
        new MachineAT_RPT(),new MachinePT_TIS(),
        new MachineGP1_1(), new MachineGP1_2(), new MachineGP1_3(),new MachineGP1_4(),

        
//        new MachineGP2_1(),
//        new MachineGP2_2(),new MachineGP2_3(),new MachineGP2_4(),
//        new MachineGP3_1(), 
//        new MachineGP3_2(),new MachineGP3_3(),new MachineGP3_4(),
//        new MachineGP4_1(),
//        new MachineGP4_2(),new MachineGP4_3(),new MachineGP4_4(),
//        new MachineGP5_1(),
//        new MachineGP5_2(),new MachineGP5_3(),new MachineGP5_4(),
//        new MachineGP6_1(),
//        new MachineGP6_2(),new MachineGP6_3(),new MachineGP6_4(),
//        new MachineGP7_1(),
//        new MachineGP7_2(),new MachineGP7_3(),new MachineGP7_4(),
//        new MachineGP8_1(),
//        new MachineGP8_2(),new MachineGP8_3(),new MachineGP8_4(),
//        new MachineGP9_1(),
//        new MachineGP9_2(),new MachineGP9_3(),new MachineGP9_4(),
//        new MachineGP10_1(),
//        new MachineGP10_2(),new MachineGP10_3(),new MachineGP10_4(),
//        new MachineGP11_1(),
//        new MachineGP11_2(),new MachineGP11_3(),new MachineGP11_4(),
//        new MachineGP12_1(),
//        new MachineGP12_2(),new MachineGP12_3(),new MachineGP12_4(),
//        new MachineGP13_1(),
//        new MachineGP13_2(),new MachineGP13_3(),new MachineGP13_4(),
//        new MachineGP14_1(),
//        new MachineGP14_2(),new MachineGP14_3(),new MachineGP14_4(),
//        new MachineGP15_1(),
//        new MachineGP15_2(),new MachineGP15_3(),new MachineGP15_4(),
        
        new MachineGP16_1(),
        new MachineGP17_1(),
        new MachineGP18_1(),
        new MachineGP19_1(),
        new MachineGP20_1(),

};
    
    
//    public static final IMachineRule[] mGPRules = {
////		new MachineSPT(),
////        new MachineEDD(), new MachineTIS(),new MachineSRPT(),new MachineSL(),new MachineCR(),
////        new MachineWEDD(),new MachineWSPT(),
////        new MachineAT_RPT(),new MachinePT_TIS(),
//        new MachineGP1_1(), new MachineGP1_2(), new MachineGP1_3(),new MachineGP1_4(),
//        new MachineGP2_1(),
////        new MachineGP2_2(),new MachineGP2_3(),new MachineGP2_4(),
//        new MachineGP3_1(), 
////        new MachineGP3_2(),new MachineGP3_3(),new MachineGP3_4(),
//        new MachineGP4_1(),
////        new MachineGP4_2(),new MachineGP4_3(),new MachineGP4_4(),
//        new MachineGP5_1(),
////        new MachineGP5_2(),new MachineGP5_3(),new MachineGP5_4(),
//        new MachineGP6_1(),
////        new MachineGP6_2(),new MachineGP6_3(),new MachineGP6_4(),
//        new MachineGP7_1(),
////        new MachineGP7_2(),new MachineGP7_3(),new MachineGP7_4(),
//        new MachineGP8_1(),
////        new MachineGP8_2(),new MachineGP8_3(),new MachineGP8_4(),
//        new MachineGP9_1(),
////        new MachineGP9_2(),new MachineGP9_3(),new MachineGP9_4(),
//        new MachineGP10_1(),
////        new MachineGP10_2(),new MachineGP10_3(),new MachineGP10_4(),
//        new MachineGP11_1(),
////        new MachineGP11_2(),new MachineGP11_3(),new MachineGP11_4(),
//        new MachineGP12_1(),
////        new MachineGP12_2(),new MachineGP12_3(),new MachineGP12_4(),
//        new MachineGP13_1(),
////        new MachineGP13_2(),new MachineGP13_3(),new MachineGP13_4(),
//        new MachineGP14_1(),
////        new MachineGP14_2(),new MachineGP14_3(),new MachineGP14_4(),
//        new MachineGP15_1(),
////        new MachineGP15_2(),new MachineGP15_3(),new MachineGP15_4(),
//        new MachineGP16_1(),
////        new MachineGP16_2(),new MachineGP16_3(),new MachineGP16_4(),
//        new MachineGP17_1(),
////        new MachineGP17_2(),new MachineGP17_3(),new MachineGP17_4(),
//        new MachineGP18_1(),
////        new MachineGP18_2(),new MachineGP18_3(),new MachineGP18_4(),
//        new MachineGP19_1(),
////        new MachineGP19_2(),new MachineGP19_3(),new MachineGP19_4(),
//        new MachineGP20_1(),
////        new MachineGP20_2(),new MachineGP20_3(),new MachineGP20_4(),
//};
//    public static final IMachineRule[] mRules = { 
//    	new MachineWSPT(),
//    	new MachineEDD(),
//    	new MachineGP1(),
//    	new MachinePT_TIS(),
//    	new MachineAT_RPT(),new MachineSL(), new MachineSRPT(),
//    	new MachineGP2(),
//    	new MachineWEDD(), 
//    	new MachineCR(),
//    	new MachineGP3(),new MachineGP4(),
//    	new MachineGP5(),new MachineGP6(),new MachineGP7(),
//    	new MachineGP8(),
//    };
//  public static String RULESET_DIR = "RulePriosirs/withGP";
    public static String RULESET_DIR = "Rule_new20/rule";
    public static String GPSET_DIR = "Rule_new20/GP";
//	public static final String GPSET_DIR =  "Rule0919/GP";
//	public static String GPSET_DIR =  "Rule_new12/GP";
//	public static final String GPSET_DIR =  "Rule0919/TEST";
//	public static String AG_ITER_DIR = "result/ABC/AgingLeader";
//	public static final String MU_ITER_DIR = "result/ABC/Mutation";
	public static final String MUPAN_ITER_DIR = "result/ABC/MultiPan";
//	public static final String PAN_ITER_DIR = "result/ABC/Pan";
//	public static final String RANDOM_ITER_DIR = "result/ABC/random";
//	public static final String STRATEGY_ITER_DIR = "result/ABC/strategy";
//	public static String RULESQUENCE_DIR = "RuleSequences/K1K2";
  public static String RULESQUENCE_DIR = "RuleSequences/WithGP";
//  public static String RULESQUENCE_DIR = "NewSequences/WithGP";
//  public static String RULESQUENCE_DIR = "NewSeq8/WithGP";
//  public static String RULESQUENCE_DIR = "NewSeq8/WithoutGP";
//  public static String RULESQUENCE_DIR = "RuleSequences/rule";
    
    
    /**用于EM-like的例子输入输出**/
//    public static String RULESQUENCE_DIR = "RuleSequences/WithGP_EM";
//    public static String RULESQUENCE_DIR = "RuleSequences/WithoutGP_EM";
//    public static String GPSET_DIR =  "RuleEM/GP";
//    public static String RULESET_DIR = "RuleEM/rule";
    
    /**用于CACO的例子输入输出**/
////  public static String RULESQUENCE_DIR = "RuleSequences/WithGP_CACO";
//  public static String RULESQUENCE_DIR = "RuleSequences/WithoutGP_CACO";
//  public static String GPSET_DIR =  "Rule_CACO/GP";
//  public static String RULESET_DIR = "Rule_CACO/rule";
    
    
    
    public static int MachineRuleIndex;
    /**Transportor调度规则*/
//      public static fin al ITransportorRule[] TRules = {
//    	  new TransOperAndTrans(),new TransFIFO(),new TransEDD(),new TransOpersAndFIFO()
//      };
    public static final ITransportorRule[] TRules = {    //带GP9个,不带GP4个
    	new TransOperAndTrans(),new TransOpersAndFIFO(),
    	new TransFIFO(),new TransEDD(),
    	new TransGP1(),
    	new TransGP2(),new TransGP3(),
    	new TransGP5(),new TransGP6()
    };
    public static int TransRuleIndex;
 
    public static int[][] setupTime; 		// 生产准备时间，以工件族为索引
    public static int[][] transferTime;		// 转移时间矩阵
    
    public static int[][] MachineToParts;	// 机器可加工的工件集合
//    public static int[][] MachineToPartsFunc;//机器可加工的工件Func
    public static int[][] CellToNextCells;	// 单元可运输到的单元集合
//    public static int[][] CellToNextCellsFunc;//机器可加工的工件Func
    public static ArrayList<Integer>[][] CellToParts;	// 对应路线上可以运输的工件集合
//    public static int[][][] CellToPartsFunc;//对应路线上可以运输的工件Func
    
    /**机器--单元的哈希表关系**/
    public static Map<Integer, Integer> CellForm;
    
//    public static final String[] PROBLEM_NAMES = {
//    	"1","2","3","4","5",
//    	"6","7","8","9","10"
//    	
////    	"j5m6c3__3", "j15m8c3__3","j20m11c3__3",
////    	"j40m13c5__5","j50m15c5__5","j60m16c5__5",
////        "j70m20c7__7","j80m21c7__7","j90m21c7__7",
////        "j100m25c9__9", "j200m50c15__15"
//    };
    public static final String[] PROBLEM_NAMES = {
    	"j5m6c3__3", "j15m8c3__3","j20m11c3__3","j30m11c4_4",
    	"j40m13c5__5","j45m13c5_5","j50m15c5__5","j60m16c5__5","j65m18c6_6",
        "j70m20c7__7","j80m21c7__7","j90m21c7__7","j95m23c7_7",
        "j100m25c9__9", "j110m27c9__9", "j120m30c9__9",
        "j130m32c10__10", "j140m34c10__10", "j150m36c10__10",
        "j160m40c12__12"
    };
    public static final String[] PROBLEM_NAMES_CPLEX = {
    	
    };
    
//    public static final String[] PROBLEM_NAMES = {
//    	"j5m5c2","j10m5c2","j15m5c3","j20m5c3","j30m6c3","j40m8c3","j50m10c4","j60m12c4","j70m15c4","j80m15c5",
//    	"j90m18c5","j100m20c5","j110m21c5","j120m25c7","j130m25c7","j140m27c7","j150m30c10","j160m30c10","j180m36c10","j200m40c10"
//    };
    
//  /**CACO**/
//    public static final String[] PROBLEM_NAMES = {
//    	"1","2","3","4","5","6","7","8","9","10",
//    	"11","12"
//    };  
//    
//    public static final String[] PROBLEM_NAMES = {
//    	"p10m10c3","p14m12c3","p15m12c4","p15m9c3","p18m9c3","p20m9c3","p20m12c4","p20m15c5","p25m20c5","p30m12c3",
//    	"p30m20c4","p30m20c5","p35m30c5","p35m30c6","p40m28c7","p40m30c6"
//    };
    
	public static int RULE_SEQUENCES_COUNT ;	
//    public static final int TOTAL_PROBLEMS = 20;
    public static final int TOTAL_PROBLEMS = 16;    //EM-like的规模
    public static final int INSTANCES_PER_PROBLEM = 5;
    public static final int REPLICATIONS_PER_INSTANCE = 1;
	public static final float DUE_FACTOR_TIGHT = 0.1f;
	public static final float DUE_FACTOR_LOOSE = 6;
}
