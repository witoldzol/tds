import java.time.LocalTime;

public class OpeningTime {
  private final LocalTime opening;
  private final LocalTime closing;

  public LocalTime getOpening() {
    return opening;
  }

  public LocalTime getClosing() {
    return closing;
  }

  public OpeningTime(LocalTime opening, LocalTime closing) {
    this.opening = opening;
    this.closing = closing;
  }
}
