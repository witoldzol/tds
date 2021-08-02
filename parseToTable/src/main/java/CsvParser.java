import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
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

  public Set<DayOfWeek> parseDayRange(String period) throws Exception {
    String regex = "((mon|tue|wed|thu|fri|sat|sun)-(mon|tue|wed|thu|fri|sat|sun))";
    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(period);
    Set<DayOfWeek> days = new HashSet<>();
    if (matcher.find()) {
      String open = matcher.group(2).toLowerCase();
      open = open.substring(0,1).toUpperCase() + open.substring(1);
      String close = matcher.group(3).toLowerCase();
      close = close.substring(0,1).toUpperCase() + close.substring(1);
      System.out.println(open);
      System.out.println(close);
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("eee");
      TemporalAccessor startAccessor = formatter.parse(open);
      int start = DayOfWeek.from(startAccessor).getValue();
      TemporalAccessor closeAccessor = formatter.parse(close);
      int end = DayOfWeek.from(closeAccessor).getValue();
      if (start >= end) throw new Exception("Starting day cannot be the same or after end day");
      for (int i = start; i <= end; i++) {
        days.add(DayOfWeek.of(i));
      }
    }
    return days;
  }
}

