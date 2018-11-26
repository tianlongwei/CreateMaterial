package my.loong.commons;

/**
 * @program: CreateMaterial
 * @description:
 * @AUTHOR: tlw
 * @create: 2018-11-21 21:59
 */
public class MaterialParameter_GB50010_2010 {

    //
    //返回alfaC1
    //
    /// 返回规范50010-2010的轴心抗压强度与立方体抗压强度的比值alfa_C1
    public static double GetAlfaC1(double fcuk)
    {
        if (fcuk <= 50)
        {
            return 0.76;
        }
        else if (fcuk >= 80)
        {
            return 0.82;
        }
        else
        {
            return (fcuk - 50) / 30 * (0.82 - 0.76) + 0.76;
        }
    }

    //
    //返回alfaC2
    //
    ///返回规范50010-2010的脆性折减系数
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
    /// 返回规范50010-2010的轴心抗压强度标准值fck
    public static double GetFck(double fcuk, boolean isConsider088, boolean isConsiderAlfa_C2)
    {
        return (isConsider088 ? 0.88 : 1) * GetAlfaC1(fcuk) * (isConsiderAlfa_C2 ? GetAlfaC2(fcuk) : 1) * fcuk;
    }

    //
    //返回Ftk
    //
    /// 返回规范50010-2010的轴心抗拉强度标准值ftk
    public static double GetFtk(double fcuk, boolean isConsider088, boolean isConsiderAlfa_C2, double delta_c)
    {
        return (isConsider088 ? 0.88 : 1) * 0.395 * (Math.pow(fcuk, 0.55))
                * Math.pow(1 - 1.645 * delta_c, 0.45) * (isConsiderAlfa_C2 ? GetAlfaC2(fcuk) : 1);
    }

    //
    //返回Fcm
    //
    /// 返回规范50010-2010的轴心抗压强度平均值fcm
    public static double GetFcm(double fcum)
    {
        return 0.76 * fcum;
    }

    //
    //返回fcum
    //
    /// 返回规范50010-2010的立方体抗压强度平均值fcum
    public static double GetFcum(double fcuk,double delta_c)
    {
        return fcuk * (1 + 1.645 * delta_c);
    }

    //
    //返回Ftm
    //
    /// 返回规范50010-2010的轴心抗拉强度平均值ftm
    public static double GetFtm(double fcum)
    {
        return 0.395 * Math.pow(fcum, 0.55);
    }

    //
    //修正试验强度为标准试块强度
    //GB_T5081-2002普通混凝土力学性能试验方法标准
    //
    /// 返回规范50010-2010的100非标准试件换算到标准试件立方体强度
    public static double GetFcuFromFcu100(double fcu100)
    {
        return fcu100 * 0.95;
    }

    /// 返回规范50010-2010的200非标准试件换算到标准试件立方体强度
    public static double GetFcuFromFcu200(double fcu200)
    {
        return fcu200 * 1.05;
    }
}