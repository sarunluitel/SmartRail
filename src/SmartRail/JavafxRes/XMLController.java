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
  private final int DISTANCE = 95;// dictated by the size of tracks and trains. length pixel count
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
  private ArrayList<ArrayList> entireMap;
  @FXML
  private Pane gamePane;

  @FXML
  void initialize()
  {

    gc = canvas.getGraphicsContext2D();

    entireMap = MapView.getInstance().getEntireMap();

    trainNCanvas.add(0, new ImageView());//temp added at 0 to add canvas later

    for (int i = 0; i < entireMap.size(); i++)
    {
      ArrayList temp = entireMap.get(i);
      int componentInTemp=0;

      for (int j = 0; componentInTemp < temp.size(); j++)

      {

        if (temp.get(componentInTemp) instanceof Station)
        {
          gc.drawImage(stationImage, DISTANCE * (j + 1), DISTANCE * (i + 1));

        }

        if (temp.get(componentInTemp) instanceof Track)
        {
          gc.drawImage(trackImage, DISTANCE * (j + 1), DISTANCE * (i + 1));

        }
        if (temp.get(componentInTemp) instanceof Switch) // a switch unfolds to a 0=S=0
        {

          gc.drawImage(trackImage, DISTANCE * (j + 1), DISTANCE * (i + 1));

          gc.drawImage(leftGreen, DISTANCE * (j + 1), DISTANCE * (i + 1)-20);

          j++;

          gc.drawImage(trackImage, DISTANCE * (j + 1), DISTANCE * (i + 1));

          gc.drawImage(noLight, DISTANCE * (j + 2), DISTANCE * (i+1)-20);
          gc.fillOval(DISTANCE * (j + 1), DISTANCE * (i + 1)-10,40,40);
          if(i!=0) drawConnection(i);


        }
        componentInTemp++;
      }
    }

    putTrainsOnMap();
    gamePane.getChildren().setAll(trainNCanvas);
    gamePane.getChildren().set(0, canvas);

    this.start();


  }

  private void drawConnection(int currentLayer)
  {
    Track tempTrack = new Track();

    for (Component senior : (ArrayList<Component>) entireMap.get(currentLayer-1))
    {
      if(senior instanceof Switch)
      {
        for (Component junior:(ArrayList<Component>) entireMap.get(currentLayer))

        {
          if(junior instanceof Switch)
          {
            ((Switch) junior).setUpTrack(tempTrack);
            ((Switch) senior).setDown(tempTrack);
            if (((Switch) junior).getIsLeft())
            {

              tempTrack.setNeighbors(junior, "right");
              tempTrack.setNeighbors(senior, "left");

            } else
            {
              tempTrack.setNeighbors(junior, "left");
              tempTrack.setNeighbors(senior, "right");

            }
            tempTrack.start();

          }

        }

      }

    }


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

    gc.strokeLine(50,60,30,30);

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





