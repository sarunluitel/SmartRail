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
import javafx.scene.layout.Pane;

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
  private final Image stationImage = new Image(getClass().getResourceAsStream("GUI_resources/station.png"));

  private ArrayList<ImageView> allComponentsGUI = new ArrayList<>();

  @FXML
  private Pane gamePane;

  @FXML
  void initialize()
  {
    ArrayList<ArrayList> entireMap;
    gc = canvas.getGraphicsContext2D();

    entireMap = MapView.getInstance().getEntireMap();

    allComponentsGUI.add(0, new ImageView());//temp added at 0 later canvas
    for (int i = 0; i < entireMap.size(); i++)
    {
      ArrayList temp = entireMap.get(i);

      for (int j = 0; j < temp.size(); j++)
      {

        if (temp.get(j) instanceof Station)
        {
          gc.drawImage(stationImage,DISTANCE * (j+1), DISTANCE * (i + 2));
          if (j == 0)
          {
            // element 0 is the canvas so increment of one.
            allComponentsGUI.add(i + 1, new ImageView(trainImage));
            allComponentsGUI.get(i + 1).setX(DISTANCE * (j + 1));
            allComponentsGUI.get(i + 1).setY(DISTANCE * (i + 2) - 5);
            allComponentsGUI.get(i + 1).setId("train " + j + 1);
          }
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
    gamePane.getChildren().setAll(allComponentsGUI);
    gamePane.getChildren().set(0, canvas);

    this.start();
  }

  private ArrayList<Train> trainList = TrainView.getInstance().getList();

  @Override
  public void handle(long now)
  {
    frameCounter++;
    for (int i = 1; i < 7; i++)
    {

      allComponentsGUI.get(i).setX(frameCounter / 2);
    }
  }
}





