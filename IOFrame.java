import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

/**
  *
  * description
  * Class used to provide a simple code-configurable I/O interface
  * v1.0.0:  Initial Creation
  * v1.0.1:  Bugfixes
  * v1.0.2:  Bugfixes
  * v1.0.3:  Addend german Documentation
  * v1.1.0:  Large Rewrite (keep downwards Compatibility)
  *          Removed biggest part of german Documentation
  *          Added Checkboxes
  *          Added Possibility for multiple Buttons
  * v1.1.1:  Added Possibility for custom Controls
  * v1.1.1b: Added try/catch routine for default buttonClick/rechnen methods
  *          Bugfixes
  * v1.1.1c: Added possibility to use the complete Constructor
  *          Re-added german documentation
  * v1.2.0:  Rewrote the new Stuff to work more efficient and without subclasses
  *          Adding Controls at runtime is now possible
  * v1.2.1:  Removal of Controls at runtime is now possible
  *          Removed code of subclasses
  * v1.2.2:  Fixed a bug when removing Elements with additional helper Objects
  * v1.2.3:  Added the removeAllControls() function written initially for just one Project
  *          Added a simple VersionControl System
  *
  * @version 1.2.3 from 24.01.2014
  * @author D. Schweighoefer
  */

public class IOFrame extends Frame {
  //Versioning
  public static final int var_maj = 1;
  public static final int var_min = 2;
  public static final int var_rev = 3;  
  // Anfang Attribute
  public ArrayList<TextField> Output = new ArrayList<TextField>();                //Ausgabefelder
  public ArrayList<TextField> Input = new ArrayList<TextField>();                 //Eingabeflder
  private int labelWidth = 150;                                                   //Standardwert Beschriftungsbreite
  private int boxWidth = 200;                                                     //Standardwert Feldbreite
  private static final int elementHeight = 25;                                    //Elementhöhe
  private static final int elementSpacing = 5;                                    //Elementabstand
  private static final int border = 10;                                           //Rand
  public ArrayList<Component> ControlList = new ArrayList<Component>();           //Wichtige Steuerelemente
  private ArrayList<Component> ControlListHelper = new ArrayList<Component>();    //Unterstützende Steuerelemente
  private ArrayList<Integer> ControlListHelperLink = new ArrayList<Integer>();
  private int currentY = border;
  private Panel clientPanel; //Clientbereich
  private boolean isCompatibilityMode = false;
  // Ende Attribute
  
