package com.qindi.mvc.handler.error;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.qindi.mvc.Query;
import com.qindi.mvc.Reply;
import com.qindi.mvc.beans.response.ResponseDialog;
import com.qindi.mvc.beans.response.ResponsePrint;
import com.qindi.mvc.handler.EventHandler;

public class UserInvalidHandler extends EventHandler {

	public Reply processThis(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException
	{
		Reply reply = new Reply(request);
		String html = "<table width='400px' cellspacing=\"5\"><tr><td align=top width=\"5%\"><img src=\"images/userInvalid.png\"></td><td><span class=\"errorTitle\">Invalid User!</span><BR><span class=\"errorText\">Sorry you do not have permission to access this content. Contact your administrator.</span></td></tr></table>";		
		
		if( Query.isRequestAjax(request) || Query.isRequestIFrame(request) ){			
			reply.addResponse( new ResponseDialog("Restricted Content" , html , null));
		}	
		else{
			reply.addResponse( new ResponsePrint(html));			
		}
		return reply;
	}
	
}
