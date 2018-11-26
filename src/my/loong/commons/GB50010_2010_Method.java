package my.loong.commons;


import my.loong.model.ParameterValue;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @program: CreateMaterial
 * @description:
 * @AUTHOR: tlw
 * @create: 2018-11-20 15:35
 */
public class GB50010_2010_Method {
        /// 返回规范50010-2010的初始弹性模量Ec0
        public static double GetEc0(double fcuk)
        {
            return (Math.pow(10, 5) / (2.2 + 34.74 / fcuk));
        }
        /// 返回GB50010-2010单轴受压应力应变曲线峰值应变
        public static double GetEpsilon_cr(double fcr)
        {
            return (700 + 172 * Math.sqrt(fcr)) * 1E-6;
        }

        /// 返回GB50010-2010单轴受拉应力应变曲线峰值应变
        public static double GetEpsilon_tr(double ftr)
        {
            return Math.pow(ftr, 0.54) * 65 * 1E-6;
        }
        /// 返回GB50010-2010单轴受压应力应变曲线下降段参数Alfa_c
        public static double GetAlfa_c(double fcr)
        {
            return 0.157 * Math.pow(fcr, 0.785) - 0.905;
        }
        /// 返回GB50010-2010单轴受拉应力应变曲线下降段参数Alfa_t
        public static double GetAlfa_t(double ftr)
        {
            return 0.312 * Math.pow(ftr, 2);
        }
        /// 返回GB50010-2010单轴受压应力应变曲线与0.5fcr对应的极限压应变epsilon_cu
        public static double GetEpsilon_cu(double epsilon_cr, double alfa_c)
        {
            double a = 2 * alfa_c;
            double b = 1 / a * (1 + a + Math.sqrt(1 + 2 * a));
            return b * epsilon_cr;
        }

        //
        //建立应力-应变关系函数，已知工程应变，返回工程应力结果

        /// 已知单个点的受压应变求取单个点的受压应力
        public static double GetStress_Comp(double fcr, double Ec0, double strain)
        {
            double stress = 0;

            double epsilon_cr = GetEpsilon_cr(fcr);
            double alfa_c = GetAlfa_c(fcr);
            double x = strain / epsilon_cr;
            double pc = fcr / (Ec0 * epsilon_cr);
            double n = 1 / (1 - pc);

            double dc = 0;
            if(x>1)
            {
                dc = 1 - pc / (alfa_c * Math.pow(x - 1, 2) + x);
            }
            else if(x<=1&&x>=0)
            {
                dc = 1 - pc * n / (n - 1 + Math.pow(x, n));
            }
            else
            {
                //提示信息显示
                //MessageBox.Show(string.Format("在获取与受压应变strain={0}对应的受压应力时，strain<0，非法！", strain), "提示", MessageBoxButtons.OK, MessageBoxIcon.Error);

            }

            stress = (1 - dc) * Ec0 * strain;
            return stress;
        }

