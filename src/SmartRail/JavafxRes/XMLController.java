package SmartRail.JavafxRes;

import SmartRail.Light;
import SmartRail.Station;
import SmartRail.Track;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class XMLController
{
  private ArrayList<ArrayList> entireMap;


  @FXML
  private Canvas canvas;


  @FXML
  void initialize()
  {
    final int DISTANCE=60;
    entireMap = MapView.getInstance().getEntireMap();

    GraphicsContext gc = canvas.getGraphicsContext2D();

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

  }

}





