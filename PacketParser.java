import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.io.UnsupportedEncodingException;

public class PacketParser{

  //Protocol types
  public static final int ARP =   0x806;
  public static final int IPV4 =  0x800;
  //Hardware types
  public static final int ETHERNET = 1;
  //Operation codes
  public static final int REQUEST = 1;
  public static final int REPLY = 2;
  public static final int ARP_NAK = 10;
  private final boolean DEBUG =false;

  public void handleError(int code,String msg, Exception e){
    ErrorHandler.handleError(code,msg,e);
  }


  /**
  * This method parses name labels recursively. A name label can either be:
  * - A String of labels terminated with a zero octet
  * - A pointer
  * - A String of labels followed by a pointer
  * The method fills up the ArrayList qNames and returns the new value of index.
  */
  protected int parseLabels(int index, byte[] data, ArrayList<String> qNames)
            throws IndexOutOfBoundsException, UnsupportedEncodingException, Exception{
    try{
      int offset = bytesToInt(new byte[]{data[index],data[index+1]});

      //In this case the labels are terminated with a zero octet
      if(data[index]==(byte)0) return index+1;

      //This means that the first 2 bits are 1s, which means it is a pointer
      if(offset>=0xC000){
        offset -= 0xC000;
        //for(int i=offset;i<data.length; i++)System.out.print(""+data[i]+",");System.out.println();
        parseLabels(offset,data,qNames);
        index+=2;
        return index;
      }
      else if(offset >=0x4000){
        //10.... and 01.... , reserved for future use
        return index;
      }
      //In this case it is a label
      else{
        int length = (int)data[index++];
        byte[] nameBytes = new byte[length];
        for(int i=0;i<length;i++)nameBytes[i]=data[index++];
        String name = new String(nameBytes, "UTF-8");
        qNames.add(name);
        return parseLabels(index,data,qNames);
      }
    }
    catch(IndexOutOfBoundsException ioobe){
      throw ioobe;
    }
    catch(UnsupportedEncodingException usee){
      throw usee;
    }
    catch(Exception e){
      throw e;
    }
  }

  /**
  * Returns an int array with elements either 0 or 1 depending on the corresponding position in the byte
  * Extracting bits out of byte by shifting and doing AND operation with the byte 00000001
  * using int type because Java does not allow hex notation for any other type
  */
  protected int[] byteToBits(byte inByte){
    int[] retBits = new int[8];
    int inByteInt = (int)inByte;
    for(int i=0;i<8;i++){
      retBits[i] = (inByteInt >>(7-i))&0x01;
    }
    return retBits;
  }

  /**
  * Converts a byte array into an Integer
  * If the array's length is >4 it will be shortened accordingly
  * If the array's length is <4 it will be padded with 0 bytes
  */
  protected int bytesToInt(byte[] bytes){
    if(bytes.length>4){
      byte[] bytesNew = new byte[4];
      bytes[0] = bytesNew[0];
      bytes[1] = bytesNew[1];
      bytes[2] = bytesNew[2];
      bytes[3] = bytesNew[3];
      bytes = bytesNew;
    }
    else{
      int padding = 4-bytes.length;
      byte[] bytesNew = new byte[4];
      for(int i=0;i<bytes.length;i++){
        bytesNew[i+padding] = bytes[i];
      }
      bytes = bytesNew;
    }
    ByteBuffer buffer = ByteBuffer.wrap(bytes);
    //buffer.order(ByteOrder.LITTLE_ENDIAN);
    int retInt = buffer.getInt();
    return retInt;
  }

  protected String[] arrayListToArray(ArrayList<String> list){
    String[] ret = new String[list.size()];
    for(int i=0;i<list.size();i++){
      ret[i]=list.get(i);
    }
    return ret;
  }

  protected String byteToHex(byte data){
    return String.format("%02X",data);
  }

  public static String ethertypeToString(int type){
    switch(type){
      case IPV4: return "IPV4";
      case ARP: return "ARP";
      default: return "Other";
    }
  }

  public static String hardwareTypeToString(int type){
    switch(type){
      case ETHERNET: return "Ethernet";
      default: return "Other";
    }
  }

  public static String opCodeToString(int opCode){
    switch(opCode){
      case REQUEST: return "Request";
      case REPLY: return "Reply";
      case ARP_NAK: return "ARP-NAK";
      default: return "Other";
    }
  }

}
