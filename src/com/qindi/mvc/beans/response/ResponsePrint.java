/*
    Copyright (c) 2008-2009, Prashanta Shrestha All Rights Reserved.
    Available under GNU Lesser General Public License >= 3.0 as published by the Free Software Foundation.
    see <http://www.gnu.org/licenses/>
*/
package com.qindi.mvc.beans.response;

import org.json.JSONException;



public class ResponsePrint extends ResponseX {

	String content;
	public ResponsePrint(String content) {	
		super();
		this.content = content;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public ResponseX formulate() {		
		try {
			this.put("_rpt","print");
			this.put("payload",this.content);										
		} catch (JSONException e) 
		{		
			e.printStackTrace();
		}		
		return this;
	}	
		
}
