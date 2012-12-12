package com.qindi.mvc.utility;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class Tester {

	
	public static void main(String[] args) {
		try{
			XMLConfiguration config = new XMLConfiguration();
			config.setFile(new File("WebRoot/WEB-INF/qindi.config.xml"));
			config.load();
		    System.out.println("title = " + config.getString("title"));
		    System.out.println("event-file = " + config.getString("event-file"));
		    System.out.println("event-file = " + config.getString("event-file[@name]"));
		    System.out.println("event-file = " + config.getString("event-file[@verbose]"));
		    System.out.println("event-file = " + config.getString("database[@username]"));
		    System.out.println("event-file = " + config.getString("database[@password]"));
		    System.out.println("event-file = " + config.getString("database"));
		    		   		    
		}catch(ConfigurationException e){
			e.printStackTrace();
		}
		/*Field[] fi = Basic.class.getDeclaredFields();
		System.out.println( "len = " + fi.length);
		
		for (Field field : fi) 
		{
			Annotation[] a = field.getAnnotations();
			System.out.println( "len = " + a.length);
			
			for (Annotation aa : a) 
			{
				if(aa instanceof Constraint){
					Constraint c = (Constraint)aa; 
					System.out.println( c.maxLength());
					System.out.println( c.minLength());
					System.out.println( c.empty());
					System.out.println( c.warn());
				}
			}
		}*/
		// iterate over the annotations to locate the MaxLength constraint if it exists
		
	}
}
