public class DotAddition{

  private int type;
  private byte[] data;

  public String toString(){
    String ret="Data:\n";
    for(int i=0;i<data.length;i++){
      ret+=""+new PacketParser().byteToHex(data[i])+((i+1)%8==0?"  ":" ");
      if((i+1)%16==0)
        ret+="\n";
    }
    return ret+"\n";
  }

	/**
	* Default DotAddition constructor
	*/
	public DotAddition(int type, byte[] data) {
		this.type = type;
		this.data = data;
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
	* Returns value of data
	* @return
	*/
	public byte[] getData() {
		return data;
	}

	/**
	* Sets new value of data
	* @param
	*/
	public void setData(byte[] data) {
		this.data = data;
	}

}
