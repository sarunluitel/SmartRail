package SmartRail.JavafxRes;

import SmartRail.Train;
import javafx.scene.image.Image;

import java.util.ArrayList;

// Standard Size of train 25:88
// remember to photoshop a track in a way where it get alined like []==o==[]

public class TrainView
{
  private ArrayList<Train> trainList = new ArrayList<>();
  private static TrainView instance = null;

  private TrainView(){}

  public static TrainView getInstance()
  {
    if(instance==null) instance= new TrainView();
    return  instance;
  }

  public void addTrain(Train t1)
  {
    trainList.add(t1);
  }

  ArrayList getList()
  {
    return trainList;
  }

}
