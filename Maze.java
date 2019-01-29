
import java.util.ArrayList;

import java.util.Random;

import java.util.concurrent.ThreadLocalRandom;

public class Maze {

	final int numWalls = 4; //each cell has 4 walls
        final int N = 0, S = 1, E = 2, W = 3;
	DisjointSet s;
	Random random;
	int mazeLength;
	int[][] walls;
	
	public Maze(int n) {
		    int i = 0;
		    int j = 0;
		    
		    walls = new int[n*n][numWalls]; //each cell (n*n) has array of numWalls
		                                            //i.e - walls[0][0] is first cell north wall
		                                            // walls[0][1]:south, walls[0][2]:east, walls[0][3]:west
		    mazeLength = n;
		    //set all walls to 1 (closed)
		    for(int c = 0; c < n * n; c++) {
		      for(int w = 0; w < numWalls; w++) {
		        walls[c][w] = 1;
		      }
		    }
		    
		    walls[0][N] = 0; //first cell north wall is open
		    walls[(n * n) - 1][S] = 0; //last cell south wall is open
		    
		    s = new DisjointSet(n * n);
		    
		    while(s.find(0) != s.find((n * n) - 1)) {
		      ArrayList<Integer> adjacentCells = new ArrayList<Integer>();
		      
		      i = ThreadLocalRandom.current().nextInt(0, (n * n));
		      if(i < ((n * n) - n)) {
		        //can go down
		        adjacentCells.add(i + n);
		      }
		      if(i >= n) {
		        //can go up
		        adjacentCells.add(i - n);
		      }
		      if(i % n >= 0 && i % n < (n - 1)) {
		        //can go right
		        adjacentCells.add(i + 1);
		      }
		      if(i % n > 0 && i % n <= (n - 1)) {
		        //can go left
		        adjacentCells.add(i - 1);
		      }
		      
		      j = ThreadLocalRandom.current().nextInt(0, adjacentCells.size());
		      j = adjacentCells.get(j);      

		      if(s.find(i) != s.find(j)) {
		        //open the doors
		        s.union(s.find(i), s.find(j));
		        
		        if(j == (i + n)) {
		          //open the bottom wall
		          walls[i][S] = 0;
		          walls[j][N] = 0;
		        } else if(j == (i - n)) {
		          //open the top wall
		          walls[i][N] = 0;
		          walls[j][S] = 0; 
		        } else if(j == (i + 1)) {
		          //open the right wall
		          walls[i][E] = 0;
		          walls[j][W] = 0;
		        } else if(j == (i - 1)) {
		          //open the left wall
		          walls[i][W] = 0;
		          walls[j][E] = 0;
		        }
		      }
		    }   
		    //System.out.println(s);
		    /*System.out.println();
		    for(int c = 0; c < n * n; c++) {
		      for(int w = 0; w < numWalls; w++) {
		        System.out.print(walls[c][w] + " ");
		      }
		      System.out.println();
		    }*/
		    
	}
	
}

	
