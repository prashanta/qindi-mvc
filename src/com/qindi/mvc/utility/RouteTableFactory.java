package com.qindi.mvc.utility;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class RouteTableFactory {
	
	public  Document 	dom;
	@SuppressWarnings("unused")
	private String 		filename;
	/* ================================================================================
	 * Constructor
	 * ================================================================================*/		
	
	public RouteTableFactory(String filename) throws Exception {
		File f = new File(filename);
		this.filename = filename;
		try {
			this.dom = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);		
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {			
			//e.printStackTrace();
			throw new Exception("Config file not found - " + e.getMessage());
		} catch (ParserConfigurationException e) {			
			e.printStackTrace();
		}
	}
	
	
			
	/* ================================================================================
	 * Unit Test
	 * ================================================================================*/		
	public static void main(String[] args) throws TransformerFactoryConfigurationError, Exception {

		RouteTableFactory x = new RouteTableFactory("WebRoot\\qindi.events.xml");
		Element docEle = x.dom.getDocumentElement();
		System.out.println(" -- " + docEle.getNodeName());
		System.out.println(" -- " + docEle.getNodeType());
		System.out.println(" -- " + docEle.getChildNodes().getLength());
		NodeList nl = docEle.getElementsByTagName("event-handler");		
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {					
				Element el = (Element)nl.item(i);		
				System.out.println("class: " + el.getAttribute("class"));				
				NodeList nodes = el.getElementsByTagName("event-method");								
				if(nodes != null && nodes.getLength() > 0) {
					for(int j = 0 ; j < nodes.getLength() ;  j++){ 					
						System.out.println("method: " + nodes.item(j).getFirstChild().getNodeValue());				
						System.out.println("view: " + ((Element)nodes.item(j)).getAttribute("view"));
					}	
				}
			}
		}
		

	}
}
