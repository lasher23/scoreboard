
package ch.uhc_yetis.view;

import ch.uhc_yetis.view.settings.controller.style.ControllerStyleProvider;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScoreboardControllerController extends Stage {
	private ScoreboardController controller;
	@FXML
	private Label thirdCount;
	@FXML
	private Label homeCount;
	@FXML
	private Label guestCount;
	@FXML
	private Label time;
	@FXML
	private VBox root;

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
		controller.startNewGame();
		thirdCount.textProperty().bind(controller.getThirdCount());
		homeCount.textProperty().bind(controller.getHomeCount());
		guestCount.textProperty().bind(controller.getGuestCount());
		time.textProperty().bind(controller.getTime());
		ControllerStyleProvider.getInstance().addSizeChangeListener(x -> root.setStyle("-fx-font-size:" + x + ";"));
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
