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


  Train(Station Destination, Station spawnStation)
  {
    totalTrains++;
    this.trainID = totalTrains;
    this.Destination = Destination;// this should be a pointer to a station
    this.spawnStation = spawnStation;
    this.currentComponent=spawnStation;
  }

  @Override
  public void run()
  {
    while (this.Destination != this.currentComponent)
    {
      move();
    }

    System.out.println("Arrived at " + this.Destination.getStationName());

  }

  private void move()
  {
    synchronized (this)
    {
      System.out.println("chu chu chu chu");

      if(currentComponent instanceof Station)
      {
        System.out.println("All Aboard leaving from !!!!!" + this.spawnStation.getStationName());
      }


        this.setCurrentComponent(this.currentComponent.nextComponent("right"));
        System.out.println("Rolling down track " + this.currentComponent);
        System.out.println();

      try
      {
        Thread.sleep(2000);
      } catch (InterruptedException e)
      {
        e.printStackTrace();
      }
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
