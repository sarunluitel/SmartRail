package SmartRail;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;



public class JavaFX extends Application
{

  private Canvas test =new Canvas(800,500);


  void runDisplay(String[] args)
  {

    launch(args);

  }

  @Override
  public void start(Stage primaryStage) throws Exception
  {
    Group root = new Group();
    root.getChildren().add(test);

    draw();


    Scene testS=new Scene(root);
    primaryStage.setScene(testS);
    primaryStage.show();

  }

  private void draw()

  {
   /***
    *  Syntax reference
    GraphicsContext gc= test.getGraphicsContext2D();
    gc.setFill(Color.GREEN);
    gc.setStroke(Color.BLUE);
    gc.setLineWidth(5);
    gc.strokeLine(40, 10, 10, 40);
    gc.fillOval(10, 60, 30, 30);
    gc.strokeOval(60, 60, 30, 30);
    gc.fillRoundRect(110, 60, 30, 30, 10, 10);
    gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
    gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
    gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
    gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
    gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
    gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
    gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
    gc.fillPolygon(new double[]{10, 40, 10, 40},
        new double[]{210, 210, 240, 240}, 4);
    gc.strokePolygon(new double[]{60, 90, 60, 90},
        new double[]{210, 210, 240, 240}, 4);
    gc.strokePolyline(new double[]{110, 140, 110, 140},
        new double[]{210, 210, 240, 240}, 4);
    ***/

    GraphicsContext gc= test.getGraphicsContext2D();


  }
}
