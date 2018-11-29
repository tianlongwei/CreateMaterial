package my.loong.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import my.loong.MyMain;

/**
 * @program: CreateMaterial
 * @description:page1的控制器类
 * @AUTHOR: tlw
 * @create: 2018-11-29 15:19
 */
public class Page1Controller {

    private MyMain myMain;

    public void setMyMain(MyMain myMain) {
        this.myMain = myMain;
    }

    @FXML
    private Button button_3;//钢管混凝土承载力验算

    @FXML
    private void button_3_cliocked(){
        myMain.initiaRootLayout_second();
        myMain.showTab1();
    }



}