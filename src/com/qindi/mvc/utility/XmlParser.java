package com.qindi.mvc.utility;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//import com.qindi.mvc.beans.EventMap;

/**
 * Simplified XML parser
 * 
 * @author Prashanta Shrestha
 *
 */
public class XmlParser {
	
	/**
	 * Document object model for navigating XML document
	 */
	public  Document 	dom;
	
	/**
	 * Location of XML file
	 */
	private String 		filename;	
	
	/**
	 * Constructor
	 * @param filename	XML file location
	 * @throws Exception
	 */
	public XmlParser(String filename) throws Exception {
		File f = new File(filename);
		this.filename = filename;
		try {
			this.dom = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);		
		} catch (SAXException e) 				{	e.printStackTrace();
		} catch (IOException e) 				{	e.printStackTrace();	throw new Exception("Config file not found - " + e.getMessage());
		} catch (ParserConfigurationException e){	e.printStackTrace();	}
	}
	
	/**
	 * Get number of child elements under given element.
	 * 
	 * @param el	Element whose child elements to count
	 * @return		Number of child elements
	 */
	public int getChildCount(Element el){
		int l = el.getChildNodes().getLength();
		return l<=1? 0 : (l - 1)/2;
	}
	
	/**
	 * Get number of child elements under given element.
	 * 
	 * @param el	Element whose child elements to count
	 * @param tag	Tag name to filter the child elements
	 * @return		Number of child elements
	 */
	public int getChildCount(Element el , String tag){		
		return el.getElementsByTagName(tag).getLength();
	}	
	
	/**
	 * Get value within an element having a tag.	 
	 * 
	 * @param el	Element form Document
	 * @param tag	Name of tag
	 * @return		Feature value  
	 */
	public String getValue(Element el , String tag , int index){
		String val= null;
		NodeList nl = el.getElementsByTagName(tag);
		if(nl!=null && nl.getLength()>0)
			val = (String)nl.item(index).getFirstChild().getNodeValue();		
		return val;
	}
	
	/**
	 * Get value as integer 
	 * @param el	Element form Document
	 * @param tag	Name of tag
	 * @return		Feature value as integer, if invalid then -1
	 */
	public int getValueInt(Element el , String tag , int index){		
		int val= -1;
		NodeList nl = el.getElementsByTagName(tag);
		if(nl!=null && nl.getLength()>0){
			try{
				val = Integer.parseInt((String)nl.item(index).getFirstChild().getNodeValue());
			}catch(Exception e){
				
			}
		}
		return val;		
	}
	
	/**
	 * Set value for given tag name
	 * 
	 * @param el	Element form Document
	 * @param tag	Name of tag	 
	 * @param value	Feature value to store
	 */
	public void setValue(Element el , String tag , String value){
		NodeList nl = el.getElementsByTagName(tag);
		if(nl!=null && nl.getLength()>0)
			nl.item(0).getFirstChild().setNodeValue(value);
	}
	

	/**
	 * Get attribute of element
	 * @param el
	 * @param tag
	 * @param attribute
	 * @return
	 */
	public String getAttribute(Element el , String tag , int index , String attribute){
		String val= null;
		NodeList nl = el.getElementsByTagName(tag);				
		if(nl!=null && nl.getLength()>0){
			NamedNodeMap attrs = nl.item(index).getAttributes();
	        for(int i = 0; i < attrs.getLength(); i++) {  
	            if( attrs.item(i).getNodeName().equals(attribute))
	            	val = attrs.item(i).getNodeValue();
	        }
		}
		return val;
	}
	
	/**
	 * Save XML
	 * 
	 * @param destination	Location and name of file to store. 
	 */
	public void save(String destination){		
		if(destination == null || destination.length()<=0)
			destination = this.filename;
		Result result = new StreamResult(new File(destination));
        Source source = new DOMSource(this.dom);		
		try {
			TransformerFactory.newInstance().newTransformer().transform(source, result);
		} catch (TransformerConfigurationException e) 	{	e.printStackTrace();
		} catch (TransformerException e) 				{	e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e){	e.printStackTrace();	}
	}
	
	/* ================================================================================
	 * Unit Test
	 * ================================================================================*/		
	/**
	 * @param args
	 * @throws TransformerFactoryConfigurationError
	 * @throws Exception
	 */
	public static void main(String[] args) throws TransformerFactoryConfigurationError, Exception {

		/*Config c =	new Config();	
		c.getConfiguration("WebRoot\\WEB-INF\\qindi.config.xml");*/
		//EventMap m = new EventMap("src\\qindi.events.xml");
	}
}
