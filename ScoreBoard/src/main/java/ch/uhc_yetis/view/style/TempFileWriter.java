package ch.uhc_yetis.view.style;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TempFileWriter {

	private String filename;

	public TempFileWriter(String filename) {
		this.filename = filename;
	}

	public String writeAndGetUri(String fileContent) throws IOException {
		String property = System.getProperty("java.io.tmpdir");
		File file = new File(property, filename);
		try (FileWriter fw = new FileWriter(file)) {
			fw.write(fileContent);
		}
		if (file.exists()) {
			return file.getAbsolutePath();
		}
		throw new RuntimeException("Some Error");
	}
}
