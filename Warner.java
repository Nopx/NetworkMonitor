public class Warner{

  public static void warn(String message, int errorCode){
    switch(errorCode){
      case PacketInspector.ERROR:
        System.out.println("********************************");
        System.out.println("*                              *");
        System.out.println("*          ERROR START         *");
        System.out.println("*                              *");
        System.out.println("********************************");
        System.out.println(message);
        System.out.println("********************************");
        System.out.println("*                              *");
        System.out.println("*           ERROR END          *");
        System.out.println("*                              *");
        System.out.println("********************************");
        break;
      case PacketInspector.NOTICE:
        System.out.println("********************************");
        System.out.println("*                              *");
        System.out.println("*         WARNING START        *");
        System.out.println("*                              *");
        System.out.println("********************************");
        System.out.println(message);
        System.out.println("********************************");
        System.out.println("*                              *");
        System.out.println("*          WARNING END         *");
        System.out.println("*                              *");
        System.out.println("********************************");
        break;
    }
  }

}
