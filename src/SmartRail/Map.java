package SmartRail;

import java.util.ArrayList;

class Map
{
  private static int layerCount=-1;
  private ArrayList<ArrayList> layers = new ArrayList<>();
  //private ArrayList<Component> path0 = new ArrayList<>();

  void setMap(String config)
  {
    layerCount++;
    layers.add(layerCount,new ArrayList<Component>());
    int componentsInLine=-1;
    for (char c : config.toCharArray())
    {
      componentsInLine++;
      switch (c)
      {
        case 'R':
          layers.get(layerCount).add(componentsInLine,new Station());
          System.out.println("Added staiton in"+ layers.get(layerCount));
          break;

        case '=':
          layers.get(layerCount).add(componentsInLine,new Track());
          break;

        case 'O':
          layers.get(layerCount).add(componentsInLine,new Light());
          break;

        case 'L':
          layers.get(layerCount).add(componentsInLine,new Station());
          break;
      }
    }
    assignNeighbour(componentsInLine);
  }
  private void assignNeighbour(int comInLayer)
  {
    for (int i = 0; i < comInLayer; i++)
    {

     // layers.get(layerCount).get(i).
    }
  }
}
