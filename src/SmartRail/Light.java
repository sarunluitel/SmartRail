package SmartRail;

import java.util.LinkedList;

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
    while(true)
    {
      synchronized (this)
      {
        if (message == null)
        {
          try {
            wait();
          } catch (InterruptedException ex) {
            //Print
          }
        }
        else
        {
          String action = message.getAction();
          String direction = message.getDirection();
          LinkedList<Component> target = message.getTarget();
          if (action.equalsIgnoreCase("findpath"))
          {
            findPath(target.get(0), direction);
            message = null;
            notifyAll();
          }
          else if (action.equalsIgnoreCase("returnpath"))
          {
            returnPath(message);
            message = null;
            notifyAll();
          }
          else
          {
            System.out.println(action);
            message = null;
          }

        }
      }
    }
  }

  @Override
  public synchronized void acceptMessage(Message message)
  {
    System.out.println("Light message");
    this.message = message;
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
    }
    else if (dir.equalsIgnoreCase("left"))
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
    LinkedList<Component> pathList = m.getTarget();
    if(!pathList.isEmpty())
    {
      pathList.add(this);
    }

    if(dir.equalsIgnoreCase("left"))
    {
      m.setSender(this);
      leftTrack.acceptMessage(m);
    }
    else
    {
      m.setSender(this);
      rightTrack.acceptMessage(m);
    }

    return true;
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
