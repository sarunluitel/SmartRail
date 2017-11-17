package SmartRail;

import SmartRail.JavafxRes.MapView;
import SmartRail.JavafxRes.TrainView;

/************************************
 @author Sarun Luitel, Vincent Huber
 ************************************/
public class Main
{
  public static void main(String[] args) throws Exception
  {
    Main mainObj = new Main();
    mainObj.instantiate(args);
  }

  private void instantiate(String[] args) throws Exception
  {
    Configuration c = new Configuration();
    Map map = new Map();

    String temp;

    while (true)
    {
      temp = c.getNextLine();
      if (temp.equals("EOF")) break;
      map.setMap(temp);
    }
    MapView.getInstance().setEntireMap(map.getEntireMap());

    JavaFX GUI = new JavaFX();
    GUI.runDisplay(args);

  }


}
