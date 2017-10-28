package SmartRail;

import java.util.List;
import java.util.ArrayList;

public class Track extends Thread implements Component
{
  ArrayList<Component> neighbors;
  String name;
  private Component left;
  private Component right;

  public Component getLeft()
  {
    return left;
  }

  public Component getRight()
  {
    return right;
  }

  public Track(String name)
  {
    this.name = name;
    this.left=left;
    this.right=right;
  }

  public void setNeighbors(Component c, String dir)
  {
    if(dir.equalsIgnoreCase("right"))
    {
     right = c;
    }
    else
    {
      left = c;
    }
  }

  public String acceptMessage(String message)
  {
    System.out.println(name);
    System.out.println(message);
    return message;
  }

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

  @Override
  public void run()
  {
    if(!neighbors.isEmpty())
    {
      neighbors.get(0).acceptMessage("Hi");
    }
  }
  @Override
  public Component nextComponent(String Direction)
  {
    if(Direction.equalsIgnoreCase("right"))return right;
    return left;
  }
}
