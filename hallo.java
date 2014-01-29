import java.awt.*;
import java.awt.event.*;

/**
  *
  * description
  *
  * @version 1.0 from 24.01.2014
  * @author D. Schweighoefer
  */

public class hallo extends Frame 
{
  // Anfang Attribute
  private TextArea textArea1 = new TextArea("", 1, 1, TextArea.SCROLLBARS_BOTH);
  // Ende Attribute

  public hallo(String title)
  {
    super (title);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) { dispose(); }
    });
    int frameWidth = 300; 
    int frameHeight = 300;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    Panel cp = new Panel(null);
    add(cp);
    // Anfang Komponenten
    textArea1.setBounds(80, 40, 145, 129);
    cp.add(textArea1);
    // Ende Komponenten
    setResizable(false);
    setVisible(true);
  }

  // Anfang Methoden
  // Ende Methoden

  public static void main(String[] args) 
  {
    new hallo("hallo");
  }
}
