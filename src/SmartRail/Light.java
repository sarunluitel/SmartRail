package SmartRail;

public class Light extends Thread implements Component
{
  private String leftLight;
  private String rightLight;

  // next component will be null if light is red.
  private Track rightTrack;
  // Left component has pointer to where the light is.
  private Track leftTrack;
  private Message message;

  //Setters for data types.

  public void setLeftTrack(Track leftTrack)
  {
    this.leftTrack = leftTrack;
  }

  public void setRightTrack(Track rightTrack)
  {
    this.rightTrack = rightTrack;
  }

  public void setLeftLight(String leftLight)
  {
    this.leftLight = leftLight;
  }

  public void setRightLight(String rightLight)
  {
    this.rightLight = rightLight;
  }

  @Override
  public void getTrainId(Train t)
  {

  }

  @Override
  public void run()
  {
    // make light talk to other components next to it
  }

  @Override
  public void acceptMessage(Message message)
  {
    this.message = message;
  }

  @Override
  public boolean findPath(Component c, String dir)
  {
    return false;
  }

  @Override
  public boolean returnPath(Message m)
  {
    return false;
  }

  @Override
  public Component nextComponent(String direction)
  {
    if (direction.equals("right") && leftLight.equals("green"))
    {
      // green light facing left allows trains to move right
      return rightTrack;
    }
    if (direction.equals("left") && rightLight.equals("green"))
    {
      return leftTrack;
    }
    return null;
  }

  public String getComponentName()
  {
    return "light at track : " + this.leftTrack.getComponentName();
  }
}
