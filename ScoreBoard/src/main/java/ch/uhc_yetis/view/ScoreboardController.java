
package ch.uhc_yetis.view;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ch.uhc_yetis.view.settings.scoreboard.game.GameSettingsProvider;
import ch.uhc_yetis.view.settings.scoreboard.style.ScoreboardStyleProvider;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ScoreboardController extends Stage {
	private static final String FX_FONT_FAMILY_DSEG14_MODERN_REGULAR = ";-fx-font-family:'DSEG14 Modern-Regular';";
	private static final String FX_TEXT_FILL = "; -fx-text-fill:";
	private static final String FX_FONT_SIZE = "-fx-font-size: ";

	public enum TimeState {
		PAUSE, IN_HALF
	}

	@FXML
	private Label scoreGuestLabel;
	@FXML
	private Label scoreHomeLabel;
	@FXML
	private Label scoreHome;
	@FXML
	private Label scoreGuest;
	@FXML
	private Label thirdCount;
	@FXML
	private Label time;
	private List<GameEndListener> gameEndListeners = new ArrayList<>();
	private long actualTime = 0;
	private Timer timer;
	private boolean timerCountUp = true;
	private TimeState timeState = TimeState.IN_HALF;
	private boolean gameIsStarted = false;
	private boolean timeStarted;
	private VBox root;

	public ScoreboardController() {
		super(StageStyle.UNDECORATED);
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Scoreboard.fxml"));
			loader.setController(this);
			root = (VBox) loader.load();
			setScene(new Scene(root));
		} catch (Exception e) {
		}
		scoreHome.setText("");
		scoreGuest.setText("");
		thirdCount.setText("");
		time.setText("");
		setStyleListeners();
	}

	private void setStyleListeners() {
		ScoreboardStyleProvider.BACKGROUND_COLOR
				.addListener((ChangeListener<String>) (observable, oldValue, newValue) -> setRootStyle(newValue));
		ScoreboardStyleProvider.PRIMARY_COLOR.addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			setTimerStyle(newValue, ScoreboardStyleProvider.TIME_SIZE.getValue());
			setThirdCountStyle(newValue, ScoreboardStyleProvider.THIRD_SIZE.getValue());
			setScoreStyle(newValue, ScoreboardStyleProvider.SCORE_SIZE.getValue());
		});
		ScoreboardStyleProvider.SCORE_SIZE.addListener((ChangeListener<String>) (observable, oldValue,
				newValue) -> setScoreStyle(ScoreboardStyleProvider.PRIMARY_COLOR.getValue(), newValue));
		ScoreboardStyleProvider.THIRD_SIZE.addListener((ChangeListener<String>) (observable, oldValue,
				newValue) -> setThirdCountStyle(ScoreboardStyleProvider.PRIMARY_COLOR.getValue(), newValue));
		ScoreboardStyleProvider.TIME_SIZE.addListener((ChangeListener<String>) (observable, oldValue,
				newValue) -> setTimerStyle(ScoreboardStyleProvider.PRIMARY_COLOR.getValue(), newValue));
		ScoreboardStyleProvider.SCORE_GUEST_HOME_SIZE.addListener((ChangeListener<String>) (observable, oldValue,
				newValue) -> setScoreHomeGuestStyle(ScoreboardStyleProvider.PRIMARY_COLOR.getValue(), newValue));
	}

	private void setRootStyle(String background) {
		root.setStyle("-fx-background-color:" + background + ";");
	}

	private void setTimerStyle(String fontColor, String fontSize) {
		time.setStyle(FX_FONT_SIZE + fontSize + FX_TEXT_FILL + fontColor + FX_FONT_FAMILY_DSEG14_MODERN_REGULAR);
	}

	private void setThirdCountStyle(String fontColor, String fontSize) {
		thirdCount.setStyle(FX_FONT_SIZE + fontSize + FX_TEXT_FILL + fontColor + FX_FONT_FAMILY_DSEG14_MODERN_REGULAR);
	}

	private void setScoreStyle(String fontColor, String fontSize) {
		String style = FX_FONT_SIZE + fontSize + FX_TEXT_FILL + fontColor + FX_FONT_FAMILY_DSEG14_MODERN_REGULAR;
		scoreHome.setStyle(style);
		scoreGuest.setStyle(style);
	}

	private void setScoreHomeGuestStyle(String fontColor, String fontSize) {
		String style = FX_FONT_SIZE + fontSize + FX_TEXT_FILL + fontColor + FX_FONT_FAMILY_DSEG14_MODERN_REGULAR;
		scoreHomeLabel.setStyle(style);
		scoreGuestLabel.setStyle(style);
	}

	public void startNewGame() {
		gameIsStarted = true;
		scoreHome.setText("0");
		scoreGuest.setText("0");
		thirdCount.setText("1");
		if (timer != null) {
			timer.cancel();
		}
		actualTime = 0;
		time.setText("00:00");
	}

	public void addGameEndListeners(GameEndListener onGameEnd) {
		gameEndListeners.add(onGameEnd);
	}

	public void addScoreHome() throws GameNotStartedException {
		checkIfGameIsStarted();
		scoreHome.setText(String.valueOf(Integer.parseInt(scoreHome.getText()) + 1));
	}

	public void addScoreGuest() throws GameNotStartedException {
		checkIfGameIsStarted();
		scoreGuest.setText(String.valueOf(Integer.parseInt(scoreGuest.getText()) + 1));
	}

	public void undoScoreHome() throws GameNotStartedException {
		checkIfGameIsStarted();
		scoreHome.setText(String.valueOf(Integer.parseInt(scoreHome.getText()) - 1));
	}

	public void undoScoreGuest() throws GameNotStartedException {
		checkIfGameIsStarted();
		scoreGuest.setText(String.valueOf(Integer.parseInt(scoreGuest.getText()) - 1));
	}

	public void addThird() throws GameNotStartedException {
		checkIfGameIsStarted();
		thirdCount.setText(String.valueOf(Integer.parseInt(thirdCount.getText()) + 1));
	}

	public void undoThird() throws GameNotStartedException {
		checkIfGameIsStarted();
		thirdCount.setText(String.valueOf(Integer.parseInt(thirdCount.getText()) - 1));
	}

	public void startTime() throws GameNotStartedException {
		checkIfGameIsStarted();
		if (timeStarted) {
			return;
		}
		timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				if (actualTime == GameSettingsProvider.THIRD_DURATION.get() && timeState == TimeState.IN_HALF) {
					actualTime = GameSettingsProvider.PAUSE_DURATION.get();
					timerCountUp = false;
					timeState = TimeState.PAUSE;
					stopTime();
					return;
				} else if (actualTime == 0 && timeState == TimeState.PAUSE) {
					timeState = TimeState.IN_HALF;
					timerCountUp = true;
					stopTime();
					return;
				}

				if (timerCountUp) {
					actualTime += 1000;
				} else {
					actualTime -= 1000;
				}
				LocalDateTime localDateTime = new Timestamp(actualTime).toLocalDateTime();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss");

				Platform.runLater(() -> time.setText(localDateTime.format(formatter)));
			}
		};
		try {
			if (!timeStarted) {
				timer.schedule(task, 1000, 1000);
			}
			timeStarted = true;
		} catch (IllegalStateException e) {
			// Timer already started
		}
	}

	public void changeTimeState() {
		if (timeStarted) {
			try {
				timeStarted = false;
				timer.cancel();
			} catch (NullPointerException e) {
				// stop pressed without ever started
			}
		} else {
			try {
				startTime();
			} catch (GameNotStartedException e) {
			}
		}
	}

	public void stopTime() {
		try {
			timeStarted = false;
			timer.cancel();
		} catch (NullPointerException e) {
			// stop pressed without ever started
		}
	}

	public VBox getRoot() {
		return root;
	}

	public ObservableValue<String> getThirdCount() {
		return thirdCount.textProperty();
	}

	public ObservableValue<String> getHomeCount() {
		return scoreHome.textProperty();
	}

	public ObservableValue<String> getGuestCount() {
		return scoreGuest.textProperty();
	}

	public ObservableValue<String> getTime() {
		return time.textProperty();
	}

	private void checkIfGameIsStarted() throws GameNotStartedException {
		if (!gameIsStarted) {
			throw new GameNotStartedException("Start a game first");
		}
	}

	private void fireGameEndListeners() {
		for (GameEndListener gameEndListener : gameEndListeners) {
			gameEndListener.onGameEnd();
		}
	}

	@FunctionalInterface
	public interface GameEndListener {
		void onGameEnd();
	}
}
