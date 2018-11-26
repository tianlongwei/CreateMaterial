package my.loong.commons;

import javafx.scene.control.Alert;
import my.loong.model.ParameterValue;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @program: CreateMaterial
 * @description:
 * @AUTHOR: tlw
 * @create: 2018-11-21 20:07
 */
public class Ding_Yu_Method {
    // 由立方体抗压强度平均值计算轴心抗压强度fcr
    public static double Getfcr(double fcur)
    {
        return 0.4 * Math.pow(fcur, 1.166666667);
    }

    /// 由立方体强度获得轴心抗拉强度Ftr
    public static double GetFtr(double fcur)
    {
        return 0.24 * Math.pow(fcur, 0.666666667);
    }

    /// 获取混凝土劈拉强度fts
    public static double GetFts(double fcur)
    {
        return 0.16 * Math.pow(fcur, 0.8);
    }

    /// 由立方体强度计算初始弹性模量Ec0
    /// 默认的Ec0计算方法
    public static double GetEc0(double fcur)
    {
        return 9500 * Math.pow(fcur, 0.333333333);
    }
    /// 由轴心抗压强度计算初始弹性模量
    public static double GetEc0_by_fcr(double fcr)
    {
        return 12343 * Math.pow(fcr, 0.2857142857);
    }

    /// 获取初始受拉弹性模量Et0
    /// 一般不建议取此值，由Ec0代替
    public static double GetEt0(double fcur)
    {
        return 6200 * Math.pow(fcur, 0.444444444);
    }


    /// 获取受拉峰值应变epsilon_cr
    /// 参数是立方体抗压强度
    public static double GetEpsilon_tr(double fcur)
    {
        return 33 * Math.pow(fcur, 0.333333333) * 0.000001;
    }
    /// 获取受拉峰值应变Epsilon_tr
    /// 参数是轴心受拉强度，峰值应力
    public static double GetEpsilon_tr_by_ftr(double ftr)
    {
        return 67*Math.pow(ftr,0.5)*0.000001;
    }
    /// 获取峰值受压应变Epsilon_cr
    public static double GetEpsilon_cr(double fcur)
    {
        return 383.14 * Math.pow(fcur, 0.38888888888889) * 0.000001;
    }
    /// 由强度代表值计算峰值应变epsilon_cr
    public static double GetEpsilon_cr_by_fcr(double fcr)
    {
        return 520 * Math.pow(fcr, 0.333333333) * 0.000001;
    }



    /// 返回丁-余本构参数Ac
    /// Ac表示混凝土初始弹性模量Ec0与峰值弹性模量Ecp=fcr/epsilon_cr的比值
    public static double GetAc(double fcur,double Ec0)
    {
        double fcr = Getfcr(fcur);
        double epsilon_cr = GetEpsilon_cr_by_fcr(fcr);
        double Ecr = fcr / epsilon_cr;
        double Ac = Ec0 / Ecr;
        return Ac;
    }
    /// 返回丁-余本构参数Ac
    /// 参数是轴心抗压强度fcr
    /// Ac表示混凝土初始弹性模量Ec0与峰值弹性模量Ecp=fcr/epsilon_cr的比值
    public static double GetAc_by_fcr(double fcr, double Ec0)
    {
        double epsilon_cr = GetEpsilon_cr_by_fcr(fcr);
        double Ecr = fcr / epsilon_cr;
        double Ac = Ec0 / Ecr;
        return Ac;
    }


    /// 获得丁发兴-余志武本构受压参数alfa_c
    public static double GetAlfa_c(double fcur)
    {
        return 2.5 * Math.pow(fcur, 3) * 0.00001;
    }
    /// 获得丁发兴-余志武本构受压参数alfa_c
    /// 参数是轴心抗压强度fcr
    public static double GetAlfa_c_by_fcr(double fcr)
    {
        double a = Math.pow(2.5, 3.571428571);
        double b = Math.pow(fcr, 2.571428571);
        return a * b * 0.00001;
    }




    /// 已知单个点的受压应变求取单个点的受压应力
    public static double GetStress_Comp(double fcur,double Ec0, double strain)
    {
        double stress = 0;
        double fcr = Getfcr(fcur);
        double epsilon_cr = GetEpsilon_cr_by_fcr(fcr);
        double Ac = GetAc(fcur,Ec0);
        double alfa_c = GetAlfa_c(fcur);

        double x = strain / epsilon_cr;
        double y = 0;
        if (x <= 1)
        {
            y = (Ac * x - x * x) / (1 + (Ac - 2) * x);
        }
        if (x > 1)
        {
            y = x / (alfa_c * Math.pow(x - 1, 2) + x);
        }

        stress = y * fcr;
        return stress;
    }
    /// 已知单个点的受压应变求取单个点的受压应力
    /// 参数为轴心抗压强度fcr
    public static double GetStress_Comp_by_fcr(double fcr,double Ec0, double strain)
    {
        double stress = 0;
        double epsilon_cr = GetEpsilon_cr_by_fcr(fcr);
        double Ac = GetAc_by_fcr(fcr, Ec0);
        double alfa_c = GetAlfa_c_by_fcr(fcr);

        double x = strain / epsilon_cr;
        double y = 0;
        if (x <= 1)
        {
            y = (Ac * x - x * x) / (1 + (Ac - 2) * x);
        }
        if (x > 1)
        {
            y = x / (alfa_c * Math.pow(x - 1, 2) + x);
        }

        stress = y * fcr;
        return stress;
    }



    /// 返回受拉本构的参数At
    public static double GetAt(double fcur,double Ec0)
    {
        double Et = Ec0;
        double epsilon_tr = GetEpsilon_tr(fcur);
        double Ftr = GetFtr(fcur);

        return Et * epsilon_tr / Ftr;
    }
    /// 返回受拉本构的参数At
    /// 参数是ftr
    public static double GetAt_by_ftr(double ftr, double Ec0)
    {
        double Et = Ec0;
        double epsilon_tr = GetEpsilon_tr_by_ftr(ftr);

        return Et * epsilon_tr /ftr;
    }


    /// 获取受拉本构下降段参数alfa_t
    public static double GetAlfa_t(double fcur)
    {
        return 1 + 3.456 * Math.pow(fcur, 2) * 0.0001;
    }
    /// 获取受拉本构下降段参数alfa_t
    public static double GetAlfa_t_by_ftr(double ftr)
    {
        return 1 + 0.025*Math.pow(ftr,3);
    }


