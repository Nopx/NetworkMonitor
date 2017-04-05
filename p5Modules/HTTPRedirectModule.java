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
import org.jnetpcap.protocol.tcpip.Http;

public class HTTPRedirectModule extends Module implements Runnable{

  private static ConcurrentHashMap<Integer,Integer> ipBlocked;
  private static long lastCleaned=0;
  private static final long CLEANINTERVAL=30000;
  private static final int NEW = 0;
  private static final int BLOCKED = 1;
  private static final int UNBLOCKED = 2;
  private static final int REDIRECT_SENT=3;

  public HTTPRedirectModule(Integer packetId,JPacket packet){
    super( packetId, packet);
    if(ipBlocked==null) ipBlocked=new ConcurrentHashMap<Integer,Integer>();
  }

  public void run(){
    clean();
    Tcp tcp = new Tcp();
    Ip4 ip4 = new Ip4();
    if (packet.hasHeader(tcp)) {
      Integer source=0;
      if(packet.hasHeader(ip4)){
        source = ip4.sourceToInt();
      }
      //If the IP is blocked, drop the packet, If the IP is unblocked, forward it, else test the IP
      if(ipBlocked.containsKey(source)){
        System.out.println("ALLREADY CONTAINS!!!");
        switch(ipBlocked.get(source)){
          case BLOCKED:
            IPS.getCurrentIPS().decideOnPacket(packetId,DROP);
            break;
          case UNBLOCKED:
            IPS.getCurrentIPS().decideOnPacket(packetId,OK);
            break;
          case REDIRECT_SENT:
            //Send another 302 redirect and check if the client responds
            //Then send another redirect and let the original packet reach the host
            break;
        }
      }
      else{
        //Send a HTTP 302 redirect
        ipBlocked.put(source,REDIRECT_SENT);
        Http http = new Http();
        String host ="0.0.0.0";
        String extension="index.html";
        String dst="0.0.0.0";
        int destinationPort = 4567;
        int sourcePort = 80;
        long seq =0;
        long ack=0;
        int size = 0;
        if(packet.hasHeader(http)){
          host = http.fieldValue(Http.Request.Host);
          if(host.toLowerCase().equals("localhost")) host = "127.0.0.1";
          extension = http.fieldValue(Http.Request.RequestUrl);
          size+= http.size();
        }
        sourcePort = tcp.destination();
        destinationPort= tcp.source();
        seq = tcp.ack();
        ack = tcp.seq()+size;
        System.out.println("TCP SIZE: "+size);
        byte[] dstByte = ip4.source();
        int nextInt = (int)dstByte[0];
        if(nextInt<0)nextInt=128+(128+nextInt);
        dst = ""+nextInt;
        for(int i=1;i<dstByte.length;i++){
          nextInt = (int)dstByte[i];
          if(nextInt<0)nextInt=128+(128+nextInt);
          dst+="."+nextInt;
        }
        byte[] timestamps = packet.getByteArray(58,66);
        IPS.getCurrentIPS().sendHTTPRedirect("alskjdfhjkasjdfhasdf.html",dst,host,destinationPort,80,seq,ack,timestamps);
      }
    }
    //IPS.getCurrentIPS().decideOnPacket(packetId,OK);
  }

  private void clean(){
    long time = System.currentTimeMillis();
    if(time>lastCleaned+CLEANINTERVAL){
      ipBlocked=new ConcurrentHashMap<Integer,Integer>();
      lastCleaned = time;
    }
  }

}
