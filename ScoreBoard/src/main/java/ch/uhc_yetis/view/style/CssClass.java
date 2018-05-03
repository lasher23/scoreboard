package ch.uhc_yetis.view.style;

import java.util.HashMap;
import java.util.Map;

public class CssClass {
	private Map<CssAttributes, String> cssAttributes = new HashMap<>();
	private String className;

	public CssClass(String className) {
		this.setClassName(className);
	}

	public void addCssAttribute(CssAttributes attribute, String value) {
		cssAttributes.put(attribute, value);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('.').append(getClassName()).append('{').append('\n');
		cssAttributes.entrySet().forEach(x -> sb.append('\t').append(x.getKey().getName()).append(':').append(' ')
				.append(x.getValue()).append(';').append('\n'));
		sb.append('}').append('\n');
		return sb.toString();
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
