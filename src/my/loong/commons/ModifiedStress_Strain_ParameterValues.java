package my.loong.commons;

import my.loong.model.ParameterValue;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @program: CreateMaterial
 * @description:
 * @AUTHOR: tlw
 * @create: 2018-11-21 22:01
 */
public class ModifiedStress_Strain_ParameterValues {

    /// 按照能量等效原理计算损伤，并更新stress_strain
    public static SortedMap<Integer, ParameterValue>[] ToModify_DamageByErengyMethod(SortedMap<Integer, ParameterValue>[] stress_strain)
    {
        SortedMap<Integer, ParameterValue> stress_strain_Comp = stress_strain[0];
        SortedMap<Integer, ParameterValue> stress_strain_Ten = stress_strain[1];

        SortedMap<Integer, ParameterValue> stress_strain_Comp_Modified = new TreeMap<>();
        SortedMap<Integer, ParameterValue> stress_strain_Ten_Modified = new TreeMap<>();

        ParameterValue pointParameterValue = new ParameterValue();

        double Ec_Real = 0;


        for(Map.Entry<Integer,ParameterValue> item:stress_strain_Comp.entrySet())
        {
            if (item.getKey() == 0)
            {
                stress_strain_Comp_Modified.put(item.getKey(), item.getValue());
            }
            else
            {
                pointParameterValue = new ParameterValue();
                int count = item.getKey();
                double stress = item.getValue().stress;
                double strain = item.getValue().strain;
                double Ec = item.getValue().Ec;
                double damage = GetDamage.ByEnergyBalanceMethod(stress, Ec, strain);
                double strain_InElastic = strain - stress / Ec;
                double strain_Plastic = strain - stress / (Ec * (1 - damage));
                if (item.getKey() == 1)
                {
                    if (strain_InElastic < 0&&strain_InElastic>(-1e-10))
                    {
                        strain_InElastic = 0;
                    }
                    if (strain_Plastic < 0 && strain_Plastic > (-1e-10))
                    {
                        strain_Plastic = 0;
                    }
                }
                double stress_Real = stress * (1 + strain);
                double strain_Real = Math.log(1 + strain);
                if (item.getKey() == 1)
                {
                    Ec_Real = stress_Real / strain_Real;
                }
                double strain_InElastic_Real = strain_Real - stress_Real / Ec_Real;
                double damage_Real = GetDamage.ByEnergyBalanceMethod(stress_Real, Ec_Real, strain_Real);
                double strain_Plastic_Real = strain_Real - stress_Real / (Ec_Real * (1 - damage_Real));

                pointParameterValue.strain = strain;
                pointParameterValue.stress = stress;
                pointParameterValue.strain_InElastic = strain_InElastic;
                pointParameterValue.strain_Plastic = strain_Plastic;
                pointParameterValue.damage = damage;
                pointParameterValue.Ec = Ec;
                pointParameterValue.strain_Real = strain_Real;
                pointParameterValue.stress_Real = stress_Real;
                pointParameterValue.strain_InElastic_Real = strain_InElastic_Real;
                pointParameterValue.strain_Plastic_Real = strain_Plastic_Real;
                pointParameterValue.damage_Real = damage_Real;
                pointParameterValue.Ec_Real = Ec_Real;

                stress_strain_Comp_Modified.put(count, pointParameterValue);
            }

        }

        for(Map.Entry<Integer,ParameterValue> item:stress_strain_Ten.entrySet())
        {
            if (item.getKey() == 0)
            {
                stress_strain_Ten_Modified.put(item.getKey(), item.getValue());
            }
            else
            {
                pointParameterValue = new ParameterValue();
                int count = item.getKey();
                double stress = item.getValue().stress;
                double strain = item.getValue().strain;
                double Ec = item.getValue().Ec;
                double damage = GetDamage.ByEnergyBalanceMethod(stress, Ec, strain);
                double strain_InElastic = strain - stress / Ec;
                double strain_Plastic = strain - stress / (Ec * (1 - damage));
                if (item.getKey() == 1)
                {
                    if (strain_InElastic < 0 && strain_InElastic > (-1e-10))
                    {
                        strain_InElastic = 0;
                    }
                    if (strain_Plastic < 0 && strain_Plastic > (-1e-10))
                    {
                        strain_Plastic = 0;
                    }
                }
                double stress_Real = stress * (1 + strain);
                double strain_Real = Math.log(1 + strain);
                //对于本构点的受拉屈服点，因为要保证与受压具有相同的真实弹性模量，且保证第一个点的损伤为零时，真实非线性应变和真实塑性应变也为零
                //这里做了处理，调整初值的大小而不是调整应变的大小。
                //这种处理对于应力应变关系中的初值点0.000001影响极小，但如果初值点大于第二个点，则应该做调整。一般不会出现该问题！
                if (count == 1)
                {
                    stress_Real = strain_Real * Ec_Real;
                }
                double strain_InElastic_Real = strain_Real - stress_Real / Ec_Real;
                double damage_Real = GetDamage.ByEnergyBalanceMethod(stress_Real, Ec_Real, strain_Real);
                double strain_Plastic_Real = strain_Real - stress_Real / (Ec_Real * (1 - damage_Real));

                pointParameterValue.strain = strain;
                pointParameterValue.stress = stress;
                pointParameterValue.strain_InElastic = strain_InElastic;
                pointParameterValue.strain_Plastic = strain_Plastic;
                pointParameterValue.damage = damage;
                pointParameterValue.Ec = Ec;
                pointParameterValue.strain_Real = strain_Real;
                pointParameterValue.stress_Real = stress_Real;
                pointParameterValue.strain_InElastic_Real = strain_InElastic_Real;
                pointParameterValue.strain_Plastic_Real = strain_Plastic_Real;
                pointParameterValue.damage_Real = damage_Real;
                pointParameterValue.Ec_Real = Ec_Real;

                stress_strain_Ten_Modified.put(count, pointParameterValue);
            }

        }

        stress_strain[0] = stress_strain_Comp_Modified;
        stress_strain[1] = stress_strain_Ten_Modified;
        return stress_strain;
    }

