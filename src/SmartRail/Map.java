package SmartRail;

import java.util.ArrayList;

class Map
{
  private static int layerCount = -1;
  private ArrayList<ArrayList> layers = new ArrayList<>();

  void setMap(String config)
  {
    layerCount++;
    layers.add(layerCount, new ArrayList<Component>());
    int componentsInLine = -1;
    for (char c : config.toCharArray())
    {
      componentsInLine++;
      switch (c)
      {
        case 'R':
          layers.get(layerCount).add(componentsInLine, new Station());
          break;

        case '=':
          layers.get(layerCount).add(componentsInLine, new Track());
          break;

        case 'O':
          layers.get(layerCount).add(componentsInLine, new Light());
          break;

        case 'L':
          layers.get(layerCount).add(componentsInLine, new Station());
          break;
      }
    }
    assignNeighbour(componentsInLine);
  }

  private void assignNeighbour(int compInLayer)
  {
    ArrayList temp = layers.get(layerCount);
    //assign first Track to station.

    Station rightStation = (Station) temp.get(compInLayer);
    rightStation.setLeftTrack((Track) temp.get(compInLayer - 1));

    //Assign Last component to left station
    Station leftStation = (Station) temp.get(0);
    leftStation.setRightTrack((Track) temp.get(1));

    for (int i = 1; i < compInLayer; i++)
    {

      if (temp.get(i) instanceof Track)
      {
        ((Track) temp.get(i)).setNeighbors((Component) temp.get(i + 1), "right");
        ((Track) temp.get(i)).setNeighbors((Component) temp.get(i - 1), "left");
      }

      if (temp.get(i) instanceof Light)
      {
        ((Light) temp.get(i)).setLeftComponent((Component) temp.get(i - 1));
        ((Light) temp.get(i)).setRightComponent((Component) temp.get(i + 1));
      }

    }
  }

  ArrayList getMap(int layer)
  {
    return this.layers.get(layer);
  }

  int getcompInLayer(int layer)
  {
    return layers.get(layer).size();
  }

}


