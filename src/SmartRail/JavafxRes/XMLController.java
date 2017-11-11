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
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class XMLController extends AnimationTimer
{
  private final int DISTANCE = 88;
  private int frameCounter = 0;
  @FXML
  private Canvas canvas;
  private GraphicsContext gc;
  private final Image trackImage = new Image(getClass().getResourceAsStream("GUI_resources/track.png"));
  private final Image trainImage = new Image(getClass().getResourceAsStream("GUI_resources/train.png"));
  @FXML
  private ImageView train1;
  @FXML
  private StackPane stackPane;

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
          if (j == 0)
          {
           // stackPane.getChildren().addAll(canvas, train1);
            train1.setImage(trainImage);
            train1.setX(DISTANCE * (j + 1));
            train1.setY(DISTANCE * (i + 2) - 5);
          }

          // gc.drawImage(trainImage, DISTANCE * (j + 1), DISTANCE * (i + 2) - 5);

        }

        if (temp.get(j) instanceof Track)
        {

          gc.drawImage(trackImage, DISTANCE * (j + 1), DISTANCE * (i + 2));

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
    frameCounter++;
    {
      for (Train t :
          trainList)
      {
        train1.setX(DISTANCE * (t.getXPos() + 1) + frameCounter / 2);
        // gc.drawImage(trainImage, DISTANCE * (t.getXPos() + 1) + frameCounter, DISTANCE * (t.getYPos() + 2) - 5);


      }


    }
  }
}





