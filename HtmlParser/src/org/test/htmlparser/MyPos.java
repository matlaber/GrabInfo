package org.test.htmlparser;

public class MyPos {
	int row;
	int col;
	
	MyPos() {
		row = 0;
		col = 0;
	}
	
	MyPos(int r, int c) {
		row = r;
		col = c;
	}
	
	public void setRow(int r) { 
		row = r; 
	}
	
	public void setCol(int c) { 
		col = c; 
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public int compare(MyPos anotherMyPos) {
		if (this.getRow() < anotherMyPos.getRow()) 
			return -1;
		else if (this.getRow() == anotherMyPos.getRow())
			if (this.getCol() < anotherMyPos.getCol())
				return -1;
			else if (this.getCol() == anotherMyPos.getCol())
				return 0;
			else // if (this.getCol() == anotherMyPos.getCol())
				return 1;
		else // if (this.getRow() > anotherMyPos.getRow())
			return 1;		
	}
}
