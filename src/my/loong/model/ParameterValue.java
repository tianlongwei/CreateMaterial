package my.loong.model;


import my.loong.exceptions.ArgumentOutOfRangeException;

/**
 * @program: CreateMaterial
 * @description:
 * @AUTHOR: tlw
 * @create: 2018-11-20 20:42
 */
public class ParameterValue {
    /// 初始构造函数
    public ParameterValue()
    {

    }
    //
    //对象只需要获得strain、stress、Ec、damage、Ec_real、damage_Real
    //

    /// 工程应变Strain
    public double strain;
    /// 工程应力Stress
    public double stress;
    /// 工程非弹性应变Strain_InElastic
    public double strain_InElastic;
    /// 工程塑性应变Strain_Plastic
    public double strain_Plastic;
    /// 工程损伤Damage
    public double damage;
    /// 工程初始弹性模量Ec
    public double Ec;
    /// 真实应变Strain_Real
    public double strain_Real;
    /// 真实应力Stress_Real
    public double stress_Real;
    /// 真实非弹性应变Strain_InElastic_Real
    public double strain_InElastic_Real;
    /// 真实塑性应变Strain_Plastic_Real
    public double strain_Plastic_Real;
    /// 真实损伤Damage_Real
    public double damage_Real;
    /// 真实初始弹性模量Ec_Real
    public double Ec_Real;
    /// 自动更新非弹性应变、塑性应变、真实应力、真实应变、真实非弹性应变、真实塑性应变
    public void ToModified() throws ArgumentOutOfRangeException {
        strain_Real = Math.log(1 + strain);
        stress_Real = stress * (1 + strain);

        if(Ec!=0&&Ec_Real!=0)
        {
            strain_InElastic = strain - stress / Ec;
            strain_InElastic_Real = strain_Real - stress_Real / Ec_Real;
        }
        else
        {
            throw (new ArgumentOutOfRangeException("Ec、Ec_Real没有传入正确的值，其值为零，非法"));
        }

        if(damage!=1)
        {
            strain_Plastic = strain - stress / ((1 - damage) * Ec);
            strain_Plastic_Real = strain_Real - stress_Real / ((1 - damage_Real) * Ec_Real);
        }
        else
        {
            throw (new ArgumentOutOfRangeException("Damage、Damage_Real 没有传入正确的值，其值为零，非法"));
        }
    }
    /// 定义输出字段正则化长度
    private int padNumber = 20;
    /// 输出到txt文件时将所有的变量名称按照一行正则返回一行字符串
    public String ToWriteAllName2Txt()
    {
        String output="";
        output += padLeft("Strain,",padNumber," ");
        output += padLeft("Stress,",padNumber," ");
        output += padLeft("Strain_InElastic,",padNumber," ");
        output += padLeft("Strain_Plastic,",padNumber," ");
        output += padLeft("damage,",padNumber," ");
        output += padLeft("Ec,",padNumber," ");
        output += padLeft("Strain_Real,",padNumber," ");
        output += padLeft("Stress_Real,",padNumber," ");
        output += padLeft("Strain_InElastic_Real,",padNumber," ");
        output += padLeft("Strain_Plastic_Real,",padNumber," ");
        output += padLeft("Damage_Real,",padNumber," ");
        output += padLeft("Ec_Real,",padNumber," ");
        output += "\r\n";
        return output;
    }
    /// 输出到csv文件时将类中的字段名称返回一行字符串
    public String ToWriteAllName2CSV()
    {
        String output = "";
        output += "Strain,";
        output += "Stress,";
        output += "Strain_InElastic,";
        output += "Strain_Plastic,";
        output += "damage,";
        output += "Ec,";
        output += "Strain_Real,";
        output += "Stress_Real,";
        output += "Strain_InElastic_Real,";
        output += "Strain_Plastic_Real,";
        output += "Damage_Real,";
        output += "Ec_Real";
        output += "\r\n";
        return output;
    }

    /// 输出到txt文件时，将对象的变量值正则输出为一行字符串
    public String ToWriteAll2Txt()
    {
        String output = "";
        output += padLeft(String.valueOf(strain),padNumber," ");
        output += padLeft(String.valueOf(stress),padNumber," ");
        output += padLeft(String.valueOf(strain_InElastic),padNumber," ");
        output += padLeft(String.valueOf(strain_Plastic),padNumber," ");
        output += padLeft(String.valueOf(damage),padNumber," ");
        output += padLeft(String.valueOf(Ec),padNumber," ");
        output += padLeft(String.valueOf(strain_Real),padNumber," ");
        output += padLeft(String.valueOf(stress_Real),padNumber," ");
        output += padLeft(String.valueOf(strain_InElastic_Real),padNumber," ");
        output += padLeft(String.valueOf(strain_Plastic_Real),padNumber," ");
        output += padLeft(String.valueOf(damage_Real),padNumber," ");
        output += padLeft(String.valueOf(Ec_Real),padNumber," ");
        output += "\r\n";
        return output;
    }
    /// 输出到csv文件时，将对象的字段值正则输出为一行字符串
    public String ToWriteAll2CSV()
    {
        String output = "";
        output += strain + ",";
        output += stress+",";
        output += strain_InElastic+",";
        output += strain_Plastic+",";
        output += damage+",";
        output += Ec+",";
        output += strain_Real+",";
        output += stress_Real+",";
        output += strain_InElastic_Real+",";
        output += strain_Plastic_Real+",";
        output += damage_Real+",";
        output += Ec_Real+",";
        output += "\r\n";
        return output;
    }

    public String padLeft(String str,int num,String aa){
        //当str的长度小于num时，则在str左侧补上aa
        int l_str=str.length();
        StringBuilder sb=new StringBuilder();

        if (l_str<num){
            for (int i = 0; i < num-l_str; i++) {
                sb.append(aa);
            }
            sb.append(str);
        }
        return sb.toString();
    }

}