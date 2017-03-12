import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.io.UnsupportedEncodingException;

public class DNSParser extends PacketParser{

  private final boolean DEBUG =false;
  private static final String[] dnsTypeList= new String[]{"","A","NS","MD","MF","CNAME","SOA","MB","MG","MR","NULL","WKS","PTR","HINFO","MINFO","MX","TXT"};
  private static final String[] dnsClassList = new String[]{"","IN","CS","CH","HS"};
  private DNSPacket packet;
  private static final int ANSWER=0;
  private static final int AUTHORITY=1;
  private static final int ADDITIONAL=2;


  public DNSPacket parseDNS(byte[] data, int index){
    try{
      if(data.length<12){
        handleError(1,"Packet is too short!",null);
        return null;
      }
      packet = new DNSPacket();

      //Getting QueryId
      byte[] idBytes = new byte[]{data[0],data[1]};
      packet.setQueryId(bytesToInt(idBytes));
      index+=2;
      //Getting Meta Data
      byte nextByte = data[index++];
      int[] nextMetaData = byteToBits(nextByte);
      packet.setQr(nextMetaData[0]==1);
      int opCode = (int)(nextByte>>3)&0xf;
      packet.setOpCode(opCode);
      packet.setAa(nextMetaData[5]==1);
      packet.setTc(nextMetaData[6]==1);
      packet.setRd(nextMetaData[7]==1);
      //Getting next byte of Metadata
      nextByte = data[index++];
      nextMetaData = byteToBits(nextByte);
      packet.setRa(nextMetaData[0]==1);
      int rCode = (int)(nextByte)&0xf;
      packet.setRCode(rCode);
      //Getting Count Data
      int totalCount=0;
      int count =0;
      byte[] countBytes = new byte[]{data[index],data[index+1]};
      index+=2;
      count = bytesToInt(countBytes);
      packet.setQuestionCount(count);
      totalCount += count;

      countBytes = new byte[]{data[index],data[index+1]};
      index+=2;
      count = bytesToInt(countBytes);
      packet.setAnswerCount(count);
      totalCount += count;

      countBytes = new byte[]{data[index],data[index+1]};
      index+=2;
      count = bytesToInt(countBytes);
      packet.setAuthorityCount(count);
      totalCount += count;

      countBytes = new byte[]{data[index],data[index+1]};
      index+=2;
      count = bytesToInt(countBytes);
      packet.setAdditionalRecordCount(count);
      totalCount += count;

      //Reading Data
      int questionCount = packet.getQuestionCount();
      DNSQuestion[] questions = new DNSQuestion[questionCount];
      for(int i=0; i<questionCount;i++){
        index = parseDNSQuestion(data,index,i);
      }

      int answerCount = packet.getAnswerCount();
      DNSResourceRecord[] answers = new DNSResourceRecord[answerCount];
      for(int i=0; i<answerCount;i++){
        index = parseDNSResourceRecord(data,index,i,this.ANSWER);
      }

      int authorityCount = packet.getAuthorityCount();
      DNSResourceRecord[] authorities = new DNSResourceRecord[authorityCount];
      for(int i=0; i<authorityCount;i++){
        index = parseDNSResourceRecord(data,index,i,this.AUTHORITY);
      }

      int additionalRecordCount = packet.getAdditionalRecordCount();
      DNSResourceRecord[] additionalRecords = new DNSResourceRecord[additionalRecordCount];
      for(int i=0; i<additionalRecordCount;i++){
        index = parseDNSResourceRecord(data,index,i,this.ADDITIONAL);
      }

      System.out.println(packet.toString());
      return packet;
    }
    catch(ArrayIndexOutOfBoundsException aioobe){
      handleError(1,"Malformed data. Either the packet is not DNS or it is corrupted.",(Exception)aioobe);
      return null;
    }
    catch(UnsupportedEncodingException usee){
      handleError(1,"Name or Class is not representable in ASCII.",(Exception)usee);
      return null;
    }
    catch(IndexOutOfBoundsException ioobe){
      handleError(1,"Malformed data. Either the packet is not DNS or it is corrupted.",(Exception)ioobe);
      return null;
    }
    catch(Exception e){
      handleError(2,"Unknown error.",e);
      return null;
    }
  }

