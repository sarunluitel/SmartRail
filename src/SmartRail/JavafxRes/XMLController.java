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
  private ArrayList<ArrayList> entireMap;
  final int DISTANCE=60;

  @FXML
  private Canvas canvas;
  private GraphicsContext gc = canvas.getGraphicsContext2D();

  @FXML
  void initialize()
  {

    entireMap = MapView.getInstance().getEntireMap();


    for (int i = 0; i < entireMap.size(); i++)
    {
      ArrayList temp = entireMap.get(i);

      for (int j = 0; j < temp.size(); j++)
      {

        if (temp.get(j) instanceof Station)
        {
          gc.fillOval(DISTANCE * (j+1), DISTANCE * (i+2), 50, 50);
        }

        if (temp.get(j) instanceof Track)
        {

          gc.fillRect(DISTANCE * (j+1), DISTANCE * (i+2), 50, 15);
        }

        if (temp.get(j) instanceof Light)
        {
          gc.fillRect(DISTANCE * (j+1), DISTANCE * (i+2), 50, 15);
        }

      }
    }
    this.start();

  }

  private TrainView trainView=TrainView.getInstance();
  @Override
  public void handle(long now)
  {
    Train t1 = (Train) trainView.getList().get(0);
    gc.fillOval(t1.getxPos(), t1.getyPos(), 50, 50);


  }
}





