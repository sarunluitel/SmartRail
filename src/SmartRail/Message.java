/************************************
 @author Vincent Huber
 This class is used to creates messages
 for different classes to use
 The message contains 4 variables
 Direction, Action, list of components and
 a reference of who sent the object

 ************************************/

package SmartRail;

import java.util.LinkedList;

public class Message
{
  private String direction;
  private String action;
  private LinkedList<Component> intendedTarget;
  private Component sender;

  //Constructor for Message, initializes the variables
  public Message(String dir, String act, LinkedList<Component> compList, Component c)
  {
    direction = dir;
    action = act;
    intendedTarget = compList;
    sender = c;
  }

  //getter method for action, gets action
  //Returns a string
  public String getAction()
  {
    return action;
  }

  //getter method for getting direction message is to be sent
  //Returns a string
  public String getDirection()
  {
    return direction;
  }

  //getter method for the list of components (target for finding path,
  //list of objects along path when returning and securing
  //Returns a list of components
  public LinkedList<Component> getTarget()
  {
    return intendedTarget;
  }

  //getter method for action, gets action
  //Returns a component
  public Component getSender()
  {
    return sender;
  }

  //Setter method for the sender so a component can easily change
  //who is sending the message.
  //Sender is mostly used by the switch class for reference
  //returns nothing
  public void setSender(Component c)
  {
    sender = c;
  }
}
