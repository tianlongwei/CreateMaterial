package my.loong.commons;

import my.loong.model.ParameterValue;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @program: CreateMaterial
 * @description:添加过振海方法
 * @AUTHOR: tlw
 * @create: 2018-11-27 21:54
 */
public class Guo_Zhen_Hai_Method {

    private static double fcu;

    public static void setFcu(double fcu) {
        Guo_Zhen_Hai_Method.fcu = fcu;
    }

    //获取alpha_a参数
    public static double getAlpha(){
        return 2.4-0.0125*0.8*fcu;
    }

    //获取alphaD参数
    public static double getAlphaD(){
        return 0.132*Math.pow(0.8*fcu,0.785)-0.905;
    }

    //获取epsilon_c参数
    public static double getEpsilonC(){
        return (700+172*Math.sqrt(0.8*fcu))*Math.pow(10,-6);
    }

    public static SortedMap<Integer, ParameterValue> getStrain_stress(){
        SortedMap<Integer,ParameterValue> strain_stress=new TreeMap<>();
        double increment=0.025;
        Integer count=0;
        double alpha_a=getAlpha();
        double epsilon_c=getEpsilonC();
        double y;
        //当x<=1的时候
        for (double x = 0.0; x <= 1; x+=increment) {
            y=alpha_a*x+(3-2*alpha_a)*Math.pow(x,2)+(alpha_a-2)*Math.pow(x,3);
            ParameterValue parameterValue=new ParameterValue();
            parameterValue.setStrain(x*epsilon_c);//设置应变
            parameterValue.setStress(y*0.8*fcu);//设置应变
            strain_stress.put(count,parameterValue);
            count++;
        }
        //当x>1时
        double alpha_d=getAlphaD();
        for (double x = 1.025; x <18; x+=increment) {
            y=x/(alpha_d*Math.pow(x-1,2)+x);
            ParameterValue parameterValue=new ParameterValue();
            parameterValue.setStrain(x*epsilon_c);
            parameterValue.setStress(y*0.8*fcu);
            strain_stress.put(count,parameterValue);
            count++;
        }
        return strain_stress;
    }


}