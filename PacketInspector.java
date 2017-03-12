import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.util.ArrayList;

public class PacketInspector{


  //Possible returncodes
  public static final int PERMITTED=0;
  public static final int ERROR=1;
  public static final int NOTICE=2;

  /**
  * Handles errors
  */
  protected void handleError(int code,String msg, Exception e){
    ErrorHandler.handleError(code,msg,e);
  }

  /**
  * Returns the lines of the file as a String[]
  */
  protected ArrayList<String> loadConfigFile(String path){
    ArrayList<String> ret = new ArrayList<String>();
    try{
      Files.lines(Paths.get(path)).forEach(ret::add);
    }
    catch(IOException ioe){
      handleError(1,"Config file could not be read!",ioe);
    }
    return ret;
  }

}
