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
    Track t1 = new Track();
    Station s1 = new Station();
    Station s2 = new Station();
    s1.setRightTrack(t1);
    s2.setLeftTrack(t1);
    t1.setNeighbors(s1,"left");
    t1.setNeighbors(s2,"right");

    Train testTrain = new Train(s2,s1);
    //System.out.println("s1: " + s1.getComponentName() + "s2: " + s2.getComponentName() + "track1: " + t1.getComponentName());
    //Train t1 = new Train((Station) map.getMap(0).get(0), (Station) map.getMap(0).get(map.getcompInLayer(0) - 1));
    Train t2 = new Train((Station) map.getMap(1).get(0), (Station) map.getMap(1).get(map.getcompInLayer(1) - 1));

    //trainView.addTrain(t1);
    trainView.addTrain(t2);

    //t1.start();
    //t2.start();

    testTrain.start();
    s1.start();
    t1.start();
    s2.start();

  }
}


