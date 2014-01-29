import java.util.*;

public class Heron extends IOFrame
{
  public Heron(String title, ArrayList<String> Inputs, ArrayList<String> Outputs, int lblWidth, int txtFieldWidth){
    super(title, Inputs, Outputs,lblWidth, txtFieldWidth);
  }
  
  public static void main(String[] args) 
  {
    ArrayList<String> constInputs = new ArrayList<String>();
    constInputs.add("Zahl (z)");
    constInputs.add("Genauigkeit (a)");
    ArrayList<String> constOutputs = new ArrayList<String>();
    constOutputs.add("Wurzel");
    
    new Heron("Heron Algorithmus",constInputs,constOutputs, 100, 130);
  }
  
  public void rechnen(ArrayList<String> calcInput){
    double z = Double.parseDouble(calcInput.get(0));
    double a = Math.pow(10,-1 * Double.parseDouble(calcInput.get(1)));
    double x = 1;
    while (Math.abs(x*x - z) > a) 
    {
      double q = z / x;
      x = (x + q)/2;
    }
    Output.get(0).setText(Double.toString(x));
  }
}