package org.test.htmlparser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class PairFinder_201403082021 {
	ArrayList<MyPosPair> posPairList;
	MyPos previousEndPos;
	String tagName;
	PairFinder_201403082021(String tn) {
		posPairList = new ArrayList<MyPosPair>();
		previousEndPos = new MyPos(0,0);
		tagName = tn;
	}
	
	// FIXME: findPair cannot handle SimpleTag now
	private boolean findPartPairs(String filename, MyPos startPos) {
		// BufferedReader br = new BufferedReader(r);		
		Queue<MyPos> spQueue = new LinkedList<MyPos>();
		Stack<MyPos> epStack = new Stack<MyPos>();		
		
		// MyPos sp = new MyPos(); // const, start of the Reader
		MyPos sp = startPos;
		MyPos ep = null; // const
		MyPos csp = sp;
		MyPos cep = ep;		
		
		MyPos tagStartPos = MyHtmlParser.findStartTag(filename, 
				tagName, csp, cep);
		if(tagStartPos == null)
			return false;
		
		
		MyPos tagEndPos = MyHtmlParser.findEndTag(filename, tagName, 
				tagStartPos, cep);
		if(tagEndPos == null)
			return false;		
				
		// find start tags
		for(csp = tagStartPos; csp != null; ) {
			spQueue.offer(csp);
			csp.setCol(csp.getCol() + ("<" + tagName).length());
			csp = MyHtmlParser.findStartTag(filename, tagName, 
					csp, tagEndPos);			
		}
		
		// find corresponding end tags
		int startTagNum = spQueue.size();
		csp = tagEndPos;
		for(int i = 0; i < startTagNum && csp != null; i++) {
			epStack.push(csp); 
			csp.setCol(csp.getCol() + ("</" + tagName).length());
			csp = MyHtmlParser.findEndTag(filename, tagName, 
					csp, cep);				
		}
		
		previousEndPos = epStack.peek();		
		
		while(!epStack.empty()) {
			MyPosPair cpp = new MyPosPair(spQueue.poll(), epStack.pop());
			posPairList.add(cpp);			
		}			
		
		return true;
	}
	
	public void findAllPairs(String filename) {
		boolean pairFound = false;
		MyPos csp = new MyPos(0, 0);
		pairFound = this.findPartPairs(filename, csp);
		while(pairFound) {
			csp = previousEndPos;
			pairFound = this.findPartPairs(filename, csp);
		}		
	}
	
	public ArrayList<MyPosPair> getPosPairList() {
		return posPairList;
	}
}
