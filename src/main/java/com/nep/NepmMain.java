package com.nep;

import javafx.application.Application;
import javafx.stage.Stage;
import com.nep.controller.NepmLoginViewController;
import com.nep.util.JavafxUtil;

import java.io.IOException;

/**
 * 东软环保公众监督平台-管理员端 应用程序启动
 *
 * @author
 *
 */
public class NepmMain extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        NepmLoginViewController.primaryStage = primaryStage;
        JavafxUtil.showStage(NepmMain.class,"view/NepmLoginView.fxml", primaryStage, "东软环保公众监督平台-管理端");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
