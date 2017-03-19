import java.util.Arrays;

public class Wifi802dot11Parser extends PacketParser{

  //Management frames
  public static final int MANAGEMENT_FRAME=0;
  public static final int ASSOCIATION_REQUEST=0;
  public static final int ASSOCIATION_RESPONSE=1;
  public static final int REASSOCIATION_REQUEST=2;
  public static final int REASSOCIATION_RESPONSE=3;
  public static final int PROBE_REQUEST=4;
  public static final int PROBE_RESPONSE=5;
  public static final int BEACON=8;
  public static final int ATIM=9;
  public static final int DISASSOCIATION=10;
  public static final int AUTHENTICATION=11;
  public static final int DEAUTHENTICATION=12;

  //Control frames
  public static final int CONTROL_FRAME=1;
  public static final int PS=26;
  public static final int RTS=27;
  public static final int CTS=28;
  public static final int ACK=29;
  public static final int CF_END=30;
  public static final int CF_END_ACK=31;

  //Data frames
  public static final int DATA_FRAME=2;
  public static final int DATA=32;
  public static final int DATA_CF_ACK=33;
  public static final int DATA_CF_POLL=34;
  public static final int DATA_CF_ACK_CF_POLL=35;
  public static final int DATA_NULL=36;
  public static final int CF_ACK=37;
  public static final int CF_POLL=38;
  public static final int CF_ACK_CF_POLL=39;


  public static String typeToString(int type){
    String ret="";
    switch(type){
      case MANAGEMENT_FRAME:
        ret+="Management";
        break;
      case CONTROL_FRAME:
        ret+="Control";
        break;
      case DATA_FRAME:
        ret+="DATA";
    }
    return ret;
  }

  public static String subtypeToString(int subtype){
    String ret="";
    switch(subtype){
      case ASSOCIATION_REQUEST:
      	ret+="Association request";
      	break;
      case ASSOCIATION_RESPONSE:
      	ret+="Association response";
      	break;
      case REASSOCIATION_REQUEST:
      	ret+="Reassociation request";
      	break;
      case REASSOCIATION_RESPONSE:
      	ret+="Reassociation response";
      	break;
      case PROBE_REQUEST:
      	ret+="Probe request";
      	break;
      case PROBE_RESPONSE:
      	ret+="Probe response";
      	break;
      case BEACON:
      	ret+="Beacon";
      	break;
      case ATIM:
      	ret+="Announcement traffic indication message(ATIM)";
      	break;
      case DISASSOCIATION:
      	ret+="Disassociation";
      	break;
      case AUTHENTICATION:
      	ret+="Authentication";
      	break;
      case DEAUTHENTICATION:
      	ret+="Deauthentication";
      	break;

      case PS:
      	ret+="Power Save (PS)-Poll";
      	break;
      case RTS:
      	ret+="Request To Send (RTS)-Poll";
      	break;
      case CTS:
      	ret+="Clear To Send (CTS)";
      	break;
      case ACK:
      	ret+="Acknowledgement(ACK)";
      	break;
      case CF_END:
      	ret+="Contention-Free (CF)-End";
      	break;
      case CF_END_ACK:
      	ret+="Contention-Free End + Contention-Free Acknowledgement";
      	break;

      case DATA:
      	ret+="Data";
      	break;
      case DATA_CF_ACK:
      	ret+="Data + Contention-Free Acknowledgement";
      	break;
      case DATA_CF_POLL:
      	ret+="Data + Contention-Free Poll";
      	break;
      case DATA_CF_ACK_CF_POLL:
      	ret+="Data + Contention-Free Poll + Contention-Free Acknowledgement";
      	break;
      case DATA_NULL:
      	ret+="Null function (no data)";
      	break;
      case CF_ACK:
      	ret+="Contention-Free Acknowledgement";
      	break;
      case CF_POLL:
      	ret+="Contention-Free Poll";
      	break;
      case CF_ACK_CF_POLL:
      	ret+="Contention-Free Acknowledgement + Contention-Free Poll";
      	break;
    }
    return ret;
  }

