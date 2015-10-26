<%@page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
String path     = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String pid = (String)request.getParameter("id");
System.out.println(pid);
if( null == pid || "".equals(pid)){
	
	pid = "20";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic DataGrid - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/jquery_easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/jquery_easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/jquery_easyui/demo/demo.css">
	<script type="text/javascript" src="<%=basePath%>/jquery_easyui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/jquery_easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>jquery/jquery-1.4.4.js"></script>
	
	<style type="text/css">
	div1,.div2 { float:left; display:inline;}
    div1 { width:400px; margin-left:30px;}
    div2 { width:700px; margin:0 20px 0 20px;}
	
	</style>
</head>
<body>
	<h2>Basic DataGrid</h2>
	<p>The DataGrid is created from markup, no JavaScript code needed.</p>
	<div style="margin:20px 0;"></div>
	
	
	<div>
	<table>
    <tr>
	<td>
	<div>
	<table class="easyui-datagrid" title="Basic DataGrid" style="width:700px;height:350px";pagination="true";
			data-options="singleSelect:true,collapsible:true,url:'<%=basePath %>/workcardQuery!viewPicAll.do?pageid='+'<%=pid %>',method:'get',
			onClickRow:onclickgrid,pagination:true,onDblClickRow:onDbClickRow"
			 >
		<thead>
			<tr>
				<th data-options="field:'image_id',width:80">Image ID</th>
				<th data-options="field:'image_name',width:100">Image NAME</th>
				<th data-options="field:'image_type',width:20,align:'right'">Image_TYPE</th>
				<th data-options="field:'image_url',width:180,align:'right'">Image Date</th>
			</tr>
		</thead>
	</table>
	</div>
	</td>
	<td>
	<div class='div2,pic'><img id="imgid" alt='显示图片' src=></img></div>
    </td>
    </tr>	
	</table>
    </div>
	
	<script type="text/javascript" >
	
	function onclickgrid(Index,Data){
		
		//alert(Data.image_id);
		
		var pid=Data.image_id;
		var picurl =  '<%=basePath %>' +'workcardQuery!viewPicbyId.do?pid='+ pid ;
		document.getElementById("imgid").src = picurl;
		
		
		
	}
	
    function onDbClickRow(rowIndex, rowData) {
        //            alert('这是Name:' + rowData.Name + '这个是ID:' + rowData.ID);            
        if (rowData.image_url != '') {
        	
        	alert(rowData.image_url)
            document.getElementById("imgid").src =rowData.image_url;
        }
    } 
	
    <%-- 
	$('#surveryGird').datagrid( {
		  loadMsg : "正在加载数据",
		  url : '<%=basePath %>/workcardQuery!viewPicAll.do',
		  height : 320,
		  width : 270,
		  searching : true,
		  pagination : true,//分页
		  sortName: 'reportTimeStr',
		  sortOrder: 'desc',
		  remoteSort: false,
		  onClickRow:function(rowIndex,rowData){
		   //showSelectedSurveryGaiYaoMsgOnMap(rowData);
		   
		   alert(rowIndex)
		  },
		  onDblClickRow :function(rowIndex,rowData){
		   //showSelectedSurveryDataOnMap(rowData);
		   alert(rowData)
		  } });
	
	--%>
	</script>

</body>
</html>