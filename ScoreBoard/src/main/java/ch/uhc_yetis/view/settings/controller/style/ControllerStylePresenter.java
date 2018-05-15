package ch.uhc_yetis.view.settings.controller.style;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class ControllerStylePresenter implements Initializable {
	@FXML
	TextField size;
	@Inject
	ControllerStyleService service;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		size.textProperty().addListener(this::sizeChanged);
		size.textProperty().setValue("50");
	}

	public void sizeChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		try {
			service.updateSize(Integer.parseInt(newValue));
			size.setStyle("");
		} catch (NumberFormatException e) {
			size.setStyle("-fx-text-box-border: red ;-fx-focus-color: red;");
		}
	}
}
