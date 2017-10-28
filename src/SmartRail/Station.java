package SmartRail;

public class Station extends Thread implements Component
{
  //adjacent track to the station.
  private static int totalStation=0;
  private Track right;//expects pointer to a track
  private Track left;
  private String stationName; //expects Names in format St.15



  public Station(String stationName,Track leftTrack, Track rightTrack)
  {
    totalStation++;
    this.right =rightTrack;
    this.left =leftTrack;
    this.stationName =stationName;
  }

  public Component nextComponent(String Direction)
  {
    if(Direction.equalsIgnoreCase("right")) return right;
    return left;

  }
  public String getStationName(){
    return this.stationName;
  }

  @Override
  public String acceptMessage(String message)
  {
    return null;
  }

  @Override
  public boolean hasComponent(Component c, String dir)
  {
    if(dir.equalsIgnoreCase("right"))
    {
      if(c.equals(right))
      {
        return true;
      }
      else if(right.equals(null))
      {
        return false;
      }
      return right.hasComponent(c, dir);
    }
    else if(dir.equalsIgnoreCase("left"))
    {
      if(c.equals(left))
      {
        return true;
      }
      else if(left.equals(null))
      {
        return false;
      }
      return left.hasComponent(c, dir);
    }

    return false;
  }
}
