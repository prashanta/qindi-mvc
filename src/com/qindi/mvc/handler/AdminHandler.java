package com.qindi.mvc.handler;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qindi.mvc.Query;
import com.qindi.mvc.Reply;
import com.qindi.mvc.beans.Handler;
import com.qindi.mvc.beans.Mapper;
import com.qindi.mvc.beans.response.ResponseJSON;

public class AdminHandler extends EventHandler {

	@Map(include=true, viewUrl="/views/core/admin.jsp")	
	public Reply listEvents(ServletContext cs, HttpServletRequest request, HttpServletResponse response) 
	{				
		Mapper temp = (Mapper)cs.getAttribute("map");		
		HashMap<String, Handler> events = temp.sortHashMap();
				
		ResponseJSON res = new ResponseJSON();	
		JSONArray d = new JSONArray();		
		for(String key : events.keySet())
		{						
			JSONObject data = new JSONObject();
			try {
				data.put("event", key);
				data.put("class", ((Handler)events.get(key)).getHandlerClass());				
				data.put("view", ((Handler)events.get(key)).getViewURL() == null ? "-" : ((Handler)events.get(key)).getViewURL());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			d.put( data );			
		}		
		try {
			res.put("data",d);
		} catch (JSONException e) {			
			e.printStackTrace();
			return null;
		}
		Reply reply = new Reply(Query.getRequestType(request) , Query.getEventId(request));
		reply.addResponse(res);			
		return reply;
	}	
}
