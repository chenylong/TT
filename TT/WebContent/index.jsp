<%@page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
String path     = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>my frist chart</title>
<script>
	var EPM_BASEPATH = '<%=basePath %>';
	alert(EPM_BASEPATH)
</script>
<script type="text/javascript" src="<%=basePath%>index.js"></script>
<script type="text/javascript" src="<%=basePath%>jquery/jquery-1.4.4.js"></script>

<script type="text/javascript" src="fusioncharts/js/fusioncharts.js"></script>
<script type="text/javascript" src="fusioncharts/js/themes/fusioncharts.theme.zune.js"></script>

</head>

  
  <body scroll="no">
  请输入需要查询的位数<input id="num" type="text"value="" size="20" ></input><input type="button" value="确定" onclick="shownum()" >
  <script language = "javascript">
   init()
  </script>
  <script type="text/javascript">

 
</script>
  <div id="chartContainer">FusionCharts XT will load here!</div>
   请输入需要的查询语句<input id="sql" type="texteare" value="" size="20" ></input><input type="button" value="确定" onclick="showsql()" >
  <div id="chartContainer2">FusionCharts XT will load here!</div>
  <div id="chartContainer3">FusionCharts XT will load here!</div>
  <div id="chartContainer4">FusionCharts XT will load here!</div>
  </body>
</html>
