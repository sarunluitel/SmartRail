package SmartRail;

import java.util.ArrayList;

public class Map
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
        case 'S':
          layers.get(layerCount).add(componentsInLine, new Station());
          break;

        case '=':
          layers.get(layerCount).add(componentsInLine, new Track());
          break;

        case 'R':
          layers.get(layerCount).add(componentsInLine, new Switch(false));
          break;

        case 'L':
          layers.get(layerCount).add(componentsInLine, new Switch(true));
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
    rightStation.start();

    //Assign Last component to left station
    Station leftStation = (Station) temp.get(0);
    leftStation.setRightTrack((Track) temp.get(1));
    leftStation.start();

    //Starts with 1 because the component 0 is a station. already hard coded
    for (int i = 1; i < compInLayer; i++)
    {
      if (temp.get(i) instanceof Track)
      {
        ((Track) temp.get(i)).setNeighbors((Component) temp.get(i + 1), "right");
        ((Track) temp.get(i)).setNeighbors((Component) temp.get(i - 1), "left");
        ((Track) temp.get(i)).start();
      }

      if (temp.get(i) instanceof Switch)
      {// a switch unfolds to be a 0=Sw=0; surrounded by lights.

       /* ((Switch) temp.get(i)).setLeft((Track) temp.get(i - 1));
        ((Switch) temp.get(i)).setRightTrack((Track) temp.get(i + 1));*/
       Light leftLight= new Light();
       Light rightLight= new Light();
       Track leftTrack = new Track();
       Track rightTrack= new Track();


       leftLight.setLeftTrack((Track) temp.get(i - 1));
       leftLight.setRightTrack(leftTrack);
        ((Track) temp.get(i - 1)).setNeighbors(leftLight,"right");

       leftTrack.setNeighbors(leftLight,"left");
       leftTrack.setNeighbors((Switch) temp.get(i),"right");


        rightLight.setLeftTrack(rightTrack);
        rightLight.setRightTrack((Track) temp.get(i + 1));
        ((Track) temp.get(i + 1)).setNeighbors(rightLight,"left");

        rightTrack.setNeighbors((Switch) temp.get(i),"left");
        rightTrack.setNeighbors(rightLight,"right");


        ((Switch) temp.get(i)).setLeft(leftTrack);// set O=Sw

        ((Switch) temp.get(i)).setRight(rightTrack);

        ((Switch) temp.get(i)).start();
        rightLight.start();
        leftLight.start();
        leftTrack.start();
        rightTrack.start();

      }

    }
  }

  ArrayList getMap(int layer)
  {
    return this.layers.get(layer);
  }

  ArrayList getEntireMap()
  {
    return this.layers;
  }

  //Number of components in an array.
  int getcompInLayer(int layer)
  {
    return layers.get(layer).size();
  }


}


