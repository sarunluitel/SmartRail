package SmartRail.JavafxRes;
// Agreed on  Standard side for track 30:100
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class TrackView
{
  public Image image;

  public TrackView(){
    getImage();
  }
  private void getImage()
  {

    image  = new Image(getClass().getResourceAsStream("GUI_resources/track1.png"));

  }
}
