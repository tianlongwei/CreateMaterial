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

    public void setMyMain(MyMain myMain){
        this.myMain=myMain;
    }


    @FXML
    private void handledNext() throws MalformedURLException {
//        next.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//            myMain.showTab2();
//        });
        myMain.showTab2();
    }

    @FXML
    private void handledPrivous(){
        myMain.showTab1();
    }

    @FXML
    private void handledExited(){
        System.exit(0);
    }


}