import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvParserTest {
  CsvParser csvParser;
  List<String[]> expected;

  @BeforeEach
  void setup() {
    expected = getExpected();
    csvParser = new CsvParser();
  }

  @Test
  public void parsesDataIntoTwoElements() {
    List<String[]> result = csvParser.parse(Path.of("src/test/resources/data.csv"));
    assertEquals(3 ,result.size());
    for( String[] arr : result){
      assertEquals(2, arr.length );
    }
  }

  @Test
  public void parsesOpeningTimes() {
    Map<Integer, OpeningTime> result = csvParser.parseTime("Mon-Sun 11:30 am - 9 pm");
    assertEquals(LocalTime.of(11,30), result.get(1).opening);
  }

  private List<String[]> getExpected() {
    String[] s1 = {"Kushi Tsuru", "Mon-Sun 11:30 am - 9 pm"};
    String[] s2 = {"Osakaya Restaurant", "Mon-Thu, Sun 11:30 am - 9 pm  / Fri-Sat 11:30 am - 9:30 pm"};
    String[] s3 = {"Kyoto Sushi", "Mon-Thu 11 am - 10:30 pm  / Fri 11 am - 11 pm  / Sat 11:30 am - 11 pm  / Sun 4:30 pm - 10:30 pm"};
    return List.of(s1, s2, s3);
  }
}
