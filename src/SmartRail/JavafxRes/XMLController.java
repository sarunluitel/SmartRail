package SmartRail.JavafxRes;

import SmartRail.Light;
import SmartRail.Map;
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
  private void draw()
  {

    entireMap= MapView.getEntireMap();


    GraphicsContext gc = canvas.getGraphicsContext2D();


    for (int i = 0; i < entireMap.size(); i++)
    {
      ArrayList temp = entireMap.get(i);

      for (int j = 0; j < temp.size(); j++)
      {

        if (temp.get(j) instanceof Station)
        {
          gc.fillRect(100 + 100 * j, 100+ 100*i, 80, 80);
        }

        if (temp.get(j) instanceof Track)
        {
          gc.fillRect(100 * j + 100, 100+ 100*i, 100, 30);
        }

        if (temp.get(i) instanceof Light)
        {
          gc.fillRect(100 * j + 100, 100+ 100*i, 100, 30);
        }


        System.out.println("after first fill");


      }
    }
  }


}
