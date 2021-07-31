import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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
    String[] s1 = {"Kushi Tsuru", "Mon-Sun 11:30 am - 9 pm"};
    String[] s2 = {"Osakaya Restaurant", "Mon-Thu, Sun 11:30 am - 9 pm  / Fri-Sat 11:30 am - 9:30 pm"};
    String[] s3 = {"Kyoto Sushi", "Mon-Thu 11 am - 10:30 pm  / Fri 11 am - 11 pm  / Sat 11:30 am - 11 pm  / Sun 4:30 pm - 10:30 pm"};
    List<String[]> expected = List.of(s1, s2, s3);

    ArrayList<String[]> result = csvParser.parse(Path.of("src/test/resources/data.csv"));

    assertArrayEquals(expected.toArray(), result.toArray());
  }

}
