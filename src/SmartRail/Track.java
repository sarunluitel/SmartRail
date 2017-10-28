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

  public Track(String name, Component left, Component right)
  {
    this.name = name;
    this.left=left;
    this.right=right;
  }

  public void giveNeighbors(ArrayList<Component> neighbors)
  {
    this.neighbors = neighbors;
  }

  public String acceptMessage(String message)
  {
    System.out.println(name);
    System.out.println(message);
    return message;
  }

  public boolean hasComponent(Component c, String dir)
  {
    if(neighbors.contains(c))
    {
      return true;
    }
    else
    {
      return neighbors.get(1).hasComponent(c,dir);
    }
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
  public Component nextComponent(){
    return left;
  }
}
