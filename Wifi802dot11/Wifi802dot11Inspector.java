import java.util.HashMap;

public class Wifi802dot11Inspector extends PacketInspector{

  private static HashMap<String,Long> LAST_DEAUTH = new HashMap<String,Long>();
  private static HashMap<String,Long> LAST_DEASS = new HashMap<String,Long>();
  private static final long DEAUTH_LIMIT = 12000;
  private static final long DEASS_LIMIT = 12000;

  public int inspectWifi802dot11(Wifi802dot11Packet packet){
    if(packet == null) return PERMITTED;
    int returnCode = PERMITTED;
    int type = packet.getType();
    int subtype = packet.getSubtype();
    Long currentTime = (Long)System.currentTimeMillis();
    Long lastTime;
    if(type == Wifi802dot11Parser.MANAGEMENT_FRAME){
      String source = packet.getAddress2();
      String destination = packet.getAddress1();
      String mapKey = source+destination;
      if(source!=null)switch(subtype){
        case Wifi802dot11Parser.DEAUTHENTICATION:
          lastTime = LAST_DEAUTH.get(mapKey);
          if(lastTime!=null && currentTime-lastTime<DEAUTH_LIMIT)
            returnCode = ERROR;
          LAST_DEAUTH.put(mapKey,currentTime);
          break;
        case Wifi802dot11Parser.DISASSOCIATION:
          lastTime = LAST_DEASS.get(mapKey);
          if(lastTime!=null && currentTime-lastTime<DEASS_LIMIT)
            returnCode = ERROR;
          LAST_DEASS.put(mapKey,currentTime);
          break;
      }
    }
    return returnCode;
  }

}