        /// 已知单个点的受压应变求取单个点的受拉应力
        public static double GetStress_Ten(double ftr,double Ec0, double strain)
        {
            double stress = 0;

            double epsilon_tr = GetEpsilon_tr(ftr);
            double alfa_t = GetAlfa_t(ftr);
            double x = strain / epsilon_tr;
            double pt = ftr / (Ec0 * epsilon_tr);

            double dt = 0;
            if (x > 1)
            {
                dt = 1 - pt / (alfa_t * Math.pow(x - 1, 1.7) + x);
            }
            else if (x <= 1 && x >= 0)
            {
                dt = 1 - pt * (1.2 - 0.2 * Math.pow(x, 5));
            }
            else
            {
                //提示信息显示
                //MessageBox.Show(string.Format("在获取与受拉应变strain={0}对应的受拉应力时，strain<0，非法！", strain), "提示", MessageBoxButtons.OK, MessageBoxIcon.Error);

            }

            stress = (1 - dt) * Ec0 * strain;
            return stress;
        }
        /// 返回以ParameterValues格式的应力应变曲线，该曲线只包含有效的工程应力和工程应变
        public static SortedMap<Integer, ParameterValue>[]  GetStress_Strain(double fcr, double ftr, double Ec0)
        {
            SortedMap<Integer, ParameterValue> Stress_StrainPoints_Comp =new TreeMap<Integer, ParameterValue>();
            SortedMap<Integer, ParameterValue> Stress_StrainPoints_Ten = new TreeMap<Integer, ParameterValue>();
            //对ParameterValue初始化，即对所有字段均赋予了初值0，因此无需担心参数的不确定性问题。

            //
            //预生成与受压应力应变曲线有关的参数
            //
            double epsilon_cr = GetEpsilon_cr(fcr);
            double alfa_c = GetAlfa_c(fcr);
            double pc = fcr / (Ec0 * epsilon_cr);
            double n = 1 / (1 - pc);

            double strain = 0;
            double stress;

            //生成受压应力-应变曲线
            double strain_Increment = 0.000001;
            int count = 0;
            double stressMid = 0.5 * fcr;
            for (; strain <= 0.015; count++)
            {
                double x = strain / epsilon_cr;
                double dc;
                //求解受压损伤dc
                if (x <= 1)
                {
                    dc = 1 - pc * n / (n - 1 + Math.pow(x, n));
                }
                else
                {
                    dc = 1 - pc / (alfa_c * Math.pow(x - 1, 2) + x);
                }

                stress = (1 - dc) * Ec0 * strain;


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
            //预生成与受拉应力应变曲线有关的参数
            //
            double epsilon_tr = GetEpsilon_tr(ftr);
            double alfa_t = GetAlfa_t(ftr);
            double pt = ftr / (Ec0 * epsilon_tr);
            count = 0;
            strain = 0;
            strain_Increment = 0.000001;

            stressMid = 0.5 * ftr;

            //生成受拉应力应变曲线
            for (; strain <= 0.1; count++)
            {
                double x = strain / epsilon_tr;
                double dt;
                if (x <= 1)
                {
                    dt = 1 - pt * (1.2 - 0.2 * Math.pow(x, 5));
                }
                else
                {
                    dt = 1 - pt / (alfa_t * Math.pow(x - 1, 1.7) + x);
                }

                stress = (1 - dt) * Ec0 * strain;


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


            SortedMap<Integer, ParameterValue>[] Stress_StrainPoints=new TreeMap[2];
            Stress_StrainPoints[0]=Stress_StrainPoints_Comp;
            Stress_StrainPoints[1]=Stress_StrainPoints_Ten;

            return Stress_StrainPoints;
        }

        /// 返回GB50010-2010规范的受拉损伤参数dt
        public static double GetDt(double ftr, double Ec0, double epsilon_tr, double strain)
        {
            double alfa_t = GetAlfa_t(ftr);

            double pt = ftr / (Ec0 * epsilon_tr);
            double x = strain / epsilon_tr;
            double dt;
            if (x <= 1)
            {
                dt = 1 - pt * (1.2 - 0.2 * Math.pow(x, 5));
            }
            else
            {
                dt = 1 - pt / (alfa_t * Math.pow(x - 1, 1.7) + x);
            }

            return dt;
        }

        /// 返回GB50010-2010规范的受压损伤参数dc
        public static double GetDc(double fcr, double Ec0, double epsilon_cr, double strain)
        {
            double alfa_c = GetAlfa_c(fcr);

            double x = strain / epsilon_cr;
            double pc = fcr / (Ec0 * epsilon_cr);
            double n = 1/(1-pc);
            double dc;

            if (x <= 1)
            {
                dc = 1 - pc * n / (n - 1 + Math.pow(x, n));
            }
            else
            {
                dc = 1 - pc / (alfa_c * Math.pow(x - 1, 2) + x);
            }

            return dc;
        }
        /// 返回GB50010-2010方法处理后的多点本构
        /// ABAQUS屈服点弹性模量来源于初始弹性模量
        /// 该方法取得的弹性模量结果较大，但与建科院本构取得弹性模量方法一致
        public static SortedMap<Integer, ParameterValue>[] GetStress_Strain_20Point(double fcr,double ftr, double Ec0, double eta)
        {

            double epsilon_tr = GetEpsilon_tr(ftr);
            double epsilon_cr = GetEpsilon_cr(fcr);
            double alfa_t = GetAlfa_t(ftr);
            double alfa_c = GetAlfa_c(fcr);
            double epsilon_cu = GetEpsilon_cu(epsilon_cr, alfa_c);

            double strain = 0;
            double stress = 0;
            int number = 0;



            //定义返回值
            SortedMap<Integer, ParameterValue>[] stress_strain = new TreeMap[2];
            //定义受压本构返回值
            SortedMap<Integer, ParameterValue> stress_strain_Comp = new TreeMap<Integer, ParameterValue>();
            //定义受拉本构返回值
            SortedMap<Integer, ParameterValue> stress_strain_Ten = new TreeMap<Integer, ParameterValue>();

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


            //获取2倍epsilon_cu之前的10个点

            double strain_Increment = (epsilon_cu - epsilon_cr) / 50;
            strain = epsilon_cr+strain_Increment;
            double strainLimit = 1.2 * epsilon_cu;

            for (; strain <=strainLimit;strain+=strain_Increment)
            {
                stress = GetStress_Comp(fcr, Ec0, strain);

                stress_strainParameter = new ParameterValue();

                stress_strainParameter.strain = strain;
                stress_strainParameter.stress = stress;
                stress_strainParameter.Ec = Ec0;
                number++;
                stress_strain_Comp.put(number, stress_strainParameter);

            }

            for (; strain <= 0.015; )
            {
                //stress = GetStress_Comp(fcr, Ec0, strain);

                //stress_strainParameter = new ParameterValue();

                //stress_strainParameter.strain = strain;
                //stress_strainParameter.stress = stress;
                //stress_strainParameter.Ec = Ec0;
                //number++;
                //stress_strain_Comp.Add(number, stress_strainParameter);


                strain_Increment *= 1.5;

                //跳出循环的操作
                if (strain == 0.015)
                {
                    stress = GetStress_Comp(fcr, Ec0, strain);

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
                if (0.015 - strain <= strain_Increment)
                {
                    strain = 0.015;
                }
                else
                {
                    stress = GetStress_Comp(fcr, Ec0, strain);

                    stress_strainParameter = new ParameterValue();

                    stress_strainParameter.strain = strain;
                    stress_strainParameter.stress = stress;
                    stress_strainParameter.Ec = Ec0;
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

            //获取屈服点
            stress = 1.05* ftr;
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


            //获取epsilon_c之前的10个点
            strain_Increment = epsilon_tr / 80;
            strain = epsilon_tr + strain_Increment;
            strainLimit = 2* epsilon_tr;
            for (; strain <= strainLimit; strain += strain_Increment)
            {
                stress = GetStress_Ten(ftr, Ec0, strain);

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
                    stress = GetStress_Ten(ftr, Ec0, strain);

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
                    stress = GetStress_Ten(ftr, Ec0, strain);

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
        /// Etr为峰值点弹性模量。
        public static double GetEcy(double fcr,double ftr, double Ec0,double eta)
        {
            double epsilon_cr = GetEpsilon_cr(fcr);
            double strain=0;
            double strain_Increment=0.000001;
            double stress=0;
            double Ecy=0;
            double stressLimt=eta*fcr;
            //根据eta*fcr的值确定受压屈服点，进一步确定Ecy
            for(;stress<=stressLimt;strain+=strain_Increment)
            {
                stress = GetStress_Comp(fcr, Ec0, strain);
            }
            Ecy = stress / strain;

            //计算受拉峰值点的割线模量Etr
            double epsilon_tr = GetEpsilon_tr(ftr);
            double Etr = ftr / epsilon_tr;

            //判断，如果Ecy<Etr,则初始弹性模量取为Etr，以避免受拉区取值的时候，第一个点刚度小于峰值点刚度的情况
            if(Ecy<Etr)
            {
                Ecy = Etr;
            }
            return Ecy;
        }
        /// 返回GB50010-2010方法处理后的多点本构
        /// 该方法使用屈服点处的割线模量
        /// 屈服点以后的应力应变曲线与原应力应变曲线重合
        /// 弹性模量较初始弹性模量Ec0偏小
        public static SortedMap<Integer, ParameterValue>[] GetStress_Strain_20Point_Ecy(double fcr, double ftr, double Ec0, double eta)
        {

            double epsilon_tr = GetEpsilon_tr(ftr);
            double epsilon_cr = GetEpsilon_cr(fcr);
            double alfa_t = GetAlfa_t(ftr);
            double alfa_c = GetAlfa_c(fcr);
            double epsilon_cu = GetEpsilon_cu(epsilon_cr, alfa_c);

            double strain = 0;
            double stress = 0;
            int number = 0;
            double Ecy = 0;



            //定义返回值
            SortedMap<Integer, ParameterValue>[] stress_strain = new TreeMap[2];
            //定义受压本构返回值
            SortedMap<Integer, ParameterValue> stress_strain_Comp = new TreeMap<Integer, ParameterValue>();
            //定义受拉本构返回值
            SortedMap<Integer, ParameterValue> stress_strain_Ten = new TreeMap<Integer, ParameterValue>();

            //获取初始点0点
            ParameterValue stress_strainParameter = new ParameterValue();
            number = 0;
            stress_strain_Comp.put(number, stress_strainParameter);

            //获取屈服点和屈服点弹性模量

            Ecy = GetEcy(fcr, ftr,Ec0, eta);
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
                stress = GetStress_Comp(fcr, Ec0, strain);

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
                    stress = GetStress_Comp(fcr, Ec0, strain);

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
                    stress = GetStress_Comp(fcr, Ec0, strain);

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


            //获取epsilon_tu之前的10个点
            strain_Increment = epsilon_tr / 80;
            strain = epsilon_tr + strain_Increment;
            strainLimit = 2 * epsilon_tr;
            for (; strain <= strainLimit; strain += strain_Increment)
            {
                stress = GetStress_Ten(ftr, Ec0, strain);

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
                    stress = GetStress_Ten(ftr, Ec0, strain);

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
                    stress = GetStress_Ten(ftr, Ec0, strain);

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

        /// 返回GB50010-2010方法处理后的多点本构
        /// 该方法采用用户指定的屈服点刚度Ecy
        /// 需要预先判断Ecy的取值是否合理。
        /// Ecy不应该大于Ec0，且Ecy不应该小于Ecr*1.01和Etr；
        public static SortedMap<Integer, ParameterValue>[] GetStress_Strain_20Point_EcyUsedifined(double fcr, double ftr, double Ec0,double Ecy, double eta)
        {

            double epsilon_tr = GetEpsilon_tr(ftr);
            double epsilon_cr = GetEpsilon_cr(fcr);
            double alfa_t = GetAlfa_t(ftr);
            double alfa_c = GetAlfa_c(fcr);
            double epsilon_cu = GetEpsilon_cu(epsilon_cr, alfa_c);

            double strain = 0;
            double stress = 0;
            int number = 0;



            //定义返回值
            SortedMap<Integer, ParameterValue>[] stress_strain = new TreeMap[2];
            //定义受压本构返回值
            SortedMap<Integer, ParameterValue> stress_strain_Comp = new TreeMap<Integer, ParameterValue>();
            //定义受拉本构返回值
            SortedMap<Integer, ParameterValue> stress_strain_Ten = new TreeMap<Integer, ParameterValue>();

            //判断屈服点的合法性
            double Ecr = fcr / epsilon_cr;
            double Etr = ftr / epsilon_tr;
            if (Ecy < Ecr * 1.01 || Ecy < Etr*1.01)
            {
                return stress_strain;//直接返回了
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

            double strain_Increment = (epsilon_cu - epsilon_cr) / 50;
            strain = epsilon_cr + strain_Increment;
            double strainLimit = 1.2 * epsilon_cu;

            for (; strain <= strainLimit; strain += strain_Increment)
            {
                stress = GetStress_Comp(fcr, Ec0, strain);

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
                    stress = GetStress_Comp(fcr, Ec0, strain);

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
                    stress = GetStress_Comp(fcr, Ec0, strain);

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

            if(Ecy>Etr)
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
                stress = GetStress_Ten(ftr, Ec0, strain);

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
                    stress = GetStress_Ten(ftr, Ec0, strain);

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
                    stress = GetStress_Ten(ftr, Ec0, strain);

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
}