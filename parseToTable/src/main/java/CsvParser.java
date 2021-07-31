import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvParser {
  public List<String[]> parse(Path pathToFile) {
    try (Stream<String> lines = Files.lines(pathToFile)) {
      return lines.map(str -> str.replaceAll("\"", ""))
              // limit is the MAX number of array items returned after split operation
              // pattern is applied max limit -1 times
              .map(str -> str.split(",", 2))
              .collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}

