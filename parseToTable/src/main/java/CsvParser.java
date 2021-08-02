import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
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

  public OpeningTime parseTime(String str) throws Exception {
    final Matcher matcher = get24HourTimeMatcher(str);
    List<LocalTime> times = new ArrayList<>();
    while (matcher.find()) {
      String time = matcher.group(1).toUpperCase();
      times.add(LocalTime.parse(time, getAmPmTimeFormatter()));
    }
    if (times.size() != 2) throw new Exception("Invalid time input");
    return new OpeningTime(times.get(0), times.get(1));
  }

  private DateTimeFormatter getAmPmTimeFormatter() {
    return DateTimeFormatter.ofPattern("[h:m a][h a]", Locale.US);
  }

  private Matcher get24HourTimeMatcher(String period) {
    final Pattern pattern = Pattern.compile("(\\d+:?\\d{0,2} (am|pm))");
    return pattern.matcher(period);
  }

}