  //parsing a question part
  public int parseDNSQuestion(byte[] data, int index, int questionIndex)
            throws IndexOutOfBoundsException, UnsupportedEncodingException, Exception{
    try{
      //iterate through names
      DNSQuestion query;
      ArrayList<String> qNames=new ArrayList<String>();
      index = parseLabels(index, data, qNames);
      //get type and class
      int type = bytesToInt(new byte[]{data[index],data[index+1]});
      index+=2;
      int qClass = bytesToInt(new byte[]{data[index],data[index+1]});
      index+=2;
      //Inserting data into DNSQuestion
      query = new DNSQuestion(arrayListToArray(qNames),type,qClass);
      this.packet.addQuestion(query);
      return index;
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

  public int parseDNSResourceRecord(byte[] data, int index, int recordIndex, int rrType)
            throws IndexOutOfBoundsException, UnsupportedEncodingException, Exception{
    try{
      DNSResourceRecord record;
      ArrayList<String> qNames = new ArrayList<String>();
      index = parseLabels(index,data,qNames);
      int type = bytesToInt(new byte[]{data[index],data[index+1]});
      index+=2;
      int qClass = bytesToInt(new byte[]{data[index],data[index+1]});
      index+=2;
      int ttl = bytesToInt(new byte[]{data[index],data[index+1],data[index+2],data[index+3]});
      index+=4;
      int rDataLength = bytesToInt(new byte[]{data[index],data[index+1]});
      index+=2;
      record = new DNSResourceRecord(arrayListToArray(qNames),type,qClass,ttl);
      index = parseRData(data, rDataLength, index, type, record);
      switch(rrType){
        case ANSWER:
          this.packet.addAnswer(record);
          break;
        case AUTHORITY:
          this.packet.addAuthority(record);
          break;
        case ADDITIONAL:
          this.packet.addAdditional(record);
          break;
      }
      return index;
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


    private int parseRData(byte[] data, int length, int startIndex, int type, DNSResourceRecord rr)
              throws IndexOutOfBoundsException, UnsupportedEncodingException, Exception{
      try{
        int index = startIndex;
        ArrayList<String> qNames = new ArrayList<String>();
        switch(type){
          case 1: //A
            String inetAddress = "";
            inetAddress += bytesToInt(new byte[]{data[startIndex]});
            inetAddress += ".";
            inetAddress += bytesToInt(new byte[]{data[startIndex+1]});
            inetAddress += ".";
            inetAddress += bytesToInt(new byte[]{data[startIndex+2]});
            inetAddress += ".";
            inetAddress += bytesToInt(new byte[]{data[startIndex+3]});
            rr.setRData(inetAddress);
            break;
          case 15: //MX
            int preference = bytesToInt(new byte[]{data[index],data[index+1]});
            index+=2;
            rr.setPreference(preference);
          case 2: //NS
          case 5: //CNAME
          case 12: //PTR
            //Disecting Domain Name
            qNames = new ArrayList<String>();
            parseLabels(index,data,qNames);
            String nsName = "";
            for(int i=0;i<qNames.size()-1;i++)nsName+=qNames.get(i)+".";
            nsName+=qNames.get(qNames.size()-1);
            rr.setRData(nsName);
            break;
          case 6: //SOA
            //Getting mName
            qNames = new ArrayList<String>();
            index = parseLabels(startIndex,data,qNames);
            String mName = "";
            for(int i=0;i<qNames.size()-1;i++)mName+=qNames.get(i)+".";
            mName+=qNames.get(qNames.size()-1);

            //getting rName
            qNames = new ArrayList<String>();
            index = parseLabels(index,data,qNames);
            String rName = "";
            for(int i=0;i<qNames.size()-1;i++)rName+=qNames.get(i)+".";
            rName+=qNames.get(qNames.size()-1);

            //getting integers
            int serial = bytesToInt(new byte[]{data[index],data[index+1],data[index+2],data[index+3]});
            index+=4;
            int refresh = bytesToInt(new byte[]{data[index],data[index+1],data[index+2],data[index+3]});
            index+=4;
            int retry = bytesToInt(new byte[]{data[index],data[index+1],data[index+2],data[index+3]});
            index+=4;
            int expire = bytesToInt(new byte[]{data[index],data[index+1],data[index+2],data[index+3]});
            index+=4;
            int minimum = bytesToInt(new byte[]{data[index],data[index+1],data[index+2],data[index+3]});
            index+=4;

            //saving data
            rr.setMName(mName);
            rr.setRName(rName);
            rr.setSerial(serial);
            rr.setRefresh(refresh);
            rr.setRetry(retry);
            rr.setExpire(expire);
            rr.setMinimum(minimum);
            break;
          case 16: //TXT
            byte[] nameBytes = new byte[length];
            for(int i=0;i<length;i++){
              nameBytes[i] = data[index+i];
            }
            String text = new String(nameBytes, "UTF-8");
            rr.setRData(text);
            break;
          default:
        }
        return startIndex+length;
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
    * Returns the String corresponding to the type as int
    */
    public static String dnsTypeToString(int type){
      if(type<dnsTypeList.length) return dnsTypeList[type];
      switch(type){
        case 252:
          return "AXFR";
        case 253:
          return "MAILB";
        case 254:
          return "MAILA";
        case 255:
          return "*";
      }
      return "";
    }

    /**
    * Returns the String corresponding to the class as int
    */
    public static String dnsClassToString(int qClass){
      if(qClass<dnsClassList.length)return dnsClassList[qClass];
      if(qClass == 255)return "*";
      return "";
    }


}
