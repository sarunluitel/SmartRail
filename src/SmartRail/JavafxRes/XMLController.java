package SmartRail.JavafxRes;

import SmartRail.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
  private ArrayList<ArrayList> entireMap;
  @FXML
  private Pane gamePane;

  @FXML
  void initialize()
  {
    gc = canvas.getGraphicsContext2D();

    entireMap = MapView.getInstance().getEntireMap(); //comes from configuration file

    trainNCanvas.add(0, new ImageView());//temp added at 0 to add canvas later

    for (int i = 0; i < entireMap.size(); i++)
    {
      ArrayList temp = entireMap.get(i);
      int componentInTemp = 0;

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

          gc.drawImage(noLight, DISTANCE * (j + 1), DISTANCE * (i + 1) - 10);

          j++;

          gc.drawImage(trackImage, DISTANCE * (j + 1), DISTANCE * (i + 1));
          gc.drawImage(noLight, DISTANCE * (j + 2), DISTANCE * (i + 1) - 10);

          gc.fillOval(DISTANCE * (j + 1), DISTANCE * (i + 1) - 10, 40, 40);


        }
        componentInTemp++;
      }
      if (i != 0) drawConnection(i);
    }


    gamePane.getChildren().setAll(trainNCanvas);
    gamePane.getChildren().set(0, canvas);
    gamePane.getChildren().add(btnSpawn);

    this.start();


  }

  @FXML
  private Button btnSpawn;

  private void drawConnection(int currentLayer)
  {
    ArrayList<Integer> seniorSwitches = new ArrayList<>();
    ArrayList<Integer> juniorSwitches = new ArrayList<>();
    Integer seniorPos = 1;
    Integer juniorPos = 1;

    for (Component senior : (ArrayList<Component>) entireMap.get(currentLayer - 1))
    {
      seniorPos++;
      if (senior instanceof Switch)
      {

        seniorSwitches.add(seniorPos);
      }
    }

    for (Component junior : (ArrayList<Component>) entireMap.get(currentLayer))
    {
      juniorPos++;
      if (junior instanceof Switch)
      {

        juniorSwitches.add(juniorPos);
      }
    }
    if (juniorSwitches.size() == 0 || seniorSwitches.size() == 0) return;
    for (int i = 0; i < seniorSwitches.size(); i++)
    {
      int x1, x2, y1, y2;
      x1 = (seniorSwitches.get(i) + i) * DISTANCE + 10;
      x2 = (juniorSwitches.get(i) + i) * DISTANCE + 10;
      y1 = (currentLayer) * DISTANCE + 10;
      y2 = (currentLayer + 1) * DISTANCE + 10;
      gc.setLineWidth(8);
      gc.strokeLine(x2, y2, x1, y1); //DISTANCE * (j + 1), DISTANCE * (i + 1) - 10
    }
  }


  private void putTrainsOnMap()
  {
    int i = 1; // index 0 is reserved for canvas always

    for (Train t : trainList)
    {
      System.out.println("looking at the list");
      // element 0 is the canvas so increment of one.
      trainNCanvas.add(i, new ImageView(trainImage));
      trainNCanvas.get(i).setX(t.getXPos() * DISTANCE);
      trainNCanvas.get(i).setY(DISTANCE * t.getYPos());
      trainNCanvas.get(i).setId(t.getTrainID() + "");
      i++;
    }
    gamePane.getChildren().setAll(trainNCanvas);
    gamePane.getChildren().set(0, canvas);
    gamePane.getChildren().add(btnSpawn);
  }

  private int currentXpos = 0;// pos where the train should be.

  @Override
  public void handle(long now)
  {
    frameCounter++;

    for (int i = 0; i < trainNCanvas.size() - 1; i++)
    {

      // currentXpos = trainList.get(i).getXPos() * DISTANCE;
      // if (currentXpos != currentXpos + (frameCounter / 3) % DISTANCE)
      // {
      trainNCanvas.get(i + 1).setX(frameCounter);
      //  }
    }

  }


  private int trainSpawn = -1;
  private int trainDestination = -1;

  @FXML
  private void clicked(MouseEvent e)
  {
    int stationX = (int) (e.getX() / 88);
    int stationY = (int) (e.getY() / 88);
    gc.fillRect(stationX * DISTANCE, stationY * DISTANCE, 53, 46);// some indication for clicked station

    if (trainSpawn == -1)
    {
      trainSpawn = stationY * 10 + stationX;
      return;
    }
    trainDestination = stationY * 10 + stationX;
  }


  @FXML
  private void spawn()
  {
    Train train;
    if (trainDestination != -1 && trainSpawn != -1)
    {
      for (int j = 0; j < entireMap.get(trainDestination / 10 - 1).size() - 1; j++)
      {

        if (entireMap.get(trainDestination / 10 - 1).get(trainDestination % 10 - 1 + j) instanceof Station)
        {
          train = new Train((Station) entireMap.get(trainDestination / 10 - 1).get(trainDestination % 10 - 1 + j),
              (Station) entireMap.get(trainSpawn / 10 - 1).get(trainSpawn % 10 - 1), trainSpawn % 10, trainSpawn / 10);
          TrainView.getInstance().addTrain(train);
          trainList = TrainView.getInstance().getList();

          trainSpawn = -1;
          trainDestination = -1;
          putTrainsOnMap();
          train.start();
          return;
        }
      }


    }
  }
}





