/*
    Copyright (c) 2008-2009, Prashanta Shrestha All Rights Reserved.
    Available under GNU Lesser General Public License >= 3.0 as published by the Free Software Foundation.
    see <http://www.gnu.org/licenses/>
*/
package com.qindi.mvc.handler;

import javax.servlet.http.HttpServletRequest;

import com.qindi.mvc.Query;

/**
 * Event handler class that services requests to the controller servlet.
 * Note: Child of this class cannot be extended to a subclass.  
 * 
 * @author 	Prashanta Shrestha
 * @version 0.2
 *
 */

public abstract class EventHandler extends Query{			
	/**
	 * Check user validity.
	 * Controller calls this by default if derived event handler class does not implement this method, returns always <code>true</code>
	 * 
	 * @param	request	instance of HTTP request
	 * @return			<code>true</code> if user has permission to get the request serviced, <code>false</code> otherwise
	 */
	public boolean checkUserValidity(HttpServletRequest request){
		return true;
	}		
}
