import java.util.*;

public class Fakultät extends IOFrame
{
  public Fakultät(String title, ArrayList<String> Inputs, ArrayList<String> Outputs, int lblWidth, int txtFieldWidth){
    super(title, Inputs, Outputs,lblWidth, txtFieldWidth);
  }
  
  public static void main(String[] args) 
  {
    ArrayList<String> constInputs = new ArrayList<String>();
    constInputs.add("n");
    ArrayList<String> constOutputs = new ArrayList<String>();
    constOutputs.add("n!");
    
    new Fakultät("Fakultät",constInputs,constOutputs, 30, 150);
  }
  
  public void rechnen(ArrayList<String> calcInput){
    int fak=fakultät(Integer.parseInt(calcInput.get(0)));
    if (fak==-1) 
    {
      Output.get(0).setText("Fakultät nicht errechenbar");
    }else{
      Output.get(0).setText(Integer.toString(fak));  
    }
  }
  public int fakultät(int n){
    if (n==1) 
    {
      return 1;
    }else {
      if (n>31) 
      {
        return -1;
      }else{
        if (n<0) 
        {
          return -1;
        }else{
          return fakultät(n-1)*n;
        }
        
      }
    }
  }
}