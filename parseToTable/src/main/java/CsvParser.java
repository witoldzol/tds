import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class CsvParser {
 public ArrayList<String[]> parse(Path pathToFile) {
   try(BufferedReader br = Files.newBufferedReader(pathToFile)){
//     return br.readLine().split(",");
     ArrayList<String[]> result = new ArrayList<>();
     String line = "";
     while((line =  br.readLine()) != null){
       String[] temp = line.split(",");
       result.add(temp);
     };
     return result;
   } catch (IOException e) {
     e.printStackTrace();
   }
   return null;
 }
}
