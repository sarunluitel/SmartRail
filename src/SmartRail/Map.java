package SmartRail;

import SmartRail.JavafxRes.LightView;
import SmartRail.JavafxRes.StationView;
import SmartRail.JavafxRes.TrackView;

import java.util.ArrayList;
import java.util.LinkedList;

public class Map
{
  private int layerCount = -1;
  private ArrayList<ArrayList> layers = new ArrayList<>();

  void setMap(String config) throws Exception
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
        default:
          Exception e = new Exception("Please Use Letters S,R,L or = only");
          throw e;

      }
    }
    assignNeighbour(componentsInLine);
  }

  private void assignNeighbour(int compInLayer)
  {
    ArrayList temp = layers.get(layerCount);

// to push
    //Starts with 1 because the component 0 is a station. already hard coded
    for (int i = 0; i < compInLayer + 1; i++)
    {
      if (temp.get(i) instanceof Station)
      {
        //assign first Track to station.

        Station station = (Station) temp.get(i);
        if (i == 0) station.setRightTrack((Track) temp.get(1));
        if (i != 0) station.setLeftTrack((Track) temp.get(i - 1));
        StationView.getInstance().addStation(station.getComponentName(), station);
        station.start();


      }
      if (temp.get(i) instanceof Track)
      {
        if (i != compInLayer) ((Track) temp.get(i)).setNeighbors((Component) temp.get(i + 1), "right");

        if (i != 0) ((Track) temp.get(i)).setNeighbors((Component) temp.get(i - 1), "left");
        TrackView.getInstance().addTrack(((Track) temp.get(i)).getComponentName(), (Track) temp.get(i));

        ((Track) temp.get(i)).start();
      }

      if (temp.get(i) instanceof Switch)
      {// a switch unfolds to be a 0=Sw=0; surrounded by lights.

        Light leftLight = new Light();
        Light rightLight = new Light();
        Track leftTrack = new Track();
        Track rightTrack = new Track();


        // =0= connecting left light to the rest of system
        if (i != 0) leftLight.setLeftTrack((Track) temp.get(i - 1));
        leftLight.setRightTrack(leftTrack);
        if (i != 0)((Track) temp.get(i - 1)).setNeighbors(leftLight, "right");

        leftTrack.setNeighbors(leftLight, "left");
        leftTrack.setNeighbors((Switch) temp.get(i), "right");


        rightLight.setLeftTrack(rightTrack);
        if (i != compInLayer)rightLight.setRightTrack((Track) temp.get(i + 1));

        if (i != compInLayer)((Track) temp.get(i + 1)).setNeighbors(rightLight, "left");
        rightTrack.setNeighbors((Switch) temp.get(i), "left");

        rightTrack.setNeighbors(rightLight, "right");
        ((Switch) temp.get(i)).setLeft(leftTrack);// set O=Sw

        ((Switch) temp.get(i)).setRight(rightTrack);

        ((Switch) temp.get(i)).start();
        //add to view to be shown in canvas.
        LightView.getInstance().addLight(rightLight);
        LightView.getInstance().addLight(leftLight);

        TrackView.getInstance().addTrack(leftTrack.getComponentName(), leftTrack);
        TrackView.getInstance().addTrack(rightTrack.getComponentName(), rightTrack);

        temp.add(i + 1, rightLight);
        temp.add(i + 1, rightTrack);
        temp.add(i, leftTrack);
        temp.add(i, leftLight);

        compInLayer += 4;
        i += 3;
        rightLight.start();
        leftLight.start();
        leftTrack.start();
        rightTrack.start();
        //break;
      }
    }
    if (layerCount != 0)
      addNeighbourTop();// assign top and bottom layers for a switch
  }

  private void addNeighbourTop()
  {
    LinkedList<Track> tempList = new LinkedList<>();
    for (Component senior : (ArrayList<Component>) layers.get(layerCount - 1))
    {
      if (senior instanceof Switch && ((Switch) senior).getDown() == null)
      {
        for (Component junior : (ArrayList<Component>) layers.get(layerCount))
        {
          if (junior instanceof Switch && ((Switch) junior).getUpTrack() == null)
          {
            tempList.addLast(new Track());
            ((Switch) junior).setUpTrack(tempList.getLast());
            ((Switch) senior).setDown(tempList.getLast());
            if (((Switch) junior).getIsLeft())
            {

              tempList.getLast().setNeighbors(junior, "right");
              tempList.getLast().setNeighbors(senior, "left");


            } else
            {
              tempList.getLast().setNeighbors(junior, "left");
              tempList.getLast().setNeighbors(senior, "right");

            }
            tempList.getLast().start();

            break;
          }

        }
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
}


