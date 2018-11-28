package my.loong.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import my.loong.commons.GetDelta_Concrete;
import my.loong.commons.MaterialParameter_Ding_Yu;
import my.loong.commons.MaterialParameter_GB50010_2010;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @program: CreateMaterial
 * @description:
 * @AUTHOR: tlw
 * @create: 2018-11-18 16:20
 */
public class Tab1Controller {

    @FXML
    private RadioButton radio_designDtrength;//来源于设计强度值
    @FXML
    private RadioButton radio_testDtrength;//来源于试验强度值

    @FXML
    private void initialize() {
        radio_designDtrength.setSelected(true);
        radio_testDtrength.setSelected(false);
        radio_notConsidered.setDisable(true);

        //
        label_fcu.setDisable(true);

        label_fcu150.setDisable(true);
        text_fcu150.setDisable(true);
        radio_fcu150.setDisable(true);
        //
        label_fcu100.setDisable(true);
        text_fcu100.setDisable(true);
        radio_fcu100.setDisable(true);

        label_fcu200.setDisable(true);
        text_fcu200.setDisable(true);
        radio_fcu200.setDisable(true);

        label_fcu150_cal.setDisable(true);
        text_fcu150_cal.setDisable(true);

        //设计强度参数输入初始化
        radio_088_no.setSelected(true);
        radio_brittlement_no.setSelected(true);
        text_standardFcuk.setText("50");

        //试验强度参数输入初始化
//        label_fcu200.setDisable(true);
//        text_fcu200.setDisable(true);
//        radio_fcu200.setDisable(true);

        //初始化计算方法选择
        radio_dingyu_method.setSelected(true);
        //初始化变异系数选择
        radio_GB50010_2002.setSelected(true);


    }

    //当使用设计值时
    @FXML
    private void radio_designDtrength_Clicked() {
        radio_designDtrength.setSelected(true);
        radio_testDtrength.setSelected(false);
        label_fcu.setDisable(true);

        label_fcu150.setDisable(true);
        text_fcu150.setDisable(true);
        radio_fcu150.setDisable(true);
        //
        label_fcu100.setDisable(true);
        text_fcu100.setDisable(true);
        radio_fcu100.setDisable(true);

        label_fcu200.setDisable(true);
        text_fcu200.setDisable(true);
        radio_fcu200.setDisable(true);

        label_fcu150_cal.setDisable(true);
        text_fcu150_cal.setDisable(true);

        //设计强度参数输入可见
        label_fcuk.setDisable(false);
        label_standardFcuk.setDisable(false);
        text_standardFcuk.setDisable(false);
        label_088.setDisable(false);
        radio_088_yes.setDisable(false);
        radio_088_no.setDisable(false);
        label_brittlement.setDisable(false);
        radio_brittlement_yes.setDisable(false);
        radio_brittlement_no.setDisable(false);

        //提示信息显示
        label_attention1.setVisible(true);
        label_attention2.setVisible(true);
        label_formula.setVisible(true);

        //变异系数来源GB50010显示
        radio_GB50010_2010.setDisable(false);
        radio_GB50010_2002.setDisable(false);
        //radio_userDetermined.setDisable(true);
        radio_notConsidered.setDisable(true);
        radio_GB50010_2002.setSelected(true);
        radio_notConsidered.setSelected(false);
        radio_userDetermined.setSelected(false);
        radio_GB50010_2010.setSelected(false);
    }

    //当点击使用试验强度值时
    @FXML
    private void radio_testDtrength_Clicked() {
        radio_testDtrength.setSelected(true);
        radio_designDtrength.setSelected(false);
        label_fcu.setDisable(false);
        label_fcu150.setDisable(false);
        text_fcu150.setDisable(false);
        radio_fcu150.setDisable(false);

        label_fcu100.setDisable(false);
        text_fcu100.setDisable(false);
        radio_fcu100.setDisable(false);

        //label_fcu200.setDisable(false);
        //text_fcu200.setDisable(false);
        //radio_fcu200.setDisable(false);

        label_fcu150_cal.setDisable(false);
        //text_fcu150_cal.setDisable(false);//用于显示计算值

        //设计强度参数输入不可见
        label_fcuk.setDisable(true);
        label_standardFcuk.setDisable(true);
        text_standardFcuk.setDisable(true);
        label_088.setDisable(true);
        radio_088_yes.setDisable(true);
        radio_088_no.setDisable(true);
        label_brittlement.setDisable(true);
        radio_brittlement_yes.setDisable(true);
        radio_brittlement_no.setDisable(true);

        //试验参书数初始化
        radio_fcu150.setSelected(true);
        text_fcu100.setDisable(true);

        //提示信息不显示
        label_attention1.setVisible(false);
        label_attention2.setVisible(false);
        label_formula.setVisible(false);

        //变异系数来源GB50010不显示
        radio_GB50010_2010.setDisable(true);
        radio_GB50010_2002.setDisable(true);
        radio_userDetermined.setDisable(false);
        radio_notConsidered.setDisable(false);
        radio_notConsidered.setSelected(true);
        radio_GB50010_2002.setSelected(false);

        radio_userDetermined.setSelected(false);
        radio_GB50010_2010.setSelected(false);
    }


