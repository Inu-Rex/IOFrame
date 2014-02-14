import java.util.*;
import javax.swing.*;
import java.awt.*;

//Programmklassen erben von IOFrame
public class Suche extends IOFrame
{
  public java.awt.List lst;
  
  //Konstruktor = Konstruktor von IOFrame
  public Suche(String title, int lblWidth, int txtFieldWidth){
    super (title, lblWidth, txtFieldWidth);
    startMenu();  
    startDisplay();
  }
  
  public static void main(String[] args) 
  {
    new Suche("Suche",100, 130);
  }
  
  public void buttonClick(Button button){
    if (button.getLabel().equalsIgnoreCase("Information")) 
    {
      removeAllControls();
      JLabel lbl = new JLabel("<html>Infotext:<br>Dieses Programm durchsucht alle gegebenen Datensätze und gibt einen Wert zurück ob der ausgewählte Datensatz enthalten ist.</html>");
      this.addCustomControl(lbl,80);
      this.addButton("Hauptmenü");
    }else{
      if (button.getLabel().equalsIgnoreCase("Datensuche")) 
      {
        removeAllControls();
        this.addTextBox("Suchwort");
        JLabel lbl = new JLabel("<html>Durchsuchbare Einträge:</html>");
        this.addCustomControl(lbl,20);
        lst = new java.awt.List();
        initList(20);
        this.addCustomControl(lst,100);
        this.addButton("Neue Einträge erzeugen");
        this.addButton("Datensatz suchen");
        this.addTextBox("Ergebnis");
        this.addButton("Hauptmenü");
      }else{
        if (button.getLabel().equalsIgnoreCase("Maximumsuche")) 
        {
          removeAllControls();
          JLabel lbl = new JLabel("<html>Durchsuchbare Einträge:</html>");
          this.addCustomControl(lbl,20);
          lst = new java.awt.List();
          initMax(100,200,20);
          this.addCustomControl(lst,100);
          this.addButton("Neue Werte erzeugen");
          this.addButton("Maximum suchen");
          this.addTextBox("Ergebnis");
          this.addButton("Hauptmenü");
        }else{
          if (button.getLabel().equalsIgnoreCase("Hauptmenü")) 
          {
            startMenu();
          }else{
            if (button.getLabel().equalsIgnoreCase("Datensatz suchen")) 
            {
              TextField searchField = ((TextField) this.ControlList.get(0));
              String search = searchField.getText();
              boolean output = false;
              for (int i = 0;i<lst.getItemCount();i++) 
              {
                if (search.equals(lst.getItem(i))) 
                {
                  output = true;
                }
              }
              TextField OutputField = (TextField) this.ControlList.get(5);
              if (output) 
              {
                OutputField.setText("GEFUNDEN");
              }else{
                OutputField.setText("NICHT GEFUNDEN");
              }
            }else{
              if (button.getLabel().equalsIgnoreCase("Maximum suchen")) 
              {
                int output = 0;
                for (int i = 0;i<lst.getItemCount();i++) 
                {
                  if (Integer.parseInt(lst.getItem(i)) > output) 
                  {
                    output = Integer.parseInt(lst.getItem(i));
                  }
                }
                TextField OutputField = (TextField) this.ControlList.get(4);
                OutputField.setText(Integer.toString(output));
              }else{
                if (button.getLabel().equalsIgnoreCase("Ende")) 
                {
                  System.exit(0);
                }else{
                  if (button.getLabel().equalsIgnoreCase("Neue Einträge erzeugen")) 
                  {
                    initList(20);
                  }else{
                    if (button.getLabel().equalsIgnoreCase("Neue Werte erzeugen")) 
                    {
                      initMax(100,200,20);
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  
  public void initList(int count){
    lst.removeAll();
    Random generator = new Random();
    for (int a=0;a<count;a++) 
    {
      lst.add(Integer.toString(generator.nextInt()));
    }
  }
  
  public void initMax(int start, int end, int count){
    lst.removeAll();
    for (int a=0;a<count;a++) 
    {
      lst.add(Integer.toString(start + (int)(Math.random()*(end - start))));
    }
  }
  
  public void startMenu(){
    removeAllControls();    
    this.addButton("Information");
    this.addButton("Datensuche");
    this.addButton("Maximumsuche");
    this.addButton("Ende");
    JLabel lbl = new JLabel("<html>&copy; 2014, Daniel Schweighöfer</html>");
    this.addCustomControl(lbl,20);
  }
  
  public void removeAllControls(){
    try{
      int cnt = ControlList.size();
      for (int i=0;i<cnt;i++) 
      {
        this.removeByID(cnt -1 - i);
      }
    }catch(Exception e){
      
    }
  }
}