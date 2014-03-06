package org.test.htmlparser;

import javax.swing.text.html.HTMLEditorKit;

public class HTMLParseLister extends HTMLEditorKit.ParserCallback{
	int indentSize = 0;
	
	protected void indent() {
		indentSize += 3;
	}
	
	protected void unindent() {
		indentSize -= 3;
		if(indentSize < 0) 
			indentSize = 0;
	}
}
