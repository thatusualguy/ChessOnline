package io.github.thatusualguy.chessonline;

public class Settings {
	static Settings sSettings;

	public static Settings getSettings() {
		if (sSettings == null)
			sSettings = new Settings();
		return sSettings;
	}
}
