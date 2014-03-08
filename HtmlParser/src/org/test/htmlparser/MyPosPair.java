package org.test.htmlparser;

public class MyPosPair {
	MyPos startPos;
	MyPos endPos;
	
	MyPosPair() {
		startPos = new MyPos();
		endPos = new MyPos();
	}
	
	MyPosPair(MyPos sp, MyPos ep) {
		startPos = new MyPos(sp.getRow(), sp.getCol());
		endPos = new MyPos(ep.getRow(), ep.getCol());
	}
	
	void setStartPos(MyPos sp) {
		startPos.setRow(sp.getRow());
		startPos.setCol(sp.getCol());
	}
	
	void setEndPos(MyPos ep) {
		endPos.setRow(ep.getRow());
		endPos.setCol(ep.getCol());
	}
	
	MyPos getStartPos() {
		return startPos;
	}
	
	MyPos getEndPos() {
		return endPos;
	}
}
