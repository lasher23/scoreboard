package ch.uhc_yetis.view;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ScoreboardController extends Stage {
  public enum TimeState {
    PAUSE, FISRHALF, SECONDHALF
  }

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
  private TimeState timeState = TimeState.FISRHALF;
  private boolean gameIsStarted = false;

  public ScoreboardController() {
    super(StageStyle.UNDECORATED);
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("Scoreboard.fxml"));
      loader.setController(this);
      VBox box = (VBox) loader.load();
      setScene(new Scene(box));
    } catch (Exception e) {
    }

    scoreHome.setText("");
    scoreGuest.setText("");
    thirdCount.setText("");
    time.setText("");
  }

  public void startNewGame() {
    gameIsStarted = true;
    scoreHome.setText("0");
    scoreGuest.setText("0");
    thirdCount.setText("1");
    time.setText("00:00");
  }

  public void addGameEndListeners(GameEndListener onGameEnd) {
    gameEndListeners.add(onGameEnd);
  }

  private void checkIfGameIsStarted() throws GameNotStartedException {
    if (!gameIsStarted) {
      throw new GameNotStartedException("Start a game first");
    }
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
    timer = new Timer();
    TimerTask task = new TimerTask() {

      @Override
      public void run() {
        if (actualTime == 1200000 && timeState == TimeState.FISRHALF) {
          actualTime = 300000;
          timerCountUp = false;
          timeState = TimeState.PAUSE;
          stopTime();
          return;
        } else if (actualTime == 0 && timeState == TimeState.PAUSE) {
          actualTime = 1200000;
          timeState = TimeState.SECONDHALF;
          stopTime();
          return;
        } else if (actualTime == 0 && timeState == TimeState.SECONDHALF) {
          timer.cancel();
          fireGameEndListeners();
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
      timer.schedule(task, 1000, 1000);
    } catch (IllegalStateException e) {
      // Timer already started
    }
  }

  public void stopTime() {
    try {
      timer.cancel();
    } catch (NullPointerException e) {
      // stop pressed without ever started
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
