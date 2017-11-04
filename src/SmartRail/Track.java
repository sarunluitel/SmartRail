package SmartRail;

import java.util.List;
import java.util.ArrayList;

public class Track extends Thread implements Component
{
  ArrayList<Component> neighbors;
  String name;
  private static int totalTracks=0;
  private Component left;
  private Component right;
  private String message = null;

  public Track()
  {
    totalTracks++;
    this.name = "Track "+ totalTracks;
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
    if(dir.equalsIgnoreCase("right"))
    {
     right = c;
    }
    else
    {
      left = c;
    }
  }

  public void acceptMessage(String message)
  {
    this.message = message;
    //return message;
  }

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

  @Override
  public void run()
  {

  }
  @Override
  public Component nextComponent(String Direction)
  {
    if(Direction.equalsIgnoreCase("right"))return right;
    return left;
  }
  public String getComponentName()
  {
    return this.name;
  }
}
