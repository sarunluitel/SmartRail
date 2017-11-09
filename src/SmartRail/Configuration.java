package SmartRail;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;


public class Configuration
{
  private LinkedList<String> linesOfTracks = new LinkedList<>();
  // index starts with 0
  private int lineCounter = -1;
  private int linesSent = -1;

  Configuration()
  {
    try
    {
      //Made it compatible with Jar Export
      InputStream is = ClassLoader.getSystemResourceAsStream("resources/Config.txt");
      InputStreamReader r = new InputStreamReader(is);
      BufferedReader buffer = new BufferedReader(r);
      String thisLine;

      while ((thisLine = buffer.readLine()) != null && !thisLine.trim().isEmpty())
      {
        // Ignore commented lines.
        if (!thisLine.startsWith("/"))
        {
          lineCounter++;
          linesOfTracks.addLast(thisLine.trim());

        }
      }
    } catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  String getNextLine()
  {
    if (lineCounter != linesSent)
    {
      linesSent++;
      return linesOfTracks.get(linesSent);
    }
    return "EOF";
  }
}
