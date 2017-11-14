package SmartRail.JavafxRes;

import SmartRail.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class XMLController extends AnimationTimer
{
  private final int DISTANCE = 88;// dictated by the size of tracks and trains. length pixel count
  private int frameCounter = 0;
  @FXML
  private Canvas canvas;
  private GraphicsContext gc;
  private final Image trackImage = new Image(getClass().getResourceAsStream("GUI_resources/track.png"));
  private final Image trainImage = new Image(getClass().getResourceAsStream("GUI_resources/train.png"));
  private final Image stationImage = new Image(getClass().getResourceAsStream("GUI_resources/station.png"));
  private final Image noLight = new Image(getClass().getResourceAsStream("GUI_resources/noLight.png"));
  private final Image leftRed = new Image(getClass().getResourceAsStream("GUI_resources/leftRed.png"));
  private final Image leftGreen = new Image(getClass().getResourceAsStream("GUI_resources/leftGreen.png"));

  private ArrayList<ImageView> trainNCanvas = new ArrayList<>();
  private ArrayList<Train> trainList = TrainView.getInstance().getList();

  @FXML
  private Pane gamePane;

  @FXML
  void initialize()
  {
    ArrayList<ArrayList> entireMap;
    gc = canvas.getGraphicsContext2D();

    entireMap = MapView.getInstance().getEntireMap();

    trainNCanvas.add(0, new ImageView());//temp added at 0 to add canvas later
    for (int i = 0; i < entireMap.size(); i++)
    {
      ArrayList temp = entireMap.get(i);

      for (int j = 0; j < temp.size(); j++)
      {

        if (temp.get(j) instanceof Station)
        {
          gc.drawImage(stationImage, DISTANCE * (j + 1), DISTANCE * (i + 1));

        }

        if (temp.get(j) instanceof Track)
        {
          gc.fillRect(0, 0, 20, 20);
          gc.drawImage(trackImage, DISTANCE * (j + 1), DISTANCE * (i + 1));

        }
        if (temp.get(j) instanceof Switch)
        {
          gc.drawImage(noLight, DISTANCE * (j + 1), DISTANCE * (i+1));
        }

      }
    }

    putTrainsOnMap();


    canvas.setHeight(720);
    canvas.setWidth(1280);
    gamePane.getChildren().setAll(trainNCanvas);
    gamePane.getChildren().set(0, canvas);
    this.start();


  }

  private void putTrainsOnMap()
  {
    int i = 1; // index 0 is reserved for canvas always

    for (Train t : trainList)
    {
      // element 0 is the canvas so increment of one.
      trainNCanvas.add(i, new ImageView(trainImage));
      trainNCanvas.get(i).setX(DISTANCE);
      trainNCanvas.get(i).setY(DISTANCE * (i + 1) - 5);
      trainNCanvas.get(i).setId(t.getTrainID() + "");
      i++;
    }
  }

  private int currentXpos=0;// pos where the train should be.

  @Override
  public void handle(long now)
  {
    frameCounter++;


    for (int i = 0; i < trainNCanvas.size() - 1; i++)
    {

      currentXpos = trainList.get(i).getXPos() * DISTANCE;
      if (currentXpos != currentXpos + (frameCounter / 3) % 88)
      {
        trainNCanvas.get(i + 1).setX(currentXpos + (frameCounter / 3) % 88);
      }
    }

  }
}





