package SmartRail.JavafxRes;
// Agreed on  Standard side for track 30:100

import SmartRail.Station;
import SmartRail.Track;
import SmartRail.Train;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

public class StationView
{
  private HashMap<String, Station> allTracks = new HashMap<>();

  private static StationView instance = null;

  private StationView()
  {
  }

  public static StationView getInstance()
  {
    if (instance == null) instance = new StationView();
    return instance;
  }

  public void addStation(String stationID, Station s1)
  {
    allTracks.put(stationID, s1);
  }

  HashMap<String, Station> getList()
  {
    return allTracks;
  }


}
