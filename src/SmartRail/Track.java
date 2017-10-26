package SmartRail;

import java.util.List;
import java.util.ArrayList;

public class Track extends Thread implements Component
{
  ArrayList<Component> neighbors;

  public Track()
  {

  }

  public void giveNeighbors(ArrayList<Component> neighbors)
  {
    this.neighbors = neighbors;
  }

  public String acceptMessage(String message)
  {
    System.out.println(message);
    return message;
  }

  @Override
  public void run()
  {
    if(!neighbors.isEmpty())
    {
      neighbors.get(0).acceptMessage("Hi");
    }
  }

}
