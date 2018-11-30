package my.loong.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import my.loong.MyMain;

import java.net.MalformedURLException;

/**
 * @program: CreateMaterial
 * @description:
 * @AUTHOR: tlw
 * @create: 2018-11-18 21:33
 */
public class RootLayoutController {

    @FXML
    private Button next;
    @FXML
    private Button privous;

    @FXML
    private Button exited;
    private MyMain myMain;

    //用户统计页数信息
    int index=2;

    public void setMyMain(MyMain myMain){
        this.myMain=myMain;
    }


    @FXML
    private void handledNext() throws MalformedURLException {
        index++;
        if (index==3){
            myMain.showTab3();
            return;
        }
        if (index>3){
            MessageBox.showBox("已经是最后一页内容");
            index=3;
        }


    }

    @FXML
    private void handledPrivous(){
        index--;
        if (index==2){
            myMain.showTab2();
            return;
        }
        if (index<2){
            MessageBox.showBox("已经是第一页内容");
            index=2;
        }
    }

    @FXML
    private void handledExited(){
        System.exit(0);
    }


}