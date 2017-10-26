package SmartRail;

public class Train implements  Component, Runnable
{
  private static int totalTrains=0;
  private int trainID;
 // private String color;
  private Station Destination;// change string to Station when we define station
  private Track currentTrack;
  private Station spawnStation; // change string to Station when we define station

  // made this private to spawn trains only with attributes;
  private Train(){}

  Train(int TrainID, Station Destination, Station spawnStation)
  {

   totalTrains++;
   this.trainID=TrainID;
   this.Destination= Destination;// this should be a pointer to a station
   this.spawnStation= spawnStation;
  }

  @Override
  public String acceptMessage(String message)
  {
    return null;
  }

  @Override
  public void run()
  {

  }
}
