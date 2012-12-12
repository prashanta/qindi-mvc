/*
    Copyright (c) 2008-2009, Prashanta Shrestha All Rights Reserved.
    Available under GNU Lesser General Public License >= 3.0 as published by the Free Software Foundation.
    see <http://www.gnu.org/licenses/>
*/
package com.qindi.mvc.beans;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.HierarchicalConfiguration.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qindi.mvc.Query;
import com.qindi.mvc.Reply;
import com.qindi.mvc.handler.EventHandler;
import com.qindi.mvc.handler.Map;

/**
 * Class that maps namespace.eventid to handler; 
 * parses event configuration file and performs other event mapping related functions.
 * Extended from HashMap.
 * 
 * @author Prashanta Shrestha
 */
public class Mapper extends HashMap<String , Handler>
{	
	private static final long serialVersionUID = 1L;

	Logger log = LoggerFactory.getLogger(Mapper.class);
	
	/**
	 * Constructor
	 * @throws Exception 
	 */
	public Mapper(String file) throws Exception
	{
		initMap(file);		
	}		
	public Mapper() throws Exception
	{				
	}	
	/**
	 * Populate map
	 * @param file
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void initMap(String filename)
	{		
		try
		{												
			XMLConfiguration cfg = new XMLConfiguration();
			cfg.setFile(new File(filename));
			cfg.load();											
			if(cfg.getRoot().getChildrenCount() > 0)
			{
				int cCount = 1;
				for(Node eh: (List<Node>)(cfg.getRoot().getChildren()))
				{
					if(eh.getAttributeCount(Const.CL) > 0)
					{
						String c = (String)((Node)(eh.getAttributes(Const.CL).get(0))).getValue();
						if(verifyClass(c) == 0)
						{
							String ns = eh.getAttributes(Const.NS).size()>0? (String)((Node)(eh.getAttributes(Const.NS).get(0))).getValue() : Const.DEFAULTNS;
							String v = eh.getAttributes(Const.VW).size()>0? (String)((Node)(eh.getAttributes(Const.VW).get(0))).getValue() : null;							
							if(eh.getChildrenCount(Const.EI) > 0)
							{
								for(Node ei: (List<Node>)eh.getChildren(Const.EI))
								{						
									v = ei.getAttributes(Const.VW).size()>0? (String)((Node)(ei.getAttributes(Const.VW).get(0))).getValue() : v;
									String m = (String)ei.getValue();
									Handler h = new Handler( c , v );		
									int vm = verifyMethod(c, m); 
									if(vm == 0)
									{
										h.setError(verifyEvent(ns+Const.DELIMITER+m));
										this.addEventHandler(m, ns, h);																
									}
									else
										log.error("METHOD NOT FOUND - " + m + " = " + vm);										
								}
							}else
							{							
								EventHandler eventHandler = (EventHandler)Class.forName(c).newInstance();
								for(Method m : eventHandler.getClass().getMethods())
								{					
									String methodName = m.getName();
									//TODO Strict return value or returns anything ?  //if(m.getReturnType().toString().equals(Const.REPLY))								
									String viewUrl = null;
									boolean include = false;									
									for(Annotation a : m.getAnnotations())
									{
										if(a instanceof Map)
										{
											Map mp = (Map)a; 
											include = mp.include();
											viewUrl = mp.viewUrl();
											break;
										}	
									}
									if(include)
									{										
										Handler h = new Handler( c , (viewUrl==null||viewUrl.length()==0)? v : viewUrl );
										h.setError(verifyEvent(ns+Const.DELIMITER+methodName));
										this.addEventHandler(methodName , ns , h );																				
									}									
								}																				
							}
						}
						else
						{
							log.error("CLASS INSTANCE NOT FOUND - " + c );
						}
					}
					else{
						log.error("NO CLASS ATTRIBUTE AT event-handler ["+(cCount-1)+"]");
					}
					cCount++;
				}				
				this.print();
			}
			else
			{
				log.error("NO event-handler FOUND, THERE SHOULD BE AT LEAST ONE");
			}
		}catch(ConfigurationException e){
			e.printStackTrace();
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	/**
	 * Verify existance of class from reflection
	 * @param className
	 * @return
	 */
	public int verifyClass(String className){
		try {
			Class.forName(className).newInstance();
		} catch (InstantiationException e) {			
			return -5;
		} catch (IllegalAccessException e) {
			return -4;
		} catch (ClassNotFoundException e) {
			return -3;
		}				
		return 0;		
	}
	public int verifyMethod(String className, String method)
	{				
		if(verifyClass(className) >= 0)
		{			
			try {
				EventHandler eh = (EventHandler)Class.forName(className).newInstance();
				eh.getClass().getDeclaredMethod(method, new Class[]{ServletContext.class , HttpServletRequest.class , HttpServletResponse.class } );
			}catch (SecurityException e) {
				return -6;
			}catch (InstantiationException e) {			
				//e.printStackTrace();
				return -5; 
			}catch (IllegalAccessException e) {
				//e.printStackTrace();
				return -4; 
			}catch (ClassNotFoundException e) {			
				return -3;
			}catch (NoSuchMethodException e) {
				//e.printStackTrace();
				return -2;
			}		
			return 0;
		}else
			return -3;
	}		
	/**
	 * Validate event
	 * 
	 * @param	nsevent	name of event to validate
	 * @param	handler	name of handler class
	 * 
	 * @return			<ul>	
	 * 					<li>  1 : Event handler class found, requested method handler found 
	 * 					<li>  2 : Event handler class found, default process method found
	 * 					<li> -1 : No such event handler
	 * 					<li> -2 : Method not found
	 * 					<li> -3 : Class not found	 
	 * 					<li> -4 : Illegal access  
	 * 					<li> -5 : Instantiation failed 
	 * 					<li> -6 : Security violation
	 * 					</ul>			
	 */
	@SuppressWarnings("unused")
	public int verifyEvent(String nsevent)
	{	
		String className = getHandlerClass(nsevent);
		if(className == null || className.length()==0)
			return -1;		
		try {						
			Method method  = getMethodInstance(nsevent);
		}catch (SecurityException e) {
			return -6;
		}catch (InstantiationException e) {			
			e.printStackTrace();
			return -5; 
		}catch (IllegalAccessException e) {
		//	e.printStackTrace();
			return -4; 
		}catch (ClassNotFoundException e) {			
			return -3;
		}catch (NoSuchMethodException e) {						
			try {
				@SuppressWarnings("unused")				
				Method method = getDefaultMethodInstance(nsevent);
			} catch (SecurityException e1) {					
				return -6;
			} catch (InstantiationException e1) {
				e1.printStackTrace();
				return -5;
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
				return -4;
			} catch (ClassNotFoundException e1) {				
				return -3;
			} catch (NoSuchMethodException e1) {	
				e1.printStackTrace();
				return -2;	
			}		
			return 2;
		}  		
		return 1;
	}		
	/**
	 * Add mapping of event handler to list
	 * @param event
	 * @param namespace
	 * @param handlerClass
	 * @param viewURL
	 */
	public void addEventHandler(String event, String namespace , Handler handler)
	{	
		this.put( namespace+Const.DELIMITER+event, handler );		
	}		
	/**
	 * Get handler class name
	 * @param nsevent
	 * @return
	 */
	public String getHandlerClass(String nsevent)
	{			
		return this.get(nsevent)== null? null : this.get(nsevent).getHandlerClass();
	}		
	/**
	 * Get instance of appropriate event handler class 
	 * @param nsevent
	 * @return
	 * @throws Exception 
	 */
	public EventHandler getEventHandler(String nsevent) throws Exception
	{		
		EventHandler eventHandler = null;		
		if(!this.containsKey(nsevent)){		
			nsevent = Const.DEFAULTNS+Const.DELIMITER+"unknownEvent";			
		}		
		try 
		{			
			eventHandler = get(nsevent).getHandlerClassInstance();			
		}catch (InstantiationException e) { e.printStackTrace();
		}catch (IllegalAccessException e) { e.printStackTrace();
		}catch (ClassNotFoundException e) 
		{		
			eventHandler = null;			
			try 
			{		
				eventHandler = get(Const.DEFAULTNS+Const.DELIMITER+"unknownEvent").getHandlerClassInstance();
			}catch (Exception e1) 
			{	
				eventHandler = null;
				e1.printStackTrace();	
				throw new Exception("UnknownEvent handler not found.");
			} 
		}
		return eventHandler;		
	}			
	/**
	 * Get instance of method handler
	 * @param nsevent
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public Method getMethodInstance(String nsevent) throws SecurityException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{		
		String methodName = nsevent.substring(nsevent.indexOf(Const.DELIMITER)+1);		
		return get(nsevent).getHandlerClassInstance().getClass().getDeclaredMethod( methodName , new Class[]{ServletContext.class , Query.class , Reply.class } );		
	}		
	/**
	 * Get instance of default process method
	 * @param event
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public Method getDefaultMethodInstance(String nsevent) throws SecurityException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{		
		return get(nsevent).getHandlerClassInstance().getClass().getDeclaredMethod( Const.DEFAULMETHOD , new Class[]{ServletContext.class , HttpServletRequest.class , HttpServletResponse.class } );
	}
	/**
	 * Get URL of view handler
	 * @param nsevent
	 * @return
	 */
	public String getUrl(String nsevent)
	{
		return this.get(nsevent)==null? null : this.get(nsevent).getViewURL();	
	}
	
	public void print()
	{	
		HashMap<String, Handler> tempMap = sortHashMap();
		for(Iterator<String> i = tempMap.keySet().iterator(); i.hasNext() ; ){
			String m = i.next();
			Handler h = tempMap.get(m);			 
			log.info("[" + h.getHandlerClass() + "] == [" + h.getViewURL() + "] == [" + m + "]");					
		}
	}
	public HashMap<String, Handler> sortHashMap(){
		HashMap<String, Handler> tempMap = new HashMap<String, Handler>();
	    for (String wsState : this.keySet()){
	        tempMap.put(wsState,this.get(wsState));
	    }

	    List<String> mapKeys = new ArrayList<String>(tempMap.keySet());
	    List<Handler> mapValues = new ArrayList<Handler>(tempMap.values());
	    HashMap<String, Handler> sortedMap = new LinkedHashMap<String, Handler>();
	    TreeSet<String> sortedSet = new TreeSet<String>(mapKeys);
	    Object[] sortedArray = sortedSet.toArray();
	    int size = sortedArray.length;
	    for (int i=0; i<size; i++){
	        sortedMap.put(	(String)sortedArray[i], mapValues.get(mapKeys.indexOf(sortedArray[i])) );
	    }
	    return sortedMap;
	}
}