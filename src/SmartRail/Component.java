package SmartRail;

import java.util.List;

public interface Component
{
  List<Component> neighbors;
  String message;

  public String acceptMessage(String message);

}