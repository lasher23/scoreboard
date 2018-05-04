/**
 * File Name: GameController.java
 * 
 * Copyright (c) 2018 BISON Schweiz AG, All Rights Reserved.
 */

package ch.uhc_yetis.view.settings.game;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
      pauseDuration.setText(formatToDate() + "");
      thirdDuration.setText(GameSettingsProvider.THIRD_DURATION.get() + "");
      pauseDuration.textProperty().addListener((ChangeListener<String>)(observable, oldValue, newValue) -> {
        try {
          long duration = getMilis(newValue);
          GameSettingsProvider.PAUSE_DURATION.set(duration);
        } catch (NumberFormatException e) {
          String contentText = "Pause Duration must be a whole number";
          String headerText = "Invalid value";
          showError(contentText, headerText);
          pauseDuration.setText(oldValue);
        } catch (ParseException e) {
          showError("Invalid Date", "Format is HH:mm");
          pauseDuration.setText(oldValue);
        }
      });
      thirdDuration.textProperty().addListener((ChangeListener<String>)(observable, oldValue, newValue) -> {
        try {
          long duration = getMilis(newValue);
          GameSettingsProvider.THIRD_DURATION.set(duration);
        } catch (NumberFormatException e) {
          String contentText = "Pause Duration must be a whole number";
          String headerText = "Invalid value";
          showError(contentText, headerText);
          pauseDuration.setText(oldValue);
        } catch (ParseException e) {
          showError("Invalid Date", "Format is HH:mm");
          pauseDuration.setText(oldValue);
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

  private long getMilis(String newValue) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
    char[] charArray = newValue.toCharArray();
    String display = newValue;
    if (charArray.length == 4) {
      display = newValue + "0";
    } else if (charArray.length == 3) {
      display = newValue + "00";
    } else if (charArray.length == 2) {
      display = newValue + ":00";
    } else if (charArray.length == 1) {
      display = newValue + "0:00";
    } else if (charArray.length == 0) {
      display = newValue + "00:00";
    }
    LocalTime.parse(display, DateTimeFormatter.ofPattern("mm:ss"));
    return sdf.parse(display + " UTC").getTime();
  }

  private long formatToDate() {
    return GameSettingsProvider.PAUSE_DURATION.get();
  }
}
