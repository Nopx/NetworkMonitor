public class ErrorHandler{

  private static final boolean DEBUG = false;
  private static final boolean SILENCE=true;

  public static void handleError(int code,String msg, Exception e){
    if(SILENCE)return;
    System.out.println(""+code+": "+msg);
    if(DEBUG && e!=null)e.printStackTrace();
  }

}
