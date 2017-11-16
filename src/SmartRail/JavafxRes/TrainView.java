package SmartRail.JavafxRes;

import SmartRail.Train;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Standard Size of train 25:88
// remember to photoshop a track in a way where it get alined like []==o==[]

public class TrainView
{
  private HashMap<String, Train> trainList = new HashMap<>();
  private static TrainView instance = null;

  private TrainView()
  {
  }

  public static TrainView getInstance()
  {
    if (instance == null) instance = new TrainView();
    return instance;
  }

  void addTrain(String trainID, Train t1)
  {
    trainList.put(trainID, t1);
  }

  HashMap<String, Train> getList()
  {
    return trainList;
  }

}
