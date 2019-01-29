import java.util.ArrayList;

public class Queue<Object> {

	public LinkedList queue;
	
	int size = 0;
	
	public Queue() {
		queue = new LinkedList();
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public void enqueue(Room room) {
		if (size != 0) {
			Node prevHead = queue.head;
			queue.head = new Node(room);
			queue.head.next = prevHead;
			prevHead.prev = queue.head;
			size++;
		} else {
			 queue.head = new Node(room);
			 size++;
		}
			
	}
	
	public Room peek() {
		return queue.head.value;
	}
	
	public Room dequeue() {
		if (size == 0) {
			return null;
		} else if (size == 1) {
			Room returnRoom = queue.head.value;
			queue.head = null;
			size--;
			return returnRoom;
		} else {
			Room returnRoom = queue.head.value;
			queue.head = queue.head.next;
			queue.head.prev = null;
			size--;
			return returnRoom;
		}
	}
	
}
