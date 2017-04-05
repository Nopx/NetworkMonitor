import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.PcapHeader;
import java.util.List;
import java.util.ArrayList;
import java.lang.StringBuilder;
import java.nio.ByteBuffer;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.JPacketHandler;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;
import org.jnetpcap.protocol.JProtocol;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.network.Arp;
import org.jnetpcap.packet.JMemoryPacket;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.packet.JHeader;
import org.jnetpcap.PcapDumper;
import org.jnetpcap.PcapHandler;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ConcurrentHashMap;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.packet.JMemoryPacket;

public class IPS{

  private static IPS currentIPS;
  private ArrayList devs;
  private StringBuilder errbuff;
  private Pcap rawPcap = new Pcap();
  private Pcap outPcap = new Pcap();
  String device = "any";
  String deviceOut = "any";
  private int listeningPort = 80;
  private int forwardingPort = 8901;
  private byte[] serverIp;
  private byte[] serverMac;
  private boolean layer7=false;

  private ConcurrentHashMap<Integer,JPacket> packetMap = new ConcurrentHashMap<Integer,JPacket>();
  private ConcurrentHashMap<Integer,Integer> counterMap = new ConcurrentHashMap<Integer,Integer>();


  public static void main(String[] args){
    if(args.length<=6){
      System.out.println("Arguments:");
      System.out.println("\t1.Device to capture from");
      System.out.println("\t2.Device to forward to");
      System.out.println("\t3.Input port");
      System.out.println("\t4.Output port");
      System.out.println("\t5.Server IP");
      System.out.println("\t6.Server MAC Address");
      System.out.println("\t7.1 or 0 for layer 7 mode");
      System.out.println("\t7.Source File (optional)");
      System.out.println("If no Destination File is given, the packets will be interpreted and printed to the output.");
      return;
    }
    String device = args[0];
    String deviceOut=args[1];
    int amount = -1;
    int portIn = 80;
    int portOut = 8901;
    String serverIp = args[4];
    String serverMac = args[5];
    boolean layer7 = args[6].equals("1");

    byte[] ipInt=new byte[4];
    byte[] macInt=new byte[6];
    try{
      //make ip an integer
      String[] ipSplit = serverIp.split("\\.");
      for(int i=0;i<4;i++){
        ipInt[i]= (byte)Integer.parseInt(ipSplit[i]);
      }
      String[] macSplit = serverMac.split("\\:");
      for(int i=0;i<6;i++){
        macInt[i]= (byte)Integer.parseInt(macSplit[i],16);
      }
      portIn = Integer.parseInt(args[2]);
      portOut = Integer.parseInt(args[3]);
    }
    catch(Exception e){
      System.out.println("Invalid ports or Addresses");
      return;
    }
    String source = "";
    if(args.length>7) source = args[7];
    IPS ips = new IPS(device,deviceOut,portIn,portOut,ipInt,macInt,layer7);
    try{
      ips.init(source);
      //ips.sendHTTPRedirect("127.0.0.1","8.8.8.8",4567,80,1,2);
      ips.sniffToConsole(amount);
    }
    catch(Exception e){
      e.printStackTrace();
    }
    finally{
      ips.close();
    }

  }

  public static IPS getCurrentIPS(){
    return currentIPS;
  }

  public IPS(String device, String deviceOut, int portIn, int portOut, byte[] serverIp, byte[] serverMac, boolean layer7){
    this.device = device;
    this.listeningPort=portIn;
    this.forwardingPort=portOut;
    this.serverIp = serverIp;
    this.serverMac = serverMac;
    this.deviceOut = deviceOut;
    this.layer7 = layer7;
    devs = new ArrayList<PcapIf>();
    errbuff = new StringBuilder();
    currentIPS=this;
  }

  //Initializes the pcap utility
  public int init(String filePath){
    int snaplen=65535;
    int promisc =Pcap.MODE_PROMISCUOUS;
    int timeout=0;
    if(filePath==null|| filePath.length()==0)
      rawPcap=Pcap.openLive(device,snaplen,promisc,timeout,errbuff);
    else
      rawPcap = Pcap.openOffline(filePath, errbuff);
    outPcap = Pcap.openLive(deviceOut,snaplen,promisc,timeout,errbuff);
    System.out.println(errbuff.toString());
    return 0;
  }

