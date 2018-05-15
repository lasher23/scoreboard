package ch.uhc_yetis.view.settings.scoreboard.screen;

import ch.uhc_yetis.view.ScoreboardController;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

public class ScreenController extends VBox {
	@FXML
	private ComboBox<Screen> screenBox;

	public ScreenController(ScoreboardController scoreboard) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Screen.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			loader.load();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		ObservableList<Screen> screens = FXCollections.observableArrayList(Screen.getScreens());
		screenBox.getItems().addAll(screens);
		screenBox.valueProperty().addListener((ChangeListener<Screen>) (observable, oldValue, newValue) -> {
			scoreboard.setX(newValue.getVisualBounds().getMinX());
			scoreboard.setY(newValue.getVisualBounds().getMinY());
		});
		screenBox.setValue(getNotPrimaryScreen());
	}

	private Screen getNotPrimaryScreen() {
		for (Screen screen : Screen.getScreens()) {
			if (!screen.equals(Screen.getPrimary())) {
				return screen;
			}
		}
		return Screen.getPrimary();
	}
}
