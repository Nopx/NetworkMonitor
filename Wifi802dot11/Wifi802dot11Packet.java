public class Wifi802dot11Packet{

  private int protocolVersion;
  private int type;
  private int subtype;
  private int toDs;
  private int fromDs;
  private int moreFrag;
  private int retry;
  private int pwrMgt;
  private int moreData;
  private int protect;
  private int ordered;
  private int duration;
  private String address1;
  private String address2;
  private String address3;
  private int sequenceControl;
  private int fragmentNumber;
  private byte[] payload;
  private DotAddition addition;
  private int fcs;

  public String toString(){
    String ret="";
    ret+="\tType/Subtype: "+Wifi802dot11Parser.fullTypeToString(type,subtype)+"\n";
    ret+="\tFrame Control Field: "+"\n";
    ret+="\t\tVersion: "+protocolVersion+"\n";
    ret+="\t\tType: "+type+"("+Wifi802dot11Parser.typeToString(type)+")"+"\n";
    ret+="\t\tSubtype: "+subtype+"("+Wifi802dot11Parser.subtypeToString(subtype)+")"+"\n";
    ret+="\tFlags: "+"\n";
    ret+="\t\tTo DS: "+toDs+"\n";
    ret+="\t\tFrom DS: "+fromDs+"\n";
    ret+="\t\tMore Fragments: "+moreFrag+"\n";
    ret+="\t\tRetry: "+retry+"\n";
    ret+="\t\tPower Management: "+pwrMgt+"\n";
    ret+="\t\tMore Data: "+moreData+"\n";
    ret+="\t\tProtected: "+protect+"\n";
    ret+="\t\tOrdered: "+ordered+"\n";
    ret+="\tDuration: "+duration+"\n";
    ret+="\tAddress 1: "+address1+"\n";
    ret+="\tAddress 2: "+address2+"\n";
    ret+="\tAddress 3: "+address3+"\n";
    ret+="\tSequence Number: "+sequenceControl+"\n";
    ret+="\tFragment Number: "+fragmentNumber+"\n";
    ret+="\tFCS: "+fcs+"\n";
    return ret;
  }

	/**
	* Default 802dot11Packet constructor
	*/
	public Wifi802dot11Packet(int protocolVersion, int type, int subtype, int toDs,
  int fromDs, int moreFrag, int retry, int pwrMgt, int moreData,
  int protect, int ordered, int duration, String address1,
  String address2, String address3, int sequenceControl,
  int fragmentNumber, int fcs, byte[] payload, DotAddition addition){
    this.protocolVersion = protocolVersion;
		this.type = type;
		this.subtype = subtype;
		this.toDs = toDs;
		this.fromDs = fromDs;
		this.moreFrag = moreFrag;
		this.retry = retry;
		this.pwrMgt = pwrMgt;
		this.moreData = moreData;
		this.protect = protect;
		this.ordered = ordered;
		this.duration = duration;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		this.sequenceControl = sequenceControl;
		this.fragmentNumber = fragmentNumber;
		this.payload = payload;
		this.fcs = fcs;
    this.addition = addition;
	}

	/**
	* Returns value of protocolVersion
	* @return
	*/
	public int getProtocolVersion() {
		return protocolVersion;
	}

	/**
	* Sets new value of protocolVersion
	* @param
	*/
	public void setProtocolVersion(int protocolVersion) {
		this.protocolVersion = protocolVersion;
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

	/**
	* Returns value of subType
	* @return
	*/
	public int getSubtype() {
		return subtype;
	}

	/**
	* Sets new value of subType
	* @param
	*/
	public void setSubtype(int subtype) {
		this.subtype = subtype;
	}

	/**
	* Returns value of toDs
	* @return
	*/
	public int getToDs() {
		return toDs;
	}

	/**
	* Sets new value of toDs
	* @param
	*/
	public void setToDs(int toDs) {
		this.toDs = toDs;
	}

	/**
	* Returns value of fromDs
	* @return
	*/
	public int getFromDs() {
		return fromDs;
	}

	/**
	* Sets new value of fromDs
	* @param
	*/
	public void setFromDs(int fromDs) {
		this.fromDs = fromDs;
	}

	/**
	* Returns value of moreFrag
	* @return
	*/
	public int getMoreFrag() {
		return moreFrag;
	}

	/**
	* Sets new value of moreFrag
	* @param
	*/
	public void setMoreFrag(int moreFrag) {
		this.moreFrag = moreFrag;
	}

	/**
	* Returns value of retry
	* @return
	*/
	public int getRetry() {
		return retry;
	}

	/**
	* Sets new value of retry
	* @param
	*/
	public void setRetry(int retry) {
		this.retry = retry;
	}

	/**
	* Returns value of pwrMgt
	* @return
	*/
	public int getPwrMgt() {
		return pwrMgt;
	}

	/**
	* Sets new value of pwrMgt
	* @param
	*/
	public void setPwrMgt(int pwrMgt) {
		this.pwrMgt = pwrMgt;
	}

	/**
	* Returns value of moreData
	* @return
	*/
	public int getMoreData() {
		return moreData;
	}

	/**
	* Sets new value of moreData
	* @param
	*/
	public void setMoreData(int moreData) {
		this.moreData = moreData;
	}

	/**
	* Returns value of protected
	* @return
	*/
	public int getProtect() {
		return protect;
	}

	/**
	* Sets new value of protected
	* @param
	*/
	public void setProtect(int protect) {
		this.protect = protect;
	}

	/**
	* Returns value of ordered
	* @return
	*/
	public int getOrdered() {
		return ordered;
	}

	/**
	* Sets new value of ordered
	* @param
	*/
	public void setOrdered(int ordered) {
		this.ordered = ordered;
	}

	/**
	* Returns value of duration
	* @return
	*/
	public int getDuration() {
		return duration;
	}

	/**
	* Sets new value of duration
	* @param
	*/
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	* Returns value of receiverAddress
	* @return
	*/
	public String getAddress1() {
		return address1;
	}

	/**
	* Sets new value of receiverAddress
	* @param
	*/
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	* Returns value of destinationAddress
	* @return
	*/
	public String getAddress2() {
		return address2;
	}

	/**
	* Sets new value of destinationAddress
	* @param
	*/
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	* Returns value of transmitterAddress
	* @return
	*/
	public String getAddress3() {
		return address3;
	}

	/**
	* Sets new value of transmitterAddress
	* @param
	*/
	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	/**
	* Returns value of sequenceControl
	* @return
	*/
	public int getSequenceControl() {
		return sequenceControl;
	}

	/**
	* Sets new value of sequenceControl
	* @param
	*/
	public void setSequenceControl(int sequenceControl) {
		this.sequenceControl = sequenceControl;
	}

	/**
	* Returns value of fragmentNumber
	* @return
	*/
	public int getFragmentNumber() {
		return fragmentNumber;
	}

	/**
	* Sets new value of fragmentNumber
	* @param
	*/
	public void setFragmentNumber(int fragmentNumber) {
		this.fragmentNumber = fragmentNumber;
	}

	/**
	* Returns value of body
	* @return
	*/
	public byte[] getPayload() {
		return payload;
	}

	/**
	* Sets new value of body
	* @param
	*/
	public void setPayload(byte[] payload) {
		this.payload = payload;
	}

  public DotAddition getAddition(){
    return addition;
  }

  public void setAddition(DotAddition addition){
    this.addition=addition;
  }

	/**
	* Returns value of fcs
	* @return
	*/
	public int getFcs() {
		return fcs;
	}

	/**
	* Sets new value of fcs
	* @param
	*/
	public void setFcs(int fcs) {
		this.fcs = fcs;
	}

}