    /// 按照作图法原理原理计算损伤，并更新stress_strain
    public static SortedMap<Integer, ParameterValue>[] ToModify_DamageByGraphicMethod(SortedMap<Integer, ParameterValue>[] stress_strain)
    {
        SortedMap<Integer, ParameterValue> stress_strain_Comp = stress_strain[0];
        SortedMap<Integer, ParameterValue> stress_strain_Ten = stress_strain[1];

        SortedMap<Integer, ParameterValue> stress_strain_Comp_Modified = new TreeMap<>();
        SortedMap<Integer, ParameterValue> stress_strain_Ten_Modified = new TreeMap<>();

        ParameterValue pointParameterValue = new ParameterValue();

        double Ec_Real = 0;


        for(Map.Entry<Integer, ParameterValue> item:stress_strain_Comp.entrySet())
        {
            if (item.getKey()== 0)
            {
                stress_strain_Comp_Modified.put(item.getKey(), item.getValue());
            }
            else
            {
                pointParameterValue = new ParameterValue();
                int count = item.getKey();
                double stress = item.getValue().stress;
                double strain = item.getValue().strain;
                double Ec = item.getValue().Ec;
                double damage = GetDamage.ByGraphicMethod_Comp(stress, Ec, strain);
                double strain_InElastic = strain - stress / Ec;
                double strain_Plastic = strain - stress / (Ec * (1 - damage));
                if (item.getKey() == 1)
                {
                    if (strain_InElastic < 0 && strain_InElastic > (-1e-10))
                    {
                        strain_InElastic = 0;
                    }
                    if (strain_Plastic < 0 && strain_Plastic > (-1e-10))
                    {
                        strain_Plastic = 0;
                    }
                }
                double stress_Real = stress * (1 + strain);
                double strain_Real = Math.log(1 + strain);
                if (item.getKey() == 1)
                {
                    Ec_Real = stress_Real / strain_Real;
                }
                double strain_InElastic_Real = strain_Real - stress_Real / Ec_Real;
                double damage_Real = GetDamage.ByGraphicMethod_Comp(stress_Real, Ec_Real, strain_Real);
                double strain_Plastic_Real = strain_Real - stress_Real / (Ec_Real * (1 - damage_Real));

                pointParameterValue.strain = strain;
                pointParameterValue.stress = stress;
                pointParameterValue.strain_InElastic = strain_InElastic;
                pointParameterValue.strain_Plastic = strain_Plastic;
                pointParameterValue.damage = damage;
                pointParameterValue.Ec = Ec;
                pointParameterValue.strain_Real = strain_Real;
                pointParameterValue.stress_Real = stress_Real;
                pointParameterValue.strain_InElastic_Real = strain_InElastic_Real;
                pointParameterValue.strain_Plastic_Real = strain_Plastic_Real;
                pointParameterValue.damage_Real = damage_Real;
                pointParameterValue.Ec_Real = Ec_Real;

                stress_strain_Comp_Modified.put(count, pointParameterValue);
            }

        }

        for(Map.Entry<Integer,ParameterValue> item:stress_strain_Ten.entrySet())
        {
            if (item.getKey() == 0)
            {
                stress_strain_Ten_Modified.put(item.getKey(), item.getValue());
            }
            else
            {
                pointParameterValue = new ParameterValue();
                int count = item.getKey();
                double stress = item.getValue().stress;
                double strain = item.getValue().strain;
                double Ec = item.getValue().Ec;
                double damage = GetDamage.ByGraphicMethod_Ten(stress, Ec, strain);
                double strain_InElastic = strain - stress / Ec;
                double strain_Plastic = strain - stress / (Ec * (1 - damage));
                if (item.getKey() == 1)
                {
                    if (strain_InElastic < 0 && strain_InElastic > (-1e-10))
                    {
                        strain_InElastic = 0;
                    }
                    if (strain_Plastic < 0 && strain_Plastic > (-1e-10))
                    {
                        strain_Plastic = 0;
                    }
                }
                double stress_Real = stress * (1 + strain);
                double strain_Real = Math.log(1 + strain);
                //对于本构点的受拉屈服点，因为要保证与受压具有相同的真实弹性模量，且保证第一个点的损伤为零时，真实非线性应变和真实塑性应变也为零
                //这里做了处理，调整初值的大小而不是调整应变的大小。
                //这种处理对于应力应变关系中的初值点0.000001影响极小，但如果初值点大于第二个点，则应该做调整。一般不会出现该问题！
                if (count == 1)
                {
                    stress_Real = strain_Real * Ec_Real;
                }
                double strain_InElastic_Real = strain_Real - stress_Real / Ec_Real;
                double damage_Real = GetDamage.ByGraphicMethod_Ten(stress_Real, Ec_Real, strain_Real);
                double strain_Plastic_Real = strain_Real - stress_Real / (Ec_Real * (1 - damage_Real));

                pointParameterValue.strain = strain;
                pointParameterValue.stress = stress;
                pointParameterValue.strain_InElastic = strain_InElastic;
                pointParameterValue.strain_Plastic = strain_Plastic;
                pointParameterValue.damage = damage;
                pointParameterValue.Ec = Ec;
                pointParameterValue.strain_Real = strain_Real;
                pointParameterValue.stress_Real = stress_Real;
                pointParameterValue.strain_InElastic_Real = strain_InElastic_Real;
                pointParameterValue.strain_Plastic_Real = strain_Plastic_Real;
                pointParameterValue.damage_Real = damage_Real;
                pointParameterValue.Ec_Real = Ec_Real;

                stress_strain_Ten_Modified.put(count, pointParameterValue);
            }

        }

        stress_strain[0] = stress_strain_Comp_Modified;
        stress_strain[1] = stress_strain_Ten_Modified;
        return stress_strain;
    }

