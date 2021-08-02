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

  public Set<DayOfWeek> getSetOfDays(String period) throws Exception {
    if (isDayRange(period)) {
      return parseRangeOfDays(period);
    }
    return Collections.emptySet();
  }

  private boolean isDayRange(String period) {
    return getDayRangeMatcher(period).find();
  }

  private Set<DayOfWeek> parseRangeOfDays(String period) throws Exception {
    Matcher matcher = singleDayMatcher(period);
    List<Integer> range = new ArrayList<>();
    while (matcher.find()) {
      range.add(dayToInteger(matcher));
    }
    validateResult(range);
    return populateSetWithDayOfWeek(range.get(0), range.get(1));
  }

  private void validateResult(List<Integer> range) throws Exception {
    if (range.size() != 2) throw new Exception("Invalid input - incorrect number of days matched");
    int start = range.get(0);
    int end = range.get(1);
    if (start < 0 || end < 0) throw new Exception("Failed to parse a day");
    if (start >= end) throw new Exception("Starting day cannot be the same or after end day");
  }

  private DateTimeFormatter dayFormatter() {
    return DateTimeFormatter.ofPattern("eee");
  }

  private Set<DayOfWeek> populateSetWithDayOfWeek(int start, int end) {
    Set<DayOfWeek> days = new HashSet<>();
    for (int i = start; i <= end; i++) {
      days.add(DayOfWeek.of(i));
    }
    return days;
  }

  private int dayToInteger(Matcher matcher) {
    String firstMatch = matcher.group(1).toLowerCase();
    firstMatch = capitalizeFirstLetter(firstMatch);
    TemporalAccessor accessor = dayFormatter().parse(firstMatch);
    return DayOfWeek.from(accessor).getValue();
  }

  private String capitalizeFirstLetter(String secondMatch) {
    secondMatch = secondMatch.substring(0, 1).toUpperCase() + secondMatch.substring(1);
    return secondMatch;
  }

  private Matcher getDayRangeMatcher(String period) {
    String regex = "((mon|tue|wed|thu|fri|sat|sun)-(mon|tue|wed|thu|fri|sat|sun))";
    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    return pattern.matcher(period);
  }

  private Matcher singleDayMatcher(String period) {
    String regex = "(mon|tue|wed|thu|fri|sat|sun)";
    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    return pattern.matcher(period);
  }
}

