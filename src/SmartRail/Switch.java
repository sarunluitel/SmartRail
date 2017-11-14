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
  private volatile boolean returnPath = false;
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
      waitingForResponse = false;
      returnPath = true;
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
          String action;
          String direction;
          LinkedList<Component> target;
          if (!returnPath)
          {
            action = messages.getFirst().getAction();
            direction = messages.getFirst().getDirection();
            target = messages.getFirst().getTarget();
          }
          else
          {
            action = messages.get(1).getAction();
            direction = messages.get(1).getDirection();
            target = messages.get(1).getTarget();
          }

          if (action.equalsIgnoreCase("findpath"))
          {
            returnComponent = messages.getFirst().getSender();
            findPath(target.get(0), direction);
            System.out.println("running");
            notifyAll();
          }
          else if (action.equalsIgnoreCase("returnpath"))
          {

            returnPath(messages.get(1));
            //messages.remove();


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
        try
        {
          wait();
        } catch (InterruptedException ex) {}
        if(!messages.get(1).getTarget().isEmpty())
        {
          return true;
        }
        else
        {
          messages.remove(1);
        }
      }
      //multiple options
      else
      {
        if(upTrack != null)
        {
          System.out.println("up");
          upTrack.acceptMessage(new Message(dir, "findpath", compList, this));
          try
          {
            wait();
          } catch (InterruptedException ex) {}
          if(!messages.get(1).getTarget().isEmpty())
          {
            return true;
          }
          else
          {
            messages.remove(1);
          }
          System.out.println("up");
        }
        if(right != null)
        {
          System.out.println("right");
          right.acceptMessage(new Message(dir, "findpath", compList, this));
          try
          {
            wait();
          } catch (InterruptedException ex) {}
          if(!messages.get(1).getTarget().isEmpty())
          {
            return true;
          }
          else
          {
            messages.remove(1);
          }
        }
        if(down != null)
        {
          System.out.println("down");
          down.acceptMessage(new Message(dir, "findpath", compList, this));
          try
          {
            wait();
          } catch (InterruptedException ex) {}
          if(!messages.get(1).getTarget().isEmpty())
          {
            return true;
          }
          else
          {
            messages.remove(1);
          }
          System.out.println("not null down");
        }
      }
    }
    //For Direction left
    else
    {
      if(!isLeft)
      {
        left.acceptMessage(new Message(dir, "findpath", compList, this));
      }
      else
      {
        if(upTrack != null)
        {
          upTrack.acceptMessage(new Message(dir, "findpath", compList, this));
          try
          {
            wait();
          } catch (InterruptedException ex) {}
          if(!messages.get(1).getTarget().isEmpty())
          {
            return true;
          }
          System.out.println("up");
        }
        if(right != null)
        {
          right.acceptMessage(new Message(dir, "findpath", compList, this));
          try
          {
            wait();
          } catch (InterruptedException ex) {}
          if(!messages.get(1).getTarget().isEmpty())
          {
            return true;
          }
        }
        if(down != null)
        {
          down.acceptMessage(new Message(dir, "findpath", compList, this));
          try
          {
            wait();
          } catch (InterruptedException ex) {}
          if(!messages.get(1).getTarget().isEmpty())
          {
            return true;
          }
          System.out.println("down");
        }
      }
    }



    System.out.println("Done waiting");
    return false;
  }

  @Override
  public synchronized boolean returnPath(Message m)
  {
    if(!m.getTarget().isEmpty())
    {
      m.getTarget().add(this);
    }
    m.setSender(this);
    returnComponent.acceptMessage(m);
    messages.remove();
    messages.remove();
    waitingForResponse = false;
    return false;
  }

  @Override
  public Component nextComponent(String direction)
  {
    return right;
  }

  @Override
  public void trainLeaving()
  {

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
