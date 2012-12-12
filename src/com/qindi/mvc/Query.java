/*
    Copyright (c) 2008-2009, Prashanta Shrestha All Rights Reserved.
    Available under GNU Lesser General Public License >= 3.0 as published by the Free Software Foundation.
    see <http://www.gnu.org/licenses/>
*/
package com.qindi.mvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

import com.qindi.mvc.beans.Const;

/**
 * Class to handler all request functions
 * @author Prashanta Shrestha
 *
 */
public class Query{
	
	public static int getRequestType(HttpServletRequest request) 
	{											
		return ( request.getParameter("_rt") == null)? Const.OTHER: Integer.parseInt(request.getParameter("_rt"));
	}	
	/**
	 * Get event id from request
	 * @return	Event id
	 */
	public static String getEventId(HttpServletRequest request)
	{
		String e = request.getParameter("eid") == null? Const.DEFAULTEVENT : request.getParameter("eid");				
		return (e.indexOf(Const.DELIMITER)==-1)? Const.DEFAULTNS + Const.DELIMITER + e : e;
	}	
	/**
	 * Get method name from event id
	 * @return
	 */
	/*public static String getEventMethodName(HttpServletRequest request)
	{
		//return StringEx.toCamelCase(this.getEventId().substring(this.getEventId().indexOf(Const.DELIMITER)+1));
		return getEventId(request).substring(getEventId(request).indexOf(Const.DELIMITER)+1);
	}*/
	/**
	 * Check if request made by AJAX
	 * @return	True if AJAX request
	 */
	public static boolean isRequestAjax(HttpServletRequest request)
	{		
		return getRequestType(request) == Const.AJAX ? true : false;
	}	
	/**
	 * Check if request made by iFrame
	 * @return	True if iFrame request
	 */
	public static boolean isRequestIFrame(HttpServletRequest request)
	{
		return getRequestType(request) == Const.IFRAME ? true : false;
	}	
	/**
	 * Check if request made other ways
	 * @return	True if yes
	 */
	public static boolean isRequestOther(HttpServletRequest request)
	{
		return getRequestType(request) == Const.OTHER ? true : false;
	}	
	/**
	 * Get error message
	 * @return
	 */
	public static String getErrorMessage(HttpServletRequest request)
	{
		return (String)request.getAttribute("errorMsg");
	}	
	/**
	 * Set error message
	 * @param msg
	 */
	public static HttpServletRequest setErrorMessage(HttpServletRequest request, Exception e)
	{		
		request.setAttribute("errorMsg" , e.getCause().toString());
		return request;
	}	
	/**
	 * Assign ResponseHandler to request 
	 * @param handler
	 */
	public static HttpServletRequest setResponseHandler(HttpServletRequest request, Reply handler)
	{		
		request.setAttribute("responseHandler", handler);
		return request;
	}	
	
	/**
	 * Extract string from request
	 * @param request
	 * @param key
	 * @return	String value if key present otherwise null
	 */
	public  static String getParamString(HttpServletRequest request, String key)
	{				
		return request.getParameter(key);
	}	
	/**
	 * Extract string from ServletFileUpload
	 * @param items
	 * @param key
	 * @return
	 */
	public static String getParamString(HttpServletRequest request, List<FileItem> items , String key)
	{						
		Iterator<FileItem> iter = items.iterator();
		while (iter.hasNext()) {
		    FileItem item = (FileItem) iter.next();		    
		    if (item.isFormField()) {
		    	if( item.getFieldName().equals(key) )
		    		return item.getString();		    
		    }
		}
		return null;
	}
	
	/**
	 * Extract integer from request
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public static int getParamInt(HttpServletRequest request, String key) throws Exception
	{		
		String val =request.getParameter(key);
		if(val == null)
			throw new Exception("No such parameter exists in request: " + key);
		else
			return Integer.parseInt( val );	
	}
	/**
	 * Extract integer from ServletFileUpload
	 * @param items
	 * @param key
	 * @return
	 */
	public static int getParamInt(HttpServletRequest request, List<FileItem> items , String key)
	{						
		Iterator<FileItem> iter = items.iterator();
		while (iter.hasNext()) {
		    FileItem item = (FileItem) iter.next();		    
		    if (item.isFormField()) {
		    	if( item.getFieldName().equals(key) )
		    		return Integer.parseInt( item.getString() );	
		    }
		}
		return -1;
	}
	
