import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class IOFrameCom extends IOFrame{
  PipeClient pClient;
  
  public IOFrameCom(String title, int lblWidth, int txtFieldWidth){
    super (title, lblWidth, txtFieldWidth);
    this.addTextBox("Eingabe");
    this.addButton("Senden");
    this.addTextBox("Ausgabe");
    this.addButton("Verbindung trennen");
    startDisplay();
    pClient = new PipeClient("IOFramejpipeClient");
  }
  
  public static void main(String[] args) 
  {
    new IOFrameCom("Kommunikation",100,200);
  }
  
  public void buttonClick(Button button){
    TextField a = (TextField) this.ControlList.get(0);
    TextField b = (TextField) this.ControlList.get(2);
    if (button.getLabel().equals("Senden")){
      b.setText(pClient.sendMessage(a.getText()));
    }else{
      pClient.disconnect();
      System.exit(0);
    }
  }
}
