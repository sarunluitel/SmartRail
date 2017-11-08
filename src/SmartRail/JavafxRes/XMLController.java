package SmartRail.JavafxRes;

import SmartRail.Light;
import SmartRail.Station;
import SmartRail.Track;
import SmartRail.Train;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class XMLController extends AnimationTimer
{
  private final int DISTANCE = 60;
  private int frameCounter = 0;
  @FXML
  private Canvas canvas;
  private GraphicsContext gc;

  @FXML
  void initialize()
  {
    ArrayList<ArrayList> entireMap;
    gc = canvas.getGraphicsContext2D();

    entireMap = MapView.getInstance().getEntireMap();


    for (int i = 0; i < entireMap.size(); i++)
    {
      ArrayList temp = entireMap.get(i);

      for (int j = 0; j < temp.size(); j++)
      {

        if (temp.get(j) instanceof Station)
        {
          gc.fillOval(DISTANCE * (j + 1), DISTANCE * (i + 2), 50, 50);
        }

        if (temp.get(j) instanceof Track)
        {

          gc.fillRect(DISTANCE * (j + 1), DISTANCE * (i + 2), 50, 15);
        }

        if (temp.get(j) instanceof Light)
        {
          gc.fillRect(DISTANCE * (j + 1), DISTANCE * (i + 2), 50, 15);
        }

      }
    }
    this.start();


  }

  private ArrayList<Train> trainList = TrainView.getInstance().getList();

  @Override
  public void handle(long now)
  {
   // frameCounter++;

   // if (frameCounter == 60)
    {
      for (Train t :
          trainList)
      {

        gc.fillRect((t.getXPos()) * DISTANCE, (t.getYPos() + 1.5) * DISTANCE, 30, 30);


      }


    }
  }
}





