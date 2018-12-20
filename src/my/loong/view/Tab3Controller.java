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
import my.loong.commons.*;
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
public class Tab3Controller {



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
        clearText();
        radio_GB50010_2010_method.setSelected(true);
        radio_dingyu_method.setSelected(false);
        radio_guozhenhai_method.setSelected(false);

        hanlinhai_method_show(false);
        guozhenhai_method_show(false);

        button_n_c_s.setVisible(false);
    }
    @FXML
    private void radio_dingyu_method_clicked(){
        clearText();
        radio_dingyu_method.setSelected(true);
        radio_GB50010_2010_method.setSelected(false);
        radio_guozhenhai_method.setSelected(false);

        hanlinhai_method_show(false);
        guozhenhai_method_show(false);

        button_n_c_s.setVisible(false);
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
        if (radio_GB50010_2010_method.isSelected() || radio_dingyu_method.isSelected()){
            radio_fcuk.setText("初始刚度来源于Fcuk");
        }
    }

    @FXML
    private void radio_strength_averageValue_clickled(){
        radio_strength_averageValue.setSelected(true);
        radio_strength_standardValue.setSelected(false);
        if (radio_GB50010_2010_method.isSelected() || radio_dingyu_method.isSelected()){
            radio_fcuk.setText("初始刚度来源于Fcum");
        }
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
    private Label label_attention1;
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
    /**********************选择本构******************************/
    /***********************************************************/
    @FXML
    private Label label_bengou;
    @FXML
    private RadioButton radio_real;//真实本构
    @FXML
    private RadioButton radio_engeneer;//工程本构
    @FXML
    private Label label_attentin2;

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
    private Label label_abaqus;
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

    /***********************************************************/
    /**********************屈服点应力系数*************************/
    /***********************************************************/
    @FXML
    private Label label_eta;
    @FXML
    private TextField text_eta;//屈服点应力系数

    /***********************************************************/
    /**********************轴心受拉强度**************************/
    /***********************************************************/
    @FXML
    private Label label_zhouxin;
    @FXML
    private Label label_Ftr;
    @FXML
    private Label label_tr;
    @FXML
    private TextField text_ftr;//单轴抗拉强度代表值
    @FXML
    private TextField text_tr;//峰值拉应变

    /***********************************************************/
    /**********************增加两种方法生成本构曲线****************/
    /***********************************************************/
    @FXML
    private Label label_Fcr;//单轴抗压强度代表值Fcr
    @FXML
    private Label label_Ec;//屈服弹性模量Ec
    @FXML
    private Label label_Ec0;//初始弹性模量Ec0
    @FXML
    private Label label_epsilon_cr;//峰值压应变ε cr
    @FXML
    private Label label_fcur;//强度代表值fcur

    @FXML
    private RadioButton radio_hanlinhai_method;//韩林海方法
    @FXML
    private RadioButton radio_guozhenhai_method;//过振海方法

    @FXML
    private void radio_hanlinhai_method_clicked(){
        clearText();
        //不需要元素不显示
        guozhenhai_method_show(false);
        hanlinhai_method_show(true);//关闭不用显示的内容
    }

    @FXML
    private void radio_guozhenhai_method_clicked(){
        clearText();
        guozhenhai_method_show(true);

    }


    //韩海林方法内容显示
    private void hanlinhai_method_show(boolean ishowHanhailin){

        if (ishowHanhailin){
            //选择逻辑
            radio_hanlinhai_method.setSelected(true);
            radio_dingyu_method.setSelected(false);
            radio_GB50010_2010_method.setSelected(false);
            radio_guozhenhai_method.setSelected(false);

            label_Fcr.setText("截面宽度D(mm):");//单轴抗压强度代表值Fcr
            label_Ec.setText("钢管厚度（mm）:");//屈服弹性模量Ec
            label_Ec0.setText("钢筋屈服强度(Mpa):");//初始弹性模量Ec0
            label_epsilon_cr.setText("截面高度B(mm):");//峰值压应变ε cr
            label_fcur.setText("混凝土强度(Mpa):");//强度代表值fcur


            //增加形状选择
            label_strength_value.setText("选择混凝土截面参数类型");//选择强度代表值
            radio_strength_standardValue.setText("矩形截面");//标准值
            radio_strength_averageValue.setText("圆形截面");//平均值
        }else {
            label_fcur.setVisible(true);
            text_fcur.setVisible(true);

            label_Fcr.setText("单轴抗压强度代表值Fcr:");//单轴抗压强度代表值Fcr：
            label_Ec.setText("屈服弹性模量Ec:");//屈服弹性模量Ec：
            label_Ec0.setText("初始弹性模量Ec0:");//初始弹性模量Ec0：
            label_epsilon_cr.setText("峰值压应变εcr:");//峰值压应变ε cr
            label_fcur.setText("强度代表值fcur:");//强度代表值fcur

            //增加形状选择
            label_strength_value.setText("选择强度代表值");//选择强度代表值
            radio_strength_standardValue.setText("标准值");//标准值
            radio_strength_averageValue.setText("平均值");//平均值

            radio_hanlinhai_method.setSelected(false);
        }

        label_source_fcuk.setDisable(ishowHanhailin);
        radio_fcuk.setDisable(ishowHanhailin);
        radio_Ec0.setDisable(ishowHanhailin);
        text_Ec0.setDisable(ishowHanhailin);

        label_zhouxin.setDisable(ishowHanhailin);
        label_Ftr.setDisable(ishowHanhailin);
        label_tr.setDisable(ishowHanhailin);
        text_ftr.setDisable(ishowHanhailin);
        text_tr.setDisable(ishowHanhailin);

        label_demage_cal_method.setDisable(ishowHanhailin);
        radio_demage_enger_balance.setDisable(ishowHanhailin);
        radio_demage_tuxing.setDisable(ishowHanhailin);
        radio_demage_GB50010_2010_method.setDisable(ishowHanhailin);

        label_bengou.setDisable(ishowHanhailin);
        radio_real.setDisable(ishowHanhailin);
        radio_engeneer.setDisable(ishowHanhailin);

        label_eta.setDisable(ishowHanhailin);
        text_eta.setDisable(ishowHanhailin);

        label_abaqus.setDisable(ishowHanhailin);
        radio_Ec_origanl.setDisable(ishowHanhailin);
        radio_Ec_gexian.setDisable(ishowHanhailin);
        radio_Ec_userDetermined.setDisable(ishowHanhailin);
        text_Ec_userDetermined.setDisable(ishowHanhailin);

        //
        button_1.setDisable(ishowHanhailin);
        button_2.setDisable(ishowHanhailin);
        button_3.setDisable(ishowHanhailin);
        button_4.setDisable(ishowHanhailin);
        button_5.setDisable(ishowHanhailin);
        button_6.setDisable(ishowHanhailin);

        //
        label_attention1.setDisable(ishowHanhailin);
        label_attentin2.setDisable(ishowHanhailin);
        label_shouyabengou.setDisable(ishowHanhailin);
        label_shoulabengou.setDisable(ishowHanhailin);

        button_n_c_s.setVisible(ishowHanhailin);//是否显示韩林海方法
    }


    //过振海方法界面显示内容
    private void guozhenhai_method_show(boolean ishowGuozhengai){
        //选择逻辑
        if (ishowGuozhengai){
            radio_guozhenhai_method.setSelected(true);
            radio_dingyu_method.setSelected(false);
            radio_GB50010_2010_method.setSelected(false);
            radio_hanlinhai_method.setSelected(false);
        }else {
            radio_guozhenhai_method.setSelected(false);
        }
        //
        label_source_fcuk.setDisable(ishowGuozhengai);
        radio_fcuk.setDisable(ishowGuozhengai);
        radio_Ec0.setDisable(ishowGuozhengai);
        text_Ec0.setDisable(ishowGuozhengai);

        label_zhouxin.setDisable(ishowGuozhengai);
        label_Ftr.setDisable(ishowGuozhengai);
        label_tr.setDisable(ishowGuozhengai);
        text_ftr.setDisable(ishowGuozhengai);
        text_tr.setDisable(ishowGuozhengai);

        label_demage_cal_method.setDisable(ishowGuozhengai);
        radio_demage_enger_balance.setDisable(ishowGuozhengai);
        radio_demage_tuxing.setDisable(ishowGuozhengai);
        radio_demage_GB50010_2010_method.setDisable(ishowGuozhengai);

        label_bengou.setDisable(ishowGuozhengai);
        radio_real.setDisable(ishowGuozhengai);
        radio_engeneer.setDisable(ishowGuozhengai);

        label_eta.setDisable(ishowGuozhengai);
        text_eta.setDisable(ishowGuozhengai);

        label_abaqus.setDisable(ishowGuozhengai);
        radio_Ec_origanl.setDisable(ishowGuozhengai);
        radio_Ec_gexian.setDisable(ishowGuozhengai);
        radio_Ec_userDetermined.setDisable(ishowGuozhengai);
        text_Ec_userDetermined.setDisable(ishowGuozhengai);

        //
        button_1.setDisable(ishowGuozhengai);
        button_2.setDisable(ishowGuozhengai);
        button_3.setDisable(ishowGuozhengai);
        button_4.setDisable(ishowGuozhengai);
        button_5.setDisable(ishowGuozhengai);
        button_6.setDisable(ishowGuozhengai);

        //增加过振海方法不显示内容
        label_strength_value.setDisable(ishowGuozhengai);
        radio_strength_standardValue.setDisable(ishowGuozhengai);
        radio_strength_averageValue.setDisable(ishowGuozhengai);

        label_Fcr.setDisable(ishowGuozhengai);
        text_fcr.setDisable(ishowGuozhengai);

        label_Ec.setDisable(ishowGuozhengai);
        text_ec.setDisable(ishowGuozhengai);

        label_Ec0.setDisable(ishowGuozhengai);
        text_ec0.setDisable(ishowGuozhengai);

        label_epsilon_cr.setDisable(ishowGuozhengai);
        text_cr.setDisable(ishowGuozhengai);

        label_attention1.setDisable(ishowGuozhengai);
        label_attentin2.setDisable(ishowGuozhengai);
        label_shouyabengou.setDisable(ishowGuozhengai);
        label_shoulabengou.setDisable(ishowGuozhengai);

        button_n_c_s.setVisible(!ishowGuozhengai);
    }

    public void clearText(){
        text_fcr.setText("");
        text_ec.setText("");
        text_ec0.setText("");
        text_cr.setText("");
        text_fcur.setText("");
        text_ftr.setText("");
        text_tr.setText("");
    }


    /***********************************************************/
    /**********************点击计算功能处理事件*******************/
    /***********************************************************/
    @FXML
    private Button button_ok;

    //定义应力应变曲线的存储SortedDictionary<int, ParameterValue>
    SortedMap<Integer, ParameterValue>[] stress_strain = new TreeMap[2];

    SortedMap<Integer, ParameterValue> stress_strain_Comp = stress_strain[0];
    SortedMap<Integer, ParameterValue> stress_strain_Ten = stress_strain[1];


    SortedMap<Integer, ParameterValue>[] maps=null;

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


        //处理韩林海方法
        if (radio_hanlinhai_method.isSelected()){
            double D=0;
            double t=0;
            double B = 0;
            double fy=0;
            double fcu=0;
            try {
                D=Double.valueOf(text_fcr.getText());
                t=Double.valueOf(text_ec.getText());
                B=Double.valueOf(text_cr.getText());
                fy=Double.valueOf(text_ec0.getText());
                fcu=Double.valueOf(text_fcur.getText());
            }catch (Exception e){
                MessageBox.showBox("请输入合法的参数值");
                return;
            }

            //设置参数
            Han_Lin_Hai_Method.setFcu(fcu);//立方体轴心抗压强度边坡准直
            Han_Lin_Hai_Method.setB(B);
            Han_Lin_Hai_Method.setD(D);
            Han_Lin_Hai_Method.setT(t);
            Han_Lin_Hai_Method.setFy(fy);

            //获取数据
            SortedMap<Integer, ParameterValue> map=new TreeMap<>();
            map=Han_Lin_Hai_Method.GetStrain_stress(true);

            //测试显示数据

            if (radio_strength_standardValue.isSelected()){
                maps=Han_Lin_Hai_Method.GetStress_N(true);
            }else {
                maps=Han_Lin_Hai_Method.GetStress_N(false);
            }



            if (radio_strength_standardValue.isSelected()){
                map=Han_Lin_Hai_Method.GetStrain_stress(true);
            }else if (radio_strength_averageValue.isSelected()){
                map=Han_Lin_Hai_Method.GetStrain_stress(false);
            }


            //在pane中显示图表
            removeSeries();
            lineChart.setCreateSymbols(false);//不显示节点符号
            XYChart.Series series = new XYChart.Series();
            series.setName("stress-strain");//设置图形名称
            for (Map.Entry<Integer,ParameterValue> aa:map.entrySet()) {
                series.getData().add(new XYChart.Data(aa.getValue().strain * 1000, aa.getValue().stress));
            }
            lineChart.getData().add(series);

            //保存结果到文件中
            File file1=new File(System.getProperty("user.dir")+File.separator+"hanlinhai"+File.separator+"strain_stess.csv");
            String title="strain,stress";
            writeToCsvFile(file1,title,map,true);

            return;
        }

        //处理过振海方法
        if (radio_guozhenhai_method.isSelected()){
            double fcu=0;
            if (text_fcur.getText().equals("")){
                text_fcur.setText(String.valueOf(fcuk));
                fcu=fcuk;
            }else {
                try {
                    fcu=Double.valueOf(text_fcur.getText());
                }catch (Exception e){
                    MessageBox.showBox("输入参数有误");
                    return;
                }
            }

            Guo_Zhen_Hai_Method.setFcu(fcu);

            SortedMap<Integer,ParameterValue> strain_stress= Guo_Zhen_Hai_Method.getStrain_stress();
            for (Map.Entry<Integer,ParameterValue> aa:strain_stress.entrySet()){
                System.out.println(aa.getValue().getStrain()+":"+aa.getValue().getStress());
            }

            removeSeries();
            lineChart.setCreateSymbols(false);//不显示节点符号
            XYChart.Series series = new XYChart.Series();
            series.setName("stress-strain");//设置图形名称
            for (Map.Entry<Integer,ParameterValue> aa:strain_stress.entrySet()) {
                series.getData().add(new XYChart.Data(aa.getValue().strain * 1000, aa.getValue().stress));
            }
            lineChart.getData().add(series);

            //将数据写入文件中
            File file1=new File(System.getProperty("user.dir")+File.separator+"guozhenhai"+File.separator+"strain_stess.csv");
            String title="strain,stress";
            writeToCsvFile(file1,title,strain_stress,true);

            return;

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
        String title="strain,stress,strain_InElastic,strain_Plastic,damage,Ec,strain_Real,stress_Real,strain_InElastic_Real,strain_Plastic_Real,damage_Real,Ec_Real";
        //2、创建文件
        File comp=new File(property+File.separator+"stress_strain_comp"+File.separator+"stress_strain_comp.csv");
        File ten=new File(property+File.separator+"stress_strain_ten"+File.separator+"stress_strain_ten.csv");

        writeToCsvFile(comp,title,stress_strain_Comp,false);
        writeToCsvFile(ten,title,stress_strain_Ten,false);



    }//方法结束
    /***********************************************************/
    /**********************曲线图形显示**************************/
    /***********************************************************/
    @FXML
    private Pane pane;
    @FXML
    private Label label_shouyabengou;
    @FXML
    private Label label_shoulabengou;

    @FXML
    private Button button_1;//应力应变曲线
    @FXML
    private Button button_2;//应力应变曲线
    @FXML
    private Button button_3;//应力应变曲线
    @FXML
    private Button button_4;//应力应变曲线
    @FXML
    private Button button_5;//应力应变曲线
    @FXML
    private Button button_6;//应力应变曲线

    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    //xAxis.setLabel("stress-strain_Comp");//x坐标标签
    //creating the chart
    LineChart<Number,Number> lineChart =new LineChart<Number,Number>(xAxis,yAxis);

    //移除已经加载的图形
    public void removeSeries(){
        ObservableList<XYChart.Series<Number,Number>> data=lineChart.getData();
        if (data!=null && data.size()>0){
            int size=lineChart.getData().size();

            for (int i = 0; i <size; i++) {
                lineChart.getData().remove(0);
            }
        }

        //注意linchart在移除一个series后linechart大小会减小1。
        //所以这里每次都可以移除第一个元素
    }

    @FXML
    private void show_stress_strain_Comp_Chart(){
        if (checkIsNull(stress_strain_Comp)){
            return;
        }
        removeSeries();
        lineChart.setCreateSymbols(false);//不显示节点符号
        XYChart.Series series = new XYChart.Series();
        series.setName("stress-strain_Comp");//设置图形名称
        for (Map.Entry<Integer,ParameterValue> aa:stress_strain_Comp.entrySet()) {
            series.getData().add(new XYChart.Data(aa.getValue().strain * 1000, aa.getValue().stress));
        }
        lineChart.getData().add(series);
    }


    @FXML
    private void show_Stress_StrainInElastic_Comp_Chart(){
        if (checkIsNull(stress_strain_Comp)){
            return;
        }
        removeSeries();
        lineChart.setCreateSymbols(false);//不显示节点符号
        XYChart.Series series = new XYChart.Series();
        series.setName("Stress_StrainInElastic_Comp");//设置图形名称
        for (Map.Entry<Integer,ParameterValue> aa:stress_strain_Comp.entrySet()) {
            series.getData().add(new XYChart.Data(aa.getValue().strain_InElastic * 1000, aa.getValue().stress));
        }
        lineChart.getData().add(series);
    }

    @FXML
    private void show_Damage_Strain_InElastic_Comp_Chart(){
        if (checkIsNull(stress_strain_Comp)){
            return;
        }
        removeSeries();
        lineChart.setCreateSymbols(false);//不显示节点符号
        XYChart.Series series = new XYChart.Series();
        series.setName("Damage_Strain_InElastic_Comp");//设置图形名称
        for (Map.Entry<Integer,ParameterValue> aa:stress_strain_Comp.entrySet()) {
            series.getData().add(new XYChart.Data(aa.getValue().strain_InElastic * 1000, aa.getValue().damage));
        }
        lineChart.getData().add(series);
    }



    @FXML
    private void show_stress_strain_Ten_Chart(){
        if (checkIsNull(stress_strain_Ten)){
            return;
        }
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
        if (checkIsNull(stress_strain_Ten)){
            return;
        }
        removeSeries();
        lineChart.setCreateSymbols(false);//不显示节点符号
        XYChart.Series series = new XYChart.Series();
        series.setName("Stress_StrainInElastic_Ten");//设置图形名称
        for (Map.Entry<Integer,ParameterValue> aa:stress_strain_Ten.entrySet()) {
            series.getData().add(new XYChart.Data(aa.getValue().strain_InElastic * 1000, aa.getValue().stress));
        }
        lineChart.getData().add(series);
    }

    @FXML
    private void show_Damage_Strain_InElastic_Ten_Chart(){
        if (checkIsNull(stress_strain_Ten)){
            return;
        }
        removeSeries();
        lineChart.setCreateSymbols(false);//不显示节点符号
        XYChart.Series series = new XYChart.Series();
        series.setName("Damage_Strain_InElastic_Ten");//设置图形名称
        for (Map.Entry<Integer,ParameterValue> aa:stress_strain_Ten.entrySet()) {
            series.getData().add(new XYChart.Data(aa.getValue().strain_InElastic * 1000, aa.getValue().damage));
        }
        lineChart.getData().add(series);
    }

    //显示曲线前检查map是否为空
    public boolean checkIsNull(SortedMap<Integer,ParameterValue> map){
        if (map==null){
            MessageBox.showBox("请先进行计算操作");
            return true;
        }
        return false;
    }


    /***********************************************************/
    /**********************添加应变_承载力复合曲线*****************/
    /***********************************************************/
    @FXML
    private Button button_n_c_s;

    @FXML
    private void button_n_c_s_clicked(){
        //如果不是第一次点击，则自动填入数据
//        D=Double.valueOf(text_fcr.getText());
//        t=Double.valueOf(text_ec.getText());
//        B=Double.valueOf(text_cr.getText());
//        fy=Double.valueOf(text_ec0.getText());
//        fcu=Double.valueOf(text_fcur.getText());

        if (maps!=null){
            text_fcr.setText(String.valueOf(Han_Lin_Hai_Method.getD()));
            text_ec.setText(String.valueOf(Han_Lin_Hai_Method.getT()));
            text_cr.setText(String.valueOf(Han_Lin_Hai_Method.getB()));
            text_ec0.setText(String.valueOf(Han_Lin_Hai_Method.getFy()));
            text_fcur.setText(String.valueOf(Han_Lin_Hai_Method.getFcu()));
        }else {
            MessageBox.showBox("请先计算");
            return;
        }


        removeSeries();
        lineChart.setCreateSymbols(false);//不显示节点符号

        XYChart.Series series0 = new XYChart.Series();
        series0.setName("混凝土");//设置图形名称
        for (Map.Entry<Integer,ParameterValue> aa:maps[0].entrySet()) {
            series0.getData().add(new XYChart.Data(aa.getValue().strain , aa.getValue().stress/1000.0));
        }
        lineChart.getData().add(series0);

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("钢管");//设置图形名称
        for (Map.Entry<Integer,ParameterValue> aa:maps[1].entrySet()) {
            series1.getData().add(new XYChart.Data(aa.getValue().strain , aa.getValue().stress/1000.0));
        }
        lineChart.getData().add(series1);


        XYChart.Series series2 = new XYChart.Series();
        series2.setName("钢管混凝土");//设置图形名称
        for (Map.Entry<Integer,ParameterValue> aa:maps[2].entrySet()) {
            series2.getData().add(new XYChart.Data(aa.getValue().strain , aa.getValue().stress/1000.0));
        }
        lineChart.getData().add(series2);
        lineChart.setTitle("荷载-应变曲线");
    }






    //写入文件操作
    private void writeToCsvFile(File file,String title,SortedMap<Integer,ParameterValue> data,boolean isHanOrGuo_method){
        //如果文件不存在则创建一个文件
        if (!file.exists()){
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //打开文件对象写入数据
        BufferedWriter writer=null;

        try {
            writer=new BufferedWriter(new FileWriter(file));
            writer.write(title+"\r\n");//默认加入换行操作

            if (data!=null && !data.isEmpty()){
                if (isHanOrGuo_method){
                    for (Map.Entry<Integer,ParameterValue> aa:data.entrySet()){
                        writer.write(aa.getValue().strain+","+aa.getValue().getStress()+"\r\n");
                    }
                }else {
                    for (Map.Entry<Integer,ParameterValue> aa:data.entrySet()){
                        writer.write(aa.getValue().ToWriteAll2CSV());
                    }
                }
            }


            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            MessageBox.showBox("文件打开失败，可能是该文件正在被其他程序使用");
        }finally {
            try {
                if (writer!=null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }





}