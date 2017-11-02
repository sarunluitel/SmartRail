package SmartRail;

public class Station extends Thread implements Component
{
  //adjacent track to the station.
  private static int totalStation=0;
  private Track right;//expects pointer to a track
  private Track left;
  private String stationName; //expects Names in format St.15
  private String message;



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

  @Override
  public void acceptMessage(String message)
  {
    //return null;
  }

  @Override
  public boolean findPath(Component c, String dir)
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
      return right.findPath(c, dir);
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
      return left.findPath(c, dir);
    }

    return false;
  }
  public String getComponentName()
  {
    return this.stationName;
  }
}
