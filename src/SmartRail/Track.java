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

  public void acceptMessage(Message message)
  {
    this.message = message;
    //return message;
  }

  public boolean findPath(Component c, String dir)
  {
    LinkedList<Component> targetComponent = new LinkedList<>();
    targetComponent.add(c);
    if (dir.equalsIgnoreCase("right"))
    {

      right.acceptMessage(new Message(dir, "findpath", targetComponent));
      return true;
    }
    else if (dir.equalsIgnoreCase("left"))
    {
      left.acceptMessage(new Message(dir, "findpath", targetComponent));
      return true;
    }

    return false;
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
        } catch (InterruptedException ex)
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
          findPath(target.get(0), direction);
          message = null;
          notifyAll();
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
