/************************************
 @author Vincent Huber
 This class is used to run a track for smartRail

 ************************************/

package SmartRail;


import java.util.ArrayList;
import java.util.LinkedList;

public class Track extends Thread implements Component
{
  ArrayList<Component> neighbors;
  String name;
  private static int totalTracks = 0;
  private Component left = null;
  private Component right = null;
  private LinkedList<Message> messages = new LinkedList<>();
  private Train trainOnTrack = null;
  private boolean secured = false;

  public Track()
  {
    totalTracks++;
    this.name = "Track " + totalTracks;
  }

  public Track(Component left, Component right)
  {
    totalTracks++;
    this.name = "Track " + totalTracks;
    this.left = left;
    this.right = right;
  }

  //Getter methods
  public Component getLeft()
  {
    return left;
  }

  public Component getRight()
  {
    return right;
  }

  public boolean hasTrain()
  {
    if(trainOnTrack != null)
    {
      return true;
    }
    return false;
  }

  public boolean isSecured()
  {
    return secured;
  }

  //Setters for Data
  public void setNeighbors(Component c, String dir)
  {
    if (dir.equalsIgnoreCase("right"))
    {

      right = c;

    } else
    {
      if (left == null)
      {
        left = c;
      }
    }
  }

  /* This method is used by a train on the track so the train
   * can give its own reference to the track
   * takes a Train parameter and returns nothing
   */
  @Override
  public void getTrainId(Train t)
  {
    trainOnTrack = t;
    //Set train reference
  }

  /* acceptMessage is a method used by the components to read in a message
  * the message received is added to the end of the queue (linkedlist) of
  * messages
  * Message parameter, no return
  */
  public synchronized void acceptMessage(Message message)
  {
    System.out.println("Recieved message by track: " + name);
    messages.add(message);
    notifyAll();
    //return message;
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
      right.acceptMessage(new Message(dir, "findpath", targetComponent, this));
      return true;
    } else if (dir.equalsIgnoreCase("left"))
    {
      left.acceptMessage(new Message(dir, "findpath", targetComponent, this));
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
    //LinkedList<Component> pathList = m.getTarget();
    if (!m.getTarget().isEmpty())
    {
      m.getTarget().add(this);
    }
    m.setSender(this);
    if (dir.equalsIgnoreCase("left"))
    {
      left.acceptMessage(m);
    } else
    {
      right.acceptMessage(m);
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
        left.acceptMessage(new Message("left", "couldnotsecure", new LinkedList<>(), this));
      }
      else
      {
        right.acceptMessage(new Message("right", "couldnotsecure", new LinkedList<>(), this));
      }
      messages.remove();
      return false;
    }

    secured = true;
    System.out.println(m.getTarget().size());
    m.getTarget().remove(m.getTarget().size() - 1);
    m.setSender(this);
    if (dir.equalsIgnoreCase("right"))
    {

      right.acceptMessage(m);
    } else
    {
      left.acceptMessage(m);
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
      left.acceptMessage(m);
    } else
    {
      right.acceptMessage(m);
    }
    messages.remove();
    return false;
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
      right.acceptMessage(m);
    }
    else
    {
      left.acceptMessage(m);
    }
    messages.remove();
    return false;
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
          }
          else if (action.equalsIgnoreCase("returnpath"))
          {
            returnPath(messages.getFirst());
            messages.remove();
            notifyAll();
          }
          else if (action.equalsIgnoreCase("securepath"))
          {
            securePath(messages.getFirst());
            //messages.remove();

          }
          else if (action.equalsIgnoreCase("readyfortrain"))
          {
            readyForTrain(messages.getFirst());
          }
          else if (action.equalsIgnoreCase("couldnotsecure"))
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

  /* This method is used by the Train to determine the next component in the path
   * This method takes in a String parameter and returns the correct next Component
   */
  @Override
  public Component nextComponent(String Direction)
  {
    if (Direction.equalsIgnoreCase("right")) return right;
    return left;
  }

  /* Code used when a train is leaving a track
   * No parameters and no return
   * this method also frees up the track to be secured for another component
   */
  @Override
  public void trainLeaving()
  {
    secured = false;
    trainOnTrack = null;
  }

  /* returns a string name of the component
   * no parameters
   */
  public String getComponentName()
  {
    return this.name;
  }
}
