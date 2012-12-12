/*
    Copyright (c) 2008-2009, Prashanta Shrestha All Rights Reserved.
    Available under GNU Lesser General Public License >= 3.0 as published by the Free Software Foundation.
    see <http://www.gnu.org/licenses/>
*/
package com.qindi.mvc.beans.response;

import org.json.JSONException;
import org.json.JSONObject;

public class ResponseToaster extends ResponseX {

	int msgType = 0;
	String message = null;
	public ResponseToaster(int msgType , String message ) 
	{
		this.msgType = msgType;
		this.message = message;		
	}

	@Override
	public ResponseX formulate() 
	{
		try 
		{			
			JSONObject payload = new JSONObject();			
			payload.put("type",msgType);
			payload.put("message",message);
			this.put("payload", payload);
			this.put("_rpt","toaster");	
		} catch (JSONException e) {
			e.printStackTrace();
		}			
		return this;
	}
}
