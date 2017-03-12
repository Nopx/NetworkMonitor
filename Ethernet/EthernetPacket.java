public class EthernetPacket{

  private String source;
  private String destination;
  private int type;
  private byte[] payload;
  private int crc;
  private long timestamp=System.currentTimeMillis();

  public String toString(){
    String ret = "";
    ret+="Source: \t"+source+"\n";
    ret+="Destination:\t"+destination+"\n";
    ret+="Type: "+type+"("+EthernetParser.ethertypeToString(type)+")"+"\n";
    ret+="CRC: "+crc+"\n";
    return ret;
  }

  public String payloadToString(){
    String ret ="|";
    if(payload!=null) for(int i =0;i<payload.length;i++)
        ret+=String.format("%02X ",payload[i])+"|";
    return ret;
  }


  /**
  * Empty constructor
  */
  public EthernetPacket(){

  }

  /**
	* EthernetPacket constructor where only part of the input should be the payload
  * Where the actual payload in the data array is is defined by firstIndex and length
	*/
	public EthernetPacket(String source, String destination, int type, byte[] data, int firstIndex, int length, int crc) {
		this.source = source;
		this.destination = destination;
		this.type = type;
    this.crc = crc;
    if(firstIndex <length){
      this.payload = new byte[length];
      for(int i = 0; i<length;i++)
        this.payload[i]=data[i+firstIndex];
    }

	}

	/**
	* Default EthernetPacket constructor
	*/
	public EthernetPacket(String source, String destination, int type, byte[] payload, int crc) {
		this.source = source;
		this.destination = destination;
		this.type = type;
    this.crc = crc;
		this.payload = payload;
	}

	/**
	* Returns value of source
	* @return
	*/
	public String getSource() {
		return source;
	}

	/**
	* Sets new value of source
	* @param
	*/
	public void setSource(String source) {
		this.source = source;
	}

	/**
	* Returns value of destination
	* @return
	*/
	public String getDestination() {
		return destination;
	}

	/**
	* Sets new value of destination
	* @param
	*/
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	* Returns value of type
	* @return
	*/
	public int getType() {
		return type;
	}

	/**
	* Sets new value of type
	* @param
	*/
	public void setType(int type) {
		this.type = type;
	}

  public byte[] getPayload(){
    return payload;
  }

  public int getCrc(){
    return crc;
  }

  public void setCrc(int crc){
    this.crc = crc;
  }

  public long getTimestamp(){
    return timestamp;
  }

}
