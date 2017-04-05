import java.lang.Runnable;
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

public class Module implements Runnable{

  public static final int OK = 0;
  public static final int DROP = 1;
  protected Integer packetId;
  protected JPacket packet;

  public Module(Integer packetId,JPacket packet){
    this.packetId = packetId;
    this.packet = packet;
  }

  public void run(){

  }

}
