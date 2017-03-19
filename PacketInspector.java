import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.util.ArrayList;

public class PacketInspector{


  //Possible returncodes
  public static final int PERMITTED=0;
  public static final int ERROR=1;
  public static final int NOTICE=2;
  protected static final int REPLAY_LIMIT=250;

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

  public int replayInspect(byte[] data){
    String hash = PacketMonitor.hashBytes(data);
    ArrayList<String> hashList = PacketMonitor.getHashs();
    int equalCounter=0;
    for(int i=0;i<hashList.size();i++){
      if(hash.equals(hashList.get(i)))equalCounter++;
    }
    System.out.println(""+equalCounter+" TIME: "+hash);
    return equalCounter>=REPLAY_LIMIT?ERROR:PERMITTED;
  }

}
