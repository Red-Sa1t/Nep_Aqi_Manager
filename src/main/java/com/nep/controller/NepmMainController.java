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

public class NepmMainController implements Initializable {
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

    public NepmMainController(Stage stage) {
        NepmMainController.stage = stage;
        this.toggleGroup = new ToggleGroup();
        ToggleButtonsUtil.addAlwaysOneSelectedSupport(toggleGroup);
    }

    public static void CloseStage() {
        NepmMainController.stage.close();
    }

    public static Stage GetStage() {
        return NepmMainController.stage;
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
        loader.addView(MFXLoaderBean.of("公众监督AQI反馈数据列表", loadURL("view/NepmAqiInfoView.fxml")).setBeanToNodeMapper(() -> createToggle("fas-circle-dot", "公共监督AQI反馈数据列表")).setControllerFactory(c -> new NepmAqiInfoViewController(stage)).setDefaultRoot(true).get());
        loader.addView(MFXLoaderBean.of("公众监督AQI反馈数据指派", loadURL("view/NepmAqiAssignView.fxml")).setBeanToNodeMapper(() -> createToggle("fas-toggle-on", "公众监督AQI反馈数据指派")).get());
//        loader.addView(MFXLoaderBean.of("管理AQI实测数据信息", loadURL("view/NepgAqiConfirmView.fxml")).setBeanToNodeMapper(() -> createToggle("fas-square-caret-down", "管理AQI实测数据信息")).setDefaultRoot(true).get());
        loader.addView(MFXLoaderBean.of("网格员AQI实测数据列表", loadURL("view/NepmConfirmInfoView.fxml")).setBeanToNodeMapper(() -> createToggle("fas-comments", "网格员AQI实测数据列表")).setControllerFactory(c -> new NepmConfirmInfoViewController(stage)).get());
        loader.addView(MFXLoaderBean.of("AQI监督报告浏览", loadURL("view/NepReportView.fxml")).setBeanToNodeMapper(() -> createToggle("fas-square-caret-down", "AQI监督报告浏览")).setControllerFactory(c -> new NepReportViewController(stage)).get());
        loader.addView(MFXLoaderBean.of("AQI信息分析", loadURL("view/NepmAqiStatistics.fxml")).setBeanToNodeMapper(() -> createToggle("fas-italic", "AQI信息分析")).get());
        loader.addView(MFXLoaderBean.of("CO等级饼状统计", loadURL("view/NepmCoLevel.fxml")).setBeanToNodeMapper(() -> createToggle("fas-rectangle-list", "CO等级饼状统计")).get());
        loader.addView(MFXLoaderBean.of("SO2等级饼状统计", loadURL("view/NepmSo2Level.fxml")).setBeanToNodeMapper(() -> createToggle("fas-bell", "SO2等级饼状统计")).get());
        loader.addView(MFXLoaderBean.of("PM2.5等级饼状统计", loadURL("view/NepmPmLevel.fxml")).setBeanToNodeMapper(() -> createToggle("fas-calendar", "PM2.5等级饼状统计")).get());
        loader.addView(MFXLoaderBean.of("切换账号", loadURL("view/NepLogin.fxml")).setBeanToNodeMapper(() -> createToggle("fas-stairs", "切换账号")).get());//        loader.addView(MFXLoaderBean.of("SCROLL-PANES", loadURL("fxml/ScrollPanes.fxml")).setBeanToNodeMapper(() -> createToggle("fas-bars-progress", "Scroll Panes", 90)).get());
//        loader.addView(MFXLoaderBean.of("SLIDERS", loadURL("fxml/Sliders.fxml")).setBeanToNodeMapper(() -> createToggle("fas-sliders", "Sliders")).get());
        //loader.addView(MFXLoaderBean.of("TABLES", loadURL("view/TableViews.fxml")).setBeanToNodeMapper(() -> createToggle("fas-table", "Tables")).get());
//        loader.addView(MFXLoaderBean.of("FONT-RESOURCES", loadURL("fxml/FontResources.fxml")).setBeanToNodeMapper(() -> createToggle("fas-icons", "Font Resources")).get());
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
