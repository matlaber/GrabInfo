package org.test.htmlparser;

import java.util.regex.Pattern;

import javax.print.attribute.AttributeSet;
import javax.print.attribute.DocAttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

public class HTMLParseLister extends HTMLEditorKit.ParserCallback{
	int indentSize = 0;
	boolean inTD = true;
	
	protected void indent() {
		indentSize += 3;
	}
	
	protected void unIndent() {
		indentSize -= 3;
		if(indentSize < 0) 
			indentSize = 0;
	}
	
	protected void pIndent() {
		for(int i = 0; i < indentSize; i++)
			System.out.print(" ");
	}
	
	public void handleText(char[] data, int pos) {
		pIndent();
		if(inTD) {
			System.out.println(data);
		}
		// System.out.println("Text(" + data.length + " chars)");
	}
	
	public void handleComment(char[] data, int pos) {
//		pIndent();
//		System.out.println("Comment(" + data.length + " chars)");
	}
	
	public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
		// DocAttributeSet das = new DocAttributeSet();
		pIndent();
		if(t.toString().equals("div")) {
			String attributes = a.toString();
			String[] attrArray= attributes.split(" ", 0);
			Pattern pattern = Pattern.compile("[0-9]*");
			for(String attr : attrArray ) {				
				if(attr.startsWith("id=") 
						&& pattern.matcher(attr.substring(3)).matches())
					System.out.println("<" + t.toString() + " " + a.toString() + ">");
			}			
		}
//		System.out.println("Tag start(<" + t.toString() + ">), " 
//				+ a.getAttributeCount() + " attrs)");
	}
	
	public void handleEndTag(HTML.Tag t, int pos) {
		unIndent();
		pIndent();
		if(t.toString().equals("div")) {
			System.out.println("</" + t.toString() + ">");
		}
//		System.out.println("Tag end(</" + t.toString() + ">)");
	}
	
	public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
		pIndent();
		if(t.toString().equals("div")) {
			System.out.println("<" + t.toString() + " " + a.toString() + ">");
		}
//		System.out.println("Tag(<" + t.toString() + ">, " 
//				+ a.getAttributeCount() + " attrs)");
	}
	
	public void handleError(String errorMsg, int pos) {
//		System.out.println("Parsing error: " + errorMsg + " at " + pos);
	}
}
