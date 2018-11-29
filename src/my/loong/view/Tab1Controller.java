package my.loong.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import my.loong.MyMain;

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
}