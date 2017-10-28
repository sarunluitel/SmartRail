package SmartRail;

public class Station extends Thread implements Component
{
  //adjacent track to the station.
  private static int totalStation=0;
  private Track adjTrack; //expects pointer to a track
  private String StationName; //expects Names in format St.15

  private Station(){}

  public Station(String StationName,Track adjacentTrack)
  {
    totalStation++;
    this.adjTrack=adjacentTrack;
    this.StationName=StationName;
  }

  public Component nextComponent()
  {
    return this.adjTrack;
  }
  public String getStationName(){
    return this.StationName;
  }

  @Override
  public String acceptMessage(String message)
  {
    return null;
  }

  @Override
  public boolean hasComponent(Component c, String dir)
  {
    return false;
  }
}
