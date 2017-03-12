import java.util.ArrayList;

public class DNSPacket{

  private int queryId;
  private boolean qr;
  private int opCode;
  private boolean aa;
  private boolean tc;
  private boolean rd;
  private boolean ra;
  private int rCode;
  private boolean cd;
  private int questionCount;
  private int answerCount;
  private int authorityCount;
  private int additionalRecordCount;
  private ArrayList<DNSQuestion> questions= new ArrayList<DNSQuestion>();
  private ArrayList<DNSResourceRecord> answers= new ArrayList<DNSResourceRecord>();
  private ArrayList<DNSResourceRecord> authorities= new ArrayList<DNSResourceRecord>();
  private ArrayList<DNSResourceRecord> additional= new ArrayList<DNSResourceRecord>();

  @Override
  public String toString(){
    String ret="";
    ret+="\t---DNS PACKET---\n";
    ret+="\tQueryID: "+queryId+"\n";
    ret+="\tQR: "+(qr?1:0)+(qr?" (response)":" (query)")+"\n";
    ret+="\tOpCode: "+opCode+(opCode==0?" (Standard Query)":(opCode==1?" (Inverse Query)":" (Server Status Request)"))+"\n";
    ret+="\tAA: "+(aa?1:0)+(aa?" (Server is an authority)":" (Server is not an authority)")+"\n";
    ret+="\tTC: "+(tc?1:0)+(tc?" (Message is truncated)":" (Message is not truncated)")+"\n";
    ret+="\tRD: "+(rd?1:0)+(rd?" (Recursion desired)":" (Recursion not desired)")+"\n";
    ret+="\tRA: "+(ra?1:0)+(ra?" (Recursion available)":" (Recursion not available)")+"\n";
    ret+="\tReply Code: "+rCode+" (";
    switch(rCode){
      case 0:
        ret+="No Error";
        break;
      case 1:
        ret+="Format Error";
        break;
      case 2:
        ret+="Server Failure";
        break;
      case 3:
        ret+="Name Error";
        break;
      case 4:
        ret+="Query not supported by server";
        break;
      case 5:
        ret+="Query not permitted by server";
        break;
    }
    ret+=")"+"\n";
    ret+="\tCD: "+(cd?1:0)+(cd?" (Checking disabled)":" (Checking not disabled)")+"\n";
    ret+="\tQuestion Count: "+questionCount+"\n";
    ret+="\tAnswer RRs: "+answerCount+"\n";
    ret+="\tAuthority RRs: "+authorityCount+"\n";
    ret+="\tAdditional RRs:"+additionalRecordCount+"\n";
    for(int i=0;i<questions.size();i++){
      ret+="\tQuestion "+(i+1)+"\n";
      ret+=questions.get(i).toString();
    }
    for(int i=0;i<answers.size();i++){
      ret+="\tAnswer "+(i+1)+"\n";
      ret+=answers.get(i).toString();
    }
    for(int i=0;i<authorities.size();i++){
      ret+="\tAuthority "+(i+1)+"\n";
      ret+=authorities.get(i).toString();
    }
    for(int i=0;i<additional.size();i++){
      ret+="\tAdditional "+(i+1)+"\n";
      ret+=additional.get(i).toString();
    }
    return ret;
  }


  public ArrayList<DNSResourceRecord> getAnswers(){
    return answers;
  }

  public void addAnswer(DNSResourceRecord answer){
    this.answers.add(answer);
  }

  public ArrayList<DNSResourceRecord> getAuthorities(){
    return authorities;
  }

  public void addAuthority(DNSResourceRecord authority){
    this.authorities.add(authority);
  }

  public ArrayList<DNSResourceRecord> getAdditional(){
    return additional;
  }

  public void addAdditional(DNSResourceRecord additionalSingle){
    this.additional.add(additionalSingle);
  }


  public ArrayList<DNSQuestion> getQuestions(){
    return questions;
  }

  public void addQuestion(DNSQuestion question){
    this.questions.add(question);
  }

	/**
	* Returns value of queryId
	* @return
	*/
	public int getQueryId() {
		return queryId;
	}

	/**
	* Sets new value of queryId
	* @param
	*/
	public void setQueryId(int queryId) {
		this.queryId = queryId;
	}

	/**
	* Returns value of qr
	* @return
	*/
	public boolean isQr() {
		return qr;
	}

	/**
	* Sets new value of qr
	* @param
	*/
	public void setQr(boolean qr) {
		this.qr = qr;
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
	* Returns value of aa
	* @return
	*/
	public boolean isAa() {
		return aa;
	}

	/**
	* Sets new value of aa
	* @param
	*/
	public void setAa(boolean aa) {
		this.aa = aa;
	}

	/**
	* Returns value of tc
	* @return
	*/
	public boolean isTc() {
		return tc;
	}

	/**
	* Sets new value of tc
	* @param
	*/
	public void setTc(boolean tc) {
		this.tc = tc;
	}

	/**
	* Returns value of rc
	* @return
	*/
	public boolean isRd() {
		return rd;
	}

	/**
	* Sets new value of rc
	* @param
	*/
	public void setRd(boolean rd) {
		this.rd = rd;
	}

	/**
	* Returns value of ra
	* @return
	*/
	public boolean isRa() {
		return ra;
	}

	/**
	* Sets new value of ra
	* @param
	*/
	public void setRa(boolean ra) {
		this.ra = ra;
	}

	/**
	* Returns value of rCode
	* @return
	*/
	public int getRCode() {
		return rCode;
	}

	/**
	* Sets new value of rCode
	* @param
	*/
	public void setRCode(int rCode) {
		this.rCode = rCode;
	}

	/**
	* Returns value of cd
	* @return
	*/
	public boolean isCd() {
		return cd;
	}

	/**
	* Sets new value of cd
	* @param
	*/
	public void setCd(boolean cd) {
		this.cd = cd;
	}

	/**
	* Returns value of questionCount
	* @return
	*/
	public int getQuestionCount() {
		return questionCount;
	}

	/**
	* Sets new value of questionCount
	* @param
	*/
	public void setQuestionCount(int questionCount) {
		this.questionCount = questionCount;
	}

	/**
	* Returns value of answerCount
	* @return
	*/
	public int getAnswerCount() {
		return answerCount;
	}

	/**
	* Sets new value of answerCount
	* @param
	*/
	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}

	/**
	* Returns value of authorityCount
	* @return
	*/
	public int getAuthorityCount() {
		return authorityCount;
	}

	/**
	* Sets new value of authorityCount
	* @param
	*/
	public void setAuthorityCount(int authorityCount) {
		this.authorityCount = authorityCount;
	}

	/**
	* Returns value of additionalRecordCount
	* @return
	*/
	public int getAdditionalRecordCount() {
		return additionalRecordCount;
	}

	/**
	* Sets new value of additionalRecordCount
	* @param
	*/
	public void setAdditionalRecordCount(int additionalRecordCount) {
		this.additionalRecordCount = additionalRecordCount;
	}

}
