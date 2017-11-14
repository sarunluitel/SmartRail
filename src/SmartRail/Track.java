package SmartRail;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class Track extends Thread implements Component
{
  ArrayList<Component> neighbors;
  String name;
  private static int totalTracks = 0;
  private Component left;
  private Component right;
  private Message message = null;

  public Track()
{
  totalTracks++;
  this.name = "Track " + totalTracks;
}

  public Track(Component left, Component right)
  {
    totalTracks++;
    this.name = "Track " + totalTracks;
    this.left=left;
    this.right=right;
  }

  //Getter methods
  public Component getLeft()
  {
    return left;
  }

  public Component getRight()
  {
    return right;
  }


  //Setters for Data
  public void setNeighbors(Component c, String dir)
  {
    if (dir.equalsIgnoreCase("right"))
    {
      right = c;
    } else
    {
      left = c;
    }
  }

  @Override
  public void getTrainId(Train t)
  {
    //Set train reference
  }

  public synchronized void acceptMessage(Message message)
  {
    System.out.println("Recieved message by track: " + name);
    this.message = message;
    notifyAll();
    //return message;
  }

  public synchronized boolean findPath(Component c, String dir)
  {
    LinkedList<Component> targetComponent = new LinkedList<>();
    targetComponent.add(c);
    if (dir.equalsIgnoreCase("right"))
    {

      right.acceptMessage(new Message(dir, "findpath", targetComponent, this));
      return true;
    }
    else if (dir.equalsIgnoreCase("left"))
    {
      left.acceptMessage(new Message(dir, "findpath", targetComponent, this));
      return true;
    }

    return false;
  }

  @Override
  public synchronized boolean returnPath(Message m)
  {
    String dir = m.getDirection();
    LinkedList<Component> pathList = m.getTarget();
    if(!pathList.isEmpty())
    {
      pathList.add(this);
    }

    if(dir.equalsIgnoreCase("left"))
    {
      m.setSender(this);
      left.acceptMessage(m);
    }
    else
    {
      m.setSender(this);
      right.acceptMessage(m);
    }

    return true;
  }

  @Override
  public void run()
  {
    while(true)
    {
      synchronized (this)
      {
        if (message == null)
        {
          try {
            wait();
          } catch (InterruptedException ex) {
            //Print
          }
        }
        else
        {
          String action = message.getAction();
          String direction = message.getDirection();
          LinkedList<Component> target = message.getTarget();
          if (action.equalsIgnoreCase("findpath"))
          {
            findPath(target.get(0), direction);
            message = null;
            notifyAll();
          }
          else if (action.equalsIgnoreCase("returnpath"))
          {
            returnPath(message);
            message = null;
            notifyAll();
          }
          else
          {
            System.out.println(action);
            message = null;
          }

        }
      }
    }
  }

  @Override
  public Component nextComponent(String Direction)
  {
    if (Direction.equalsIgnoreCase("right")) return right;
    return left;
  }

  public String getComponentName()
  {
    return this.name;
  }
}
