<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.qindi.mvc.Reply"%>
<%@page import="com.qindi.mvc.beans.response.ResponseJSON"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<% 
    String temp = "";
    Reply r = (Reply)request.getAttribute("responseHandler");
    ResponseJSON rx = (ResponseJSON)r.getRList().get(0);
    JSONArray data = (JSONArray)rx.get("data");
    for( int i=0 ; i<data.length(); i++)
    {
        JSONObject row = (JSONObject)data.get(i);
        temp += "<tr style='font: normal normal 13px verdana,arial; background:"+ (i%2==0? "#eee" : "#ddd") +"'><td><b>" + row.getString("event") + "</b></td><td> " + row.getString("class") + " </td><td> " + row.getString("view") + "</td></tr>";     
    }    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>qindi-MVC Event List</title>
<link rel="stylesheet" type="text/css" href="style/style.css">
<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="js/jquery.tablesorter-2.0.5.min.js"></script>
<script type="text/javascript" src="js/jquery.metadata.js"></script>

<script>
$(document).ready(function() {
	/* Activate table sorter */
	$("table").tablesorter({cancelSelection: true});
	$("table").bind("sortEnd",function() { 
        //$(".trow").removeClass("drow").removeClass("lrow");
       // $(".trow").filter(":even").addClass("drow");
       // $(".trow").filter(":odd").addClass("lrow");
    }); 
});
</script>

</head>
<body style="background-color: #fff">
<div style="left:60px; top:30px; position:absolute">
    <span style="font-family: Helvetica; color:#000;">qindi MVC Event List</span><br><br>
    
    	<table id="table" class='tablesorter' cellpadding=0 cellspacing=1>
		<thead> 
			<tr style="height: 30px;"> 
				<th class="col1 list-header colCommon">EVENT</th>
				<th class="col2 list-header colCommon">CLASS</th>
				<th class="col3 list-header colCommon {sorter: 'path'}">VIEW</th>				
			</tr>
		</thead>
		<tbody>
		<%=temp %>
		</tbody>		
		</table>
		<div style="font: normal bold 13px verdana,arial; color:#000;">Total: <%=data.length()%></div>
</div>
</body>
</html>