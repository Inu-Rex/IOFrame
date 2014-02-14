import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Fibonacci extends IOFrame{
  public Fibonacci(String title, int lblWidth, int txtFieldWidth){
    super (title, lblWidth, txtFieldWidth);
    addTextBox("Menge");
    addButton("Zeige Fibonacci Zahlen");
    java.awt.List lst = new java.awt.List();
    this.addCustomControl(lst,100);
    startDisplay();
  }
  
  public static void main(String[] args) 
  {
    new Fibonacci("Fibonacci!",70,100);
  }
  
  public void buttonClick(Button button){
    java.awt.List lst = (java.awt.List) ControlList.get(2);
    lst.removeAll();
    lst.add("0");
    lst.add("1");
    TextField Input1 = (TextField) ControlList.get(0);
    if (Integer.parseInt(Input1.getText())>2) 
    {
      for (int i = 2;i < Integer.parseInt(Input1.getText());i++) 
      {
        lst.add(Integer.toString(Integer.parseInt(lst.getItem(i-2)) + Integer.parseInt(lst.getItem(i-1))));
      }
    }
  }
}
