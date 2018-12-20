package my.loong.view;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import my.loong.MyMain;
import my.loong.commons.GB_50936_Method;

/**
 * @program: CreateMaterial
 * @description:tab1页面控制器类
 * @AUTHOR: tlw
 * @create: 2018-11-29 16:22
 */
public class Tab1Controller {
    private MyMain myMain;

    public void setMyMain(MyMain myMain) {
        this.myMain = myMain;
    }

//    //本构曲线计算
//    @FXML
//    private Button button_bengou_cal;
//    @FXML
//    private void button_bengou_cal_clicked(){
//        //显示本构曲线计算页面
//        myMain.initiaRootLayout_third();
//        myMain.showTab2();
//    }

    @FXML
    private void initialize(){
        radio_zhouya.setSelected(true);

        showAll();
        text_H.setDisable(true);
        text_e.setDisable(true);
        text_M.setDisable(true);
    }


    /***********************************************************/
    /************************几何尺寸输入************************/
    /***********************************************************/
    @FXML
    private TextField text_D;
    @FXML
    private TextField text_B;
    @FXML
    private TextField text_t;
    @FXML
    private TextField text_fy;
    @FXML
    private TextField text_fcu;
    @FXML
    private TextField text_H;

    /***********************************************************/
    /************************受力类型****************************/
    /***********************************************************/
    @FXML
    private RadioButton radio_zhouya;
    @FXML
    private RadioButton radio_chunwan;
    @FXML
    private RadioButton radio_zhouwan;

    @FXML
    private void radio_zhouya_clicked(){
        radio_zhouya.setSelected(true);
        radio_chunwan.setSelected(false);
        radio_zhouwan.setSelected(false);

        showAll();
        text_H.setDisable(true);
        text_e.setDisable(true);
        text_M.setDisable(true);
    }
    @FXML
    private void radio_chunwan_clicked(){
        radio_chunwan.setSelected(true);
        radio_zhouya.setSelected(false);
        radio_zhouwan.setSelected(false);

        showAll();
        text_H.setDisable(true);
        text_e.setDisable(true);
        text_N.setDisable(true);
    }
    @FXML
    private void radio_zhouwan_clicked(){
        radio_zhouwan.setSelected(true);
        radio_zhouya.setSelected(false);
        radio_chunwan.setSelected(false);

        showAll();
        text_M.setDisable(true);
    }


    private void showAll(){
        text_H.setDisable(false);//
        text_e.setDisable(false);
        text_M.setDisable(false);
        text_N.setDisable(false);
    }

    /***********************************************************/
    /************************荷载*******************************/
    /***********************************************************/
    @FXML
    private TextField text_N;
    @FXML
    private TextField text_e;//
    @FXML
    private TextField text_M;
    @FXML
    private TextArea result;//计算结果显示


    @FXML
    private void handled_cal(){
        //获取参数输入值
        double B=0;
        double D=0;
        double t=0;
        double fy=0;
        double fcu=0;

        double N=0;
        double M=0;
        double e=0;
        double H=0;

        try {
            B=Double.valueOf(text_B.getText());
            D=Double.valueOf(text_D.getText());
            t=Double.valueOf(text_t.getText());
            fy=Double.valueOf(text_fy.getText());
            fcu=Double.valueOf(text_fcu.getText());
        }catch (Exception e1){
            e1.printStackTrace();
            MessageBox.showBox("输入参数不合法或者未全部输入");
            return;
        }
        GB_50936_Method.setB(B);
        GB_50936_Method.setD(D);
        GB_50936_Method.setT(t);
        GB_50936_Method.setFy(fy);
        GB_50936_Method.setFcu(fcu);


        if (radio_zhouya.isSelected()){
            try {
                N=Double.valueOf(text_N.getText());
            }catch (Exception ee){
                ee.printStackTrace();
                MessageBox.showBox("请输入参数N");
                return;
            }
            GB_50936_Method.zhouya_cal(N);
            result.setText(GB_50936_Method.zhouya2str(N));


        }else if (radio_chunwan.isSelected()){
            try {
                M=Double.valueOf(text_M.getText());
            }catch (Exception ee){
                ee.printStackTrace();
                MessageBox.showBox("请输入参数M");
                return;
            }

            GB_50936_Method.chunwan_cal(Double.valueOf(M));
            result.setText(GB_50936_Method.chunwan2str(Double.valueOf(M)));
        }else if (radio_zhouwan.isSelected()){
            try {
                N=Double.valueOf(text_N.getText());
                e=Double.valueOf(text_e.getText());
                H=Double.valueOf(text_H.getText());
            }catch (Exception ee){
                ee.printStackTrace();
                MessageBox.showBox("请输入参数N或e");
                return;
            }
            GB_50936_Method.setH(H);
            GB_50936_Method.zhouwan_cal(N,e);
            result.setText(GB_50936_Method.yawan2str());
        }

    }

}