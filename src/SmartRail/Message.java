package SmartRail;

import java.util.LinkedList;

public class Message
{
  private String direction;
  private String action;
  private LinkedList<Component> intendedTarget;

  public Message (String dir, String act, LinkedList<Component> c)
  {
    direction = dir;
    action = act;
    intendedTarget = c;
  }

  public String getAction()
  {
    return action;
  }

  public String getDirection()
  {
    return direction;
  }

  public LinkedList<Component> getTarget()
  {
    return intendedTarget;
  }
}
