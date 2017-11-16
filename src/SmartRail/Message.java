package SmartRail;

import java.util.LinkedList;

public class Message
{
  private String direction;
  private String action;
  private LinkedList<Component> intendedTarget;
  private Component sender;

  public Message(String dir, String act, LinkedList<Component> compList, Component c)
  {
    direction = dir;
    action = act;
    intendedTarget = compList;
    sender = c;
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

  public Component getSender()
  {
    return sender;
  }

  public void setSender(Component c)
  {
    sender = c;
  }
}
