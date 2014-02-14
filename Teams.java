import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.*;

public class Teams extends IOFrame{
  public Teams(String title, int lblWidth, int txtFieldWidth){
    super (title, lblWidth, txtFieldWidth);
    JLabel lbl = new JLabel("Verfügbare Spieler:");
    this.addCustomControl(lbl,20);
    java.awt.List lst = new java.awt.List();
    this.addCustomControl(lst,100);
    loadFromFile();
    addButton("Neu laden");
    JLabel lbl2 = new JLabel("Team 1:");
    this.addCustomControl(lbl2,20);
    java.awt.List lst2 = new java.awt.List();
    this.addCustomControl(lst2,100);
    JLabel lbl3 = new JLabel("Team 2:");
    this.addCustomControl(lbl3,20);
    java.awt.List lst3 = new java.awt.List();
    this.addCustomControl(lst3,100);
    addButton("Teams mischen");
    addButton("Ende");
    startDisplay();
  }
  
  public static void main(String[] args) 
  {
    new Teams("Team Generator",200,200);
  }
  
  public void buttonClick(Button button){
    if (button.getLabel().equalsIgnoreCase("Teams mischen")) 
    {
      java.awt.List lst = (java.awt.List) ControlList.get(1);
      java.awt.List team1 = (java.awt.List) ControlList.get(4);
      java.awt.List team2 = (java.awt.List) ControlList.get(6);
      team1.removeAll();
      team2.removeAll();
      int cnt = lst.getItemCount();
      for (int i=0;i<((int) cnt/2);i++) 
      {
        int get = myRandom(0,lst.getItemCount());
        String name = lst.getItem(get);
        lst.remove(get);
        team1.add(name);
      }
      int cnt2 = lst.getItemCount();
      for (int i = 0;i < cnt2;i++) 
      {
        team2.add(lst.getItem(0));
        lst.remove(0);
      }
      loadFromFile();
    }else{
      if (button.getLabel().equalsIgnoreCase("Neu laden")) 
      {
        loadFromFile();
      }
    }
  }
  
  public static int myRandom(int low, int high) {
  		return (int) (Math.random() * (high - low) + low);
 	}
  
  public void loadFromFile(){
    try{
      FileReader fr = new FileReader("player.txt");
      BufferedReader br = new BufferedReader(fr);
      java.awt.List lst = (java.awt.List) ControlList.get(1);
      lst.removeAll();
      String zeile = "";
      while( (zeile = br.readLine()) != null )
      {
        lst.add(zeile);
      }
    }catch(Exception e){
      
    }
  }
}