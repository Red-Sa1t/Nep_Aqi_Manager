package com.nep.manager;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TipsManager {

    private static TipsManager instance;
    private AnchorPane rootStackPane;
    private Stage stage;

    private TipsManager() {
    }

    public static TipsManager getInstance() {
        if (instance == null) {
            synchronized (TipsManager.class) {
                if (instance == null) {
                    instance = new TipsManager();
                }
            }
        }
        return instance;
    }

    public void init(AnchorPane rootStackPane, Stage stage) {
        this.rootStackPane = rootStackPane;
        this.stage = stage;
    }

    private void showDialog(String title, String message, String iconName) {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));

        Label label = new Label(message);
        MFXButton confirmBtn = new MFXButton("确定");
        confirmBtn.setStyle("""
                -fx-background-color: -mfx-purple;
                -fx-text-fill: white
                """);
        confirmBtn.setPrefWidth(80);

        StackPane content = new StackPane();
        content.setPadding(new Insets(10));
        content.getChildren().add(vbox);

        vbox.getChildren().addAll(label, confirmBtn);

        MFXGenericDialog dialogContent = MFXGenericDialogBuilder.build()
                .setHeaderText(title)
                .setHeaderIcon(new MFXFontIcon(iconName, 18))
                .setContent(content)
                .setShowClose(false)
                .get();

        MFXStageDialog dialog = MFXGenericDialogBuilder.build(dialogContent)
                .toStageDialogBuilder()
                .initOwner(stage)
                .initModality(Modality.APPLICATION_MODAL)
                .setDraggable(true)
                .setTitle(title)
                .setOwnerNode(rootStackPane)
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimOwner(true)
                .get();

        confirmBtn.setOnAction(e -> dialog.close());

        dialog.showDialog();
    }

    public void showInfo(String message) {
        showDialog("提示", message, "fas-info-circle");
    }

    public void showWarning(String message) {
        showDialog("警告", message, "fas-exclamation-triangle");
    }

    public void showError(String message) {
        showDialog("错误", message, "fas-times-circle");
    }
}
