import java.util.*;
import java.awt.*;

//Programmklassen erben von IOFrame
public class BMI extends IOFrame
{
  //Konstruktor = Konstruktor von IOFrame
  public BMI(String title, int lblWidth, int txtFieldWidth){
    super (title, lblWidth, txtFieldWidth);
    this.addTextBox("Gewicht (in kg)");
    this.addTextBox("Größe (in m)");    
    this.addButton("BMI errechnen");
    this.addOutputBox("BMI");
    this.addOutputBox("Ergebnis");    
    startDisplay();
  }
  
  public static void main(String[] args) 
  {
    new BMI("Body-Mass-Index",100, 130);
  }
  
  public void buttonClick(Button button){
    double index = Double.parseDouble(((TextField) this.ControlList.get(0)).getText())/(Double.parseDouble(((TextField) this.ControlList.get(1)).getText())*Double.parseDouble(((TextField) this.ControlList.get(1)).getText()));
    ((TextField) this.ControlList.get(3)).setText(Double.toString(index));
    if (index<18.5) 
    {
      ((TextField) this.ControlList.get(4)).setText("Untergewicht!");
    }else if (index < 25){
      ((TextField) this.ControlList.get(4)).setText("Normalgewicht!");
    }else if (index < 30){                 
      ((TextField) this.ControlList.get(4)).setText("Übergewicht!");
    }else{
      ((TextField) this.ControlList.get(4)).setText("Adipositas!");
    }    
  }
}