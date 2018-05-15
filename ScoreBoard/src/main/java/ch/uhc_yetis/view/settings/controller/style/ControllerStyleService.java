package ch.uhc_yetis.view.settings.controller.style;

public class ControllerStyleService {
	public void updateSize(int size) {
		ControllerStyleProvider.getInstance().setSize(size);
	}
}
