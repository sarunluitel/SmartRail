package SmartRail;

import java.util.LinkedList;

public class Light extends Thread implements Component
{
  private String leftLight = "";
  private String rightLight = "";

  // next component will be null if light is red.
  private Track rightTrack;
  // Left component has pointer to where the light is.
  private Track leftTrack;
  private LinkedList<Message> messages = new LinkedList<>();
  private boolean secured = false;
  private Train train;


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
    train = t;
  }

  @Override
  public void trainLeaving()
  {
    secured = false;
    train = null;
  }

  @Override
  public void run()
  {
    while (true)
    {
      synchronized (this)
      {
        if (messages.isEmpty())
        {
          try
          {

            wait();
          } catch (InterruptedException ex)
          {
            //Print
          }
        } else
        {
          String action = messages.getFirst().getAction();
          String direction = messages.getFirst().getDirection();
          LinkedList<Component> target = messages.getFirst().getTarget();
          if (action.equalsIgnoreCase("findpath"))
          {
            findPath(target.get(0), direction);
            messages.remove();
            notifyAll();
          } else if (action.equalsIgnoreCase("returnpath"))
          {
            returnPath(messages.getFirst());
            messages.remove();
            notifyAll();
          } else if (action.equalsIgnoreCase("securepath"))
          {
            securePath(messages.getFirst());
          } else if (action.equalsIgnoreCase("readyfortrain"))
          {
            readyForTrain(messages.getFirst());

          } else if (action.equalsIgnoreCase("couldnotsecure"))
          {
            couldNotSecure(messages.getFirst());
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
    System.out.println("Light message");
    messages.add(message);
    notifyAll();
  }

  public synchronized boolean findPath(Component c, String dir)
  {
    LinkedList<Component> targetComponent = new LinkedList<>();
    targetComponent.add(c);
    if (dir.equalsIgnoreCase("right"))
    {
      rightTrack.acceptMessage(new Message(dir, "findpath", targetComponent, this));
      return true;
    } else if (dir.equalsIgnoreCase("left"))
    {
      leftTrack.acceptMessage(new Message(dir, "findpath", targetComponent, this));
      return true;
    }

    return false;
  }

  @Override
  public synchronized boolean returnPath(Message m)
  {
    String dir = m.getDirection();

    if (!m.getTarget().isEmpty())
    {
      m.getTarget().add(this);
    }

    if (dir.equalsIgnoreCase("left"))
    {
      m.setSender(this);
      leftTrack.acceptMessage(m);
    } else
    {
      m.setSender(this);
      rightTrack.acceptMessage(m);
    }

    return true;
  }

  @Override
  public synchronized boolean securePath(Message m)
  {
    String dir = m.getDirection();
    if (secured == true)
    {
      if(dir.equalsIgnoreCase("right"))
      {
        leftTrack.acceptMessage(new Message("left", "couldnotsecure", new LinkedList<>(), this));
      }
      else
      {
        rightTrack.acceptMessage(new Message("right", "couldnotsecure", new LinkedList<>(), this));
      }
      messages.remove();
      return false;
    }

    secured = true;
    m.getTarget().remove(m.getTarget().size() - 1);
    m.setSender(this);
    if (dir.equalsIgnoreCase("right"))
    {
      leftLight = "green";
      rightLight = "red";
      rightTrack.acceptMessage(m);
    } else
    {
      leftLight = "red";
      rightLight = "green";
      leftTrack.acceptMessage(m);
    }
    messages.remove();

    return true;

  }

  @Override
  public synchronized boolean readyForTrain(Message m)
  {
    String dir = m.getDirection();

    m.setSender(this);
    if (dir.equalsIgnoreCase("left"))
    {

      leftTrack.acceptMessage(m);
    } else
    {

      rightTrack.acceptMessage(m);
    }
    messages.remove();
    return true;
  }

  @Override
  public synchronized boolean couldNotSecure(Message m)
  {
    secured = false;
    String dir = m.getDirection();
    m.setSender(this);
    if(dir.equalsIgnoreCase("right"))
    {
      rightTrack.acceptMessage(m);
    }
    else
    {
      leftTrack.acceptMessage(m);
    }
    messages.remove();
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