    //选择计算方法
    @FXML
    private RadioButton radio_GB50010_2010_method;
    @FXML
    private RadioButton radio_dingyu_method;

    @FXML
    private void method_GB50010_2010clicked() {
        radio_GB50010_2010_method.setSelected(true);
        radio_dingyu_method.setSelected(false);
    }

    @FXML
    private void fromTestDtrengthClicked() {
        radio_dingyu_method.setSelected(true);
        radio_GB50010_2010_method.setSelected(false);
    }

    /**************************************************************************************/
    /**********************************选择变异系数来源**************************************/
    /**************************************************************************************/
    @FXML
    private RadioButton radio_GB50010_2010;
    @FXML
    private RadioButton radio_GB50010_2002;
    @FXML
    private RadioButton radio_userDetermined;
    @FXML
    private TextField text_userDetermined;
    @FXML
    private RadioButton radio_notConsidered;

    @FXML
    private void radio_GB50010_2010_clicked() {
        radio_GB50010_2010.setSelected(true);
        radio_GB50010_2002.setSelected(false);
        radio_userDetermined.setSelected(false);
        text_userDetermined.setText("");
    }

    @FXML
    private void radio_GB50010_2002_clicked() {
        radio_GB50010_2002.setSelected(true);
        radio_GB50010_2010.setSelected(false);
        radio_userDetermined.setSelected(false);
        text_userDetermined.setText("");

    }

    @FXML
    private void radio_userDetermined_clicked() {
        radio_userDetermined.setSelected(true);
        radio_GB50010_2002.setSelected(false);
        radio_GB50010_2010.setSelected(false);
        radio_notConsidered.setSelected(false);
        text_userDetermined.setText("0.1");
    }

    @FXML
    private void radio_notConsidered_clicked() {
        radio_notConsidered.setSelected(true);
        radio_userDetermined.setSelected(false);
        radio_GB50010_2002.setSelected(false);
        radio_GB50010_2010.setSelected(false);
        text_userDetermined.setText("");
    }



    /**************************************************************************************/
    /**********************************设计强度参数输入**************************************/
    /**************************************************************************************/
    //设计强度输入参数
    @FXML
    private Label label_fcuk;//设计强度疏输入参数
    @FXML
    private Label label_standardFcuk;//立方体抗压强度标准值
    @FXML
    private TextField text_standardFcuk;//
    //0.88折减系数
    @FXML
    private Label label_088;//0.88折减系数
    @FXML
    private RadioButton radio_088_yes;//是
    @FXML
    private RadioButton radio_088_no;//否
    //脆性折减
    @FXML
    private Label label_brittlement;
    @FXML
    private RadioButton radio_brittlement_yes;//是
    @FXML
    private RadioButton radio_brittlement_no;//否

    @FXML
    private void radio_088_yes_clicked() {
        radio_088_yes.setSelected(true);
        radio_088_no.setSelected(false);
    }

    @FXML
    private void radio_088_no_clicked() {
        radio_088_no.setSelected(true);
        radio_088_yes.setSelected(false);
    }

    @FXML
    private void radio_brittlement_yes_clicked() {
        radio_brittlement_yes.setSelected(true);
        radio_brittlement_no.setSelected(false);
    }

    @FXML
    private void radio_brittlement_no_clicked() {
        radio_brittlement_no.setSelected(true);
        radio_brittlement_yes.setSelected(false);
    }


    /**************************************************************************************/
    /**********************************试验强度参数输入**************************************/
    /**************************************************************************************/
    //150标准试块立方体抗压
    @FXML
    private Label label_fcu;
    @FXML
    private Label label_fcu150;//
    @FXML
    private TextField text_fcu150;
    @FXML
    private RadioButton radio_fcu150;

