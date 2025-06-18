package com.nep;

import com.nep.controller.InitController;
import fr.brouillard.oss.cssfx.CSSFX;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Login extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		CSSFX.start();

//		UserAgentBuilder.builder()
//			.themes(JavaFXThemes.MODENA)
//			.themes(MaterialFXStylesheets.forAssemble(true))
//			.setDeploy(true)
//			.setResolveAssets(true)
//			.build()
//			.setGlobal();


		FXMLLoader loader = new FXMLLoader(MFXDemoResourcesLoader.loadURL("view/Demo.fxml"));
		loader.setControllerFactory(c -> new InitController(primaryStage));
		Parent root = loader.load();

		Scene scene = new Scene(root);
		scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/nep/css/Notifications.css")).toExternalForm());
		MFXThemeManager.addOn(scene, Themes.DEFAULT);
		scene.setFill(Color.TRANSPARENT);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");
		primaryStage.show();

		//ScenicView.show(scene);
	}
}
