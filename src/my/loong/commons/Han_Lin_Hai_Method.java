package my.loong.commons;

import my.loong.model.ParameterValue;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @program: CreateMaterial
 * @description:韩林海方法计算本构曲线
 * @AUTHOR: tlw
 * @create: 2018-11-27 15:39
 */
public class Han_Lin_Hai_Method {

    public static double fcu;//混凝入抗压强度
    public static double D;//半圆区直径
    public static double B;//总长度
    public static double t;//钢管厚度
    public static double fy;//钢筋屈服强度

    public static double getFcu() {
        return fcu;
    }

    public static double getD() {
        return D;
    }

    public static void setD(double d) {
        D = d;
    }

    public static double getB() {
        return B;
    }

    public static void setB(double b) {
        B = b;
    }

    public static double getT() {
        return t;
    }

    public static void setT(double t) {
        Han_Lin_Hai_Method.t = t;
    }

    public static double getFy() {
        return fy;
    }

    public static void setFy(double fy) {
        Han_Lin_Hai_Method.fy = fy;
    }

    public static void setFcu(double fcu) {
        Han_Lin_Hai_Method.fcu = fcu;
    }

    //获取As参数
    public static double getAs(){
        double As=0.0;
        As=(B-D)*t*2.0+3.14*(Math.pow((D/2+t),2)-Math.pow(D/2,2));
        return As;
    }

    //获取Ac参数
    public static double getAc(){
        double Ac=0.0;
        Ac=(B-D)*D+3.14*(Math.pow(D/2,2));
        return Ac;
    }

    //获取epsilon参数
    public static double getEpsilon(){
        double epsilon=0.0;
        epsilon=getAs()*fy/(getAc()*fcu);
        return epsilon;
    }

    //获取ß0参数
    public static double getBate0(boolean isRectan,boolean isCircle){
        double bate0=0.0;
        if (isRectan){
            bate0=Math.pow(0.8*fcu,0.1)/(1.2*Math.sqrt(1+getEpsilon()));
        }else if (isCircle){
            //圆形时
            bate0=0.5*Math.pow((0.8*fcu),0.5)*Math.pow(0.000236,(0.25+Math.pow(getEpsilon()-0.5,7)));
        }
        return bate0;
    }


    //获取eta参数
    public static SortedMap<Integer, ParameterValue> GetStrain_stress(boolean isRectan){
        SortedMap<Integer,ParameterValue> map=new TreeMap<>();
        //如果是x<=1部分********************************
        Integer count=0;
        double y;
        double increment=0.02;
        double epsilon0=(1300+12.5*0.8*fcu)*Math.pow(10,-6)+800*Math.pow(getEpsilon(),0.2)*Math.pow(10,-6);
        for (double x=0.0;x<=1.0;x+=increment){
            y=2*x-Math.pow(x,2);
            ParameterValue parameterValue=new ParameterValue();
            parameterValue.setStrain(x*epsilon0);
            parameterValue.setStress(y*0.8*fcu);
            map.put(count,parameterValue);
            count++;
        }

        //*********x>1时********************
        double eta=0.0;
        if (isRectan){
            eta=2.0;
            for (double x=1.025;x<18;x+=increment){
                y=x/(getBate0(true,false)*(Math.pow(x-1,eta)+x));

                ParameterValue parameterValue=new ParameterValue();
                parameterValue.setStrain(x*epsilon0);
                parameterValue.setStress(y*0.8*fcu);
                map.put(count,parameterValue);
                count++;
            }
        }else {
            for (double x=1.025;x<18;x+=increment){
                eta=1.6+1.5/x;
                y=x/(getBate0(false,true)*(Math.pow(x-1,eta)+x));

                ParameterValue parameterValue=new ParameterValue();
                parameterValue.setStrain(x*epsilon0);
                parameterValue.setStress(y*0.8*fcu);
                map.put(count,parameterValue);
                count++;
            }
        }

        return map;
    }

    /**
    *@Description: 获取应变与承载力曲线
    *@Param: [isRectan]
    *@return: java.util.SortedMap<java.lang.Integer,my.loong.model.ParameterValue>
    *@Author: tlw
    *@Created: 2018/12/16-13:42 
    */
    public static SortedMap<Integer, ParameterValue>[] GetStress_N(boolean isRectan){
        SortedMap<Integer,ParameterValue>[] map=new TreeMap [3];

        SortedMap<Integer,ParameterValue> n_c=new TreeMap<>();
        SortedMap<Integer,ParameterValue> n_s=new TreeMap<>();
        SortedMap<Integer,ParameterValue> n_s_c=new TreeMap<>();

        //如果是x<=1部分********************************
        Integer count=0;
        double eta=0;
        double y;
        double increment=0.0001;
        double epsilon0=(1300+12.5*0.8*fcu)*Math.pow(10,-6)+800*Math.pow(getEpsilon(),0.2)*Math.pow(10,-6);
        //以应变为自变量获取参数
        double x=0;
        double sigma0=0.8*fcu;
        double sigma=0;

        double N_concrete=0;
        double N_steels=0;
        double N_c_s=0;
        for (double epsilon=0;epsilon<=0.04;epsilon+=increment){
            //获取x变量
            x=epsilon/epsilon0;
            //获取y值
            if (x<=1){
                y=2*x-Math.pow(x,2);
            }else {
                if (isRectan){
                    eta=1.6+1.5/x;
                }else {
                    eta=2.0;
                }
                y=x/(getBate0(isRectan,!isRectan)*Math.pow((x-1),eta)+x);
            }

            double sigma_s=206000*epsilon;
            if (sigma_s>=fy){
                sigma_s=fy;
            }

            N_steels=sigma_s*getAs();//获取到钢材承载力
            N_concrete=y*0.8*fcu*getAc();
            N_c_s=N_concrete+N_steels;



            //获取得到混凝土的承在力
            ParameterValue parameterValue0=new ParameterValue();
            parameterValue0.setStrain(epsilon);
            parameterValue0.setStress(N_concrete);
            n_c.put(count,parameterValue0);

            //获取钢材的承在力
            ParameterValue parameterValue1=new ParameterValue();
            parameterValue1.setStrain(epsilon);
            parameterValue1.setStress(N_steels);
            n_s.put(count,parameterValue1);

            //
            ParameterValue parameterValue2=new ParameterValue();
            parameterValue2.setStrain(epsilon);
            parameterValue2.setStress(N_c_s);
            n_s_c.put(count,parameterValue2);
            count++;

        }

        map[0]=n_c;
        map[1]=n_s;
        map[2]=n_s_c;

        return map;
    }



}