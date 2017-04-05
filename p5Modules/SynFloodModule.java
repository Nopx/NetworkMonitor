import java.lang.Runnable;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.JPacketHandler;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.JProtocol;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.packet.JMemoryPacket;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.packet.JHeader;
import java.util.concurrent.ConcurrentHashMap;

public class SynFloodModule extends Module implements Runnable{

  private static ConcurrentHashMap<Integer,Integer> ipSyns;
  private static long lastCleaned=0;
  private static final long CLEANINTERVAL=30000;
  private static final int MAXSYNS = 10;
  private static final int MAXSYNSAVES = 100;

  public SynFloodModule(Integer packetId,JPacket packet){
    super(packetId,packet);
    if(ipSyns==null) ipSyns=new ConcurrentHashMap<Integer,Integer>();
  }

  public void run(){
    clean();
    Tcp tcp = new Tcp();
    Ip4 ip4 = new Ip4();
    if (packet.hasHeader(tcp)) {
      Integer source=0;
      //Increment the amount of SYNs a ip has sent or decrement if an ACK was detected
      if(packet.hasHeader(ip4)){
        source = ip4.sourceToInt();
        if(tcp.flags_SYN()){
          if(ipSyns.containsKey(source)&&ipSyns.get(source)<MAXSYNSAVES)
            ipSyns.put(source,ipSyns.get(source)+1);
          else
            ipSyns.put(source,1);
        }
        if(tcp.flags_ACK()){
          if(ipSyns.containsKey(source)&&ipSyns.get(source)<MAXSYNSAVES)
            ipSyns.put(source,ipSyns.get(source)-1);
          else
            ipSyns.put(source,0);
        }
      }
      //drop packet if SYN amount is too high
      if(ipSyns.containsKey(source)&&ipSyns.get(source)>MAXSYNS){
        IPS.getCurrentIPS().decideOnPacket(packetId,DROP);
        return;
      }
    }
    IPS.getCurrentIPS().decideOnPacket(packetId,OK);
  }

  private void clean(){
    long time = System.currentTimeMillis();
    if(time>lastCleaned+CLEANINTERVAL){
      ipSyns=new ConcurrentHashMap<Integer,Integer>();
      lastCleaned = time;
    }
  }

}
