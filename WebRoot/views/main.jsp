<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@page import="com.qindi.mvc.beans.Config;"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>qindi Test</title>
    <style type="text/css">     
        @import "js/dojo/dojo/resources/dojo.css";
        @import "js/dojo/dijit/themes/nihilo/nihilo.css";
        
        @import "js/qDojo/dijix/resources/toaster.css";
        @import "js/qDojo/dijix/resources/rounded.css";
        @import "js/qDojo/dijix/resources/loadingDialog.css";             
        @import "js/qDojo/dijix/resources/menuItem.css";               
		html, body{
		    font-family:Tahoma,Verdana,sans-serif;
		    font-size:10pt;
		    font-size-adjust:none;
		    font-stretch:normal;
		    font-style:normal;
		    font-variant:normal;
		    font-weight:normal;
		    height:100%;
		    line-height:normal;
		    overflow: hidden;
		    margin:0pt;
		    padding:0pt;
		    width:100%;
        }
	    .menuIcon {
		    background-image: url('Resource/image/MenuIcons.png'); /* menu icons sprite image */
		    background-repeat: no-repeat;
		    position: absolute;
		    width: 18px;
		    height: 18px;
		    text-align: center;
		    position: absolute;        
	    }
		.icon0  { background-position: 0px; }
		.icon1  { background-position: -18px; }
		.icon2  { background-position: -36px; }
		.icon3  { background-position: -54px; }
		.icon4  { background-position: -72px; }
		.icon5  { background-position: -90px; }
		.icon6  { background-position: -108px; }
		.icon7  { background-position: -126px; }
		.icon8  { background-position: -144px; }
		.icon9  { background-position: -162px; }
		.icon10 { background-position: -180px; }
		.icon11 { background-position: -198px; }
		.icon12 { background-position: -216px; }

    </style>
    
    <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="isDebug: true, parseOnLoad: true"></script>
    <script type="text/javascript">
        dojo.require("dojo.parser");                
        dojo.registerModulePath("qDojo", "../../qDojo");
        
        dojo.require("dijit.dijit"); // optimize: load dijit layer                       
        dojo.require("dijit.layout.BorderContainer");
        dojo.require("dijit.layout.ContentPane");
        dojo.require("qDojo.dijix.ViewPanel");
              
        dojo.require("qDojo.core.AjaxHandler");
        dojo.require("qDojo.dijix.Menu");
        dojo.require("dijit.form.Button");
                        
                        
        var btt1Action = function (){
          ajax.showLoading();   
        }
        var btt2Action = function (){
          ajax.showDialog("Titlegoeshere","Mauris suscipit, ligula sit amet pharetra semper, nibh ante cursus purus, <br>vel sagittis velit mauris vel metus. Aenean fermentum risus id tortor.");                      
        }
        var btt3Action = function (){
          ajax.toast({type: 1, message: "Morbi a metus. Phasellus enim erat, vestibulum vel, aliquam a, posuere eu, velit. Nullam sapien sem, ornare ac, nonummy non, lobortis a, enim. Nunc tincidunt ante vitae massa.", title: "Able"})
        }
        var btt4Action = function (){
          ajax.toast({type: 0, message: "Morbi a metus. Phasellus enim erat, vestibulum vel, aliquam a, posuere eu, velit.", title:"Nableo"});
        }       
        var btt5Action = function (){
          ajax.toast({type: 0 ,message: "Morbi a metus. Phasellus enim erat, vestibulum vel, aliquam a, posuere eu, velit."});
        }       
        var about = function (){
          ajax.showDialog({title: "About", html: "<table><tr><td align='center'><img src='Resource//image//qindi.png'></td></tr><tr><td align='center'style='font-size:11px'>Version 0.2 Beta</td></tr><tr><td><br><span>Qindi is a MVC framework, made possible <br>with the help of Dojo Toolkit, Java and<br> various third-party libraries.<br><br>Qindi has been designed to harness<br> the capability of Dojo and provide a rigid<br> client-server interaction.</span><br><br></td></tr><tr><td><span style='font-size:10px;color:#BBB;'>Released under the terms of the GNU LGPL 3.0</span></td></tr></table>"});
        }       
    </script>
</head>
<body class="nihilo">
<script type="text/javascript">
    console.log("Dojo Toolkit Version "+dojo.version+" Loaded!!" );
    var ajax = new qDojo.core.AjaxHandler();
</script>

