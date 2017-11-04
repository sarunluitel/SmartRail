package SmartRail;

public class Message
{
  private String direction;
  private String action;
  private Component intendedTarget;

  public Message (String dir, String act, Component c)
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

  public Component getTarget()
  {
    return intendedTarget;
  }
}
