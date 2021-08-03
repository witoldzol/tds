import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CsvParserTest {
  CsvParser csvParser;
  List<String[]> expected;

  @BeforeEach
  void setup() {
    expected = getListOfOpeningTimes();
    csvParser = new CsvParser();
  }

  @Test
  public void parsesDataIntoTwoElements() {
    List<String[]> result = csvParser.parse(Path.of("src/test/resources/data.csv"));
    assertEquals(3, result.size());
    for (String[] arr : result) {
      assertEquals(2, arr.length);
    }
  }

  @Test
  public void parsesOpeningTimes() throws Exception {
    String period = "Mon-Sun 11:30 am - 9 pm";
    OpenCloseTimes result = csvParser.parseOpeningTime(period);
    assertEquals(LocalTime.of(11, 30), result.getOpening());
    assertEquals(LocalTime.of(21, 0), result.getClosing());
    period = "11 am - 11 pm  ";
    result = csvParser.parseOpeningTime(period);
    assertEquals(LocalTime.of(11, 0), result.getOpening());
    assertEquals(LocalTime.of(23, 0), result.getClosing());
  }

  @Test
  public void throwsWhenInvalidTimeProvided() {
    assertThrows(Exception.class, () -> csvParser.parseOpeningTime("11:00 - 12:00"));
    assertThrows(Exception.class, () -> csvParser.parseOpeningTime("1100 - 12:00 pm"));
    assertThrows(Exception.class, () -> csvParser.parseOpeningTime("11 pm"));
  }

  @Test
  public void matchSingleDay() throws Exception {
    String period = "Mon";
    Set<DayOfWeek> result = csvParser.getSetOfDays(period);
    assertEquals(1, result.size());
    period = "suN";
    result = csvParser.getSetOfDays(period);
    assertEquals(1, result.size());
    period = "foo";
    result = csvParser.getSetOfDays(period);
    assertEquals(0, result.size());
  }

  @Test
  public void matchDayRange() throws Exception {
    String period = "Mon-TUE";
    Set<DayOfWeek> result = csvParser.getSetOfDays(period);
    assertEquals(2, result.size());
    period = "tue-saT";
    result = csvParser.getSetOfDays(period);
    assertEquals(5, result.size());
    period = "";
    result = csvParser.getSetOfDays(period);
    assertEquals(0, result.size());
  }

  private List<String[]> getListOfOpeningTimes() {
    String[] s1 = {"Kushi Tsuru", "Mon-Sun 11:30 am - 9 pm"};
    String[] s2 = {"Osakaya Restaurant", "Mon-Thu, Sun 11:30 am - 9 pm  / Fri-Sat 11:30 am - 9:30 pm"};
    String[] s3 = {"Kyoto Sushi", "Mon-Thu 11 am - 10:30 pm  / Fri 11 am - 11 pm  / Sat 11:30 am - 11 pm  / Sun 4:30 pm - 10:30 pm"};
    return List.of(s1, s2, s3);
  }
}
