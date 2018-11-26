package my.loong.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import my.loong.commons.Ding_Yu_Method;
import my.loong.commons.GB50010_2010_Method;
import my.loong.commons.ModifiedStress_Strain_ParameterValues;
import my.loong.model.ParameterValue;

import java.io.*;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @program: CreateMaterial
 * @description:
 * @AUTHOR: tlw
 * @create: 2018-11-19 20:23
 */
public class Tab2Controller {



    //进行参数初始化操作
    @FXML
    private void initialize(){
        //默认为丁余计算方法
        radio_dingyu_method.setSelected(true);
        //默认为强度标准值
        radio_strength_standardValue.setSelected(true);
        //本构曲线参数计算默认为能量等效
        radio_demage_enger_balance.setSelected(true);
        //选择初始刚度来源为fck
        radio_fcuk.setSelected(true);
        //默认为真实本构
        radio_real.setSelected(true);
        //ABAQues屈服弹性模量Ec确定方法为初始弹性模量
        radio_Ec_origanl.setSelected(true);
        //屈服点应力系数为0.7
        text_eta.setText("0.7");


        lineChart.setPrefWidth(270);
        lineChart.setPrefHeight(220);
        pane.getChildren().add(lineChart);

    }



    /***********************************************************/
    /**********************选择计算参数**************************/
    /***********************************************************/
    @FXML
    private Label label_calMethod;
    @FXML
    private RadioButton radio_GB50010_2010_method;
    @FXML
    private RadioButton radio_dingyu_method;

    @FXML
    private void radio_GB50010_2010_method_clicked(){
        radio_GB50010_2010_method.setSelected(true);
        radio_dingyu_method.setSelected(false);
    }
    @FXML
    private void radio_dingyu_method_clicked(){
        radio_dingyu_method.setSelected(true);
        radio_GB50010_2010_method.setSelected(false);
    }

    /***********************************************************/
    /**********************选择强度代表值*************************/
    /***********************************************************/
    @FXML
    private Label label_strength_value;
    @FXML
    private RadioButton radio_strength_standardValue;
    @FXML
    private RadioButton radio_strength_averageValue;

    @FXML
    private void radio_strength_standardValue_clickled(){
        radio_strength_standardValue.setSelected(true);
        radio_strength_averageValue.setSelected(false);
        radio_fcuk.setText("初始刚度来源于Fcuk");
    }

    @FXML
    private void radio_strength_averageValue_clickled(){
        radio_strength_averageValue.setSelected(true);
        radio_strength_standardValue.setSelected(false);
        radio_fcuk.setText("初始刚度来源于Fcum");
    }

    /***********************************************************/
    /**********************选择初始刚度来源***********************/
    /***********************************************************/
    @FXML
    private Label label_source_fcuk;//初始刚度来源
    @FXML
    private RadioButton radio_fcuk;
    @FXML
    private RadioButton radio_Ec0;
    @FXML
    private TextField text_Ec0;
    @FXML
    private void radio_fcuk_clicked(){
        radio_fcuk.setSelected(true);
        radio_Ec0.setSelected(false);
    }
    @FXML
    private void radio_Ec0_clicked(){
        radio_Ec0.setSelected(true);
        radio_fcuk.setSelected(false);
    }

    /***********************************************************/
    /**********************选择损伤计算方法***********************/
    /***********************************************************/
    @FXML
    private Label label_demage_cal_method;
    @FXML
    private RadioButton radio_demage_enger_balance;//能量平衡
    @FXML
    private RadioButton radio_demage_tuxing;
    @FXML
    private RadioButton radio_demage_GB50010_2010_method;

    @FXML
    private void radio_demage_enger_balance_clicked(){
        radio_demage_enger_balance.setSelected(true);
        radio_demage_tuxing.setSelected(false);
        radio_demage_GB50010_2010_method.setSelected(false);
    }

