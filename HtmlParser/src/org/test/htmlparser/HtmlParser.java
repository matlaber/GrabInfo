package org.test.htmlparser;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

import org.w3c.dom.html.*;

public class HtmlParser {
	public static void main(String [] args) {
		Reader r;
		if( args.length == 0) {
			System.err.println("Usage: java HtmlParser [url | file]");
			System.exit(-1);
		}
		String spec = args[0];
		try {
			if(spec.indexOf("://") > 0) {
				URL u = new URL(spec);
				Object content = u.getContent();
				if( content instanceof InputStream) {
					r = new InputStreamReader((InputStream)content);					
				} else if (content instanceof Reader) {
					r = (Reader)content;
				} else {
					throw new Exception("Bad URL content type.");
				}
			} else {
				r = new FileReader(spec);
			}
			// Swing parser
//			HTMLEditorKit.Parser parser;
//			System.out.println("About to parse " + spec);
//			parser = new ParserDelegator();
//			parser.parse(r, new HTMLParseLister(), true);			
//			r.close();
			// Read and parse
			/*
			MyPos firstStartOccur = MyHtmlParser.findStartTag(r, "div", new MyPos(0,0), null);
			r = new FileReader(spec);			
			MyPos firstEndOccur = MyHtmlParser.findEndTag(r, "div", new MyPos(0,0), null);
			r = new FileReader(spec);
			MyPos firstStartH3Occur = MyHtmlParser.findTagBetweenPos(r, 
					"<h3", firstStartOccur, firstEndOccur);
			r = new FileReader(spec);
			MyPos firstEndH3Occur = MyHtmlParser.findTagBetweenPos(r, 
					">", firstStartH3Occur, firstEndOccur);
			System.out.println(firstStartOccur.getRow() + ", " + firstStartOccur.getCol());
			System.out.println(firstEndOccur.getRow() + ", " + firstEndOccur.getCol());
			System.out.println(firstStartH3Occur.getRow() + ", " + firstStartH3Occur.getCol());
			System.out.println(firstEndH3Occur.getRow() + ", " + firstEndH3Occur.getCol());
			*/
			
			MyPos currentPos = new MyPos(0,0);
			MyPos endPos = null;
			
			
//			try {
//				while(currentPos != null) {					
//					MyPos divStartOccur = MyHtmlParser.findStartTag(
//							spec, "div", currentPos, endPos);					
//					MyPos divEndOccur = MyHtmlParser.findEndTag(
//							spec, "div", divStartOccur, endPos);					
//					MyPos idOccur = MyHtmlParser.findTagBetweenPos(
//							spec, "id=", divStartOccur, divEndOccur);
//					if(idOccur != null) {
//						System.out.println(idOccur.getRow() + ", " + idOccur.getCol());
//					}
//					currentPos = divEndOccur;
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			
			try {
				PairFinder pf = new PairFinder("div");
				pf.makeTagPairs(spec);
				ArrayList<MyPosPair> listOfPos = pf.getMyPosPairList();
//				for(int i = 0; i < listOfPos.size(); i++) {
//					MyPosPair cmpp = listOfPos.get(i);
//					System.out.println(cmpp.getStartPos().getRow() +
//							", " + cmpp.getStartPos().getCol() +
//							", " + cmpp.getEndPos().getRow() +
//							", " + cmpp.getEndPos().getCol() );
//				}
//				System.out.println("Found complete");
				
				for(int i = 0; i < listOfPos.size(); i++) {
					MyPosPair cmpp = listOfPos.get(i);
					String divText = MyHtmlParser.getInternalText(
							spec, cmpp.getStartPos(), cmpp.getEndPos());
					Pattern digPtn = Pattern.compile("[0-9]*");
					int idIndex = divText.indexOf(" id=");
					if(idIndex > 0) {

						String idSubStr = divText.substring(idIndex);
						int firstQuotePos = idSubStr.indexOf('"');
						int lastQuotePos = idSubStr.indexOf('"', firstQuotePos+1);
						String digStr = idSubStr.substring(firstQuotePos+1, lastQuotePos);
						if(digPtn.matcher(digStr).matches()) {
							System.out.println(idSubStr);							
							// find h3 and the internal "a href"
							int h3Index = divText.indexOf("<h3");							
							if(h3Index >= 0) {
								String h3SubStr = 
										divText.substring(h3Index);
								int ahrefIndex = h3SubStr.indexOf(" href=");
								int lastQuoteIndex = h3SubStr.indexOf('"', ahrefIndex + (" href=").length() + 1);
								System.out.println(h3SubStr.substring
										(ahrefIndex, lastQuoteIndex));
								System.out.println("----------------------------------");
								
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			// get the internal text.
			
		} catch (Exception e) {
			System.err.println("Error: " + e);
			e.printStackTrace(System.err);
		}
	}

}
