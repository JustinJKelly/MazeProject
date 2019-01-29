
public class DisjointSet {

  private int[] array;

  public DisjointSet(int arraySize) {
    setArray(new int[arraySize]);
    for (int i = 0; i < getArray().length; i++) {
      getArray()[i] = -1;
    }
  }



  public int find(int x) {
    if (getArray()[x] < 0) {
      return x;                         // x is the root of the tree; return it
    } else {
      // Find out who the root is; compress path by making the root x's parent.
      getArray()[x] = find(getArray()[x]);
      return getArray()[x];                                       // Return the root
    }
  }


  public void union(int root1, int root2) {
	  
	  if (getArray()[root2] < getArray()[root1]) {                 // root2 has larger tree
	      getArray()[root2] += getArray()[root1];        // update # of items in root2's tree
	      getArray()[root1] = root2;                              // make root2 new root
	    } else {                                  // root1 has equal or larger tree
	      getArray()[root1] += getArray()[root2];        // update # of items in root1's tree
	      getArray()[root2] = root1;                              // make root1 new root
	    }

  }

  @Override
  public String toString() {
    String string = "";
    for(int i : getArray()) {
      string += i + " ";
    }
    return string;
  }



public int[] getArray() {
	return array;
}



public void setArray(int[] array) {
	this.array = array;
}

}