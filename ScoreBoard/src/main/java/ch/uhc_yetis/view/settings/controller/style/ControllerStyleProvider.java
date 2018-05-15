package ch.uhc_yetis.view.settings.controller.style;

import java.util.ArrayList;
import java.util.List;

public class ControllerStyleProvider {
	private static ControllerStyleProvider instance;
	private int size;
	private List<SizeChangeListener> sizeChangeListeners = new ArrayList<>();

	private ControllerStyleProvider() {
	}

	public static ControllerStyleProvider getInstance() {
		if (instance == null) {
			instance = new ControllerStyleProvider();
		}
		return instance;
	}

	public void addSizeChangeListener(SizeChangeListener changeListener) {
		sizeChangeListeners.add(changeListener);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
		fireSizeChangeListener();
	}

	private void fireSizeChangeListener() {
		for (SizeChangeListener sizeChangeListener : sizeChangeListeners) {
			sizeChangeListener.onChanged(size);
		}
	}

	public interface SizeChangeListener {
		void onChanged(int size);
	}
}
