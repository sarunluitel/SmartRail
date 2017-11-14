package SmartRail;

import sun.awt.image.ImageWatched;

import java.util.LinkedList;

public class Switch extends Thread implements Component
{

  private final boolean isLeft;
  private Track upTrack = null;
  private Track down = null;
  private Track right = null;
  private Track left = null;
  private boolean waitingForResponse = false;
  private LinkedList<Message> messages = new LinkedList<>();
  private Component returnComponent = null;

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

  public boolean getIsLeft()
  {
    return isLeft;
  }


  @Override
  public synchronized void acceptMessage(Message message)
  {
    System.out.println("Switch has message");

    if(message.getAction().equalsIgnoreCase("returnpath"))
    {
      System.out.println("returned");
      messages.add(1, message);
      notifyAll();
    }
    else
    {
      messages.add(message);
    }
    if(messages.size() == 1)
    {
      //System.out.println("Size 1");
      notifyAll();
    }

  }



  @Override
  public void run()
  {
    while(true)
    {
      synchronized (this)
      {
        if (messages.isEmpty() || waitingForResponse)
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
            returnComponent = messages.getFirst().getSender();
            findPath(target.get(0), direction);
            System.out.println("running");
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
  public synchronized boolean findPath(Component c, String dir)
  {
    waitingForResponse = true;
    LinkedList<Component> compList = new LinkedList<>();
    compList.add(c);
    //For Direction right
    if(dir.equalsIgnoreCase("right"))
    {
      //If dir = right and isLeft, only one track option
      if(isLeft)
      {
        right.acceptMessage(new Message(dir, "findpath", compList, this));
      }
      //multiple options
      else
      {
        if(right != null)
        {
          right.acceptMessage(new Message(dir, "findpath", compList, this));
          try
          {
            wait();
          } catch (InterruptedException ex)
          {

          }
          if(!messages.get(1).getTarget().isEmpty())
          {
            return true;
          }
        }
      }
    }
    //For Direction left
    else
    {
      if(isLeft)
      {
        if(left != null)
        {
          try
          {
            wait();
          } catch (InterruptedException ex)
          {

          }
        }
      }
      else
      {
        if(right != null)
        {
          try
          {
            wait();
          } catch (InterruptedException ex)
          {

          }
        }
      }
    }



    System.out.println("Done waiting");
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