<div dojoType="dijit.layout.BorderContainer" style="width: 100%; height: 100%;" design="headline" gutters="false">

    <div dojoType="dijit.layout.ContentPane" region="top" style="background-color: #fff; height: 65px; width:100%; border-bottom:2px solid #696969;">
        <table><tr><td><span style="color: #696969; font-weight: bold; font-size: 40px; margin-top:-30px">WELCOME TO </span></td><td><img src="Resource/image/qindi3.png"></td></tr></table>        
    </div>
    
    <div id="border1-left" dojoType="dijit.layout.ContentPane" region="left" style="background-color: #C0E559; width: 200px; height:100%; border-right:2px solid #233467;">
         <div id = 'mm' dojoType="qDojo.dijix.Menu" viewCanvas="arena" width="100%">             
            <div id = 'btt6' dojoType="qDojo.dijix.MenuItem"  label="calc.getCalc" iconClass="menuIcon icon1" onClick="arena.renderViewByUrl('views/calc.jsp')"></div>
            <div id = 'btt7' dojoType="qDojo.dijix.MenuItem"  label="Direct Req." iconClass="menuIcon icon0" event="{eid:'calc.getCalc'}" onClick="ajax.exec({eid:'calc.mul',a:11,b:11})" ></div>
            <div id = 'btt8' dojoType="qDojo.dijix.MenuItem"  label="Req. Error"  iconImage="Resource/image/errorIcon.png" event="{eid:'calc.getCalc'}"></div>            
            <div id = 'btt9' dojoType="qDojo.dijix.MenuItem"  label="Twitter"  iconClass="menuIcon icon3"></div>
            <div id = 'btt10' dojoType="qDojo.dijix.MenuItem"  label="ADD"  iconClass="menuIcon icon3" event="{eid:'calc.mul',a:11,b:11}"></div>
        </div> 
    </div>
    
    <div jsid="arena" id="arena" dojoType="qDojo.dijix.ViewPanel" region="center" style="background-color: #FFF; padding:5px;">
        <div dojoType="qDojo.dijix.ViewPanel" style="background-color: #FFF; padding:5px; border:1px solid red;">        
	        <h1>Gafixahuf</h1>
		    <p>Praesent in mauris eu tortor porttitor accumsan. Mauris suscipit, ligula sit amet pharetra semper, nibh ante cursus purus, vel sagittis velit mauris vel metus. Aenean fermentum risus id tortor. Integer imperdiet lectus quis justo. Integer tempor. Vivamus ac urna vel leo pretium faucibus. Mauris elementum mauris vitae tortor. In dapibus augue non sapien. Aliquam ante. Curabitur bibendum justo non orci. </p>		
			<p>Morbi a metus. Phasellus enim erat, vestibulum vel, aliquam a, posuere eu, velit. Nullam sapien sem, ornare ac, nonummy non, lobortis a, enim. Nunc tincidunt ante vitae massa. Duis ante orci, molestie vitae, vehicula venenatis, tincidunt ac, pede. Nulla accumsan, elit sit amet varius semper, nulla mauris mollis quam, tempor suscipit diam nulla vel leo. Etiam commodo dui eget wisi. Donec iaculis gravida nulla. Donec quis nibh at felis congue commodo. Etiam bibendum elit eget erat. </p>		
			<p>Morbi a metus. Phasellus enim erat, vestibulum vel, aliquam a, posuere eu, velit. Nullam sapien sem, ornare ac, nonummy non, lobortis a, enim. Nunc tincidunt ante vitae massa. Duis ante orci, molestie vitae, vehicula venenatis, tincidunt ac, pede. Nulla accumsan, elit sit amet varius semper, nulla mauris mollis quam, tempor suscipit diam nulla vel leo. Etiam commodo dui eget wisi. Donec iaculis gravida nulla. Donec quis nibh at felis congue commodo. Etiam bibendum elit eget erat. </p>		
			<p>Etiam posuere quam ac quam. Maecenas aliquet accumsan leo. Nullam dapibus fermentum ipsum. Etiam quis quam. Integer lacinia. Nulla est. Nulla turpis magna, cursus sit amet, suscipit a, interdum id, felis. Integer vulputate sem a nibh rutrum consequat. Maecenas lorem. Pellentesque pretium lectus id turpis. Etiam sapien elit, consequat eget, tristique non, venenatis quis, ante. Fusce wisi. Phasellus faucibus molestie nisl. Fusce eget urna. Curabitur vitae diam non enim vestibulum interdum. Nulla quis diam. Ut tempus purus at lorem. </p>	
	        <p>Morbi a metus. Phasellus enim erat, vestibulum vel, aliquam a, posuere eu, velit. Nullam sapien sem, ornare ac, nonummy non, lobortis a, enim. Nunc tincidunt ante vitae massa. Duis ante orci, molestie vitae, vehicula venenatis, tincidunt ac, pede. Nulla accumsan, elit sit amet varius semper, nulla mauris mollis quam, tempor suscipit diam nulla vel leo. Etiam commodo dui eget wisi. Donec iaculis gravida nulla. Donec quis nibh at felis congue commodo. Etiam bibendum elit eget erat. </p>
	    </div>    
    </div>
    
    <script type="text/javascript">
    dojo.addOnLoad(
		    function(){
		        dojo.connect(dijit.byId("btt9") , "onClickItem" , function(){ arena.renderViewByUrl('views/calc.jsp');dijit.byId("btt9").activate();});        
		    }
    );
    </script>
    
    <div dojoType="dijit.layout.ContentPane" region="right" style="background-color: #E9E9E9; width: 270px; height:100%;" splitter="true">
        <div id = 'btt1' dojoType="dijit.form.Button" onClick="btt1Action()" label="Show Loading" style="height:25px"></div>
        <div id = 'btt2' dojoType="dijit.form.Button" onClick="about()" label="About" style="height:25px"></div>
        <div id = 'btt3' dojoType="dijit.form.Button" onClick="btt3Action()" label="Toast" style="height:25px"></div>
        <div id = 'btt4' dojoType="dijit.form.Button" onClick="btt4Action()" label="Toast" style="height:25px"></div>
        <div id = 'btt5' dojoType="dijit.form.Button" onClick="btt5Action()" label="Toast" style="height:25px"></div> 
    </div>
    
    <div dojoType="dijit.layout.ContentPane" region="bottom" style="background-color: #84C5C6; height: 50px; width:100%">
          <center style="font-size:11px;color:#FFF">Copyright © 2008-2009 qindi. All Rights Reserved</center>
    </div>
</div>
</body>
</html>
