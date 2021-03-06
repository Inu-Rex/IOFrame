import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;


public class PipeClient {
  RandomAccessFile pipe;
  
  public PipeClient(String name){
    try{
      pipe = new RandomAccessFile("\\\\.\\pipe\\DSCOM", "rw");
      String verification = pipe.readLine();
      if (verification.equals("I am the one true server!")){
        pipe.write ((name + "\n").getBytes());
      }else{
        pipe.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public String sendMessage(String send){
    try{
      pipe.write((send + "\n").getBytes());
      return pipe.readLine();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
  public void disconnect(){
    try{
      sendMessage("disconnect");
      pipe.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
