package ch.uhc_yetis.view.style;

public enum CssAttributes {
	BACKGROUND_COLOR("-fx-background-color"), FONT_SIZE("-fx-font-size"), FONT_FAMILY("-fx-font-family"), FONT_COLOR(
			"-fx-text-fill");
	private String name;

	private CssAttributes(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
