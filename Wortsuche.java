import java.util.*;

public class Wortsuche extends IOFrame
{
  public Wortsuche(String title, ArrayList<String> Inputs, ArrayList<String> Outputs, int lblWidth, int txtFieldWidth){
    super(title, Inputs, Outputs,lblWidth, txtFieldWidth);
  }
  
  public static void main(String[] args) 
  {
    ArrayList<String> constInputs = new ArrayList<String>();
    constInputs.add("Text");
    constInputs.add("Suche nach");
    ArrayList<String> constOutputs = new ArrayList<String>();
    constOutputs.add("Ergebnis");
    
    new Wortsuche("Wortsuche",constInputs,constOutputs, 100, 300);
  }
  
  public void rechnen(ArrayList<String> calcInput){
    String in = calcInput.get(0);
    String search = calcInput.get(1);
    int found = -1;
    int startIndex = 0;
    String firstchar = String.valueOf(search.charAt(0));
    while (found == -1) 
    {
      int pos = in.indexOf(firstchar, startIndex);
      if (pos == -1){
        found = -2;
      }else{
        if (pos + search.length() > in.length()) 
        {
          found = -2;
        }else{
          startIndex = pos + 1;
          int testIndex = 1;
          while (found == -1) 
          {
            String char1 = Character.toString(in.charAt(testIndex+pos));
            String char2 = Character.toString(search.charAt(testIndex));
            if (char1.equals(char2)) 
            {
              testIndex = testIndex + 1;
              if (testIndex == (search.length())) 
              {
                found = pos;
              }
            }else{
              found = -3;
            }
          }
          if (found == -3) 
          {
            found = -1;
          }
        }
      }
    }
    if(found < 0) 
    {
      Output.get(0).setText("Nicht Gefunden");
    }else{
      Output.get(0).setText("Gefunden (Ab Zeichen: " + (found + 1) + ")");
    }
  }
}