  //Sniffs cnt packets and prints the parsed result to the console
  public void sniffToConsole(int cnt){
    rawPcap.loop(cnt, new JPacketHandler<StringBuilder>() {
            final Tcp tcp = new Tcp();
            final Http http = new Http();

            synchronized public void nextPacket(JPacket packet, StringBuilder errbuf) {

              //Only selects TCP packets to port listeningPort
              if (packet.hasHeader(tcp)&&tcp.destination()==listeningPort) {
                Integer packetHash = packet.hashCode();
                //added to moduleAmount so that a decision cannot be made before every module has been started
                int moduleAddOverlay = 30000;
                int moduleAmount = moduleAddOverlay;
                packetMap.put(packetHash,packet);
                counterMap.put(packetHash,moduleAmount);
                moduleAmount=0;
                SynFloodModule synMod = new SynFloodModule(packetHash,packet);
                new Thread(synMod).start();
                moduleAmount++;
                //HTTP Layer 7 Mode
                if(layer7 && packet.hasHeader(http)){
                  HTTPRedirectModule httpMod = new HTTPRedirectModule(packetHash,packet);
                  new Thread(httpMod).start();
                  moduleAmount++;
                }
                counterMap.put(packetHash,counterMap.get(packetHash)-moduleAddOverlay+moduleAmount+1);
                //make sure packet gets decided on even if there is no module for it
                decideOnPacket(packetHash,0);
              }

            }

        }, errbuff);
  }

  //Every Module calls this method and this method continues the decision for every packet
  public void decideOnPacket(Integer packetId, int decision){
    if(!counterMap.containsKey(packetId) || !packetMap.containsKey(packetId)) return;
    //get and decrement the amount of decisions which are still pending
    int decisionsPending = counterMap.get(packetId)-1;
    if(decision==Module.DROP) {
      JPacket packet = packetMap.remove(packetId);
      counterMap.remove(packetId);
      dropHandle(packet);
      return;
    }
    if(decisionsPending<=0){
      JPacket packet = packetMap.remove(packetId);
      counterMap.remove(packetId);
      forwardHandle(packet);
      return;
    }
    counterMap.put(packetId,decisionsPending);
    return;
  }

  private void dropHandle(JPacket packet){
    Ip4 ip4 = new Ip4();
    String sourceStr = "";
    if(packet.hasHeader(ip4)){
      byte[] source = ip4.source();
      for(int i=0;i<source.length-1;i++){
        int nextInt = (int)source[i];
        if(nextInt<0)nextInt=128+(128+nextInt);
        sourceStr+=nextInt+".";
      }
      int nextInt = (int)source[source.length-1];
      if(nextInt<0)nextInt=128+(128+nextInt);
      sourceStr+=nextInt;
    }
    else{
      sourceStr="0.0.0.0";
    }
    System.out.println("Packet number "+packet.hashCode()+" from "+sourceStr+" was dropped!");
  }

  private void forwardHandle(JPacket packet){
    Tcp tcp = new Tcp();
    Ip4 ip4 = new Ip4();
    Ethernet eth = new Ethernet();
    String sourceStr = "";
    if(packet.hasHeader(tcp)){
      tcp.destination(forwardingPort);
      //subtracting 17085 because for some weird reason the checksum is always falsely calculated by this amount
      tcp.checksum(tcp.calculateChecksum()-17085);
    }
    if(packet.hasHeader(ip4)){
      byte[] source = ip4.source();
      for(int i=0;i<source.length-1;i++){
        int nextInt = (int)source[i];
        if(nextInt<0)nextInt=128+(128+nextInt);
        sourceStr+=nextInt+".";
      }
      int nextInt = (int)source[source.length-1];
      if(nextInt<0)nextInt=128+(128+nextInt);
      sourceStr+=nextInt;
      ip4.destination(serverIp);
      ip4.checksum(ip4.calculateChecksum());
    }
    else{
      sourceStr="0.0.0.0";
    }
    if(packet.hasHeader(eth)){
      eth.destination(serverMac);
      eth.checksum(eth.calculateChecksum());
    }
    outPcap.sendPacket(packet);
    System.out.println("Packet number "+packet.hashCode()+" from "+sourceStr+" was forwarded!");
  }

