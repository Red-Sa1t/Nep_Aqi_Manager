package com.nep.controller;

import com.nep.manager.TipsManager;
import com.nep.po.GridMember;
import com.nep.service.AdminService;
import com.nep.service.GridMemberService;
import com.nep.service.SupervisorService;
import com.nep.service.impl.AdminServiceImpl;
import com.nep.service.impl.GridMemberServiceImpl;
import com.nep.service.impl.SupervisorServiceImpl;
import com.nep.util.MFXDemoResourcesLoader;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginController {

    // 主舞台
    public static Stage primaryStage;
    // 服务层
    private final SupervisorService supervisorService = new SupervisorServiceImpl();
    private final AdminService adminService = new AdminServiceImpl();
    private final GridMemberService gridMemberService = new GridMemberServiceImpl();
    // UI 组件
    @FXML
    private MFXRadioButton rb_admin;
    @FXML
    private MFXRadioButton rb_supervisor;
    @FXML
    private MFXRadioButton rb_grid;
    @FXML
    private TextField txt_id;
    @FXML
    private MFXPasswordField txt_password;

    /**
     * 初始化方法
     */
    @FXML
    public void initialize() {
        setupDefaultSelection();
    }

    /**
     * 设置默认选中的角色
     */
    private void setupDefaultSelection() {
        rb_admin.setSelected(true);
    }

    /**
     * 处理登录按钮事件
     */
    @FXML
    public void login() {
        String selectedRole = getSelectedRole();
        String username = txt_id.getText().trim();
        String password = txt_password.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            TipsManager.getInstance().showError("请输入用户名和密码");
            return;
        }

        switch (selectedRole) {
            case "admin":
                handleAdminLogin(username, password);
                break;
            case "supervisor":
                handleSupervisorLogin(username, password);
                break;
            case "grid":
                handleGridMemberLogin(username, password);
                break;
            default:
                TipsManager.getInstance().showError("请选择有效的登录角色");
        }
    }

    /**
     * 处理管理员登录
     */
    private void handleAdminLogin(String username, String password) {
        if (adminService.login(username, password)) {
            //SceneManager.getInstance().addScene("管理员界面","view/NepmAqiAssignView.fxml","fas-arrow-right-to-city","公众监督AQI反馈数据指派");

            try {
                FXMLLoader loader = new FXMLLoader(MFXDemoResourcesLoader.loadURL("view/NepmMainView.fxml"));
                Stage newStage = new Stage();
                loader.setControllerFactory(c -> new NepmMainController(newStage));
                Parent root = loader.load();

                Scene newScene = new Scene(root);
                MFXThemeManager.addOn(newScene, Themes.DEFAULT);
                newScene.setFill(Color.TRANSPARENT);
                newStage.initStyle(StageStyle.TRANSPARENT);
                newStage.setScene(newScene);
                newStage.setTitle("Main");
                newStage.show();

                InitController.stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            TipsManager.getInstance().showError("管理员账号或密码错误");
        }
    }

    /**
     * 处理监督员登录
     */
    private void handleSupervisorLogin(String username, String password) {
        if (supervisorService.login(username, password)) {

            try {
                FXMLLoader loader = new FXMLLoader(MFXDemoResourcesLoader.loadURL("view/NepsMainView.fxml"));
                Stage newStage = new Stage();
                loader.setControllerFactory(c -> new NepsMainController(newStage));
                Parent root = loader.load();

                Scene newScene = new Scene(root);
                MFXThemeManager.addOn(newScene, Themes.DEFAULT);
                newScene.setFill(Color.TRANSPARENT);
                newStage.initStyle(StageStyle.TRANSPARENT);
                newStage.setScene(newScene);
                newStage.show();

                InitController.stage.close();
                // 关闭当前窗口
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            TipsManager.getInstance().showError("监督员账号或密码错误");
        }
    }

    /**
     * 处理网格员登录
     */

    private void handleGridMemberLogin(String username, String password) {
        GridMember member = gridMemberService.login(username, password);
        if (member != null) {
            NepgAqiConfirmViewController.gridMember = member;


            try {
                FXMLLoader loader = new FXMLLoader(MFXDemoResourcesLoader.loadURL("view/NepgMainView.fxml"));
                Stage newStage = new Stage();
                loader.setControllerFactory(c -> new NepgMainController(newStage));
                Parent root = loader.load();

                Scene newScene = new Scene(root);
                MFXThemeManager.addOn(newScene, Themes.DEFAULT);
                newScene.setFill(Color.TRANSPARENT);
                newStage.initStyle(StageStyle.TRANSPARENT);
                newStage.setScene(newScene);
                newStage.show();

                InitController.stage.close();
                // 关闭当前窗口
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            TipsManager.getInstance().showError("网格员账号或密码错误");
        }
    }

    /**
     * 获取当前选择的角色
     */
    private String getSelectedRole() {
        if (rb_admin.isSelected()) {
            return "admin";
        } else if (rb_supervisor.isSelected()) {
            return "supervisor";
        } else if (rb_grid.isSelected()) {
            return "grid";
        }
        return "";
    }

    @FXML
    private void handleEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            login(); // 调用登录方法
        }
    }
}