    /// 已知单个点的受拉应变求取单个点的受拉应力
    /// </summary>
    /// <param name="fcur">立方体抗压强度</param>
    /// <param name="Ec0">初始弹性模量</param>
    /// <param name="strain">应变</param>
    /// <returns></returns>
    public static double GetStress_Ten(double fcur,double Ec0, double strain)
    {
        double stress = 0;
        double ftr = GetFtr(fcur);
        double epsilon_tr = GetEpsilon_tr(fcur);
        double A_t = GetAt(fcur,Ec0);
        double alfa_t = GetAlfa_t(fcur);


        double x = strain / epsilon_tr;
        double y = 0;
        if (x <= 1)
        {
            double a = A_t * x - x * x;
            double b = 1 + (A_t - 2) * x;
            y = a / b;
        }
        else if (x > 1)
        {
            double c = alfa_t * Math.pow(x - 1, 1.7) + x;
            y = x / c;
        }

        stress = y * ftr;

        return stress;
    }
    /// 已知单个点的受拉应变求取单个点的受拉应力
    public static double GetStress_Ten_by_ftr(double ftr, double Ec0, double strain)
    {
        double stress = 0;
        double epsilon_tr = GetEpsilon_tr_by_ftr(ftr);
        double A_t = GetAt_by_ftr(ftr, Ec0);
        double alfa_t = GetAlfa_t_by_ftr(ftr);


        double x = strain / epsilon_tr;
        double y = 0;
        if (x <= 1)
        {
            double a = A_t * x - x * x;
            double b = 1 + (A_t - 2) * x;
            y = a / b;
        }
        else if (x > 1)
        {
            double c = alfa_t * Math.pow(x - 1, 1.7) + x;
            y = x / c;
        }

        stress = y * ftr;

        return stress;
    }

    ///// 返回受压本构的极限压应变Epsilon_cu
    ///// 初始弹性模量Ec0按默认方法计算
    /// 返回受压本构的极限压应变Epsilon_cu
    /// Ec0为用户自定义的初始弹性模量
    /// epsilon_cu为0.4倍峰值强度对应的应变
    /// </summary>
    /// <param name="fcur"></param>
    /// <param name="Ec0"></param>
    /// <returns></returns>
    public static double GetEpsilon_cu(double fcur,double Ec0)
    {
        double fcr = Getfcr(fcur);
        double epsilon_cr = GetEpsilon_cr_by_fcr(fcr);
        double strain = epsilon_cr;
        double stressLimt = 0.4 * fcr;
        double stress =fcr ;
        double strain_Increment = 0.000001;
        for (; stress > stressLimt; strain += strain_Increment)
        {
            stress = GetStress_Comp(fcur,Ec0, strain);
        }
        return strain;
    }
    /// 返回受压本构的极限压应变Epsilon_cu
    public static double GetEpsilon_cu_by_fcr(double fcr, double Ec0)
    {
        double epsilon_cr = GetEpsilon_cr_by_fcr(fcr);
        double strain = epsilon_cr;
        double stressLimt = 0.4 * fcr;
        double stress = fcr;
        double strain_Increment = 0.000001;
        for (; stress > stressLimt; strain += strain_Increment)
        {
            stress = GetStress_Comp_by_fcr(fcr, Ec0, strain);
        }
        return strain;
    }



