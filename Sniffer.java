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
import org.jnetpcap.protocol.network.Arp;
import org.jnetpcap.packet.JHeader;
import org.jnetpcap.PcapDumper;
import org.jnetpcap.PcapHandler;

public class Sniffer{

  private ArrayList devs;
  private StringBuilder errbuff;
  private Pcap rawPcap = new Pcap();
  String device = "wlp3s0";

  public static void main(String[] args){
    if(args.length<=0){
      System.out.println("Arguments:");
      System.out.println("\t1.Device to capture from");
      System.out.println("\t2.Amount of Packets to sniff");
      System.out.println("\t3.Source File (optional)");
      System.out.println("\t4.Destination File (optional)(does not work with ARP)");
      System.out.println("If no Destination File is given, the packets will be interpreted and printed to the output.");
      return;
    }
    String device = args[0];
    int amount = -1;
    try{amount = Integer.parseInt(args[1]);}catch(Exception e){}
    if(amount ==-1){
      System.out.println("Invalid Input");
      return;
    }
    String source = "";
    if(args.length>2) source = args[2];
    String dest = "";
    if(args.length>3) dest = args[3];
    Sniffer dnsSniffer = new Sniffer(device);
    try{
      dnsSniffer.init(source);
      if(dest.length()>0)
        dnsSniffer.sniffToFile(amount,dest);
      else
        dnsSniffer.sniffToConsole(amount);
    }
    catch(Exception e){
      e.printStackTrace();
    }
    finally{
      dnsSniffer.close();
    }
  }

  public Sniffer(String device){
    this.device = device;
    devs = new ArrayList<PcapIf>();
    errbuff = new StringBuilder();
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
    System.out.println(errbuff.toString());
    return 0;
  }

  //Sniffs cnt packets and prints the parsed result to the console
  public void sniffToConsole(int cnt){
    rawPcap.loop(cnt, new JPacketHandler<StringBuilder>() {
            final Udp udp = new Udp();
            final Tcp tcp = new Tcp();
            final Arp arp = new Arp();

            public void nextPacket(JPacket packet, StringBuilder errbuf) {
              //Parsing header of packet as Ethernet header
              byte[] data = packet.getByteArray(0,packet.size());
              EthernetParser ethParser = new EthernetParser();
              EthernetPacket eth = ethParser.parseEthernetPacket(data, 0);
              ARPInspector arpInspector = new ARPInspector();
              //Parsing payload of packet as ARP data
              if(eth != null && eth.getType() == EthernetParser.ARP){
                ARPParser arpParser = new ARPParser();
                ARPPacket arp = arpParser.parseARPPacket(eth.getPayload(),0,eth);
                //Log packet
                PacketMonitor.savePacket((EthernetPacket)arp);
                //Analyze if packet conforms to standards and log analysis
                int arpReturnCode = arpInspector.inspectARP(arp);
                if(arp!=null){
                  switch(arpReturnCode){
                    case PacketInspector.ERROR:
                      PacketMonitor.errorPacket((EthernetPacket)arp);
                      break;
                    case PacketInspector.NOTICE:
                      PacketMonitor.noticePacket((EthernetPacket)arp);
                      break;
                  }
                  //Analyze if the packet indicates unwanted behaviour of a host and prints it
                  //Only checks if a host is ignoring/dropping certain packets
                  arpReturnCode = arpInspector.foreignInspectArp(arp);
                  if(arpReturnCode==PacketInspector.NOTICE)
                    System.out.println("Host: "+arp.getSource()+" is dropping ARP replies from: "+arp.getTargetIPAddress());
                  //Print packet
                  System.out.print(eth.toString());
                  System.out.println(arp.toString());
                }
              }
              if (packet.hasHeader(tcp)) {

              }

              //Checking if a packet is UDP and extracting Payload
              if (packet.hasHeader(udp)) {

                /*DNS Stuff
                System.out.println("UDP Packet found, parsing as DNS...");
                byte[] data = udp.getPayload();
                DNSParser p = new DNSParser();
                DNSPacket dnsPacket = p.parseDNS(udp.getPayload(),0);
                if(dnsPacket!=null)
                  System.out.print(dnsPacket.toString());
                  */
              }
            }

        }, errbuff);
  }

  //Sniffs cnt packets and saves them raw to file
  public void sniffToFile(int cnt, String file){
    PcapDumper dumper = rawPcap.dumpOpen(file);
    rawPcap.loop(cnt, new PcapHandler<PcapDumper>() {
            public void nextPacket(PcapDumper dumper, long seconds,
                int useconds,int caplen, int len, ByteBuffer buffer) {
                  dumper.dump(seconds, useconds, caplen, len, buffer);
                }
        }, dumper);
    }

  public void close(){
    rawPcap.close();
  }

}
