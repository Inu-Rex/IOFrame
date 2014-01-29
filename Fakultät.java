import java.util.*;

public class Fakult�t extends IOFrame
{
  public Fakult�t(String title, ArrayList<String> Inputs, ArrayList<String> Outputs, int lblWidth, int txtFieldWidth){
    super(title, Inputs, Outputs,lblWidth, txtFieldWidth);
  }
  
  public static void main(String[] args) 
  {
    ArrayList<String> constInputs = new ArrayList<String>();
    constInputs.add("n");
    ArrayList<String> constOutputs = new ArrayList<String>();
    constOutputs.add("n!");
    
    new Fakult�t("Fakult�t",constInputs,constOutputs, 30, 150);
  }
  
  public void rechnen(ArrayList<String> calcInput){
    int fak=fakult�t(Integer.parseInt(calcInput.get(0)));
    if (fak==-1) 
    {
      Output.get(0).setText("Fakult�t nicht errechenbar");
    }else{
      Output.get(0).setText(Integer.toString(fak));  
    }
  }
  public int fakult�t(int n){
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
          return fakult�t(n-1)*n;
        }
        
      }
    }
  }
}