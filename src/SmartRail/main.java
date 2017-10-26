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
    Track track1 = new Track("track1");
    Track track2 = new Track("track2");
    ArrayList<Component> nextTo = new ArrayList<>();
    nextTo.add(track2);
    track1.giveNeighbors(nextTo);
    track1.start();

    Train t1 = new Train(new Station(),new Station());
    Train t2 = new Train(new Station(),new Station());

    System.out.println(t1.getTrainID()+ "    "+ t2.getTrainID());

  }


  public void run()
  {

  }
}


