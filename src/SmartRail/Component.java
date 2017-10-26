package SmartRail;

import java.util.ArrayList;
import java.util.List;

public interface Component
{
  List<Component> neighbors = new ArrayList<>();
  String message = "";

  public String acceptMessage(String message);

}