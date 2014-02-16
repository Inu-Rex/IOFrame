import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class Teams extends IOFrame{
  public String URL = "";
  public boolean isLocal = false;
  public ArrayList<String> players = new ArrayList<String>();
  public ArrayList<String> playerscache = new ArrayList<String>();
  public ArrayList<String> team1 = new ArrayList<String>();
  public ArrayList<String> team2 = new ArrayList<String>();
  
  public java.awt.List team1List;
  public java.awt.List team2List;
  public java.awt.List PlayerList;
  public Panel frm;
  
  public Teams(String title, int lblWidth, int txtFieldWidth){
    super (title, lblWidth, txtFieldWidth);
    startInitMenu();
    startDisplay();
  }
  
  public void startInitMenu(){
    this.removeAllControls();
    addTextBox("URL", "https://raw.github.com/Inu-Rex/OnlineConfigs/master/Shuffle_players.txt");
    this.addCheckBox("lokale Datei", false);
    addButton("Programm Starten");
    addButton("Ende");
  }
  
  public void getLists(){
    PlayerList = (java.awt.List) this.ControlList.get(1);    
    frm = (Panel) this.ControlList.get(6);
    team1List = (java.awt.List) frm.getComponent(2);
    team2List = (java.awt.List) frm.getComponent(3);    
  }  
  
  
  public void startMainFrame(){
    this.removeAllControls();
    JLabel lbl = new JLabel("Verfügbare Spieler:");
    this.addCustomControl(lbl,20);
    PlayerList = new java.awt.List();
    PlayerList.setMultipleMode(true);
    this.addCustomControl(PlayerList,100);
    loadFromFile();
    addButton("Entfernen");
    addTextBox("Name");
    addButton("Hinzufügen");
    addButton("Neu laden");
    JLabel lbl2 = new JLabel("Alpha:");
    //this.addCustomControl(lbl2,20);
    team1List = new java.awt.List();
    //this.addCustomControl(team1List,100);
    JLabel lbl3 = new JLabel("Beta:");
    //this.addCustomControl(lbl3,20);
    team2List = new java.awt.List();
    //this.addCustomControl(team2List,100);
    frm = new Panel();
    frm.setLayout(null); 
    lbl2.setBounds(0,0,250,25);
    lbl3.setBounds(250 + 5,0,250,25);
    team1List.setBounds(0, 25 + 5, 250, 120);
    team1List.setBackground(new Color(100,0,0));
    team1List.setForeground(Color.WHITE);
    team2List.setBounds(250 + 5, 25 + 5, 250, 120);
    team2List.setBackground(new Color(0,100,0));
    team2List.setForeground(Color.WHITE);
    frm.add(lbl2);
    frm.add(lbl3);
    frm.add(team1List);
    frm.add(team2List);
    this.addCustomControl(frm,120 + 5 + 25);
    addCheckBox("Nur ausgewählte Spieler beachten",false);
    addButton("Teams mischen");
    addButton("Teams zurücksetzen");
    addButton("Hauptmenü");
    addButton("Ende");
    getLists();
  }
  
  public static void main(String[] args) 
  {
    new Teams("Team Generator",100,400);
  }
  
  public void buttonClick(Button button){
    if (button.getLabel().equalsIgnoreCase("Programm Starten")){
      TextField txt = (TextField) this.ControlList.get(0);
      Checkbox chk = (Checkbox) this.ControlList.get(1);
      URL = txt.getText();
      isLocal = chk.getState();
      startMainFrame();
    }else{
      if (button.getLabel().equalsIgnoreCase("Teams mischen")) 
      {
        Checkbox cb = (Checkbox) this.ControlList.get(7);
        team1.clear();
        team2.clear();
        playerscache.clear();
        for (int k = 0;k<players.size();k++) 
        {
          if (cb.getState()) 
          {
            if (PlayerList.isIndexSelected(k)) 
            {
              playerscache.add(PlayerList.getItem(k));
            }
          }else{
            playerscache.add(PlayerList.getItem(k));
          }
        }
        
        int cnt = playerscache.size();
        for (int i=0;i<((int) cnt/2);i++) 
        {
          int get = myRandom(0,playerscache.size());
          String name = playerscache.get(get);
          playerscache.remove(get);
          team1.add(name);
        }
        int cnt2 = playerscache.size();
        if ((cnt % 2) == 1) 
        {
          int get = myRandom(0,playerscache.size());
          String name = playerscache.get(get);
          playerscache.remove(get);
          
          int get2 = myRandom(0,2);
          if (get2 == 1) 
          {
            team2.add(name);
          }else{
            team1.add(name);
          }
          cnt2 = cnt2 - 1;
        }
        for (int i = 0;i < cnt2;i++) 
        {
          team2.add(playerscache.get(0));
          playerscache.remove(0);
        }
        updateTeamLists();
      }else{
        if (button.getLabel().equalsIgnoreCase("Neu laden")) 
        {
          loadFromFile();
        }else{
          if (button.getLabel().equalsIgnoreCase("Teams zurücksetzen")) 
          {
            team1.clear();
            team2.clear();
            updateTeamLists();
          }else{
            if (button.getLabel().equalsIgnoreCase("Entfernen")) 
            {
              int[] index = PlayerList.getSelectedIndexes();
              if (index.length > 0) 
              {
                for (int k = 0;k < index.length;k++) 
                {
                  PlayerList.remove(index[k] - k);
                }
                backSync();
              }
            }else{
              if (button.getLabel().equalsIgnoreCase("Hinzufügen")) 
              {
                TextField txt = (TextField) this.ControlList.get(3);
                if (!txt.getText().equalsIgnoreCase("")) 
                {
                  PlayerList.add(txt.getText());
                  txt.setText("");
                  backSync();
                }
              }else{
                if (button.getLabel().equalsIgnoreCase("Hauptmenü")){
                  startInitMenu();
                }
              }
            }
          }
        }
      }
    } 
  }
  
  public static int myRandom(int low, int high) {
    return (int) (Math.random() * (high - low) + low);
  }
  
  public void loadFromFile(){
    try{
      BufferedReader br;
      if (isLocal) 
      {
        FileReader fr = new FileReader(URL);
        br = new BufferedReader(fr);
      }else{
        URL url = new URL(URL);
        InputStream is = url.openStream();
        br = new BufferedReader(new InputStreamReader(is));
      }
      java.awt.List lst = (java.awt.List) ControlList.get(1);
      players.clear();
      String zeile = "";
      while( (zeile = br.readLine()) != null )
      {
        if (!zeile.equalsIgnoreCase("")) 
        {
          players.add(zeile);
        }
      }
      updateRepoList();
    }catch(Exception e){
      
    }
  }
  
  public void updateRepoList(){
    PlayerList.removeAll();
    for (int k = 0;k < players.size();k++) 
    {
      PlayerList.add(players.get(k));
    }
  }
  
  public void updateTeamLists(){
    team1List.removeAll();
    for (int k = 0;k < team1.size();k++) 
    {
      team1List.add(team1.get(k));
    }
    team2List.removeAll();
    for (int k = 0;k < team2.size();k++) 
    {
      team2List.add(team2.get(k));
    }
  }
  
  public void backSync(){
    players.clear();
    for (int k = 0;k<PlayerList.getItemCount();k++) 
    {
      players.add(PlayerList.getItem(k));
    }
  }
}