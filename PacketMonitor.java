import java.util.HashMap;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.io.IOException;

public class PacketMonitor{

  private static final int TIMEOUT=120000; //2 minutes
  private static final String LOGFILE="ErrorNotice.log";
  private static HashMap<String,ArrayList<EthernetPacket>> mapBySource = new HashMap<String,ArrayList<EthernetPacket>>();
  private static HashMap<String,ArrayList<EthernetPacket>> mapByDestination = new HashMap<String,ArrayList<EthernetPacket>>();
  private static ArrayList<EthernetPacket> errorList = new ArrayList<EthernetPacket>();
  private static ArrayList<EthernetPacket> noticeList = new ArrayList<EthernetPacket>();

  /**
  * removes all packages older than TIMEOUT ms
  */
  synchronized private static void clean(){
    long currentTime = System.currentTimeMillis();
    mapBySource.forEach(
      (k,ethList) -> ethList.forEach(
        (eth)-> {if(currentTime-eth.getTimestamp()>TIMEOUT)
            ethList.remove(eth);}));
    mapByDestination.forEach(
      (k,ethList) -> ethList.forEach(
        (eth)-> {if(currentTime-eth.getTimestamp()>TIMEOUT)
            ethList.remove(eth);}));
  }

  /**
  * Saves packets according to source and destination
  */
  public static void savePacket(EthernetPacket eth){
    clean();
    if(eth==null)return;
    ArrayList<EthernetPacket> bySource;
    synchronized(eth){
      bySource = mapBySource.get(eth.getSource().toLowerCase());
      if(bySource==null)bySource=new ArrayList<EthernetPacket>();
    }
    bySource.add(eth);
    mapBySource.put(eth.getSource().toLowerCase(),bySource);
    ArrayList<EthernetPacket> byDestination;
    synchronized(eth){
      byDestination = mapByDestination.get(eth.getDestination().toLowerCase());
      if(byDestination==null)byDestination=new ArrayList<EthernetPacket>();
    }
    byDestination.add(eth);
    mapByDestination.put(eth.getDestination().toLowerCase(),byDestination);
  }

  /**
  * Saves and logs packages that throw an ERROR
  */
  public static void errorPacket(EthernetPacket eth){
    clean();
    errorList.add(eth);
    try(Writer output = new BufferedWriter(new FileWriter(LOGFILE,true))){
      output.append("ERROR: "+System.currentTimeMillis()+" \n");
      output.append(eth.toString());
      output.append("\n");
      output.close();
    }
    catch(IOException ioe){
      ErrorHandler.handleError(1,"Unable to log ERROR!",ioe);
    }
  }

  /**
  * Saves and logs packages that throw a NOTICE
  */
  public static void noticePacket(EthernetPacket eth){
    clean();
    noticeList.add(eth);
    try(Writer output=new BufferedWriter(new FileWriter(LOGFILE,true))){
      output.append("NOTICE: "+System.currentTimeMillis()+" \n");
      output.append(eth.toString());
      output.append("\n");
      output.close();
    }
    catch(IOException ioe){
      ErrorHandler.handleError(1,"Unable to log ERROR!",ioe);
    }
  }

  /**
  * Returns all saved Error packets
  */
  public static ArrayList<EthernetPacket> getErrors(){
    return errorList;
  }

  /**
  * Returns all saved Notice packets
  */
  public static ArrayList<EthernetPacket> getNotices(){
    return noticeList;
  }

  /**
  * Returns packets by source
  */
  public static ArrayList<EthernetPacket> getPacketsBySource(String source){
    clean();
    ArrayList<EthernetPacket> ret = mapBySource.get(source.toLowerCase());
    if(ret == null) ret = new ArrayList<EthernetPacket>();
    return ret;
  }

  /**
  * Returns packets by destination
  */
  public static ArrayList<EthernetPacket> getPacketsByDestination(String destination){
    clean();
    ArrayList<EthernetPacket> ret = mapByDestination.get(destination.toLowerCase());
    if(ret == null) ret = new ArrayList<EthernetPacket>();
    return ret;
  }

}
