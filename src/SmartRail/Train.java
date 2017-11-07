/************************************
 @author Sarun Luitel
 ************************************/

package SmartRail;


// Being edited by sarun.
public class Train extends Thread
{
  private static int totalTrains = 0;

  private int trainID;
  private Station Destination;// change string to Station when we define station
  private Component currentComponent; // Train can be currently at station, light switch or track.
  private Station spawnStation; // change string to Station when we define station
  private int xPos=0; // increase as train moves forward.
  private int yPos=0; // increase as train moves down a track



  Train(Station Destination, Station spawnStation)
  {
    totalTrains++;
    this.trainID = totalTrains;
    this.Destination = Destination;// this should be a pointer to a station
    this.spawnStation = spawnStation;
    this.currentComponent = spawnStation;
  }

  @Override
  public void run()
  {
    //pathFind to Destination
    //send message to initial Station
    //spawnStation.acceptMessage(MESSAGE);
    //notifyAll();

    while (this.Destination != this.currentComponent)
    {
      move();
    }

    System.out.println("Train " + trainID + " Arrived at " + this.currentComponent.getComponentName());

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

        }


        while (this.currentComponent.nextComponent("left") == null)
        {
          System.out.println("train " + trainID + " Waiting on red light");
          Thread.sleep(1000);
        }

        this.setCurrentComponent(this.currentComponent.nextComponent("left"));


        System.out.println("train " + trainID + " Rolling down track " + this.currentComponent.getComponentName());
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

  public int getTrainID()
  {
    return trainID;
  }

  public void setCurrentComponent(Component curComp)
  {
    this.currentComponent = curComp;
  }

  public int getxPos()
  {
    return xPos;
  }


  public int getyPos()
  {
    return yPos;
  }
}
