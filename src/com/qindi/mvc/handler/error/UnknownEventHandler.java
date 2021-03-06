package com.qindi.mvc.handler.error;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.qindi.mvc.Reply;
import com.qindi.mvc.beans.Config;
import com.qindi.mvc.beans.response.ResponseDialog;
import com.qindi.mvc.beans.response.ResponsePrint;
import com.qindi.mvc.handler.EventHandler;
import com.qindi.mvc.handler.Map;

public class UnknownEventHandler extends EventHandler {
	
	@Map(include=true)
	public Reply unknownEvent(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException
	{		
		Reply reply = new Reply(request);
		String html = "<table style=\"width:500px;\" cellspacing='0' cellpadding='0' align=\"center\">" +
		"<tr><td style=\"\" align=\"center\">" +
		"<span style=\"color:#BB0000; font-size: 25px;font-weight: bold;\">Error! </span>" +
		"<span style=\"color:#BB0000; font-size: 20px;font-weight: 100;\">Could not process your request.</span><br>" +
		"<span style=\"color:#000; font-size: 15px;\">Request not recognized.<br><br></span>" +
		"</td></tr></table>";
		
		if(isRequestOther(request))
		{
			String title = Config.getTitle() + " - Error processing request";
			String op ="" +
			"<HTML><HEAD><META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><link rel=\"icon\" type=\"image/x-icon\" href=\"images/favicon.ico\">" +
			"<TITLE>"+title+"</TITLE><STYLE  type=\"text/css\">@import \"/Resource/include.css\"; </STYLE></HEAD><BODY>" + html + "</BODY></HTML>";			
			reply.addResponse(new ResponsePrint(op));						
		}
		else
		{
			reply.addResponse(new ResponseDialog("Server Error" , html , null));																
		}	
		return reply;
	}
}
