package SmartRail;

public class Train implements  Component, Runnable
{

  private String color;
  private String Destination;


  @Override
  public String acceptMessage(String message)
  {
    return null;
  }

  @Override
  public void run()
  {

  }
}
