import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvParser {
  public List<String[]> parse(Path pathToFile) {
    try (Stream<String> lines = Files.lines(pathToFile)) {
      return lines.map(str -> removeChar("\"", str))
              .map(str -> str.split(",", 2))
              .collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private String removeChar(String pattern, String str) {
    return str.replaceAll(pattern, "");
  }
}

