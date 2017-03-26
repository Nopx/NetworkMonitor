import net.openhft.hashing.LongHashFunction;
import java.util.Random;
import java.util.ArrayList;

public class BloomFilter extends PacketInspector{

  private static final String CONFIG_FILE = "config.cfg";
  public int[] filter;
  private LongHashFunction[] hashes;
  private int size;
  private int hashAmount;

  public BloomFilter(int size, int hashAmount){
    this.size=size;
    this.hashAmount = hashAmount;
    Random rand = new Random();
    //adapting size so that every 1 is a bit in the array, for performance reasons
    filter = new int[size/32+1];
    hashes = new LongHashFunction[hashAmount];
    for(int i=0;i<hashes.length;i++) hashes[i] = LongHashFunction.xx((long)rand.nextInt());
    ArrayList<String> ipsToFilter = loadConfigFile(CONFIG_FILE);
    for(int i=0;i<ipsToFilter.size();i++)
      addElement(ipsToFilter.get(i));
  }

  /**
  * Adds an element to the bloom filter
  */
  public void addElement(String element){
    long[] elementHashes = getHashes(element);
    for(int i=0;i<hashAmount;i++)
      addSingleHashedValue(elementHashes[i]);
  }

  /**
  * Checks if value is allowed according to the filter rule
  * An ERROR is returned if not
  */
  public int testValue(String value){
    long[] elementHashes = getHashes(value);
    for(int i=0;i<hashAmount;i++)
      if(containsValue(elementHashes[i])) return ERROR;
    return PERMITTED;
  }

  /**
  * Adds a value to the filter rule in the form of bit number x in the int array filter
  */
  private void addSingleHashedValue(long value){
    //divide by length of Integer
    int adaptedValue=(int)(value/32);
    //Adding the valueth bit as a 1
    int valueToAdd = 1<<value-(adaptedValue*32);
    filter[adaptedValue]|=valueToAdd;
  }

  /**
  * Checks whether the valueth bit in the filter array is 1
  */
  private boolean containsValue(long value){
    //divide by length of Integer
    int adaptedValue=(int)(value/32);
    //Adding the valueth bit as a 1
    int valueToTest = 1<<value-(adaptedValue*32);
    return valueToTest== (filter[adaptedValue]&valueToTest);
  }

  private long[] getHashes(String element){
    byte[] elementBytes = element.getBytes();
    int modVal = size/2;
    long[] ret = new long[hashAmount];
    for(int i=0;i<hashAmount;i++){
      ret[i] = hashes[i].hashBytes(elementBytes)%modVal;
      if(ret[i]<0) ret[i]*=-2;
    }
    return ret;
  }

  public static double testTime(int size, int hashAmount, int iterations){
    BloomFilter bloom = new BloomFilter(size,hashAmount);
    Random rand = new Random();
    long time=System.currentTimeMillis();
    for(int i=0;i<iterations;i++){
      bloom.testValue(""+rand.nextInt());
    }
    return ((double)(System.currentTimeMillis()-time))/iterations;
  }

  public static void main(String[] args){
    System.out.println(""+BloomFilter.testTime(175000000,30,5000));
  }

}
