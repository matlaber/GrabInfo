package org.test.htmlparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;

public class MyHtmlParser {
	public static MyPos findTag(String filename, String tagName, 
			MyPos startPos, MyPos endPos) {
		int startR = startPos.getRow();
		int startC = startPos.getCol();		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			for(int i = 0; i < startR; i++) {
				line = br.readLine();
			}
			// parse the startR line ...
			line = br.readLine();
			String tailStr = line.substring(startC);
			int foundR = startR;
			int foundC = tailStr.indexOf(tagName);
			if( foundC >= 0 ) {
				br.close();
				return new MyPos(foundR, startC + foundC);
			}
			// ... else try to find the tag in the following text.			
			if(endPos != null) {
				int endR = endPos.getRow();
				int endC = endPos.getCol();
				while((line = br.readLine()) != null && foundR < endR) {
					foundR++;
					foundC = line.indexOf(tagName);
					if( foundR < endR ) {
						if ( foundC >= 0 ) {
							br.close();
							return new MyPos(foundR, foundC);
						}
					} else if ( foundR == endR ) {
						if ( foundC > endC ) {
							br.close();
							return new MyPos(foundR, foundC);
						}
					}
				}					
			} else { 
				while( (line = br.readLine()) != null ) {
					foundR++;
					foundC = line.indexOf(tagName);
					if (foundC >= 0 ) {
						br.close();
						return new MyPos(foundR, foundC);
					}
				}				
			}
			br.close();
		} catch(Exception e) {
			
			e.printStackTrace();
		}		
		// not found		
		return null;
	}
	
	public static MyPos findStartTag(String filename, String tagName, 
			MyPos startPos, MyPos endPos) {
		String prefixTagName = "<" + tagName;
		return findTag(filename, prefixTagName, startPos, endPos);
	}
	
	public static MyPos findEndTag(String filename, String tagName, 
			MyPos startPos, MyPos endPos) {
		String postfixTagName = "</" + tagName;
		return findTag(filename, postfixTagName, startPos, endPos);
	}
	
	public static MyPos findTagBetweenPos(String filename, String tagName, 
			MyPos startPos, MyPos endPos) {		
		return findTag(filename, tagName, startPos, endPos);
	}
	
	// This is a function to show the extract text, also can be used for debug.
	public static String getInternalText(String filename, 
			MyPos startPos, MyPos endPos) {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			StringBuilder text = new StringBuilder();
			int startR = startPos.getRow();
			int startC = startPos.getCol();
			int endR = endPos.getRow();
			int endC = endPos.getCol();
			for(int i = 0; i < startR; i++) {
				br.readLine(); // skip the first startR lines 
			}
			// At line startR, read from startC to the end of line
			line = br.readLine();
			text.append(line.substring(startC));			
			for(int currentR = startR; currentR < endR; currentR++) {
				line = br.readLine();
				text.append(line);
			}
			// At line startC, append the string from 0 to endC
			line = br.readLine();
			text.append(line.substring(0, endC));
			br.close();
			return text.toString();			
		} catch (Exception e) {			
			e.printStackTrace();
			return null;
		}
		
		
	}
}
