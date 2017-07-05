package ch.uhc_yetis;

import ch.uhc_yetis.view.ScoreboardController;
import ch.uhc_yetis.view.ScoreboardControllerController;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Scoreboard extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Font.loadFont(getClass().getResourceAsStream("view/DSEG14-Modern/DSEG14Modern-Regular.ttf"), 250);
    Font.loadFont(getClass().getResourceAsStream("view/DSEG14-Modern/DSEG14Modern-Regular.ttf"), 150);
    ScoreboardController sc = new ScoreboardController();
    sc.setMaximized(true);
    sc.show();
    ScoreboardControllerController scc = new ScoreboardControllerController(sc);
    scc.setMaximized(true);
    scc.show();
    sc.setOnCloseRequest(event -> scc.close());
    scc.setOnCloseRequest(event -> sc.close());
  }
}
