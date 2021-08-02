import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

  public Map<Integer, OpeningTime> parseTime(String str) {
    HashMap<Integer, OpeningTime> map = new HashMap<>();
    LocalTime opening = LocalTime.of(11, 30);
    LocalTime closing = LocalTime.of(21, 0);

    OpeningTime openingTime = new OpeningTime(opening, closing);
    map.put(1, openingTime);
    return map;
  }
}

