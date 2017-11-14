package SmartRail;

import java.util.LinkedList;
import java.util.Queue;

public class Switch extends Thread implements Component
{

  private final boolean isLeft;
  private Track upTrack;
  private Track down;
  private Track right;
  private Track left;
  private boolean waitingForResponse = false;
  private LinkedList<Message> messages = new LinkedList<>();

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
  public void run()
  {
    while(true)
    {
      synchronized (this)
      {
        if (messages.isEmpty())
        {
          try {
            wait();
          } catch (InterruptedException ex) {
            //Print
          }
        }
        else
        {
          String action = messages.getFirst().getAction();
          String direction = messages.getFirst().getDirection();
          LinkedList<Component> target = messages.getFirst().getTarget();
          if (action.equalsIgnoreCase("findpath"))
          {
            findPath(target.get(0), direction);
            messages.remove();
            notifyAll();
          }
          else if (action.equalsIgnoreCase("returnpath"))
          {
            returnPath(messages.getFirst());
            messages.remove();
            notifyAll();
          }
          else
          {
            System.out.println(action);
            messages.remove();
          }

        }
      }
    }
  }



  @Override
  public synchronized void acceptMessage(Message message)
  {
    System.out.println("Switch has message");
    messages.add(message);
    if(messages.size() == 1)
    {
      notifyAll();
    }

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
