/*** ==========================
 * @author Vincent Huber
   ==========================***/

package SmartRail;


public interface Component
{
   //
   String acceptMessage(String message);
   boolean hasComponent(Component c, String dir);

   // makes component keep track of next node
  // linear tracks.
   Component nextComponent(String direction);
   String getComponentName();

}