    //100非标准试块立方体抗压
    @FXML
    private Label label_fcu100;//
    @FXML
    private TextField text_fcu100;
    @FXML
    private RadioButton radio_fcu100;

    //200非标准试块立方体抗压
    @FXML
    private Label label_fcu200;//
    @FXML
    private TextField text_fcu200;
    @FXML
    private RadioButton radio_fcu200;

    //计算采用的fcu150
    @FXML
    private Label label_fcu150_cal;//
    @FXML
    private TextField text_fcu150_cal;


    @FXML
    private void radio_fcu150_clicked() {
        radio_fcu150.setSelected(true);
        label_fcu150.setDisable(false);
        text_fcu150.setDisable(false);

        radio_fcu100.setSelected(false);

        label_fcu100.setDisable(true);
        text_fcu100.setDisable(true);
//        radio_fcu100.setDisable(true);
    }

    @FXML
    private void radio_fcu100_clicked() {
        radio_fcu100.setSelected(true);
        label_fcu100.setDisable(false);
        text_fcu100.setDisable(false);

        radio_fcu150.setSelected(false);

        label_fcu150.setDisable(true);
        text_fcu150.setDisable(true);
//        radio_fcu150.setDisable(true);
    }

    @FXML
    private void setText_fcu100_exited(){
        text_fcu150.setText("");
        text_fcu200.setText("");

        double fcu100 = 0;
        if (text_fcu100.getText().equals("")){
            MessageBox.showBox("不能为空");
            clearText();
            return;
        }else {
            fcu100=Double.valueOf(text_fcu100.getText());
        }
        if (radio_dingyu_method.isSelected()){
            text_fcu150_cal.setText(String.valueOf(MaterialParameter_Ding_Yu.GetFcuFromFcu100(fcu100)));
        }else if (radio_GB50010_2010_method.isSelected()){
            text_fcu150_cal.setText(String.valueOf(MaterialParameter_GB50010_2010.GetFcuFromFcu100(fcu100)));
        }

    }

    @FXML
    private void setText_fcu150_exited(){
        text_fcu100.setText("");
        text_fcu200.setText("");


        double fcu150 = 0;
        if (text_fcu150.getText().equals("")){
            MessageBox.showBox("不能为空");
            clearText();
            return;
        }else {
            fcu150=Double.valueOf(text_fcu150.getText());
        }
        text_fcu150_cal.setText(text_fcu150.getText());
    }

    @FXML
    private void setText_fcu200_exited() {
        text_fcu100.setText("");
        text_fcu150.setText("");

        double fcu200 = Double.valueOf(text_fcu200.getText());
        if (text_fcu200.getText().equals("")) {
            MessageBox.showBox("不能为空");
            clearText();
            return;
        } else {
            fcu200 = Double.valueOf(text_fcu200.getText());
        }

        if (radio_GB50010_2010_method.isSelected()) {
            text_fcu150_cal.setText(String.valueOf(MaterialParameter_GB50010_2010.GetFcuFromFcu200(fcu200)));
        }
    }

    /**************************************************************************************/
    /**********************************计算参数*********************************************/
    /**************************************************************************************/
    @FXML
    private Label label_cv;//变异系数
    @FXML
    private TextField text_cv;
    @FXML
    private Label label_fck;//轴心抗压强度标准值
    @FXML
    private TextField text_fck;
    @FXML
    private Label label_ftk;//轴心抗拉强度标准值
    @FXML
    private TextField text_ftk;

    @FXML
    private Label label_fcum;//立方体抗压强度均值
    @FXML
    private TextField text_fcum;

    @FXML
    private Label label_fcm;//轴心抗压强度均值
    @FXML
    private TextField text_fcm;

    @FXML
    private Label label_ftm;//轴心抗拉强度均值
    @FXML
    private TextField text_ftm;


    @FXML
    private Label label_ac1;//αc1
    @FXML
    private TextField text_ac1;

    @FXML
    private Label label_ac2;//αc2
    @FXML
    private TextField text_ac2;


    /**************************************************************************************/
    /**********************************红色提示部分******************************************/
    /**************************************************************************************/
    @FXML
    private Label label_attention1;//注意一
    @FXML
    private Label label_attention2;//注意二
    @FXML
    private Label label_formula;//公式delta_c=..

    /**************************************************************************************/
    /**********************************OK按钮**********************************************/
    /**************************************************************************************/
    @FXML
    private Button button_ok;

