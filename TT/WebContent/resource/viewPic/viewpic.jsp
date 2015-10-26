<%@page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
String path     = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String pid = (String)request.getParameter("pid");
if( null == pid || "".equals(pid)){
	
	pid = "100000530";
}
%>
<html>
<head>
<title>my frist chart</title>
<script>
	var EPM_BASEPATH = '<%=basePath %>';
	var pid = '<%=pid %>';
	 
	var picurl =  '<%=basePath %>' +'workcardQuery!viewPicbyId.do?pid='+ pid ;
	
</script>
<!-- <script type="text/javascript" src="<%=basePath%>resource/viewPic/proc.js"></script> -->
<script type="text/javascript" src="<%=basePath%>jquery/jquery-1.4.4.js"></script>

</head>

  
  <body scroll="no">
<div class='pic'><img id="imgid" alt='显示图片' src=></img></div>
  
  <script language = "javascript">
   
      document.getElementById("imgid").src = picurl;
   
  </script>

  </body>
</html>
