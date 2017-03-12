public class Test{
  public static void main(String[] args){
    for(int i=0;i<256;i++){
      byte f = (byte)i;
      if(f>=(byte)192&&f<0){//This means that the first 2 bits are 1s
        for(int j=0;j<8;j++){
          System.out.print(""+(((int)f>>(7-j))&0x1));
        }
        System.out.println();
      }
    }
  }
}
