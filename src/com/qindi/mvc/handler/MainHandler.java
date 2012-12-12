package com.qindi.mvc.handler;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qindi.mvc.Reply;

public class MainHandler extends EventHandler {

	@Map(viewUrl="/index.jsp",include=true)	
	public Reply main(ServletContext cs, HttpServletRequest request, HttpServletResponse response)throws Exception 
	{						
		return new Reply();
	}		
}
