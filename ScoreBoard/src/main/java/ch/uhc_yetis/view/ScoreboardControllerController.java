
package ch.uhc_yetis.view;

import ch.uhc_yetis.view.settings.controller.style.ControllerStyleProvider;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
			thirdCount.requestFocus();
		} catch (GameNotStartedException e) {
		}
	}

	@FXML
	private void onPreviousThird() {
		try {
			controller.undoThird();
			thirdCount.requestFocus();
		} catch (GameNotStartedException e) {
		}
	}

	@FXML
	private void onGoalHomePlus() {
		try {
			controller.addScoreHome();
			thirdCount.requestFocus();
		} catch (GameNotStartedException e) {
		}
	}

	@FXML
	private void onGoalHomeMinus() {
		try {
			controller.undoScoreHome();
			thirdCount.requestFocus();
		} catch (GameNotStartedException e) {
		}
	}

	@FXML
	private void onGoalGuestPlus() {
		try {
			controller.addScoreGuest();
			thirdCount.requestFocus();
		} catch (GameNotStartedException e) {
		}
	}

	@FXML
	private void onGoalGuestMinus() {
		try {
			controller.undoScoreGuest();
			thirdCount.requestFocus();
		} catch (GameNotStartedException e) {
		}
	}

	@FXML
	private void onTimeStateChange() {
		controller.changeTimeState();
		thirdCount.requestFocus();
	}

	@FXML
	private void onNewGame() {
		controller.startNewGame();
		thirdCount.requestFocus();
	}

	@FXML
	private void onKeyPressed(KeyEvent event) {
		if (event.getCode().equals(KeyCode.SPACE)) {
			controller.changeTimeState();
		}
	}
}
