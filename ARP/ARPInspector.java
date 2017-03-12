import java.util.ArrayList;

public class ARPInspector extends PacketInspector{

  //Address lists according to which packet analysis is done
  private ArrayList<String> macBlackList = new ArrayList<String>();
  private ArrayList<String> macBroadcast = new ArrayList<String>();
  private ArrayList<String> ipBlackList = new ArrayList<String>();
  private ArrayList<String> ipBroadcast = new ArrayList<String>();
  //Config file location and format specification
  private static final String CONFIGPATH = "config.cfg";
  private static final String CONFIGDELIM = "state:";

  //Inspecting ARP Package and returning a code
  public int inspectARP(ARPPacket arp){
    if(arp == null) return ERROR;
    int returnCode = PERMITTED;
    configure();

    if(isGratuitous(arp)){
      //Gratuitous:
      //-To broadcast address - NORMAL
      //-To unicast/multicast address - ERROR
      if(!macBroadcast.contains(arp.getDestination().toLowerCase())) returnCode = ERROR;
      //Source MAC Address differs from binded to MAC address - NOTICE
      if(returnCode!=ERROR &&!arp.getSource().toLowerCase().equals(arp.getSenderMACAddress().toLowerCase()))returnCode = NOTICE;
      //Binding to MAC broadcast address - ERROR
      if(macBroadcast.contains(arp.getSenderMACAddress().toLowerCase())) returnCode = ERROR;
      //Binding to IP addresses having 255 in the last two integers(Broadcast) - ERROR
      if(arp.getSenderIPAddress().substring(arp.getSenderIPAddress().length()-4).contains("255")) returnCode = ERROR;
    }
    int opCode = arp.getOpCode();
    switch(opCode){
      case PacketParser.REPLY:
        //Response
        //-To broadcast/multicast address - ERROR
        //-To unicast address - NORMAL
        if(macBroadcast.contains(arp.getDestination().toLowerCase())) returnCode = ERROR;
        //Source MAC Address differs from binded to MAC address - NOTICE
        if(returnCode!=ERROR &&!arp.getSource().equals(arp.getSenderMACAddress()))returnCode = NOTICE;
        //Binding to MAC broadcast address - ERROR
        if(macBroadcast.contains(arp.getSenderMACAddress().toLowerCase())) returnCode = ERROR;
        //Binding to IP addresses having 255 in the last two integers(Broadcast) - ERROR
        if(arp.getSenderIPAddress().substring(arp.getSenderIPAddress().length()-4).contains("255")) returnCode = ERROR;
        break;
      case PacketParser.REQUEST:
        //Request:
	      //-To broadcast address - NORMAL
	      //-To unicast/multicast address - NOTICE
        if(returnCode!=ERROR &&!macBroadcast.contains(arp.getDestination().toLowerCase())) returnCode = NOTICE;
        break;
    }
    return returnCode;
  }

  /**
  * Checks if an ARP packet indicates unusual responses of a host
  */
  public int foreignInspectArp(ARPPacket arp){
    if(arp.getOpCode()==PacketParser.REPLY||isGratuitous(arp)) return PERMITTED;
    String source = arp.getSource();
    //Making list of all packets that host should have received
    ArrayList<EthernetPacket> receivedPackets = PacketMonitor.getPacketsByDestination(source);
    for(String addr:macBroadcast) receivedPackets.addAll(PacketMonitor.getPacketsByDestination(addr));
    //Checking for address he wants to know who it belongs to
    String requestedAddress = arp.getTargetIPAddress();
    for(EthernetPacket eth:receivedPackets){
      if(eth.getClass() == ARPPacket.class){
        ARPPacket recvArp = (ARPPacket)eth;
        //If the host in question should have received a reply indicating the address, return a NOTICE
        if(recvArp.getOpCode()==PacketParser.REPLY||isGratuitous(recvArp)){
          if(recvArp.getSenderIPAddress().equals(requestedAddress)){
            return NOTICE;
          }
        }
      }
    }
    return PERMITTED;
  }

  /**
  * Checks if a packet is a gratuitous ARP (If target and sender IP are same)
  */
  public boolean isGratuitous(ARPPacket arp){
    return arp.getSenderIPAddress().equals(arp.getTargetIPAddress());
  }

  /**
  * Checks whether there was a request corresponding to a reply (Not in use)
  */
  public boolean deprIsGratuitous(ARPPacket arp){
    ArrayList<EthernetPacket> packets = PacketMonitor.getPacketsByDestination(arp.getSource());
    if(packets== null) return true;
    for(int i=0; i<packets.size();i++){
      if(packets.get(i).getClass() == ARPPacket.class){
        ARPPacket arpReq = (ARPPacket)packets.get(i);
        if(arpReq.getTargetIPAddress().equals(arp.getSenderIPAddress())){
          return false;
        }
      }
    }
    return true;
  }

  /**
  * Loads and interprets the config file
  * Right now it parses lines with the CONFIGDELIM prefix as indicators what
  * the lines below represent and adds them accordingly to the fields
  */
  private void configure(){
    ArrayList<String> configLines = loadConfigFile(CONFIGPATH);
    String parseState = "";
    for(String l:configLines){
      if(l.contains(CONFIGDELIM)){
        parseState = l.substring(CONFIGDELIM.length());
        continue;
      }
      switch(parseState.toLowerCase()){
        case "":
          break;
        case "macbroadcast":
          macBroadcast.add(l);
          break;
        case "macblacklist":
          macBlackList.add(l);
          break;
        case "ipbroadcast":
          ipBroadcast.add(l);
          break;
        case "ipblacklist":
          ipBlackList.add(l);
          break;
      }
    }
  }

}