  //Löschen von Steuerelementen
  public void removeByID(int id){
    Component cmp = ControlList.get(id);
    clientPanel.remove(cmp);
    Input.remove(cmp);
    Output.remove(cmp);
    ControlList.remove(cmp);
    if (ControlListHelperLink.get(id) > -1) 
    {
      for (int i = id + 1;i < ControlListHelperLink.size();i++ ) 
      {
        if (ControlListHelperLink.get(i) > 0) 
        {
          ControlListHelperLink.set(i,ControlListHelperLink.get(i) - 1);
        }
      }
      Component cmpHelper = ControlListHelper.get(ControlListHelperLink.get(id));
      clientPanel.remove(cmpHelper);
      ControlListHelper.remove(cmpHelper);
    }
    ControlListHelperLink.remove(id);
    updateControls();
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
  
  //Schaltfläche hinzufügen
  public void addButton(String Title){
    //Schaltfläche erstellen und positionieren
    Button btn = new Button();
    btn.setBounds(border, currentY, labelWidth+elementSpacing+boxWidth, elementHeight);
    currentY = currentY + elementHeight + elementSpacing;
    //Beschriften
    btn.setLabel(Title);
    btn.setName(Title);
    //Verwendungsroutine initialisieren
    btn.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        btn_ActionPerformed(evt);
      }
    });
    //Dem Formular hinzufügen
    ControlList.add(btn);
    ControlListHelperLink.add(-1);
    clientPanel.add(btn);
    updateSize();     
  }
  
  //TextBox erstellen (Ausgabefeld)
  public void addOutputBox(String Title){
    addTextBox(Title,"",true);
    updateSize();
  }
  
  //TextBox erstellen (Standard)
  public void addTextBox(String Title){
    addTextBox(Title,"",false);
    updateSize();
  }
  
  //TextBox erstellen (default Wert)
  public void addTextBox(String Title, String defaultValue){
    addTextBox(Title,defaultValue,false);
    updateSize();
  }
  
  //TextBox hinzufügen (Intern)
  private void addTextBox(String Title, String defaultValue, boolean isOutput){
    //Beschriftung erstellen und positionieren
    Label newLabel = new Label();
    newLabel.setText(Title + ":");
    newLabel.setBounds(border,currentY,labelWidth,elementHeight);
    //Textfeld erstellen und positionieren        
    TextField newTextField = new TextField();
    newTextField.setBounds(border+labelWidth+elementSpacing,currentY,boxWidth,elementHeight);
    newTextField.setText(defaultValue);
    currentY = currentY + elementHeight + elementSpacing;
    //Textfeld dem Formular hinzufügen
    ControlList.add(newTextField);
    clientPanel.add(newTextField);
    if (isOutput) 
    {
      Output.add(newTextField);
    }else{
      Input.add(newTextField);
    }
    //Beschriftung dem Formular hinzufügen
    ControlListHelper.add(newLabel);
    ControlListHelperLink.add(ControlListHelper.size() - 1);
    clientPanel.add(newLabel);
    updateSize();
  }
  
  //CheckBox erstellen
  public void addCheckBox(String Title, boolean defaultValue){
    //Checkbox erstellen und positionieren
    Checkbox cBox = new Checkbox();
    cBox.setLabel(Title);
    cBox.setState(defaultValue);
    cBox.setBounds(border, currentY, labelWidth+elementSpacing+boxWidth, elementHeight);
    currentY = currentY + elementHeight + elementSpacing;
    //Dem Formular hinzufügen
    ControlList.add(cBox);
    ControlListHelperLink.add(-1);
    clientPanel.add(cBox);
    updateSize();
  }
  
  //Eigenes Steuerelement hinzufügen
  public void addCustomControl(Component Control, int height){
    //positionieren
    Control.setBounds(border, currentY, labelWidth+elementSpacing+boxWidth, height);
    currentY = currentY + height + elementSpacing;
    //Dem Formular hinzufügen
    ControlList.add(Control);
    ControlListHelperLink.add(-1);
    clientPanel.add(Control);
    updateSize();
  }
  
  //Methode zum Updaten der GUI
  public void updateControls(){
    currentY = border;
    for (int i = 0;i < ControlList.size() ;i++) 
    {
      if (this.ControlListHelperLink.get(i)>-1) 
      {
        ControlList.get(i).setBounds(border + labelWidth+elementSpacing, currentY, ControlList.get(i).getBounds().width, ControlList.get(i).getBounds().height);
        ControlListHelper.get(ControlListHelperLink.get(i)).setBounds(border, currentY, ControlListHelper.get(ControlListHelperLink.get(i)).getBounds().width, ControlListHelper.get(ControlListHelperLink.get(i)).getBounds().height);
      }else{
        ControlList.get(i).setBounds(border, currentY, ControlList.get(i).getBounds().width, ControlList.get(i).getBounds().height);
      }
      currentY = currentY + ControlList.get(i).getBounds().height + elementSpacing;
    }
    updateSize();
  }
  
  //Methode zum anpassen der Fenstergröße
  public void updateSize(){
    int frameWidth = 2*border + labelWidth + boxWidth + elementSpacing + 5;
    int frameHeight = currentY - elementSpacing + border + 30;
    setSize(frameWidth, frameHeight);
  }
  
  //Methode zum füllen des Formulars
  public void initialisation(int lblWidth, int txtFieldWidth){
    //Parameter übergeben
    labelWidth = lblWidth;
    boxWidth = txtFieldWidth;
    //Funktion für freisetzen der Ressourcen bei Programmende einsetzen
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) { dispose(); }
    });
    //Clientbereich erstellen
    clientPanel = new Panel(null);
    add(clientPanel);
    setResizable(false);
    setIcon("IOFrame.png");
  }
  
  public boolean setIcon(String path){
    try{
      URL iconURL = getClass().getResource(path);
      // iconURL is null when not found
      ImageIcon icon = new ImageIcon(iconURL);
      this.setIconImage(icon.getImage());
      return true;
    }catch(Exception e){
      return false;
    }
  }
  
  public boolean requiresVersion(int min_maj, int min_min, int min_rev, int max_maj, int max_min, int max_rev, boolean customHandling){
    boolean isValid;
    if (max_maj == -1) 
    {
      max_maj = var_maj;
      max_min = var_min;
      max_rev = var_rev;
    }
    if ((this.var_maj >= min_maj) && (this.var_min >=min_min) && (this.var_rev >= min_rev) && (this.var_maj <= max_maj) && (this.var_min <= max_min) && (this.var_rev <= max_rev)) 
    {
      isValid = true;
    }else{
      isValid = false;
    }
    if (!isValid) 
    {
      if (!customHandling) 
      {
        this.removeAllControls();
        JLabel lbl = new JLabel("<html>Dieses Programm scheint nicht mit dieses Version von IO Frame kompatibel zu sein:<br>Benötigte Version:<br>" + min_maj + "." + min_min + "." + min_rev + " - " + max_maj + "." + max_min + "." + max_rev + "<br>Aktuelle Version:<br>" + var_maj + "." + var_min + "." + var_rev + "</html>");
        this.addCustomControl(lbl,120);
        this.addButton("Ende");
      }
    }
    
    return isValid;
  }
  
  public void centerOnScreen(){
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
  }
  
  public void startDisplay(){
    updateSize();
    centerOnScreen();
    setVisible(true);
  }
  
  //neuer Gesamtkonstruktor 
  public IOFrame(String title, int lblWidth, int txtFieldWidth){
    //Titel übergeben
    super (title);
    //Parameter an Funktion übergeben
    initialisation(lblWidth, txtFieldWidth);
  }
  
  //neuer Standardkonstruktor (bitte beim überschreiben constructor() aufrufen)
  public IOFrame(String title){
    //Titel übergeben
    super(title);
  }
  
  //alter Konstruktor, für Kompatibilität
  public IOFrame(String title, ArrayList<String> Inputs, ArrayList<String> Outputs,int lblWidth, int txtFieldWidth)
  {
    //Titel übergeben
    super(title);
    //Auf Kompatibilitätsmodus setzen
    this.isCompatibilityMode = true;
    System.out.println("Kompatibilitätsmodus aktiviert: Dieses Programm kann möglicherweise nicht mit neueren Versionen dieser Klasse funktionieren");
    //Konstruktion auslösen
    initialisation(lblWidth,txtFieldWidth);
    //Für jedes Eingabefeld ein Textfeld erzeugen
    for (int i = 0;i<Inputs.size();i++) 
    {
      addTextBox(Inputs.get(i));
    }
    //"Rechnen!" Knopf erstellen
    addButton("Rechnen!");
    //Für jedes Ausgabefeld ein Ausgabetextfeld erzeugen
    for (int i = 0;i<Outputs.size();i++) 
    {
      addOutputBox(Outputs.get(i));
    }
    //"Ende" Knopf hinzufügen
    addButton("Ende");
    //Anzeigen
    startDisplay();
  }
  
  // Anfang Methoden
  //Wenn "Rechnen!" angeklickt wurde:
  public void btn_ActionPerformed(ActionEvent evt) {
    //Schaltflächentitel herausfiltern
    Button btn = (Button) evt.getSource();
    //Wenn "Rechnen!" alte 'rechnen' Operation vorbereiten und ausführen
    if (btn.getLabel().equals("Rechnen!") && this.isCompatibilityMode){ 
      //Ausgabefelder leeren
      for (int i=0;i<Output.size();i++) 
      {
        Output.get(i).setText("");
      }
      //Eingaben herausfinden
      ArrayList<String> calcInput = new ArrayList<String>();
      for (int i=0;i<Input.size();i++) 
      {
        calcInput.add((Input.get(i).getText()));
      }
      //rechnen ausführen
      rechnen(calcInput);
    }else{
      //Wenn "Ende"
      if (btn.getLabel().equals("Ende")) 
      {
        //Programm beenden
        System.exit(0);
      }else{
        //Ansonsten: buttonClick(String) aufrufen
        buttonClick(btn);
      }
    }
  }
  
  //Neue Funktion zum auswerten von Schaltflächen
  public void buttonClick(Button button){
    //Versuchen (da diese Funktion nicht überschrieben werden muss)
    try{
      //Eingabefeld und Ausgabefeld herausfiltern
      TextField Input1 = (TextField) ControlList.get(0);
      TextField Output1 = (TextField) ControlList.get(2);
      //Inhalt vom Eingabefeld in Ausgabefeld setzen
      Output1.setText(Input1.getText());
    }catch(Exception e){
      //Bei fehlenden oder falschen Feldern
      System.out.println("Please override 'public void buttonClick(String button)'");
      System.out.println("And don't name your Button 'Rechnen!'");
    }
  }
  
  /**
  *  Alte Funktion zum auswerten von Schaltflächen
  *  @deprecated: Diese Funktion soll nur für Abwärtskompatibilität verwendet werden. Bitte 'buttonClick(Button button)' verwenden.
  */
  public void rechnen(ArrayList<String> calcInput){
    //Versuchen (da diese Funktion nicht überschrieben werden muss)
    try{
      //Ausgabe = Eingabe
      Output.get(0).setText(calcInput.get(0));
    }catch(Exception e){
      //Bei fehlenden oder falschen Feldern
      System.out.println("Please override 'public void rechnen(ArrayList<String> calcInput)'");
    }
  }
  
  /**
  * Standard Startprozedur (Alte Funktionsweise) 
  */
  public static void main(String[] args) 
  {
    //Ein Eingabe- und ein Ausgabefeld erstellen
    ArrayList<String> constInputs = new ArrayList<String>();
    constInputs.add("Input 1");
    ArrayList<String> constOutputs = new ArrayList<String>();
    constOutputs.add("Output 1");
    
    //Konstruktor aufrufen
    new IOFrame("Vorlagenklasse IOFrame",constInputs,constOutputs, 100, 150);
  }
}