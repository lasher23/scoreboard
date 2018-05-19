
package ch.uhc_yetis;

import ch.uhc_yetis.view.ScoreboardController;
import ch.uhc_yetis.view.ScoreboardControllerController;
import ch.uhc_yetis.view.settings.SettingsController;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Scoreboard extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Font.loadFont(
				getClass().getClassLoader().getResourceAsStream("ressources/DSEG14-Modern/DSEG14Modern-Regular.ttf"),
				250);
		Font.loadFont(
				getClass().getClassLoader().getResourceAsStream("ressources/DSEG14-Modern/DSEG14Modern-Regular.ttf"),
				150);

		ScoreboardController sc = new ScoreboardController();
		ScoreboardControllerController scc = new ScoreboardControllerController(sc);
		sc.setOnCloseRequest(event -> {
			sc.stopTime();
			scc.close();
		});
		scc.setOnCloseRequest(event -> {
			sc.stopTime();
			sc.close();
		});
		SettingsController settingsController = new SettingsController(sc);
		scc.getScene().setOnKeyPressed(event -> {
			if (!settingsController.isShowing() && event.getCode().equals(KeyCode.F11)) {
				settingsController.show();
			}
		});
		sc.show();
		scc.show();
		Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
		scc.setX(visualBounds.getMinX());
		scc.setY(visualBounds.getMinY());

		sc.setMaximized(true);
		scc.setMaximized(true);
	}
}
