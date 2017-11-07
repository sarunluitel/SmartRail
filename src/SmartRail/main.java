package SmartRail;

import SmartRail.JavafxRes.MapView;
import SmartRail.JavafxRes.TrainView;
import SmartRail.JavafxRes.XMLController;

/************************************
 @author Sarun Luitel
 ************************************/
public class main
{

  static TrainView trainView;

  public static void main(String[] args)
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

    instantiate(map);

    JavaFX GUI = new JavaFX();
    GUI.runDisplay(args);




  }

  static void instantiate(Map map)
  {
    trainView = TrainView.getInstance();

    Train t1 = new Train((Station) map.getMap(0).get(0), (Station) map.getMap(0).get(map.getcompInLayer(0) - 1));

    Train t2 = new Train((Station) map.getMap(1).get(0), (Station) map.getMap(1).get(map.getcompInLayer(1) - 1));

    trainView.addTrain(t1);
    trainView.addTrain(t2);



    t1.start();
    t2.start();

  }
}


