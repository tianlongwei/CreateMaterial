package my.loong;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import my.loong.view.RootLayoutController;

import java.io.IOException;


public class MyMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Stage stage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.stage=primaryStage;
        initiaRootLayout();
        showTab1();
    }



    //初始化主容器
    public void initiaRootLayout(){
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(MyMain.class.getResource("view/RootLayout.fxml"));
        try {
            rootLayout=loader.load();
            //加载控制器。。
            RootLayoutController controller=loader.getController();
            controller.setMyMain(this);

            Scene scene=new Scene(rootLayout,678,600);
            stage.setScene(scene);
            stage.setTitle("CreateMaterial");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //显示tab1
    public void showTab1(){
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(MyMain.class.getResource("view/Tab1.fxml"));
        try {
            AnchorPane pane=loader.load();

            rootLayout.setCenter(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //显示tab2
    public void showTab2(){
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("view/Tab2.fxml"));
        try {
            AnchorPane pane=loader.load();
            rootLayout.setCenter(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
