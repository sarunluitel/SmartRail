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
import java.util.HashMap;
import java.util.Set;

public class XMLController extends AnimationTimer
{
  private final int DISTANCE = 88;// dictated by the size of tracks and trains. length pixel count make 88
  private double frameCounter = 0.00;
  @FXML
  private Canvas canvas;
  private GraphicsContext gc;
  private final Image trackImage = new Image(getClass().getResourceAsStream("GUI_resources/track.png"));
  private final Image trackImagesecure = new Image(getClass().getResourceAsStream("GUI_resources/tracksecure.png"));
  private final Image trainImage = new Image(getClass().getResourceAsStream("GUI_resources/train.png"));
  private final Image trainImageflip = new Image(getClass().getResourceAsStream("GUI_resources/fliptrain.png"));
  private final Image stationImage = new Image(getClass().getResourceAsStream("GUI_resources/station.png"));
  private final Image stationImageSecure = new Image(getClass().getResourceAsStream("GUI_resources/stationsecure.png"));
  private final Image noLight = new Image(getClass().getResourceAsStream("GUI_resources/noLight.png"));
  private final Image leftRed = new Image(getClass().getResourceAsStream("GUI_resources/leftRed.png"));
  private final Image leftGreen = new Image(getClass().getResourceAsStream("GUI_resources/leftGreen.png"));

  private HashMap<String, ImageView> trainNCanvas = new HashMap<>();
  private HashMap<String, Train> trainList = TrainView.getInstance().getList();
  private ArrayList<ArrayList> entireMap;
  @FXML
  private Pane gamePane;

  @FXML
  void initialize()
  {
    gc = canvas.getGraphicsContext2D();

    entireMap = MapView.getInstance().getEntireMap(); //comes from configuration file

    //  trainNCanvas.put("canvas", new ImageView());//temp added at 0 to add canvas later

    for (int i = 0; i < entireMap.size(); i++)
    {
      ArrayList temp = entireMap.get(i);
      int componentInTemp = 0;

      for (int j = 0; componentInTemp < temp.size(); j++)

      {

        if (temp.get(componentInTemp) instanceof Station)
        {
          Station s = (Station) temp.get(componentInTemp);
          trainNCanvas.put(s.getComponentName(), new ImageView(stationImage));
          trainNCanvas.get(s.getComponentName()).setX(DISTANCE * (j + 1));
          trainNCanvas.get(s.getComponentName()).setY(DISTANCE * (i + 1));

        }

        if (temp.get(componentInTemp) instanceof Track)
        {
          Track t = (Track) temp.get(componentInTemp);
          trainNCanvas.put(t.getComponentName(), new ImageView(trackImage));
          trainNCanvas.get(t.getComponentName()).setX(DISTANCE * (j + 1));
          trainNCanvas.get(t.getComponentName()).setY(DISTANCE * (i + 1));

        }
        if (temp.get(componentInTemp) instanceof Switch) // a switch unfolds to a 0=S=0
        {
          gc.fillOval(DISTANCE * (j + 1), DISTANCE * (i + 1) - 10, 40, 40);
          j--;

        }

        if (temp.get(componentInTemp) instanceof Light) // a switch unfolds to a 0=S=0
        {
          Light l = (Light) temp.get(componentInTemp);
          trainNCanvas.put(l.getComponentName(), new ImageView(noLight));
          trainNCanvas.get(l.getComponentName()).setX(DISTANCE * (j + 1));
          trainNCanvas.get(l.getComponentName()).setY(DISTANCE * (i + 1) - 10);
          j--;

        }
        componentInTemp++;
      }
      if (i != 0) drawConnection(i);
    }
    updateGUI();
    this.start();
  }

  private void updateGUI()
  {
    gamePane.getChildren().setAll(trainNCanvas.values());
    gamePane.getChildren().add(btnSpawn);
    gamePane.getChildren().add(canvas);

  }

  @FXML
  private Button btnSpawn;

  private void drawConnection(int currentLayer)
  {
    ArrayList<Integer> seniorSwitches = new ArrayList<>();
    ArrayList<Integer> juniorSwitches = new ArrayList<>();
    Integer seniorPos = 1;// Compensiates the Offset when components start. blank at the beginning
    Integer juniorPos = 1;

    for (Component senior : (ArrayList<Component>) entireMap.get(currentLayer - 1))
    {
      if (senior instanceof Switch)
      {

        seniorSwitches.add(seniorPos);
      }
      seniorPos++;
      if (senior instanceof Light || senior instanceof Switch) seniorPos--;
    }

    for (Component junior : (ArrayList<Component>) entireMap.get(currentLayer))
    {
      if (junior instanceof Switch)
      {

        juniorSwitches.add(juniorPos);
      }
      juniorPos++;
      if (junior instanceof Light || junior instanceof Switch) juniorPos--;
    }
    if (juniorSwitches.size() == 0 || seniorSwitches.size() == 0) return;
    for (int i = 0; i < seniorSwitches.size(); i++)
    {
      int x1, x2, y1, y2;
      x1 = (seniorSwitches.get(i)) * DISTANCE + 10; //-1 is set to compensiate the space not taken by light
      x2 = (juniorSwitches.get(i)) * DISTANCE + 10;
      y1 = (currentLayer) * DISTANCE + 10;
      y2 = (currentLayer + 1) * DISTANCE + 10;
      gc.setLineWidth(8);
      gc.strokeLine(x2, y2, x1, y1); //DISTANCE * (j + 1), DISTANCE * (i + 1) - 10
    }
  }


