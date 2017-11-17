/************************************
 @author Sarun Luitel and Vincent Huber
 This class is used to run a train in the smartRail
 ************************************/

package SmartRail;



import java.util.LinkedList;


public class Train extends Thread
{
  private static int totalTrains = 0;

  private int trainID;
  private Station destination;// change string to Station when we define station
  private Component currentComponent; // Train can be currently at station, light switch or track.
  private Station spawnStation; // change string to Station when we define station
  private int xPos = 0; // increase as train moves forward.
  private int yPos = 0; // increase as train moves down a track
  private volatile boolean waiting = false;
  private boolean goodPath = true;
  private volatile LinkedList<Component> pathList = new LinkedList<>();
  private String direction;
  private String trainName;
  private boolean waitForSecure;

  //Constructor for train
  public Train(Station destination, Station spawnStation, int x, int y)
  {
    this.trainID = totalTrains;
    this.trainName = "Train " + trainID;
    this.destination = destination;// this should be a pointer to a station
    this.spawnStation = spawnStation;
    this.currentComponent = spawnStation;
    currentComponent.getTrainId(this);
    this.xPos = x;
    this.yPos = y;// this needs to come from the GUI Click
    totalTrains++;
    direction = ((Station) currentComponent).directionOut();
  }

  //String return method used for display purposes
  public String getTrainName()
  {
    return trainName;
  }

  /* Accepts a message on whether a path has been found or secured
   * no return
   */
  public synchronized void acceptMessage(Message m)
  {
    waiting = false;
    if(waitForSecure)
    {
      if(m.getTarget().isEmpty())
      {
        waiting = true;
      }
      notifyAll();
      return;

    }
    else if (m.getTarget().isEmpty())
    {
      System.out.println("pathlist empty");
      goodPath = false;
    } else if (pathList.isEmpty())
    {
      pathList = m.getTarget();
      System.out.println("Message has" + pathList.getFirst().getComponentName());
    }
    notifyAll();
  }

  /* The run method for train finds a path, then tries to secure the path
   * if no path is found the train dies and if the path cannot be secured, it
   * keeps trying after a sleep period
   */
  @Override
  public void run()
  {
    //pathFind to Destination
    //send message to initial Station
    //spawnStation.acceptMessage(MESSAGE);
    //notifyAll();

    LinkedList<Component> compList = new LinkedList<>();
    compList.add(destination);
    currentComponent.getTrainId(this);
    System.out.println(direction);
    currentComponent.acceptMessage(new Message(direction, "findpath", compList, currentComponent));
    waiting = true;

    synchronized (this)
    {
      if (waiting)
      {
        try
        {
          wait();
        } catch (InterruptedException e)
        {
          System.out.println("Interrupted");
        }
      }
      if (!goodPath)
      {
        return;
      }
      //secure path
      waiting = true;



      while(waiting)
      {
        waitForSecure = true;
        System.out.println(pathList.size());
        LinkedList<Component> messList = new LinkedList<>();
        for(int i = 0; i < pathList.size(); i++)
        {
          messList.add(i, pathList.get(i));
        }
        currentComponent.acceptMessage(new Message(direction, "securepath", messList, currentComponent));
        waiting = true;
        if (waiting) {
          try {
            wait();
          } catch (InterruptedException e) {
            System.out.println("Interrupted");
          }
        }
        try
        {

          Thread.sleep(2500);
        } catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }


    }
    while (this.destination != this.currentComponent)
    {

      //notifyAll();
      //System.out.println("sent message");

      move();
    }
    currentComponent.trainLeaving();
    System.out.println("Train " + trainID + " Arrived at " + this.currentComponent.getComponentName());
    return;


  }

  /* Moves the train once path has been secured
   * no return
   */
  private synchronized void move()
  {
    try
    {
      synchronized (this)
      {
        System.out.println("!!!!!chu chu chu chu!!!!!!!! train!!!" + trainID + "\n");


        if (this.currentComponent instanceof Station)
        {
          System.out.println("All Aboard train " + trainID + " leaving from " + this.spawnStation.getComponentName());
          xPos++;
          System.out.println("Current XPOS " + xPos);

        }


        while (this.currentComponent.nextComponent(direction) == null)
        {
          System.out.println("train " + trainID + " Waiting on red light");
          Thread.sleep(1000);
        }
        currentComponent.trainLeaving();
        this.setCurrentComponent(this.currentComponent.nextComponent(direction));
        currentComponent.getTrainId(this);

        System.out.println("train " + trainID + " Rolling down track " + this.currentComponent.getComponentName());
        if (currentComponent instanceof Track)
        {
          if(direction.equalsIgnoreCase("right"))
          {
            xPos++;
          }
          else
          {
            xPos--;
          }
        }
        else if (currentComponent instanceof Switch)
        {
          String moveDir = ((Switch) currentComponent).directionForTrain();
          if (moveDir.equalsIgnoreCase("up"))
          {
            yPos--;
            //xPos will be updated for track
          } else if (moveDir.equalsIgnoreCase("down"))
          {
            yPos++;
          }
        }
        System.out.println("Current XPOS " + xPos);
        System.out.println();

        try
        {
          Thread.sleep(2000);
        } catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }
    } catch (Exception e)
    {
    }
  }

  /* Sets currentComponent to the correct component
   * No return
   */
  public void setCurrentComponent(Component curComp)
  {
    this.currentComponent = curComp;
  }

  /* Gives GUI the xPos for display purposes
   * returns an int
   */
  public int getXPos()
  {
    return xPos;
  }

  /* Gives GUI the yPos for display purposes
     * returns an int
     */
  public int getYPos()
  {
    return yPos;
  }

  /* Gives GUI the direction for display purposes
   * returns an int
   */
  public String getDirection()
  {
    return direction;
  }

}