    @FXML
    private void radio_demage_tuxing_clicked(){
        radio_demage_tuxing.setSelected(true);
        radio_demage_enger_balance.setSelected(false);
        radio_demage_GB50010_2010_method.setSelected(false);
    }

    @FXML
    private void radio_demage_GB50010_2010_method_clicked(){
        radio_demage_GB50010_2010_method.setSelected(true);
        radio_demage_enger_balance.setSelected(false);
        radio_demage_tuxing.setSelected(false);
    }

    /***********************************************************/
    /**********************选择损本构****************************/
    /***********************************************************/
    @FXML
    private RadioButton radio_real;//真实本构
    @FXML
    private RadioButton radio_engeneer;//工程本构

    @FXML
    private void radio_real_clicked(){
        radio_real.setSelected(true);
        radio_engeneer.setSelected(false);
    }

    @FXML
    private void radio_engeneer_clicked(){
        radio_engeneer.setSelected(true);
        radio_real.setSelected(false);
    }

    /***********************************************************/
    /**********************ABAQUS屈服弹性模量Ec确定方法************/
    /***********************************************************/
    @FXML
    private RadioButton radio_Ec_origanl;//初始弹性模量
    @FXML
    private RadioButton radio_Ec_gexian;
    @FXML
    private RadioButton radio_Ec_userDetermined;
    @FXML
    private TextField text_Ec_userDetermined;

    @FXML
    private void radio_Ec_origanl_clicked(){
        radio_Ec_origanl.setSelected(true);
        radio_Ec_gexian.setSelected(false);
        radio_Ec_userDetermined.setSelected(false);
    }

    @FXML
    private void radio_Ec_gexian_clicked(){
        radio_Ec_gexian.setSelected(true);
        radio_Ec_origanl.setSelected(false);
        radio_Ec_userDetermined.setSelected(false);
    }
    @FXML
    private void radio_Ec_userDetermined_clicked(){
        radio_Ec_userDetermined.setSelected(true);
        radio_Ec_origanl.setSelected(false);
        radio_Ec_gexian.setSelected(false);
    }
    /***********************************************************/
    /**********************点击计算功能处理事件*******************/
    /***********************************************************/
    @FXML
    private TextField text_fcr;//单轴抗压强度代表值
    @FXML
    private TextField text_ec;//屈服弹性模量
    @FXML
    private TextField text_ec0;//初始弹性模量
    @FXML
    private TextField text_cr;//峰值压应变
    @FXML
    private TextField text_fcur;//强度代表值

    @FXML
    private TextField text_eta;//屈服点应力系数

    @FXML
    private TextField text_ftr;//单轴抗拉强度代表值
    @FXML
    private TextField text_tr;//峰值拉应变



    /***********************************************************/
    /**********************点击计算功能处理事件*******************/
    /***********************************************************/
    @FXML
    private Button button_ok;

    //定义应力应变曲线的存储SortedDictionary<int, ParameterValue>
    SortedMap<Integer, ParameterValue>[] stress_strain = new TreeMap[2];

    SortedMap<Integer, ParameterValue> stress_strain_Comp = stress_strain[0];
    SortedMap<Integer, ParameterValue> stress_strain_Ten = stress_strain[1];

