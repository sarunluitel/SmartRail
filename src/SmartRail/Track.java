package SmartRail;

import java.util.List;
import java.util.ArrayList;

public class Track implements Component
{
  ArrayList<Component> neighbors;

  public Track(ArrayList<Component> neighbors)
  {
    this.neighbors = neighbors;
  }

  public String acceptMessage(String message)
  {
    return "";
  }

}
