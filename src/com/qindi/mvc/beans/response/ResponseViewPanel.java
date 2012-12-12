/*
    Copyright (c) 2008-2009, Prashanta Shrestha All Rights Reserved.
    Available under GNU Lesser General Public License >= 3.0 as published by the Free Software Foundation.
    see <http://www.gnu.org/licenses/>
*/
package com.qindi.mvc.beans.response;

import org.json.JSONException;
import org.json.JSONObject;

public class ResponseViewPanel extends ResponseX {
	
	public JSONObject payload = new JSONObject();
	
	public ResponseViewPanel()
	{		
	}

	@Override
	public ResponseX formulate() {
		try 
		{																
			this.put("payload", payload);
			this.put("_rpt","viewPanel");
		} catch (JSONException e) 
		{		
			e.printStackTrace();
		}		
		return this;
	}			
}