    /// 获取丁发兴-余志武本构的应力应变离散关系曲线，该曲线只包含工程应力和工程应变。
    public static SortedMap<Integer, ParameterValue>[] GetStress_Strain(double fcur, double Ec0)
    {
        SortedMap<Integer, ParameterValue> Stress_StrainPoints_Comp = new TreeMap<>();
        SortedMap<Integer, ParameterValue> Stress_StrainPoints_Ten = new TreeMap<>();
        //对ParameterValue初始化，即对所有字段均赋予了初值0，因此无需担心参数的不确定性问题。


        double fcr=Getfcr(fcur);
        double epsilon_cr = GetEpsilon_cr_by_fcr(fcr);
        double ftr=GetFtr(fcur);
        double epsilon_tr=GetEpsilon_tr(fcur);


        double strain = 0;
        double stress = 0;
        double strain_Increment = 0.000001;
        int count=0;
        double stressMid=0.5*fcr;
        double Ac = GetAc(fcur,Ec0);
        double alfa_c = GetAlfa_c(fcur);

        //获取受压应力应变离散关系
        for(;strain<=0.015;count++)
        {
            double x = strain / epsilon_cr;
            double y = 0;
            if (x <= 1)
            {
                y = (Ac * x - x * x) / (1 + (Ac - 2) * x);
            }
            if (x > 1)
            {
                y = x / (alfa_c * Math.pow(x - 1, 2) + x);
            }

            stress = y * fcr;

            ParameterValue nodePointParameterValue = new ParameterValue();
            nodePointParameterValue.strain = strain;
            nodePointParameterValue.stress = stress;
            nodePointParameterValue.Ec = Ec0;

            Stress_StrainPoints_Comp.put(count, nodePointParameterValue);

            if (strain > epsilon_cr && stress < stressMid)
            {
                strain_Increment *= 1.1;
            }

            //跳出循环的操作
            if (strain == 0.015)
            {
                break;
            }

            //
            //应注意有可能无法跳出操作，见上
            //
            if (0.015 - strain <= strain_Increment)
            {
                strain = 0.015;
            }
            else
            {
                strain += strain_Increment;
            }

        }

        //
        //获取受拉应力应变离散关系
        //
        strain = 0;
        stress = 0;
        count = 0;
        stressMid = 0.5 * ftr;
        strain_Increment = 0.000001;
        double At = GetAt(fcur,Ec0);
        double alfa_t = GetAlfa_t(fcur);

        for(;strain <=0.1;count++)
        {
            double x = strain / epsilon_tr;
            double y = 0;
            if (x <= 1)
            {
                double a = At * x - x * x;
                double b = 1 + (At - 2) * x;
                y = a / b;
            }
            else if (x > 1)
            {
                double c = alfa_t * Math.pow(x - 1, 1.7) + x;
                y = x / c;
            }

            stress = y * ftr;

            ParameterValue nodePointParameterValue = new ParameterValue();
            nodePointParameterValue.strain = strain;
            nodePointParameterValue.stress = stress;
            nodePointParameterValue.Ec = Ec0;

            Stress_StrainPoints_Ten.put(count, nodePointParameterValue);

            if (strain > epsilon_tr && stress < stressMid)
            {
                strain_Increment *= 1.1;
            }
            //跳出循环的操作
            if(strain==0.1)
            {
                break;
            }

            //
            //应注意有可能无法跳出操作，见上
            //
            if (0.1 - strain <= strain_Increment)
            {
                strain = 0.1;
            }
            else
            {
                strain += strain_Increment;
            }
        }


        SortedMap<Integer, ParameterValue>[] Stress_StrainPoints =new TreeMap[2];
        Stress_StrainPoints[0]=Stress_StrainPoints_Comp;
        Stress_StrainPoints[1]=Stress_StrainPoints_Ten;
        return Stress_StrainPoints;
    }
    /// 获取丁发兴-余志武本构的应力应变离散关系曲线，该曲线只包含工程应力和工程应变
    public static SortedMap<Integer, ParameterValue>[] GetStress_Strain(double fcr,double ftr, double Ec0)
    {
        SortedMap<Integer, ParameterValue> Stress_StrainPoints_Comp = new TreeMap<>();
        SortedMap<Integer, ParameterValue> Stress_StrainPoints_Ten = new TreeMap<>();
        //对ParameterValue初始化，即对所有字段均赋予了初值0，因此无需担心参数的不确定性问题。

        double epsilon_cr = GetEpsilon_cr_by_fcr(fcr);
        double epsilon_tr = GetEpsilon_tr_by_ftr(ftr);


        double strain = 0;
        double stress = 0;
        double strain_Increment = 0.000001;
        int count = 0;
        double stressMid = 0.5 * fcr;
        double Ac = GetAc_by_fcr(fcr, Ec0);
        double alfa_c = GetAlfa_c_by_fcr(fcr);

        //获取受压应力应变离散关系
        for (; strain <= 0.015; count++)
        {
            double x = strain / epsilon_cr;
            double y = 0;
            if (x <= 1)
            {
                y = (Ac * x - x * x) / (1 + (Ac - 2) * x);
            }
            if (x > 1)
            {
                y = x / (alfa_c * Math.pow(x - 1, 2) + x);
            }

            stress = y * fcr;

            ParameterValue nodePointParameterValue = new ParameterValue();
            nodePointParameterValue.strain = strain;
            nodePointParameterValue.stress = stress;
            nodePointParameterValue.Ec = Ec0;

            Stress_StrainPoints_Comp.put(count, nodePointParameterValue);

            if (strain > epsilon_cr && stress < stressMid)
            {
                strain_Increment *= 1.1;
            }

            //跳出循环的操作
            if (strain == 0.015)
            {
                break;
            }

            //
            //应注意有可能无法跳出操作，见上
            //
            if (0.015 - strain <= strain_Increment)
            {
                strain = 0.015;
            }
            else
            {
                strain += strain_Increment;
            }

        }

        //
        //获取受拉应力应变离散关系
        //
        strain = 0;
        stress = 0;
        count = 0;
        stressMid = 0.5 * ftr;
        strain_Increment = 0.000001;
        double At = GetAt_by_ftr(ftr, Ec0);
        double alfa_t = GetAlfa_t_by_ftr(ftr);

        for (; strain <= 0.1; count++)
        {
            double x = strain / epsilon_tr;
            double y = 0;
            if (x <= 1)
            {
                double a = At * x - x * x;
                double b = 1 + (At - 2) * x;
                y = a / b;
            }
            else if (x > 1)
            {
                double c = alfa_t * Math.pow(x - 1, 1.7) + x;
                y = x / c;
            }

            stress = y * ftr;

            ParameterValue nodePointParameterValue = new ParameterValue();
            nodePointParameterValue.strain = strain;
            nodePointParameterValue.stress = stress;
            nodePointParameterValue.Ec = Ec0;

            Stress_StrainPoints_Ten.put(count, nodePointParameterValue);

            if (strain > epsilon_tr && stress < stressMid)
            {
                strain_Increment *= 1.1;
            }
            //跳出循环的操作
            if (strain == 0.1)
            {
                break;
            }

            //
            //应注意有可能无法跳出操作，见上
            //
            if (0.1 - strain <= strain_Increment)
            {
                strain = 0.1;
            }
            else
            {
                strain += strain_Increment;
            }
        }

        SortedMap<Integer,ParameterValue>[] Stress_StrainPoints=new TreeMap[2];
        Stress_StrainPoints[0]=Stress_StrainPoints_Comp;
        Stress_StrainPoints[1]=Stress_StrainPoints_Ten;
        return Stress_StrainPoints;
    }
    /// 基于丁发兴-余志武方法获取处理后的多点本构；
    /// 屈服弹性模量Ecy=初始弹性模量Ec0。
    public static SortedMap<Integer, ParameterValue>[] GetStress_Strain_20Point(double fcur,double Ec0, double eta)
    {

        double fcr = Getfcr(fcur);
        double ftr = GetFtr(fcur);

        double epsilon_tr = GetEpsilon_tr(fcur);
        double epsilon_cr = GetEpsilon_cr_by_fcr(fcr);
        double alfa_t = GetAlfa_t(fcur);
        double alfa_c = GetAlfa_c(fcur);

        double strain = 0;
        double stress = 0;
        int number = 0;



        //定义返回值
        SortedMap<Integer, ParameterValue>[] stress_strain = new TreeMap[2];
        //定义受压本构返回值
        SortedMap<Integer, ParameterValue> stress_strain_Comp = new TreeMap<>();
        //定义受拉本构返回值
        SortedMap<Integer, ParameterValue> stress_strain_Ten = new TreeMap<>();


        //
        //获取受压本构特征点
        //
        //获取初始点0点
        ParameterValue stress_strainParameter = new ParameterValue();
        number = 0;
        stress_strain_Comp.put(number, stress_strainParameter);

        //获取屈服点
        stress = eta * fcr;
        strain = stress / Ec0;

        stress_strainParameter = new ParameterValue();

        stress_strainParameter.strain = strain;
        stress_strainParameter.stress = stress;
        stress_strainParameter.Ec = Ec0;
        number++;
        stress_strain_Comp.put(number, stress_strainParameter);

        //获取峰值点
        strain = epsilon_cr;
        stress = fcr;

        stress_strainParameter = new ParameterValue();

        stress_strainParameter.strain = strain;
        stress_strainParameter.stress = stress;
        stress_strainParameter.Ec = Ec0;
        number++;
        stress_strain_Comp.put(number, stress_strainParameter);


        //
        //获取极限应变
        //
        double epsilon_cu=0;
        for(strain=epsilon_cr;stress>0.4*fcr;strain+=0.000001)
        {
            stress = GetStress_Comp(fcur,Ec0, strain);
            epsilon_cu = strain;
        }

        //获取epsilon_cr~3*epsilon_cr之前的10个点
        double strain_Increment =(epsilon_cu-epsilon_cr)/ 5;
        strain = epsilon_cr + strain_Increment;
        double strainLimit = epsilon_cu;

        for (; strain <= strainLimit; strain += strain_Increment)
        {
            stress = GetStress_Comp(fcur,Ec0, strain);

            stress_strainParameter = new ParameterValue();

            stress_strainParameter.strain = strain;
            stress_strainParameter.stress = stress;
            stress_strainParameter.Ec = Ec0;
            number++;
            stress_strain_Comp.put(number, stress_strainParameter);

        }

        for (; strain <= 0.015; )
        {
            //跳出循环的操作
            if (strain == 0.015)
            {
                stress = GetStress_Comp(fcur,Ec0, strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ec0;
                number++;
                stress_strain_Comp.put(number, stress_strainParameter);
                break;
            }

            //
            //应注意有可能无法跳出操作，见上
            //
            strain_Increment *= 2;

            if (0.015 - strain <= strain_Increment)
            {
                strain = 0.015;
            }
            else
            {
                stress = GetStress_Comp(fcur,Ec0, strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ec0;
                number++;
                stress_strain_Comp.put(number, stress_strainParameter);

                strain += strain_Increment;
            }
        }


        //
        //获取受拉本构特征点
        //

        strain = 0;
        stress = 0;
        //获取初始点0点
        stress_strainParameter = new ParameterValue();
        number = 0;
        stress_strain_Ten.put(number, stress_strainParameter);

        //获取屈服点
        stress = 1.05 * ftr;
        strain = stress / Ec0;

        stress_strainParameter = new ParameterValue();

        stress_strainParameter.strain = strain;
        stress_strainParameter.stress = stress;
        stress_strainParameter.Ec = Ec0;
        number++;
        stress_strain_Ten.put(number, stress_strainParameter);

        //获取峰值点
        strain = epsilon_tr;
        stress = ftr;

        stress_strainParameter = new ParameterValue();

        stress_strainParameter.strain = strain;
        stress_strainParameter.stress = stress;
        stress_strainParameter.Ec = Ec0;
        number++;
        stress_strain_Ten.put(number, stress_strainParameter);



        //获取epsilon_tr~4epsilon_tr之间的15个点
        strain_Increment = epsilon_tr/ 4;
        strain = epsilon_tr + strain_Increment;
        strainLimit = 2* epsilon_tr;
        for (; strain <= strainLimit; strain+=strain_Increment)
        {
            stress = GetStress_Ten(fcur,Ec0,strain);

            stress_strainParameter = new ParameterValue();

            stress_strainParameter.strain = strain;
            stress_strainParameter.stress = stress;
            stress_strainParameter.Ec = Ec0;
            number++;
            stress_strain_Ten.put(number, stress_strainParameter);
        }

        for (; strain <= 0.1;)
        {


            strain_Increment *= 2;

            //跳出循环的操作
            if (strain == 0.1)
            {
                stress = GetStress_Ten(fcur,Ec0,strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ec0;
                number++;
                stress_strain_Ten.put(number, stress_strainParameter);

                break;
            }

            //
            //应注意有可能无法跳出操作，见上
            //

            if (0.1 - strain <= strain_Increment)
            {
                strain = 0.1;
            }
            else
            {
                stress = GetStress_Ten(fcur,Ec0, strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ec0;
                number++;
                stress_strain_Ten.put(number, stress_strainParameter);

                strain += strain_Increment;
            }
        }

        stress_strain[0] = stress_strain_Comp;
        stress_strain[1] = stress_strain_Ten;
        return stress_strain;
    }
    /// 基于丁发兴-余志武方法获取处理后的多点本构；
    /// 屈服弹性模量Ecy=初始弹性模量Ec0。
    public static SortedMap<Integer, ParameterValue>[] GetStress_Strain_20Point(double fcr,double ftr, double Ec0, double eta)
    {


        double epsilon_tr = GetEpsilon_tr_by_ftr(ftr);
        double epsilon_cr = GetEpsilon_cr_by_fcr(fcr);
        double alfa_t = GetAlfa_t_by_ftr(ftr);
        double alfa_c = GetAlfa_c_by_fcr(fcr);

        double strain = 0;
        double stress = 0;
        int number = 0;



        //定义返回值
        SortedMap<Integer, ParameterValue>[] stress_strain = new TreeMap[2];
        //定义受压本构返回值
        SortedMap<Integer, ParameterValue> stress_strain_Comp = new TreeMap<>();
        //定义受拉本构返回值
        SortedMap<Integer, ParameterValue> stress_strain_Ten = new TreeMap<>();


        //
        //获取受压本构特征点
        //
        //获取初始点0点
        ParameterValue stress_strainParameter = new ParameterValue();
        number = 0;
        stress_strain_Comp.put(number, stress_strainParameter);

        //获取屈服点
        stress = eta * fcr;
        strain = stress / Ec0;

        stress_strainParameter = new ParameterValue();

        stress_strainParameter.strain = strain;
        stress_strainParameter.stress = stress;
        stress_strainParameter.Ec = Ec0;
        number++;
        stress_strain_Comp.put(number, stress_strainParameter);

        //获取峰值点
        strain = epsilon_cr;
        stress = fcr;

        stress_strainParameter = new ParameterValue();

        stress_strainParameter.strain = strain;
        stress_strainParameter.stress = stress;
        stress_strainParameter.Ec = Ec0;
        number++;
        stress_strain_Comp.put(number, stress_strainParameter);


        //
        //获取极限应变
        //
        double epsilon_cu = 0;
        for (strain = epsilon_cr; stress > 0.4 * fcr; strain += 0.000001)
        {
            stress = GetStress_Comp_by_fcr(fcr, Ec0, strain);
            epsilon_cu = strain;
        }

        //获取epsilon_cr~3*epsilon_cr之前的10个点
        double strain_Increment = (epsilon_cu - epsilon_cr) / 80;
        strain = epsilon_cr + strain_Increment;
        double strainLimit = epsilon_cu;

        for (; strain <= strainLimit; strain += strain_Increment)
        {
            stress = GetStress_Comp_by_fcr(fcr, Ec0, strain);

            stress_strainParameter = new ParameterValue();

            stress_strainParameter.strain = strain;
            stress_strainParameter.stress = stress;
            stress_strainParameter.Ec = Ec0;
            number++;
            stress_strain_Comp.put(number, stress_strainParameter);

        }

        for (; strain <= 0.015; )
        {
            //跳出循环的操作
            if (strain == 0.015)
            {
                stress = GetStress_Comp_by_fcr(fcr, Ec0, strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ec0;
                number++;
                stress_strain_Comp.put(number, stress_strainParameter);
                break;
            }

            //
            //应注意有可能无法跳出操作，见上
            //
            strain_Increment *= 2;

            if (0.015 - strain <= strain_Increment)
            {
                strain = 0.015;
            }
            else
            {
                stress = GetStress_Comp_by_fcr(fcr, Ec0, strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ec0;
                number++;
                stress_strain_Comp.put(number, stress_strainParameter);

                strain += strain_Increment;
            }
        }


        //
        //获取受拉本构特征点
        //

        strain = 0;
        stress = 0;
        //获取初始点0点
        stress_strainParameter = new ParameterValue();
        number = 0;
        stress_strain_Ten.put(number, stress_strainParameter);

        //获取屈服点
        stress = 1.05 * ftr;
        strain = stress / Ec0;

        stress_strainParameter = new ParameterValue();

        stress_strainParameter.strain = strain;
        stress_strainParameter.stress = stress;
        stress_strainParameter.Ec = Ec0;
        number++;
        stress_strain_Ten.put(number, stress_strainParameter);

        //获取峰值点
        strain = epsilon_tr;
        stress = ftr;

        stress_strainParameter = new ParameterValue();

        stress_strainParameter.strain = strain;
        stress_strainParameter.stress = stress;
        stress_strainParameter.Ec = Ec0;
        number++;
        stress_strain_Ten.put(number, stress_strainParameter);



        //获取epsilon_tr~4epsilon_tr之间的15个点
        strain_Increment = epsilon_tr / 80;
        strain = epsilon_tr + strain_Increment;
        strainLimit = 2 * epsilon_tr;
        for (; strain <= strainLimit; strain += strain_Increment)
        {
            stress = GetStress_Ten_by_ftr(ftr, Ec0, strain);

            stress_strainParameter = new ParameterValue();

            stress_strainParameter.strain = strain;
            stress_strainParameter.stress = stress;
            stress_strainParameter.Ec = Ec0;
            number++;
            stress_strain_Ten.put(number, stress_strainParameter);
        }

        for (; strain <= 0.1; )
        {


            strain_Increment *= 2;

            //跳出循环的操作
            if (strain == 0.1)
            {
                stress = GetStress_Ten_by_ftr(ftr, Ec0, strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ec0;
                number++;
                stress_strain_Ten.put(number, stress_strainParameter);

                break;
            }

            //
            //应注意有可能无法跳出操作，见上
            //

            if (0.1 - strain <= strain_Increment)
            {
                strain = 0.1;
            }
            else
            {

                stress = GetStress_Ten_by_ftr(ftr, Ec0, strain);
                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ec0;
                number++;
                stress_strain_Ten.put(number, stress_strainParameter);

                strain += strain_Increment;
            }
        }

        stress_strain[0] = stress_strain_Comp;
        stress_strain[1] = stress_strain_Ten;
        return stress_strain;
    }

    /// 返回屈服点处的弹性模量Ecy；
    /// 屈服点弹性模量进行了处理，当Ecy小于Etr时，取为Etr；
    public static double GetEcy(double fcur,double Ec0, double eta)
    {
        double fcr = Getfcr(fcur);
        double ftr = GetFtr(fcur);
        double epsilon_cr = GetEpsilon_cr_by_fcr(fcr);
        double strain = 0;
        double strain_Increment = 0.000001;
        double stress = 0;
        double Ecy = 0;
        double stressLimt = eta * fcr;
        //根据eta*fcr的值确定受压屈服点，进一步确定Ecy
        for (; stress <= stressLimt; strain += strain_Increment)
        {
            stress = GetStress_Comp(fcur, Ec0, strain);
        }
        Ecy = stress / strain;

        //计算受拉峰值点的割线模量Etr
        double epsilon_tr = GetEpsilon_tr(fcur);
        double Etr = ftr / epsilon_tr;

        //判断，如果Ecy<Etr,则初始弹性模量取为Etr，以避免受拉区取值的时候，第一个点刚度小于峰值点刚度的情况
        if (Ecy < Etr)
        {
            Ecy = Etr;
        }
        return Ecy;
    }
    /// 返回屈服点处的弹性模量Ecy；
    /// 屈服点弹性模量进行了处理，当Ecy小于Etr时，取为Etr；
    /// Etr为峰值点弹性模量。
    public static double GetEcy(double fcr,double ftr, double Ec0, double eta)
    {
        double epsilon_cr = GetEpsilon_cr_by_fcr(fcr);
        double strain = 0;
        double strain_Increment = 0.000001;
        double stress = 0;
        double Ecy = 0;
        double stressLimt = eta * fcr;
        //根据eta*fcr的值确定受压屈服点，进一步确定Ecy
        for (; stress <= stressLimt; strain += strain_Increment)
        {
            stress = GetStress_Comp_by_fcr(fcr, Ec0, strain);
        }
        Ecy = stress / strain;

        //计算受拉峰值点的割线模量Etr
        double epsilon_tr = GetEpsilon_tr_by_ftr(ftr);
        double Etr = ftr / epsilon_tr;

        //判断，如果Ecy<Etr,则初始弹性模量取为Etr，以避免受拉区取值的时候，第一个点刚度小于峰值点刚度的情况
        if (Ecy < Etr)
        {
            Ecy = Etr;
        }
        return Ecy;
    }
    /// 返回丁发兴-余志武方法处理后的多点本构
    /// 屈服弹性模量Ecy使用屈服点处的割线模量
    /// 屈服点以后的应力应变曲线与原应力应变曲线重合
    /// 弹性模量较初始弹性模量Ec0偏小
    public static SortedMap<Integer, ParameterValue>[] GetStress_Strain_20Point_Ecy(double fcur, double Ec0, double eta)
    {
        double ftr = GetFtr(fcur);
        double fcr = Getfcr(fcur);
        double epsilon_tr = GetEpsilon_tr(fcur);
        double epsilon_cr = GetEpsilon_cr_by_fcr(fcr);
        //受拉下降段参数
        double alfa_t = GetAlfa_t(fcur);
        //受压下降段参数
        double alfa_c = GetAlfa_c(fcur);
        //极限压应变
        double epsilon_cu = GetEpsilon_cu(fcur,Ec0);

        double strain = 0;
        double stress = 0;
        int number = 0;
        double Ecy = 0;



        //定义返回值
        SortedMap<Integer, ParameterValue>[] stress_strain = new TreeMap[2];
        //定义受压本构返回值
        SortedMap<Integer, ParameterValue> stress_strain_Comp = new TreeMap<>();
        //定义受拉本构返回值
        SortedMap<Integer, ParameterValue> stress_strain_Ten = new TreeMap<>();

        //获取初始点0点
        ParameterValue stress_strainParameter = new ParameterValue();
        number = 0;
        stress_strain_Comp.put(number, stress_strainParameter);

        //获取屈服点和屈服点弹性模量

        Ecy = GetEcy(fcur,Ec0,eta);

        stress = eta * fcr;
        strain = stress / Ecy;

        stress_strainParameter = new ParameterValue();

        stress_strainParameter.strain = strain;
        stress_strainParameter.stress = stress;
        //弹性模量取屈服点处的割线模量Ecy;
        stress_strainParameter.Ec = Ecy;
        number++;
        stress_strain_Comp.put(number, stress_strainParameter);

        //获取峰值点
        strain = epsilon_cr;
        stress = fcr;

        stress_strainParameter = new ParameterValue();

        stress_strainParameter.strain = strain;
        stress_strainParameter.stress = stress;
        stress_strainParameter.Ec = Ecy;
        number++;
        stress_strain_Comp.put(number, stress_strainParameter);


        //获取2倍epsilon_cu之前的10个点

        double strain_Increment = (epsilon_cu - epsilon_cr) / 5;
        strain = epsilon_cr + strain_Increment;
        double strainLimit = 1.2 * epsilon_cu;

        for (; strain <= strainLimit; strain += strain_Increment)
        {
            stress = GetStress_Comp(fcur,Ec0, strain);

            stress_strainParameter = new ParameterValue();

            stress_strainParameter.strain = strain;
            stress_strainParameter.stress = stress;
            stress_strainParameter.Ec = Ecy;
            number++;
            stress_strain_Comp.put(number, stress_strainParameter);

        }

        for (; strain <= 0.015; )
        {
            strain_Increment *= 1.5;

            //跳出循环的操作
            if (strain == 0.015)
            {
                stress = GetStress_Comp(fcur,Ec0, strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ecy;
                number++;
                stress_strain_Comp.put(number, stress_strainParameter);

                break;
            }
            //
            //应注意有可能无法跳出操作，见上
            //
            if (0.015 - strain <= strain_Increment)
            {
                strain = 0.015;
            }
            else
            {
                stress = GetStress_Comp(fcur,Ec0, strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ecy;
                number++;
                stress_strain_Comp.put(number, stress_strainParameter);

                strain += strain_Increment;
            }
        }



        //获取受拉本构
        strain = 0;

        //获取初始点0点
        stress_strainParameter = new ParameterValue();
        number = 0;
        stress_strain_Ten.put(number, stress_strainParameter);

        //获取屈服点,如果屈服刚度等于受拉峰值割线刚度Etr，则屈服点就是受拉峰值点
        strain = epsilon_tr;
        stress = ftr;
        double Etr = stress / strain;
        if (Ecy > Etr)
        {
            strain = 0.95 * epsilon_tr;
            stress = strain * Ecy;

            stress_strainParameter = new ParameterValue();

            stress_strainParameter.strain = strain;
            stress_strainParameter.stress = stress;
            stress_strainParameter.Ec = Ecy;
            number++;
            stress_strain_Ten.put(number, stress_strainParameter);
        }
        //获取峰值点
        strain = epsilon_tr;
        stress = ftr;

        stress_strainParameter = new ParameterValue();

        stress_strainParameter.strain = strain;
        stress_strainParameter.stress = stress;
        stress_strainParameter.Ec = Ecy;
        number++;
        stress_strain_Ten.put(number, stress_strainParameter);


        //获取epsilon_c之前的10个点
        strain_Increment = epsilon_tr / 4;
        strain = epsilon_tr + strain_Increment;
        strainLimit = 2 * epsilon_tr;
        for (; strain <= strainLimit; strain += strain_Increment)
        {
            stress = GetStress_Ten(fcur,Ec0, strain);

            stress_strainParameter = new ParameterValue();

            stress_strainParameter.strain = strain;
            stress_strainParameter.stress = stress;
            stress_strainParameter.Ec = Ecy;
            number++;
            stress_strain_Ten.put(number, stress_strainParameter);
        }

        for (; strain <= 0.1; )
        {
            strain_Increment *= 2;

            //跳出循环的操作
            if (strain == 0.1)
            {
                stress = GetStress_Ten(fcur,Ec0,strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ecy;
                number++;
                stress_strain_Ten.put(number, stress_strainParameter);

                break;
            }
            //
            //应注意有可能无法跳出操作，见上
            //
            if (0.1 - strain <= strain_Increment)
            {
                strain = 0.1;
            }
            else
            {
                stress = GetStress_Ten(fcur,Ec0,strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ecy;
                number++;
                stress_strain_Ten.put(number, stress_strainParameter);

                strain += strain_Increment;
            }
        }

        stress_strain[0] = stress_strain_Comp;
        stress_strain[1] = stress_strain_Ten;
        return stress_strain;
    }
    /// 返回丁发兴-余志武方法处理后的多点本构
    /// 屈服弹性模量Ecy使用屈服点处的割线模量
    /// 屈服点以后的应力应变曲线与原应力应变曲线重合
    /// 弹性模量较初始弹性模量Ec0偏小
    public static SortedMap<Integer, ParameterValue>[] GetStress_Strain_20Point_Ecy(double fcr,double ftr, double Ec0, double eta)
    {

        double epsilon_tr = GetEpsilon_tr_by_ftr(ftr);
        double epsilon_cr = GetEpsilon_cr_by_fcr(fcr);
        //受拉下降段参数
        double alfa_t = GetAlfa_t_by_ftr(ftr);
        //受压下降段参数
        double alfa_c = GetAlfa_c_by_fcr(fcr);
        //极限压应变
        double epsilon_cu = GetEpsilon_cu_by_fcr(fcr, Ec0);

        double strain = 0;
        double stress = 0;
        int number = 0;
        double Ecy = 0;



        //定义返回值
        SortedMap<Integer, ParameterValue>[] stress_strain = new TreeMap[2];
        //定义受压本构返回值
        SortedMap<Integer, ParameterValue> stress_strain_Comp = new TreeMap<>();
        //定义受拉本构返回值
        SortedMap<Integer, ParameterValue> stress_strain_Ten = new TreeMap<>();

        //获取初始点0点
        ParameterValue stress_strainParameter = new ParameterValue();
        number = 0;
        stress_strain_Comp.put(number, stress_strainParameter);

        //获取屈服点和屈服点弹性模量

        Ecy = GetEcy(fcr,ftr, Ec0, eta);

        stress = eta * fcr;
        strain = stress / Ecy;

        stress_strainParameter = new ParameterValue();

        stress_strainParameter.strain = strain;
        stress_strainParameter.stress = stress;
        //弹性模量取屈服点处的割线模量Ecy;
        stress_strainParameter.Ec = Ecy;
        number++;
        stress_strain_Comp.put(number, stress_strainParameter);

        //获取峰值点
        strain = epsilon_cr;
        stress = fcr;

        stress_strainParameter = new ParameterValue();

        stress_strainParameter.strain = strain;
        stress_strainParameter.stress = stress;
        stress_strainParameter.Ec = Ecy;
        number++;
        stress_strain_Comp.put(number, stress_strainParameter);


        //获取2倍epsilon_cu之前的10个点

        double strain_Increment = (epsilon_cu - epsilon_cr) / 50;
        strain = epsilon_cr + strain_Increment;
        double strainLimit = 1.2 * epsilon_cu;

        for (; strain <= strainLimit; strain += strain_Increment)
        {
            stress = GetStress_Comp_by_fcr(fcr, Ec0, strain);

            stress_strainParameter = new ParameterValue();

            stress_strainParameter.strain = strain;
            stress_strainParameter.stress = stress;
            stress_strainParameter.Ec = Ecy;
            number++;
            stress_strain_Comp.put(number, stress_strainParameter);

        }

        for (; strain <= 0.015; )
        {
            strain_Increment *= 1.5;

            //跳出循环的操作
            if (strain == 0.015)
            {
                stress = GetStress_Comp_by_fcr(fcr, Ec0, strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ecy;
                number++;
                stress_strain_Comp.put(number, stress_strainParameter);

                break;
            }
            //
            //应注意有可能无法跳出操作，见上
            //
            if (0.015 - strain <= strain_Increment)
            {
                strain = 0.015;
            }
            else
            {
                stress = GetStress_Comp_by_fcr(fcr, Ec0, strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ecy;
                number++;
                stress_strain_Comp.put(number, stress_strainParameter);

                strain += strain_Increment;
            }
        }



        //获取受拉本构
        strain = 0;

        //获取初始点0点
        stress_strainParameter = new ParameterValue();
        number = 0;
        stress_strain_Ten.put(number, stress_strainParameter);

        //获取屈服点,如果屈服刚度等于受拉峰值割线刚度Etr，则屈服点就是受拉峰值点
        strain = epsilon_tr;
        stress = ftr;
        double Etr = stress / strain;
        if (Ecy > Etr)
        {
            strain = 0.95 * epsilon_tr;
            stress = strain * Ecy;

            stress_strainParameter = new ParameterValue();

            stress_strainParameter.strain = strain;
            stress_strainParameter.stress = stress;
            stress_strainParameter.Ec = Ecy;
            number++;
            stress_strain_Ten.put(number, stress_strainParameter);
        }
        //获取峰值点
        strain = epsilon_tr;
        stress = ftr;

        stress_strainParameter = new ParameterValue();

        stress_strainParameter.strain = strain;
        stress_strainParameter.stress = stress;
        stress_strainParameter.Ec = Ecy;
        number++;
        stress_strain_Ten.put(number, stress_strainParameter);


        //获取epsilon_c之前的10个点
        strain_Increment = epsilon_tr / 80;
        strain = epsilon_tr + strain_Increment;
        strainLimit = 2 * epsilon_tr;
        for (; strain <= strainLimit; strain += strain_Increment)
        {
            stress = GetStress_Ten_by_ftr(ftr, Ec0, strain);

            stress_strainParameter = new ParameterValue();

            stress_strainParameter.strain = strain;
            stress_strainParameter.stress = stress;
            stress_strainParameter.Ec = Ecy;
            number++;
            stress_strain_Ten.put(number, stress_strainParameter);
        }

        for (; strain <= 0.1; )
        {
            strain_Increment *= 2;

            //跳出循环的操作
            if (strain == 0.1)
            {
                //stress = GetStress_Ten(fcur, Ec0, strain);
                stress = GetStress_Ten_by_ftr(ftr, Ec0, strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ecy;
                number++;
                stress_strain_Ten.put(number, stress_strainParameter);

                break;
            }
            //
            //应注意有可能无法跳出操作，见上
            //
            if (0.1 - strain <= strain_Increment)
            {
                strain = 0.1;
            }
            else
            {
                //stress = GetStress_Ten(fcur, Ec0, strain);
                stress = GetStress_Ten_by_ftr(ftr, Ec0, strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ecy;
                number++;
                stress_strain_Ten.put(number, stress_strainParameter);

                strain += strain_Increment;
            }
        }

        stress_strain[0] = stress_strain_Comp;
        stress_strain[1] = stress_strain_Ten;
        return stress_strain;
    }
    /// 返回GB50010-2010方法处理后的多点本构；
    /// 该方法采用用户指定的屈服点刚度Ecy；
    /// 需要预先判断Ecy的取值是否合理；
    public static SortedMap<Integer, ParameterValue>[] GetStress_Strain_20Point_EcyUsedifined(double fcur, double Ec0, double Ecy, double eta)
    {
        double fcr = Getfcr(fcur);
        double ftr = GetFtr(fcur);

        double epsilon_tr = GetEpsilon_tr(fcur);
        double epsilon_cr = GetEpsilon_cr(fcur);
        double alfa_t = GetAlfa_t(fcur);
        double alfa_c = GetAlfa_c(fcur);
        double epsilon_cu = GetEpsilon_cu(fcur,Ec0);

        double strain = 0;
        double stress = 0;
        int number = 0;



        //定义返回值
        SortedMap<Integer, ParameterValue>[] stress_strain = new TreeMap[2];
        //定义受压本构返回值
        SortedMap<Integer, ParameterValue> stress_strain_Comp = new TreeMap<>();
        //定义受拉本构返回值
        SortedMap<Integer, ParameterValue> stress_strain_Ten = new TreeMap<>();

        //判断屈服点的合法性
        double Ecr = fcr / epsilon_cr;
        double Etr = ftr / epsilon_tr;
        if (Ecy < Ecr * 1.01 || Ecy < Etr * 1.01)
        {
//            MessageBox.Show("请输入合法的Ecy，Ecy不应小于Ecr*1.01和Etr*1.01！\r\n其中Ecr：峰值受压点的割线模量；\r\nEtr：峰值受拉点的割线模量。", "提示", MessageBoxButtons.OK, MessageBoxIcon.Error);
//                goto endLoop;
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("请输入合法的Ecy，Ecy不应小于Ecr*1.01和Etr*1.01！\r\n其中Ecr：峰值受压点的割线模量；\r\nEtr：峰值受拉点的割线模量。");
            alert.showAndWait();

            return stress_strain;
        }

        //获取初始点0点
        ParameterValue stress_strainParameter = new ParameterValue();
        number = 0;
        stress_strain_Comp.put(number, stress_strainParameter);

        //获得屈服点
        stress = eta * fcr;
        strain = stress / Ecy;

        stress_strainParameter = new ParameterValue();

        stress_strainParameter.strain = strain;
        stress_strainParameter.stress = stress;
        //弹性模量取屈服点处的割线模量Ecy;
        stress_strainParameter.Ec = Ecy;
        number++;
        stress_strain_Comp.put(number, stress_strainParameter);

        //获取峰值点
        strain = epsilon_cr;
        stress = fcr;

        stress_strainParameter = new ParameterValue();

        stress_strainParameter.strain = strain;
        stress_strainParameter.stress = stress;
        stress_strainParameter.Ec = Ecy;
        number++;
        stress_strain_Comp.put(number, stress_strainParameter);


        //获取2倍epsilon_cu之前的10个点

        double strain_Increment = (epsilon_cu - epsilon_cr) / 5;
        strain = epsilon_cr + strain_Increment;
        double strainLimit = 1.2 * epsilon_cu;

        for (; strain <= strainLimit; strain += strain_Increment)
        {
            stress = GetStress_Comp(fcur,Ec0, strain);

            stress_strainParameter = new ParameterValue();

            stress_strainParameter.strain = strain;
            stress_strainParameter.stress = stress;
            stress_strainParameter.Ec = Ecy;
            number++;
            stress_strain_Comp.put(number, stress_strainParameter);

        }

        for (; strain <= 0.015; )
        {
            strain_Increment *= 1.5;

            //跳出循环的操作
            if (strain == 0.015)
            {
                stress = GetStress_Comp(fcur,Ec0, strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ecy;
                number++;
                stress_strain_Comp.put(number, stress_strainParameter);

                break;
            }
            //
            //应注意有可能无法跳出操作，见上
            //
            if (0.015 - strain <= strain_Increment)
            {
                strain = 0.015;
            }
            else
            {
                stress = GetStress_Comp(fcur,Ec0, strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ecy;
                number++;
                stress_strain_Comp.put(number, stress_strainParameter);

                strain += strain_Increment;
            }
        }



        //获取受拉本构
        strain = 0;

        //获取初始点0点
        stress_strainParameter = new ParameterValue();
        number = 0;
        stress_strain_Ten.put(number, stress_strainParameter);

        //获取屈服点,如果屈服刚度等于受拉峰值割线刚度Etr，则屈服点就是受拉峰值点
        //这里不要求屈服点一定要大于应力应变的峰值点，与建科院本构有稍微区别

        if (Ecy > Etr)
        {
            strain = 0.95 * epsilon_tr;
            stress = strain * Ecy;

            stress_strainParameter = new ParameterValue();

            stress_strainParameter.strain = strain;
            stress_strainParameter.stress = stress;
            stress_strainParameter.Ec = Ecy;
            number++;
            stress_strain_Ten.put(number, stress_strainParameter);
        }

        //获取峰值点
        strain = epsilon_tr;
        stress = ftr;

        stress_strainParameter = new ParameterValue();

        stress_strainParameter.strain = strain;
        stress_strainParameter.stress = stress;
        stress_strainParameter.Ec = Ecy;
        number++;
        stress_strain_Ten.put(number, stress_strainParameter);


        //获取epsilon_c之前的10个点
        strain_Increment = epsilon_tr / 5;
        strain = epsilon_tr + strain_Increment;
        strainLimit = 2 * epsilon_tr;
        for (; strain <= strainLimit; strain += strain_Increment)
        {
            stress = GetStress_Ten(fcur,Ec0, strain);

            stress_strainParameter = new ParameterValue();

            stress_strainParameter.strain = strain;
            stress_strainParameter.stress = stress;
            stress_strainParameter.Ec = Ecy;
            number++;
            stress_strain_Ten.put(number, stress_strainParameter);
        }

        for (; strain <= 0.1; )
        {

            strain_Increment *= 2;
            //跳出循环的操作
            if (strain == 0.1)
            {
                stress = GetStress_Ten(fcur, Ec0, strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ecy;
                number++;
                stress_strain_Ten.put(number, stress_strainParameter);

                break;
            }
            //
            //应注意有可能无法跳出操作，见上
            //
            if (0.1 - strain <= strain_Increment)
            {
                strain = 0.1;
            }
            else
            {
                stress = GetStress_Ten(fcur,Ec0, strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ecy;
                number++;
                stress_strain_Ten.put(number, stress_strainParameter);

                strain += strain_Increment;
            }
        }

        stress_strain[0] = stress_strain_Comp;
        stress_strain[1] = stress_strain_Ten;

        endLoop:
        return stress_strain;
    }

    public static SortedMap<Integer, ParameterValue>[] GetStress_Strain_20Point_EcyUsedifined(double fcr,double ftr, double Ec0, double Ecy, double eta)
    {

        double epsilon_tr = GetEpsilon_tr_by_ftr(ftr);
        double epsilon_cr = GetEpsilon_cr_by_fcr(fcr);
        double alfa_t = GetAlfa_t_by_ftr(ftr);
        double alfa_c = GetAlfa_c_by_fcr(fcr);
        double epsilon_cu = GetEpsilon_cu_by_fcr(fcr, Ec0);

        double strain = 0;
        double stress = 0;
        int number = 0;



        //定义返回值
        SortedMap<Integer, ParameterValue>[] stress_strain = new TreeMap[2];
        //定义受压本构返回值
        SortedMap<Integer, ParameterValue> stress_strain_Comp = new TreeMap<>();
        //定义受拉本构返回值
        SortedMap<Integer, ParameterValue> stress_strain_Ten = new TreeMap<>();

        //判断屈服点的合法性
        double Ecr = fcr / epsilon_cr;
        double Etr = ftr / epsilon_tr;
        if (Ecy < Ecr * 1.01 || Ecy < Etr * 1.01)
        {
//            MessageBox.Show("请输入合法的Ecy，Ecy不应小于Ecr*1.01和Etr*1.01！\r\n其中Ecr：峰值受压点的割线模量；\r\nEtr：峰值受拉点的割线模量。", "提示", MessageBoxButtons.OK, MessageBoxIcon.Error);
//                goto endLoop;
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("请输入合法的Ecy，Ecy不应小于Ecr*1.01和Etr*1.01！\r\n其中Ecr：峰值受压点的割线模量；\r\nEtr：峰值受拉点的割线模量。");
            alert.showAndWait();
            return stress_strain;
        }

        //获取初始点0点
        ParameterValue stress_strainParameter = new ParameterValue();
        number = 0;
        stress_strain_Comp.put(number, stress_strainParameter);

        //获得屈服点
        stress = eta * fcr;
        strain = stress / Ecy;

        stress_strainParameter = new ParameterValue();

        stress_strainParameter.strain = strain;
        stress_strainParameter.stress = stress;
        //弹性模量取屈服点处的割线模量Ecy;
        stress_strainParameter.Ec = Ecy;
        number++;
        stress_strain_Comp.put(number, stress_strainParameter);

        //获取峰值点
        strain = epsilon_cr;
        stress = fcr;

        stress_strainParameter = new ParameterValue();

        stress_strainParameter.strain = strain;
        stress_strainParameter.stress = stress;
        stress_strainParameter.Ec = Ecy;
        number++;
        stress_strain_Comp.put(number, stress_strainParameter);


        //获取2倍epsilon_cu之前的10个点

        double strain_Increment = (epsilon_cu - epsilon_cr) / 80;
        strain = epsilon_cr + strain_Increment;
        double strainLimit = 1.2 * epsilon_cu;

        for (; strain <= strainLimit; strain += strain_Increment)
        {
            stress = GetStress_Comp_by_fcr(fcr, Ec0, strain);

            stress_strainParameter = new ParameterValue();

            stress_strainParameter.strain = strain;
            stress_strainParameter.stress = stress;
            stress_strainParameter.Ec = Ecy;
            number++;
            stress_strain_Comp.put(number, stress_strainParameter);

        }

        for (; strain <= 0.015; )
        {
            strain_Increment *= 1.5;

            //跳出循环的操作
            if (strain == 0.015)
            {
                //stress = GetStress_Comp(fcur, Ec0, strain);
                stress = GetStress_Comp_by_fcr(fcr, Ec0, strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ecy;
                number++;
                stress_strain_Comp.put(number, stress_strainParameter);

                break;
            }
            //
            //应注意有可能无法跳出操作，见上
            //
            if (0.015 - strain <= strain_Increment)
            {
                strain = 0.015;
            }
            else
            {
                //stress = GetStress_Comp(fcur, Ec0, strain);
                stress = GetStress_Comp_by_fcr(fcr, Ec0, strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ecy;
                number++;
                stress_strain_Comp.put(number, stress_strainParameter);

                strain += strain_Increment;
            }
        }



        //获取受拉本构
        strain = 0;

        //获取初始点0点
        stress_strainParameter = new ParameterValue();
        number = 0;
        stress_strain_Ten.put(number, stress_strainParameter);

        //获取屈服点,如果屈服刚度等于受拉峰值割线刚度Etr，则屈服点就是受拉峰值点
        //这里不要求屈服点一定要大于应力应变的峰值点，与建科院本构有稍微区别

        if (Ecy > Etr)
        {
            strain = 0.95 * epsilon_tr;
            stress = strain * Ecy;

            stress_strainParameter = new ParameterValue();

            stress_strainParameter.strain = strain;
            stress_strainParameter.stress = stress;
            stress_strainParameter.Ec = Ecy;
            number++;
            stress_strain_Ten.put(number, stress_strainParameter);
        }

        //获取峰值点
        strain = epsilon_tr;
        stress = ftr;

        stress_strainParameter = new ParameterValue();

        stress_strainParameter.strain = strain;
        stress_strainParameter.stress = stress;
        stress_strainParameter.Ec = Ecy;
        number++;
        stress_strain_Ten.put(number, stress_strainParameter);


        //获取epsilon_c之前的10个点
        strain_Increment = epsilon_tr / 80;
        strain = epsilon_tr + strain_Increment;
        strainLimit = 2 * epsilon_tr;
        for (; strain <= strainLimit; strain += strain_Increment)
        {
            stress = GetStress_Ten_by_ftr(ftr, Ec0, strain);


            stress_strainParameter = new ParameterValue();

            stress_strainParameter.strain = strain;
            stress_strainParameter.stress = stress;
            stress_strainParameter.Ec = Ecy;
            number++;
            stress_strain_Ten.put(number, stress_strainParameter);
        }

        for (; strain <= 0.1; )
        {

            strain_Increment *= 2;
            //跳出循环的操作
            if (strain == 0.1)
            {
                //stress = GetStress_Ten(fcur, Ec0, strain);
                stress = GetStress_Ten_by_ftr(ftr, Ec0, strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ecy;
                number++;
                stress_strain_Ten.put(number, stress_strainParameter);

                break;
            }
            //
            //应注意有可能无法跳出操作，见上
            //
            if (0.1 - strain <= strain_Increment)
            {
                strain = 0.1;
            }
            else
            {
                //stress = GetStress_Ten(fcur, Ec0, strain);
                stress = GetStress_Ten_by_ftr(ftr, Ec0, strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ecy;
                number++;
                stress_strain_Ten.put(number, stress_strainParameter);

                strain += strain_Increment;
            }
        }

        stress_strain[0] = stress_strain_Comp;
        stress_strain[1] = stress_strain_Ten;

        return stress_strain;
    }


}