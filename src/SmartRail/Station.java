package SmartRail;

import java.util.LinkedList;

public class Station extends Thread implements Component
{
  //adjacent track to the station.
  private static int totalStation = 0;
  private Track rightTrack;//expects pointer to a track
  private Track leftTrack;
  private String stationName; //expects Names in format St.15
  private Train trainInStation = null;
  private volatile LinkedList<Message> messages = new LinkedList<>();
  private boolean secured = false;


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

  @Override
  public void trainLeaving()
  {
    secured = false;
    trainInStation = null;
  }

  // code to  determine station
  public Component nextComponent(String Direction)
  {
    if (Direction.equalsIgnoreCase("right")) return rightTrack;
    return leftTrack;

  }

  @Override
  public synchronized void acceptMessage(Message mes)
  {
    System.out.println("Message received: " + stationName);
    messages.add(mes);
    System.out.println(messages.getFirst().getAction());
    System.out.println(messages.size());
    //System.out.println(message.getDirection());
    notifyAll();
  }

  @Override
  public boolean findPath(Component c, String dir)
  {
    LinkedList<Component> compList = new LinkedList<>();
    if (dir.equalsIgnoreCase("right") && rightTrack != null)
    {
      compList.add(c);
      rightTrack.acceptMessage(new Message(dir, "findpath", compList, this));
      return true;
    } else if (dir.equalsIgnoreCase("left") && leftTrack != null)
    {
      compList.add(c);
      leftTrack.acceptMessage(new Message(dir, "findpath", compList, this));
      return true;
    }
    return false;
  }

  @Override
  public boolean returnPath(Message m)
  {
    String dir = m.getDirection();

    if (trainInStation != null)
    {
      System.out.println("Not null");
      //Do something
    } else if (dir.equalsIgnoreCase("right") && rightTrack != null)
    {
      rightTrack.acceptMessage(m);
      return true;
    } else if (dir.equalsIgnoreCase("left") && leftTrack != null)
    {
      leftTrack.acceptMessage(m);
      return true;
    }
    return false;

  }

  @Override
  public synchronized boolean securePath(Message m)
  {
    String dir = m.getDirection();
    if (secured)
    {
      if (dir.equalsIgnoreCase("right"))
      {
        leftTrack.acceptMessage(new Message("left", "couldNotSecure", new LinkedList<>(), this));
      } else
      {
        rightTrack.acceptMessage(new Message("right", "couldNotSecure", new LinkedList<>(), this));
      }
    }
    secured = true;
    System.out.println("Secure " + stationName);
    //System.out.println(m.getTarget().getLast().getComponentName());
    m.getTarget().remove(m.getTarget().size() - 1);
    //System.out.println(m.getTarget().getLast().getComponentName());
    messages.remove();
    if (dir.equalsIgnoreCase("right"))
    {
      rightTrack.acceptMessage(m);
    } else
    {
      leftTrack.acceptMessage(m);
    }
    return true;
  }

  @Override
  public synchronized boolean readyForTrain(Message m)
  {
    if (trainInStation != null)
    {

    }
    System.out.println("sending back");
    return false;
  }

  @Override
  public synchronized boolean couldNotSecure(Message m)
  {
    return false;
  }

  @Override
  public void run()
  {

    while (true)
    {
      synchronized (this)
      {
        if (messages.isEmpty())
        {
          try
          {
            wait();
          } catch (Exception ex)
          {
            //Print
          }
        } else
        {
          //System.out.println("Past wait");
          String newDir;
          String action = messages.getFirst().getAction();
          String direction = messages.getFirst().getDirection();
          LinkedList<Component> target = messages.getFirst().getTarget();
          if (action.equalsIgnoreCase("findpath"))
          {
            if (target.get(0).getComponentName().equalsIgnoreCase(stationName))
            {
              System.out.println(stationName + " found.");
              LinkedList<Component> pathList = new LinkedList<>();
              if (direction.equalsIgnoreCase("right"))
              {
                newDir = "left";
              } else
              {
                newDir = "right";
              }
              pathList.add(this);
              Message correctStation = new Message(newDir, "returnpath", pathList, this);
              returnPath(correctStation);
              System.out.println("Sending return path");
            } else if (findPath(target.get(0), direction))
            {
              if (direction.equalsIgnoreCase("right"))
              {
                System.out.println("Sending messsage to track: " + rightTrack.getComponentName());
              } else
              {
                System.out.println("Sending messsage to track: " + leftTrack.getComponentName());
              }
              //System.out.println(stationName + " found.");
            } else
            {
              LinkedList<Component> emptyList = new LinkedList<>();
              if (direction.equalsIgnoreCase("right"))
              {
                newDir = "left";
              } else
              {
                newDir = "right";
              }
              Message wrongStation = new Message(newDir, "returnpath", emptyList, this);
              returnPath(wrongStation);
              System.out.println("Sending return path");

            }
            messages.remove();
          } else if (action.equalsIgnoreCase("returnpath"))
          {
            if (trainInStation != null)
            {
              System.out.println("Train on station");
              if (!target.isEmpty())
              {
                messages.getFirst().getTarget().add(this);
              }
              trainInStation.acceptMessage(messages.getFirst());
              messages.remove();
              ;
            }
          } else if (action.equalsIgnoreCase("securepath"))
          {
            System.out.println(target.getFirst().getComponentName());
            if (target.getFirst().getComponentName().equalsIgnoreCase(stationName))
            {
              System.out.println("HERE");
              secured = true;
              if (direction.equalsIgnoreCase("right"))
              {
                newDir = "left";
              } else
              {
                newDir = "right";
              }
              Message pathSecured = new Message(newDir, "readyfortrain", target, this);
              if (newDir.equalsIgnoreCase("left"))
              {
                leftTrack.acceptMessage(pathSecured);
              } else
              {
                rightTrack.acceptMessage(pathSecured);
              }
              messages.remove();
            } else
            {
              securePath(messages.getFirst());
            }
            //====================================

            //====================================
          } else if (action.equalsIgnoreCase("readyfortrain"))
          {
            if (trainInStation != null)
            {
              trainInStation.acceptMessage(messages.getFirst());

              messages.remove();
            }
          }
        }
      }
      //System.out.println("Here");
    }

  }

  public String directionOut()
  {
    if (rightTrack != null)
    {
      return "right";
    }
    return "left";
  }

  public String getComponentName()
  {
    return this.stationName;
  }
}
