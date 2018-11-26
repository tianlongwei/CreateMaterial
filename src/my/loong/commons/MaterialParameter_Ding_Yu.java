package my.loong.commons;

/**
 * @program: CreateMaterial
 * @description:
 * @AUTHOR: tlw
 * @create: 2018-11-21 21:43
 */
public class MaterialParameter_Ding_Yu {
    //
    //返回alfaC2
    //
    /// 返回脆性折减系数，一般脆性折减系数在丁-余本构中是不考虑的。
    public static double GetAlfaC2(double fcuk)
    {
        if (fcuk <= 40)
        {
            return 1;
        }
        else if (fcuk >= 80)
        {
            return 0.87;
        }
        else
        {
            return (fcuk - 40) / 40 * (0.87 - 1) + 1;
        }
    }


    //
    //返回Fck
    //
    /// 返回Ding_Yu本构的fck
    public static double GetFck(double fcuk, boolean isConsider088, boolean isConsiderAlfa_C2)
    {
        return (isConsider088 ? 0.88 : 1)  * (isConsiderAlfa_C2 ? GetAlfaC2(fcuk) : 1) *0.4*Math.pow(fcuk,1.1666666667);
    }

    //
    //返回Ftk
    //
    /// 返回Ding_Yu本构的ftk
    public static double GetFtk(double fcuk, boolean isConsider088, boolean isConsiderAlfa_C2)
    {
        return (isConsider088 ? 0.88 : 1) *(isConsiderAlfa_C2 ? GetAlfaC2(fcuk) : 1)*0.24*Math.pow(fcuk,0.666666667);
    }

    //
    //返回fcum
    //
    /// 返回Ding_Yu本构的Fcum
    public static double GetFcum(double fcuk, double delta_c)
    {
        return fcuk * (1 + 1.645 * delta_c);
    }
    //
    //返回Fcm
    //
    /// 返回丁_余本构的fcm
    public static double GetFcm(double fcum)
    {
        return 0.4 * Math.pow(fcum, 1.1666666667);
    }

    //
    //返回Ftm
    //
    /// 返回丁—余本构的ftm
    public static double GetFtm(double fcum)
    {
        return 0.24 * Math.pow(fcum, 0.6666666667);
    }

    //
    //修正试验强度为标准试块强度
    //GB_T5081-2002普通混凝土力学性能试验方法标准
    //
    ///由100非标准试件返回标准试件强度
    public static double GetFcuFromFcu100(double fcu100)
    {
        return 1.17*Math.pow(fcu100,0.95)-0.7;
    }

}