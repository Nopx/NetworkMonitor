public class ARPPacket extends EthernetPacket{

  private int hardwareType;
  private int protocolType;
  private int hardwareSize;
  private int protocolSize;
  private int opCode;
  private String senderMACAddress;
  private String senderIPAddress;
  private String targetMACAddress;
  private String targetIPAddress;

  public String toString(){
    String ret="";
    ret+="\tHardware type: "+hardwareType+" ("+PacketParser.hardwareTypeToString(hardwareType)+")\n";
    ret+="\tProtocol type: "+protocolType+" ("+PacketParser.ethertypeToString(protocolType)+")\n";
    ret+="\tHardware size: "+hardwareSize+"\n";
    ret+="\tProtocol size: "+protocolSize+"\n";
    ret+="\tOpCode: "+opCode+" ("+PacketParser.opCodeToString(opCode)+")"+"\n";
    ret+="\tSender MAC Address: "+senderMACAddress+"\n";
    ret+="\tSender IP Address: "+senderIPAddress+"\n";
    ret+="\tTarget MAC Address: "+targetMACAddress+"\n";
    ret+="\tTarget IP Address: "+targetIPAddress+"\n";
    return ret;
  }
	/**
	* Default ARPPacket constructor
	*/
	public ARPPacket(int hardwareType, int protocolType, int hardwareSize, int protocolSize, int opCode, String senderMACAddress, String senderIPAddress, String targetMACAddress, String targetIPAddress, EthernetPacket eth) {
    super(eth.getSource(),eth.getDestination(),eth.getType(),eth.getPayload(),eth.getCrc());
		this.hardwareType = hardwareType;
		this.protocolType = protocolType;
		this.hardwareSize = hardwareSize;
		this.protocolSize = protocolSize;
		this.opCode = opCode;
		this.senderMACAddress = senderMACAddress;
		this.senderIPAddress = senderIPAddress;
		this.targetMACAddress = targetMACAddress;
		this.targetIPAddress = targetIPAddress;
	}

  /**
	* Default ARPPacket constructor
	*/
	public ARPPacket(int hardwareType, int protocolType, int hardwareSize, int protocolSize, int opCode, String senderMACAddress, String senderIPAddress, String targetMACAddress, String targetIPAddress,
    String source, String destination, int type, byte[] data, int firstIndex, int length, int crc) {
    super(source, destination, type, data, firstIndex, length, crc);
		this.hardwareType = hardwareType;
		this.protocolType = protocolType;
		this.hardwareSize = hardwareSize;
		this.protocolSize = protocolSize;
		this.opCode = opCode;
		this.senderMACAddress = senderMACAddress;
		this.senderIPAddress = senderIPAddress;
		this.targetMACAddress = targetMACAddress;
		this.targetIPAddress = targetIPAddress;
	}

	/**
	* Returns value of hardwareType
	* @return
	*/
	public int getHardwareType() {
		return hardwareType;
	}

	/**
	* Sets new value of hardwareType
	* @param
	*/
	public void setHardwareType(int hardwareType) {
		this.hardwareType = hardwareType;
	}

	/**
	* Returns value of protocolType
	* @return
	*/
	public int getProtocolType() {
		return protocolType;
	}

	/**
	* Sets new value of protocolType
	* @param
	*/
	public void setProtocolType(int protocolType) {
		this.protocolType = protocolType;
	}

	/**
	* Returns value of hardwareSize
	* @return
	*/
	public int getHardwareSize() {
		return hardwareSize;
	}

	/**
	* Sets new value of hardwareSize
	* @param
	*/
	public void setHardwareSize(int hardwareSize) {
		this.hardwareSize = hardwareSize;
	}

	/**
	* Returns value of protocolSize
	* @return
	*/
	public int getProtocolSize() {
		return protocolSize;
	}

	/**
	* Sets new value of protocolSize
	* @param
	*/
	public void setProtocolSize(int protocolSize) {
		this.protocolSize = protocolSize;
	}

	/**
	* Returns value of opCode
	* @return
	*/
	public int getOpCode() {
		return opCode;
	}

	/**
	* Sets new value of opCode
	* @param
	*/
	public void setOpCode(int opCode) {
		this.opCode = opCode;
	}

	/**
	* Returns value of senderMACAddress
	* @return
	*/
	public String getSenderMACAddress() {
		return senderMACAddress;
	}

	/**
	* Sets new value of senderMACAddress
	* @param
	*/
	public void setSenderMACAddress(String senderMACAddress) {
		this.senderMACAddress = senderMACAddress;
	}

	/**
	* Returns value of senderIPAddress
	* @return
	*/
	public String getSenderIPAddress() {
		return senderIPAddress;
	}

	/**
	* Sets new value of senderIPAddress
	* @param
	*/
	public void setSenderIPAddress(String senderIPAddress) {
		this.senderIPAddress = senderIPAddress;
	}

	/**
	* Returns value of targetMACAddress
	* @return
	*/
	public String getTargetMACAddress() {
		return targetMACAddress;
	}

	/**
	* Sets new value of targetMACAddress
	* @param
	*/
	public void setTargetMACAddress(String targetMACAddress) {
		this.targetMACAddress = targetMACAddress;
	}

	/**
	* Returns value of targetIPAddress
	* @return
	*/
	public String getTargetIPAddress() {
		return targetIPAddress;
	}

	/**
	* Sets new value of targetIPAddress
	* @param
	*/
	public void setTargetIPAddress(String targetIPAddress) {
		this.targetIPAddress = targetIPAddress;
	}

}
