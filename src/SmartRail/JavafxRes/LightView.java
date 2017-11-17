package SmartRail.JavafxRes;

import SmartRail.Light;

import java.util.HashMap;

public class LightView
{
  private static LightView Instance = null;
  private HashMap<String, Light> allLights = new HashMap<>();

  private LightView()
  {
  }

  public static LightView getInstance()
  {
    if (Instance == null) Instance = new LightView();
    return Instance;
  }

  public void addLight(Light light)
  {
    allLights.put(light.getComponentName(), light);
  }

  public HashMap<String, Light> getAllLights()
  {
    return allLights;
  }
}
