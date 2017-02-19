package ec.app.myApp;

// HSGA 的实验数据，作为参考值
public class Reference {
    public static final double[][] REF_CMAX = {
            { 374, 362.6, 361.4, 355, 402, 398.6, 364, 384, 386.2, 385.4 },
            { 419, 430, 434.8, 400, 448, 427, 450, 423, 438.8, 441 },
            { 446.2, 430, 430.6, 407, 426.8, 450.2, 446.6, 431.2, 427.8, 410 },
            { 542, 536.8, 517, 528.6, 501, 541.4, 520.4, 519, 512.4, 521.2 },
            { 934.4, 971.8, 936.8, 949, 953, 896.2, 973, 921, 911.8, 888.4 },
            { 1059.2, 1103.4, 1114.4, 1134.4, 1120, 1110.6, 1066, 1083.6,
                    1062.8, 1107.8 },
            { 1096.8, 1059.6, 1030.8, 1034, 1084.8, 1061.6, 1078.4, 1126.8,
                    1124.8, 1054.2 },
            { 1210.2, 1223.4, 1180, 1198.4, 1170, 1127, 1180.8, 1121, 1151,
                    1144.2 },
            { 1206.2, 1275.6, 1233.8, 1353, 1278.4, 1199.2, 1209.2, 1297.2,
                    1243.2, 1242.8 },
            { 1326.2, 1329, 1361.4, 1292.6, 1411, 1323.6, 1314.6, 1378, 1403.6,
                    1337 },
            { 1141.2, 1129.4, 1135.8, 1121, 1163.6, 1140.6, 1165.4, 1121.6,
                    1100, 1150.8 },
            { 1247.4, 1310.2, 1248.4, 1204.2, 1243.6, 1221.2, 1202.8, 1187.6,
                    1183, 1210.6 },
            { 1251.8, 1361.4, 1312.4, 1235.8, 1254.8, 1308.8, 1260.4, 1284,
                    1341, 1327.4 },
            { 1364, 1298, 1346.6, 1384, 1441.4, 1370.8, 1338.2, 1372, 1345.6,
                    1391.4 },
            { 1855.2, 1886, 1935.2, 2053, 1835.2, 1921.4, 1853.6, 1836, 1934,
                    1916.4 },
            { 1969.6, 2099.2, 1961.8, 2060, 2017, 2023, 2058.4, 1994.4, 2058.4,
                    1987.2 },
            { 2109.4, 2089.2, 2026.2, 2184, 2070.8, 2166.6, 2189.8, 2126,
                    2081.6, 2059 },
            { 2239, 2274.6, 2284.8, 2247.6, 2247.2, 2170.2, 2184.2, 2255.8,
                    2218, 2134.4 } };
    public static final double[][] REF_TWT = {
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 578.394, 680.5, 873.722, 703.802, 606.126, 486.55, 559.382,
                    758.622, 648.664, 466.37 },
            { 1368.008, 1754.06, 813.75, 1275.874, 1601.866, 1681.806,
                    1361.278, 1455.358, 892.222, 1083.81 },
            { 952.662, 773.126, 653.552, 1203.014, 717.942, 557.978, 1353.36,
                    787.266, 929.932, 542.808 },
            { 2049.398, 2517.756, 1955.47, 2019.654, 1607.738, 1110.014,
                    2084.676, 1592.184, 1538.95, 1953.75 },
            { 2254.916, 2390.82, 2218.116, 2743.34, 2105.14, 2333.492,
                    1587.398, 2650.766, 2001.464, 2268.892 },
            { 2516.48, 2618.558, 4831.858, 3169.342, 2912.094, 3082.416,
                    4007.176, 2634.894, 3864.428, 3309.278 },
            { 1719.186, 2409.12, 1895.114, 1386.016, 1772.638, 2389.768,
                    1845.73, 2070.868, 1188.222, 1530.732 },
            { 2330.05, 3046.718, 2416.91, 2427, 2121.81, 1888.244, 2935.168,
                    2574.864, 3121.494, 2100.896 },
            { 2355.188, 1958.976, 4334.372, 2724.14, 3319.824, 3106.24,
                    1566.85, 2962.472, 2944.956, 2334.804 },
            { 3765.568, 3266.162, 4425.212, 3841.144, 4318.91, 3564.27,
                    4592.06, 4463.48, 4237.714, 3496.684 },
            { 9287.042, 10149.714, 9818.888, 12402.75, 10246.75, 11813.314,
                    8361.38, 9626.794, 13945.57, 12358.824 },
            { 14521.886, 16348.396, 11976.22, 15117.12, 14228.94, 15951.118,
                    14281.208, 15143.11, 12927.462, 14082.33 },
            { 16038.812, 15825.298, 13570.028, 14237.168, 14932.566, 14757.216,
                    16258.898, 14883.692, 15314.184, 15052.976 },
            { 16331.006, 18635.476, 19049.106, 19516.674, 15040.03, 16970.872,
                    23386.564, 21277.874, 15801.578, 19274.202 } };
    public static final double[][] REF_Test = {
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
    };
}
