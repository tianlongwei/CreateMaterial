package my.loong.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
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

    //本构曲线计算
    @FXML
    private Button button_bengou_cal;
    @FXML
    private void button_bengou_cal_clicked(){
        //显示本构曲线计算页面
        myMain.initiaRootLayout_third();
        myMain.showTab2();
    }

    @FXML
    private void initialize(){
        radio_zhouya.setSelected(true);
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
    }
    @FXML
    private void radio_chunwan_clicked(){
        radio_chunwan.setSelected(true);
        radio_zhouya.setSelected(false);
        radio_zhouwan.setSelected(false);
    }
    @FXML
    private void radio_zhouwan_clicked(){
        radio_zhouwan.setSelected(true);
        radio_zhouya.setSelected(false);
        radio_chunwan.setSelected(false);
    }

    /***********************************************************/
    /************************荷载*******************************/
    /***********************************************************/
    @FXML
    private TextField text_N;
    @FXML
    private TextField text_M;


    @FXML
    private void handled_cal(){
        GB_50936_Method.setB(Double.valueOf(text_B.getText()));
        GB_50936_Method.setD(Double.valueOf(text_D.getText()));
        GB_50936_Method.setT(Double.valueOf(text_t.getText()));
        GB_50936_Method.setFy(Double.valueOf(text_fy.getText()));
        GB_50936_Method.setFcu(Double.valueOf(text_fcu.getText()));


        GB_50936_Method.setH(Double.valueOf(text_H.getText()));


        if (radio_zhouya.isSelected()){
            GB_50936_Method.zhouya_cal(Double.valueOf(text_N.getText()));
        }else if (radio_chunwan.isSelected()){
            GB_50936_Method.chunwan_cal(Double.valueOf(text_M.getText()));
        }else if (radio_zhouwan.isSelected()){
            GB_50936_Method.zhouwan_cal(Double.valueOf(text_N.getText()),Double.valueOf(text_M.getText()));
        }

    }

}