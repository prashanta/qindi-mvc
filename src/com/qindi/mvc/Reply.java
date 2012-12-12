package com.qindi.mvc;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.qindi.mvc.beans.Const;
import com.qindi.mvc.beans.response.ResponsePrint;
import com.qindi.mvc.beans.response.ResponseX;

public class Reply extends ArrayList<ResponseX>{
	
	
	private static final long serialVersionUID = 0L;
	
	private int 	requestType;	
	private String 	eid;			
	private boolean	suppressViewForward = false;
	
	public Reply() {			
		requestType	= 2;				
		eid = null;
	}

	public Reply(HttpServletRequest request) {
		requestType	= Query.getRequestType(request);				
		eid = null; 
	}	
	/**
	 * Constructor
	 * @param response	
	 * @param type	Type of request
	 */
	public Reply(int type, String eid) {		
		this.eid = eid;
		requestType	= type;		
	}
	
	public void addResponse(ResponseX r){
		this.add(r);		
	}
	
	public ResponsePrint getResponsePrint()
	{
		ResponsePrint rp = null;
		for(ResponseX r : this)
		{
			if(r instanceof ResponsePrint)
			{
				rp = (ResponsePrint)(r);				
			}
		}
		return rp;		
	}			
	/**
	 * Send response to client according to request type
	 * 
	 * @throws Exception
	 */
	public void sendResponse(HttpServletResponse response, String eid) throws Exception
	{	
		if(this.size()==0)	
			throw new Exception("No response generated by event handler! There should be at least one response.");
		String resp = "";
		JSONObject responseObj = new JSONObject();
		JSONArray 	target = new JSONArray();			
		responseObj.put("eid" , this.eid );
		if(requestType == Const.OTHER)
		{			
			if(getResponsePrint() == null){
				for(ResponseX r : this)				
					target.put(r.formulate());
				responseObj.put("target" , target);
				resp = responseObj.toString();				
			}
			else
				resp = getResponsePrint().getContent();												
		}else
		{			
			for(ResponseX r : this)
			{	
				target.put(r.formulate());
			}			
			responseObj.put("target" , target);
			resp = responseObj.toString();
		}				
		response.setCharacterEncoding("UTF-8");										
		response.getWriter().print( requestType == Const.OTHER || requestType == Const.AJAX? resp : "<html><body><textarea>"+ resp +"</textarea></body></html>");							
		response.getWriter().flush();
		response.getWriter().close();	 			
	}
	public String getResponse(HttpServletResponse response) throws Exception
	{	
		if(this.size()==0)	
			throw new Exception("No response generated by event handler! There should be at least one response.");
		String resp = "";
		JSONObject responseObj = new JSONObject();
		JSONArray 	target = new JSONArray();			
		responseObj.put("eid" , this.eid );
		if(requestType == Const.OTHER)
		{			
			if(getResponsePrint() == null){
				for(ResponseX r : this)				
					target.put(r.formulate());
				responseObj.put("target" , target);
				resp = responseObj.toString();				
			}
			else
				resp = getResponsePrint().getContent();												
		}else
		{			
			for(ResponseX r : this)
			{	
				target.put(r.formulate());
			}			
			responseObj.put("target" , target);
			resp = responseObj.toString();
		}
		return resp;
	}
	public boolean supressViewForward(){
		return suppressViewForward = true;
	}

	public boolean unsupressViewForward(){
		return suppressViewForward = false;
	}

	public boolean isSuppressViewForward() {
		return suppressViewForward;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}	
	
	public void merge(Reply r){
		for(int i=0 ; i<r.getRList().size() ; i++){
			this.add( r.getRList().get(i) );			
		}		
	}

	public ArrayList<ResponseX> getRList() {
		return this;
	}

	public int getRequestType() {
		return requestType;
	}

	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}
}

