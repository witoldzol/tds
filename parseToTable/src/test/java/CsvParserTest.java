import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CsvParserTest {
  CsvParser csvParser;

  @BeforeEach
  void setup() {
    csvParser = new CsvParser();
  }

  @Test
  public void returnsTrue() {
    assertTrue(true);
  }

  @Test
  public void returnsString() {
    String[] result = csvParser.parse(Path.of("src/test/resources/data.csv"));
    String[][] expected = {{"Kushi Tsuru, Mon-Sun 11:30 am - 9 pm"}, {"Osakaya Restaurant", "Mon-Thu, Sun 11:30 am - 9 pm  / Fri-Sat 11:30 am - 9:30 pm"}};

    assertEquals(Arrays.deepToString(expected), Arrays.deepToString(result));
  }

}
