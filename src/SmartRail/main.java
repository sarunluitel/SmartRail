package SmartRail;

import java.util.ArrayList;

/************************************
 @author Sarun Luitel
 ************************************/
public class main
{
  public static void main(String[] args)
  {

    Configuration c = new Configuration();
    Map map= new Map(c.getNextLine());


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

    light1.start();
    t1.start();

    Track trk3=new Track("trk3");
    Track trk4=new Track("trk4");
    Station s4;


    Station s3 = new Station("Station3",null,trk3);
    trk3.setNeighbors(trk4, "right");
    trk3.setNeighbors(s3, "left");

    s4 = new Station("Station4",trk4,null);

    trk4.setNeighbors(trk3, "left");
    trk4.setNeighbors(s4, "right");
    Train t2 = new Train(s4,s3);

    t2.start();

  }
}


