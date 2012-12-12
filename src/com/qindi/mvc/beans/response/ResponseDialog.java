/*
    Copyright (c) 2008-2009, Prashanta Shrestha All Rights Reserved.
    Available under GNU Lesser General Public License >= 3.0 as published by the Free Software Foundation.
    see <http://www.gnu.org/licenses/>
*/
package com.qindi.mvc.beans.response;

import org.json.JSONException;
import org.json.JSONObject;

public class ResponseDialog extends ResponseX {
	
	String title = null;
	String html = null;
	String href = null;
	
	public ResponseDialog(String title , String html , String href)
	{
		this.title = title;
		this.html = html;
		this.href = href;
	}

	@Override
	public ResponseX formulate() {		
		try 
		{
			JSONObject payload = new JSONObject();			
			payload.put("title",title);
			payload.put("html",html);
			payload.put("href",href);				
			this.put("payload", payload);
			this.put("_rpt","dialog");
		} catch (JSONException e) 
		{		
			e.printStackTrace();
		}		
		return this;
	}			
}
