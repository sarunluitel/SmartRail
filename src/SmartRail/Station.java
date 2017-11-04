package SmartRail;

public class Station extends Thread implements Component
{
  //adjacent track to the station.
  private static int totalStation = 0;
  private Track rightTrack;//expects pointer to a track
  private Track leftTrack;
  private String stationName; //expects Names in format St.15
  private Message message;


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
  public void acceptMessage(Message message)
  {
    this.message = message;
  }

  @Override
  public boolean findPath(Component c, String dir)
  {

    if (c.getComponentName().equalsIgnoreCase(stationName))
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  @Override
  public void run()
  {

  }

  public String getComponentName()
  {
    return this.stationName;
  }
}
