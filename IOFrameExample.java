import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class IOFrameExample extends IOFrame{
  public IOFrameExample(String title, int lblWidth, int txtFieldWidth){
    super (title, lblWidth, txtFieldWidth);
    addButton("Add");
    startDisplay();
  }
  
  public static void main(String[] args) 
  {
    new IOFrameExample("Hallo Welt!",100,200);
  }
  
  public void buttonClick(Button button){
    if (button.getLabel().equals("Add")) 
    {
      if (this.ControlList.size() > 1) 
      {
        Button btn = (Button) this.ControlList.get(this.ControlList.size() - 1);
        int id = Integer.parseInt(btn.getLabel());
        addButton(Integer.toString(id +1));
      }else{
        addButton("1");
      }
    }else{
      this.removeByID(this.ControlList.indexOf(button)); 
    }
  }
}
