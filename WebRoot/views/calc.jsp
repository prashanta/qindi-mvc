<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<html>
<body>
<div dojoType="qDojo.dijix.Rounded" style="width:300px" bgType=1>
<div style="margin: 10px;">
    <span style="font-size: 16px; color:#234334; font-weight: bold;">Calculator</span><br><br>
    <form id="calcForm">
    <table border=0 width="120px">
        <tr><td width="auto">A:</td><td> <input id="a" name="a" type="text"></td></tr>
        <tr><td width="auto">B:</td><td> <input id="b" name="b" type="text"></td></tr>
    </table>                        
    </form>        
    <div id = 'bttAdd' dojoType="dijit.form.Button" onClick="ajax.execForm({eid:'calc.add'}, 'calcForm')" label="Add" style="height:25px"></div>
    <div id = 'bttSub' dojoType="dijit.form.Button" onClick="ajax.execForm({eid:'calc.sub'} , 'calcForm')" label="Sub" style="height:25px"></div>
    <div id = 'bttMul' dojoType="dijit.form.Button" label="Mul" onClick="ajax.execForm({eid:'calc.mul'} , 'calcForm')" style="height:25px"></div>
    <div id = 'bttDiv' dojoType="dijit.form.Button" label="Div" onClick="ajax.execForm({eid:'calc.div'} , 'calcForm')" style="height:25px"></div>        
</div>
</div>
</body>
</html>