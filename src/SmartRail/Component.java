/*** ==========================
 * @author Vincent Huber
==========================***/

package SmartRail;


public interface Component
{
  //Methods used by each of the components:
  //Switch, Station, Light, and Track
  //Comments are given in the components
  void acceptMessage(Message message);

  boolean findPath(Component c, String dir);

  boolean returnPath(Message m);

  boolean securePath(Message m);

  boolean readyForTrain(Message m);

  boolean couldNotSecure(Message m);

  // makes component keep track of next node
  //linear tracks.
  //Used by train to move after path is found
  Component nextComponent(String direction);

  void getTrainId(Train t);

  void trainLeaving();

  String getComponentName();

}