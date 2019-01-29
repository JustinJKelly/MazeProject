import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.concurrent.TimeUnit;
/****
 * 
 * 
 * @author Justin Kelly
 * 
 * @author Jeff Nagy
 * 
 * 
 ******/


public class MazeGUI extends Application {

	private static final int START_X = 15, START_Y = 15, ROOM_LENGTH = 10; //standard START_X/Y = 50, ROOM_LENGTH=15
	private static Pane pane;
        //must initally equal START_X,START_Y
	private static int startX = 15;
	private static int startY = 15;
    private static ArrayList<Room> rooms; //holds rooms of maze
    private static int N = 0;  //for the number of rooms in a row

    
    
	
	public static void main(String[] args) {
		launch(args);
	}

	private static void makeMaze(int[][] maze, int n) { //makes maze
		boolean keepGoing = true;
		int i = 0; //first indice in maze matrix

		while(keepGoing) {
			Room thisRoom = new Room();
			for (int l = 0; l < 4; l++) {
				thisRoom.doors.add(maze[i][l]); //adds each door to door ArrayList, used for creating the maze diagram
			}

			//Set thisRoom to the values of each door in the maze array
			thisRoom.north = maze[i][0];  
			thisRoom.south = maze[i][1];
			thisRoom.east = maze[i][2];
			thisRoom.west = maze[i][3];
			thisRoom.roomNumber = i;
			
			rooms.add(thisRoom); //add thisROom to rooms
			makeRoom(thisRoom, i,n);  //makes diagram of thisRoom with walls and open doors
			
			if (i == n*n-1) {
				keepGoing = false;
			} else {
				i++;
			}
		}
	 }
		
		

