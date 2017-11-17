# SmartRail
Project #3 train Simulation

SmartRail is a program used by choosing a starting station
and a destination station for a train and having the train
run using concurrency. 
To run SmartRail, click on the initial station, then click on
the destination. Afer you have clicked your two stations, 
either click the enter button on the keyboard or slick the spawn 
button on the GUI. This will create a train that will run through that route if
possible. If the route is not possible, the train will exit the 
system. Right now there is a bug with clicking on stations on the same side of the board 
however, there will be an error from the interface. The train can test any station, 
even a non-existent station but the interface gives an error when
testing two station on the same side.
Multiple trains can run at the same time. A train however will error if spawned in a destination station
of a previous train or a station with a train still in it. Other
than that a train can spawn anywhere and go anywhere. Multiple trains can 
go to the same destination.
While the train is running, printouts will print stating the trains movements
validating the internal code. The printouts also shows the path of messaging 
the trains do. 

For this project, Vincent Huber worked on the internal
structure such as the component interface and th classes that implement that
along with the message class and the train class. 
Sarun Luitel worked on the GUI aspects of the project
