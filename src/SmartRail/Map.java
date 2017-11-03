package SmartRail;

import java.util.LinkedList;

class Map
{
  LinkedList<Station> right= new LinkedList<>();

  Map(String config)
  {
    for(char c : config.toCharArray()) {
     // if(c=='R') right.add(new Station())
    }
  }
}
