/************************************
 @author Vincent Huber
 This class is used to run a light for smartRail


 ************************************/

package SmartRail;

import java.util.LinkedList;

public class Light extends Thread implements Component
{
  private static int totalLights = 0;
  private String leftLight = "";
  private String rightLight = "";

  // next component will be null if light is red.
  private Track rightTrack;
  // Left component has pointer to where the light is.
  private Track leftTrack;
  private LinkedList<Message> messages = new LinkedList<>();
  private boolean secured = false;
  private String lightName;
  private Train train;

  Light()
  {
    this.lightName = "Light " + totalLights;
    totalLights++;
  }

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

  /* This method is used by a train on the light so the train
   * can give its own reference to the light
   * takes a Train parameter and returns nothing
   */
  @Override
  public void getTrainId(Train t)
  {
    train = t;
  }

  /* Code used when a train is leaving a light
   * No parameters and no return
   * this method also frees up the light to be secured for another component
   */
  @Override
  public void trainLeaving()
  {

    secured = false;
    train = null;
  }

  /* The track processes the message and correctly generates a return message
   * or uses the correct method to process the message
   * No parameters or return
   */
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

  /* acceptMessage is a method used by the components to read in a message
  * the message received is added to the end of the queue (linkedlist) of
  * messages
  * Message parameter, no return
  */
  @Override
  public synchronized void acceptMessage(Message message)
  {
    System.out.println("Light message");
    messages.add(message);
    notifyAll();
  }

  /* This method is used to send a findPath message along to a correct station
  * This method has a component and a String (direction) for parameters
  * returns a boolean for processing purposes
  */
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

  /* This method is used to send a returnPath message along to a correct station
 * This method has a component and a String (direction) for parameters
 * returns a boolean for processing purposes
 */
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

  /* This method is used to send a securePath message along to a correct station
 * This method has a component and a String (direction) for parameters
 * returns a boolean for processing purposes
 */
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

  /* This method is used to send a readyfortrain message along to the correct train
 * This method has a component and a String (direction) for parameters
 * returns a boolean for processing purposes
 */
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

  /* This method is used to send a couldnotsecure message along to the correct train
 * This method has a component and a String (direction) for parameters
 * returns a boolean for processing purposes
 */
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

  /* This method is used by the Train to determine the next component in the path
   * This method takes in a String parameter and returns the correct next Component
   * if somehow a train comes to a red light(should not be possible) the train will
   * stop because it will hae a null next track
   */
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

  public String getOrientation()
  {
    if(leftLight.equalsIgnoreCase("green"))
    {
      return "left";
    } else if (leftLight.equalsIgnoreCase("red") && rightLight.equalsIgnoreCase("green"))
    {
      return "right";
    }
    return "empty";
  }

  /* returns a string name of the component
   * no parameters
   */
  public String getComponentName()
  {
    return lightName;
  }
}
