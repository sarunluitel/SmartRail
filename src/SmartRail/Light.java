package SmartRail;

public class Light extends Thread implements Component
{
  private String leftLight;
  private String rightLight;

  // next component will be null if light is red.
  private Component rightComponent;
  // Left component has pointer to where the light is.
  private Component leftComponent;

  public Light(Component leftComponent, Component rightComponent)
  {
    this.leftComponent=leftComponent;
    this.rightComponent=rightComponent;
    this.leftLight="red";
    this.rightLight="red";
  }

  @Override
  public void run()
  {
    synchronized (this)
    {
      while(true)
      {
        try
        {
          Thread.sleep(2000);
          leftLight = "green";
          rightLight = "red";
          Thread.sleep(2000);
          leftLight = "red";
          rightLight = "green";
        } catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }
    }
  }

  @Override
  public void acceptMessage(String message)
  {
    //return null;
  }

  @Override
  public boolean findPath(Component c, String dir)
  {
    return false;
  }

  @Override
  public Component nextComponent(String direction)
  {
    if(direction.equals("right") && leftLight.equals("green"))
    {
      // green light facing left allows trains to move right
      return rightComponent;
    }
    if(direction.equals("left") && rightLight.equals("green"))
    {
      return leftComponent;
    }
    return null;
  }
  public String getComponentName()
  {
    return "light at track : "+this.leftComponent.getComponentName();
  }
}
