package SmartRail;


public class Light extends Thread implements Component
{
  private String leftLight;
  private String rightLight;

  // next component will be null if light is red.
  private Component rightComponent;
  // Left component has pointer to where the light is.
  private Component leftComponent;

  //Setters for data types.

  public void setLeftComponent(Component leftComponent)
  {
    this.leftComponent = leftComponent;
  }

  public void setRightComponent(Component rightComponent)
  {
    this.rightComponent = rightComponent;
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
  public void run()
  {
    // make light talk to other components next to it
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
    if (direction.equals("right") && leftLight.equals("green"))
    {
      // green light facing left allows trains to move right
      return rightComponent;
    }
    if (direction.equals("left") && rightLight.equals("green"))
    {
      return leftComponent;
    }
    return null;
  }

  public String getComponentName()
  {
    return "light at track : " + this.leftComponent.getComponentName();
  }
}
