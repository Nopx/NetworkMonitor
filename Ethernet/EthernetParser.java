public class EthernetParser extends PacketParser{

  public EthernetPacket parseEthernetPacket(byte[] data, int index){
    try{
        if(index>data.length||data.length-index<18) handleError(0,"Packet not ethernet or faulty.",null);
        String source = "";
        source += byteToHex(data[index++]);
        for(int i=0;i<5;i++){
          source+=":"+byteToHex(data[index++]);
        }
        String destination = "";
        destination += byteToHex(data[index++]);
        for(int i=0;i<5;i++){
          destination+=":"+byteToHex(data[index++]);
        }
        int type = bytesToInt(new byte[]{data[index++],data[index++]});
        int firstIndex = index;
        index = data.length -4;
        int length =index-firstIndex+4;
        int crc = bytesToInt(new byte[]{data[index++],data[index++],data[index++],data[index++]});
        EthernetPacket ethPacket = new EthernetPacket(destination,source,type,data,firstIndex,length,crc);
        return ethPacket;
      }
      catch(ArrayIndexOutOfBoundsException aioobe){
        handleError(1,"Malformed data. Either the packet is not Ethernet or it is corrupted.",(Exception)aioobe);
        return null;
      }
      catch(IndexOutOfBoundsException ioobe){
        handleError(1,"Malformed data. Either the packet is not Ethernet or it is corrupted.",(Exception)ioobe);
        return null;
      }
      catch(Exception e){
        handleError(2,"Unknown error.",e);
        return null;
      }
  }

}
