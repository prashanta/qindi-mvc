package com.qindi.mvc;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qindi.mvc.beans.Config;
import com.qindi.mvc.beans.Const;
import com.qindi.mvc.beans.Mapper;
import com.qindi.mvc.handler.EventHandler;

/**
 * Servlet implementation class Controller
 */
@WebServlet(description = "qindi controller", urlPatterns = { "/go/*" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public Mapper map;   
	Logger log = LoggerFactory.getLogger(Controller.class);
    /**
     * @return 
     * @see HttpServlet#HttpServlet()
     */
	public void init() {
		try 
		{
			Config.initConfiguration(getServletContext().getRealPath("/WEB-INF/qindi.config.xml"));		
			map = new Mapper(getServletContext().getRealPath("/WEB-INF/"+Config.getEventConfigFile()));
			log.info("QINDI controller initiated.");			
		} catch (Exception e1) {			
			e1.printStackTrace();
		}		
    }

	/** 
	 * GET Request Handler
	 * @param	request		instance of HTTP request 
	 * @param	response	instance of HTTP response 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try {	engine(request,response);} 
		catch (JSONException e) {	e.printStackTrace();}
	}
	
	/**
	 * POST Request Handler
	 * @param	request		instance of HTTP request 
	 * @param	response	instance of HTTP response 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		try {	engine(request,response);} 
		catch (JSONException e) {	e.printStackTrace();}		
	}	
	
	/**
	 * Controller engine that handles all requests
	 * @param	request		instance of HTTP request 
	 * @param	response	instance of HTTP response 
	 * @throws ServletException
	 * @throws IOException
	 * @throws JSONException 
	 */
	private void engine(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException 
	{							
		Reply reply = null;
		String eid = "";
		if(request.getPathInfo() != null && Config.isBreadcrumb())
		{			
			String bc = request.getPathInfo();
			String [] ns = bc.split("/");
			boolean i = false;
			for(String n : ns)
			{	if(n.length()!=0){			 
					eid += ((i==false? "" : ".") + n) ;
					i = true;
				}
			}
		}		
		else
		{
			eid = Query.getEventId(request);			
		}
		log.info(">> REQUEST : [" + eid + "]");
		if(eid.equals("qa.listEvents")){
			getServletContext().setAttribute("map", map);
		}
		EventHandler eventHandler = null;				
		try {
			if(map.containsKey(eid))
				eventHandler = map.getEventHandler(eid);
			else{
				eid = Const.DEFAULTNS+Const.DELIMITER+"unknownEvent";
				eventHandler = map.getEventHandler(eid);
			}
		}catch (Exception e){ 
			log.error("\tEXCEPTION while creating event handler- [" + eid + "] instance for : " + e.toString());
		}
		// PROCESS
		try {
			log.info("\tProcssing Event:[" + eid + "] by class:[" + eventHandler.getClass() + "]");
			reply = process(eventHandler, request, response, eid);			
		}catch(Exception e)
		{		
			e.printStackTrace();
			log.error("\tError while processing request - " + e.getCause() );
			request.setAttribute("errorMsg" , e.getCause().toString());						
			try 
			{
				eventHandler = map.getEventHandler(eid = Const.DEFAULTNS+".errorInEvent");
				reply = process(eventHandler, request, response, eid);
			}catch (Exception e2) 
			{					
				log.error("\tEXCEPTION while processing mandatory function- [" + eid + "] instance for : " + e.toString());
			}								
		}				
		// FORWARD
		reply.setEid(eid);
		reply.setRequestType(Query.getRequestType(request));
		try {
			forward(request, response, reply);
		} catch (Exception e) {			
			e.printStackTrace();
			log.error("\tError while forwarding request - " + e.getCause() );
		}		
	}
	
	/**
	 * Process 
	 * 
	 * @param eh
	 * @param request
	 * @param response
	 * @param eid
	 * @return
	 * @throws Exception 
	 */
	public Reply process(EventHandler eh, HttpServletRequest request, HttpServletResponse response, String eid) throws Exception
	{
		Reply reply = new Reply();
		if( eid != null )
		{			
			Method method = null;	
			String mn = eid.substring(eid.indexOf(Const.DELIMITER)+1);
			// Get method to invoke
			try {
				method = eh.getClass().getDeclaredMethod( mn , new Class[]{ServletContext.class , HttpServletRequest.class , HttpServletResponse.class} );
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}							
			// Invoke method														
			try {				
				reply = (Reply)method.invoke(eh , new Object[]{getServletContext(), request, response} );
			}catch (IllegalArgumentException e) {		
				e.printStackTrace();
			}catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (Exception e) {											
				throw new Exception(e.getCause());
			}			
		}
		return reply;	
	}
	
	/**
	 * Forward to appropriate view generator
	 * 
	 * @param	cs			instance of servletContext
	 * @param	request		instance of HTTP request
	 * @param	response	instance of HTTP response
	 */
	public void forward(HttpServletRequest request, HttpServletResponse response, Reply reply)	throws Exception 
	{	
		String url = map.getUrl(reply.getEid());			
		if( url != null && !reply.isSuppressViewForward() )
		{							
			try {																
				RequestDispatcher rd  = request.getRequestDispatcher( url );				
				if(rd==null)
					throw new Exception("Error 404!");
				request.setAttribute("responseHandler", reply);
				log.info("\tForwarding to - " + url);	
				rd.forward(request, response );
			}catch (Exception e) {					
				e.printStackTrace();
				log.error("\tError while forwarding to JSP - " + e.getMessage());				
			}		
		}
		else
		{			
			try {	
				reply.sendResponse(response,reply.getEid());
			} catch (Exception e) {
				e.printStackTrace();
				log.error("\tError while sending response - " + e.getMessage());				
			}
		}
	}
}
