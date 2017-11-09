package SmartRail;

import javax.xml.soap.SOAPPart;
import java.util.LinkedList;

public class Station extends Thread implements Component
{
  //adjacent track to the station.
  private static int totalStation = 0;
  private Track rightTrack;//expects pointer to a track
  private Track leftTrack;
  private String stationName; //expects Names in format St.15
  private Train trainInStation = null;
  private volatile Message message = new Message("0", "0", new LinkedList<>());


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

  @Override
  public void getTrainId(Train t)
  {
    trainInStation = t;
  }

  // code to  determine station
  public Component nextComponent(String Direction)
  {
    //if(Direction.equalsIgnoreCase("right")) return rightTrack;
    return leftTrack;

  }

  @Override
  public synchronized void acceptMessage(Message mes)
  {
    System.out.println("Message received: " + stationName);
    message = mes;
    //System.out.println(message.getDirection());
    notifyAll();
  }

  @Override
  public boolean findPath(Component c, String dir)
  {
    LinkedList<Component> compList = new LinkedList<>();
    if(dir.equalsIgnoreCase("right") && rightTrack != null)
    {
      compList.add(c);
      rightTrack.acceptMessage(new Message(dir, "findpath", compList));
      return true;
    }
    else if(dir.equalsIgnoreCase("left") && leftTrack != null)
    {
      compList.add(c);
      leftTrack.acceptMessage(new Message(dir, "findpath", compList));
      return true;
    }
    return false;
  }

  @Override
  public boolean returnPath(Message m)
  {
    String dir = m.getDirection();

    if(trainInStation != null)
    {
      System.out.println("Not null");
      //Do something
    }
    if(dir.equalsIgnoreCase("right") && rightTrack != null)
    {
      rightTrack.acceptMessage(m);
      return true;
    }
    else if(dir.equalsIgnoreCase("left") && leftTrack != null)
    {
      leftTrack.acceptMessage(m);
      return true;
    }
    return false;

  }

  @Override
  public void run()
  {
    int testing = 0;
    while(true)
    {
      synchronized (this)
      {
        if (message.getDirection().equalsIgnoreCase("0")) {


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
          //System.out.println("Past wait");
          String newDir;
          String action = message.getAction();
          String direction = message.getDirection();
          LinkedList<Component> target = message.getTarget();
          if (action.equalsIgnoreCase("findpath"))
          {
            if (target.get(0).getComponentName().equalsIgnoreCase(stationName))
            {
              System.out.println(stationName + " found.");
              LinkedList<Component> pathList = new LinkedList<>();
              if(direction.equalsIgnoreCase("right"))
              {
                newDir = "left";
              }
              else
              {
                newDir = "right";
              }
              Message wrongStation = new Message(newDir, "returnpath", pathList);
              returnPath(wrongStation);
              System.out.println("Sending return path");
            }
            else if (findPath(target.get(0), direction))
            {
              System.out.println("Sending messsage to track: " + rightTrack.getComponentName());
              //System.out.println(stationName + " found.");
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
            else
            {
              LinkedList<Component> emptyList = new LinkedList<>();
              if(direction.equalsIgnoreCase("right"))
              {
                newDir = "left";
              }
              else
              {
                newDir = "right";
              }
              Message wrongStation = new Message(newDir, "returnpath", emptyList);
              returnPath(wrongStation);
              System.out.println("Sending return path");

            }
            message = new Message("0", "0", new LinkedList<>());
          }
        }
      }
      //System.out.println("Here");
    }

  }

  public synchronized void lookForMessage()
  {

    if (message == null)
    {
      try
      {

        System.out.println("Waiting");
        wait();

      } catch (Exception ex)
      {
        //Print
      }
    }
    System.out.println("out");
  }

  public String getComponentName()
  {
    return this.stationName;
  }
}
