package SmartRail.JavafxRes;

import SmartRail.Light;
import SmartRail.Station;
import SmartRail.Track;
import SmartRail.Train;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class XMLController extends AnimationTimer
{
  private final int DISTANCE = 60;
  private int frameCounter = 0;
  @FXML
  private Canvas canvas;
  private GraphicsContext gc;
  private final Image trackImage = new Image(getClass().getResourceAsStream("GUI_resources/track.png"));

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

    gc.drawImage(trackImage, 0, 0);
   // gc.drawImage(trackView.getImage(), 0, 0);
    this.start();


  }

  private ArrayList<Train> trainList = TrainView.getInstance().getList();


  private double past3FrameX = 0;
  private double past3FrameY = 0;
  private TrackView trackView;

  @Override
  public void handle(long now)
  {
    frameCounter++;

    // if (frameCounter == 60)
    {
      for (Train t :
          trainList)
      {

        past3FrameX = (t.getXPos()) * DISTANCE + frameCounter;
        past3FrameY = (t.getYPos() + 1.5) * DISTANCE;

        if (frameCounter % 5 == 0)
        {
          gc.clearRect(past3FrameX, past3FrameY, 30, 30);
        }

      }


    }
  }
}





