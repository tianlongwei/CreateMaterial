package my.loong;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import my.loong.view.Page1Controller;
import my.loong.view.RootLayoutController;
import my.loong.view.Tab1Controller;

import java.io.IOException;


public class MyMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Stage stage_first;
    private Stage stage_second=new Stage();
    private Stage stage_third=new Stage();
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.stage_first=primaryStage;
        //initiaRootLayout();
        //showTab1();
        showPage1();
    }



    //初始化主容器
    public void initiaRootLayout_second(){
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(MyMain.class.getResource("view/RootLayout_second.fxml"));
        try {
            rootLayout=loader.load();
            //加载控制器。。
            RootLayoutController controller=loader.getController();
            controller.setMyMain(this);

            Scene scene=new Scene(rootLayout);
            stage_second.setScene(scene);
            stage_second.setTitle("CreateMaterial");
            stage_second.setResizable(false);
            stage_second.show();

            stage_second.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    stage_first.show();
                }
            });
            this.stage_first.hide();//隐藏


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //初始化主容器
    public void initiaRootLayout_third(){
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(MyMain.class.getResource("view/RootLayout.fxml"));
        try {
            rootLayout=loader.load();
            //加载控制器。。
            RootLayoutController controller=loader.getController();
            controller.setMyMain(this);

            Scene scene=new Scene(rootLayout);
            stage_third.setScene(scene);
            stage_third.setTitle("CreateMaterial");
            stage_third.setResizable(false);
            stage_third.show();

            stage_third.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    stage_second.show();
                }
            });
            this.stage_second.hide();//隐藏


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //显示page1页面
    private void showPage1(){
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("view/page1.fxml"));
        try {
            AnchorPane pane=loader.load();

            Page1Controller controller=loader.getController();
            controller.setMyMain(this);

            Scene scene=new Scene(pane);
            stage_first.setScene(scene);
            stage_first.setResizable(false);
            stage_first.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //显示page2页面
    private void showPage2(){


    }


    //显示tab1
    public void showTab1(){
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(MyMain.class.getResource("view/Tab1.fxml"));
        try {
            AnchorPane pane=loader.load();

            Tab1Controller controller=loader.getController();
            controller.setMyMain(this);


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

    //显示承载力验算页面
    public void showTab3(){
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("view/Tab3.fxml"));
        try {
            AnchorPane pane=loader.load();
            rootLayout.setCenter(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
