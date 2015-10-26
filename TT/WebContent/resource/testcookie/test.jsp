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
<script type="text/javascript" src="<%=basePath%>resource/testcookie/proc.js"></script>
<script type="text/javascript" src="<%=basePath%>jquery/jquery-1.4.4.js"></script>

</head>

  
  <body scroll="no">
  请输入需要查询的位数<input id="num" type="text"value="" size="20" ></input><input type="button" value="确定" onclick="init()" >
  <div>
  <iframe id="iframe1" ></iframe>
  
  </div>
  
  <script language = "javascript">
   init()
  </script>

  </body>
</html>
