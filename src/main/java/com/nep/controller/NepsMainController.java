package com.nep.controller;

//import io.github.palexdev.materialfx.controls.MFXIconWrapper;
//import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
//import io.github.palexdev.materialfx.controls.MFXScrollPane;
//import io.github.palexdev.materialfx.demo.MFXDemoResourcesLoader;
//import io.github.palexdev.materialfx.utils.ScrollUtils;
//import io.github.palexdev.materialfx.utils.ToggleButtonsUtil;
//import io.github.palexdev.materialfx.utils.others.loader.MFXLoader;
//import io.github.palexdev.materialfx.utils.others.loader.MFXLoaderBean;
//import io.github.palexdev.mfxresources.fonts.MFXFontIcon;

import com.nep.MFXDemoResourcesLoader;
import com.nep.manager.TipsManager;
import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import io.github.palexdev.materialfx.utils.ToggleButtonsUtil;
import io.github.palexdev.materialfx.utils.others.loader.MFXLoader;
import io.github.palexdev.materialfx.utils.others.loader.MFXLoaderBean;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.nep.MFXDemoResourcesLoader.loadURL;

public class NepsMainController implements Initializable {
    private static Stage stage;
    private final ToggleGroup toggleGroup;
    private double xOffset;
    private double yOffset;
    @FXML
    private HBox windowHeader;

    @FXML
    private MFXFontIcon closeIcon;

    @FXML
    private MFXFontIcon minimizeIcon;

    @FXML
    private MFXFontIcon alwaysOnTopIcon;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private MFXScrollPane scrollPane;

    @FXML
    private VBox navBar;

    @FXML
    private StackPane contentPane;

    @FXML
    private StackPane logoContainer;

    public NepsMainController(Stage stage) {
        NepsMainController.stage = stage;
        this.toggleGroup = new ToggleGroup();
        ToggleButtonsUtil.addAlwaysOneSelectedSupport(toggleGroup);
    }

    public static void CloseStage() {
        NepsMainController.stage.close();
    }

    public static Stage GetStage() {
        return NepsMainController.stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TipsManager.getInstance().init(rootPane, stage);
        closeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> Platform.exit());
        minimizeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> ((Stage) rootPane.getScene().getWindow()).setIconified(true));
        alwaysOnTopIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            boolean newVal = !stage.isAlwaysOnTop();
            alwaysOnTopIcon.pseudoClassStateChanged(PseudoClass.getPseudoClass("always-on-top"), newVal);
            stage.setAlwaysOnTop(newVal);
        });

        windowHeader.setOnMousePressed(event -> {
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });
        windowHeader.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });

        initializeLoader();

        ScrollUtils.addSmoothScrolling(scrollPane);

        Image image = new Image(MFXDemoResourcesLoader.load("logo_alt.png"), 100, 100, true, true);
        ImageView logo = new ImageView(image);
        Circle clip = new Circle(50, 50, 50);

        logo.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            double centerX = newValue.getMinX() + newValue.getWidth() / 2;
            double centerY = newValue.getMinY() + newValue.getHeight() / 2;

            clip.setCenterX(centerX);
            clip.setCenterY(centerY);
        });

        logo.setClip(clip);
        logoContainer.getChildren().add(logo);

    }

    private void initializeLoader() {
        MFXLoader loader = new MFXLoader();
//        loader.addView(MFXLoaderBean.of("用户登录", loadURL("view/NepLogin.fxml")).setBeanToNodeMapper(() -> createToggle("fas-stairs", "用户登录")).setDefaultRoot(true).get());
//        loader.addView(MFXLoaderBean.of("公众监督AQI反馈数据列表", loadURL("view/NepmAqiInfoView.fxml")).setBeanToNodeMapper(() -> createToggle("fas-circle-dot", "公共监督AQI反馈数据列表")).get());
//        loader.addView(MFXLoaderBean.of("公众监督AQI反馈数据指派", loadURL("view/NepmAqiAssignView.fxml")).setBeanToNodeMapper(() -> createToggle("fas-toggle-on", "公众监督AQI反馈数据指派")).get());
        loader.addView(MFXLoaderBean.of("历史反馈预估AQI信息列表", loadURL("view/NepsFeedbackView.fxml")).setBeanToNodeMapper(() -> createToggle("fas-italic", "历史反馈预估AQI信息列表")).setDefaultRoot(true).get());
        loader.addView(MFXLoaderBean.of("公众监督员信息反馈", loadURL("view/NepsSelectAqiView.fxml")).setBeanToNodeMapper(() -> createToggle("fas-rectangle-list", "公众监督员信息反馈")).get());
        loader.addView(MFXLoaderBean.of("AQI监督报告提交", loadURL("view/NepsReportDetailViewController.fxml")).setBeanToNodeMapper(() -> createToggle("fas-comments", "AQI监督报告提交")).get());
        loader.addView(MFXLoaderBean.of("AQI监督报告浏览", loadURL("view/NepReportView.fxml")).setBeanToNodeMapper(() -> createToggle("fas-square-caret-down", "AQI监督报告浏览")).setControllerFactory(c -> new NepReportViewController(stage)).get());
        loader.addView(MFXLoaderBean.of("切换账号", loadURL("view/NepLogin.fxml")).setBeanToNodeMapper(() -> createToggle("fas-stairs", "切换账号")).get());
        loader.setOnLoadedAction(beans -> {
            List<ToggleButton> nodes = beans.stream()
                    .map(bean -> {
                        ToggleButton toggle = (ToggleButton) bean.getBeanToNodeMapper().get();
                        toggle.setOnAction(event -> contentPane.getChildren().setAll(bean.getRoot()));
                        if (bean.isDefaultView()) {
                            contentPane.getChildren().setAll(bean.getRoot());
                            toggle.setSelected(true);
                        }
                        return toggle;
                    })
                    .toList();
            navBar.getChildren().setAll(nodes);
        });
        loader.start();
    }

    private ToggleButton createToggle(String icon, String text) {
        return createToggle(icon, text, 0);
    }

    private ToggleButton createToggle(String icon, String text, double rotate) {
        MFXIconWrapper wrapper = new MFXIconWrapper(icon, 24, 32);
        MFXRectangleToggleNode toggleNode = new MFXRectangleToggleNode(text, wrapper);
        toggleNode.setAlignment(Pos.CENTER_LEFT);
        toggleNode.setMaxWidth(Double.MAX_VALUE);
        toggleNode.setToggleGroup(toggleGroup);
        if (rotate != 0) wrapper.getIcon().setRotate(rotate);
        return toggleNode;
    }
}