    /// 按照GB50010-2010损伤计算方法计算损伤，并更新stress_strain
    public static SortedMap<Integer, ParameterValue>[] ToModify_DamageByGB50010_2010(SortedMap<Integer, ParameterValue>[] stress_strain)
    {
        SortedMap<Integer, ParameterValue> stress_strain_Comp = stress_strain[0];
        SortedMap<Integer, ParameterValue> stress_strain_Ten = stress_strain[1];

        SortedMap<Integer, ParameterValue> stress_strain_Comp_Modified = new TreeMap<>();
        SortedMap<Integer, ParameterValue> stress_strain_Ten_Modified =  new TreeMap<>();

        ParameterValue pointParameterValue = new ParameterValue();

        double Ec_Real = 0;

        //
        //获取峰值应变
        //
        double epsilon_cr = 0;
        double epsilon_tr = 0;
        double stressFlag = 0;
        for(Map.Entry<Integer,ParameterValue> item:stress_strain_Comp.entrySet())
        {
            if (item.getValue().stress >= stressFlag)
            {
                stressFlag = item.getValue().stress;
                epsilon_cr = item.getValue().strain;
            }
        }
        for(Map.Entry<Integer,ParameterValue> item:stress_strain_Ten.entrySet())
        {
            if (item.getValue().stress >= stressFlag)
            {
                stressFlag = item.getValue().stress;
                epsilon_tr = item.getValue().strain;
            }
        }

        double epsilon_cr_Real = Math.log(1 + epsilon_cr);
        double epsilon_tr_Real = Math.log(1 + epsilon_tr);



        for(Map.Entry<Integer,ParameterValue> item:stress_strain_Comp.entrySet())
        {
            if (item.getKey() == 0)
            {
                stress_strain_Comp_Modified.put(item.getKey(), item.getValue());
            }
            else
            {
                pointParameterValue = new ParameterValue();
                int count = item.getKey();
                double stress = item.getValue().stress;
                double strain = item.getValue().strain;
                double Ec = item.getValue().Ec;
                double damage = GetDamage.ByGB50010_2010Method(stress, Ec, strain, epsilon_cr);
                double strain_InElastic = strain - stress / Ec;
                double strain_Plastic = strain - stress / (Ec * (1 - damage));
                if (item.getKey() == 1)
                {
                    if (strain_InElastic < 0 && strain_InElastic > (-1e-10))
                    {
                        strain_InElastic = 0;
                    }
                    if (strain_Plastic < 0 && strain_Plastic > (-1e-10))
                    {
                        strain_Plastic = 0;
                    }
                }

                double stress_Real = stress * (1 + strain);
                double strain_Real = Math.log(1 + strain);
                if (item.getKey()== 1)
                {
                    Ec_Real = stress_Real / strain_Real;
                }
                double strain_InElastic_Real = strain_Real - stress_Real / Ec_Real;
                double damage_Real = GetDamage.ByGB50010_2010Method(stress_Real, Ec_Real, strain_Real, epsilon_cr_Real);
                double strain_Plastic_Real = strain_Real - stress_Real / (Ec_Real * (1 - damage_Real));

                pointParameterValue.strain = strain;
                pointParameterValue.stress = stress;
                pointParameterValue.strain_InElastic = strain_InElastic;
                pointParameterValue.strain_Plastic = strain_Plastic;
                pointParameterValue.damage = damage;
                pointParameterValue.Ec = Ec;
                pointParameterValue.strain_Real = strain_Real;
                pointParameterValue.stress_Real = stress_Real;
                pointParameterValue.strain_InElastic_Real = strain_InElastic_Real;
                pointParameterValue.strain_Plastic_Real = strain_Plastic_Real;
                pointParameterValue.damage_Real = damage_Real;
                pointParameterValue.Ec_Real = Ec_Real;

                stress_strain_Comp_Modified.put(count, pointParameterValue);
            }

        }

        for(Map.Entry<Integer,ParameterValue> item:stress_strain_Ten.entrySet())
        {
            if (item.getKey()== 0)
            {
                stress_strain_Ten_Modified.put(item.getKey(), item.getValue());
            }
            else
            {
                pointParameterValue = new ParameterValue();
                int count = item.getKey();
                double stress = item.getValue().stress;
                double strain = item.getValue().strain;
                double Ec = item.getValue().Ec;
                double damage = GetDamage.ByGB50010_2010Method(stress, Ec, strain, epsilon_tr);
                double strain_InElastic = strain - stress / Ec;
                double strain_Plastic = strain - stress / (Ec * (1 - damage));
                if (item.getKey() == 1)
                {
                    if (strain_InElastic < 0 && strain_InElastic > (-1e-10))
                    {
                        strain_InElastic = 0;
                    }
                    if (strain_Plastic < 0 && strain_Plastic > (-1e-10))
                    {
                        strain_Plastic = 0;
                    }
                }

                double stress_Real = stress * (1 + strain);
                double strain_Real = Math.log(1 + strain);
                //对于本构点的受拉屈服点，因为要保证与受压具有相同的真实弹性模量，且保证第一个点的损伤为零时，真实非线性应变和真实塑性应变也为零
                //这里做了处理，调整初值的大小而不是调整应变的大小。
                //这种处理对于应力应变关系中的初值点0.000001影响极小，但如果初值点大于第二个点，则应该做调整。一般不会出现该问题！
                if (count == 1)
                {
                    stress_Real = strain_Real * Ec_Real;
                }
                double strain_InElastic_Real = strain_Real - stress_Real / Ec_Real;
                double damage_Real = GetDamage.ByGB50010_2010Method(stress_Real, Ec_Real, strain_Real, epsilon_tr_Real);
                double strain_Plastic_Real = strain_Real - stress_Real / (Ec_Real * (1 - damage_Real));

                pointParameterValue.strain = strain;
                pointParameterValue.stress = stress;
                pointParameterValue.strain_InElastic = strain_InElastic;
                pointParameterValue.strain_Plastic = strain_Plastic;
                pointParameterValue.damage = damage;
                pointParameterValue.Ec = Ec;
                pointParameterValue.strain_Real = strain_Real;
                pointParameterValue.stress_Real = stress_Real;
                pointParameterValue.strain_InElastic_Real = strain_InElastic_Real;
                pointParameterValue.strain_Plastic_Real = strain_Plastic_Real;
                pointParameterValue.damage_Real = damage_Real;
                pointParameterValue.Ec_Real = Ec_Real;

                stress_strain_Ten_Modified.put(count, pointParameterValue);
            }

        }

        stress_strain[0] = stress_strain_Comp_Modified;
        stress_strain[1] = stress_strain_Ten_Modified;
        return stress_strain;
    }
}