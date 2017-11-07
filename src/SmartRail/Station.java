package SmartRail;

import java.util.LinkedList;

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
  public Message returnPath(Message m)
  {
    return message;
  }

  @Override
  public void run()
  {
    while(true)
    {
      if (message == null)
      {
        try
        {
          wait();
        } catch (Exception ex)
        {
          //Print
        }
      }
      else
      {
        String action = message.getAction();
        String direction = message.getDirection();
        LinkedList<Component> target = message.getTarget();
        if(action.equalsIgnoreCase("findpath"))
        {
          if(findPath(target.get(0), direction))
          {
            System.out.println(stationName + " found.");
            if(direction.equalsIgnoreCase("right"))
            {

            }
            else
            {
              LinkedList<Component> pathList = new LinkedList<>();
              pathList.add(this);
              Message path = new Message("right", "returnpath", pathList);
            }
          }
          message = null;
        }
      }
    }
  }

  public String getComponentName()
  {
    return this.stationName;
  }
}
