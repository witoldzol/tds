import java.time.LocalTime;

public class OpeningTime {
  private LocalTime opening;

  public LocalTime getOpening() {
    return opening;
  }

  public LocalTime getClosing() {
    return closing;
  }

  private LocalTime closing;

  public OpeningTime(LocalTime opening, LocalTime closing) {
    this.opening = opening;
    this.closing = closing;
  }
}
