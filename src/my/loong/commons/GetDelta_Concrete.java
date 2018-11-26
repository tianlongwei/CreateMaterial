package my.loong.commons;

/**
 * @program: CreateMaterial
 * @description:
 * @AUTHOR: tlw
 * @create: 2018-11-21 21:42
 */
public class GetDelta_Concrete {
    /// 返回由Gb50010-2010取得的混凝土强度变异系数
    public static double GetDelta_cFrom2010(double fcuk)
    {
        return 60.485 * Math.pow(fcuk, -0.361) * 0.01;
    }
    /// 返回由Gb50010-2002取得的混凝土强度变异系数
    public static double GetDelta_cFrom2002(double fcuk)
    {
        return ((-5.113316E-08) * Math.pow(fcuk, 5) + (1.36569952E-05) * Math.pow(fcuk, 4)
                - 1.43154896E-03 * Math.pow(fcuk, 3) + 7.64818470E-02 * Math.pow(fcuk, 2) - 2.25187872 * fcuk + 41.8083305) * 0.01;
        //return (-6E-05*Math.Pow(fcuk,3)+0.0127*Math.Pow(fcuk,2)-0.9033*fcuk+31.637)*0.01;
    }
}