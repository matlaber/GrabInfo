package org.test.htmlparser;

import java.io.*;
import java.net.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

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
			
			HTMLEditorKit.Parser parser;
			System.out.println("About to parse " + spec);
			parser = new ParserDelegator();
			parser.parse(r, new HTMLParseLister(), true);			
			r.close();
		} catch (Exception e) {
			System.err.println("Error: " + e);
			e.printStackTrace(System.err);
		}
	}

}
