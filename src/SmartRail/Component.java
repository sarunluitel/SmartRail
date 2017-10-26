/*** ==========================
 * @author Vincent Huber
   ==========================***/

package SmartRail;


public interface Component
{


  public String acceptMessage(String message);
  public boolean hasComponent(Component c, String dir);

}