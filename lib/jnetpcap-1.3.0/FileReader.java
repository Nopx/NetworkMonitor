import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

public class FileReader{

  public static byte[] getBytes(String path){
    byte[] data = new byte[1];
    try{
      Path filePath = Paths.get(path);
      data = Files.readAllBytes(filePath);
    }
    catch(Exception e){
      e.printStackTrace();
    }
    return data;
  }

}
