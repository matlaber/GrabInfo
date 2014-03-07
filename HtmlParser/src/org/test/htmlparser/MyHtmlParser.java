package org.test.htmlparser;

import java.io.BufferedReader;
import java.io.Reader;

public class MyHtmlParser {
	public static MyPos findTag(Reader r, String tagName, 
			MyPos startPos, MyPos endPos) {
		int startR = startPos.getRow();
		int startC = startPos.getCol();		
		try {
			BufferedReader br = new BufferedReader(r);
			String line;
			for(int i = 0; i < startR; i++) {
				line = br.readLine();
			}
			// parse the startR line ...
			line = br.readLine();
			String tailStr = line.substring(startC);
			int foundR = startR;
			int foundC = tailStr.indexOf(tagName);
			if( foundC >= 0 )
				return new MyPos(foundR, foundC);
			// ... else try to find the tag in the following text.			
			if(endPos != null) {
				int endR = endPos.getRow();
				int endC = endPos.getCol();
				while((line = br.readLine()) != null && foundR < endR) {
					foundR++;
					foundC = line.indexOf(tagName);
					if( foundR < endR ) {
						if ( foundC >= 0 ) {
							return new MyPos(foundR, foundC);
						}
					} else if ( foundR == endR ) {
						if ( foundC > endC ) {
							return new MyPos(foundR, foundC);
						}
					}
				}					
			} else { 
				while( (line = br.readLine()) != null ) {
					foundR++;
					foundC = line.indexOf(tagName);
					if (foundC >= 0 ) {
						return new MyPos(foundR, foundC);
					}
				}		
			}
		} catch(Exception e) { 
			e.printStackTrace();
		}		
		// not found
		return null;
	}
	
	public static MyPos findStartTag(Reader r, String tagName, 
			MyPos startPos, MyPos endPos) {
		String prefixTagName = "<" + tagName;
		return findTag(r, prefixTagName, startPos, endPos);
	}
	
	public static MyPos findEndTag(Reader r, String tagName, 
			MyPos startPos, MyPos endPos) {
		String postfixTagName = "</" + tagName;
		return findTag(r, postfixTagName, startPos, endPos);
	}
	
	public static MyPos findTagBetweenPos(Reader r, String tagName, 
			MyPos startPos, MyPos endPos) {		
		return findTag(r, tagName, startPos, endPos);
	}
}