	private static void makeRoom(Room thisRoom, int i, int numSlotsinMatrix) {  //makes room diagram
		thisRoom.setStartX(startX);  //set the startX of this room
		thisRoom.setStartY(startY);  //set the startY of this room, this will be room space in the maze
		if (thisRoom.doors.get(0) == 1) { //if the room's north door is closed, make a horizontal line at the top(30 pixels long)
			Line line = new Line(startX,startY, startX + ROOM_LENGTH, startY);
			pane.getChildren().add(line);
		}
		if (thisRoom.doors.get(1) == 1) { //if the door to south is closed, make a new Line 30 pixels down from the startY and 30 px across
			Line line = new Line(startX,startY + ROOM_LENGTH, startX + ROOM_LENGTH, startY + ROOM_LENGTH);
			pane.getChildren().add(line);
		}
		if (thisRoom.doors.get(2) == 1) { //if door to east is closed make a  30 px vertical line starting or right side of door
			Line line = new Line(startX + ROOM_LENGTH,startY, startX + ROOM_LENGTH, startY + ROOM_LENGTH);
			pane.getChildren().add(line);
		}
		if (thisRoom.doors.get(3) == 1) { //if door to west is closed make a vertical line 30 px long
			Line line = new Line(startX,startY, startX, startY + ROOM_LENGTH);
			pane.getChildren().add(line);
		} 
		
		if (i % numSlotsinMatrix == numSlotsinMatrix-1) { //if the matrix is at its final rightMost point, add 30 to startY to move it down, put original startX in startX
			startX = START_X;
			startY = startY + (ROOM_LENGTH);
		} else {
			startX = startX + ROOM_LENGTH;
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		pane = new Pane();
		
		rooms = new ArrayList<Room>();
		int[][] maze = null;     //This makes the maze as a int matrix
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter size(up to 75, will make 75x75 maze) of maze or enter text file(ex: maze.txt):");
		Maze maze1 = null;
		
		String result = scan.nextLine();
		int result1 = 0;
		Scanner fileScanner = null;
		
		try {
		  result1 = Integer.parseInt(result); //try to turn String result1 into an int, if it can then input is a maze text file
		} catch (NumberFormatException e) {
			
		}
	    if (result1 == 0)   { //if the input was a text file
		  try { //This will read in a text file 
			File file = new File(result);
			FileReader fr = new FileReader(file);
			fileScanner = new Scanner(fr);
			String nValue = fileScanner.nextLine();
			N = Integer.parseInt(nValue);
			
			maze = new int[N*N][4];

			int j = 0;
			int k = 0;
			
			while (fileScanner.hasNextLine())
			{
				 String line = fileScanner.nextLine();
			     Scanner lineScanner = new Scanner(line);
				 while (lineScanner.hasNextInt()) {
					maze[j][k] = lineScanner.nextInt();
					if (k == 3) {
						k= 0;
						j++;
					} else {
						k++;
					}
				}
			}
			fileScanner.close();
			
		} catch (FileNotFoundException e) {
			System.out.print("File not found.");
		} 
	   } else { //else make a random maze with size from the user input
			maze1 = new Maze(result1); 
		} 
		
		Button button = new Button(); //this button is for breadth first search BFS()
		button.setMinWidth(75);
		button.setText("BFS");
		button.setLayoutX(900);
		button.setLayoutY(400);
		pane.getChildren().add(button);
		
		Button button1 = new Button(); //this button is for breadth first search BFS()
		button1.setMinWidth(75);
		button1.setText("DFS");
		button1.setLayoutX(900);
		button1.setLayoutY(450);
		pane.getChildren().add(button1);
		
		
		if (maze1 == null) {
		  makeMaze(maze,N); // this makes the maze, I used lines as barriers
		  makeTexts(); //this makes a text in between each of room in the maze
		} else {
			N = maze1.mazeLength;
			makeMaze(maze1.walls/*maze1.maze for maze I made*/, maze1.mazeLength);
			makeTexts();
		}
		button.setOnAction((e) -> {  
			BFS(rooms.get(0), N);
			button.setDisable(true);
			button1.setDisable(true);
		});
		button1.setOnAction((e) -> {  
			DFS(rooms.get(0), N); 
			button1.setDisable(true);
			button.setDisable(true);
		});
		
		//Scene scene = new Scene(pane,700,700);
		Scene scene = new Scene(pane,1000,780);
		stage.setScene(scene);
		stage.show();
		
	}
	

	private void makeTexts() { //this creates a text object in the rooms of the maze, the text is initially an empty string
		
		for (int i = 0; i<rooms.size(); i++) {
			Room room = rooms.get(i);
			room.text = new Text("");
			room.text.setLayoutX(room.startX + 10 - 5-3);
			room.text.setLayoutY(room.startY + 15 - 5 - 2);
			pane.getChildren().add(room.text);
		}
		
	}
	

	private void BFS(Room thisRoom, int n) {
		
		   Queue<Room> inMethodQueue = new Queue<Room>(); //this queue holds rooms that have more than 1 direction option to move
		   Queue<Room> roomQueue = new Queue<Room>(); //this queue holds the rooms based on the this order, 1. North, 2. South, 3. East, 4.West
		   
		   int i = 0; //will hold room number
		   //Room thisRoom = rooms.get(i); //this is the start room
		   Room prevRoom = thisRoom; //prevRoom is an attribute of the Room object I created, it points to the room visited previous to thisRoom
		   
		   thisRoom.visited = true; 
		   roomQueue.enqueue(thisRoom); //add first Room to queue
		   
		   boolean keepGoing = true; 
		   
		   while(roomQueue.size != 0 && keepGoing) { //while keepGoing is true and roomQueue is not empty
			   thisRoom = roomQueue.dequeue(); //dequeue the next room
			   
			   if (thisRoom.roomNumber != 0) { // while its not the first room, room0
				   thisRoom.prevRoom = prevRoom; //assign prevRoom to thisRoom's prevRoom attribute
				   prevRoom = thisRoom; //assign prevRoom to current room
			   }
			   
			   int numberMoves = 0;
			   i = thisRoom.roomNumber; //make i equal to this room number
		       visitNode(thisRoom); //visit the room
		       
			   if(thisRoom.equals(rooms.get(rooms.size()-1))) { //if thisRoom equals the final room, keepGoing is false and BFS is over
				   keepGoing = false;
			   } else {
					  
					   if ((thisRoom.west == 0) && !(rooms.get(i-1).visited)) { //if the room to west of thisRoom is true, and not visited, enqueue that room
						   roomQueue.enqueue(rooms.get(i-1));
						   rooms.get(i-1).visited = true;
						   numberMoves++;
					   }
					   if ((thisRoom.east == 0) && !(rooms.get(i+1).visited)) { //if the room to east of thisRoom is true, and not visited, enqueue that room
						   roomQueue.enqueue(rooms.get(i+1));
						   rooms.get(i+1).visited = true;
						   numberMoves++;
					   }
					   if ((thisRoom.south == 0) && !(rooms.get(i+n).visited)) {//if the room to south of thisRoom is true, and not visited, enqueue that room
						   roomQueue.enqueue(rooms.get(i+n));
						   rooms.get(i+n).visited = true;
						   numberMoves++;
					   } 
					   
					  if ((thisRoom.north == 0) && (thisRoom.roomNumber > (n-1)) && !(rooms.get(i-n).visited)) { //if the room to north of thisRoom is true, and the room number is greater than 4, and not visited, enqueue that room
						   roomQueue.enqueue(rooms.get(i-n));
						   rooms.get(i-n).visited = true;
						   numberMoves++;
					  }
					  
					  if (numberMoves > 1) { //if numberMoves for thisRoom  is greater than 1, enqueue room in inMethodQueue
						  inMethodQueue.enqueue(thisRoom);
						  if (numberMoves == 3) {
							  inMethodQueue.enqueue(thisRoom);
						  }
					  }
					  
					  if (numberMoves == 0) { //if no moves, go back
						  Room room = thisRoom;
						  while (room != inMethodQueue.peek()) { //while room does not equal the next room in queue, remove the text from the room, go to the prevRoom
							  //System.out.println("Going back from " + room.roomNumber + " To " + room.prevRoom.roomNumber);
							  removeText(room); //removes room from the path
							  room = room.prevRoom;  //goes back to previous room
						  }
						  inMethodQueue.dequeue(); //take next queue off
					  }
				  }
			   }
	  }
	
	
	
	
	
	private void DFS(Room thisRoom, int n) {
		
		Stack<Room> roomStack = new Stack<Room>();  //main stack to go through maze
		Stack<Room> inMethodStack = new Stack<Room>();  //stack when algorithm hits hits a dead end and needs to go back
		
		int i = 0;
		
		roomStack.push(thisRoom);
		thisRoom.visited = true;
		Room prevRoom = thisRoom;
		
		while (!roomStack.isEmpty() && i != rooms.size()-1) {
			thisRoom = roomStack.pop();
			int numberMoves = 0;
			
			if (thisRoom.roomNumber != 0) { //if its not the first room, the room will have a prevRoom
				thisRoom.prevRoom = prevRoom;
				prevRoom = thisRoom;
			}
			
			 //System.out.println("Visiting room" + thisRoom.roomNumber);
			 i = thisRoom.roomNumber; //make i equal to this room number
		     visitNode(thisRoom); //visit the room
		       
			  if(!thisRoom.equals(rooms.get(rooms.size()-1))) { //if thisRoom equals the final room, keepGoing is false and BFS is over
					  
					   if ((thisRoom.west == 0) && !(rooms.get(i-1).visited)) { //if the room to west of thisRoom is true, and not visited, enqueue that room
						   roomStack.push(rooms.get(i-1));
						   rooms.get(i-1).visited = true;
						   numberMoves++;
					   }
					   if ((thisRoom.east == 0) && !(rooms.get(i+1).visited)) { //if the room to east of thisRoom is true, and not visited, enqueue that room
						   roomStack.push(rooms.get(i+1));
						   rooms.get(i+1).visited = true;
						   numberMoves++;
					   }
					   if ((thisRoom.south == 0) && !(rooms.get(i+n).visited)) {//if the room to south of thisRoom is true, and not visited, enqueue that room
						   roomStack.push(rooms.get(i+n));
						   rooms.get(i+n).visited = true;
						   numberMoves++;
					   } 
					   
					  if ((thisRoom.north == 0) && (thisRoom.roomNumber > (n-1)) && !(rooms.get(i-n).visited)) { //if the room to north of thisRoom is true, and the room number is greater than 4, and not visited, enqueue that room
						   roomStack.push(rooms.get(i-n));
						   rooms.get(i-n).visited = true;
						   numberMoves++;
					  }
					  
					 if (numberMoves > 1) { //if numberMoves for thisRoom  is greater than 1, enqueue room in inMethodQueue
						  inMethodStack.push(thisRoom);
						  if (numberMoves == 3) {
							  inMethodStack.push(thisRoom);
						  }
					  }
					  
					if (numberMoves == 0) { //if no moves, go back
						  Room room = thisRoom;
						  while (room != inMethodStack.peek()) { //while room does not equal the next room in queue, remove the text from the room, go to the prevRoom
							 //System.out.println("Going back from " + room.roomNumber + " To " + room.prevRoom.roomNumber);
							  removeText(room);  //removes room from the path
							  room = room.prevRoom; //goes back to previous room
						  }
						  inMethodStack.pop(); //take next queue off
					  }
				 }
					  
		 }
				   
	}
		   
	 
	private void removeText(Room room) {
		room.text.setText("");
	}

	private void visitNode(Room thisRoom) {
		thisRoom.text.setText("x");
	}

}
