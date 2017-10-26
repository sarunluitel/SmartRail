package SmartRail;

import java.util.ArrayList;

/************************************
 @author Sarun Luitel
 ************************************/
public class main
{
  public static void main(String[] args)
  {
    System.out.println("smartRail");
    //Edited by Vincent
    Track track1 = new Track();
    Track track2 = new Track();
    ArrayList<Component> nextTo = new ArrayList<>();
    nextTo.add(track2);
    track1.giveNeighbors(nextTo);
    track1.start();



  }
}


