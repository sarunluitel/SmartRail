package SmartRail.JavafxRes;

import java.util.ArrayList;

public class MapView
{
  private static ArrayList<ArrayList> entireMap;

  public static void setEntireMap(ArrayList<ArrayList> a)
  {
    entireMap = a;
  }

  public static ArrayList<ArrayList> getEntireMap()
  {
    return entireMap;
  }
}
