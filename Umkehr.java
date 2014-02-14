import java.util.*;

public class Umkehr extends IOFrame
{
  public Umkehr(String title, ArrayList<String> Inputs, ArrayList<String> Outputs, int lblWidth, int txtFieldWidth){
    super(title, Inputs, Outputs,lblWidth, txtFieldWidth);
  }
  
  public static void main(String[] args) 
  {
    ArrayList<String> constInputs = new ArrayList<String>();
    constInputs.add("Wort");
    ArrayList<String> constOutputs = new ArrayList<String>();
    constOutputs.add("troW");
    new Umkehr("Umkehr",constInputs,constOutputs, 30, 150);
  }
  
  public void rechnen(ArrayList<String> calcInput){
    String a = calcInput.get(0);
    String b = "";
    for (int i=0;i<a.length();i++) 
    {
      b = b + a.charAt(a.length()-1 - i);
    }
    Output.get(0).setText(b);
  }
}