/*
    Copyright (c) 2008-2009, Prashanta Shrestha All Rights Reserved.
    Available under GNU Lesser General Public License >= 3.0 as published by the Free Software Foundation.
    see <http://www.gnu.org/licenses/>
*/
package com.qindi.mvc.beans;

import com.qindi.mvc.handler.EventHandler;


/**
 * Handler class that identifies event handler class and view generator file.
 * 
 * @author	Prashanta Shrestha
 * @version	0.2
 */
public class Handler {
	
	/**
	 *	Holds name of handler class 
	 */
	private String handlerClass;	
	/**
	 *	Holds URL of view generator JSP file
	 */
	private String viewURL;	
	/**
	 * Holds verification error code
	 */
	private int error;
	/**
	 * Constructor 
	 */
	public Handler(){
		handlerClass 	= null;
		viewURL			= null;	
	}	
	/**
	 * Constructor with initial parameter values
	 * 
	 * @param actionClass	name of class that holds action handler
	 * @param viewURL		URL of JSP file that handle generation of view part
	 */
	public Handler(String className , String url){
		handlerClass 	= className;
		viewURL			= url;	
	}

	/**
	 * Get name of handler class
	 * @return	name of handler class
	 */
	public String getHandlerClass() {
		return handlerClass;
	}

	public void setHandlerClass(String name) {
		handlerClass = name;
	}
	
	public EventHandler getHandlerClassInstance() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return (EventHandler)Class.forName(handlerClass).newInstance();
	}
	
	public String getViewURL() {
		return viewURL;
	}

	public void setViewURL(String url) {
		viewURL = url;
	}
	
	public boolean hasHandlerClass(){
		return handlerClass == null? false : true;
	}
	
	public boolean hasViewURL(){
		return viewURL == null ? false : true;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}
		
}