    @FXML
    private void handled_buttton_cal(){
        //读取必要参数
        double fcuk = 0;
        double fck = 0;
        double ftk = 0;
        double fcum = 0;
        double fcm = 0;
        double ftm = 0;


        //1、读取存储的参数信息
        String property = System.getProperty("user.dir");
        File file=new File(property+"\\1.txt");
        //2、
        BufferedReader reader=null;
        try {
            reader=new BufferedReader(new FileReader(file));
            String line="";
            String val="";
            while ((line=reader.readLine())!=null){
                if (line.contains("立方体抗压强度标准值fcuk：")){
                    val=line.split("：")[1];
                    fcuk=Double.valueOf(val);
                }
                if (line.contains("抗压强度标准值fck：")){
                    val=line.split("：")[1];
                    fck=Double.valueOf(val);
                }
                if (line.contains("抗拉强度标准值ftk：")){
                    val=line.split("：")[1];
                    ftk=Double.valueOf(val);
                }
                if (line.contains("立方体抗压强度平均值fcum：")){
                    val=line.split("：")[1];
                    fcum=Double.valueOf(val);
                }
                if (line.contains("立方体抗压强度平均值fcm：")){
                    val=line.split("：")[1];
                    fcm=Double.valueOf(val);
                }
                if (line.contains("立方体抗压强度平均值ftm：")){
                    val=line.split("：")[1];
                    ftm=Double.valueOf(val);
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //
        //计算本构参数
        //
        double fcur = 0;
        double fcr = 0;
        double ftr = 0;
        //Ec0是初始弹性模量
        double Ec0 = 0;
        //Ec是屈服弹性模量
        double Ec = 0;
        double epsilon_cr = 0;
        double epsilon_tr = 0;

        if (radio_strength_standardValue.isSelected()){
            if (fcuk==0){
                MessageBox.showBox("材料参数来源于试验值，找不到合法的fcuk，fck，ftk！请“选择强度代表值”来源于“平均值”");
                return;
            }else{
                fcur=fcuk;
                fcr=fck;
                ftr=ftk;
            }
        }else{
            fcur = fcum;
            fcr = fcm;
            ftr = ftm;
        }

        //弹性模量与立方体抗压强度标准值具有直接关系。——这种观点有问题，因为在计算本构的时候，如果Ec0不是由fcr取得，混凝土将具有初始损伤，即计算得到的dc和dt不为零。
        if(radio_fcuk.isSelected())
        {
            if(radio_GB50010_2010_method.isSelected())
            {
                Ec0 = GB50010_2010_Method.GetEc0(fcur);
            }
            if(radio_dingyu_method.isSelected())
            {
                Ec0 = Ding_Yu_Method.GetEc0(fcur);
            }
        }
        else
        {
            try
            {
                Ec0 = Double.valueOf(text_Ec0.getText());
            }
            catch(Exception e)
            {
                MessageBox.showBox("还未输入指定的Ec0");
                return;
            }
        }

        text_ec0.setText(String.valueOf(Ec0));

        //本构参数
        //单轴本构受压下降段参数
        double alfa_c = 0;
        //单轴本构受拉下降段参数
        double alfa_t=0;
        //丁发兴-余志武本构参数
        double Ac = 0;
        double At = 0;

        //获取屈服点应力系数
        double eta;

        try {
            eta=Double.valueOf(text_eta.getText());
            if (eta>1 || eta<0.4){
                MessageBox.showBox("屈服点应力系数η=σ_e/fcr:取值范围0.4~1，建议按照默认取为0.7");
                return;
            }
        }catch (Exception e){
            MessageBox.showBox("请输入合法的应力系数");
            return;
        }


        //求取屈服点弹性模量Ec=Ecy
        if(radio_Ec_origanl.isSelected())
        {
            Ec = Ec0;
        }
        else if(radio_Ec_gexian.isSelected())
        {
            if(radio_GB50010_2010_method.isSelected())
            {
                Ec = GB50010_2010_Method.GetEcy(fcr, ftr, Ec0, eta);
            }
            if(radio_dingyu_method.isSelected())
            {
                Ec = Ding_Yu_Method.GetEcy(fcr,ftr, Ec0, eta);
            }
        }
        else if(radio_Ec_userDetermined.isSelected())
        {
            try {
                Ec=Double.valueOf(text_Ec_userDetermined.getText());
            }catch (Exception e){
                MessageBox.showBox("请输入合法的Ec值");
                return;
            }
        }

        if(radio_GB50010_2010_method.isSelected())
        {
            epsilon_cr = GB50010_2010_Method.GetEpsilon_cr(fcr);
            epsilon_tr = GB50010_2010_Method.GetEpsilon_tr(ftr);
            alfa_c = GB50010_2010_Method.GetAlfa_c(fcr);
            alfa_t = GB50010_2010_Method.GetAlfa_t(ftr);
        }
        if(radio_dingyu_method.isSelected())
        {
            epsilon_cr = Ding_Yu_Method.GetEpsilon_cr(fcr);
            epsilon_tr = Ding_Yu_Method.GetEpsilon_tr_by_ftr(ftr);
            alfa_t = Ding_Yu_Method.GetAlfa_t_by_ftr(ftr);
            alfa_c = Ding_Yu_Method.GetAlfa_c_by_fcr(fcr);
            At = Ding_Yu_Method.GetAt_by_ftr(ftr,Ec0);
            Ac = Ding_Yu_Method.GetAc_by_fcr(fcr,Ec0);
        }

        if(Ec0<fcr/epsilon_cr)
        {
            MessageBox.showBox("Ec0不合法，您输入了过小的Ec0！\n" +
                    "本例中Ec0不小于{0}");
            text_Ec_userDetermined.setText("");
            return;
        }

        //输出到界面
        text_fcur.setText(String.valueOf(fcur));
        text_fcr.setText(String.valueOf(fcr));
        text_cr.setText(String.valueOf(epsilon_cr));//
        text_ec.setText(String.valueOf(Ec));
        text_ftr.setText(String.valueOf(ftr));
        text_tr.setText(String.valueOf(epsilon_tr));//

        //
        //当i=1时，创建应力应变离散曲线，当i=2时，创建20点本构
        //
        for(int i=1;i<=2;i++)
        {
            if (radio_GB50010_2010_method.isSelected())
            {
                if(i==1)
                {
                    stress_strain = GB50010_2010_Method.GetStress_Strain(fcr, ftr, Ec0);
                }
                if(i==2)
                {
                    if(radio_Ec_origanl.isSelected())
                    {
                        stress_strain = GB50010_2010_Method.GetStress_Strain_20Point(fcr, ftr, Ec0, eta);
                    }
                    else if(radio_Ec_gexian.isSelected())
                    {
                        stress_strain = GB50010_2010_Method.GetStress_Strain_20Point_Ecy(fcr, ftr, Ec0, eta);
                    }
                    else if(radio_Ec_userDetermined.isSelected())
                    {
                        stress_strain = GB50010_2010_Method.GetStress_Strain_20Point_EcyUsedifined(fcr, ftr, Ec0, Ec, eta);
                    }
                }
            }
            if (radio_dingyu_method.isSelected())
            {
                if(i==1)
                {
                    //stress_strain = Ding_Yu_Method.GetStress_Strain(fcur,Ec0);
                    stress_strain = Ding_Yu_Method.GetStress_Strain(fcr, ftr, Ec0);
                }
                if(i==2)
                {
                    if(radio_Ec_origanl.isSelected())
                    {
                        stress_strain = Ding_Yu_Method.GetStress_Strain_20Point(fcr,ftr, Ec0, eta);
                    }
                    else if(radio_Ec_gexian.isSelected())
                    {
                        stress_strain = Ding_Yu_Method.GetStress_Strain_20Point_Ecy(fcr,ftr, Ec0, eta);
                    }
                    else if(radio_Ec_userDetermined.isSelected())
                    {
                        stress_strain = Ding_Yu_Method.GetStress_Strain_20Point_EcyUsedifined(fcr,ftr, Ec0, Ec, eta);
                    }

                }
            }
            //
            //待更新内部数据
            //
            if (radio_demage_enger_balance.isSelected())
            {
                stress_strain = ModifiedStress_Strain_ParameterValues.ToModify_DamageByErengyMethod(stress_strain);
            }
            if (radio_demage_tuxing.isSelected())
            {
                stress_strain = ModifiedStress_Strain_ParameterValues.ToModify_DamageByGraphicMethod(stress_strain);
            }
            if (radio_demage_GB50010_2010_method.isSelected())
            {
                stress_strain = ModifiedStress_Strain_ParameterValues.ToModify_DamageByGB50010_2010(stress_strain);
            }

            stress_strain_Comp = stress_strain[0];
            stress_strain_Ten = stress_strain[1];

        }
        //1、将数据输出到csv文件中
        String title="strain,stress,strain_InElastic,strain_Plastic,damage,Ec,strain_Real,stress_Real,strain_InElastic_Real,strain_Plastic_Real,damage_Real,Ec_Real\r\n";
        File dir1=new File(property+File.separator+"stress_strain_comp");
        File dir2=new File(property+File.separator+"stress_strain_ten");
        dir1.mkdir();
        dir2.mkdir();

        //2、创建文件
        File comp=new File(property+File.separator+"stress_strain_comp"+File.separator+"stress_strain_comp.csv");
        File ten=new File(property+File.separator+"stress_strain_ten"+File.separator+"stress_strain_ten.csv");

        if (!comp.exists()){
            try {
                comp.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!ten.exists()){
            try {
                ten.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //System.out.println(csv);

        BufferedWriter writer=null;
        try {
            writer=new BufferedWriter(new FileWriter(comp));
            writer.write(title);

            for (Map.Entry<Integer,ParameterValue> aa:stress_strain_Comp.entrySet()) {
                //System.out.println(aa.getValue().strain * 1000+":"+aa.getValue().stress);
                writer.write(aa.getValue().ToWriteAll2CSV());
            }

        } catch (IOException e) {
            e.printStackTrace();
            MessageBox.showBox("写入数据到文件失败，可能是该文件已经打开");
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        BufferedWriter writerten=null;
        try {
            writerten=new BufferedWriter(new FileWriter(ten));
            writerten.write(title);

            for (Map.Entry<Integer,ParameterValue> aa:stress_strain_Ten.entrySet()) {
                //System.out.println(aa.getValue().strain * 1000+":"+aa.getValue().stress);
                writerten.write(aa.getValue().ToWriteAll2CSV());
            }

        } catch (IOException e) {
            e.printStackTrace();
            MessageBox.showBox("写入数据到文件失败，可能是该文件已经打开");
        }finally {
            try {
                writerten.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }//方法结束
    /***********************************************************/
    /**********************曲线图形显示**************************/
    /***********************************************************/
    @FXML
    private Pane pane;

    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    //xAxis.setLabel("stress-strain_Comp");//x坐标标签
    //creating the chart
    LineChart<Number,Number> lineChart =
            new LineChart<Number,Number>(xAxis,yAxis);

    public void removeSeries(){
        ObservableList<XYChart.Series<Number,Number>> data=lineChart.getData();
        if (data!=null && data.size()>0){
            for (int i = 0; i < data.size(); i++) {
                lineChart.getData().remove(i);
            }
        }
    }

    @FXML
    private void show_stress_strain_Comp_Chart(){
        removeSeries();
        //lineChart.setTitle("stress-strain_Comp");//设置主题
        lineChart.setCreateSymbols(false);//不显示节点符号
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("stress-strain_Comp");//设置图形名称

        //populating the series with data
        for (Map.Entry<Integer,ParameterValue> aa:stress_strain_Comp.entrySet()) {
            series.getData().add(new XYChart.Data(aa.getValue().strain * 1000, aa.getValue().stress));
        }
        //series.getData().add(new XYChart.Data(1, 23));
       // lineChart.setPrefWidth(270);
       // lineChart.setPrefHeight(220);
        lineChart.getData().add(series);
        //pane.getChildren().add(lineChart);
    }


    @FXML
    private void show_Stress_StrainInElastic_Comp_Chart(){
        removeSeries();
        //xAxis.setLabel("stress-strain_Comp");//x坐标标签
        //creating the chart
        //lineChart.setTitle("stress-strain_Comp");//设置主题
        lineChart.setCreateSymbols(false);//不显示节点符号
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Stress_StrainInElastic_Comp");//设置图形名称
        //populating the series with data
        for (Map.Entry<Integer,ParameterValue> aa:stress_strain_Comp.entrySet()) {
            series.getData().add(new XYChart.Data(aa.getValue().strain_InElastic * 1000, aa.getValue().stress));
        }
        //series.getData().add(new XYChart.Data(1, 23));
        lineChart.getData().add(series);
    }

    @FXML
    private void show_Damage_Strain_InElastic_Comp_Chart(){
        removeSeries();
        //xAxis.setLabel("stress-strain_Comp");//x坐标标签
        //creating the chart

        //lineChart.setTitle("stress-strain_Comp");//设置主题
        lineChart.setCreateSymbols(false);//不显示节点符号
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Damage_Strain_InElastic_Comp");//设置图形名称
        //populating the series with data
        for (Map.Entry<Integer,ParameterValue> aa:stress_strain_Comp.entrySet()) {
            series.getData().add(new XYChart.Data(aa.getValue().strain_InElastic * 1000, aa.getValue().damage));
        }
        //series.getData().add(new XYChart.Data(1, 23));
        //lineChart.setPrefWidth(270);
        //lineChart.setPrefHeight(220);
        lineChart.getData().add(series);
        //pane.getChildren().add(lineChart);
    }

    @FXML
    private void show_Strain_Plastic_Strain_Comp_Chart(){
        removeSeries();
        lineChart.setCreateSymbols(false);//不显示节点符号
        XYChart.Series series = new XYChart.Series();
        series.setName("Strain_Plastic_Strain_Comp");//设置图形名称
        for (Map.Entry<Integer,ParameterValue> aa:stress_strain_Comp.entrySet()) {
            series.getData().add(new XYChart.Data(aa.getValue().strain * 1000, aa.getValue().strain_Plastic));
        }
        lineChart.getData().add(series);
    }



    @FXML
    private void show_stress_strain_Ten_Chart(){
        removeSeries();
        lineChart.setCreateSymbols(false);//不显示节点符号
        XYChart.Series series = new XYChart.Series();
        series.setName("stress-strain_Ten");//设置图形名称
        for (Map.Entry<Integer,ParameterValue> aa:stress_strain_Ten.entrySet()) {
            series.getData().add(new XYChart.Data(aa.getValue().strain * 1000, aa.getValue().stress));
        }
        lineChart.getData().add(series);
    }

    @FXML
    private void show_Stress_StrainInElastic_Ten_Chart(){
        removeSeries();
        //xAxis.setLabel("stress-strain_Comp");//x坐标标签
        //creating the chart
        //lineChart.setTitle("stress-strain_Comp");//设置主题
        lineChart.setCreateSymbols(false);//不显示节点符号
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Stress_StrainInElastic_Ten");//设置图形名称
        //populating the series with data
        for (Map.Entry<Integer,ParameterValue> aa:stress_strain_Ten.entrySet()) {
            series.getData().add(new XYChart.Data(aa.getValue().strain_InElastic * 1000, aa.getValue().stress));
        }
        //series.getData().add(new XYChart.Data(1, 23));
        lineChart.getData().add(series);
    }

    @FXML
    private void show_Damage_Strain_InElastic_Ten_Chart(){
        removeSeries();
        //xAxis.setLabel("stress-strain_Comp");//x坐标标签
        //creating the chart

        //lineChart.setTitle("stress-strain_Comp");//设置主题
        lineChart.setCreateSymbols(false);//不显示节点符号
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Damage_Strain_InElastic_Ten");//设置图形名称
        //populating the series with data
        for (Map.Entry<Integer,ParameterValue> aa:stress_strain_Ten.entrySet()) {
            series.getData().add(new XYChart.Data(aa.getValue().strain_InElastic * 1000, aa.getValue().damage));
        }
        //series.getData().add(new XYChart.Data(1, 23));
        //lineChart.setPrefWidth(270);
        //lineChart.setPrefHeight(220);
        lineChart.getData().add(series);
        //pane.getChildren().add(lineChart);
    }



}