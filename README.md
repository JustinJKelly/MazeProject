# MazeProject
This project has several classes which create a maze and has a GUI that displays the maze and solves the maze.
The projects takes in a integer n(up to 75) and creates a solvable nxn maze. It can also take in a formated
file and will construct that maze based on the contents. 

Maze.java

This class defines fields used for the maze object and the constructor
that will build the maze given a size parameter. The constructor will construct 
a nx4 matrix in which n is the number of rooms in the maze and 4 represents
each wall of the room which will lead to an adjacent room. Walls are ordered by indexes
0=North, 1=South, 2=East, 3=West. Initially, all the values in the walls matrix is set to 1
denoting that their to no path from this room to an adjacent room. The algorithm will pick 
a random room and then willpick a random door and "break" down the wall to make a path through 
the maze. This will set an index of the walls matrix to 0 denoting a path from room i to an 
adjacent room j. A disjoint set is used to determine if the maze contains a pathway from room 1
to room n, hence the maze can be solved. A nx4 matrix named walls will represent a grah of the 
maze.

MazeGUI.java

This class contains the JavaFX GUI that will display the constructed maze to the
user. The GUI contains two buttons, BFS and DFS, which when pressed, will run BFS
or DFS on the Graph walls and use x's to draw out a path in the maze.

Node.java

This contains necessary data for creating a Queue consisting of Node objects.

Queue.java

This file contains the Queue object fields/methods which are used for the the BFS function.

DisjointSet.java

This file contains the makings of a DisjointSet object whcih will be used to determine if
there is a pathway through the maze.

LinkedList.java

This class will be used for the Queue object which is a LinkedList with specific operations.

Room.java

This class contains the methods/fields that will represent a room in the maze.
