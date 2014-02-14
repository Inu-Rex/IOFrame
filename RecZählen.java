import java.util.*;

public class RecZählen extends IOFrame
{
  public RecZählen(String title, ArrayList<String> Inputs, ArrayList<String> Outputs, int lblWidth, int txtFieldWidth){
    super(title, Inputs, Outputs,lblWidth, txtFieldWidth);
  }
  
  public static void main(String[] args) 
  {
    ArrayList<String> constInputs = new ArrayList<String>();
    constInputs.add("a");
    constInputs.add("b");
    ArrayList<String> constOutputs = new ArrayList<String>();
    constOutputs.add("a+b");
    constOutputs.add("a*b");
    constOutputs.add("a^b");
    new RecZählen("RecZählen",constInputs,constOutputs, 30, 150);
  }
  
  public void rechnen(ArrayList<String> calcInput){
    int a = Integer.parseInt(calcInput.get(0));
    int b = Integer.parseInt(calcInput.get(1));
    Output.get(0).setText(Integer.toString(RecAdd(a,b)));
    Output.get(1).setText(Integer.toString(RecMult(a,b)));
    Output.get(2).setText(Integer.toString(RecPotenz(a,b)));
  }
  
  public int RecAdd(int a, int b){
    if (b==0) 
    {
      return a;
    }else{
      return RecAdd(a+1,b-1);
    } 
  }
  
  public int RecMult(int a, int b){
    if (b==1) 
    {
      return a;
    }else{
      return  a + RecMult(a,b-1);
    }
  }
  
  public int RecPotenz(int a, int b){
    if (b==1) 
    {
      return a;
    }else{
      return RecMult(a, RecPotenz(a, b-1));
    }
  }
}