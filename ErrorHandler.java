public class ErrorHandler{

  private static final boolean DEBUG = true;

  public static void handleError(int code,String msg, Exception e){
    System.out.println(""+code+": "+msg);
    if(DEBUG && e!=null)e.printStackTrace();
  }

}
