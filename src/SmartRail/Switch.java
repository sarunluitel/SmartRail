package SmartRail;

public class Switch extends Thread implements Component
{

  private final boolean isLeft;
  private Track upTrack;
  private Track down;
  private Track right;
  private Track left;

  Switch(boolean isLeft){
    this.isLeft=isLeft;
  }

  public Track getUpTrack()
  {

    return upTrack;
  }

  public void setUpTrack(Track upTrack)
  {
    this.upTrack = upTrack;
  }

  public Track getDown()
  {
    return down;
  }

  public void setDown(Track down)
  {
    this.down = down;
  }

  public Track getRight()
  {
    return right;
  }

  public void setRight(Track right)
  {
    this.right = right;
  }

  public Track getLeft()
  {
    return left;
  }

  public void setLeft(Track left)
  {
    this.left = left;
  }






  @Override
  public void run(){}



  @Override
  public void acceptMessage(Message message)
  {

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
    return null;
  }

  @Override
  public void getTrainId(Train t)
  {

  }

  @Override
  public String getComponentName()
  {
    return null;
  }
}
