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
    //Track track1 = new Track("track1");
    //Track track2 = new Track("track2");
    ArrayList<Component> nextTo = new ArrayList<>();
  //  nextTo.add(track2);
   // track1.giveNeighbors(nextTo);
  //  track1.start();
    instantiate();

  }
  static void instantiate()
  {
    Track trk1=null;
    Track trk2= null;
    Station s2= null;


    Station s1 = new Station("Station1",trk1);
    trk1 = new Track("track1",s1,trk2);
    trk2 = new Track("track2",trk1,s2);
    s2 = new Station("Station2",trk2);


    Train t1 = new Train(s1,s2);

    t1.start();
  }
}


