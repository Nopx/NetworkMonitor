import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.PcapHeader;
import java.util.List;
import java.util.ArrayList;
import java.lang.StringBuilder;
import java.nio.ByteBuffer;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.JPacketHandler;
import org.jnetpcap.protocol.tcpip.Udp;
import org.jnetpcap.packet.JHeader;
import org.jnetpcap.PcapDumper;
import org.jnetpcap.PcapHandler;

public class Sniffer{

  private ArrayList devs;
  private StringBuilder errbuff;
  private Pcap rawPcap = new Pcap();

  public static void main(String[] args){
    if(args.length<=0){
      System.out.println("Arguments:");
      System.out.println("\t1.Amount of Packets to sniff");
      System.out.println("\t2.Source File (optional)");
      System.out.println("\t3.Destination File (optional)");
      System.out.println("If no Destination File is given, the packets will be interpreted and printed to the output.");
      return;
    }
    int amount = -1;
    try{amount = Integer.parseInt(args[0]);}catch(Exception e){}
    if(amount ==-1){
      System.out.println("Invalid Input");
      return;
    }
    String source = "";
    if(args.length>1) source = args[1];
    String dest = "";
    if(args.length>2) dest = args[2];
    Sniffer dnsSniffer = new Sniffer();
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

  public Sniffer(){
    devs = new ArrayList<PcapIf>();
    errbuff = new StringBuilder();
  }

  //Initializes the pcap utility
  public int init(String filePath){
    String device = "any";
    int snaplen=65535;
    int promisc =0;
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

            public void nextPacket(JPacket packet, StringBuilder errbuf) {
              //Checking if a packet is UDP and extracting Payload
              if (packet.hasHeader(udp)) {
                System.out.println("UDP Packet found, parsing as DNS...");
                byte[] data = udp.getPayload();
                PacketParser p = new PacketParser();
                DNSPacket dnsPacket = p.parseDNS(udp.getPayload());
                if(dnsPacket!=null)
                  System.out.print(dnsPacket.toString());
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
