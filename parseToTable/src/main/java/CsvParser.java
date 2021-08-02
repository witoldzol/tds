import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvParser {
  public List<String[]> parse(Path pathToFile) {
    try (Stream<String> lines = Files.lines(pathToFile)) {
      return splitIntoTwoColumns(removeExtraQuotes(lines));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Collections.emptyList();
  }

  private List<String[]> splitIntoTwoColumns(Stream<String> lines) {
    // limit is the MAX number of array items returned after split operation
    // pattern is applied max limit -1 times
    return lines.map(str -> str.split(",", 2))
            .collect(Collectors.toList());
  }

  private Stream<String> removeExtraQuotes(Stream<String> lines) {
    return lines.map(str -> str.replaceAll("\"", ""));
  }

  public Set<DayOfWeek> parseOpeningDays(String period) {
    HashSet<DayOfWeek> days = new HashSet<>();
    days.add(DayOfWeek.MONDAY);
    return days;

  }

  public OpeningTime parseOpeningTime(String str) throws Exception {
    List<LocalTime> times = getOpeningAndClosingTime(get24HourTimeMatcher(str));
    throwIfMoreThanTwoTimesProvided(times);
    return new OpeningTime(times.get(0), times.get(1));
  }

  private List<LocalTime> getOpeningAndClosingTime(Matcher matcher) {
    List<LocalTime> times = new ArrayList<>();
    while (matcher.find()) {
      String time = matcher.group(1).toUpperCase();
      times.add(LocalTime.parse(time, getAmPmTimeFormatter()));
    }
    return times;
  }

  private void throwIfMoreThanTwoTimesProvided(List<LocalTime> times) throws Exception {
    if (times.size() != 2) throw new Exception("There can only be only one time pair : opening and closing time");
  }

  private DateTimeFormatter getAmPmTimeFormatter() {
    return DateTimeFormatter.ofPattern("[h:m a][h a]", Locale.US);
  }

  private Matcher get24HourTimeMatcher(String period) {
    final Pattern pattern = Pattern.compile("(\\d+:?\\d{0,2} (am|pm))");
    return pattern.matcher(period);
  }

}

