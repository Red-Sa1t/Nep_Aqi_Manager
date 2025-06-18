/*
 * Copyright (C) 2022 Parisi Alessandro
 * This file is part of MaterialFX (https://github.com/palexdev/MaterialFX).
 *
 * MaterialFX is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MaterialFX is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with MaterialFX.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nep;

import com.nep.controller.InitController;
import com.nep.util.MFXDemoResourcesLoader;
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
