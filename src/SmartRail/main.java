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
    // ArrayList<Component> nextTo = new ArrayList<>();
    //  nextTo.add(track2);
    // track1.giveNeighbors(nextTo);
    //  track1.start();
    instantiate();

  }
  static void instantiate()
  {
    Track trk1=new Track("trk1");
    Track trk2=new Track("trk2");
    Light light1 = new Light(trk1,trk2);
    Station s2;


    Station s1 = new Station("Station1",null,trk1);
    trk1.setNeighbors(light1, "right");
    trk1.setNeighbors(s1, "left");

    s2 = new Station("Station2",trk2,null);

    trk2.setNeighbors(light1, "left");
    trk2.setNeighbors(s2, "right");





    Train t1 = new Train(s2,s1);
    Train t2 = new Train(s1,s2);

    // light1.start();
    t1.start();
    t2.start();
  }
}


