package SmartRail.JavafxRes;
// Agreed on  Standard side for track 30:100

import SmartRail.Track;
import SmartRail.Train;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

public class TrackView
{
  private HashMap<String, Track> allTracks = new HashMap<>();

  private static TrackView instance = null;

  private TrackView()
  {
  }

  public static TrackView getInstance()
  {
    if (instance == null) instance = new TrackView();
    return instance;
  }

 public void addTrack(String trackID, Track t1)
  {
    allTracks.put(trackID, t1);
  }

  HashMap<String, Track> getList()
  {
    return allTracks;
  }


}
