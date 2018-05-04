
package ch.uhc_yetis.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScoreboardControllerController extends Stage {
	private ScoreboardController controller;

	public ScoreboardControllerController(ScoreboardController controller) {
		this.controller = controller;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ScoreboardController.fxml"));
			loader.setController(this);
			VBox box = (VBox) loader.load();
			setScene(new Scene(box));
		} catch (Exception e) {
			e.printStackTrace();
		}
		setOnShowing(event -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Neues Spiel starten?");
			ButtonType type = alert.showAndWait().get();
			if (type == ButtonType.OK) {
				controller.startNewGame();
			} else {
				close();
			}
		});
	}

	@FXML
	private void onNextThird() {
		try {
			controller.addThird();
		} catch (GameNotStartedException e) {
		}
	}

	@FXML
	private void onPreviousThird() {
		try {
			controller.undoThird();
		} catch (GameNotStartedException e) {
		}
	}

	@FXML
	private void onGoalHomePlus() {
		try {
			controller.addScoreHome();
		} catch (GameNotStartedException e) {
		}
	}

	@FXML
	private void onGoalHomeMinus() {
		try {
			controller.undoScoreHome();
		} catch (GameNotStartedException e) {
		}
	}

	@FXML
	private void onGoalGuestPlus() {
		try {
			controller.addScoreGuest();
		} catch (GameNotStartedException e) {
		}
	}

	@FXML
	private void onGoalGuestMinus() {
		try {
			controller.undoScoreGuest();
		} catch (GameNotStartedException e) {
		}
	}

	@FXML
	private void onTimeStart() {
		try {
			controller.startTime();
		} catch (GameNotStartedException e) {
		}
	}

	@FXML
	private void onTimeStop() {
		controller.stopTime();
	}

	@FXML
	private void onNewGame() {
		controller.startNewGame();
	}
}