  public static String fullTypeToString(int type,int subtype){
    String ret="";
    ret+= typeToString(type)+": ";
    ret+= subtypeToString(subtype);
    return ret;
  }

  public Wifi802dot11Packet parseWifi802dot11(byte[] data, int index){
    try{
      //Frame Control
      int[] fc1 = byteToBitsReverse(data[index++]);
      int[] fc2 = byteToBitsReverse(data[index++]);
      int fcIndex = 0;
      int protocolVersion = bitsToInt(new int[]{fc1[fcIndex++],fc1[fcIndex++]});
      int type = bitsToInt(new int[]{fc1[fcIndex++],fc1[fcIndex++]});
      int subType =  bitsToInt(new int[]{fc1[fcIndex++],fc1[fcIndex++],fc1[fcIndex++],fc1[fcIndex++]});
      fcIndex = 0;
      int toDs = fc2[fcIndex++];
      int fromDs = fc2[fcIndex++];
      int moreFrag = fc2[fcIndex++];
      int retry = fc2[fcIndex++];
      int pwrMgt = fc2[fcIndex++];
      int moreData = fc2[fcIndex++];
      int protect = fc2[fcIndex++];
      int ordered = fc2[fcIndex++];
      //FC is over
      int duration = bytesToInt(new byte[]{data[index++],data[index++]});
      //Addresses
      String address1 = bytesToHexAddress(new byte[]{data[index++],data[index++],data[index++],data[index++],data[index++],data[index++]});
      String address2 = bytesToHexAddress(new byte[]{data[index++],data[index++],data[index++],data[index++],data[index++],data[index++]});
      String address3 = bytesToHexAddress(new byte[]{data[index++],data[index++],data[index++],data[index++],data[index++],data[index++]});
      //Sequence Control and Fragment Number
      int[] part2 = byteToBitsReverse(data[index++]);
      int[] part1 = byteToBitsReverse(data[index++]);
      int[] partCombined = new int[12];
      for(int i=0;i<4;i++)partCombined[i]=part2[i+4];
      for(int i=0;i<8;i++)partCombined[i+4]=part1[i];
      int sequenceControl = bitsToInt(partCombined);
      int fragmentNumber = bitsToInt(Arrays.copyOfRange(part2,0,3));

      byte[] payload = new byte[data.length-4-index];
      for(int i=0;i<payload.length;i++)payload[i]=data[index++];
      int fcs = bytesToInt(new byte[]{data[index++],data[index++],data[index++],data[index++]});
      return new Wifi802dot11Packet(protocolVersion, type, subType, toDs, fromDs, moreFrag, retry, pwrMgt, moreData,
                  protect, ordered, duration, address1, address2, address3,
                  sequenceControl, fragmentNumber, fcs, payload, new DotAddition(-1,payload));
    }
    catch(ArrayIndexOutOfBoundsException aioobe){
      handleError(1,"Malformed data. Either the packet is not 802.11 or it is corrupted.",(Exception)aioobe);
      return null;
    }
    catch(IndexOutOfBoundsException ioobe){
      handleError(1,"Malformed data. Either the packet is not 802.11 or it is corrupted.",(Exception)ioobe);
      return null;
    }
    catch(Exception e){
      handleError(2,"Unknown error.",e);
      return null;
    }
  }
/*
  public DotAddition parseWifi802dot11wManagement(byte[] data, int index, int type){
    switch(type){
      case ASSOCIATION_REQUEST:
      	break;
      case ASSOCIATION_RESPONSE:
      	break;
      case REASSOCIATION_REQUEST:
      	break;
      case REASSOCIATION_RESPONSE:
      	break;
      case PROBE_REQUEST:
      	break;
      case PROBE_RESPONSE:
      	break;
      case BEACON:
      	break;
      case ATIM:
      	break;
      case DISASSOCIATION:
      	break;
      case AUTHENTICATION:
      	break;
      case DEAUTHENTICATION:
      	break;
    }
  }*/

}
