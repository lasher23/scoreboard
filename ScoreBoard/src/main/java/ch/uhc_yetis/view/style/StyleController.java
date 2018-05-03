package ch.uhc_yetis.view.style;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StyleController extends Stage {
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
	private VBox root;

	public StyleController(VBox root) {
		this.root = root;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Style.fxml"));
			loader.setController(this);
			VBox box = (VBox) loader.load();
			setScene(new Scene(box));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void initialize() {
		setDefault();
		addListeners();
		root.getStylesheets().clear();
		setStylesheet(null);
	}

	private void addListeners() {
		colorBackground.valueProperty().addListener(this::setStylesheet);
		colorText.valueProperty().addListener(this::setStylesheet);
		sizeThird.textProperty().addListener(this::setStylesheet);
		sizeTime.textProperty().addListener(this::setStylesheet);
		sizeScore.textProperty().addListener(this::setStylesheet);
	}

	private void setStylesheet(Object any) {
		StyleProvider styleProvider = new StyleProvider();
		CssClass scoreClass = createScoreCssClass();
		CssClass backgroundClass = createBackgroundCssClass();
		CssClass timeClass = createTimeCssClass();
		CssClass thirdClass = createThirdCssClass();
		styleProvider.addClass(scoreClass);
		styleProvider.addClass(backgroundClass);
		styleProvider.addClass(timeClass);
		styleProvider.addClass(thirdClass);
		String css = styleProvider.toString();
		try {
			String uri = new TempFileWriter("application.css").writeAndGetUri(css);
			root.getStylesheets().add("file:///" + uri);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private CssClass createScoreCssClass() {
		CssClass scoreClass = new CssClass("Score");
		scoreClass.addCssAttribute(CssAttributes.FONT_COLOR, toRGBCode(colorText.getValue()));
		scoreClass.addCssAttribute(CssAttributes.FONT_FAMILY, "DSEG14 Modern-Regular");
		scoreClass.addCssAttribute(CssAttributes.FONT_SIZE, sizeScore.getText() + "px");
		return scoreClass;
	}

	private CssClass createThirdCssClass() {
		CssClass scoreClass = new CssClass("Third");
		scoreClass.addCssAttribute(CssAttributes.FONT_COLOR, toRGBCode(colorText.getValue()));
		scoreClass.addCssAttribute(CssAttributes.FONT_FAMILY, "DSEG14 Modern-Regular");
		scoreClass.addCssAttribute(CssAttributes.FONT_SIZE, sizeThird.getText() + "px");
		return scoreClass;
	}

	private CssClass createTimeCssClass() {
		CssClass scoreClass = new CssClass("Time");
		scoreClass.addCssAttribute(CssAttributes.FONT_COLOR, toRGBCode(colorText.getValue()));
		scoreClass.addCssAttribute(CssAttributes.FONT_FAMILY, "DSEG14 Modern-Regular");
		scoreClass.addCssAttribute(CssAttributes.FONT_SIZE, sizeTime.getText() + "px");
		return scoreClass;
	}

	private CssClass createBackgroundCssClass() {
		CssClass scoreClass = new CssClass("Bakcground");
		scoreClass.addCssAttribute(CssAttributes.BACKGROUND_COLOR, toRGBCode(colorBackground.getValue()));
		return scoreClass;
	}

	public static String toRGBCode(Color color) {
		return String.format("rgb(%d,%d,%d)", (int) (color.getRed() * 255), (int) (color.getGreen() * 255),
				(int) (color.getBlue() * 255));
	}

	private void setDefault() {
		colorBackground.setValue(BACKGROUND_COLOR);
		colorBackground.setValue(TEXT_COLOR);
		sizeThird.setText(SIZE_THIRD);
		sizeScore.setText(SIZE_SCORE);
		sizeTime.setText(SIZE_TIME);
	}
}
