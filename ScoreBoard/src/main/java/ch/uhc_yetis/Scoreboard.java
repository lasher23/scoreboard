
package ch.uhc_yetis;

import ch.uhc_yetis.view.ScoreboardController;
import ch.uhc_yetis.view.ScoreboardControllerController;
import ch.uhc_yetis.view.style.StyleController;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Screen;
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
    ScoreboardControllerController scc = new ScoreboardControllerController(sc);
    sc.setOnCloseRequest(event -> scc.close());
    scc.setOnCloseRequest(event -> sc.close());
    StyleController styleController = new StyleController(sc.getRoot());
    scc.getScene().setOnKeyPressed(event -> {
      if (!styleController.isShowing() && event.getCode().equals(KeyCode.F11)) {
        styleController.show();
      }
    });
    sc.show();
    scc.show();
    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
    scc.setX(visualBounds.getMinX());
    scc.setY(visualBounds.getMinY());

    for (Screen screen : Screen.getScreens()) {
      if (!screen.equals(Screen.getPrimary())) {
        sc.setX(screen.getVisualBounds().getMinX());
        sc.setY(screen.getVisualBounds().getMinY());
      }
    }

    sc.setMaximized(true);
    scc.setMaximized(true);
  }
}
