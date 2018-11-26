package my.loong.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * @program: CreateMaterial
 * @description:
 * @AUTHOR: tlw
 * @create: 2018-11-22 10:22
 */
public class MessageBox extends Alert {
    public MessageBox(AlertType alertType) {
        super(alertType);
    }

    public MessageBox(AlertType alertType, String contentText, ButtonType... buttons) {
        super(alertType, contentText, buttons);
    }

    public static void showBox(String message){
        MessageBox messageBox=new MessageBox(AlertType.INFORMATION);
        messageBox.setTitle("注意");
        messageBox.setContentText(message);
        messageBox.showAndWait();
    }
}