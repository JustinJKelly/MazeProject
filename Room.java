import java.util.ArrayList;

import javafx.scene.text.Text;

public class Room {

	int n;  //number of rooms available
	boolean visited = false;
	int north = 1, south = 1, east = 1, west = 1;
	public int roomNumber;
	public int startX, startY;
	public Room prevRoom;
	public Text text;
	public ArrayList<Integer> doors;
	
	public void setStartX(int x) {
		this.startX = x;
	}
	
	public int getStartX() {
		return this.startX;
	}
	
	public void setStartY(int y) {
		this.startY = y;
	}
	
	public int getStartY() {
		return this.startY;
	}
	
    public Room() {
      doors = new ArrayList<Integer>();
    }
}
