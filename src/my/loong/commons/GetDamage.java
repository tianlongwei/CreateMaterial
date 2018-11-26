package my.loong.commons;

/**
 * @program: CreateMaterial
 * @description:
 * @AUTHOR: tlw
 * @create: 2018-11-21 21:41
 */
public class GetDamage {

    /// 能量平衡方法计算损伤
    public static double ByEnergyBalanceMethod(double stress, double Ec, double strain)
    {
        return 1 - Math.sqrt(stress / (Ec * strain));
    }
    /// 图解法计算受压损伤
    public static double ByGraphicMethod_Comp(double stress, double Ec, double strain_InElastic)
    {
        double b = 0.7;
        double a = strain_InElastic * (1 - b);
        double c = stress / Ec;
        double d = a + c;
        return a / d;
    }
    /// 图解法计算受拉损伤
    public static double ByGraphicMethod_Ten(double stress, double Ec, double strain_InElastic)
    {
        double b = 0.1;
        double a = strain_InElastic * (1 - b);
        double c = stress / Ec;
        double d = a + c;
        return a / d;
    }
    /// GB50010-2010损伤计算方法
    public static double ByGB50010_2010Method(double stress, double Ec, double strain, double epsilon_cr)
    {
        double epsilon_ca = Math.max(epsilon_cr / (epsilon_cr + strain), 0.09 * strain / epsilon_cr) * Math.sqrt(strain * epsilon_cr);
        double strain_Plastic = strain - ((strain + epsilon_ca) * stress / (stress + Ec * epsilon_ca));
        //这里的初始强度并不是规范使用的Ec0因为这里修正了曲线的初始强度为Ec。如果使用Ec0则造成前部分塑性应变为零或负值，且不是单调递增
        double Er = stress / (strain - strain_Plastic);
        return 1 - Er / Ec;
    }
}
