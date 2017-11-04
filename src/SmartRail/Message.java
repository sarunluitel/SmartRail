package SmartRail;

public class Message
{
  private String direction;
  private String action;

  public Message (String dir, String act)
  {
    direction = dir;
    action = act;
  }

  public String getAction()
  {
    return action;
  }

  public String getDirection()
  {
    return direction;
  }
}
