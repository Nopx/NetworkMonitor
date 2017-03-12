public class DNSQuestion{

  private String[] qName;
  private int type;
  private int qClass;


  public DNSQuestion(String[] qName, int type, int qClass){
    this.qName = qName;
    this.type = type;
    this.qClass = qClass;
  }

  @Override
  public String toString(){
    String ret = "";
    ret+="\t\tName: ";
    for(int i=0; i<qName.length-1;i++)ret+=qName[i]+".";
    ret+=qName[qName.length-1]+"\n";
    ret+="\t\tType: "+DNSParser.dnsTypeToString(type)+" ("+type+")"+"\n";
    ret+="\t\tClass: "+DNSParser.dnsClassToString(qClass)+" ("+qClass+")"+"\n";
    return ret;
  }

	/**
	* Returns value of class
	* @return
	*/
	public int getQClass() {
		return qClass;
	}

	/**
	* Sets new value of type
	* @param
	*/
	public void setQClass(int qClass) {
		this.qClass = qClass;
	}
	/**
	* Returns value of qName
	* @return
	*/
	public String[] getQName() {
		return qName;
	}

	/**
	* Sets new value of qName
	* @param
	*/
	public void setQName(String[] qName) {
		this.qName = qName;
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
}
