/************************************
 @author Sarun Luitel
 ************************************/

package SmartRail;

import com.sun.org.apache.xpath.internal.SourceTree;

// Being edited by sarun.
public class Train extends Thread
{
  private static int totalTrains = 0;

  private int trainID;
  private Station Destination;// change string to Station when we define station
  private Component currentComponent; // Train can be currently at station, light switch or track.
  private Station spawnStation; // change string to Station when we define station


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
    while (this.Destination != this.currentComponent)
    {
      move();
    }

    System.out.println("Train " + trainID+" Arrived at " + this.currentComponent.getComponentName());

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


        while (this.currentComponent.nextComponent("right") == null)
        {
          System.out.println("train " + trainID + " Waiting on red light");
          Thread.sleep(1000);
        }

        this.setCurrentComponent(this.currentComponent.nextComponent("right"));


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


}
