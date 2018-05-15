package ch.uhc_yetis.view.settings.scoreboard.game;

public final class TimeStringToLong {
	private TimeStringToLong() {
		throw new IllegalAccessError();
	}

	public static long getLongFromString(String time) throws InvalidFormatException {
		try {
			String[] split = time.split(":");
			return Long.parseLong(split[0]) * 60 * 1000 + Long.parseLong(split[1]) * 1000;
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			throw new InvalidFormatException();
		}
	}
}
