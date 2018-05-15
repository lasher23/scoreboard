
package ch.uhc_yetis.view.settings.scoreboard.style;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class StyleController extends VBox {
  private static final String SIZE_THIRD = "300";
  private static final String SIZE_TIME = "200";
  private static final String SIZE_SCORE = "200";
  private static final Color BACKGROUND_COLOR = Color.rgb(33, 33, 33);
  private static final Color TEXT_COLOR = Color.rgb(198, 40, 2);
  @FXML
  private ColorPicker colorBackground;
  @FXML
  private ColorPicker colorText;
  @FXML
  private TextField sizeThird;
  @FXML
  private TextField sizeScore;
  @FXML
  private TextField sizeTime;

  public StyleController() {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("Style.fxml"));
      loader.setRoot(this);
      loader.setController(this);
      loader.load();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void initialize() {
    setDefault();
    addListeners();
  }

  private void addListeners() {
    colorBackground.valueProperty().addListener(x -> StyleProvider.BACKGROUND_COLOR.setValue(toRGBCode(colorBackground.getValue())));
    colorText.valueProperty().addListener(x -> StyleProvider.PRIMARY_COLOR.setValue(toRGBCode(colorText.getValue())));
    sizeScore.textProperty().addListener(x -> StyleProvider.SCORE_SIZE.setValue(sizeScore.getText() + "px"));
    sizeTime.textProperty().addListener(x -> StyleProvider.TIME_SIZE.setValue(sizeTime.getText() + "px"));
    sizeThird.textProperty().addListener(x -> StyleProvider.THIRD_SIZE.setValue(sizeThird.getText() + "px"));
  }

  public static String toRGBCode(Color color) {
    return String.format("rgb(%d,%d,%d)", (int)(color.getRed() * 255), (int)(color.getGreen() * 255), (int)(color.getBlue() * 255));
  }

  private void setDefault() {
    colorBackground.setValue(BACKGROUND_COLOR);
    colorText.setValue(TEXT_COLOR);
    sizeThird.setText(SIZE_THIRD);
    sizeScore.setText(SIZE_SCORE);
    sizeTime.setText(SIZE_TIME);
    StyleProvider.BACKGROUND_COLOR.setValue(toRGBCode(BACKGROUND_COLOR));
    StyleProvider.PRIMARY_COLOR.setValue(toRGBCode(TEXT_COLOR));
    StyleProvider.SCORE_SIZE.setValue(SIZE_SCORE);
    StyleProvider.THIRD_SIZE.setValue(SIZE_THIRD);
    StyleProvider.TIME_SIZE.setValue(SIZE_TIME);
  }
}
