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
  private static ArrayList<String> packetHashs = new ArrayList<String>();
  private static long packetHashsTime = 0;
  private static long packetHashsTimeLimit = 30000;

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

  public static ArrayList<String> getHashs(){
    return packetHashs;
  }

  /**
  * This method adds hashs to check for replay attacks later
  * The list is resetted every minute
  */
  public static String addHash(byte[] data){
    long time = System.currentTimeMillis();
    if(time-packetHashsTime>=packetHashsTimeLimit){
      packetHashs = new ArrayList<String>();
      packetHashsTime = time;
    }
    String ret = hashBytes(data);
    packetHashs.add(ret);
    //for(int i=0;i<packetHashs.size();i++)System.out.println(packetHashs.get(i));
    return ret;
  }

  public static String hashBytes(byte[] data){
    try{
      java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-1");
      byte[] hash = md.digest(data);
      String ret = "";
      for(int i=0;i<hash.length;i++){
        ret+=String.format("%02X",data[i]);
      }
      return ret;
    }
    catch(java.security.NoSuchAlgorithmException nsae){
      //won't happen
    }
    return "";
  }

}