  public void sendHTTPRedirect(String urlExtension, String destinationIP,String sourceIP, int destinationPort, int sourcePort, long seq, long ack, byte[] timestamps){
    byte[] dstIpInt=new byte[4];
    byte[] srcIpInt=new byte[4];
    try{
      //make ip an integer
      String[] ipSplit = destinationIP.split("\\.");
      for(int i=0;i<4;i++){
        dstIpInt[i]= (byte)Integer.parseInt(ipSplit[i]);
      }
      ipSplit = sourceIP.split("\\.");
      for(int i=0;i<4;i++){
        srcIpInt[i]= (byte)Integer.parseInt(ipSplit[i]);
      }
    }
    catch(Exception e){
      //TODO Fix if somebody injects a bad ipaddress
      e.printStackTrace();
      return;
    }

    //TCP ACK
    //Using staple data to create a new packet, since there is no other way in JNetPcap
    String data = "b888e3367a8e0800276de6aa080045000034b16c4000400605dac0a80116c0a801170050b60483990d8e8370d5c5801000eb555b00000101080a005dbce200483ebf";
    JMemoryPacket packetAck = new JMemoryPacket(hexStringToByteArray(data));
    for(int i=58;i<timestamps.length;i++)
      packetAck.setByte(i,timestamps[i]);
    packetAck.scan(JProtocol.ETHERNET_ID);
    Ip4 ip4Ack = new Ip4();
    Tcp tcpAck = new Tcp();
    Ethernet ethAck = new Ethernet();
    if(packetAck.hasHeader(tcpAck)){
      tcpAck.destination(destinationPort);
      tcpAck.source(sourcePort);
      tcpAck.seq(seq);
      tcpAck.ack(ack);
      tcpAck.checksum(tcpAck.calculateChecksum());
    }
    if(packetAck.hasHeader(ip4Ack)){
      ip4Ack.destination(dstIpInt);
      ip4Ack.source(srcIpInt);
      ip4Ack.length(data.length()/2-14);
      ip4Ack.checksum(ip4Ack.calculateChecksum());
    }
    if(packetAck.hasHeader(ethAck)){
      ethAck.destination(new byte[]{0,0,0,0,0,0});
      ethAck.source(new byte[]{0,0,0,0,0,0});
      ethAck.checksum(ethAck.calculateChecksum());
    }
    rawPcap.sendPacket(java.nio.ByteBuffer.wrap( packetAck.getByteArray(0, packetAck.size() ) ));

    //HTTP Redirect
    String sourceIPNum ="";
    for(int i=0;i<sourceIP.length();i++){
      sourceIPNum+=String.format("%02x",(int)(sourceIP.charAt(i)));
    }
    String urlExtensionHex ="";
    for(int i=0;i<urlExtension.length();i++){
      urlExtensionHex+=String.format("%02x",(int)(urlExtension.charAt(i)));
    }
    //Using staple data to create a new packet, since there is no other way in JNetPcap
    String dateServer="446174653a205765642c2030352041707220323031372031333a32363a353320474d540d0a5365727665723a204170616368652f322e342e313820285562756e7475290d0a";
    String input = "000000000000000000000000080045000255d91140004006dc12c0a80117c0a80117005089f6f88d891fda8c5b2a8018015e85c600000101080a0014500f0014500f485454502f312e312033303220466f756e640d0a"
    +dateServer
    +"4c6f636174696f6e3a20687474703a2f2f"
    +sourceIPNum+"2f"
    +urlExtensionHex
    +"0d0a436f6e74656e742d4c656e6774683a203239310d0a4b6565702d416c6976653a2074696d656f75743d352c206d61783d3130300d0a436f6e6e656374696f6e3a204b6565702d416c6976650d0a436f6e74656e742d547970653a20746578742f68746d6c3b20636861727365743d69736f2d383835392d310d0a0d0a"
    +"3c21444f43545950452048544d4c205055424c494320222d2f2f494554462f2f4454442048544d4c20322e302f2f454e223e0a3c68746d6c3e3c686561643e0a3c7469746c653e33303220466f756e643c2f7469746c653e0a3c2f686561643e3c626f64793e0a3c68313e466f756e643c2f68313e0a3c703e54686520646f63756d656e7420686173206d6f766564203c6120687265663d22687474703a2f2f"
    +sourceIPNum+"2f"+urlExtensionHex //new Address
    +"223e686572653c2f613e2e3c2f703e0a3c68723e0a3c616464726573733e4170616368652f322e342e313820285562756e7475292053657276657220617420"
    +sourceIPNum //IP
    +"20506f72742038303c2f616464726573733e0a3c2f626f64793e3c2f68746d6c3e0a";
    JMemoryPacket packet = new JMemoryPacket(hexStringToByteArray(input));
    for(int i=58;i<timestamps.length;i++)
      packet.setByte(i,timestamps[i]);
    packet.scan(JProtocol.ETHERNET_ID);
    Ip4 ip4 = new Ip4();
    Http http = new Http();
    Tcp tcp = new Tcp();
    Ethernet eth = new Ethernet();
    if(packet.hasHeader(ip4)){
      ip4.destination(dstIpInt);
      ip4.source(srcIpInt);
      ip4.length(input.length()/2-14);
      ip4.checksum(ip4.calculateChecksum());
    }
    if(packet.hasHeader(tcp)){
      tcp.destination(destinationPort);
      tcp.source(sourcePort);
      tcp.seq(seq);
      tcp.ack(ack);
      tcp.checksum(tcp.calculateChecksum());
    }
    if(packet.hasHeader(eth)){
      eth.checksum(eth.calculateChecksum());
    }
    rawPcap.sendPacket(java.nio.ByteBuffer.wrap( packet.getByteArray(0, packet.size() ) ));
  }

  public void close(){
    rawPcap.close();
  }

  private static byte[] hexStringToByteArray(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                             + Character.digit(s.charAt(i+1), 16));
    }
    return data;
  }

}
