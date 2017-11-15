/************************************
 @author Sarun Luitel
 ************************************/

package SmartRail;


import java.util.LinkedList;


public class Train extends Thread
{
  private static int totalTrains = 0;

  private int trainID;
  private Station Destination;// change string to Station when we define station
  private Component currentComponent; // Train can be currently at station, light switch or track.
  private Station spawnStation; // change string to Station when we define station
  private int xPos = 0; // increase as train moves forward.
  private int yPos = 0; // increase as train moves down a track
  private volatile boolean waiting = false;
  private boolean goodPath = true;
  private LinkedList<Component> pathList = new LinkedList<>();


  Train(Station Destination, Station spawnStation)
  {

    this.trainID = totalTrains;
    this.Destination = Destination;// this should be a pointer to a station
    this.spawnStation = spawnStation;
    this.currentComponent = spawnStation;
    currentComponent.getTrainId(this);
    this.yPos = trainID;// this needs to come from the GUI Click
    totalTrains++;


  }

  public synchronized void acceptMessage(Message m)
  {
    waiting = false;
    if(m.getTarget().isEmpty())
    {
      goodPath = false;
    }
    if(pathList.isEmpty())
    {
      pathList = m.getTarget();
    }
    notifyAll();
  }

  @Override
  public void run()
  {
    //pathFind to Destination
    //send message to initial Station
    //spawnStation.acceptMessage(MESSAGE);
    //notifyAll();

    LinkedList<Component> compList = new LinkedList<>();
    compList.add(Destination);
    currentComponent.acceptMessage(new Message("right", "findpath", compList, currentComponent));
    waiting = true;
    while (this.Destination != this.currentComponent)
    {
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
        if(!goodPath)
        {
          break;
        }
        //secure path

        currentComponent.acceptMessage(new Message("right", "securepath", pathList, currentComponent));
        waiting = true;
      }
      //notifyAll();
      //System.out.println("sent message");

      //move();
    }

    System.out.println("Train " + trainID + " Arrived at " + this.currentComponent.getComponentName());
    return;

  }

  private void move()
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
          System.out.println("Current XPOS "+xPos);

        }


        while (this.currentComponent.nextComponent("right") == null)
        {
          System.out.println("train " + trainID + " Waiting on red light");
          Thread.sleep(1000);
        }

        this.setCurrentComponent(this.currentComponent.nextComponent("right"));


        System.out.println("train " + trainID + " Rolling down track " + this.currentComponent.getComponentName());
        xPos++;
        System.out.println("Current XPOS "+xPos);
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

  public void setCurrentComponent(Component curComp)
  {
    this.currentComponent = curComp;
  }

  public int getXPos()
  {
    return xPos;
  }


  public int getYPos()
  {
    return yPos;
  }

  public int getTrainID(){return this.trainID;}
}
