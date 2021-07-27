import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CsvParser {
 public String[] parse(Path pathToFile) {
   try(BufferedReader br = Files.newBufferedReader(pathToFile)){
     return br.readLine().split(",");
//     String line = "";
//     while((line =  br.readLine()) != null){
//       str += line;
//     };
//     return str;
   } catch (IOException e) {
     e.printStackTrace();
   }
   return null;
 }
}
