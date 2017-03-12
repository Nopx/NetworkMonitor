
public class ARPParser extends PacketParser{

  public ARPPacket parseARPPacket(byte[] data,int index, EthernetPacket eth){
    try{
      if(eth==null) eth = new EthernetPacket();
      if(data.length<8)handleError(0,"Not ARP or faulty packet.",null);
      int hardwareType = bytesToInt(new byte[]{data[index++],data[index++]});
      int protocolType = bytesToInt(new byte[]{data[index++],data[index++]});
      int hardwareSize = bytesToInt(new byte[]{data[index++]});
      int protocolSize = bytesToInt(new byte[]{data[index++]});
      int opCode = bytesToInt(new byte[]{data[index++],data[index++]});
      if(data.length-index<2*protocolSize+2*hardwareSize)handleError(0,"Not ARP or faulty packet.",null);
      String senderMACAddress = byteToHex(data[index++]);
      for(int i=0;i<hardwareSize-1;i++)
        senderMACAddress+=":"+byteToHex(data[index++]);
      String senderIPAddress = ""+bytesToInt(new byte[]{data[index++]});
      for(int i=0;i<protocolSize-1;i++)
        senderIPAddress+="."+bytesToInt(new byte[]{data[index++]});
      String targetMACAddress = byteToHex(data[index++]);
      for(int i=0;i<hardwareSize-1;i++)
        targetMACAddress+=":"+byteToHex(data[index++]);
      String targetIPAddress = ""+bytesToInt(new byte[]{data[index++]});
      for(int i=0;i<protocolSize-1;i++)
        targetIPAddress+="."+bytesToInt(new byte[]{data[index++]});
      ARPPacket arpPacket = new ARPPacket(hardwareType, protocolType, hardwareSize, protocolSize, opCode, senderMACAddress, senderIPAddress, targetMACAddress, targetIPAddress, eth);
      return arpPacket;
    }
    catch(ArrayIndexOutOfBoundsException aioobe){
      handleError(1,"Malformed data. Either the packet is not ARP or it is corrupted.",(Exception)aioobe);
      return null;
    }
    catch(IndexOutOfBoundsException ioobe){
      handleError(1,"Malformed data. Either the packet is not ARP or it is corrupted.",(Exception)ioobe);
      return null;
    }
    catch(Exception e){
      handleError(2,"Unknown error.",e);
      return null;
    }
  }

}
