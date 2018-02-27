package ch.uhc_yetis.view;

public class GameNotStartedException extends Exception {
  private static final long serialVersionUID = 1L;

  public GameNotStartedException() {
    super();
  }

  public GameNotStartedException(String message) {
    super(message);
  }
}
