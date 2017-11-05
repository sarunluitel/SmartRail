package SmartRail;

import SmartRail.JavafxRes.TrackView;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

import java.util.ArrayList;


public class JavaFX extends Application
{

  private Canvas test = new Canvas(800, 500);
  private ArrayList<ArrayList> entireMap;


  void runDisplay(String[] args, ArrayList<ArrayList> layers)
  {
    entireMap = layers;

    launch(args);


  }

  @Override
  public void start(Stage primaryStage) throws Exception
  {

    //System.out.println(entireMap.size());
    Group root = new Group();
    root.getChildren().add(test);
    Scene testS = new Scene(root);

    primaryStage.setScene(testS);
    primaryStage.show();

  }

  private void draw()
  {
    GraphicsContext gc = test.getGraphicsContext2D();


    for (int i = 0; i < entireMap.size(); i++)
    {
      ArrayList temp = entireMap.get(i);

      for (int j = 0; j < temp.size(); j++)
      {

        if (temp.get(j) instanceof Station)
        {
          gc.fillRect(100 + 100 * j, 100, 100, 100);
        }

        if (temp.get(j) instanceof Track)
        {
          gc.fillRect(100 * j + 100, 100, 100, 30);
        }

        if (temp.get(i) instanceof Light)
        {
          gc.fillRect(100 * j + 100, 100, 100, 30);
        }


        System.out.println("after first fill");


      }
    }
  }
}
