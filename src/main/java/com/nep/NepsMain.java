package com.nep;

import javafx.application.Application;
import javafx.stage.Stage;
import com.nep.controller.NepsLoginViewController;
import com.nep.util.JavafxUtil;

import java.io.IOException;

/**
 * 东软环保公众监督平台-公众监督员端 应用程序启动
 *
 * @author
 *
 */

public class NepsMain extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        NepsLoginViewController.primaryStage = primaryStage;
        JavafxUtil.showStage(NepsMain.class,"view/NepsLoginView.fxml", primaryStage, "东软环保公众监督平台-公众监督员端");
    }

    public static void main(String[] args) {
        launch(args);
    }
}