import java.time.LocalTime;

public class OpenCloseTimes {
  private final LocalTime opening;
  private final LocalTime closing;

  public LocalTime getOpening() {
    return opening;
  }

  public LocalTime getClosing() {
    return closing;
  }

  public OpenCloseTimes(LocalTime opening, LocalTime closing) {
    this.opening = opening;
    this.closing = closing;
  }
}
