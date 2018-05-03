package ch.uhc_yetis.view.style;

import java.util.ArrayList;
import java.util.List;

public class StyleProvider {
	private List<CssClass> cssClasses = new ArrayList<>();

	public void addClass(CssClass cssClass) {
		cssClasses.removeIf(x -> x.getClassName().equals(cssClass.getClassName()));
		this.cssClasses.add(cssClass);
	}

	public List<CssClass> getCssClasses() {
		return cssClasses;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		cssClasses.forEach(sb::append);
		return sb.toString();
	}
}
