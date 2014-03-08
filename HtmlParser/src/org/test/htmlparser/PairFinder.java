package org.test.htmlparser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

public class PairFinder {
	ArrayList<MyPosPair> posPairList;	
	String tagName;
	ArrayList<MyPos> startPosList;
	ArrayList<MyPos> endPosList;
	
	PairFinder(String tn) {
		posPairList = new ArrayList<MyPosPair>();
		tagName = tn;
		startPosList = new ArrayList<MyPos>();
		endPosList = new ArrayList<MyPos>();
	}
	
	private void findAllTagStartPos(String filename) {
		MyPos csp = null;
		try {
			csp = new MyPos(0, 0);
			MyPos foundPos = MyHtmlParser.findStartTag(filename, tagName, csp, null);
			while(foundPos != null) {				
				startPosList.add(foundPos);
				csp.setRow(foundPos.getRow());
				csp.setCol(foundPos.getCol() + 
						("<" + tagName).length() );	
				foundPos = MyHtmlParser.findStartTag(filename, tagName, csp, null);
			}
		} catch (Exception e) {
			System.out.println("The current Pos for "
					+ "findAllTagStartPos is ("  
					+ csp.getRow() + ", " + csp.getCol() + ")");
			e.printStackTrace();
			
		}		
	}
	
	private void findAllTagEndPos(String filename) {
		MyPos csp = null;;
		try {
			csp = new MyPos(0, 0);
			MyPos foundPos = MyHtmlParser.findEndTag(filename, tagName, csp, null);			
			while(foundPos != null) {				
				endPosList.add(foundPos);
				csp.setRow(foundPos.getRow());
				csp.setCol(foundPos.getCol() + ("</" + tagName).length() );	
				foundPos = MyHtmlParser.findEndTag(filename, tagName, csp, null);
			}
		} catch (Exception e) {
			System.out.println("The current Pos for "
					+ "findAllTagEndPos is ("  
					+ csp.getRow() + ", " + csp.getCol() + ")");
			e.printStackTrace();
		}		
	}
	
	// sort is not needed because the positions are acquired in order.
	
	// This function can be broken into two functions according to the loop.
	public void makeTagPairs(String filename) {	
		// the following functions are ugly.
		findAllTagStartPos(filename);
		findAllTagEndPos(filename);
		for(int i = 0; i < endPosList.size(); i++) {
			MyPos curEndMyPos = endPosList.get(i);
			for(int j = 0; j < startPosList.size(); j++) {
				MyPos preMyPos = startPosList.get(j);				
				if(preMyPos.compare(curEndMyPos) < 0) {
					if(j < startPosList.size() - 1) {
						MyPos postMyPos = startPosList.get(j + 1);
						if(postMyPos.compare(curEndMyPos) > 0) {
							MyPosPair cmpp = new MyPosPair(preMyPos, curEndMyPos);
							posPairList.add(cmpp);
							startPosList.remove(j);
							break;
						}
					} else { // if (j = startPosList.size() + 1) 
						MyPosPair cmpp = new MyPosPair(preMyPos, curEndMyPos);
						posPairList.add(cmpp);
						startPosList.remove(j);
						break;						
					}						
				}
			}			
		}
	}
	
	public ArrayList<MyPosPair> getMyPosPairList() {
		return posPairList;
	}
}
