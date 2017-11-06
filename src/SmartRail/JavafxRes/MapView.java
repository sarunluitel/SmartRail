package SmartRail.JavafxRes;


import java.util.ArrayList;

public class MapView
{
  private static MapView mapView=null;
  private  ArrayList<ArrayList> entireMap;


  private MapView()
  {
  }

  public static MapView getInstance()
  {
    if(mapView==null)
    {
      mapView= new MapView();
    }
    return mapView;

  }



  public  void setEntireMap(ArrayList<ArrayList> a)
  {
    entireMap = a;
  }

  public  ArrayList<ArrayList> getEntireMap()
  {
    return entireMap;
  }
}
