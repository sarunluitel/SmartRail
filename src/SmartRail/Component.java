/*** ==========================
 * @author Vincent Huber
   ==========================***/

package SmartRail;


public interface Component
{
   String acceptMessage(String message);
   boolean hasComponent(Component c, String dir);
   Component nextComponent();

}