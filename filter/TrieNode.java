public class TrieNode{
  private TrieNode[] next;
  private int[] nextCompare;
  //defines how many strides are done through the bits of a value
  private int[] nextNodeIncrement;
  private int action=-1;
  private int bitDim;

  public TrieNode(int nextLength){
    next = new TrieNode[nextLength];
    this.nextCompare = new int[next.length];
    this.nextNodeIncrement = new int[next.length];
    for(int i=0;i<nextCompare.length;i++) nextCompare[i]=i;
    for(int i=0;i<nextNodeIncrement.length;i++) nextNodeIncrement[i]=1;
    bitDim = next.length/2;
  }

  public String toStringBla(){
    String ret =""+this.toString()+"\n";
    for(int i=0;i<next.length;i++){
      if(next[i]!=null)
        ret+="\tkid"+i+" "+next[i].toString()+" "+nextCompare[i]+"\n";
    }
    for(int i=0;i<next.length;i++){
      if(next[i]!=null)
        ret+=next[i].toStringBla();
    }
    return ret;
  }

	/**
	* Default TrieNode constructor
	*/
	public TrieNode(TrieNode[] next, int action) {
		this.next = next;
    this.nextCompare = new int[next.length];
    this.nextNodeIncrement = new int[next.length];
    for(int i=0;i<nextCompare.length;i++) nextCompare[i]=i;
    for(int i=0;i<nextNodeIncrement.length;i++) nextNodeIncrement[i]=1;
		this.action = action;
    bitDim = next.length/2;
	}

  public boolean hasKid(){
    for(int i=0;i<next.length;i++){
      if(next[i]!=null)return true;
    }
    return false;
  }

  /**
  * Optimizing the structure of the trie recursively
  * Everybody invokes the method for their kids, the kids return themselves
  * If you only have 1 kid, you return that kid instead of yourself and increment his increment value
  */
  public int multiStrideOptimize(){
    int ownCounter=0;
    int kidCounter=0;
    int kidValue;
    int kidIndex=-1;
    //returning -1 means you have more or less than one kid
    for(int i=0;i<next.length;i++){
      if(next[i]!=null){
        kidValue = next[i].multiStrideOptimize();
        if(kidValue!=-1){
          int shiftValue=(int)Math.floor(Math.log(kidValue)/Math.log(2))+1;
          int kidKidIndex=-1;
          for(int j=0;j<next[i].getNext().length;j++){
            if(next[i].getNext()[j]!=null){
              kidKidIndex=j;
              break;
            }
          }
          shiftValue = next[i].getNextNodeIncrement()[kidKidIndex]*bitDim;
          nextCompare[i] = nextCompare[i]<<shiftValue;
          nextCompare[i] |=kidValue;
          next[i]=next[i].getNext()[kidKidIndex];
          int[] kidIncrements = next[i].getNextNodeIncrement();
          kidIncrements[kidKidIndex]+=nextNodeIncrement[i];
          //nextNodeIncrement[i]+=next[i].getNextNodeIncrement()[kidKidIndex];
          next[i].setNextNodeIncrement(kidIncrements);
        }
        kidIndex=i;
        ownCounter++;
      }
    }
    if(ownCounter==1){
      return nextCompare[kidIndex];
    }
    else{
      return -1;
    }
  }

  public void addToCompareVal(int addVal){
    //TODO CHANGE
    for(int i=0;i<nextCompare.length;i++)
      nextCompare[i] |= (addVal<<2);
  }

  /**
	* Returns value of nextNodeIncrement
	* @return
	*/
	public int[] getNextNodeIncrement() {
		return nextNodeIncrement;
	}

	/**
	* Sets new value of next
	* @param
	*/
	public void setNextNodeIncrement(int[] nextNodeIncrement) {
		this.nextNodeIncrement = nextNodeIncrement;
	}

  /**
	* Returns value of nextCompare
	* @return
	*/
	public int[] getNextCompare() {
		return nextCompare;
	}

	/**
	* Sets new value of nextCompare
	* @param
	*/
	public void setNextCompare(int[] nextCompare) {
		this.nextCompare = nextCompare;
	}

	/**
	* Returns value of next
	* @return
	*/
	public TrieNode[] getNext() {
		return next;
	}

	/**
	* Sets new value of next
	* @param
	*/
	public void setNext(TrieNode[] next) {
		this.next = next;
	}

	/**
	* Returns value of action
	* @return
	*/
	public int getAction() {
		return action;
	}

	/**
	* Sets new value of action
	* @param
	*/
	public void setAction(int action) {
		this.action = action;
	}
}
