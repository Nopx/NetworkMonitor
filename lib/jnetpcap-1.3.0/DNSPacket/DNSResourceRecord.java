public class DNSResourceRecord{

  private String[] qName;
  private int type;
  private int qClass;
  private int ttl;
  private String rData;
  private int preference;
  private String mName;
  private String rName;
  private int serial;
  private int refresh;
  private int retry;
  private int expire;
  private int minimum;

  @Override
  public String toString(){
    String ret = "";
    ret+="\t\tName: ";
    for(int i=0; i<qName.length-1;i++)ret+=qName[i]+".";
    if(qName.length>0)
      ret+=qName[qName.length-1]+"\n";
    ret+="\t\tType: "+PacketParser.dnsTypeToString(type)+" ("+type+")"+"\n";
    ret+="\t\tClass: "+PacketParser.dnsClassToString(qClass)+" ("+qClass+")"+"\n";
    ret+="\t\tTime to live: "+ttl+"\n ";
    switch(type){
      case 1: //A
        ret+="\t\tAddress: "+rData+"\n";
        break;
      case 15: //MX
        ret+="\t\tPreference: "+preference+"\n";
        ret+="\t\tMail Exchange: "+rData+"\n";
        break;
      case 2: //NS
        ret+= "\t\tName Server: "+rData+"\n";
        break;
      case 5: //CNAME
        ret+="\t\tCanonical Name: "+rData+"\n";
        break;
      case 12: //PTR
        ret+="\t\tPointer Name: "+rData+"\n";
        break;
      case 6: //SOA
        ret+="\t\tPrimary Name: "+mName+"\n";
        ret+="\t\tResponsible authority's mailbox: "+rName+"\n";
        ret+="\t\tSerial: "+serial+"\n";
        ret+="\t\tRefresh time: "+refresh+"\n";
        ret+="\t\tRetry time: "+retry+"\n";
        ret+="\t\tExpire time: "+expire+"\n";
        ret+="\t\tMinimum TTL: "+minimum+"\n";
        break;
      case 16: //TXT
        ret+="\t\tText: "+rData+"\n";
        break;
    }
    return ret;
  }

	/**
	* Default DNSResourceRecord constructor
	*/
	public DNSResourceRecord(String[] qName, int type, int qClass, int ttl) {
		this.qName = qName;
		this.type = type;
		this.qClass = qClass;
		this.ttl = ttl;
	}

  /**
	* Returns value of rData
	* @return
	*/
  public String getRData() {
    return rData;
  }

  /**
	* Sets new value of rData
	* @param
	*/
  public void setRData(String rData){
    this.rData = rData;
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

	/**
	* Returns value of qClass
	* @return
	*/
	public int getQClass() {
		return qClass;
	}

	/**
	* Sets new value of qClass
	* @param
	*/
	public void setQClass(int qClass) {
		this.qClass = qClass;
	}

	/**
	* Returns value of ttl
	* @return
	*/
	public int getTtl() {
		return ttl;
	}

	/**
	* Sets new value of ttl
	* @param
	*/
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}

  /**
  * Returns value of preference
  * @return
  */
  public int getPreference() {
    return preference;
  }

  /**
  * Sets new value of preference
  * @param
  */
  public void setPreference(int preference) {
    this.preference = preference;
  }

  /**
  * Returns value of mName
  * @return
  */
  public String getMName() {
    return mName;
  }

  /**
  * Sets new value of mName
  * @param
  */
  public void setMName(String mName) {
    this.mName = mName;
  }

  /**
  * Returns value of rName
  * @return
  */
  public String getRName() {
    return rName;
  }

  /**
  * Sets new value of rName
  * @param
  */
  public void setRName(String rName) {
    this.rName = rName;
  }

  /**
  * Returns value of serial
  * @return
  */
  public int getSerial() {
    return serial;
  }

  /**
  * Sets new value of serial
  * @param
  */
  public void setSerial(int serial) {
    this.serial = serial;
  }

  /**
  * Returns value of refresh
  * @return
  */
  public int getRefresh() {
    return refresh;
  }

  /**
  * Sets new value of refresh
  * @param
  */
  public void setRefresh(int refresh) {
    this.refresh = refresh;
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
  * Returns value of expire
  * @return
  */
  public int getExpire() {
    return expire;
  }

  /**
  * Sets new value of expire
  * @param
  */
  public void setExpire(int expire) {
    this.expire = expire;
  }

  /**
  * Returns value of minimum
  * @return
  */
  public int getMinimum() {
    return minimum;
  }

  /**
  * Sets new value of minimum
  * @param
  */
  public void setMinimum(int minimum) {
    this.minimum = minimum;
  }
}
