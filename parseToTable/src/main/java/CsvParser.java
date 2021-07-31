import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class CsvParser {
  public ArrayList<String[]> parse(Path pathToFile) {
    try (BufferedReader br = Files.newBufferedReader(pathToFile)) {
      ArrayList<String[]> result = new ArrayList<>();
      String line = "";
      while ((line = br.readLine()) != null) {
        String[] temp = removeChar("\"",line).split(",",2);
                result.add(temp);
      } ;
      return result;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private String removeChar(String pattern, String str) {
    return str.replaceAll(pattern, "");
  }
}

