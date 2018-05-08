/**
 * File Name: SettingsController.java
 * 
 * Copyright (c) 2018 BISON Schweiz AG, All Rights Reserved.
 */

package ch.uhc_yetis.view.settings;

import ch.uhc_yetis.view.ScoreboardController;
import ch.uhc_yetis.view.settings.game.GameController;
import ch.uhc_yetis.view.settings.screen.ScreenController;
import ch.uhc_yetis.view.settings.style.StyleController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author nicolas.schmid
 */
public class SettingsController extends Stage {
  @FXML
  private Tab styleTab;
  @FXML
  private Tab gameTab;
  @FXML
  private Tab screenTab;

  public SettingsController(ScoreboardController scoreboard) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("Settings.fxml"));
      loader.setController(this);
      VBox box = loader.load();
      setScene(new Scene(box));
    } catch (Exception e) {
    	throw new RuntimeException(e);
    }
    styleTab.setContent(new StyleController());
    gameTab.setContent(new GameController());
    screenTab.setContent(new ScreenController(scoreboard));
  }
}
