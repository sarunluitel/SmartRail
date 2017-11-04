package SmartRail;

public class Station extends Thread implements Component
{
  //adjacent track to the station.
  private static int totalStation = 0;
  private Track rightTrack;//expects pointer to a track
  private Track leftTrack;
  private String stationName; //expects Names in format St.15
  private String message;


  public Station()
  {
    totalStation++;
    this.stationName = "station " + totalStation;
  }


  // Setters for data types
  public void setRightTrack(Track rightTrack)
  {
    this.rightTrack = rightTrack;
  }

  public void setLeftTrack(Track leftTrack)
  {
    this.leftTrack = leftTrack;
  }

  // code to  determine station
  public Component nextComponent(String Direction)
  {
    //if(Direction.equalsIgnoreCase("right")) return rightTrack;
    return leftTrack;

  }

  @Override
  public void acceptMessage(String message)
  {
    //return null;
  }

  @Override
  public boolean findPath(Component c, String dir)
  {
    if (dir.equalsIgnoreCase("right"))
    {
      if (c.equals(rightTrack))
      {
        return true;
      } else if (rightTrack.equals(null))
      {
        return false;
      }
      return rightTrack.findPath(c, dir);
    } else if (dir.equalsIgnoreCase("left"))
    {
      if (c.equals(leftTrack))
      {
        return true;
      } else if (leftTrack.equals(null))
      {
        return false;
      }
      return leftTrack.findPath(c, dir);
    }

    return false;
  }

  public String getComponentName()
  {
    return this.stationName;
  }
}