  private void putTrainsOnMap()
  {
    int totalTrains = trainList.size();

    Train t = trainList.get("Train " + (totalTrains - 1));
    if (t.getDirection().equals("left")) trainNCanvas.put(t.getTrainName(), new ImageView(trainImageflip));
    if (t.getDirection().equals("right")) trainNCanvas.put(t.getTrainName(), new ImageView(trainImage));
    trainNCanvas.get(t.getTrainName()).setX(t.getXPos() * DISTANCE);
    trainNCanvas.get(t.getTrainName()).setY(DISTANCE * t.getYPos() + 10);
    trainNCanvas.get(t.getTrainName()).setId(t.getTrainName());

    //put new train on the pane to display

    //updateGUI();
    gamePane.getChildren().add(trainNCanvas.get(t.getTrainName()));
  }


  private int trainSpawn = -1;
  private int trainDestination = -1;

  @FXML
  private void clicked(MouseEvent e)
  {
    int stationX = (int) (e.getX() / DISTANCE);
    int stationY = (int) (e.getY() / DISTANCE);
    if (trainSpawn == -1)
    {
      trainSpawn = stationY * 100 + stationX;
      return;
    }
    trainDestination = stationY * 100 + stationX;
  }


  @FXML
  private void spawn()
  {
    if (trainDestination == -1 || trainSpawn == -1) return;
    Train train = null;
    ArrayList<Station> tempSpawn = new ArrayList<>();
    ArrayList<Station> tempDest = new ArrayList<>();
    int GUIComp = 1;
    for (Component c : (ArrayList<Component>) entireMap.get(trainSpawn / 100 - 1))
    {
      if (trainSpawn % 100 != 1) GUIComp++;
      if (c instanceof Station) tempSpawn.add((Station) c);
    }

    for (Component c : (ArrayList<Component>) entireMap.get(trainDestination / 100 - 1))
    {
      if (c instanceof Station) tempDest.add((Station) c);
      if (trainDestination % 100 != 1) GUIComp++;
    }
    Station begin = null;
    Station destination = null;

    if (trainSpawn % 100 == 1)
    {
      //tells if the station is on left corner of board
      begin = tempSpawn.get(0);
      destination = tempDest.get(1);
      train = new Train(destination, begin, 1, trainSpawn / 100);
    }
    if (trainDestination % 100 == 1)
    {
      //tells if the station is on left corner of board
      begin = tempSpawn.get(1);
      destination = tempDest.get(0);
      train = new Train(destination, begin, GUIComp, trainSpawn / 100);
    }

    TrainView.getInstance().addTrain(train.getTrainName(), train);
    trainList = TrainView.getInstance().getList();
    trainSpawn = -1;
    trainDestination = -1;
    putTrainsOnMap();
    train.start();
  }


  private int currentXpos = 0;// pos where the train should be.
  private HashMap<String, Light> lightList = LightView.getInstance().getAllLights();
  private HashMap<String, Station> stationList = StationView.getInstance().getList();
  private HashMap<String, Track> trackList = TrackView.getInstance().getList();
  private Set<String> loop = trainNCanvas.keySet();

  @Override
  public void handle(long now)
  {
    frameCounter++;
    if (frameCounter % 2 == 0) return;
    for (String name : loop)
    {
      switch (name.substring(0, 5))
      {
        case "Train":
          Train t = trainList.get(name);
          if (!t.isAlive())
          {
            gamePane.getChildren().remove(trainNCanvas.get(name));
            break;
          }

          trainNCanvas.get(name).setX(trainList.get(name).getXPos() * DISTANCE);
          trainNCanvas.get(name).setY(trainList.get(name).getYPos() * DISTANCE);
          break;
        case "Light":

          if (lightList.get(name) != null)
          {
            if (lightList.get(name).getOrientation().equalsIgnoreCase("empty"))
              trainNCanvas.get(name).setImage(noLight);
            else if (lightList.get(name).getOrientation().equalsIgnoreCase("left"))
              trainNCanvas.get(name).setImage(leftGreen);
            else if (lightList.get(name).getOrientation().equalsIgnoreCase("right"))
              trainNCanvas.get(name).setImage(leftRed);
            //
          }
          break;
        case "Track":

          if (trackList.get(name).isSecured()) trainNCanvas.get(name).setImage(trackImagesecure);
          if (!trackList.get(name).isSecured()) trainNCanvas.get(name).setImage(trackImage);


          break;
        case "Stati":
          // trainNCanvas.get(name).setX(frameCounter);
          Station s = stationList.get(name);
          if (s.hasTrain()) trainNCanvas.get(name).setImage(stationImageSecure);
          if (!s.hasTrain()) trainNCanvas.get(name).setImage(stationImage);
          break;


      }

    }

  }


}