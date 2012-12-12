<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@page import="com.qindi.mvc.Reply"%>
<% 
Reply r = (Reply)request.getAttribute("responseHandler");
String data = r.getResponse(response);
%> 
Response: <%=data %>