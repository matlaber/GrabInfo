package org.test.htmlparser;

public class MyPos {
	int row;
	int col;
	
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
}