	/**
	 * Extract float from request
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public static float getParamFloat(HttpServletRequest request, String key) throws Exception
	{		
		String val =request.getParameter(key);
		if(val == null)
			throw new Exception("No such parameter exists in request: " + key);
		else
			return Float.parseFloat( val );	
	}
	
	/**
	 * Extract float from ServletFileUpload
	 * @param items
	 * @param key
	 * @return
	 */
	public static float getParamFloat(HttpServletRequest request, List<FileItem> items , String key)
	{						
		Iterator<FileItem> iter = items.iterator();
		while (iter.hasNext()) {
		    FileItem item = (FileItem) iter.next();		    
		    if (item.isFormField()) {
		    	if( item.getFieldName().equals(key) )
		    		return Float.parseFloat( item.getString() );	
		    }
		}
		return -1;
	}
	
	/**
	 * Extract date from request
	 * @param request
	 * @param key
	 * @return
	 * @throws ParseException
	 */
	public static Date getParamDate(HttpServletRequest request, String key ) throws ParseException
	{
		String temp = request.getParameter(key);
		if(temp == null)
			return null;
		else
			return new SimpleDateFormat("yyyy-MM-dd" , Locale.ENGLISH).parse(  (String)request.getParameter(key) );	
	}

	/**
	 * Extract date from ServletFileUpload
	 * @param items
	 * @param key
	 * @return
	 * @throws ParseException
	 */
	public static Date getParamDate(HttpServletRequest request, List<FileItem> items , String key) throws ParseException
	{				
		
		Iterator<FileItem> iter = items.iterator();
		while (iter.hasNext()) {
		    FileItem item = (FileItem) iter.next();		    
		    if (item.isFormField()) {
		    	if( item.getFieldName().equals(key) )
		    		return new SimpleDateFormat("yyyy-MM-dd" , Locale.ENGLISH).parse(  item.getString() );	
		    }
		}
		return null;
	}
	
	/**
	 * Extract time from request
	 * @param request
	 * @param key
	 * @return
	 * @throws ParseException
	 */
	public static Date getParamTime(HttpServletRequest request, String key ) throws ParseException
	{
		String temp = request.getParameter(key);
		if(temp == null)
			return null;
		else
			return new SimpleDateFormat("HH:mm:ss" , Locale.ENGLISH).parse(  (String)request.getParameter(key).substring(1) );	
	}
	
	/**
	 * Extract time from ServletFileUpload
	 * @param items
	 * @param key
	 * @return
	 * @throws ParseException
	 */
	public static Date getParamTime(HttpServletRequest request, List<FileItem> items , String key) throws ParseException
	{				
		
		Iterator<FileItem> iter = items.iterator();
		while (iter.hasNext()) {
		    FileItem item = (FileItem) iter.next();		    
		    if (item.isFormField()) {
		    	if( item.getFieldName().equals(key) )
		    		return new SimpleDateFormat("HH:mm:ss" , Locale.ENGLISH).parse(  item.getString() );	
		    }
		}
		return null;
	}
	
	/**
	 * Extract boolean from request
	 * @param request
	 * @param key
	 * @return
	 */
	public static boolean getParamBoolean(HttpServletRequest request , String key)
	{
		String bool = request.getParameter(key);	
		String temp = request.getParameter(key);	
		if(temp == null)
			return false;
		else{
			bool = bool.toLowerCase();
			if((bool.equals("yes")) || (bool.equals("true")) || (bool.equals("1")))
				return true;			
			return false;
		}
	}
	
	/**
	 * Extract boolean from ServletFileUpload
	 * @param items
	 * @param key
	 * @return
	 * @throws ParseException
	 */
	public static boolean getParamBoolean(HttpServletRequest request, List<FileItem> items , String key) throws ParseException
	{						
		Iterator<FileItem> iter = items.iterator();
		while (iter.hasNext()) {
		    FileItem item = (FileItem) iter.next();		    
		    if (item.isFormField()) {
		    	if( item.getFieldName().equals(key) ){
		    		if((item.getString().equals("yes")) || (item.getString().equals("true")) || (item.getString().equals("1")))
		    			return true; 
		    		else 
		    			return false;
		    	}
		    }
		}
		return false;
	}
	
	/**
	 * Extract file from ServletFileUpload
	 * @param items
	 * @param index
	 * @return
	 * @throws ParseException
	 */
	public static FileItem getParamFile(HttpServletRequest request, List<FileItem> items , int index) throws ParseException
	{				
		int i=0;
		Iterator<FileItem> iter = items.iterator();
		while (iter.hasNext()) {
		    FileItem item = (FileItem) iter.next();		    
		    if ( !item.isFormField() ) {
		    	if(index == i)		  
		    		return item;
		    	else
		    		i++;
		    }
		}
		return null;
	}
}


