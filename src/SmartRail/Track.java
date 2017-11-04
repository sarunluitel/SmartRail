package SmartRail;

import java.util.List;
import java.util.ArrayList;

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
    if (dir.equalsIgnoreCase("right"))
    {
      right.acceptMessage(new Message(dir, "findpath", c));
      return true;
    }
    else if (dir.equalsIgnoreCase("left"))
    {
      left.acceptMessage(new Message(dir, "findpath", c));
      return true;
    }

    return false;
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
        Component target = message.getTarget();
        if(action.equalsIgnoreCase("findpath"))
        {
          findPath(target, direction);
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
