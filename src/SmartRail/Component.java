/*** ==========================
 * @author Vincent Huber
==========================***/

package SmartRail;


public interface Component
{
  //
  void acceptMessage(Message message);

  boolean findPath(Component c, String dir);
  boolean returnPath(Message m);

  // makes component keep track of next node
  //linear tracks.
  //Used by train to move after path is found
  Component nextComponent(String direction);
  void getTrainId(Train t);
  String getComponentName();

}