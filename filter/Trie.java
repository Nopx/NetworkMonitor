import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Trie extends PacketInspector{

  public static void main(String args[]){
    Trie trie;
    if(args.length==0) trie = new Trie(false);
    else trie = new Trie(args[0].equals("1"));
    Runtime runtime = Runtime.getRuntime();
    long totalAllocatedMemory =0;
    long totalTime=0;
    System.out.println("GO");
    java.util.Random rand = new java.util.Random();
    for(int i=0;i<5000;i++){
      long time = System.currentTimeMillis();
      trie.checkAction(""+(rand.nextInt()));
      totalAllocatedMemory+=runtime.totalMemory();
      totalTime+=System.currentTimeMillis()-time;
    }
    System.out.println(" time "+((double)totalTime/5000));
    System.out.println(" mem  "+((double)totalAllocatedMemory/5000));
  }

  private static final String CONFIG_FILE = "config.cfg";
  private TrieNode root;
  private boolean multiBit;

  public Trie(boolean multiBit){
    this.multiBit = multiBit;
    int bitAmount = multiBit?4:2;
    int chunksize = multiBit?2:1;
    root = new TrieNode(bitAmount);
    TrieNode currentNode;
    try (BufferedReader br = new BufferedReader(new FileReader(CONFIG_FILE))){
      //iterate through all ips
      String line;
			while ((line = br.readLine()) != null){
        currentNode = root;
        byte[] disectedIP = disectIP(line);
        //iterate through all 4 numbers of IP
        for(int j =0;j<disectedIP.length;j++){
          int[] bits = byteToBits(disectedIP[j],chunksize);
          //iterate through all bits of the number
          for(int k=0;k<bits.length;k++){
            TrieNode[] nextNodes = currentNode.getNext();
            //check if the next node already exists
            if(nextNodes[bits[k]]==null){
              nextNodes[bits[k]]= new TrieNode(bitAmount);
              currentNode.setNext(nextNodes);
            }
            //set new node to continue from
            currentNode = currentNode.getNext()[bits[k]];
          }
        }
        currentNode.setAction(DROP);
      }
    }
    catch(IOException ioe){
      handleError(1,"IO Error.",(Exception)ioe);
    }
    //optimize Trie
    if(multiBit){
      //root.multiStrideOptimize();
    }
  }

  public int checkAction(String ip){
    int comparisonInt = getComparisonInt(ip);
    int chunksize = multiBit?2:1;
    TrieNode currentNode = root;
    byte[] disectedIP = disectIP(ip);
    //iterate through all 4 numbers of IP
    int extraK =0;
    for(int j =0;j<disectedIP.length;j++){
      int[] bits = byteToBits(disectedIP[j],chunksize);
      //iterate through all bits of the number
      int k=extraK>0?extraK:0;
      while(k<bits.length){
        int increment = currentNode.getNextNodeIncrement()[bits[k]];
        TrieNode[] nextNodes = currentNode.getNext();
        //check if the next node already exists
        if(nextNodes[bits[k]]==null) return PERMITTED;
        //set new node to continue from
        int shiftAmount = (((24-(chunksize-1))-j*8)+(7-k*chunksize))-((increment-1)*chunksize);
        int compareVal = (currentNode.getNextCompare()[bits[k]]);
        int compareValShifted = compareVal<<shiftAmount;
        int outcome = (compareValShifted&comparisonInt);
        if(outcome==compareValShifted)
          currentNode = currentNode.getNext()[bits[k]];
        else
          return PERMITTED;
        if(currentNode.getAction()!= -1) return currentNode.getAction();
        k+=increment;
      }
      extraK = k-bits.length;
    }
    return PERMITTED;
  }

  private int getComparisonInt(String ip){
    int ret =0;
    int chunksize = multiBit?2:1;
    byte[] disectedIP = disectIP(ip);
    //iterate through all 4 numbers of IP
    int extraK =0;
    for(int j =0;j<disectedIP.length;j++){
      int[] bits = byteToBits(disectedIP[j],chunksize);
      for(int m=0;m<bits.length;m++){
        ret |= bits[m]<<(((8-chunksize)-(m*chunksize))+(3-j)*8);
      }
    }
    return ret;
  }

  private byte[] disectIP(String ip){
    String[] numbers = ip.split("\\.");
    byte[] ret = new byte[numbers.length];
    for(int i=0;i<ret.length;i++){
      try{
        ret[i] = (byte)(Integer.parseInt(numbers[i]));
      }
      catch(java.lang.NumberFormatException nfe){
        handleError(0,"Bad configuration file for Trie filter.",(Exception)nfe);
      }
    }
    return ret;
  }

  /**
  * converts a byte into a array of integers which represent the bits of that byte
  * each integer contains chunksize bits of inByte
  */
  protected int[] byteToBits(byte inByte,int chunksize){
    int[] retBits = new int[8/chunksize];
    int inByteInt = (int)inByte;
    for(int i=0;i<retBits.length;i++){
      for(int j=0;j<chunksize;j++){
        int extraShift = chunksize-1;
        int verOder = (inByteInt >>((7-extraShift)-i*chunksize))&(0x01<<(extraShift-j));
        retBits[i] |= verOder;
      }
    }
    return retBits;
  }

}
