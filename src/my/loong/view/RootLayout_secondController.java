package my.loong.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import my.loong.MyMain;

/**
 * @program: CreateMaterial
 * @description:
 * @AUTHOR: tlw
 * @create: 2018-12-20 14:09
 */
public class RootLayout_secondController {

    @FXML
    private Button exited;
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
    private void handledExited(){
        System.exit(0);
    }

}