    @FXML
    private void button_ok_clicked() {
        boolean isConsider088 = true;
        boolean isConsiderAlfa_C2 = true;
        boolean isDeltaFrom2010 = false;
        double fcuk = 0;
        double delta_c = 0;
        double fck = 0;
        double ftk = 0;
        double fcum = 0;
        double fcm = 0;
        double ftm = 0;
        double alfa_C1 = 0;
        double alfa_C2 = 0;


        //System.out.println("点击了");
        //如果是来自设计强度
        if (radio_designDtrength.isSelected()) {
            try {
                fcuk = Double.valueOf(text_standardFcuk.getText());
            } catch (Exception e) {
                //e.printStackTrace();
                MessageBox.showBox("请输入合法数字");
                clearText();
                return;
            }

            //对选择GB50010-2010变异系数计算方法给出提示
            if (radio_GB50010_2010.isSelected()) {
                MessageBox.showBox("不建议使用GB50010-2010计算变异系数");
                clearText();
                return;
            }
            //强度超出GB50010的混凝土强度等级给出提示
            if (fcuk > 80 && (radio_GB50010_2010.isSelected() || radio_GB50010_2002.isSelected())) {
                MessageBox.showBox("选择的变异系数计算方法超出了GB50010-2010和GB50010-2002的适用范围，建议使用自定义系数");
                clearText();
                return;
            }

            //对用户输入的变异系数进行检查，不适用者给予警告,下同。
            if (radio_userDetermined.isSelected()) {
                if (text_userDetermined.getText().equals("")) {
                    MessageBox.showBox("用户指定的变异系数不得为空");
                    clearText();
                    return;
                }
                try {
                    delta_c = Double.valueOf(text_userDetermined.getText());
                } catch (Exception e) {
                    MessageBox.showBox("请输入合法数字");
                    clearText();
                    return;
                }
                if (delta_c < 0 || delta_c > 0.3) {
                    MessageBox.showBox("变异系数取值范围为[0,0.3]");
                    clearText();
                    return;
                }
            }


            if (radio_GB50010_2010_method.isSelected()) {
                //是否考虑0。88折减
                if (radio_088_no.isSelected()) {
                    isConsider088 = false;
                }
                //是否考虑脆性折减
                if (radio_brittlement_no.isSelected()) {
                    isConsiderAlfa_C2 = false;
                }
                //变异系数来源
                isDeltaFrom2010 = radio_GB50010_2010.isSelected();

                if (fcuk >= 60 && fcuk <= 80 && radio_GB50010_2010_method.isSelected()) {
                    MessageBox.showBox("C80>=Fcuk>=C60，规范方法虽然适用，但精度较低.请选择丁发兴-余志武方法,并重新计算");
                    clearText();
                    return;
                }
                if (fcuk > 80 && radio_GB50010_2010_method.isSelected()) {
                    MessageBox.showBox("Fcuk>=C80，规范方法已经不适用.请选择丁发兴-余志武方法,并重新计算");
                    clearText();
                    return;
                }

                delta_c = isDeltaFrom2010 ? GetDelta_Concrete.GetDelta_cFrom2010(fcuk) :
                        (radio_GB50010_2002.isSelected() ? GetDelta_Concrete.GetDelta_cFrom2002(fcuk) : Double.valueOf(text_userDetermined.getText()));
                fck = MaterialParameter_GB50010_2010.GetFck(fcuk, isConsider088, isConsiderAlfa_C2);

                ftk = MaterialParameter_GB50010_2010.GetFtk(fcuk, isConsider088, isConsiderAlfa_C2, delta_c);
                fcum = MaterialParameter_GB50010_2010.GetFcum(fcuk, delta_c);
                fcm = MaterialParameter_GB50010_2010.GetFcm(fcum);
                ftm = MaterialParameter_GB50010_2010.GetFtm(fcum);
                alfa_C1 = MaterialParameter_GB50010_2010.GetAlfaC1(fcuk);
                alfa_C2 = MaterialParameter_GB50010_2010.GetAlfaC2(fcuk);

                //写入各参数
                text_fck.setText(String.valueOf(fck));
                text_ftk.setText(String.valueOf(ftk));
                text_fcm.setText(String.valueOf(fcm));
                text_ftm.setText(String.valueOf(ftm));

                text_fck.setText(String.valueOf(fck));
                text_fck.setText(String.valueOf(fck));

                text_cv.setText(String.valueOf(delta_c));
                text_fcum.setText(String.valueOf(fcum));
                text_ac1.setText(String.valueOf(alfa_C1));
                text_ac2.setText(String.valueOf(alfa_C2));

            }


            //如果是选择的丁-余方法
            if (radio_dingyu_method.isSelected()){
                if (radio_088_no.isSelected()){
                    isConsider088=false;
                }
                if (radio_brittlement_no.isSelected()){
                    isConsiderAlfa_C2=false;
                }

                isDeltaFrom2010=radio_GB50010_2010.isSelected();

                delta_c = isDeltaFrom2010 ? GetDelta_Concrete.GetDelta_cFrom2010(fcuk) : (radio_GB50010_2002.isSelected() ? GetDelta_Concrete.GetDelta_cFrom2002(fcuk) : Double.valueOf(text_userDetermined.getText()));
                fck = MaterialParameter_Ding_Yu.GetFck(fcuk, isConsider088, isConsiderAlfa_C2);
                ftk = MaterialParameter_Ding_Yu.GetFtk(fcuk, isConsider088, isConsiderAlfa_C2);
                fcum = MaterialParameter_Ding_Yu.GetFcum(fcuk, delta_c);
                fcm = MaterialParameter_Ding_Yu.GetFcm(fcum);
                ftm = MaterialParameter_Ding_Yu.GetFtm(fcum);
                alfa_C2 = MaterialParameter_Ding_Yu.GetAlfaC2(fcuk);

                //写入各参数
                text_fck.setText(String.valueOf(fck));
                text_ftk.setText(String.valueOf(ftk));
                text_fcm.setText(String.valueOf(fcm));
                text_ftm.setText(String.valueOf(ftm));

                text_cv.setText(String.valueOf(delta_c));
                text_fcum.setText(String.valueOf(fcum));
                text_ac1.setText(String.valueOf(""));//没有该值
                text_ac2.setText(String.valueOf(alfa_C2));
            }
        }else if (radio_testDtrength.isSelected()){
            /************************************************************************************************************************************************************/
            //如果是使用的试验值

            if (text_fcu150_cal.getText().equals("")){
                MessageBox.showBox("试验强度参数不能为空");
                clearText();
                return;
            }else {
                fcum=Double.valueOf(text_fcu150_cal.getText());
            }


            if (radio_GB50010_2010_method.isSelected()){
                if (fcum>=60 && fcum<=80 && radio_GB50010_2010_method.isSelected()){
                    MessageBox.showBox("C80>=Fcum>=C60，规范方法虽然适用，但精度较低.请选择丁发兴-余志武方法,并重新计算");
                    clearText();
                    return;
                }
                if (fcum>80 && radio_GB50010_2010_method.isSelected()){
                    MessageBox.showBox("Fcum>=C80，规范方法已经不适用.请选择丁发兴-余志武方法,并重新计算");
                    clearText();
                    return;
                }

                fcm = MaterialParameter_GB50010_2010.GetFcm(fcum);
                ftm = MaterialParameter_GB50010_2010.GetFtm(fcum);

                text_fcm.setText(String.valueOf(fcm));
                text_ftm.setText(String.valueOf(ftm));
                text_fcum.setText(String.valueOf(fcum));

                if (!radio_userDetermined.isSelected()){
                    text_cv.setText("");
                    text_fck.setText("");
                    text_ftk.setText("");
                }else {
                    delta_c=Double.valueOf(text_userDetermined.getText());
                    fcuk = fcum * (1 - 1.645 * delta_c);
                    fck = fcm * (1 - 1.645 * delta_c);
                    ftk = ftm * (1 - 1.645 * delta_c);

                    text_cv.setText(String.valueOf(delta_c));
                    text_fck.setText(String.valueOf(fck));
                    text_ftk.setText(String.valueOf(ftk));
                }
            }
            if (radio_dingyu_method.isSelected()){
                //使用丁-余方法时
                fcm = MaterialParameter_Ding_Yu.GetFcm(fcum);
                ftm = MaterialParameter_Ding_Yu.GetFtm(fcum);

                if (!radio_userDetermined.isSelected()){
                    text_cv.setText("");
                    text_fck.setText("");
                    text_ftk.setText("");
                }else {
                    delta_c=Double.valueOf(text_userDetermined.getText());
                    fcuk = fcum * (1 - 1.645 * delta_c);
                    fck = fcm * (1 - 1.645 * delta_c);
                    ftk = ftm * (1 - 1.645 * delta_c);

                    text_cv.setText(String.valueOf(delta_c));
                    text_fck.setText(String.valueOf(fck));
                    text_ftk.setText(String.valueOf(ftk));
                }
                text_fcm.setText(String.valueOf(fcm));
                text_ftm.setText(String.valueOf(ftm));
                text_fcum.setText(String.valueOf(fcum));
            }

        }
        //
//        System.out.println("立方体抗压强度标准值fcuk："+fcuk);
//        System.out.println("抗压强度标准值fck："+fck);
//        System.out.println("抗拉强度标准值ftk："+ftk);
//        System.out.println("立方体抗压强度平均值fcum："+fcum);
//        System.out.println("抗压强度平均值fcm："+fcm);
//        System.out.println("抗压强度平均值ftm："+ftm);
        String key="";
        String value="";

        StringBuilder sb=new StringBuilder();
        key="立方体抗压强度标准值fcuk：";
        value=String.valueOf(fcuk);
        sb.append(key+value+"\r\n");

        key="轴心抗压强度标准值fck：";
        value=String.valueOf(fck);
        sb.append(key+value+"\r\n");

        key="轴心抗拉强度标准值ftk：";
        value=String.valueOf(ftk);
        sb.append(key+value+"\r\n");

        key="立方体抗压强度平均值fcum：";
        value=String.valueOf(fcum);
        sb.append(key+value+"\r\n");

        key="轴心抗压强度平均值fcm：";
        value=String.valueOf(fcm);
        sb.append(key+value+"\r\n");

        key="轴心抗拉强度平均值ftm：";
        value=String.valueOf(ftm);
        sb.append(key+value+"\r\n");
        sb.append("*********************其他参数来源*********************\r\n");
        if (radio_testDtrength.isSelected()){
            //选择材料参数来源
            key="材料强度参数来源：";
            value="来源于试验强度值";
            sb.append(key+value+"\r\n");
        }
        if (radio_designDtrength.isSelected()){
            //选择材料参数来源
            key="材料强度参数来源：";
            value="来源于设计强度值";
            sb.append(key+value+"\r\n");
        }

        if (radio_GB50010_2010_method.isSelected()){
            //选择计算方法
            key="计算方法为：";
            value="GB50010-2010（<=60）";
            sb.append(key+value+"\r\n");
        }

        if (radio_dingyu_method.isSelected()){
            //选择计算方法
            key="计算方法为：";
            value="丁发兴-余志武方法（<=140）";
            sb.append(key+value+"\r\n");
        }

        if (radio_GB50010_2010.isSelected()){
            key="变异系数来源于：";
            value="GB50010-2010";
            sb.append(key+value+"\r\n");
        }
        if (radio_GB50010_2002.isSelected()){
            key="变异系数来源于：";
            value="GB50010-2002";
            sb.append(key+value+"\r\n");
        }
        if (radio_notConsidered.isSelected()){
            key="变异系数来源于：";
            value="不考虑变异系数";
            sb.append(key+value+"\r\n");
        }
        if (radio_userDetermined.isSelected()){
            key="变异系数来源于：";
            value="用户自定义的值";
            sb.append(key+value+"\r\n");
        }
        if (radio_088_yes.isSelected()){
            key="是否考虑0.88折减系数：";
            value="考虑";
        }else {
            key="是否考虑0.88折减系数：";
            value="不考虑";
        }
        sb.append(key+value+"\r\n");
        if (radio_brittlement_yes.isSelected()){
            key="是否考虑脆性折减系数：";
            value="考虑";
        }else {
            key="是否考虑脆性折减系数：";
            value="不考虑";
        }
        sb.append(key+value+"\r\n");

        //
        key="α_c1：";
        value=String.valueOf(alfa_C1);
        sb.append(key+value+"\r\n");
        key="α_c2：";
        value=String.valueOf(alfa_C2);
        sb.append(key+value+"\r\n");

        //获取当前文件路径写入得到的参数
        String filepath = System.getProperty("user.dir");
        System.out.println(filepath);
        File file=new File(filepath+"\\1.txt");
        FileWriter fw=null;
        try {
            fw=new FileWriter(file);
            fw.write(sb.toString());
            fw.flush();
            //System.out.println("完成写入！！");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fw!=null){
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }//方法结束



    //用户清除所有文本框信息
    private void clearText(){
        //text_userDetermined.setText("");
        //text_standardFcuk.setText("50");
        text_fcu150.setText("");
        text_fcu100.setText("");
        text_fcu200.setText("");
        text_fcu150_cal.setText("");
        text_cv.setText("");
        text_fck.setText("");
        text_ftk.setText("");
        text_fcum.setText("");
        text_fcm.setText("");
        text_ftm.setText("");
        text_ac1.setText("");
        text_ac2.setText("");
    }
}






















