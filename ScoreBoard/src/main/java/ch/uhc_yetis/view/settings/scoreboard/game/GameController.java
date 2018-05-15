/**
 * File Name: GameController.java
 * 
 * Copyright (c) 2018 BISON Schweiz AG, All Rights Reserved.
 */

package ch.uhc_yetis.view.settings.scoreboard.game;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 *
 * @author nicolas.schmid
 */
public class GameController extends VBox {
	@FXML
	private TextField pauseDuration;
	@FXML
	private TextField thirdDuration;

	public GameController() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Game.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			loader.load();
			GameSettingsProvider.PAUSE_DURATION.set(300000);
			GameSettingsProvider.THIRD_DURATION.set(1200000);
			pauseDuration.setText("05:00");
			thirdDuration.setText("20:00");
			pauseDuration.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
				try {
					GameSettingsProvider.PAUSE_DURATION.set(TimeStringToLong.getLongFromString(newValue));
					pauseDuration.setStyle("");
				} catch (InvalidFormatException e) {
					String contentText = "Pause Duration must be a whole number";
					String headerText = "Invalid value";
					pauseDuration.setStyle("-fx-text-box-border: red ;-fx-focus-color: red ;");
				}
			});
			thirdDuration.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
				try {
					GameSettingsProvider.THIRD_DURATION.set(TimeStringToLong.getLongFromString(newValue));
					thirdDuration.setStyle("");
				} catch (InvalidFormatException e) {
					String contentText = "Pause Duration must be a whole number";
					String headerText = "Invalid value";
					thirdDuration.setStyle("-fx-text-box-border: red ;-fx-focus-color: red ;");
				}
			});
		} catch (Exception e) {
		}
	}

	private void showError(String contentText, String headerText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.show();
	}

	private long formatToDate() {
		return GameSettingsProvider.PAUSE_DURATION.get();
	}
}
